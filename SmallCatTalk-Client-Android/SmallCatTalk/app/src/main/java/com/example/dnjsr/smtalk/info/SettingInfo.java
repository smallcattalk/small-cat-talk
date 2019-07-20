package com.example.dnjsr.smtalk.info;

import android.graphics.drawable.Drawable;

public class SettingInfo {
    //private Drawable setting_Image;
    private int setting_Image;
    private String setting_menu;

   /* public SettingInfo(Drawable setting_Image, String setting_menu) {
        this.setting_Image = setting_Image;
        this.setting_menu = setting_menu;
    }

    public Drawable getSetting_Image() {
        return setting_Image;
    }

    public void setSetting_Image(Drawable setting_Image) {
        this.setting_Image = setting_Image;
    }*/

    public SettingInfo(int setting_Image, String setting_menu) {
        this.setting_Image = setting_Image;
        this.setting_menu = setting_menu;
    }

    public int getSetting_Image() {
        return setting_Image;
    }

    public void setSetting_Image(int setting_Image) {
        this.setting_Image = setting_Image;
    }

    public String getSetting_menu() {
        return setting_menu;
    }

    public void setSetting_menu(String setting_menu) {
        this.setting_menu = setting_menu;
    }
}
