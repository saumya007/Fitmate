package com.saumya.fitmate.beans;

import java.util.ArrayList;

/**
 * Created by saumyamehta on 8/23/17.
 */

public class main_beans {
    private String name;
    private ArrayList<horizontal_beans> allItemsInSection;

//    private String count;
//    private int addbtn;
//    private String title;
//    private int thumbnail;

    public main_beans() {
    }

    public main_beans(String name/*, String count, int addbtn, String title, int thumbnail*/) {
        this.name = name;
//        this.title = title;
//        this.count = count;
//        this.addbtn = addbtn;
//        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<horizontal_beans> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<horizontal_beans> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }
//    public String getCount() {
//        return count;
//    }
//
//    public void setCount(String count) {
//        this.count = count;
//    }
//
//    public int getAddbtn() {
//        return addbtn;
//    }
//
//    public void setAddbtn(int addbtn) {
//        this.addbtn = addbtn;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public int getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(int thumbnail) {
//        this.thumbnail = thumbnail;
//    }
}