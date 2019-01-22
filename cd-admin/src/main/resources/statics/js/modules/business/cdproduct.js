$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/cdproduct/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true,hidden:true },
			{ label: '商品名称', name: 'name', index: 'name', width: 80 },
			{ label: '商铺类别', name: 'type', index: 'type', width: 80 }, 			
			{ label: '简介', name: 'about', index: 'about', width: 80 }, 			
			{ label: '简介图片', name: 'imgs', index: 'imgs', width: 80 ,formatter:function(cellvalue, options, rowObject){
                if(cellvalue != null && cellvalue != ""){
                    var resStr = "";
                    var urls = cellvalue.split(",");
                    for(var i =0;i<urls.length;i++){
                        if(urls[i] != "" && urls[i] != null){
                            resStr += "<img style='width:50px;' src='"+urls[i]+"' />";
                        }
                    }
                    return resStr;
                }else{
                    return "<img style='width:50px;' />";
                }
            } },
			{ label: '运费', name: 'freight', index: 'freight', width: 80 }, 			
			{ label: '缩略图', name: 'shortImg', index: 'short_img', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return "<img style='width:50px;' src='"+cellvalue+"' />"
            } },
			{ label: '属性', name: 'tag', index: 'tag', width: 80 ,formatter:function(cellvalue, options, rowObject){
                // var resStr = "";
                // var urls = cellvalue.split(",");
                // for(var i =0;i<urls.length;i++){
                //     resStr += "<span class='label label-default' style='margin: 5px;'>"+urls[i]+"</span>";
                // }
                return "<span class='label label-default' style='margin: 5px;'>"+cellvalue+"...</span>";
            } },
			{ label: ' 今日库存', name: 'todayInventory', index: 'today_inventory', width: 80 }, 			
			{ label: '总库存', name: 'sumInventory', index: 'sum_inventory', width: 80 }, 			
			{ label: '价格', name: 'price', index: 'price', width: 80 }, 			
			{ label: '商铺名称', name: 'storeName', index: 'store_id', width: 80 },
            { label: '属性类型', name: 'attributeType', index: 'attribute_type', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return showValue(cellvalue, options, rowObject,vm.attributeTypeList);
            } },
			{ label: '状态', name: 'state', index: 'state', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return showValue(cellvalue, options, rowObject,vm.states);
            } },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }			
        ],
		viewrecords: true,
        height: 600,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });

    function showValue(cellvalue, options, rowObject,e){
        for(var i in e){
            if(e[i].code == cellvalue ){
                return e[i].value;
            }
        }
    }

    //时间控件初始化
    laydate.render({
        elem: '#specialPrice',
        mark: vm.priceMark,
        position: 'static',
        showBottom: false,
        done: function(value, date){
            vm.current = value;
            var val = vm.getSpecialPrice(vm.specialPriceList,value);
            if(val != null){
                $('#specialPriceValue').val(val.price);
                $('#specialInventoryValue').val(val.inventory);
            }else{
                $('#specialPriceValue').val(0);
                $('#specialInventoryValue').val(0);
            }
        }
    });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		cdProduct: {},
        states:[],
        storesList:[],
		tags:[{
			nameTitle:"请输入名称",
            valueTitle:"请输入值",
			sortTitle:1,
            sort:1,
            idName: "tags[0].productId",
            nameName: "tags[0].name",
            valueName: "tags[0].value",
            sortName: "tags[0].sort"
		}],
        attributeTypeList:[],
        priceMark:{},
        specialPriceList:[],
        current:''
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.cdProduct = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
            var url = baseURL + "business/cdproduct/save";
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $("#productForm").attr("action",url);
            $("#id").val(vm.cdProduct.id);
            $("#productForm").ajaxSubmit(function(r) {
                layer.close(index);
                if(r.code == 0){
                    alert('操作成功', function(index){
                        vm.reload();
                    });
                }else{
                    alert(r.msg);
                }
            })
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "business/cdproduct/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "business/cdproduct/info/"+id, function(r){
                vm.cdProduct = r.cdProduct;
                if(r.tags != null && r.tags != ''){
                    vm.tags = r.tags;
                    for(var i =0;i<vm.tags.length;i++){
                        vm.tags[i].tagIdName = "tags["+i+"].id";
                        vm.tags[i].idName = "tags["+i+"].productId";
                        vm.tags[i].nameName = "tags["+i+"].name";
                        vm.tags[i].valueName = "tags["+i+"].value";
                        vm.tags[i].sortName = "tags["+i+"].sort";
                    }
                }
                console.log(vm.tags);
                if(r.specialPriceList != null && r.specialPriceList != ''){
                    vm.specialPriceList = r.specialPriceList;
                    //拼接json
                    var specialMark = "{";
                    for(var i =0;i<vm.specialPriceList.length;i++){
                        var specialObj = vm.specialPriceList[i];
                        var strDate = specialObj.strDate;
                        var price = specialObj.price;
                        var str = "'"+strDate+"':'',";
                        specialMark+=str;
                    }
                    if(specialMark.indexOf(",") != -1 ){
                        specialMark = specialMark.substr(0,specialMark.length-1);
                    }
                    specialMark += "}"
                    vm.priceMark = eval('(' + specialMark + ')');
                    vm.initSpecialTime();
                }
                console.log(vm.priceMark);
            });
		},
		reload: function (event) {
            // var sortVal = $(".sortClass");
            // console.log("sortVal",sortVal);
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			var storeId = $('#storeId').val();
            var name = $('#name').val();
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:{
                    "storeId": storeId,
                    "name":name
                }
            }).trigger("reloadGrid");
		},
        init: function(){
            $.get(baseURL + "business/cdproduct/init", function(r){
                vm.states = r.state;
                vm.storesList = r.storesList;
                vm.attributeTypeList = r.attributeTypeList;
            });
        },
        addTag:function(){
            var length = vm.tags.length;
			var sortIndex = length;
            sortIndex = sortIndex+1;
        	var itemObj = {
                nameTitle:"请输入名称",
                valueTitle:"请输入值",
                sortTitle: new Number(sortIndex),
                sort: sortIndex,
                idName: "tags["+length+"].productId",
                nameName: "tags["+length+"].name",
                valueName: "tags["+length+"].value",
                sortName: "tags["+length+"].sort"
            };
        	vm.tags.push(itemObj);
        	console.log(vm.tags);
        	return false;
		},
        getSpecialPrice : function(list,key){
            for(var i=0;i<list.length;i++){
                var obj = list[i];
                if(obj.strDate == key){
                    return obj;
                }
            }
            return null;
        },
        setSpecialPrice : function(list,key,value){
            for(var i=0;i<list.length;i++){
                var obj = list[i];
                if(obj.strDate == key){
                    obj.price = value;
                    list[i] = obj;
                    return list;
                }
            }
            var newObj = {
                strDate : key,
                price : value
            };
            list.push(newObj);
            return list;
        },
        setSpecialInventory : function(list,key,value){
            for(var i=0;i<list.length;i++){
                var obj = list[i];
                if(obj.strDate == key){
                    obj.inventory = value;
                    list[i] = obj;
                    return list;
                }
            }
            var newObj = {
                strDate : key,
                inventory : value
            };
            list.push(newObj);
            return list;
        },
        initSpecialTime : function(){
            $("#specialPrice").html("");
            //时间控件初始化
            laydate.render({
                elem: '#specialPrice',
                mark: vm.priceMark,
                position: 'static',
                showBottom: false,
                done: function(value, date){
                    vm.current = value;
                    var val = vm.getSpecialPrice(vm.specialPriceList,value);
                    if(val != null){
                        $('#specialPriceValue').val(val.price);
                        $('#specialInventoryValue').val(val.inventory);
                    }else{
                        $('#specialPriceValue').val(0);
                        $('#specialInventoryValue').val(0);
                    }
                }
            });
        },
        addSpecialTime : function(){
            var specialPrice = $('#specialPriceValue').val();
            var specialInventory = $('#specialInventoryValue').val();
            vm.specialPriceList = vm.setSpecialPrice(vm.specialPriceList,vm.current,specialPrice);
            vm.specialPriceList = vm.setSpecialInventory(vm.specialPriceList,vm.current,specialInventory);
            vm.specialPriceList[0].productId=vm.cdProduct.id;
            $.ajax({
                type: "POST",
                url: baseURL + "business/cdproduct/save/price",
                contentType: "application/json",
                data: JSON.stringify(vm.specialPriceList),
                success: function(r){
                    if(r.code == 0){
                        alert('操作成功', function(index){
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        }
    },
    created: function(){
        this.init();
    }
});
