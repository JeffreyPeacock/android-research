package com.inspiringapps.ui.customdatepicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inspiringapps.ui.customdatepicker.widget.CustomDatePicker;
import com.inspiringapps.ui.customdatepicker.widget.CustomDatePickerDialog;
import com.inspiringapps.ui.customdatepicker.widget.CustomDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity
    extends AppCompatActivity
    implements CustomDatePickerDialog.OnDateSetListener
{
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
    }


    @VisibleForTesting
    void showDate(final int year, final int monthOfYear, final int dayOfMonth, final int spinnerTheme) {
        new CustomDatePickerDialogBuilder()
                .context(MainActivity.this)
                .callback(MainActivity.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }
}
