package com.surehappiness.app.dto;

public class Rank {

    private String userId;
    private int totalStamp;
    private String ranking; // 공동 랭킹은 "-" 로 표시
    private int mine;       // 1: mine, 0: not mine (사용자 순위 받아오기 위함)

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalStamp() {
        return totalStamp;
    }

    public void setTotalStamp(int totalStamp) {
        this.totalStamp = totalStamp;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public int getMine() {
        return mine;
    }

    public void setMine(int mine) {
        this.mine = mine;
    }

    @Override
    public String toString() {
        return ranking + "\t\t" + userId + "\t\t" + totalStamp;
    }
}