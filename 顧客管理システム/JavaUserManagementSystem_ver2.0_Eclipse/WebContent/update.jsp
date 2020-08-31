<%@page import="jums.JumsHelper"
		import="javax.servlet.http.HttpSession"
		import="jums.UserDataBeans"
		import="jums.UserDataDTO"%>
<%
    JumsHelper jh = JumsHelper.getInstance();
    HttpSession Session = request.getSession();
    UserDataDTO udd = (UserDataDTO)session.getAttribute("resultData");
    UserDataBeans udb = (UserDataBeans)session.getAttribute("udb");
    int id = (int)session.getAttribute("ID");


    %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JUMS変更画面</title>
    </head>
    <body>
    <%
    int a = 0;
    int b = 0;
    int c = 0;
    int d = 0;

    if (udb==null) {
    	a = 0;
    	b = 0;
    	c = 0;
    	d = 0;
    }else{
    	a = udb.getYear();
    	b = udb.getMonth();
    	c = udb.getDay();
    	d = udb.getType();
    }
    %>

    <form action="UpdateResult" method="POST">
        名前:
        <input type="text" name="name" value="<%= udb.getName()%>">
        <br><br>

        生年月日:
        <select name="year">
            <option value="<%=a%>"><%if (a!=0) {
            	out.print(a);
            }else{
            	out.print("--");
            }%></option>
            <% for(int i=1950; i<=2010; i++){ %>
            <option value="<%=i%>" ><%=i%></option>
            <% } %>
        </select>年

        <select name="month">
            <option value="<%=b%>"><%if (b!=0) {
            	out.print(b);
            }else{
            	out.print("--");
            }%></option>
            <% for(int i = 1; i<=12; i++){ %>
            <option value="<%=i%>" ><%=i%></option>
            <% } %>
        </select>月

        <select name="day">
            <option value="<%=c%>"><%if (c!=0) {
            	out.print(c);
            }else{
            	out.print("--");
            }%></option>
            <% for(int i = 1; i<=31; i++){ %>
            <option value="<%=i%>"><%=i%></option>
            <% } %>
        </select>日
        <br><br>

        種別:
        <br>
            <% for(int i = 1; i<=3; i++){ %>
            <input type="radio" name="type" value="<%=i%>" <% if(d==i) {out.print("checked");} %>><%=jh.exTypenum(i)%><br>
            <% } %>
        <br>

        電話番号:
        <input type="text" name="tel" value="<%= udb.getTel()%>">
        <br><br>

        自己紹介文
        <br>
        <textarea name="comment" rows=10 cols=50 style="resize:none" wrap="hard"><%= udb.getComment()%></textarea><br><br>

        <input type="submit" name="btnSubmit" value="確認画面へ">
    </form>
        <br>
        <form action="ResultDetail?id=<%=id%>" method="POST">
        	<input type="hidden" name="name" value="<%=udd.getName()%>">
        	<input type="hidden" name="birthday" value="<%=udd.getBirthday()%>">
        	<input type="hidden" name="type" value="<%=jh.exTypenum(udd.getType())%>">
        	<input type="hidden" name="tel" value="<%=udd.getTel()%>">
        	<input type="hidden" name="comment" value="<%=udd.getComment()%>">

        	<input type="submit" name="return" value="詳細画面に戻る">
        </form>
        <br>
        <%=jh.home()%>
    </body>
</html>
