$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'business/cdactivity/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true,hidden:true },
			{ label: '活动类型', name: 'type', index: 'type', width: 80,formatter:function(cellvalue, options, rowObject){
                return showValue(cellvalue, options, rowObject,vm.type);
			} },
			{ label: '显示位置', name: 'locationType', index: 'location_type', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return showValue(cellvalue, options, rowObject,vm.locationType);
            } },
			{ label: '标题', name: 'title', index: 'title', width: 80 }, 			
			{ label: '状态', name: 'state', index: 'state', width: 80,formatter:function(cellvalue, options, rowObject){
                return showValue(cellvalue, options, rowObject,vm.states);
            } },
			{ label: '商铺名称', name: 'storeName', index: 'store_name', width: 80 },
			{ label: '链接', name: 'link', index: 'link', width: 80 }, 			
			{ label: '图片简介', name: 'imgs', index: 'imgs', width: 80,formatter:function(cellvalue, options, rowObject){
                return "<img style='width:50px;' src='"+cellvalue+"' />"
            } }
        ],
		viewrecords: true,
        height: 600,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        // rownumWidth: 25,
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
		cdActivity: {},
		type:[],
        locationType:[],
        states:[],
        storesList:[]
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.cdActivity = {};
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
			var url = baseURL + "business/cdactivity/save";
			//vm.cdActivity.id == null ? baseURL + "business/cdactivity/save" : baseURL + "business/cdactivity/update";
            $("#activityForm").attr("action",url);
			$("#id").val(vm.cdActivity.id);
            $("#activityForm").ajaxSubmit(function(r) {
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
				    url: baseURL + "business/cdactivity/delete",
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
			$.get(baseURL + "business/cdactivity/info/"+id, function(r){
                vm.cdActivity = r.cdActivity;
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
            $.get(baseURL + "business/cdactivity/init", function(r){
                vm.type = r.type;
                vm.locationType = r.locationType;
                vm.states = r.states;
                vm.storesList = r.storesList;
            });
        }
	},
	created: function(){
        this.init();
    }
});