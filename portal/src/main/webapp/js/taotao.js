var _ticket = "";
var TT = TAOTAO = {
	checkLogin : function(){
		_ticket = $.cookie("TT_TOKEN");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://goshopping.com:8085/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到逛啊！<a href=\"javascript:void(0);\" onclick=\"logout123()\" class=\"link-logout\">[退出]</a href=\"#\">";
					$("#loginbar").html(html);
				}
			}
		});
	}
};
//退出登陆
// \"http://goshopping.com:8085/user/logout/"+_ticket+"\""+" class=\"link-logout\"
//"http://goshopping.com:8085/user/logout/"+_ticket,
function logout123(){
	$.ajax({
		url : "http://goshopping.com:8085/user/logout/"+_ticket,
		dataType : "jsonp",
		type : "GET",
		success : function(data){
			if(data.status == 200){
				alert("您已经安全退出！");
				window.location.reload();
			}
		}
	});
}
$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});