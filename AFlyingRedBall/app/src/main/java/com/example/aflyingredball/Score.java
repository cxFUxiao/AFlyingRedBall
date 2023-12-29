package com.example.aflyingredball;

public class Score {
    private int pages;

    //数据库暂存
    public int getpages() {
        return pages;
    }

    public void setpages(Score score) {
        this.pages = pages;
    }

    public String toString() {
        return pages+"";
    }

    public Score(int pages) {
        super();
        this.pages = pages;
    }


}
