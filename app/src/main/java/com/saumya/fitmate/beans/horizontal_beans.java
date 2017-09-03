package com.saumya.fitmate.beans;

import android.text.SpannableString;

/**
 * Created by saumyamehta on 8/24/17.
 */

public class horizontal_beans {
    private SpannableString count;
    private int addbtn;
    private SpannableString title;
    private int thumbnail,cancelimg;

    public int getCancelimg() {
        return cancelimg;
    }

    public void setCancelimg(int cancelimg) {
        this.cancelimg = cancelimg;
    }

    public horizontal_beans() {
    }

    public horizontal_beans(SpannableString count, int addbtn, SpannableString title, int thumbnail, int cancelimg) {
        this.title = title;
        this.count = count;
        this.addbtn = addbtn;
        this.thumbnail = thumbnail;
        this.cancelimg = cancelimg;
    }
    public horizontal_beans(SpannableString count,  int thumbnail) {
        this.count = count;
        this.thumbnail = thumbnail;
    }


    public SpannableString getCount() {
        return count;
    }

    public void setCount(SpannableString count) {
        this.count = count;
    }

    public int getAddbtn() {
        return addbtn;
    }

    public void setAddbtn(int addbtn) {
        this.addbtn = addbtn;
    }

    public SpannableString getTitle() {
        return title;
    }

    public void setTitle(SpannableString title) {
        this.title = title;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }



}