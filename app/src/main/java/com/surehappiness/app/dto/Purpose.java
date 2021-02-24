package com.surehappiness.app.dto;

public class Purpose {

    private int idx;
    private String purposeName;
    private String purposeMemo;
    private String startDate;
    private String endDate;
    private String successDate;
    private int stampNum;              // 목표 설정 시 정할 스탬프 개수
    private String purposeState;     // S, P, F
    private int user_idx;
    private int userStampCount;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getPurposeName() {
        return purposeName;
    }

    public void setPurposeName(String purposeName) {
        this.purposeName = purposeName;
    }

    public String getPurposeMemo() {
        return purposeMemo;
    }

    public void setPurposeMemo(String purposeMemo) {
        this.purposeMemo = purposeMemo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSuccessDate() {
        return successDate;
    }

    public void setSuccessDate(String successDate) {
        this.successDate = successDate;
    }

    public int getStampNum() {
        return stampNum;
    }

    public void setStampNum(int stampNum) {
        this.stampNum = stampNum;
    }

    public String getPurposeState() {
        return purposeState;
    }

    public void setPurposeState(String purposeState) {
        this.purposeState = purposeState;
    }

    public int getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(int user_idx) {
        this.user_idx = user_idx;
    }

    public int getUserStampCount() {
        return userStampCount;
    }

    public void setUserStampCount(int userStampCount) {
        this.userStampCount = userStampCount;
    }

    @Override
    public String toString() {
        return "Purpose{" +
                "idx=" + idx +
                ", purposeName='" + purposeName + '\'' +
                ", purposeMemo='" + purposeMemo + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", successDate='" + successDate + '\'' +
                ", stampNum=" + stampNum +
                ", purposeState='" + purposeState + '\'' +
                ", user_idx=" + user_idx +
                ", userStampCount=" + userStampCount +
                '}';
    }
}