package com.example.test.langlarm.gags;


import com.example.test.langlarm.R;

import java.util.NoSuchElementException;

public enum GagLevel {

    HARD(3, R.color.colorHard),
    MEDIUM(2, R.color.colorMedium),
    EASY(1, R.color.colorEasy);

    private int level;
    private int color;

    GagLevel(int level, int color) {
        this.level = level;
        this.color = color;
    }

    public int getLevel() {
        return level;
    }

    public int getColor() {
        return color;
    }

    public static GagLevel valueOf(int level) {
        switch (level) {
            case 3:
                return GagLevel.HARD;
            case 2:
                return GagLevel.MEDIUM;
            case 1:
                return GagLevel.EASY;
            default:
                throw new NoSuchElementException("there is no level like this");
        }
    }

}
