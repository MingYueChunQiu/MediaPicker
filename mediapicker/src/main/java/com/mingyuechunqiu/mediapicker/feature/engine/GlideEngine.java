package com.mingyuechunqiu.mediapicker.feature.engine;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mingyuechunqiu.mediapicker.framework.ImageEngine;

import java.io.File;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : Glide图片引擎
 *              实现ImageEngine
 *     version: 1.0
 * </pre>
 */
public class GlideEngine implements ImageEngine {

    @Override
    public void showImage(Context context, File file, @DrawableRes int placeholderId, @DrawableRes int errorId, ImageView imageView) {
        if (context == null || file == null || !file.exists()) {
            return;
        }
        Glide.with(context)
                .load(file)
                .apply(new RequestOptions().placeholder(placeholderId).error(errorId))
                .into(imageView);
    }

    @Override
    public void showImage(Fragment fragment, File file, int placeholderId, int errorId, ImageView imageView) {
        if (fragment == null || file == null || !file.exists()) {
            return;
        }
        Glide.with(fragment)
                .load(file)
                .apply(new RequestOptions().placeholder(placeholderId).error(errorId))
                .into(imageView);
    }
}
