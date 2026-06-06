<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.activity.model.*" %>
<%
    // 直接呼叫 Service 撈取全部資料
    ActivityService actSvc = new ActivityService();
    List<ActivityVO> list = actSvc.getAll();
    pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有活動列表</title>
<style>
    table { width: 100%; border-collapse: collapse; margin-top: 20px;}
    th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
    th { background-color: #f4f4f4; }
</style>
</head>
<body>

    <h2>所有揪團活動列表</h2>
    <a href="addActivity.jsp">👉 點我新增活動</a> | <a href="select_page.jsp">🏠 回首頁</a>
    
    <table>
        <tr>
            <th>活動編號</th>
            <th>標題</th>
            <th>價格</th>
            <th>狀態</th>
            <th>修改</th>
            <th>刪除</th>
        </tr>
        
        <% for(ActivityVO vo : list) { %>
        <tr>
            <td><%= vo.getActivityId() %></td>
            <td><%= vo.getActivityTitle() %></td>
            <td>$<%= vo.getActivityPrice() %></td>
            <td><%= vo.getActivityStatus() == 0 ? "正常" : (vo.getActivityStatus() == 1 ? "延期" : "取消") %></td>
            
            <td>
                <form method="post" action="<%=request.getContextPath()%>/activity/activity.do">
                    <input type="submit" value="修改">
                    <input type="hidden" name="activityId" value="<%= vo.getActivityId() %>">
                    <input type="hidden" name="action" value="getOne_For_Update">
                </form>
            </td>
            
            <td>
                <form method="post" action="<%=request.getContextPath()%>/activity/activity.do">
                    <input type="submit" value="刪除" onclick="return confirm('確定要刪除嗎？');">
                    <input type="hidden" name="activityId" value="<%= vo.getActivityId() %>">
                    <input type="hidden" name="action" value="delete">
                </form>
            </td>
        </tr>
        <% } %>
    </table>

</body>
</html>