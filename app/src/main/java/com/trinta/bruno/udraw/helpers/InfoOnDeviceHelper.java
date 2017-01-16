package com.trinta.bruno.udraw.helpers;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import org.jcodec.common.model.Point;

/**
 * Created by Bruno on 14/10/2016.
 *
 * Helper used in the application to get
 * the size of the device the application runs on.
 */

public class InfoOnDeviceHelper {
    /**
     * Method return the path to the ExternalStorage Directory if it exists.
     * @return Path to external storage
     */
    public static String getVideoFilePath() {
        String resultPath = "error";
        //TODO Throw error here
        if(Environment.getExternalStorageDirectory() != null) {
            StringBuilder pathSave = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath());
            resultPath = pathSave.toString();
        }
        return resultPath;
    }

    /**
     * Return the size of the screen of the context in a Point Object.
     * @param context
     * @return Point X: width Y: height
     */
    public static Point getScreenSizeInPixels(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        return new Point(screenWidth, screenHeight);
    }


}
