package com.jimhopp.situpstraight;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class WhenActivity extends Activity {
    static TextView mStartTimeView, mEndTimeView;
    static CheckBox mEnabledView;

    static boolean mEnabled;
    static String mEndTimeStr;

    public static final String TYPE = "NUDGE_ME";

    public static class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {
        static TextView tv;
        //TODO: turn into no-arg constructor and use Fragment#setArguments(Bundle) to pass
        // TextView, and getArguments to retrieve.
        public TimePickerFragment(TextView f) {
            super();
            tv = f;
            Log.d(TYPE, "new TimePickerFragment: tv=" + tv.toString());
        }

        /**
         * Override to build your own custom Dialog container.  This is typically
         * used to show an AlertDialog instead of a generic Dialog; when doing so,
         * {@link #onCreateView(LayoutInflater, ViewGroup, android.os.Bundle)} does not need
         * to be implemented since the AlertDialog takes care of its own content.
         * <p/>
         * <p>This method will be called after {@link #onCreate(android.os.Bundle)} and
         * before {@link #onCreateView(LayoutInflater, ViewGroup, android.os.Bundle)}.  The
         * default implementation simply instantiates and returns a {@link android.app.Dialog}
         * class.
         * <p/>
         * <p><em>Note: DialogFragment own the {@link android.app.Dialog#setOnCancelListener
         * Dialog.setOnCancelListener} and {@link android.app.Dialog#setOnDismissListener
         * Dialog.setOnDismissListener} callbacks.  You must not set them yourself.</em>
         * To find out about these events, override {@link #onCancel(DialogInterface)}
         * and {@link #onDismiss(DialogInterface)}.</p>
         *
         * @param savedInstanceState The last saved instance state of the Fragment,
         *                           or null if this is a freshly created Fragment.
         * @return Return a new Dialog instance to be displayed by the Fragment.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //TODO: getArguments(Bundle) to retrieve the TextView
            Calendar cal = Calendar.getInstance();
            int hour, minute;
            try {
                hour = parseHour(tv.getText().toString());
                minute = parseMinute(tv.getText().toString());
            } catch (ParseException e) {
                Log.d(TYPE, "unable to parse " + tv.getText().toString() +
                            " because " + e.toString());
            }
            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(),this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public static int parseHour(String tm) throws ParseException {
            if (tm == null) throw new ParseException("tm is null", -1);
            if (tm.length() == 0) throw new ParseException("tm is empty", 0);

            int sep = tm.indexOf(":");
            if (sep == -1) throw new ParseException("unable to find separator", sep);

            int hr;

            try {hr = Integer.parseInt(tm.substring(0,sep));}
            catch (NumberFormatException e) {
                throw new ParseException("unable to parse " + tm, sep);
            }

            if (hr < 0 || hr > 23) {
                throw new ParseException("hour out of range: " + hr, sep);
            }

            if (tm.contains("PM") && hr < 12) hr += 12;
            if (tm.contains("AM") && hr == 12) hr = 0;

            return hr;
        }

        static int parseMinute(String tm) throws ParseException {
            if (tm == null) throw new ParseException("tm is null", -1);
            if (tm.length() == 0) throw new ParseException("tm is empty", 0);

            int sep = tm.indexOf(":");
            if (sep == -1) throw new ParseException("unable to find separator", sep);

            int min;

            int end = tm.length();
            int am = tm.indexOf("AM");
            int pm = tm.indexOf("PM");

            if (am != -1) end = am;
            if (pm != -1) end = pm;

            try {min = Integer.parseInt(tm.substring(sep+1,end));}
            catch (NumberFormatException e) {
                throw new ParseException("unable to parse " + tm, sep);
            }

            if (min < 0 || min > 59) {
                throw new ParseException("minutes out of range: " + min, sep);

            }

            return min;
        }
        /**
         * @param view      The view associated with this listener.
         * @param hourOfDay The hour that was set.
         * @param minute    The minute that was set.
         */
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Log.d(TYPE, "time is " + hourOfDay + ":" + minute);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            tv.setText(DateFormat.getTimeFormat(getActivity()).format(cal.getTime()));
            Log.d(TYPE, "time value is now " + tv.getText());
        }
    }
    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * {@link #managedQuery(android.net.Uri, String[], String, String[], String)} to retrieve
     * cursors for data being displayed, etc.
     * <p/>
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called without any of the rest
     * of the activity lifecycle ({@link #onStart}, {@link #onResume},
     * {@link #onPause}, etc) executing.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mStartTimeView = (TextView) findViewById(R.id.startTime);
        mStartTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TYPE, "start time got clicked");
                DialogFragment tmFrag = new TimePickerFragment(mStartTimeView);
                tmFrag.show(getFragmentManager(), "starttimepicker");
            }
        });

        mEndTimeView = (TextView) findViewById(R.id.endTime);
        mEndTimeView.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Log.d(TYPE, "end time got clicked");
                DialogFragment tmFrag = new TimePickerFragment(mEndTimeView);
                tmFrag.show(getFragmentManager(), "endtimepicker");
            }
        });

        mEnabledView = (CheckBox) findViewById(R.id.enabled);
        mEnabledView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TYPE, "onCheckedChanged; isChecked= " + isChecked);
                mEnabled = isChecked;
                mStartTimeView.setEnabled(mEnabled);
                mEndTimeView.setEnabled(mEnabled);
            }
        });
    }

    public void clicked(View v) {

    }

    public static int squareMe(int a) { return a * a;}
}
