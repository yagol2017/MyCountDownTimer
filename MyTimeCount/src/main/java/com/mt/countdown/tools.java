package com.mt.countdown;

public class tools {

    public String Int2String(int num) {
        String temp;
        temp = "" + num;
        return temp;
    }

    public String MakeTimeBetter(String sec_befor) {
        String time = "00:00:00";
        if (sec_befor.isEmpty()) {
            sec_befor = "0";
        }
        int sec = Integer.valueOf(sec_befor);
        if (sec < 60) {
            if (sec < 10) {
                time = "00:00:0" + sec;
            } else time = "00:00:" + sec;

            return time;
        }
        if (sec >= 60 && sec < 3600) {
            int min = sec / 60;
            int sec_after = sec - min * 60;
            if (min < 10) {
                if (sec_after < 10) {
                    time = "00:0" + min + ":0" + sec_after;
                } else
                    time = "00:0" + min + ":" + sec_after;
            } else
                time = "00:" + min + ":" + sec_after;
            return time;
        }
        if (sec >= 3600) {
            int shi = sec / 3600;
            int min = (sec - shi * 3600) / 60;
            int sec_after = sec - shi * 3600 - min * 60;
            if (shi < 10) {
                if (min < 10) {
                    if (sec_after < 10) {
                        time = "0" + shi + ":0" + min + ":0" + sec_after;
                    } else {
                        time = "0" + shi + ":0" + min + ":" + sec_after;
                    }
                } else if (sec_after < 10) {
                    time = "0" + shi + ":0" + min + ":0" + sec_after;
                } else if (min < 10) {
                    if (sec_after < 10) {
                        time = "0" + shi + ":0" + min + ":0" + sec_after;
                    } else {
                        time = "0" + shi + ":0" + min + ":" + sec_after;
                    }
                } else if (sec_after < 10) {
                    time = "0" + shi + ":0" + min + ":0" + sec_after;
                }
            } else {
                time = shi + ":" + min + ":" + sec_after;
            }
            return time;
        }
        return time;
    }

}
