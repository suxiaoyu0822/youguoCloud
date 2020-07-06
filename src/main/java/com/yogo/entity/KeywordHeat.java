package com.yogo.entity;

public class KeywordHeat {
    private Integer keywordId;

    private Long heatTime;

    public Integer getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Integer keywordId) {
        this.keywordId = keywordId;
    }

    public Long getHeatTime() {
        return heatTime;
    }

    public void setHeatTime(Long heatTime) {
        this.heatTime = heatTime;
    }

    public KeywordHeat(Integer keywordId, Long heatTime) {
        this.keywordId = keywordId;
        this.heatTime = heatTime;
    }

    public KeywordHeat() {
    }

    @Override
    public String toString() {
        return "KeywordHeat{" +
                "keywordId=" + keywordId +
                ", heatTime=" + heatTime +
                '}';
    }
}