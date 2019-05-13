package com.mingyuechunqiu.mediapicker.util;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ToolbarUtils {

    public static void initToolbar(FragmentActivity activity, Toolbar toolbar, MediaPickerConfig config) {
        if (activity == null || toolbar == null || config == null) {
            return;
        }
        final WeakReference<AppCompatActivity> activityWeakRef = new WeakReference<>((AppCompatActivity) activity);
        activityWeakRef.get().setSupportActionBar(toolbar);
        ActionBar actionBar = activityWeakRef.get().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        int statusBarHeight = getStatusBarHeight(activity);
        toolbar.getLayoutParams().height = toolbar.getLayoutParams().height + statusBarHeight;
        toolbar.setPadding(0, statusBarHeight, 0, 0);
        toolbar.setBackgroundColor(config.getThemeConfig().getTopBackgroundColor());
        toolbar.setNavigationIcon(config.getThemeConfig().getBackIconResId());
        String title = "";
        switch (config.getMediaPickerType()) {
            case TYPE_IMAGE:
                title = activityWeakRef.get().getString(R.string.mp_select_image);
                break;
            case TYPE_AUDIO:
                title = activityWeakRef.get().getString(R.string.mp_select_audio);
                break;
            case TYPE_VIDEO:
                title = activityWeakRef.get().getString(R.string.mp_select_video);
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(title)) {
            toolbar.setTitle(title);
            toolbar.setTitleTextColor(config.getThemeConfig().getTopTextColor());
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 返回状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }
}
