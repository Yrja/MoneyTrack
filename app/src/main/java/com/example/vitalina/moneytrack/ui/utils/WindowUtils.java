package com.example.vitalina.moneytrack.ui.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class WindowUtils {
    public static Point getScreenDimension(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Point point = new Point();
        point.x = width;
        point.y = height;
        return point;
    }
}
