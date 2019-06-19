package com.inspiringapps.ui.customdatepicker.widget;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CustomDatePickerDialogBuilder
{
    private int theme = 0;                 //default theme
    private int spinnerTheme = 0;          //default theme
    private boolean isDayShown = true;
    private boolean isTitleShown = true;

    private Context context;
    private CustomDatePickerDialog.OnDateSetListener callBack;

    private Calendar defaultDate = new GregorianCalendar(1980, 0, 1);
    private Calendar minDate = new GregorianCalendar(1900, 0, 1);
    private Calendar maxDate = new GregorianCalendar(2100, 0, 1);

    private View.OnClickListener positiveButtonListener;
    private View.OnClickListener negativeButtonListener;


    @NonNull
    public CustomDatePickerDialogBuilder context(@NonNull final Context context) {
        this.context = context;
        return this;
    }

    @NonNull
    public CustomDatePickerDialogBuilder callback(@NonNull final CustomDatePickerDialog.OnDateSetListener callBack) {
        this.callBack = callBack;
        return this;
    }

    @NonNull
    public CustomDatePickerDialogBuilder dialogTheme(final int theme) {
        this.theme = theme;
        return this;
    }

    @NonNull
    public CustomDatePickerDialogBuilder spinnerTheme(final int spinnerTheme) {
        this.spinnerTheme = spinnerTheme;
        return this;
    }

    @NonNull
    public CustomDatePickerDialogBuilder defaultDate(final int year, final int monthIndexedFromZero, final int day) {
        this.defaultDate = new GregorianCalendar(year, monthIndexedFromZero, day);
        return this;
    }

    @NonNull
    public CustomDatePickerDialogBuilder minDate(final int year, final int monthIndexedFromZero, final int day) {
        this.minDate = new GregorianCalendar(year, monthIndexedFromZero, day);
        return this;
    }

    public CustomDatePickerDialogBuilder maxDate(final int year, final int monthIndexedFromZero, final int day) {
        this.maxDate = new GregorianCalendar(year, monthIndexedFromZero, day);
        return this;
    }

    @NonNull
    public CustomDatePickerDialogBuilder showDaySpinner(final boolean showDaySpinner) {
        this.isDayShown = showDaySpinner;
        return this;
    }

    @NonNull
    public CustomDatePickerDialogBuilder showTitle(final boolean showTitle) {
        this.isTitleShown = showTitle;
        return this;
    }

    @NonNull
    public CustomDatePickerDialogBuilder setPositiveButtonListener(@NonNull final View.OnClickListener listener) {
        this.positiveButtonListener = listener;
        return this;
    }

    @NonNull
    public CustomDatePickerDialogBuilder setNegativeButtonListener(@NonNull final View.OnClickListener listener) {
        this.negativeButtonListener = listener;
        return this;
    }

    @NonNull
    public CustomDatePickerDialog build() {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        if (maxDate.getTime().getTime() <= minDate.getTime().getTime()) {
            throw new IllegalArgumentException("Max date is not after Min date");
        }
        return new CustomDatePickerDialog(context, theme, spinnerTheme,
                callBack, positiveButtonListener, negativeButtonListener,
                defaultDate, minDate, maxDate, isDayShown, isTitleShown);
    }
}