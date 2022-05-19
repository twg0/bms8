package com.alddeul.capstone;

public class villageselect_VO {
    boolean checked;
    String ItemString;

    villageselect_VO(boolean b, String t) {
        checked = b;
        ItemString = t;
    }

    public boolean isChecked() {
        return checked;
    }
}