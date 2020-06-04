package com.example.truncation;

import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.LayoutMatchers.hasEllipsizedText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> menuActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.truncation", appContext.getPackageName());
    }

    @Test
    public void shoulRunTheApp() {
        onView(withId(R.id.hello)).check(matches(isDisplayed()));
    }

    @Test
    public void getText() {

        String text = getText(withId(R.id.hello));
        onView(withId(R.id.hello)).check(matches((hasEllipsizedText())));
    }

    String getText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    public static boolean isTextTruncated(String text, TextView textView) {
        if (textView != null && text != null) {

            Layout layout = textView.getLayout();
            if (layout != null) {
                int lines = layout.getLineCount();
                if (lines > 0) {
                    int ellipsisCount = layout.getEllipsisCount(lines - 1);
                    if (ellipsisCount > 0) {
                        return true;
                    }
                }
            }

        }
        return false;
    }
}
