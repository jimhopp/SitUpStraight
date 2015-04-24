package com.jimhopp.situpstraight;


import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.TextView;

public class WhenActivityTest extends ActivityInstrumentationTestCase2<WhenActivity>{
    private WhenActivity mActivity;
    private TextView mStartTime, mEndTime;
    private CheckBox mEnabled;

    public WhenActivityTest() {
        super(WhenActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);

        mActivity = getActivity();
        mStartTime = (TextView) mActivity.findViewById(R.id.startTime);
        mEndTime = (TextView) mActivity.findViewById(R.id.endTime);
        mEnabled = (CheckBox) mActivity.findViewById(R.id.enabled);
    }

    public void testSetStartTime() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStartTime.requestFocus();
            }
        });
//how to send :?
        sendKeys(KeyEvent.KEYCODE_1);
        sendKeys(KeyEvent.KEYCODE_0);
        getInstrumentation().waitForIdleSync();
        assertEquals("Got wrong time", "10", mStartTime.getText().toString());
    }
}
