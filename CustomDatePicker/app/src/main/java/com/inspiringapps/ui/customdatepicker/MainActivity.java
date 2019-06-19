package com.inspiringapps.ui.customdatepicker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import com.inspiringapps.ui.customdatepicker.widget.CustomDatePicker;
import com.inspiringapps.ui.customdatepicker.widget.CustomDatePickerDialog;
import com.inspiringapps.ui.customdatepicker.widget.CustomDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity
    extends AppCompatActivity
    implements CustomDatePickerDialog.OnDateSetListener
{
    private static final String LOGTAG = "MainActivity";

    private Calendar selectedDate;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showDate(1980, 0, 1, R.style.BklibDatePicker);
    }

    @Override
    public void onDateSet(@NonNull final CustomDatePicker view,
                                   final int year,
                                   final int monthOfYear,
                                   final int dayOfMonth) {
        selectedDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
    }


    @VisibleForTesting
    void showDate(final int year, final int monthOfYear, final int dayOfMonth, final int spinnerTheme) {
        new CustomDatePickerDialogBuilder()
                .context(MainActivity.this)
                .callback(MainActivity.this)
                .dialogTheme(R.style.BklibDialogStyle)
                .setPositiveButtonListener(v -> okButtonClicked(v))
                .setNegativeButtonListener(v -> cancelButtonClicked(v))
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    private void okButtonClicked(@NonNull final View view) {
        Log.d(LOGTAG, "okButtonClicked().selectedDate="+formatter.format(selectedDate.getTime()));

    }
    private void cancelButtonClicked(@NonNull final View view) {
        Log.d(LOGTAG, "cancelButtonClicked().selectedDate="+formatter.format(selectedDate.getTime()));
    }
}
