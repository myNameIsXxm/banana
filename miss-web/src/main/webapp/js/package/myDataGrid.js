
function number2(da){
	return parseFloat(da).toFixed(2);
}

function number3(da){
	return parseFloat(da).toFixed(3);
}

function percent(da){
	if((da+'').indexOf('%')!=-1){
		return da;
	}
	var re = parseFloat(da)*1000/10+"%";
	if(re.charAt(0)=='.'){
		return "0"+re;
	}else{
		return re;
	}
}



////////////////////////////////////////////////////////////////////////

function packageHead(data){
	var result = [];
	$.each(data,function(i,v){
		result[i]=packageOne(v);
	});
	return result;
}
function mydatagrid_format(data){
	var arr = data.rows;
	if(typeof arr == "undefined"){
		arr = data;
	}
	if(typeof arr == "undefined"){
		return data;
	}
	$("[name='mydatagridformatkey']").each(function(i,v){
		if($(v).val()!='normal'){
			if($(v).val()=='number2'){
				for(var i=0;i<arr.length;i++){
					var obj = arr[i];
					obj[$(v).attr("key")] = number2(obj[$(v).attr("key")]);
				}
			}else if($(v).val()=='number3'){
				for(var i=0;i<arr.length;i++){
					var obj = arr[i];
					obj[$(v).attr("key")] = number3(obj[$(v).attr("key")]);
				}
			}else if($(v).val()=='percent'){
				for(var i=0;i<arr.length;i++){
					var obj = arr[i];
					obj[$(v).attr("key")] = percent(obj[$(v).attr("key")]);
				}
			}
		}
	});
	return data;
}
function packageOne(data){
	var thead = [];
	$.each(data,function(i,v){
		$("body:last").append("<input type='hidden' name='mydatagridformatkey' value='"+v.format+"' key='"+v.colname+"'/>");
		thead[i] = {
			field : v.colname,
			title : v.name,
			halign : "center",
			align : v.align,
			width : v.width+'%',
			colspan:v.colspan,
			rowspan:v.rowspan,
			checkbox:v.ischeck
		};	
	});
	return thead;
}
function mydatagrid_flush(key,num,size,table_id,hecs){
	var da;
	if(typeof hecs==="undefined"){
		var param_id = $("#h_hecs_"+table_id).val();
		da = window[param_id]();
	}else{
		da = window[hecs]();
	}
	$.ajax({
		url : rootPath+"/body/"+key+"/"+num+"/"+size+"/$",
		type : "post",
		data:da,
		beforeSend : function() {
			showProgress();
		},
		complete : function() {
			closeProgress();
		},
		error: function(data){
		},
		success : function(data) {
			if(data.hasOwnProperty("errMsg")){
				closeProgress();
				alert(data.errMsg);
				return false;
			}
			$("#"+table_id).datagrid("unselectAll");
			$("#"+table_id).datagrid("uncheckAll");
			$("#"+table_id).datagrid("loadData",data);	
			$("#"+table_id).datagrid("getPager").pagination("refresh",{pageNumber:num,pageSize:size});
		}
	});
};

var myDataGrid = function(inputData,inputPage,table_id,head){
	var data = {
		height:$("#"+table_id).parent().height(),
		toolbarId:"",
		hide:[],
		rownum:false,
		dbclick:function(i,v){
		},
		menu_head:function(i,v){
		},
		dbclickcell : function(index, field, value) {
		},
		click:function(i,v){
		},
		success:function(i){}
	};
	var page = {
		size:20,
		show:"true",
		click:function(i,v){
		}	
	};
	$.extend(data,inputData);
	$.extend(page,inputPage);
	var config = {
		height : data.height,
		checkOnSelect:false,
		selectOnCheck:false,
		fit : true,
		rownumbers:data.rownum,
		columns : [],
		onDblClickRow : function(rowIndex, rowData){
			data.dbclick(rowIndex,rowData);
		},
		onDblClickCell : function(index, field, value) {
			data.dbclickcell(index, field, value);
		},
		onClickRow : function(rowIndex, rowData){
			data.click(rowIndex,rowData);
		},
		onHeaderContextMenu: function(e,field) {
            data.menu_head(e,field);
        },
		onLoadSuccess : function(re) {
			$('#'+table_id).datagrid('unselectAll');
			if (re.total == 0) {
				var tbody = $(this).data().datagrid.dc.body2.find('table tbody');
				$td = $('<td name="nodatatd">没有找到数据</td>');
				$td.css("width",tbody.width()).css("height","25px").css("text-align","center");
				$td.attr("colspan",$("#"+table_id).datagrid("getColumnFields").length);
				$tr = $('<tr></tr>');
				$tr.append($td);
				tbody.append($tr);
			}
			data.success();
		}
	}
	if(data.toolbarId!=""){
		config.toolbar="#"+data.toolbarId;
	}
	if(page.show=="false"){
		config.pagination=false;
	}
	this.init = function(){
		config.columns=head;
		$("#"+table_id).datagrid(config);
		if(page.show=="true"){
			$("#"+table_id).datagrid('getPager').pagination({
				pageSize : page.size,
				showRefresh : false,
				onSelectPage : function(num,size) {
					page.click(num,size);
				}
			});
		}
		if(data.hide.length>0){
			$.each(data.hide,function(i,v){
				$("#"+table_id).datagrid("hideColumn", v);
			});
		}
	}
	this.load = function(rdata){
		$("#"+table_id).datagrid("loadData",mydatagrid_format(rdata));
	}
	this.initData = function(key,hecs,headcs){
		if($("#h_hecs_"+table_id).length>0){
			$("#h_hecs_"+table_id).val(hecs);
		}else{
			$("body:last").append("<input type='hidden' id='h_hecs_"+table_id+"' value='"+hecs+"'/>");
		}
		$.ajax({
			url : rootPath+"/body/"+key+"/1/"+page.size+"/$",
			data:window[hecs](),
			type : "post",
			beforeSend : function() {
				showProgress();
			},
			complete : function() {
				closeProgress();
			},
			error: function(data){
			},
			success : function(data2) {
				if(data2.hasOwnProperty("errMsg")){
					closeProgress();
					alert(data2.errMsg);
					return false;
				}
				if(typeof(headcs)=="undefined" || headcs==null){
					$.ajax({
						url : rootPath+"/head/"+key+"/$",
						type : "post",
						sync:false,
						success : function(data4) {
							if(data4.hasOwnProperty("errMsg")){
								closeProgress();
								alert(data4.errMsg);
								return false;
							}
							config.columns=packageHead(data4);
							$("#"+table_id).datagrid(config);
							$("#"+table_id).datagrid('getPager').pagination({
								pageSize : page.size,
								showRefresh : false,
								onSelectPage : function(num,size) {
									mydatagrid_flush(key,num,size,table_id);
								}
							});
							if(data.hide.length>0){
								$.each(data.hide,function(i,v){
									$("#"+table_id).datagrid("hideColumn", v);
								});
							}
							$("#"+table_id).datagrid("loadData",mydatagrid_format(data2));
						}
					});
				}else{
					$.ajax({
						url : rootPath+"/head/"+key+"/$",
						data:window[headcs](),
						type : "post",
						sync:false,
						success : function(data4) {
							if(data4.hasOwnProperty("errMsg")){
								closeProgress();
								alert(data4.errMsg);
								return false;
							}
							config.columns=packageHead(data4);
							$("#"+table_id).datagrid(config);
							$("#"+table_id).datagrid('getPager').pagination({
								pageSize : page.size,
								showRefresh : false,
								onSelectPage : function(num,size) {
									mydatagrid_flush(key,num,size,table_id);
								}
							});
							if(data.hide.length>0){
								$.each(data.hide,function(i,v){
									$("#"+table_id).datagrid("hideColumn", v);
								});
							}
							$("#"+table_id).datagrid("loadData",mydatagrid_format(data2));
						}
					});
				}
			}
		});
	}
	this.flush=function(key,hecs){
		this.initData(key,hecs);
	}
};