

/*
* 函数说明：取cookie值
* 参数：	cookie字段名
* 返回值：	cookie值
* 时间：2006-5-12
*/
function getCookie(sName) {
	var aCookie = document.cookie.split("; ");
	for (var i=0; i < aCookie.length; i++){
		var aCrumb = aCookie[i].split("=");
		if (sName == aCrumb[0]){
			return unescape(aCrumb[1]);
		}
	}
	return null;
}


function ShowMain(FileName_Left, FileName_Right) {
    var temp;
    if (FileName_Left != "") {
        var checkLeftUrl = CheckCurrentLeftUrl(FileName_Left);
        if (checkLeftUrl == "false") {
            temp = document.getElementById("left");
            temp.src = FileName_Left + GetUrlParm(FileName_Left);
            temp.contentWindow.window.name = "left";
            frames["left"] = temp.contentWindow.window;
        }
    }
    if (FileName_Right != "") {
        temp = document.getElementById("main_right");
        temp.src = FileName_Right + GetUrlParm(FileName_Right);
        temp.contentWindow.window.name = "main_right";
        frames["main_right"] = temp.contentWindow.window;
    }
    InitSideBarState();
}

function CheckCurrentLeftUrl(leftUrl) {
    var src = document.getElementById("left").src;
    var regex = /\s*[\?&]{1,1}t=[0-9]{1,}$/;
    var currentLeftUrl = src.replace(regex, '');
    if (currentLeftUrl.lastIndexOf(leftUrl) >= 0) {
        if (currentLeftUrl.lastIndexOf(leftUrl) == (currentLeftUrl.length - leftUrl.length)) {
            return "true";
        }
    }
    return "false";
}

function GetUrlParm(url) {
    var urlparm = "?";
    if (url.indexOf('?') >= 0) {
        urlparm = "&";
    }
    urlparm = urlparm + "t=" + GetRandomNum();
    return urlparm;
}

function GetRandomNum() {
    var Range = 1000;
    var Rand = Math.random();
    return (Math.round(Rand * Range));
}

function switchSysBar() {
	debugger;
    var obj = document.getElementById("switchPoint");
    if (obj.alt == "关闭左栏") {
        obj.alt = "打开左栏";
        obj.src = "images/butOpen.jpg";
        jQuery("#frmTitle").hide();
        var width, height;
        width = jQuery(window).width() - 7;
        height = jQuery(window).height() - 143; 
        jQuery("#main_right").css({"width":width},{"height":height}); 
        jQuery("#FrameTabs").css("width",width); 
       // if (CheckFramesScroll) {
//             CheckFramesScroll();
//         }
    }
    else {
        obj.alt = "关闭左栏";
        obj.src = "images/butClose.jpg";
        jQuery("#frmTitle").show();
        onload();
    }
    //CreateSideBarCookie();
}

