<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>揪團活動管理首頁</title>
</head>
<body>

    <h2>揪團活動管理首頁</h2>
    
    <div style="margin-bottom: 20px; padding: 10px; background-color: #f4f4f4; border-radius: 5px;">
        <h3>📌 系統管理選單</h3>
        <a href="listAllActivity.jsp" style="font-size: 18px; color: blue; font-weight: bold;">
            👉 點此進入【所有揪團活動列表】(包含新增、修改、刪除功能)
        </a>
    </div>

    <hr>
    
    <h3>🔍 快速查詢單筆活動</h3>
    <form action="${pageContext.request.contextPath}/activity/activity.do" method="post">
        <label>輸入活動編號：</label>
        <input type="text" name="activityId" value="1">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出查詢">
    </form>

</body>
</html>