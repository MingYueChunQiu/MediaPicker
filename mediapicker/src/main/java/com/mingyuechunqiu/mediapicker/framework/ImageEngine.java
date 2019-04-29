package com.mingyuechunqiu.mediapicker.framework;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import java.io.File;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 图片展示引擎接口
 *     version: 1.0
 * </pre>
 */
public interface ImageEngine {

    /**
     * 显示图片
     *
     * @param context       上下文
     * @param file          显示图片
     * @param placeholderId 占位图资源
     * @param errorId       错误图资源
     * @param imageView     显示控件
     */
    void showImage(Context context, File file,
                   @DrawableRes int placeholderId, @DrawableRes int errorId, ImageView imageView);

    /**
     * 显示图片
     *
     * @param fragment      界面
     * @param file          显示图片
     * @param placeholderId 占位图资源
     * @param errorId       错误图资源
     * @param imageView     显示控件
     */
    void showImage(Fragment fragment, File file,
                   @DrawableRes int placeholderId, @DrawableRes int errorId, ImageView imageView);
}
