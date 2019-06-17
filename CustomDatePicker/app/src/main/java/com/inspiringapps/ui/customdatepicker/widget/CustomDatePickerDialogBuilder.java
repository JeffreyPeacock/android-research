package com.inspiringapps.ui.customdatepicker.widget;

import android.content.Context;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CustomDatePickerDialogBuilder
{
    private Context context;
    private CustomDatePickerDialog.OnDateSetListener callBack;
    private boolean isDayShown = true;
    private boolean isTitleShown = true;
    private int theme = 0;                 //default theme
    private int spinnerTheme = 0;          //default theme
    private Calendar defaultDate = new GregorianCalendar(1980, 0, 1);
    private Calendar minDate = new GregorianCalendar(1900, 0, 1);
    private Calendar maxDate = new GregorianCalendar(2100, 0, 1);


    public CustomDatePickerDialogBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public CustomDatePickerDialogBuilder callback(CustomDatePickerDialog.OnDateSetListener callBack) {
        this.callBack = callBack;
        return this;
    }

    public CustomDatePickerDialogBuilder dialogTheme(int theme) {
        this.theme = theme;
        return this;
    }

    public CustomDatePickerDialogBuilder spinnerTheme(int spinnerTheme) {
        this.spinnerTheme = spinnerTheme;
        return this;
    }

    public CustomDatePickerDialogBuilder defaultDate(int year, int monthIndexedFromZero, int day) {
        this.defaultDate = new GregorianCalendar(year, monthIndexedFromZero, day);
        return this;
    }

    public CustomDatePickerDialogBuilder minDate(int year, int monthIndexedFromZero, int day) {
        this.minDate = new GregorianCalendar(year, monthIndexedFromZero, day);
        return this;
    }

    public CustomDatePickerDialogBuilder maxDate(int year, int monthIndexedFromZero, int day) {
        this.maxDate = new GregorianCalendar(year, monthIndexedFromZero, day);
        return this;
    }

    public CustomDatePickerDialogBuilder showDaySpinner(boolean showDaySpinner) {
        this.isDayShown = showDaySpinner;
        return this;
    }

    public CustomDatePickerDialogBuilder showTitle(boolean showTitle) {
        this.isTitleShown = showTitle;
        return this;
    }

    public CustomDatePickerDialog build() {
        if (context == null) throw new IllegalArgumentException("Context must not be null");
        if (maxDate.getTime().getTime() <= minDate.getTime().getTime()) throw new IllegalArgumentException("Max date is not after Min date");

        return new CustomDatePickerDialog(context, theme, spinnerTheme, callBack, defaultDate, minDate, maxDate, isDayShown, isTitleShown);
    }
}