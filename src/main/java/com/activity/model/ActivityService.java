package com.activity.model;

import java.util.List;
import java.sql.Timestamp;

public class ActivityService {
    private ActivityDAO dao;

    public ActivityService() {
        dao = new ActivityJDBCDAO();
    }

    public ActivityVO addActivity(Integer activityTypeId, Integer memberId, String activityTitle,
            String activityDescription, Integer activityPrice, Integer minParticipants,
            Integer maxParticipants, Timestamp registrationStartTime, Timestamp registrationDeadline, Timestamp endTime) {

        ActivityVO activityVo = new ActivityVO();
        activityVo.setActivityTypeId(activityTypeId);
        activityVo.setMemberId(memberId);
        activityVo.setActivityTitle(activityTitle);
        activityVo.setActivityDescription(activityDescription);
        activityVo.setActivityPrice(activityPrice);
        activityVo.setMinParticipants(minParticipants);
        activityVo.setMaxParticipants(maxParticipants);
        activityVo.setAttendeesCount(0); // 新開揪團初始為 0 人
        activityVo.setRegistrationStartTime(registrationStartTime);
        activityVo.setRegistrationDeadline(registrationDeadline);
        activityVo.setActivityStatus((byte) 0); // 預設 0:正常舉行
        activityVo.setEndTime(endTime);

        dao.insert(activityVo);
        return activityVo;
    }

    public ActivityVO updateActivity(Integer activityId, Integer activityTypeId, Integer memberId, String activityTitle,
            String activityDescription, Integer activityPrice, Integer minParticipants,
            Integer maxParticipants, Integer attendeesCount, Timestamp registrationStartTime, 
            Timestamp registrationDeadline, Byte activityStatus, Timestamp endTime) {

        ActivityVO activityVo = new ActivityVO();
        activityVo.setActivityId(activityId);
        activityVo.setActivityTypeId(activityTypeId);
        activityVo.setMemberId(memberId);
        activityVo.setActivityTitle(activityTitle);
        activityVo.setActivityDescription(activityDescription);
        activityVo.setActivityPrice(activityPrice);
        activityVo.setMinParticipants(minParticipants);
        activityVo.setMaxParticipants(maxParticipants);
        activityVo.setAttendeesCount(attendeesCount);
        activityVo.setRegistrationStartTime(registrationStartTime);
        activityVo.setRegistrationDeadline(registrationDeadline);
        activityVo.setActivityStatus(activityStatus);
        activityVo.setEndTime(endTime);

        dao.update(activityVo);
        return dao.findByPrimaryKey(activityId);
    }

    public void deleteActivity(Integer activityId) {
        dao.delete(activityId);
    }

    public ActivityVO getOneActivity(Integer activityId) {
        return dao.findByPrimaryKey(activityId);
    }

    public List<ActivityVO> getAll() {
        return dao.getAll();
    }
}