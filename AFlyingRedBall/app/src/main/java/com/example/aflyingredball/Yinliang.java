package com.example.aflyingredball;


public class Yinliang{
    private static int sound;
    //音量开关暂存
    public static int getsound() {
        return sound;
    }

    public void setsound(int sound) {
        this.sound = sound;
    }

    public int toint() {
        return sound;
    }

    public Yinliang(int sound) {
        super();
        this.sound = sound;
    }

}