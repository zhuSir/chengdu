$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/cdorder/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true,hidden:true },
			{ label: '订单编号', name: 'code', index: 'code', width: 80 },
            { label: '商家名称', name: 'storeName', index: 'store_name', width: 80 },
            { label: '用户手机号', name: 'userPhone', index: 'user_hone', width: 80 },
            { label: '商品名称', name: 'productName', index: 'product_name', width: 80 },
			{ label: '收货人手机号', name: 'phone', index: 'phone', width: 80 }, 			
			{ label: '收货人姓名', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
            { label: '订单状态', name: 'state', index: 'state', width: 80,hidden:true },
			{ label: '订单状态', name: 'stateName', index: 'stateName', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return showValue(rowObject.state, options, rowObject,vm.status);
            } },
			{ label: '收货地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 },
			{ label: '数量', name: 'count', index: 'count', width: 50 },
			{ label: '总价', name: 'sum', index: 'sum', width: 50 },
			{ label: '运费', name: 'freight', index: 'freight', width: 50 },
            { label: '预约开始时间', name: 'startTime', index: 'start_time', width: 50 },
            { label: '预约结束时间', name: 'endTime', index: 'end_time', width: 50 },
			{ label: '支付类型', name: 'payType', index: 'pay_type', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return showValue(cellvalue, options, rowObject,vm.payType);
            } },
            { label: '支付结果', name: 'payDescription', index: 'pay_description', width: 60 },
            { label: '快递公司', name: 'expressCompany', index: 'express_company', width: 80 },
            { label: '快递编号', name: 'expressCode', index: 'express_code', width: 80 }
        ],
		viewrecords: true,
        width:"100%",
        shrinkToFit: false,
        autowidth: true,
        height:"100%",
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25,
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
        	// $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    function showValue(cellvalue, options, rowObject,e){
        for(var i in e){
            if(e[i].code == cellvalue ){
                return e[i].value;
            }
        }
        return "";
    }
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		cdOrder: {},
		payType:[],
		status:[],
        payResults:[]
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
            var status = $('#status').val();
            var payResults = $('#payResults').val();
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:{
                	'startTime':startTime,
                    'endTime':endTime,
					'phone':phone,
					'storeName':storeName,
					'status':status,
                    "payResults":payResults
                }

            }).trigger("reloadGrid");
		},
        init: function(){
            $.get(baseURL + "business/cdorder/init", function(r){
                vm.payType = r.payType;
                vm.status = r.status;
                vm.payResults = r.payResults;
            });
        },
        shipments: function() {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            var rowData = getSelectedRowData(id);
            var state = rowData.state;
            if(state != 2){
                alert("该订单状态不能发货")
                return ;
            }
            var orderId = rowData.id;
            //发货
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "发货",
                area: ['600px', '255px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#expressInfo"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var expressCompany = $('#expressCompany').val();
                    if(expressCompany == "" || expressCompany == null){
                        alert("请输入快递公司");
                        return ;
                    }
                    var expressCode = $('#expressCode').val();
                    if(expressCode == "" || expressCode == null){
                        alert("请输入快递单号");
                        return ;
                    }
                    $.ajax({
                        type: "POST",
                        url: baseURL + "business/cdorder/shipments",
                        data: {orderId:orderId,expressCompany:expressCompany,expressCode:expressCode},
                        success: function(r){
                            layer.close(index);
                            if(r.code === 0){
                                alert('操作成功', function(){
                                    vm.reload();
                                });
                            }else{
                                alert(r.msg);
                            }
                        }
                    });

                }
            });
        },
        refund: function() {
            var id = getSelectedRow();
            if(id == null){
            	alert("请选择要退款订单")
                return ;
            }
            var rowData = getSelectedRowData(id);
            var orderId = rowData.id;
            //退款
            confirm('确定要对该订单退款么？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "business/cdorder/refund",
                    data: "orderId=" + orderId,
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        finished: function() {
            var id = getSelectedRow();
            if(id == null){
                alert("请选择要退款订单")
                return ;
            }
            var rowData = getSelectedRowData(id);
            var state = rowData.state;
            if(state != 2){
                alert("该订单状态不能进行完成操作")
                return ;
            }
            var orderId = rowData.id;
            //退款
            confirm('确定要完成该订单么？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "business/cdorder/finished",
                    data: "orderId=" + orderId,
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        exportOrder: function(){
            var startTime = $('#startTime').val();
            var endTime = $('#endTime').val();
            var phone = $('#phone').val();
            var storeName = $('#storeName').val();
            var status = $('#status').val();
            var payResults = $('#payResults').val();
            var path = baseURL + "business/cdorder/export" +
                "?startTime="+startTime+
                '&endTime='+endTime+
                '&phone='+phone+
                '&storeName='+storeName+
                '&status='+status+
                "&payResults="+payResults;
            window.open(path);
        }
	},
    created: function(){
        this.init();
    }

});