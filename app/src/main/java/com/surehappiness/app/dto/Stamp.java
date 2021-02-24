package com.surehappiness.app.dto;

public class Stamp {

    private int idx;
    private String updateDate;
    private int position;
    private boolean isChecked;
    private Purpose purpose;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "Stamp{" +
                "idx=" + idx +
                ", updateDate='" + updateDate + '\'' +
                ", position=" + position +
                ", isChecked=" + isChecked +
                ", purpose=" + purpose +
                '}';
    }
}