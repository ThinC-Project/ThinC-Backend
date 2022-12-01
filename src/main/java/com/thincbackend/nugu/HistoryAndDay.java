package com.thincbackend.nugu;

import java.util.List;

public enum HistoryAndDay {

    YESTERDAY(List.of("어제", "전날"), -1),
    TWODAYSBEFORE(List.of("그제", "이틀전","2일전","엊그제"), -2),
    LASTWEEK(List.of("저번주", "지난주", "전주"), -7),
    TODAY(List.of("오늘", "지금"), 0);

    private List<String> history;
    private int calValue;

    HistoryAndDay(List<String> history, int calValue) {
        this.history = history;
        this.calValue = calValue;
    }

    public boolean isEqualDay(String day){
        for(String s : this.history){
            if(day.equals(s)){
                return true;
            }
        }
        return false;
    }

    public int getCalValue() {
        return calValue;
    }
}
