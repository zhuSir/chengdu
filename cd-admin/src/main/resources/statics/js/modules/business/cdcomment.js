$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/cdcomment/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true,hidden:true },
			{ label: '分数', name: 'score', index: 'score', width: 80 }, 			
			{ label: '内容', name: 'content', index: 'content'},
			{ label: '图片', name: 'imgs', index: 'imgs', width: 80 ,formatter:function(cellvalue, options, rowObject){
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
            }},
			{ label: '评论时间', name: 'createTime', index: 'create_time', width: 80 },
			{ label: '类型', name: 'type', index: 'type', width: 80,formatter:function(cellvalue, options, rowObject){
                return showValue(cellvalue, options, rowObject,vm.type);
            }},
            { label: '关联对应名称', name: 'name', index: 'name', width: 80 },
            { label: '状态', name: 'status', index: 'status', width: 80,formatter:function(cellvalue, options, rowObject){
                return showValue(cellvalue, options, rowObject,vm.status);
            }}
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
		cdComment: {},
		type: [],
        status:[]
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.cdComment = {};
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
			var url = vm.cdComment.id == null ? "business/cdcomment/save" : "business/cdcomment/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.cdComment),
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
				    url: baseURL + "business/cdcomment/delete",
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
			$.get(baseURL + "business/cdcomment/info/"+id, function(r){
                vm.cdComment = r.cdComment;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
            var content = $('#content').val();
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
				postData:{
                	"content":content
				}
            }).trigger("reloadGrid");
		},
        init: function(){
            $.get(baseURL + "business/cdcomment/init", function(r){
                vm.type = r.type;
                vm.status = r.status;
            });
        }
	},
    created: function(){
        this.init();
    }
});