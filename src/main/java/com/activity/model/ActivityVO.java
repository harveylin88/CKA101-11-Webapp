package com.activity.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ActivityVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer activityId;
    private Integer activityTypeId;
    private Integer memberId;
    private String activityTitle;
    private String activityDescription;
    private Integer activityPrice;
    private Integer minParticipants;
    private Integer maxParticipants;
    private Integer attendeesCount;
    private Timestamp registrationStartTime;
    private Timestamp registrationDeadline;
    private Byte activityStatus;
    private Timestamp createdAt;
    private Timestamp endTime;

    // Getter and Setter
    public Integer getActivityId() { return activityId; }
    public void setActivityId(Integer activityId) { this.activityId = activityId; }

    public Integer getActivityTypeId() { return activityTypeId; }
    public void setActivityTypeId(Integer activityTypeId) { this.activityTypeId = activityTypeId; }

    public Integer getMemberId() { return memberId; }
    public void setMemberId(Integer memberId) { this.memberId = memberId; }

    public String getActivityTitle() { return activityTitle; }
    public void setActivityTitle(String activityTitle) { this.activityTitle = activityTitle; }

    public String getActivityDescription() { return activityDescription; }
    public void setActivityDescription(String activityDescription) { this.activityDescription = activityDescription; }

    public Integer getActivityPrice() { return activityPrice; }
    public void setActivityPrice(Integer activityPrice) { this.activityPrice = activityPrice; }

    public Integer getMinParticipants() { return minParticipants; }
    public void setMinParticipants(Integer minParticipants) { this.minParticipants = minParticipants; }

    public Integer getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }

    public Integer getAttendeesCount() { return attendeesCount; }
    public void setAttendeesCount(Integer attendeesCount) { this.attendeesCount = attendeesCount; }

    public Timestamp getRegistrationStartTime() { return registrationStartTime; }
    public void setRegistrationStartTime(Timestamp registrationStartTime) { this.registrationStartTime = registrationStartTime; }

    public Timestamp getRegistrationDeadline() { return registrationDeadline; }
    public void setRegistrationDeadline(Timestamp registrationDeadline) { this.registrationDeadline = registrationDeadline; }

    public Byte getActivityStatus() { return activityStatus; }
    public void setActivityStatus(Byte activityStatus) { this.activityStatus = activityStatus; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getEndTime() { return endTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }
}