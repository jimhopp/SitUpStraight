package com.jimhopp.situpstraight;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
import java.text.ParseException;

public class WhenActivityUnitTest extends ActivityUnitTestCase<WhenActivity> {

    public WhenActivityUnitTest() {
        super(WhenActivity.class);
    }

    private WhenActivity mActivity;
    private TextView mStartTime, mEndTime;
    private CheckBox mEnabled;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getContext(), WhenActivity.class);
        startActivity(intent, null, null);
        mActivity = getActivity();
        mStartTime = (TextView) mActivity.findViewById(R.id.startTime);
        mEndTime = (TextView) mActivity.findViewById(R.id.endTime);
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

    public void testAMHour() throws ParseException {
        assertEquals(1, WhenActivity.TimePickerFragment.parseHour("01:59AM"));
    }

    public void testMidnight() throws ParseException {
            assertEquals(0, WhenActivity.TimePickerFragment.parseHour("12:59AM"));
    }

    public void testAlmostMidnight() throws ParseException {
        assertEquals(23, WhenActivity.TimePickerFragment.parseHour("11:59PM"));
    }

    public void testPM() throws ParseException {
        assertEquals(13, WhenActivity.TimePickerFragment.parseHour("1:59PM"));
    }

    public void testHourTrailingSpaces() throws ParseException {
        assertEquals(9, WhenActivity.TimePickerFragment.parseHour("9  :10 AM"));

    }
    public void testHourNull() {
        try {
            assertEquals(1, WhenActivity.TimePickerFragment.parseHour(null));
            fail("did not throw parse exception for null string");
        }
        catch (ParseException e) {}
    }

    public void testHourEmpty() {
        try {
            assertEquals(1, WhenActivity.TimePickerFragment.parseHour(""));
            fail("did not throw parse exception for empty string");
        }
        catch (ParseException e) {}
    }

    public void testHourNoSep() {
        try {
            assertEquals(1, WhenActivity.TimePickerFragment.parseHour("0123"));
            fail("did not throw parse exception for missing separator");
        }
        catch (ParseException e) {}
    }

    public void testHourNotAnInteger() {
        try {
            assertEquals(1, WhenActivity.TimePickerFragment.parseHour("01.50:30"));
            fail("did not throw parse exception for not an int");
        }
        catch (ParseException e) {}
    }
    public void testHourOutOfRange() {
        try {
            assertEquals(25, WhenActivity.TimePickerFragment.parseHour("25:10"));
            fail("did not throw parse exception for out of range");
        }
        catch (ParseException e) {}
    }

    public void testHourNegative() {
        try {
            assertEquals(-3, WhenActivity.TimePickerFragment.parseHour("-3:01"));
            fail("did not throw parse exception for negative");
        }
        catch (ParseException e) {}
    }

    public void testMinuteNoAMPM() throws ParseException {
        assertEquals(59, WhenActivity.TimePickerFragment.parseMinute("01:59"));
    }

    public void testMinuteNoLeadingZero() throws ParseException {
        assertEquals(1, WhenActivity.TimePickerFragment.parseMinute("03:1"));
    }

    public void testMinuteTrailingSpaces() throws ParseException {
        assertEquals(10, WhenActivity.TimePickerFragment.parseMinute("9:10 AM"));
    }

    public void testMinuteAM() throws ParseException {
        assertEquals(59, WhenActivity.TimePickerFragment.parseMinute("01:59AM"));
    }

    public void testMinutePM() throws ParseException {
        assertEquals(59, WhenActivity.TimePickerFragment.parseMinute("01:59PM"));
    }

    public void testMinuteNull() {
        try {
            assertEquals(1, WhenActivity.TimePickerFragment.parseMinute(null));
            fail("did not throw parse exception for null string");
        }
        catch (ParseException e) {}
    }

    public void testMinuteEmpty() {
        try {
            assertEquals(1, WhenActivity.TimePickerFragment.parseMinute(""));
            fail("did not throw parse exception for empty string");
        }
        catch (ParseException e) {}
    }

    public void testMinuteNoSep() {
        try {
            assertEquals(1, WhenActivity.TimePickerFragment.parseMinute("0123"));
            fail("did not throw parse exception for missing separator");
        }
        catch (ParseException e) {}
    }

    public void testMinuteOutOfRange() {
        try {
            assertEquals(60, WhenActivity.TimePickerFragment.parseMinute("01:60"));
            fail("did not throw parse exception for out of range");
        }
        catch (ParseException e) {}
    }

    public void testMinuteNegative() {
        try {
            assertEquals(-1, WhenActivity.TimePickerFragment.parseMinute("01:-1"));
            fail("did not throw parse exception for negative");
        }
        catch (ParseException e) {}
    }
}
