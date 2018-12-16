$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/cdorder/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '订单编号', name: 'code', index: 'code', width: 80 },
            { label: '商家名称', name: 'storeName', index: 'store_name', width: 80 },
            { label: '商品名称', name: 'productName', index: 'product_name', width: 80 },
			{ label: '收货人手机号', name: 'phone', index: 'phone', width: 80 }, 			
			{ label: '收货人姓名', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '订单状态', name: 'state', index: 'state', width: 80 }, 			
			{ label: '收货地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 },
			{ label: '数量', name: 'count', index: 'count', width: 80 }, 			
			{ label: '总价', name: 'sum', index: 'sum', width: 80 }, 			
			{ label: '运费', name: 'freight', index: 'freight', width: 80 }
			// { label: '支付类型', name: 'payType', index: 'pay_type', width: 80 }
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

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		cdOrder: {}
	},
	methods: {
		query: function () {

			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.cdOrder = {};
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
			var url = vm.cdOrder.id == null ? "business/cdorder/save" : "business/cdorder/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.cdOrder),
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
				    url: baseURL + "business/cdorder/delete",
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
			$.get(baseURL + "business/cdorder/info/"+id, function(r){
                vm.cdOrder = r.cdOrder;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
            var startTime = $('#startTime').val();
            var endTime = $('#endTime').val();
            var phone = $('#phone').val();
            var storeName = $('#storeName').val();
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:{
                	'startTime':startTime,
                    'endTime':endTime,
					'phone':phone,
					'storeName':storeName
                }

            }).trigger("reloadGrid");
		}
	}
});