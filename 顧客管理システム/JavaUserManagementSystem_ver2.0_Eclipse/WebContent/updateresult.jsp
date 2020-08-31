<%@page import="jums.JumsHelper"
		import="javax.servlet.http.HttpSession"
		import="jums.UserDataBeans"
		import="java.util.ArrayList"%>
<%
    JumsHelper jh = JumsHelper.getInstance();
	UserDataBeans udb = (UserDataBeans)request.getAttribute("udb");
	ArrayList<String> chkList = udb.chkproperties();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JUMS更新結果画面</title>
    </head>
    <body>

     <% if(chkList.size()==0){ %>
        <h1>変更結果</h1><br>
        名前:<%= udb.getName()%><br>
        生年月日:<%= udb.getYear() + "年" + udb.getMonth() + "月" + udb.getDay() + "日"%><br>
        種別:<%= jh.exTypenum(udb.getType())%><br>
        電話番号:<%= udb.getTel()%><br>
        自己紹介:<%= udb.getComment()%><br>
        以上の内容で登録しました。<br>

    <% }else{ %>
        <h1>入力が不完全です</h1>
        <%=jh.chkinput(chkList) %>
    <% } %>
        <form action="update" method="POST">
            <input type="submit" name="no" value="戻る">
            <input type="hidden" name="mode" value="REINPUT">
        </form>
    </body>
    <%=jh.home()%>
    </body>
</html>
