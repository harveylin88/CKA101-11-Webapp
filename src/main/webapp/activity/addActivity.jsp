<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增活動</title>
<style>
    table { margin-top: 20px; border-collapse: collapse; }
    td { padding: 8px; }
    select, input[type="text"], input[type="number"], input[type="datetime-local"] { padding: 5px; width: 220px; }
    .error { color: red; font-size: 14px; font-weight: bold; margin-left: 10px; }
    .sys-error { color: white; background-color: red; padding: 10px; border-radius: 5px; display: inline-block; }
</style>
</head>
<body>
    <h2>新增揪團活動</h2>
    
    <%-- 使用 EL 判斷，若 systemError 有值就會印出來 --%>
    <div style="color: red; font-weight: bold; background-color: #ffe6e6; padding: 10px; margin-bottom: 10px; display: ${empty errorMsgs.systemError ? 'none' : 'block'};">
    ${errorMsgs.systemError}
</div>
    
    <form method="post" action="${pageContext.request.contextPath}/activity/activity.do">
        <table>
            <tr>
                <td>活動類型:</td>
                <td>
                    <select name="activityTypeId">
                        <option value="1" ${activityVo.activityTypeId == 1 ? 'selected' : ''}>球類運動 (羽球/籃球等)</option>
                        <option value="2" ${activityVo.activityTypeId == 2 ? 'selected' : ''}>桌遊益智</option>
                        <option value="3" ${activityVo.activityTypeId == 3 ? 'selected' : ''}>逛街購物</option>
                        <option value="4" ${activityVo.activityTypeId == 4 ? 'selected' : ''}>戶外踏青</option>
                        <option value="5" ${activityVo.activityTypeId == 5 ? 'selected' : ''}>探店美食</option>
                        <option value="6" ${activityVo.activityTypeId == 6 ? 'selected' : ''}>看電影/追劇</option>
                        <option value="7" ${activityVo.activityTypeId == 7 ? 'selected' : ''}>唱歌KTV</option>
                        <option value="8" ${activityVo.activityTypeId == 8 ? 'selected' : ''}>健身重訓</option>
                        <option value="9" ${activityVo.activityTypeId == 9 ? 'selected' : ''}>密室逃脫</option>
                        <option value="10" ${activityVo.activityTypeId == 10 ? 'selected' : ''}>藝文展覽</option>
                        <option value="11" ${activityVo.activityTypeId == 11 ? 'selected' : ''}>遊戲電競</option>
                        <option value="12" ${activityVo.activityTypeId == 12 ? 'selected' : ''}>其他</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>主辦會員ID:</td>
                <td>
                    <input type="number" name="memberId" value="${not empty activityVo.memberId ? activityVo.memberId : '1'}" required>
                    <span class="error">${errorMsgs.memberId}</span>
                </td>
            </tr>
            <tr>
                <td>活動標題:</td>
                <td>
                    <input type="text" name="activityTitle" value="${activityVo.activityTitle}">
                    <span class="error">${errorMsgs.activityTitle}</span>
                </td>
            </tr>
            <tr>
                <td>活動描述:</td>
                <td>
                    <textarea name="activityDescription" style="width: 220px; height: 60px;">${activityVo.activityDescription}</textarea>
                </td>
            </tr>
            <tr>
                <td>價格:</td>
                <td>
                    <input type="number" name="activityPrice" value="${not empty activityVo.activityPrice ? activityVo.activityPrice : '0'}">
                    <span class="error">${errorMsgs.activityPrice}</span>
                </td>
            </tr>
            <tr>
                <td>最低人數:</td>
                <td>
                    <input type="number" name="minParticipants" value="${not empty activityVo.minParticipants ? activityVo.minParticipants : '1'}">
                    <span class="error">${errorMsgs.minParticipants}</span>
                </td>
            </tr>
            <tr>
                <td>最高人數:</td>
                <td>
                    <input type="number" name="maxParticipants" value="${not empty activityVo.maxParticipants ? activityVo.maxParticipants : '10'}">
                    <span class="error">${errorMsgs.maxParticipants}</span>
                </td>
            </tr>
            <tr>
                <td>報名開始:</td>
                <td>
                    <input type="datetime-local" name="registrationStartTime" value="${regStart}">
                    <span class="error">${errorMsgs.registrationStartTime}</span>
                </td>
            </tr>
            <tr>
                <td>報名結束:</td>
                <td>
                    <input type="datetime-local" name="registrationDeadline" value="${regDead}">
                    <span class="error">${errorMsgs.registrationDeadline}</span>
                </td>
            </tr>
            <tr>
                <td>活動結束:</td>
                <td>
                    <input type="datetime-local" name="endTime" value="${actEnd}">
                    <span class="error">${errorMsgs.endTime}</span>
                </td>
            </tr>
        </table>
        
        <br>
        <span class="error">${errorMsgs.timeError}</span>
        <br><br>
        
        <input type="hidden" name="action" value="insert">
        <input type="submit" value="送出新增" style="padding: 5px 15px; cursor: pointer; font-size: 16px;">
    </form>
    <br>
    <a href="${pageContext.request.contextPath}/activity/listAllActivity.jsp">🔙 取消並回列表</a>
</body>
</html>