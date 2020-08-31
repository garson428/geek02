<%@page import="jums.JumsHelper"
        import="jums.UserDataDTO"
        import="java.util.List"
        import="jums.UserDataBeans" %>
<%
    JumsHelper jh = JumsHelper.getInstance();
    HttpSession Session = request.getSession();
    UserDataDTO udd = (UserDataDTO)session.getAttribute("resultData");
    UserDataBeans udb = (UserDataBeans)session.getAttribute("udb");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JUMSユーザー情報詳細画面</title>
    </head>
    <body>
        <h1>詳細情報</h1>
        名前:<%= udd.getName()%><br>
        生年月日:<%= udd.getBirthday()%><br>
        種別:<%= jh.exTypenum(udd.getType())%><br>
        電話番号:<%= udd.getTel()%><br>
        自己紹介:<%= udd.getComment()%><br>
        登録日時:<%= udd.getNewDate()%><br>
        <form action="Update" method="POST">
        <input type="submit" name="update" value="変更"style="width:100px">
        </form>
        <form action="Delete" method="POST">
        <input type="submit" name="delete" value="削除"style="width:100px">
        </form>

        <!-- 検索画面へ戻る -->
        <form action="SearchResult" method="POST">
        <input type="hidden" name="name" value="<%=udb.getName()%>">
        <input type="hidden" name="year" value="<%=udb.getYear()%>">
        <input type="hidden" name="type" value="<%=udb.getType()%>">
        <input type="submit" name="return" value="検索結果画面へ戻る" style="width:100px">
        </form>
    </body>
</html>
