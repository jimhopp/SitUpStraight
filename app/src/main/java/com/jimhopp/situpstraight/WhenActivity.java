package com.jimhopp.situpstraight;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;


public class WhenActivity extends Activity {
    EditText mStartTimeView, mEndTimeView;
    CheckBox mEnabledView;

    boolean mEnabled;
    String mStartTimeStr, mEndTimeStr;

    public static final String TYPE = "NUDGE_ME";

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

        mStartTimeView = (EditText) findViewById(R.id.startTime);
        mStartTimeView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(TYPE, "onEditorAction got " + actionId + " for "
                        + (event == null ? "(null event)" : event.toString()));
                mStartTimeStr = mStartTimeView.getText().toString();
                Log.d(TYPE, "onEditorAction: start time=" + mStartTimeStr);
                return false;
            }
        });

        mEndTimeView = (EditText) findViewById(R.id.endTime);
        mEndTimeView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(TYPE, "onEditorAction got " + actionId + " for "
                        + (event == null ? "(null event)" : event.toString()));
                mEndTimeStr = mEndTimeView.getText().toString();
                Log.d(TYPE, "onEditorAction: end time=" + mEndTimeStr);
                return false;
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

    public static int squareMe(int a) { return a * a;}
}
