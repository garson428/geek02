<%@page import="jums.JumsHelper"
        import="jums.UserDataDTO"
        import="java.util.List" %>
<%
    JumsHelper jh = JumsHelper.getInstance();
	//UserDataDTOからList<UserDataDTO>に変更、リクエストからセッションに変更
    List<UserDataDTO> dtoList = (List<UserDataDTO>)session.getAttribute("resultData");
	boolean reinput = false;
	if (request.getParameter("mode") != null && request.getParameter("mode").equals("REINPUT")) {
		reinput = true;
		dtoList = (List<UserDataDTO>)session.getAttribute("resultData");
	}
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JUMS検索結果画面</title>
    </head>
    <body>
        <h1>検索結果</h1>
        <table border=1>
            <tr>
                <th>名前</th>
                <th>生年</th>
                <th>種別</th>
                <th>登録日時</th>
            </tr>
            <% for(UserDataDTO dto : dtoList) { %><tr>
                <td><a href="ResultDetail?id=<%= dto.getUserID()%>"><%= dto.getName()%></a></td>
                <td><%= dto.getBirthday()%></td>
                <td><%= dto.getType()%></td>
                <td><%= dto.getNewDate()%></td>
            </tr><% } %>
        </table>
    </body>
    <%=jh.home()%>
</html>
