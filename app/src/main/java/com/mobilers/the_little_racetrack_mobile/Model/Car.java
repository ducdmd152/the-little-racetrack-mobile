package com.mobilers.the_little_racetrack_mobile.Model;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class Car {
    private String name;
    private LinearLayout checkBoxContainer;
    private CheckBox checkBox;
    private EditText etAmountForCar;
    private SeekBar seekBar;
    private ImageView badgeImage;

    public Car(String name, LinearLayout checkBoxContainer, CheckBox checkBox, EditText etAmountForCar, SeekBar seekBar, ImageView badgeImage) {
        this.name = name;
        this.checkBoxContainer = checkBoxContainer;
        this.checkBox = checkBox;
        this.badgeImage = badgeImage;
        this.seekBar = seekBar;
        this.etAmountForCar = etAmountForCar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinearLayout getCheckBoxContainer() {
        return checkBoxContainer;
    }

    public void setCheckBoxContainer(LinearLayout checkBoxContainer) {
        this.checkBoxContainer = checkBoxContainer;
    }

    public EditText getEtAmountForCar() {
        return etAmountForCar;
    }

    public void setEtAmountForCar(EditText etAmountForCar) {
        this.etAmountForCar = etAmountForCar;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public ImageView getBadgeImage() {
        return badgeImage;
    }

    public void setBadgeImage(ImageView badgeImage) {
        this.badgeImage = badgeImage;
    }
}
