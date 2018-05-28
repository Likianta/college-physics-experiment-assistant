package com.likianta.cpea.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Likianta_Dodoora on 2018/4/12 0012.
 */
public class DpKillsPxUtils {
    /**
     * By this way, you can use '1*dpx' to express '1dp' in Java code.
     * Usually the width of our phone screen is 360dp, and the height is 640dp.
     * So it could be very simple to use '360*dpx'/'640*dpx' instead of '1080px'/'1920px' or any
     * other specific pixels.
     *
     * @param context
     * @return dp
     */
    public int dp(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels / 360;
    }
}
