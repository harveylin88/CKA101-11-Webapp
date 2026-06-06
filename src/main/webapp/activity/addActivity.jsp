<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增活動</title>
<style>
    table { margin-top: 20px; }
    td { padding: 5px; }
    select, input[type="text"], input[type="number"], input[type="datetime-local"] { padding: 5px; width: 200px; }
</style>
</head>
<body>
    <h2>新增揪團活動</h2>
    <form method="post" action="<%=request.getContextPath()%>/activity/activity.do">
        <table>
            <tr>
                <td>活動類型:</td>
                <td>
                    <select name="activityTypeId">
                        <option value="1">球類運動 (羽球/籃球等)</option>
                        <option value="2">桌遊益智</option>
                        <option value="3">逛街購物</option>
                        <option value="4">戶外踏青</option>
                        <option value="5">探店美食</option>
                        <option value="6">看電影/追劇</option>
                        <option value="7">唱歌KTV</option>
                        <option value="8">健身重訓</option>
                        <option value="9">密室逃脫</option>
                        <option value="10">藝文展覽</option>
                        <option value="11">遊戲電競</option>
                        <option value="12">其他</option>
                    </select>
                </td>
            </tr>
            
            <tr>
                <td>主辦會員ID:</td>
                <td><input type="number" name="memberId" value="1" required></td>
            </tr>
            
            <tr><td>活動標題:</td><td><input type="text" name="activityTitle" required></td></tr>
            <tr><td>活動描述:</td><td><textarea name="activityDescription" style="width: 200px; height: 60px;"></textarea></td></tr>
            <tr><td>價格:</td><td><input type="number" name="activityPrice" value="0" min="0"></td></tr>
            <tr><td>最低人數:</td><td><input type="number" name="minParticipants" value="1" min="1"></td></tr>
            <tr><td>最高人數:</td><td><input type="number" name="maxParticipants" value="10" min="1"></td></tr>
            <tr><td>報名開始:</td><td><input type="datetime-local" name="registrationStartTime" required></td></tr>
            <tr><td>報名結束:</td><td><input type="datetime-local" name="registrationDeadline" required></td></tr>
            <tr><td>活動結束:</td><td><input type="datetime-local" name="endTime" required></td></tr>
        </table>
        <br>
        <input type="hidden" name="action" value="insert">
        <input type="submit" value="送出新增" style="padding: 5px 15px; cursor: pointer;">
    </form>
    <br>
    <a href="listAllActivity.jsp">🔙 取消並回列表</a>
</body>
</html>