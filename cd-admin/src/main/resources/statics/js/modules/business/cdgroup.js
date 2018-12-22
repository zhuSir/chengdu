$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/cdgroup/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '宣传图', name: 'publicityImgs', index: 'publicity_imgs', width: 80,formatter:function(cellvalue, options, rowObject){
                return jointImgUrl(cellvalue);
            } },
			{ label: '背景图', name: 'backstageImgs', index: 'backstage_imgs', width: 80,formatter:function(cellvalue, options, rowObject){
                return jointImgUrl(cellvalue);
            } },
            { label: '头像', name: 'headImg', index: 'head_img', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return jointImgUrl(cellvalue);
            } },
            { label: '描述', name: 'description', index: 'description', width: 80 },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			{ label: '收藏数', name: 'collectNum', index: 'collect_num', width: 80 }			
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
		cdGroup: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.cdGroup = {};
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
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
			// var url = vm.cdGroup.id == null ? "business/cdgroup/save" : "business/cdgroup/update";
            var url = baseURL + "business/cdgroup/save";
            //vm.cdActivity.id == null ? baseURL + "business/cdactivity/save" : baseURL + "business/cdactivity/update";
            $("#groupForm").attr("action",url);
            $("#id").val(vm.cdGroup.id);
            $("#groupForm").ajaxSubmit(function(r) {
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
				    url: baseURL + "business/cdgroup/delete",
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
			$.get(baseURL + "business/cdgroup/info/"+id, function(r){
                vm.cdGroup = r.cdGroup;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});