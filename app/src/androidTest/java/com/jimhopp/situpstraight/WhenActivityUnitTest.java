package com.jimhopp.situpstraight;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

public class WhenActivityUnitTest extends ActivityUnitTestCase<WhenActivity> {
    public WhenActivityUnitTest() {
        super(WhenActivity.class);
    }

    private WhenActivity mActivity;
    private TextView mStartTime;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getContext(), WhenActivity.class);
        startActivity(intent, null, null);
        mActivity = getActivity();
        mStartTime = (TextView) mActivity.findViewById(R.id.startTime);

    }

    public void testStartTime() {
        assertNotNull(mStartTime);
    }
}
