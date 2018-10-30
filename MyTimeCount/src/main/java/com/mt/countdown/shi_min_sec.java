package com.mt.countdown;

public class shi_min_sec {
    private int my_shi;
    private int my_min;
    private int my_sec;

    public int getMy_min() {
        return my_min;
    }

    public int getMy_sec() {
        return my_sec;
    }

    public int getMy_shi() {
        return my_shi;
    }

    public void setShiMinSec(String all_sec) {
        int time_sec = Integer.valueOf(all_sec);
        if (time_sec < 60) {
            my_shi = 0;
            my_min = 0;
            my_sec = time_sec;
        }
        if (time_sec >= 60 && time_sec < 3600) {
            int min = time_sec / 60;
            int sec_after = time_sec - min * 60;
            my_shi = 0;
            my_min = min;
            my_sec = sec_after;
        }
        if (time_sec >= 3600) {
            int shi = time_sec / 3600;
            int min = (time_sec - shi * 3600) / 60;
            int sec_after = time_sec - shi * 3600 - min * 60;
            if (sec_after < 10) {
                my_shi = shi;
                my_min = min;
                my_sec = sec_after;
            }
        }
    }
}
