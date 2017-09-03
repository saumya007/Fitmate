package com.saumya.fitmate.beans;

import android.text.SpannableString;

/**
 * Created by saumyamehta on 8/25/17.
 */

public class selector_beans {
    public int thubmnail;
    public SpannableString name;
    public String title;

    public selector_beans(String title,int thubmnail, SpannableString name) {
        this.thubmnail = thubmnail;
        this.title = title;
        this.name = name;
    }

    public selector_beans() {

    }

    public int getThubmnail() {
        return thubmnail;
    }

    public void setThubmnail(int thubmnail) {
        this.thubmnail = thubmnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SpannableString getName() {
        return name;
    }

    public void setName(SpannableString name) {
        this.name = name;
    }
}
