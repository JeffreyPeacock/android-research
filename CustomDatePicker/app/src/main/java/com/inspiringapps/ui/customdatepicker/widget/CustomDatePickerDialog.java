package com.inspiringapps.ui.customdatepicker.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.inspiringapps.ui.customdatepicker.R;

import java.text.DateFormat;
import java.util.Calendar;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * A fork of the Android Open Source Project DatePickerDialog class
 */
public class CustomDatePickerDialog
    extends AlertDialog
    implements OnClickListener, OnDateChangedListener
{
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TITLE_SHOWN = "title_enabled";

    private final CustomDatePicker mDatePicker;
    private final OnDateSetListener mCallBack;
    private final DateFormat mTitleDateFormat;

    private boolean mIsDayShown = true;
    private boolean mIsTitleShown = true;

    private TextView dateTextView;

    /**
     * The callback used to indicate the user is done filling in the date.
     */
    public interface OnDateSetListener {
        /**
         * @param view        The view associated with this listener.
         * @param year        The year that was set
         * @param monthOfYear The month that was set (0-11) for compatibility
 *                    with {@link Calendar}.
         * @param dayOfMonth  The day of the month that was set.
         */
        void onDateSet(CustomDatePicker view, int year, int monthOfYear, int dayOfMonth);
    }

    @SuppressWarnings("squid:S00107")
    CustomDatePickerDialog(Context context,
                           int theme,
                           int spinnerTheme,
                           OnDateSetListener callBack,
                           Calendar defaultDate,
                           Calendar minDate,
                           Calendar maxDate,
                           boolean isDayShown,
                           boolean isTitleShown) {
        super(context, theme);

        mCallBack = callBack;
        mTitleDateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        mIsDayShown = isDayShown;
        mIsTitleShown = isTitleShown;

        final LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.custom_date_picker_dialog, null);



        frameLayout.addView(view, new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));


        dateTextView = (TextView) view.findViewById(R.id.date_text);
        updateTitle(defaultDate);

        mDatePicker = new CustomDatePicker((ViewGroup) view, spinnerTheme);
        mDatePicker.setMinDate(minDate.getTimeInMillis());
        mDatePicker.setMaxDate(maxDate.getTimeInMillis());
        mDatePicker.init(defaultDate.get(Calendar.YEAR),
                defaultDate.get(Calendar.MONTH),
                defaultDate.get(Calendar.DAY_OF_MONTH),
                isDayShown, this);

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mCallBack != null) {
            mDatePicker.clearFocus();
            mCallBack.onDateSet(mDatePicker, mDatePicker.getYear(),
                    mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
        }
    }

    @Override
    public void onDateChanged(CustomDatePicker view, int year, int monthOfYear, int dayOfMonth) {
        final Calendar updatedDate = Calendar.getInstance();
        updatedDate.set(Calendar.YEAR, year);
        updatedDate.set(Calendar.MONTH, monthOfYear);
        updatedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateTitle(updatedDate);
    }

    private void updateTitle(Calendar updatedDate) {
        if(mIsTitleShown) {
            dateTextView.setText(mTitleDateFormat.format(updatedDate.getTime()));
        } else {
            dateTextView.setText(" ");
        }
    }

    @NonNull
    @Override
    public Bundle onSaveInstanceState() {
        final Bundle state = super.onSaveInstanceState();
        state.putInt(YEAR, mDatePicker.getYear());
        state.putInt(MONTH, mDatePicker.getMonth());
        state.putInt(DAY, mDatePicker.getDayOfMonth());
        state.putBoolean(TITLE_SHOWN, mIsTitleShown);
        return state;
    }

    @Override
    public void onRestoreInstanceState(@NonNull final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int year = savedInstanceState.getInt(YEAR);
        int month = savedInstanceState.getInt(MONTH);
        int day = savedInstanceState.getInt(DAY);
        mIsTitleShown = savedInstanceState.getBoolean(TITLE_SHOWN);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        updateTitle(c);
        mDatePicker.init(year, month, day, mIsDayShown, this);
    }
}