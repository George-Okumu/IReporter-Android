package com.moringa.ireporter;

import android.widget.TextView;

import com.moringa.ireporter.ui.SignUpActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SignUpTest {
    private SignUpActivity activity;
    @Before
    public void setUP(){
       activity = Robolectric.buildActivity(SignUpActivity.class)
               .create()
               .resume()
               .get();

    }

    @Test
    public void getTextView() throws Exception {
        TextView geo = activity.findViewById(R.id.sign_up);
        Assert.assertTrue("Let's G Started".equals(geo.getText().toString()));
    }

}
