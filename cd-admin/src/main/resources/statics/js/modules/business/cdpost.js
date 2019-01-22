$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/cdpost/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true ,hidden:true},
            { label: '圈子名称', name: 'groupName', index: 'group_name', width: 80 },
            { label: '标题', name: 'title', index: 'title', width: 80 },
            { label: '内容', name: 'content', index: 'content'},
			{ label: '图片', name: 'imgs', index: 'imgs', width: 80,formatter:function(cellvalue, options, rowObject){
                return jointImgUrl(cellvalue);
            } },
			{ label: '发帖用户', name: 'userName', index: 'user_name', width: 80 },
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
    function jointImgUrl(cellvalue){
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
    }
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		cdPost: {},
        groups:[]
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.cdPost = {};
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
			var url = vm.cdPost.id == null ? "business/cdpost/save" : "business/cdpost/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.cdPost),
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
				    url: baseURL + "business/cdpost/delete",
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
			$.get(baseURL + "business/cdpost/info/"+id, function(r){
                vm.cdPost = r.cdPost;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			var groupId = $('#groupId').val();
            var name = $('#name').val();
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:{
                    'name':name,
                    'groupId':groupId
                }
            }).trigger("reloadGrid");
		},
        init: function(){
            $.get(baseURL + "business/cdpost/init", function(r){
                vm.groups = r.groups;
            });
        }
	},
    created: function(){
        this.init();
    }
});