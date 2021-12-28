package com.example.amsallel_tabata_timer.data;

import android.util.Pair;

import androidx.annotation.ColorInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Action {
    private String label;
    private long timeValue;
    private @ColorInt int color;

    public Action(String label, long timeValue, @ColorInt int color ){
        this.label = label;
        this.timeValue = timeValue;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(long timeValue) {
        this.timeValue = timeValue;
    }

    public @ColorInt int getColor() { return color; }

    public void setColor(@ColorInt int color) { this.color = color; }

}
