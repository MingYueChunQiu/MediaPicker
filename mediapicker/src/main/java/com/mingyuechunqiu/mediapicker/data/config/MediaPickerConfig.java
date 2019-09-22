package com.mingyuechunqiu.mediapicker.data.config;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mingyuechunqiu.mediapicker.data.constants.MediaPickerType;
import com.mingyuechunqiu.mediapicker.feature.engine.GlideEngine;
import com.mingyuechunqiu.mediapicker.framework.ImageEngine;

import java.util.List;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.SET_INVALID;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择器配置信息类
 *              实现Parcelable
 *     version: 1.0
 * </pre>
 */
public class MediaPickerConfig implements Parcelable {

    private MediaPickerType mediaPickerType;//多媒体选择类型

    private int maxSelectMediaCount;//最多可选择多媒体个数

    private long limitSize;//限制大小（单位B）

    private long limitDuration;//限制时长（毫秒）

    private List<String> limitSuffixTypeList;//限制只能显示的多媒体后缀类型列表

    private MediaPickerFilter mediaPickerFilter;//多媒体过滤器

    private boolean filterLimitSuffixType;//是否过滤超出后缀名类型限制的多媒体

    private boolean filterLimitMedia;//是否过滤超出限制的多媒体信息

    private int columnCount;//一行列数

    private int loadAnimation;//Item加载动画

    private boolean startPreviewByThird;//以第三方应用方式打开预览多媒体

    private MediaPickerThemeConfig themeConfig;//主题配置

    private ImageEngine engine;//图片加载引擎

    public MediaPickerConfig() {
        mediaPickerType = MediaPickerType.TYPE_IMAGE;
        maxSelectMediaCount = 1;
        limitSize = SET_INVALID;
        limitDuration = SET_INVALID;
        columnCount = 4;
        loadAnimation = BaseQuickAdapter.SCALEIN;
        startPreviewByThird = false;//默认以自定义方式预览多媒体
        themeConfig = new MediaPickerThemeConfig.Builder().buildDarkTheme();//默认浅色主题
        engine = new GlideEngine();
    }

    protected MediaPickerConfig(@NonNull Parcel in) {
        mediaPickerType = MediaPickerType.values()[in.readInt()];
        maxSelectMediaCount = in.readInt();
        limitSize = in.readLong();
        limitDuration = in.readLong();
        limitSuffixTypeList = in.createStringArrayList();
        filterLimitSuffixType = in.readByte() != 0;
        filterLimitMedia = in.readByte() != 0;
        columnCount = in.readInt();
        loadAnimation = in.readInt();
        startPreviewByThird = in.readByte() != 0;
        readThemeConfig(in);
    }

    public static final Creator<MediaPickerConfig> CREATOR = new Creator<MediaPickerConfig>() {
        @Override
        public MediaPickerConfig createFromParcel(Parcel in) {
            return new MediaPickerConfig(in);
        }

        @Override
        public MediaPickerConfig[] newArray(int size) {
            return new MediaPickerConfig[size];
        }
    };

    public MediaPickerType getMediaPickerType() {
        return mediaPickerType;
    }

    public void setMediaPickerType(MediaPickerType mediaPickerType) {
        this.mediaPickerType = mediaPickerType;
    }

    public int getMaxSelectMediaCount() {
        return maxSelectMediaCount;
    }

    public void setMaxSelectMediaCount(int maxSelectMediaCount) {
        this.maxSelectMediaCount = maxSelectMediaCount;
    }

    public long getLimitSize() {
        return limitSize;
    }

    public void setLimitSize(long limitSize) {
        this.limitSize = limitSize;
    }

    public long getLimitDuration() {
        return limitDuration;
    }

    public void setLimitDuration(long limitDuration) {
        this.limitDuration = limitDuration;
    }

    public List<String> getLimitSuffixTypeList() {
        return limitSuffixTypeList;
    }

    public void setLimitSuffixTypeList(List<String> limitSuffixTypeList) {
        this.limitSuffixTypeList = limitSuffixTypeList;
    }

    public MediaPickerFilter getMediaPickerFilter() {
        return mediaPickerFilter;
    }

    public void setMediaPickerFilter(MediaPickerFilter mediaPickerFilter) {
        this.mediaPickerFilter = mediaPickerFilter;
    }

    public boolean isFilterLimitSuffixType() {
        return filterLimitSuffixType;
    }

    public void setFilterLimitSuffixType(boolean filterLimitSuffixType) {
        this.filterLimitSuffixType = filterLimitSuffixType;
    }

    public boolean isFilterLimitMedia() {
        return filterLimitMedia;
    }

    public void setFilterLimitMedia(boolean filterLimitMedia) {
        this.filterLimitMedia = filterLimitMedia;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getLoadAnimation() {
        return loadAnimation;
    }

    public void setLoadAnimation(int loadAnimation) {
        this.loadAnimation = loadAnimation;
    }

    public boolean isStartPreviewByThird() {
        return startPreviewByThird;
    }

    public void setStartPreviewByThird(boolean startPreviewByThird) {
        this.startPreviewByThird = startPreviewByThird;
    }

    public MediaPickerThemeConfig getThemeConfig() {
        if (themeConfig == null) {
            themeConfig = new MediaPickerThemeConfig.Builder().buildDarkTheme();//默认浅色主题
        }
        return themeConfig;
    }

    public void setThemeConfig(MediaPickerThemeConfig themeConfig) {
        this.themeConfig = themeConfig;
    }

    public ImageEngine getImageEngine() {
        return engine;
    }

    public void setImageEngine(ImageEngine engine) {
        this.engine = engine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mediaPickerType.ordinal());
        dest.writeInt(maxSelectMediaCount);
        dest.writeLong(limitSize);
        dest.writeLong(limitDuration);
        dest.writeStringList(limitSuffixTypeList);
        dest.writeByte((byte) (filterLimitSuffixType ? 1 : 0));
        dest.writeByte((byte) (filterLimitMedia ? 1 : 0));
        dest.writeInt(columnCount);
        dest.writeInt(loadAnimation);
        dest.writeByte((byte) (startPreviewByThird ? 1 : 0));
        writeThemeConfig(dest);
    }

    /**
     * 写入主题配置
     *
     * @param dest 输入储存对象
     */
    private void writeThemeConfig(@NonNull Parcel dest) {
        dest.writeInt(themeConfig.getTopBackgroundColor());
        dest.writeInt(themeConfig.getBottomBackgroundColor());
        dest.writeInt(themeConfig.getTopTextColor());
        dest.writeInt(themeConfig.getBottomTextColor());
        dest.writeInt(themeConfig.getBackIconResId());
        dest.writeInt(themeConfig.getUpTriangleIconResId());
        dest.writeInt(themeConfig.getDownTriangleIconResId());
    }

    /**
     * 读取主题配置
     *
     * @param in 输入信息
     */
    private void readThemeConfig(@NonNull Parcel in) {
        themeConfig = new MediaPickerThemeConfig.Builder()
                .setTopBackgroundColor(in.readInt())
                .setBottomBackgroundColor(in.readInt())
                .setTopTextColor(in.readInt())
                .setBottomTextColor(in.readInt())
                .setBackIconResId(in.readInt())
                .setUpTriangleIconResId(in.readInt())
                .setDownTriangleIconResId(in.readInt())
                .build();
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private MediaPickerConfig mConfig;

        public Builder() {
            mConfig = new MediaPickerConfig();
        }

        public MediaPickerConfig build() {
            return mConfig;
        }

        public MediaPickerType getMediaPickerType() {
            return mConfig.mediaPickerType;
        }

        public Builder setMediaPickerType(MediaPickerType mediaPickerType) {
            mConfig.mediaPickerType = mediaPickerType;
            return this;
        }

        public int getMaxSelectMediaCount() {
            return mConfig.maxSelectMediaCount;
        }

        public Builder setMaxSelectMediaCount(int maxSelectMediaCount) {
            mConfig.maxSelectMediaCount = maxSelectMediaCount;
            return this;
        }

        public long getLimitSize() {
            return mConfig.limitSize;
        }

        public Builder setLimitSize(long limitSize) {
            mConfig.limitSize = limitSize;
            return this;
        }

        public long getLimitDuration() {
            return mConfig.limitDuration;
        }

        public Builder setLimitDuration(long limitDuration) {
            mConfig.limitDuration = limitDuration;
            return this;
        }

        public List<String> getLimitSuffixTypeList() {
            return mConfig.limitSuffixTypeList;
        }

        public Builder setLimitSuffixTypeList(List<String> limitSuffixTypeList) {
            mConfig.limitSuffixTypeList = limitSuffixTypeList;
            return this;
        }

        public MediaPickerFilter getMediaPickerFilter() {
            return mConfig.mediaPickerFilter;
        }

        public Builder setMediaPickerFilter(MediaPickerFilter mediaPickerFilter) {
            mConfig.mediaPickerFilter = mediaPickerFilter;
            return this;
        }

        public boolean isFilterLimitSuffixType() {
            return mConfig.filterLimitSuffixType;
        }

        public Builder setFilterLimitSuffixType(boolean filterLimitSuffixType) {
            mConfig.filterLimitSuffixType = filterLimitSuffixType;
            return this;
        }

        public boolean isFilterLimitMedia() {
            return mConfig.filterLimitMedia;
        }

        public Builder setFilterLimitMedia(boolean filterLimitMedia) {
            mConfig.filterLimitMedia = filterLimitMedia;
            return this;
        }

        public int getColumnCount() {
            return mConfig.columnCount;
        }

        public Builder setColumnCount(int columnCount) {
            mConfig.columnCount = columnCount;
            return this;
        }

        public int getLoadAnimation() {
            return mConfig.loadAnimation;
        }

        public Builder setLoadAnimation(int loadAnimation) {
            mConfig.loadAnimation = loadAnimation;
            return this;
        }

        public boolean isStartPreviewByThird() {
            return mConfig.startPreviewByThird;
        }

        public Builder setStartPreviewByThird(boolean startPreviewByThird) {
            mConfig.startPreviewByThird = startPreviewByThird;
            return this;
        }

        public MediaPickerThemeConfig getThemeConfig() {
            return mConfig.themeConfig;
        }

        public Builder setThemeConfig(MediaPickerThemeConfig themeConfig) {
            mConfig.themeConfig = themeConfig;
            return this;
        }

        public ImageEngine getImageEngine() {
            return mConfig.engine;
        }

        public Builder setImageEngine(ImageEngine engine) {
            mConfig.engine = engine;
            return this;
        }
    }
}
