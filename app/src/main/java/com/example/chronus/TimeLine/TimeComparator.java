package com.example.chronus.TimeLine;

import java.util.Comparator;


public class TimeComparator implements Comparator<TimeData> {
    @Override
    public int compare(TimeData td1, TimeData td2) {
        return td1.getPostDate().compareTo(td2.getPostDate());
    }
}
