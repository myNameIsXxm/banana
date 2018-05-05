$(document).ready(function(){
	$("#s_sldh").textbox("textbox").keyup(function(e){
		if(e.keyCode==13){
			doSearch();
		}
	});
	$("#s_fybh").textbox("textbox").keyup(function(e){
		if(e.keyCode==13){
			doSearch();
		}
	});
});
var $grid;
function doSearch() {
	var sldh = $("#s_sldh").textbox("getValue");
	var fybh = $("#s_fybh").textbox("getValue");
	if(sldh==""){
		alert("请填写 受理单号！");
		return;
	}
	if(sldh==""){
		sldh = "0";
	}
	if(fybh==""){
		fybh = "0";
	}
	$.ajax({
		url:rootPath+"/"+entity+"/context",
		type : "post",
		data : {
			sldh : sldh,
			fybh : fybh,
			dhcf : $("#s_dhcf").val()
		},
		beforeSend : function() {
			showProgress();
		},
		complete : function() {
			closeProgress();
		},
		success : function(da) {
			if(da.hasOwnProperty("errMsg")){
				closeProgress();
				alert(da.errMsg);
				return false;
			}
			if(da.error=="no"){
				if(da.nodata=="true"){
					$(".mytable td[id]").html("");
				}else{
					$(".mytable td[id]").each(function(i,v){
						$("#"+$(this).attr("id")).html(da[$(this).attr("id")]);
					});
				}
				$("#sessionid").val(da.sessionid);
				$("body:last").append("<input type='hidden' name='mydatagridformatkey' value='number2' key='JSJE'/>");
				$("body:last").append("<input type='hidden' name='mydatagridformatkey' value='percent' key='SL'/>");
				$("body:last").append("<input type='hidden' name='mydatagridformatkey' value='number2' key='YNSE'/>");
				$("body:last").append("<input type='hidden' name='mydatagridformatkey' value='number2' key='JMSE'/>");
				$("body:last").append("<input type='hidden' name='mydatagridformatkey' value='number2' key='SE'/>");
				$("body:last").append("<input type='hidden' name='mydatagridformatkey' value='number2' key='CQBCDMSYE'/>");
				$("body:last").append("<input type='hidden' name='mydatagridformatkey' value='number2' key='SJJE'/>");
				
				$grid = new myDataGrid({
					rownum:true
				},{
					show:"false"
				},"hottable",sp_tab);
				$grid.init();
				$grid.load(da.tabledata);
			}else{
				alert(da.error);
			}
		}
	});
}

function saveWspz(index){
	var dhcf=0;
	if(index==3){
		var bj = $("#s_ysfbj").val();
		if(bj!=""){
			index = bj;
		}
		var cf = $("#s_dhcf").val();
		if(cf!=""){
			dhcf = cf;
		}
	}
	var url = rootPath+"/save/wspz";
	$.ajax({
		url:url,
		type : "post",
		data : {
			sessionid:$("#sessionid").val(),
			ysfbj:index,
			dhcf:dhcf
			
		},
		beforeSend : function() {
			showProgress();
		},
		complete : function() {
			closeProgress();
		},
		success:function(da){
			if(da.hasOwnProperty("errMsg")){
				closeProgress();
				alert(da.errMsg);
				return false;
			}
			alert(da);
		}
	});
}