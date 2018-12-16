$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/cduser/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户名', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: '手机号', name: 'phone', index: 'phone', width: 80 }, 			
			{ label: '状态', name: 'state', index: 'state', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return showValue(cellvalue, options, rowObject,vm.state);
            } },
			{ label: '年龄', name: 'age', index: 'age', width: 80 }, 			
			// { label: '维度', name: 'lat', index: 'lat', width: 80 },
			// { label: '经度', name: 'lon', index: 'lon', width: 80 },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '最后一次登录ip', name: 'lastLoginIp', index: 'last_login_ip', width: 80 }, 			
			{ label: '最后一次登录时间', name: 'lastLoginTime', index: 'last_login_time', width: 80 }, 			
			{ label: '用户头像', name: 'headImg', index: 'head_img', width: 80,formatter:function(cellvalue, options, rowObject){
                return "<img style='width:50px;' src='"+cellvalue+"' />"
            } }
			// { label: '密码(密文）', name: 'password', index: 'password', width: 80 },
			// { label: '真实密码（未加密）', name: 'realPassword', index: 'real_password', width: 80 }
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

    //显示返回值
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
		cdUser: {},
        state:[]
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.cdUser = {};
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
			var url = vm.cdUser.id == null ? "business/cduser/save" : "business/cduser/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.cdUser),
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
				    url: baseURL + "business/cduser/delete",
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
			$.get(baseURL + "business/cduser/info/"+id, function(r){
                vm.cdUser = r.cdUser;
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
            $.get(baseURL + "business/cduser/init", function(r){
                vm.state= r.state;
            });
        }
	},
    created: function(){
        this.init();
    }
});