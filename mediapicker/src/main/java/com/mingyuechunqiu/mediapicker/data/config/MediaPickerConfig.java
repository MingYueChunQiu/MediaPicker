package com.mingyuechunqiu.mediapicker.data.config;

import android.os.Parcel;
import android.os.Parcelable;

import com.mingyuechunqiu.mediapicker.data.constants.MediaPickerType;
import com.mingyuechunqiu.mediapicker.feature.engine.GlideEngine;
import com.mingyuechunqiu.mediapicker.framework.ImageEngine;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.SET_INVALID;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择器配置项
 *     version: 1.0
 * </pre>
 */
public class MediaPickerConfig implements Parcelable {

    private MediaPickerType mediaPickerType;//多媒体选择类型

    private int maxSelectMediaCount;//最多可选择多媒体个数

    private long limitSize;//限制大小（单位B）

    private long limitDuration;//限制时长（毫秒）

    private boolean filterLimitMedia;//是否过滤超出限制的多媒体信息

    private int columnCount;//一行列数

    private ImageEngine engine;

    public MediaPickerConfig() {
        mediaPickerType = MediaPickerType.TYPE_IMAGE;
        maxSelectMediaCount = 1;
        limitSize = SET_INVALID;
        limitDuration = SET_INVALID;
        columnCount = 4;
        engine = new GlideEngine();
    }

    protected MediaPickerConfig(Parcel in) {
        mediaPickerType = MediaPickerType.values()[in.readInt()];
        maxSelectMediaCount = in.readInt();
        limitSize = in.readLong();
        limitDuration = in.readLong();
        filterLimitMedia = in.readByte() != 0;
        columnCount = in.readInt();
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
        dest.writeByte((byte) (filterLimitMedia ? 1 : 0));
        dest.writeInt(columnCount);
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

        public ImageEngine getImageEngine() {
            return mConfig.engine;
        }

        public Builder setImageEngine(ImageEngine engine) {
            mConfig.engine = engine;
            return this;
        }
    }
}
