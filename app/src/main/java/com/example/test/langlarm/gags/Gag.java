package com.example.test.langlarm.gags;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class Gag {

    private String pl;
    private String en;
    private GagLevel level;

    public Gag(String pl, String en, int level) {
        this.pl = pl;
        this.en = en;
        this.level = GagLevel.valueOf(level);
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public GagLevel getLevel() {
        return level;
    }

    public void setLevel(GagLevel level) {
        this.level = level;
    }

    public int getColor(Context context) {
        return ContextCompat.getColor(context, getLevel().getColor());
    }

}
