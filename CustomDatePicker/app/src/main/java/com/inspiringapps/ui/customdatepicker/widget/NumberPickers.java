package com.inspiringapps.ui.customdatepicker.widget;

import android.widget.EditText;
import android.widget.NumberPicker;

public class NumberPickers {

    //inefficient way of obtaining EditText from inside NumberPicker - not too bad here as View
    //hierarchy is very small -
    public static EditText findEditText(NumberPicker np) {
        for (int i = 0; i < np.getChildCount(); i++) {
            if (np.getChildAt(i) instanceof EditText) {
                return (EditText) np.getChildAt(i);
            }
        }
        return null;
    }
}
