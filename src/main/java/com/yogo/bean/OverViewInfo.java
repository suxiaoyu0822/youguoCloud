package com.yogo.bean;


import com.yogo.entity.DayAndTime;
import com.yogo.entity.KeywordAndHeat;

import java.util.List;

/**
 * Created by hp on 2017/7/14.
 */
public class OverViewInfo {
    public int onlineServer;
    public int totalServer;
    public int onlineClient;
    public int advCount;
    public List<GroupAndPersonNum> groupAndPersonNums;
    public List<DayAndTime> halfMonth;
    public List<DayAndTime> weekInfo;
//    public List<DayAndTime> dayInfo;
    public List<DayAndTime> minuteInfo;
    public List<KeywordAndHeat> keywordAndHeats;

    public int getAdvCount() {
        return advCount;
    }

    public void setAdvCount(int advCount) {
        this.advCount = advCount;
    }
    
    public List<DayAndTime> getWeekInfo() {
        return weekInfo;
    }

    public void setWeekInfo(List<DayAndTime> weekInfo) {
        this.weekInfo = weekInfo;
    }

    public List<GroupAndPersonNum> getGroupAndPersonNums() {
        return groupAndPersonNums;
    }

    public void setGroupAndPersonNums(List<GroupAndPersonNum> groupAndPersonNums) {
        this.groupAndPersonNums = groupAndPersonNums;
    }

//    public List<DayAndTime> getDayInfo() {
//        return dayInfo;
//    }

//    public void setDayInfo(List<DayAndTime> dayInfo) {
//        this.dayInfo = dayInfo;
//    }

    public List<DayAndTime> getMinuteInfo() {
        return minuteInfo;
    }

    public void setMinuteInfo(List<DayAndTime> minuteInfo) {
        this.minuteInfo = minuteInfo;
    }

    public int getOnlineServer() {
        return onlineServer;
    }

    public void setOnlineServer(int onlineServer) {
        this.onlineServer = onlineServer;
    }

    public int getTotalServer() {
        return totalServer;
    }

    public void setTotalServer(int totalServer) {
        this.totalServer = totalServer;
    }

    public int getOnlineClient() {
        return onlineClient;
    }

    public void setOnlineClient(int onlineClient) {
        this.onlineClient = onlineClient;
    }

    public List<KeywordAndHeat> getKeywordAndHeats() {
        return keywordAndHeats;
    }

    public void setKeywordAndHeats(List<KeywordAndHeat> keywordAndHeats) {
        this.keywordAndHeats = keywordAndHeats;
    }

    public List<DayAndTime> getHalfMonth() {
        return halfMonth;
    }

    public void setHalfMonth(List<DayAndTime> halfMonth) {
        this.halfMonth = halfMonth;
    }
}
