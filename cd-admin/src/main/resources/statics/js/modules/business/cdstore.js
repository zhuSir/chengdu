$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/cdstore/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true,hidden:true },
			{ label: '店铺名称', name: 'name', index: 'name', width: 80 },
            { label: '缩略图', name: 'shortImg', index: 'short_img',formatter:function(cellvalue, options, rowObject){
                    return "<img style='width:50px;' src='"+cellvalue+"' />";
            } },
			{ label: '店铺地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '简介', name: 'about', index: 'about', width: 80 }, 			
			{ label: '店铺类型', name: 'type', index: 'type', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return showValue(cellvalue, options, rowObject,vm.storeType);
            } },
			{ label: '轮播图', name: 'imgs', index: 'imgs',formatter:function(cellvalue, options, rowObject){
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
			{ label: '店铺联系电话', name: 'phone', index: 'phone', width: 80 }, 			
			{ label: '备用电话', name: 'backupPhone', index: 'backup_phone', width: 80 }, 			
			{ label: '维度', name: 'lat', index: 'lat', width: 80 }, 			
			{ label: '经度', name: 'lon', index: 'lon', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
			{ label: '最后更新时间', name: 'updateTime', index: 'update_time', width: 80 },
			{ label: '评分', name: 'score', index: 'score', width: 80 },
			{ label: '销量', name: 'sales', index: 'sales', width: 80 }, 			
			{ label: '价格', name: 'price', index: 'price', width: 80 },
            { label: '商家名称', name: 'userName', index: 'user_name', width: 80 }
        ],
		viewrecords: true,
        height: 385,
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

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		cdStore: {},
        storeType:[],
        userList:[]
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.cdStore = {};
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
			//var url = vm.cdStore.id == null ? "business/cdstore/save" : "business/cdstore/update";
            var url = baseURL + "business/cdstore/save";
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $("#storeForm").attr("action",url);
            $("#id").val(vm.cdStore.id);
            $("#storeForm").ajaxSubmit(function(r) {
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
				    url: baseURL + "business/cdstore/delete",
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
			$.get(baseURL + "business/cdstore/info/"+id, function(r){
                vm.cdStore = r.cdStore;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        init: function(){
            $.get(baseURL + "business/cdstore/init", function(r){
            	debugger;
                vm.storeType= r.storeType;
                vm.userList = r.userList;
            });
        }
	},
    created: function(){
        this.init();
    }
});