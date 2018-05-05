/*
	扩展easyui中的控件方法或修改其默认属性
	@eric
 */
 
//翻页工具栏文字
if ($.fn.pagination){
	$.fn.pagination.defaults.showPageList=false;
	$.fn.pagination.defaults.beforePageText="第",
	$.fn.pagination.defaults.afterPageText = "页  共 {pages} 页";
	$.fn.pagination.defaults.displayMsg ="当前 {from}- {to} 条 共 {total} 条记录";
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.pageSize = 15;
	$.fn.datagrid.defaults.loadMsg = '正在加载...';
	$.fn.datagrid.defaults.striped = true;
	$.fn.datagrid.defaults.singleSelect= true;
	$.fn.datagrid.defaults.nowrap = true;
	$.fn.datagrid.defaults.fitColumns = false;
	$.fn.datagrid.defaults.remoteSort = true;
	$.fn.datagrid.defaults.rownumbers = true;
	$.fn.datagrid.defaults.pagination = true;
}

//window窗体默认属性
$.fn.window.defaults.resizable=false;
$.fn.window.defaults.collapsible=false;
$.fn.window.defaults.minimizable=false;
$.fn.window.defaults.maximizable=false;
$.fn.window.defaults.shadow=false;
$.fn.window.defaults.modal=true;
$.fn.window.defaults.loadingMessage = '正在加载...';

//信息框按钮文字
if ($.messager){
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
}

/*$.Ajax = function(url, options){
    if(typeof options == 'undefined'){
        options = url;
    }
    if(typeof url == 'string'){
        options.url = url;
    }

    if(options.error == undefined){
        options.error = ajaxErrorCallback;
    }
    $.ajax(options);
}

function ajaxErrorCallback(xhr, status, error){
    var msg;
    var callback = function(){};
    switch (xhr.status) {
        case 400 :
            msg = '服务异常';
            break;
        case 401 :
            msg = '身份认证异常';
            callback = function() {
                window.location.reload();
            };
            break;
        case 403 :
            msg = "权限受限";
            break;
        case 404 :
            msg = "资源不存在";
            break;
        case 500 :
            msg = "运行异常";
            break;
        default :
            msg = "未知服务异常";
    }
    if (xhr.responseJSON && xhr.responseJSON.message && xhr.responseJSON.message != "") {
        msg = xhr.responseJSON.message;
    }
    alert(msg, callback);
}*/


$.extend($.fn.linkbutton.methods, {
    enable: function(jq){
        return jq.each(function(){
            var state = $.data(this, 'linkbutton');
            if ($(this).hasClass('l-btn-disabled')) {
                var itemData = state._eventsStore;
                //恢复超链接
                if (itemData.href) {
                    $(this).attr("href", itemData.href);
                }
                //回复点击事件
                if (itemData.onclicks) {
                    for (var j = 0; j < itemData.onclicks.length; j++) {
                        $(this).bind('click', itemData.onclicks[j]);
                    }
                }
                //设置target为null，清空存储的事件处理程序
                itemData.target = null;
                itemData.onclicks = [];
                $(this).removeClass('l-btn-disabled');
            }
        });
    },
    disable: function(jq){
        return jq.each(function(){
            var state = $.data(this, 'linkbutton');
            if (!state._eventsStore)
                state._eventsStore = {};
            if (!$(this).hasClass('l-btn-disabled')) {
                var eventsStore = {};
                eventsStore.target = this;
                eventsStore.onclicks = [];
                //处理超链接
                var strHref = $(this).attr("href");
                if (strHref) {
                    eventsStore.href = strHref;
                    $(this).attr("href", "javascript:void(0)");
                }
                //处理直接耦合绑定到onclick属性上的事件
                var onclickStr = $(this).attr("onclick");
                if (onclickStr && onclickStr != "") {
                    eventsStore.onclicks[eventsStore.onclicks.length] = new Function(onclickStr);
                    $(this).attr("onclick", "");
                }
                //处理使用jquery绑定的事件
                var eventDatas = $(this).data("events") || $._data(this, 'events');
                if (eventDatas["click"]) {
                    var eventData = eventDatas["click"];
                    for (var i = 0; i < eventData.length; i++) {
                        if (eventData[i].namespace != "menu") {
                            eventsStore.onclicks[eventsStore.onclicks.length] = eventData[i]["handler"];
                            $(this).unbind('click', eventData[i]["handler"]);
                            i--;
                        }
                    }
                }
                state._eventsStore = eventsStore;
                $(this).addClass('l-btn-disabled');
            }
        });
    }
});

// datagrid 合并 Cell
$.extend($.fn.datagrid.methods, {
    autoMergeCells : function (jq, fields) {
        return jq.each(function () {
            var target = $(this);
            if (!fields) {
                fields = target.datagrid("getColumnFields");
            }
            var rows = target.datagrid("getRows");
            var i = 0,
            j = 0,
            temp = {};
            
            for (i; i < rows.length; i++) {
                var row = rows[i];
                j = 0;
                for (j; j < fields.length; j++) {
                    var field = fields[j];
                    var tf = temp[field];
                    if (!tf) {
                        tf = temp[field] = {};
                        tf[row[field]] = [i];
                    } else {
                        var tfv = tf[row[field]];
                        if (tfv) {
                            tfv.push(i);
                        } else {
                            tfv = tf[row[field]] = [i];
                        }
                    }
                }
            }
            $.each(temp, function (field, colunm) {
                $.each(colunm, function () {
                    var group = this;
                    
                    if (group.length > 1) {
                        var before,
                        after,
                        megerIndex = group[0];
                        for (var i = 0; i < group.length; i++) {
                            before = group[i];
                            after = group[i + 1];
                            if (after && (after - before) == 1) {
                                continue;
                            }
                            var rowspan = before - megerIndex + 1;
                            if (rowspan > 1) {
                                target.datagrid('mergeCells', {
                                    index : megerIndex,
                                    field : field,
                                    rowspan : rowspan
                                });
                            }
                            if (after && (after - before) != 1) {
                                megerIndex = after;
                            }
                        }
                    }
                });
            });
        });
    },
    autoMergeCellAndCells : function (jq, fields) {
        return jq.each(function () {
            var target = $(this);
            if (!fields) {
                fields = target.datagrid("getColumnFields");
            }
            var cfield = fields[0];
            if (!cfield) {
                return;
            }
            var rows = target.datagrid("getRows");
            var i = 0,
            j = 0,
            temp = {};
            for (i; i < rows.length; i++) {
                var row = rows[i];
                j = 0;
                var tf = temp[cfield];
                if (!tf) {
                    tf = temp[cfield] = {};
                    tf[row[cfield]] = [i];
                
                } else {
                    var tfv = tf[row[cfield]];
                    if (tfv) {
                        tfv.push(i);
                    } else {
                        tfv = tf[row[cfield]] = [i];
                        
                    }
                }
            }
            
            $.each(temp, function (field, colunm) {
                $.each(colunm, function () {
                    var group = this;
                    
                    if (group.length > 1) {
                        var before,
                        after,
                        megerIndex = group[0];
                        for (var i = 0; i < group.length; i++) {
                            before = group[i];
                            after = group[i + 1];
                            if (after && (after - before) == 1) {
                                continue;
                            }
                            var rowspan = before - megerIndex + 1;
                            if (rowspan > 1) {
                                for(var j=0;j<fields.length;j++){
                                    target.datagrid('mergeCells', {
                                        index : megerIndex,
                                        field : fields[j],
                                        rowspan : rowspan
                                   });
                                }
                            }
                            if (after && (after - before) != 1) {
                                megerIndex = after;
                            }
                        }
                    }
                });
            });
        });
    }
    
});


/**
 * combobox和combotree模糊查询
 */
(function(){
    //combobox可编辑，自定义模糊查询
    $.fn.combobox.defaults.editable = true;
    $.fn.combobox.defaults.filter = function(q, row){
        var opts = $(this).combobox('options');
        return row[opts.textField].indexOf(q) >= 0;
    };
    //combotree可编辑，自定义模糊查询
    $.fn.combotree.defaults.editable = true;
    $.extend($.fn.combotree.defaults.keyHandler,{
        up:function(){
        },
        down:function(){
        },
        enter:function(){
        },
        query:function(q){
            var t = $(this).combotree('tree');
            var nodes = t.tree('getChildren');
            for(var i=0; i<nodes.length; i++){
                var node = nodes[i];
                if (node.text.indexOf(q) >= 0){
                    $(node.target).show();
                } else {
                    $(node.target).hide();
                }
            }
            var opts = $(this).combotree('options');
            if (!opts.hasSetEvents){
                opts.hasSetEvents = true;
                var onShowPanel = opts.onShowPanel;
                opts.onShowPanel = function(){
                    var nodes = t.tree('getChildren');
                    for(var i=0; i<nodes.length; i++){
                        $(nodes[i].target).show();
                    }
                    onShowPanel.call(this);
                };
                $(this).combo('options').onShowPanel = opts.onShowPanel;
            }
        }
    });
})(jQuery);
