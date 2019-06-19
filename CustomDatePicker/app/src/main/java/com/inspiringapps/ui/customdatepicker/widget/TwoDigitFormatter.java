package com.inspiringapps.ui.customdatepicker.widget;

import androidx.annotation.NonNull;

import com.shawnlin.numberpicker.NumberPicker;

import java.text.DecimalFormatSymbols;
import java.util.Formatter;
import java.util.Locale;

/**
 * A class to format two-digit minutes strings like "01".
 *
 * Derived from {android.widget.NumberPicker.TwoDigitFormatter}
 */
public class TwoDigitFormatter
    implements NumberPicker.Formatter
{
    private final Object[] args = new Object[1];
    private final StringBuilder sb = new StringBuilder();

    private char zeroDigit;
    private Formatter formatter;

    TwoDigitFormatter() {
        init(Locale.getDefault());
    }

    private void init(@NonNull final Locale locale) {
        formatter = createFormatter(locale);
        zeroDigit = getZeroDigit(locale);
    }

    @NonNull
    @Override
    public String format(final int value) {
        final Locale currentLocale = Locale.getDefault();
        if (zeroDigit != getZeroDigit(currentLocale)) {
            init(currentLocale);
        }
        args[0] = value;
        sb.delete(0, sb.length());
        formatter.format("%02d", args);
        return formatter.toString();
    }

    private static char getZeroDigit(@NonNull final Locale locale) {
        // The original TwoDigitFormatter directly referenced LocaleData's value. Instead,
        // we need to use the public DecimalFormatSymbols API.
        return DecimalFormatSymbols.getInstance(locale).getZeroDigit();
    }

    private java.util.Formatter createFormatter(@NonNull final Locale locale) {
        return new java.util.Formatter(sb, locale);
    }
}
