<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.activity.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>活動詳情</title>
</head>
<body>

    <h2>查詢結果</h2>
    <%
        // 從 Request 裡面取出 Servlet 放進去的 ActivityVo 物件
        ActivityVO activityVo = (ActivityVO) request.getAttribute("activityVo");
    %>
    
    <% if (activityVo != null) { %>
        <table border="1">
            <tr>
                <th>活動編號</th>
                <td><%= activityVo.getActivityId() %></td>
            </tr>
            <tr>
                <th>活動標題</th>
                <td><%= activityVo.getActivityTitle() %></td>
            </tr>
            <tr>
                <th>活動描述</th>
                <td><%= activityVo.getActivityDescription() %></td>
            </tr>
            <tr>
                <th>活動價格</th>
                <td>$<%= activityVo.getActivityPrice() %></td>
            </tr>
        </table>
    <% } else { %>
        <p style="color:red;">找不到該活動資料！</p>
    <% } %>
    
    <br>
    <a href="select_page.jsp">回到首頁</a>

</body>
</html>