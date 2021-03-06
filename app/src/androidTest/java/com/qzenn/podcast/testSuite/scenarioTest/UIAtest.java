package com.qzenn.podcast.testSuite.scenarioTest;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.Suppress;

import com.squareup.spoon.Spoon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Suppress
@RunWith(AndroidJUnit4.class)
public class UIAtest extends InstrumentationTestCase{

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.qzenn.podcast";

    private static final int LAUNCH_TIMEOUT = 5000;

    private UiDevice mDevice;

    @Before
    public void setUp() throws UiObjectNotFoundException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        mDevice.pressHome();
//
////        File dir = InstrumentationRegistry.getInstrumentation().getContext().getDir("spoon-screenshots", android.content.Context.MODE_WORLD_READABLE);
//
//        final Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
//        assertNotNull(bitmap);
//
//        File file = new File("/sdcard","/scr.png");
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        OutputStream fos = null;
//        try {
//            fos = new BufferedOutputStream(new FileOutputStream(file));
//            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100 /* quality */, fos);
//
//            try {
//                Runtime.getRuntime().exec(new String[] {"chmod", "777", file.getAbsolutePath()});
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            bitmap.recycle();
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        mDevice.findObject(new UiSelector().descriptionContains("Apps")).click();

        mDevice.findObject(new UiSelector().textContains("Podcas-T")).click();

        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), 5000);
    }

    @Test
    public void testOpenPlayer() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().textContains("Open Media Player")).click();
    }

    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
