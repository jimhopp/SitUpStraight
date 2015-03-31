package com.jimhopp.situpstraight;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.CheckBox;
import android.widget.EditText;

public class WhenActivityUnitTest extends ActivityUnitTestCase<WhenActivity> {
    public WhenActivityUnitTest() {
        super(WhenActivity.class);
    }

    private WhenActivity mActivity;
    private EditText mStartTime, mEndTime;
    private CheckBox mEnabled;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getContext(), WhenActivity.class);
        startActivity(intent, null, null);
        mActivity = getActivity();
        mStartTime = (EditText) mActivity.findViewById(R.id.startTime);
        mEndTime = (EditText) mActivity.findViewById(R.id.endTime);
        mEnabled = (CheckBox) mActivity.findViewById(R.id.enabled);
    }

    public void testCheckboxEnabledByDefault() {
        assertTrue(mEnabled.isChecked());
    }

    public void testDisablingTimeFields() {
        mEnabled.performClick();
        assertFalse(mStartTime.isEnabled());
        assertFalse(mEndTime.isEnabled());
    }

    public void testEnablingTimeFields() {
        mEnabled.performClick();
        mEnabled.performClick();
        assertTrue(mStartTime.isEnabled());
        assertTrue(mEndTime.isEnabled());
    }
}
