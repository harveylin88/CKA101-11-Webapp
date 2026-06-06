<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.activity.model.*" %>
<%
    // 取出 Servlet 準備好的資料
    ActivityVO vo = (ActivityVO) request.getAttribute("activityVo");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改活動</title>
<style>
    table { margin-top: 20px; }
    td { padding: 5px; }
    select, input[type="text"], input[type="number"] { padding: 5px; width: 200px; }
    .readonly-text { color: gray; }
</style>
</head>
<body>
    <h2>修改活動資料</h2>
    <form method="post" action="<%=request.getContextPath()%>/activity/activity.do">
        <table>
            <tr>
                <td>活動編號:</td>
                <td class="readonly-text"><%= vo.getActivityId() %> (不可修改)</td>
            </tr>
            
            <tr>
                <td>活動類型:</td>
                <td>
                    <select name="activityTypeId">
                        <option value="1" <%= vo.getActivityTypeId() == 1 ? "selected" : "" %>>球類運動 (羽球/籃球等)</option>
                        <option value="2" <%= vo.getActivityTypeId() == 2 ? "selected" : "" %>>桌遊益智</option>
                        <option value="3" <%= vo.getActivityTypeId() == 3 ? "selected" : "" %>>逛街購物</option>
                        <option value="4" <%= vo.getActivityTypeId() == 4 ? "selected" : "" %>>戶外踏青</option>
                        <option value="5" <%= vo.getActivityTypeId() == 5 ? "selected" : "" %>>探店美食</option>
                        <option value="6" <%= vo.getActivityTypeId() == 6 ? "selected" : "" %>>看電影/追劇</option>
                        <option value="7" <%= vo.getActivityTypeId() == 7 ? "selected" : "" %>>唱歌KTV</option>
                        <option value="8" <%= vo.getActivityTypeId() == 8 ? "selected" : "" %>>健身重訓</option>
                        <option value="9" <%= vo.getActivityTypeId() == 9 ? "selected" : "" %>>密室逃脫</option>
                        <option value="10" <%= vo.getActivityTypeId() == 10 ? "selected" : "" %>>藝文展覽</option>
                        <option value="11" <%= vo.getActivityTypeId() == 11 ? "selected" : "" %>>遊戲電競</option>
                        <option value="12" <%= vo.getActivityTypeId() == 12 ? "selected" : "" %>>其他</option>
                    </select>
                </td>
            </tr>
            
            <tr>
                <td>主辦會員ID:</td>
                <td><input type="number" name="memberId" value="<%= vo.getMemberId() %>" required></td>
            </tr>
            
            <tr><td>活動標題:</td><td><input type="text" name="activityTitle" value="<%= vo.getActivityTitle() %>"></td></tr>
            <tr><td>活動描述:</td><td><textarea name="activityDescription" style="width: 200px; height: 60px;"><%= vo.getActivityDescription() %></textarea></td></tr>
            <tr><td>價格:</td><td><input type="number" name="activityPrice" value="<%= vo.getActivityPrice() %>" min="0"></td></tr>
            <tr><td>最低人數:</td><td><input type="number" name="minParticipants" value="<%= vo.getMinParticipants() %>" min="1"></td></tr>
            <tr><td>最高人數:</td><td><input type="number" name="maxParticipants" value="<%= vo.getMaxParticipants() %>" min="1"></td></tr>
            <tr><td>已參加人數:</td><td><input type="number" name="attendeesCount" value="<%= vo.getAttendeesCount() %>" min="0"></td></tr>
            
            <tr>
                <td>活動狀態:</td>
                <td>
                    <select name="activityStatus">
                        <option value="0" <%= vo.getActivityStatus() == 0 ? "selected" : "" %>>正常</option>
                        <option value="1" <%= vo.getActivityStatus() == 1 ? "selected" : "" %>>延期</option>
                        <option value="2" <%= vo.getActivityStatus() == 2 ? "selected" : "" %>>取消</option>
                    </select>
                </td>
            </tr>
            
            <tr><td>報名開始:</td><td><input type="text" name="registrationStartTime" value="<%= vo.getRegistrationStartTime() %>"></td></tr>
            <tr><td>報名結束:</td><td><input type="text" name="registrationDeadline" value="<%= vo.getRegistrationDeadline() %>"></td></tr>
            <tr><td>活動結束:</td><td><input type="text" name="endTime" value="<%= vo.getEndTime() %>"></td></tr>
        </table>
        <br>
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="activityId" value="<%= vo.getActivityId() %>">
        <input type="submit" value="💾 送出修改" style="padding: 5px 15px; cursor: pointer;">
    </form>
    <br>
    <a href="listAllActivity.jsp">🔙 取消並回列表</a>
</body>
</html>