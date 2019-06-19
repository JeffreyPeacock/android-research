package com.inspiringapps.ui.customdatepicker.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.inspiringapps.ui.customdatepicker.R;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A fork of the Android Open Source Project DatePickerDialog class
 */
public class CustomDatePickerDialog
    extends AlertDialog
    implements OnClickListener, OnDateChangedListener
{
    private static final String LOGTAG = "CustomDatePickerDialog";

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TITLE_SHOWN = "title_enabled";

    private final CustomDatePicker mDatePicker;
    private final OnDateSetListener mCallBack;
    private final DateFormat mTitleDateFormat;

    private boolean mIsDayShown = true;
    private boolean mIsTitleShown = true;

    private final TextView dateTextView;

    private final View.OnClickListener okButtonListener;
    private final View.OnClickListener cancelButtonListener;

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
    CustomDatePickerDialog(@NonNull final Context context,
                           final int theme,
                           final int spinnerTheme,
                           @Nullable OnDateSetListener callBack,
                           @Nullable View.OnClickListener positiveButtonListener,
                           @Nullable View.OnClickListener negativeButtonListener,
                           @NonNull Calendar defaultDate,
                           @NonNull Calendar minDate,
                           @NonNull Calendar maxDate,
                           final boolean isDayShown,
                           final boolean isTitleShown) {
        super(context, theme);

        mCallBack = callBack;
        okButtonListener = positiveButtonListener;
        cancelButtonListener = negativeButtonListener;
        mTitleDateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        mIsDayShown = isDayShown;
        mIsTitleShown = isTitleShown;

        final LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.custom_date_picker_dialog, null);
        setView(view);

        // find the views we manage
        dateTextView = (TextView) view.findViewById(R.id.date_text);
        final Button okButton = (Button) view.findViewById(R.id.buttonOk);
        final Button cancelButton = (Button) view.findViewById(R.id.buttonCancel);
        okButton.setOnClickListener(v -> onOkButtonClicked(v));
        cancelButton.setOnClickListener(v -> onCancelButtonClicked(v));

        updateTitle(defaultDate);

        final FrameLayout frameLayout = view.findViewById(R.id.date_picker_container);
        mDatePicker = new CustomDatePicker(frameLayout, spinnerTheme);
        mDatePicker.setMinDate(minDate.getTimeInMillis());
        mDatePicker.setMaxDate(maxDate.getTimeInMillis());
        mDatePicker.init(defaultDate.get(Calendar.YEAR),
                defaultDate.get(Calendar.MONTH),
                defaultDate.get(Calendar.DAY_OF_MONTH),
                isDayShown, this);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.drawable.bklib_bg_dialog);
    }

    // This never gets called by the user because we have our own buttons
    @Override
    public void onClick(@NonNull final DialogInterface dialog, final int which) {
        Log.d(LOGTAG, "onClick");
        if (mCallBack != null) {
            mDatePicker.clearFocus();
            mCallBack.onDateSet(mDatePicker, mDatePicker.getYear(),
                    mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
        }
    }

    private void onOkButtonClicked(@NonNull final View view) {
        onClick(this, DialogInterface.BUTTON_POSITIVE);
        okButtonListener.onClick(view);
        this.cancel();
    }

    private void onCancelButtonClicked(@NonNull final View view) {
        onClick(this, DialogInterface.BUTTON_NEGATIVE);
        cancelButtonListener.onClick(view);
        this.cancel();
    }

    @Override
    public void onDateChanged(@NonNull final CustomDatePicker view,
                                       final int year,
                                       final int monthOfYear,
                                       final int dayOfMonth) {
        Log.d(LOGTAG, "onDateChanged");
        final Calendar updatedDate = Calendar.getInstance();
        updatedDate.set(Calendar.YEAR, year);
        updatedDate.set(Calendar.MONTH, monthOfYear);
        updatedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateTitle(updatedDate);
    }

    private void updateTitle(@NonNull final Calendar updatedDate) {
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
        final int year = savedInstanceState.getInt(YEAR);
        final int month = savedInstanceState.getInt(MONTH);
        final int day = savedInstanceState.getInt(DAY);
        mIsTitleShown = savedInstanceState.getBoolean(TITLE_SHOWN);
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        updateTitle(c);
        mDatePicker.init(year, month, day, mIsDayShown, this);
    }
}