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
        
        // 3. 新增活動 (Insert) - 具備完整資料驗證與錯誤回傳
        if ("insert".equals(action)) {
            // 建立一個 Map 用來存放各欄位的專屬錯誤訊息
            java.util.Map<String, String> errorMsgs = new java.util.LinkedHashMap<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                // 1. 接收字串與基礎驗證
                Integer activityTypeId = Integer.valueOf(req.getParameter("activityTypeId"));
                Integer memberId = Integer.valueOf(req.getParameter("memberId"));
                
                String activityTitle = req.getParameter("activityTitle");
                if (activityTitle == null || activityTitle.trim().isEmpty()) {
                    errorMsgs.put("activityTitle", "活動標題請勿空白");
                } else if (activityTitle.trim().length() < 2 || activityTitle.trim().length() > 50) {
                    errorMsgs.put("activityTitle", "活動標題長度必須在 2 到 50 個字之間");
                }

                String activityDescription = req.getParameter("activityDescription");
                
                // 2. 數字類型的驗證
                Integer activityPrice = 0;
                try {
                    activityPrice = Integer.valueOf(req.getParameter("activityPrice").trim());
                    if (activityPrice < 0) {
                        errorMsgs.put("activityPrice", "活動價格不能為負數");
                    }
                } catch (NumberFormatException e) {
                    errorMsgs.put("activityPrice", "價格請填寫正確的數字");
                }

                Integer minParticipants = 1;
                Integer maxParticipants = 10;
                try {
                    minParticipants = Integer.valueOf(req.getParameter("minParticipants").trim());
                    maxParticipants = Integer.valueOf(req.getParameter("maxParticipants").trim());
                    if (minParticipants < 1) {
                        errorMsgs.put("minParticipants", "最低人數至少需為 1 人");
                    }
                    if (maxParticipants < minParticipants) {
                        errorMsgs.put("maxParticipants", "最高人數不能低於最低人數");
                    }
                } catch (NumberFormatException e) {
                    errorMsgs.put("maxParticipants", "人數請填寫正確的整數");
                }

                // 3. 日期時間驗證
                java.sql.Timestamp registrationStartTime = null;
                java.sql.Timestamp registrationDeadline = null;
                java.sql.Timestamp endTime = null;

                try {
                    String startStr = req.getParameter("registrationStartTime");
                    if (startStr == null || startStr.trim().isEmpty()) errorMsgs.put("registrationStartTime", "請選擇報名開始時間");
                    else registrationStartTime = java.sql.Timestamp.valueOf(startStr.replace("T", " ") + (startStr.length() == 16 ? ":00" : ""));

                    String deadStr = req.getParameter("registrationDeadline");
                    if (deadStr == null || deadStr.trim().isEmpty()) errorMsgs.put("registrationDeadline", "請選擇報名結束時間");
                    else registrationDeadline = java.sql.Timestamp.valueOf(deadStr.replace("T", " ") + (deadStr.length() == 16 ? ":00" : ""));

                    String endStr = req.getParameter("endTime");
                    if (endStr == null || endStr.trim().isEmpty()) errorMsgs.put("endTime", "請選擇活動結束時間");
                    else endTime = java.sql.Timestamp.valueOf(endStr.replace("T", " ") + (endStr.length() == 16 ? ":00" : ""));
                    
                    // 邏輯驗證：截止時間不能早於開始時間，結束時間不能早於截止時間
                    if (registrationStartTime != null && registrationDeadline != null && registrationDeadline.before(registrationStartTime)) {
                        errorMsgs.put("registrationDeadline", "報名截止不能早於開始時間");
                    }
                    if (registrationDeadline != null && endTime != null && endTime.before(registrationDeadline)) {
                        errorMsgs.put("endTime", "活動結束時間不能早於報名截止時間");
                    }
                } catch (IllegalArgumentException e) {
                    errorMsgs.put("timeError", "日期格式不正確");
                }

                // 4. 將填寫的資料裝回 Vo，若驗證失敗可以退回前端保留資料
                ActivityVO activityVo = new ActivityVO();
                activityVo.setActivityTypeId(activityTypeId);
                activityVo.setMemberId(memberId);
                activityVo.setActivityTitle(activityTitle);
                activityVo.setActivityDescription(activityDescription);
                activityVo.setActivityPrice(activityPrice);
                activityVo.setMinParticipants(minParticipants);
                activityVo.setMaxParticipants(maxParticipants);
                
                // 若 Map 裡面有錯誤訊息，就代表驗證沒過，直接退回新增網頁
                if (!errorMsgs.isEmpty()) {
                    req.setAttribute("activityVo", activityVo); // 保留剛剛輸入的資料
                    
                    // 因為剛輸入的時間格式跟資料庫Timestamp格式不同，為了前端 datetime-local 能吃，額外把原字串傳回去
                    req.setAttribute("regStart", req.getParameter("registrationStartTime"));
                    req.setAttribute("regDead", req.getParameter("registrationDeadline"));
                    req.setAttribute("actEnd", req.getParameter("endTime"));
                    
                    RequestDispatcher failureView = req.getRequestDispatcher("/activity/addActivity.jsp");
                    failureView.forward(req, res);
                    return; // 中斷執行，不會寫入資料庫
                }

                // 5. 驗證全過，呼叫 Service 執行新增
                ActivityService actSvc = new ActivityService();
                actSvc.addActivity(activityTypeId, memberId, activityTitle, activityDescription, activityPrice, 
                        minParticipants, maxParticipants, registrationStartTime, registrationDeadline, endTime);

                res.sendRedirect(req.getContextPath() + "/activity/listAllActivity.jsp");
                return;

            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.put("systemError", "系統發生錯誤：" + e.getMessage());
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