package com.example.jordan.weatherapp;

public class Story {
    String mTitle;
    String mContent;
    String mDate;
    Story(String title, String date, String content) {
        mTitle = title;
        mContent = content;
        mDate = date;
    }
    void setTitle(String title) {
        mTitle = title;
    }
    String getTitle() {
        return mTitle;
    }
    void setContent(String content) {
        mContent = content;
    }
    String getContent() {
        return mContent;
    }
    void setDate(String date) {
        mDate = date;
    }
    String getDate() {
        return mDate;
    }
}
