package com.surehappiness.app.activity.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PurposeInfo implements Parcelable {

    int idx;
    String name;
    String memo;
    String startDate;
    String endDate;
    int stampNum;
    String purposeState;

    protected PurposeInfo(Parcel in) {
        idx = in.readInt();
        name = in.readString();
        memo = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        stampNum = in.readInt();
        purposeState = in.readString();
    }

    public PurposeInfo(int idx, String name, String memo, String startDate, String endDate, int stampNum, String purposeState){
        this.idx = idx;
        this.name = name;
        this.memo = memo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.stampNum = stampNum;
        this.purposeState = purposeState;

    }

    public static final Creator<PurposeInfo> CREATOR = new Creator<PurposeInfo>() {
        @Override
        public PurposeInfo createFromParcel(Parcel in) {
            return new PurposeInfo(in);
        }

        @Override
        public PurposeInfo[] newArray(int size) {
            return new PurposeInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idx);
        parcel.writeString(name);
        parcel.writeString(memo);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeInt(stampNum);
        parcel.writeString(purposeState);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public int getStampNum() {
        return stampNum;
    }

    public void setStampNum(int stampNum) {
        this.stampNum = stampNum;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getPurposeState() {
        return purposeState;
    }

    public void setPurposeState(String purposeState) {
        this.purposeState = purposeState;
    }
}
