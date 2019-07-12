package com.mingyuechunqiu.mediapicker.data.config;

import android.graphics.Color;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.constants.Constants;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/5/13
 *     desc   : 多媒体选择器主题配置信息类
 *     version: 1.0
 * </pre>
 */
public class MediaPickerThemeConfig {

    private int themeType;//主题类型（浅色或深色，请设置此类时一定要配置涉及到状态栏配置，默认为深色）

    private @ColorInt
    int topBackgroundColor;//顶部背景颜色

    private @ColorInt
    int bottomBackgroundColor;//底部背景颜色

    private @ColorInt
    int topTextColor;//顶部文字颜色

    private @ColorInt
    int bottomTextColor;//底部文字颜色

    private @DrawableRes
    int backIconResId;//返回Icon资源ID

    private @DrawableRes
    int upTriangleIconResId;//向上三角Icon资源ID

    private @DrawableRes
    int downTriangleIconResId;//向下三角Icon资源ID

    public int getThemeType() {
        return themeType;
    }

    public void setThemeType(int themeType) {
        this.themeType = themeType;
    }

    public int getTopBackgroundColor() {
        return topBackgroundColor;
    }

    public void setTopBackgroundColor(int topBackgroundColor) {
        this.topBackgroundColor = topBackgroundColor;
    }

    public int getBottomBackgroundColor() {
        return bottomBackgroundColor;
    }

    public void setBottomBackgroundColor(int bottomBackgroundColor) {
        this.bottomBackgroundColor = bottomBackgroundColor;
    }

    public int getTopTextColor() {
        return topTextColor;
    }

    public void setTopTextColor(int topTextColor) {
        this.topTextColor = topTextColor;
    }

    public int getBottomTextColor() {
        return bottomTextColor;
    }

    public void setBottomTextColor(int bottomTextColor) {
        this.bottomTextColor = bottomTextColor;
    }

    public int getBackIconResId() {
        return backIconResId;
    }

    public void setBackIconResId(int backIconResId) {
        this.backIconResId = backIconResId;
    }

    public int getUpTriangleIconResId() {
        return upTriangleIconResId;
    }

    public void setUpTriangleIconResId(int upTriangleIconResId) {
        this.upTriangleIconResId = upTriangleIconResId;
    }

    public int getDownTriangleIconResId() {
        return downTriangleIconResId;
    }

    public void setDownTriangleIconResId(int downTriangleIconResId) {
        this.downTriangleIconResId = downTriangleIconResId;
    }

    /**
     * 链式调用
     */
    public static class Builder {
        private MediaPickerThemeConfig mConfig;

        public Builder() {
            mConfig = new MediaPickerThemeConfig();
        }

        public MediaPickerThemeConfig build() {
            return mConfig;
        }

        /**
         * 创建默认浅色主题配置信息
         *
         * @return 返回浅色主题配置信息对象
         */
        public MediaPickerThemeConfig buildLightTheme() {
            mConfig.topBackgroundColor = Color.WHITE;
            mConfig.bottomBackgroundColor = Color.parseColor("#33000000");
            mConfig.topTextColor = Color.BLACK;
            mConfig.bottomTextColor = Color.BLACK;
            mConfig.backIconResId = R.drawable.mp_back_dark;
            mConfig.upTriangleIconResId = R.drawable.mp_up_triangle_dark;
            mConfig.downTriangleIconResId = R.drawable.mp_down_triangle_dark;
            mConfig.themeType = Constants.ThemeTypeConstants.TYPE_LIGHT;
            return mConfig;
        }

        /**
         * 创建默认深色主题配置信息
         *
         * @return 返回深色主题配置信息对象
         */
        public MediaPickerThemeConfig buildDarkTheme() {
            mConfig.topBackgroundColor = Color.parseColor("#2C2C34");
            mConfig.bottomBackgroundColor = Color.parseColor("#2C2C34");
            mConfig.topTextColor = Color.WHITE;
            mConfig.bottomTextColor = Color.WHITE;
            mConfig.backIconResId = R.drawable.mp_back_light;
            mConfig.upTriangleIconResId = R.drawable.mp_up_triangle_light;
            mConfig.downTriangleIconResId = R.drawable.mp_down_triangle_light;
            mConfig.themeType = Constants.ThemeTypeConstants.TYPE_DARK;
            return mConfig;
        }

        public int getThemeType() {
            return mConfig.themeType;
        }

        public Builder setThemeType(int themeType) {
            mConfig.themeType = themeType;
            return this;
        }

        public int getTopBackgroundColor() {
            return mConfig.topBackgroundColor;
        }

        public Builder setTopBackgroundColor(int topBackgroundColor) {
            mConfig.topBackgroundColor = topBackgroundColor;
            return this;
        }

        public int getBottomBackgroundColor() {
            return mConfig.bottomBackgroundColor;
        }

        public Builder setBottomBackgroundColor(int bottomBackgroundColor) {
            mConfig.bottomBackgroundColor = bottomBackgroundColor;
            return this;
        }

        public int getTopTextColor() {
            return mConfig.topTextColor;
        }

        public Builder setTopTextColor(int topTextColor) {
            mConfig.topTextColor = topTextColor;
            return this;
        }

        public int getBottomTextColor() {
            return mConfig.bottomTextColor;
        }

        public Builder setBottomTextColor(int bottomTextColor) {
            mConfig.bottomTextColor = bottomTextColor;
            return this;
        }

        public int getBackIconResId() {
            return mConfig.backIconResId;
        }

        public Builder setBackIconResId(int backIconResId) {
            mConfig.backIconResId = backIconResId;
            return this;
        }

        public int getUpTriangleIconResId() {
            return mConfig.upTriangleIconResId;
        }

        public Builder setUpTriangleIconResId(int upTriangleIconResId) {
            mConfig.upTriangleIconResId = upTriangleIconResId;
            return this;
        }

        public int getDownTriangleIconResId() {
            return mConfig.downTriangleIconResId;
        }

        public Builder setDownTriangleIconResId(int downTriangleIconResId) {
            mConfig.downTriangleIconResId = downTriangleIconResId;
            return this;
        }
    }
}
