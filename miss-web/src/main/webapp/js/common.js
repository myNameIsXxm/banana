 
var $parent = self.parent.$;

$(function(){
	//closeCondition();
	$('#openOrClose').on("click",function(){
		ToggleCondition();
	});	
	
	function ToggleCondition(){
		$('#conditon').toggle(80);
		setTimeout(domresize,100);
	}
	function closeCondition(){
		$('#conditon').toggle(false);
	}
	
	function openCondition(){
		$('#conditon').toggle(true);
		
	}
})