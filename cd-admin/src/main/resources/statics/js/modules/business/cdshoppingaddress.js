$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/cdshoppingaddress/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true },
			{ label: '用户id', name: 'userId', index: 'user_id', width: 80,hidden: true  },
			{ label: '用户名', name: 'userName', index: 'user_name', width: 80 },
			{ label: '手机号', name: 'mobile', index: 'mobile', width: 80 }, 			
			{ label: '省', name: 'province', index: 'province', width: 80 }, 			
			{ label: '城市', name: 'city', index: 'city', width: 80 }, 			
			{ label: '地区', name: 'area', index: 'area', width: 80 }, 			
			{ label: '详细地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '是否默认地址', name: 'isDefault', index: 'is_default', width: 80,formatter:function(cellvalue, options, rowObject){
                return showValue(cellvalue, options, rowObject,vm.isDefault);
            }}
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
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		cdShoppingAddress: {},
        isDefault:[]
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.cdShoppingAddress = {};
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
			var url = vm.cdShoppingAddress.id == null ? "business/cdshoppingaddress/save" : "business/cdshoppingaddress/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.cdShoppingAddress),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "business/cdshoppingaddress/delete",
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
			$.get(baseURL + "business/cdshoppingaddress/info/"+id, function(r){
                vm.cdShoppingAddress = r.cdShoppingAddress;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			var phone = $('#phone').val();
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:{
                    'phone':phone
                }
            }).trigger("reloadGrid");
		},
        init: function(){
            $.get(baseURL + "business/cdshoppingaddress/init", function(r){
                vm.isDefault = r.isDefault;
            });
        }
	},
    created: function(){
        this.init();
    }
});