package com.activity.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.activity.model.ActivityService;
import com.activity.model.ActivityVO;

@WebServlet("/activity/activity.do")
public class ActivityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	System.out.println("====== 成功進入 ActivityServlet！ action: " + req.getParameter("action") + " ======");
    		doPost(req, res);
        
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        // 1. 查詢單一活動
        if ("getOne_For_Display".equals(action)) {
            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                String str = req.getParameter("activityId");
                if (str == null || (str.trim()).length() == 0) {
                    errorMsgs.add("請輸入活動編號");
                }
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req.getRequestDispatcher("/activity/select_page.jsp");
                    failureView.forward(req, res);
                    return;
                }

                Integer activityId = Integer.valueOf(str);
                ActivityService actSvc = new ActivityService();
                ActivityVO activityVo = actSvc.getOneActivity(activityId);
                if (activityVo == null) {
                    errorMsgs.add("查無資料");
                }
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req.getRequestDispatcher("/activity/select_page.jsp");
                    failureView.forward(req, res);
                    return;
                }

                req.setAttribute("activityVo", activityVo);
                RequestDispatcher successView = req.getRequestDispatcher("/activity/listOneActivity.jsp");
                successView.forward(req, res);

            } catch (Exception e) {
                errorMsgs.add("無法取得資料:" + e.getMessage());
                RequestDispatcher failureView = req.getRequestDispatcher("/activity/select_page.jsp");
                failureView.forward(req, res);
            }
        }

     // 2. 刪除活動 (Delete) 
        if ("delete".equals(action)) {
            try {
                Integer activityId = Integer.valueOf(req.getParameter("activityId"));
                ActivityService actSvc = new ActivityService();
                actSvc.deleteActivity(activityId);

                // ✨ 修正：刪除成功後，同樣改用 sendRedirect
                res.sendRedirect(req.getContextPath() + "/activity/listAllActivity.jsp");
                return;

            } catch (Exception e) {
                System.out.println("====== 刪除發生錯誤 (可能是因為有外來鍵綁定，例如該活動已經有訂單) ======");
                e.printStackTrace(); // ✨ 印出錯誤原因
                
                RequestDispatcher failureView = req.getRequestDispatcher("/activity/listAllActivity.jsp");
                failureView.forward(req, res);
            }
        }
     // 3. 新增活動 (Insert)
        if ("insert".equals(action)) {
            try {
                // 接收前端表單資料
                Integer activityTypeId = Integer.valueOf(req.getParameter("activityTypeId"));
                Integer memberId = Integer.valueOf(req.getParameter("memberId"));
                String activityTitle = req.getParameter("activityTitle");
                String activityDescription = req.getParameter("activityDescription");
                Integer activityPrice = Integer.valueOf(req.getParameter("activityPrice"));
                Integer minParticipants = Integer.valueOf(req.getParameter("minParticipants"));
                Integer maxParticipants = Integer.valueOf(req.getParameter("maxParticipants"));
                
                // 修正：加入時間格式長度判斷 (與 Update 一樣安全)
                String startStr = req.getParameter("registrationStartTime").replace("T", " ");
                if(startStr.length() == 16) startStr += ":00";
                java.sql.Timestamp registrationStartTime = java.sql.Timestamp.valueOf(startStr);
                
                String deadStr = req.getParameter("registrationDeadline").replace("T", " ");
                if(deadStr.length() == 16) deadStr += ":00";
                java.sql.Timestamp registrationDeadline = java.sql.Timestamp.valueOf(deadStr);
                
                String endStr = req.getParameter("endTime").replace("T", " ");
                if(endStr.length() == 16) endStr += ":00";
                java.sql.Timestamp endTime = java.sql.Timestamp.valueOf(endStr);

                // 呼叫 Service 執行新增
                ActivityService actSvc = new ActivityService();
                actSvc.addActivity(activityTypeId, memberId, activityTitle, activityDescription, activityPrice, 
                        minParticipants, maxParticipants, registrationStartTime, registrationDeadline, endTime);

                // ✨ 修正：新增成功後，改用 sendRedirect 導向列表，避免重新整理時重複新增
                res.sendRedirect(req.getContextPath() + "/activity/listAllActivity.jsp");
                return; // 確保程式不會繼續往下跑

            } catch (Exception e) {
                System.out.println("====== 新增發生錯誤 ======");
                e.printStackTrace(); // ✨ 發生錯誤時，印出紅字在 Eclipse Console 讓我們抓蟲
                
                RequestDispatcher failureView = req.getRequestDispatcher("/activity/addActivity.jsp");
                failureView.forward(req, res);
            }
        }

        // ====================================================================
        // 4. 準備修改 (帶出該筆資料到修改網頁)
        // ====================================================================
        if ("getOne_For_Update".equals(action)) {
            try {
                Integer activityId = Integer.valueOf(req.getParameter("activityId"));
                
                ActivityService actSvc = new ActivityService();
                ActivityVO activityVo = actSvc.getOneActivity(activityId);
                
                // 把查到的資料放進 req 傳給修改網頁
                req.setAttribute("activityVo", activityVo);
                RequestDispatcher successView = req.getRequestDispatcher("/activity/updateActivity.jsp");
                successView.forward(req, res);
            } catch (Exception e) {
                RequestDispatcher failureView = req.getRequestDispatcher("/activity/listAllActivity.jsp");
                failureView.forward(req, res);
            }
        }

        // ====================================================================
        // 5. 確認修改 (Update)
        // ====================================================================
        if ("update".equals(action)) {
            try {
                Integer activityId = Integer.valueOf(req.getParameter("activityId"));
                Integer activityTypeId = Integer.valueOf(req.getParameter("activityTypeId"));
                Integer memberId = Integer.valueOf(req.getParameter("memberId"));
                String activityTitle = req.getParameter("activityTitle");
                String activityDescription = req.getParameter("activityDescription");
                Integer activityPrice = Integer.valueOf(req.getParameter("activityPrice"));
                Integer minParticipants = Integer.valueOf(req.getParameter("minParticipants"));
                Integer maxParticipants = Integer.valueOf(req.getParameter("maxParticipants"));
                Integer attendeesCount = Integer.valueOf(req.getParameter("attendeesCount"));
                Byte activityStatus = Byte.valueOf(req.getParameter("activityStatus"));
                
                // 處理時間格式
                String startStr = req.getParameter("registrationStartTime").replace("T", " ");
                if(startStr.length() == 16) startStr += ":00"; // 確保有秒數
                java.sql.Timestamp registrationStartTime = java.sql.Timestamp.valueOf(startStr);
                
                String deadStr = req.getParameter("registrationDeadline").replace("T", " ");
                if(deadStr.length() == 16) deadStr += ":00";
                java.sql.Timestamp registrationDeadline = java.sql.Timestamp.valueOf(deadStr);
                
                String endStr = req.getParameter("endTime").replace("T", " ");
                if(endStr.length() == 16) endStr += ":00";
                java.sql.Timestamp endTime = java.sql.Timestamp.valueOf(endStr);

                ActivityService actSvc = new ActivityService();
                ActivityVO activityVo = actSvc.updateActivity(activityId, activityTypeId, memberId, activityTitle, 
                        activityDescription, activityPrice, minParticipants, maxParticipants, attendeesCount, 
                        registrationStartTime, registrationDeadline, activityStatus, endTime);

                // 修改成功後，將更新後的資料傳給單筆顯示頁面
                req.setAttribute("activityVo", activityVo);
                RequestDispatcher successView = req.getRequestDispatcher("/activity/listOneActivity.jsp");
                successView.forward(req, res);

            } catch (Exception e) {
                System.out.println("修改失敗：" + e.getMessage());
                RequestDispatcher failureView = req.getRequestDispatcher("/activity/updateActivity.jsp");
                failureView.forward(req, res);
            }
        }
    }
}