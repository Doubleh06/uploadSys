/**
 * 初始化 jqGrid 的封装
 */
(function () {
    var totalFee = 0;
    var JqGrid = function (tableId, pagerId, options) {
        this.jqGridInstance = null;
        this.tableId = tableId;
        this.pagerId = pagerId;
        this.options = options;
    };

    JqGrid.prototype = {
        /**
         * 初始化jqGrid
         */
        init: function () {
            var tableId = this.tableId;
            var options = this.options;
            var jqGridInstance = $(tableId).jqGrid({
                url: options.url,  //后台请求数据的json地址
                datatype: "json",
                mtype: options.mtype ? options.mtype : "GET",   //请求方式
                height: options.height ? options.height : "auto",   //表格高度
                width: options.width ? options.width : "auto",  //表格宽度
                autowidth: options.autowidth,   //如果开启，则width失效
                postData: options.postData, //会添加到url上
                colNames: options.colNames,
                colModel: options.colModel,
                page: 1,    //初始页码
                viewrecords : true, //是否要显示总记录数
                sortable: options.sortable, //是否可以排序
                sortname: options.sortname,    //默认排序的字段
                sortorder: options.sortorder,  //默认排序方向
                rowNum: options.rowNum ? options.rowNum : 30, //初始化pageSize
                rowList: options.rowList ? options.rowList : [10,20,30,50,100], //调整表格显示的记录数
                rownumbers: options.rownumbers ? options.rownumbers : false,    //如果为true则会在表格左边新增一列，显示行顺序号，从1开始递增
                pager : this.pagerId,   //定义翻页用的导航栏，必须是有效的html元素
                altRows: false, //不知道干嘛用的
                shrinkToFit: options.shrinkToFit && true,   //这个也不知道。。
                onSelectRow : options.onSelectRow,
                multiselect: options.multiselect ? options.multiselect : false, //开启多选
                //事件回调
                gridComplete: options.gridComplete,
                beforeSelectRow: options.beforeSelectRow,
                loadComplete : options.loadComplete,

                jsonReader : {
                    //返回json的格式匹配
                    root: function (data) {
                        return data.obj.rows
                    },
                    page: function (data) {
                        return data.obj.page
                    },
                    total: function (data) {
                        return data.obj.total
                    },
                    records: function (data) {
                        totalFee = data.obj2;
                        return data.obj.records
                    },
                    repeatitems: false,
                    id: "id"
                }
            });

            //响应式
            //FIXME 这里这样做响应式不对
            $(window).bind('resize', function () {
                var width = $(".jqGrid_wrapper").width();
                jqGridInstance.setGridWidth(width);
            });

            this.jqGridInstance = jqGridInstance;
            return this;
        },

        /**
         * 设置ajax请求时候附带的参数
         */
        setParams: function (params) {
            this.jqGridInstance.setGridParam({
                postData: params
            });
            return this;
        },

        /**
         * 重新加载jqGrid，参数会拼接在url上
         *
         * @param params    携带的参数，可选
         */
        reload: function (params) {
            if (params) {
                this.setParams(params);
            }
            this.jqGridInstance.trigger("reloadGrid");
        },

        /**
         * 获取选中行的id数组
         */
        getSelectedRowIds: function () {
            return this.jqGridInstance.jqGrid('getGridParam','selarrrow');
        },

        getRowById: function (rowId) {
            return this.jqGridInstance.jqGrid('getRowData',rowId);
        },
        getTotalFee:function(){
          return totalFee;
        }
    };

    window.JqGrid = JqGrid;

}());