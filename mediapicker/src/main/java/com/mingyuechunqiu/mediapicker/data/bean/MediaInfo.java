package com.mingyuechunqiu.mediapicker.data.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.mingyuechunqiu.mediapicker.data.constants.MediaPickerType;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/24
 *     desc   : 多媒体信息类
 *     version: 1.0
 * </pre>
 */
public class MediaInfo implements Parcelable {

    private String title;//标题
    private String name;//名称（带扩展名）
    private MediaPickerType type;//多媒体类型
    private String suffixType;//后缀名类型（例如：.mp4)
    private String filePath;//视频路径
    private String thumbnail;//缩略图
    private long addDate;//添加到Media Provider的时间
    private long duration;//时长
    private long size;//大小
    private String bucketId;//多媒体所属文件夹ID
    private String bucketName;//多媒体所属文件夹名称

    public MediaInfo() {
    }

    protected MediaInfo(@NonNull Parcel in) {
        title = in.readString();
        name = in.readString();
        type = MediaPickerType.values()[in.readInt()];
        suffixType = in.readString();
        filePath = in.readString();
        thumbnail = in.readString();
        addDate = in.readLong();
        duration = in.readLong();
        size = in.readLong();
        bucketId = in.readString();
        bucketName = in.readString();
    }

    public static final Creator<MediaInfo> CREATOR = new Creator<MediaInfo>() {
        @Override
        public MediaInfo createFromParcel(Parcel in) {
            return new MediaInfo(in);
        }

        @Override
        public MediaInfo[] newArray(int size) {
            return new MediaInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MediaPickerType getType() {
        return type;
    }

    public void setType(MediaPickerType type) {
        this.type = type;
    }

    public String getSuffixType() {
        return suffixType;
    }

    public void setSuffixType(String suffixType) {
        this.suffixType = suffixType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getAddDate() {
        return addDate;
    }

    public void setAddDate(long addDate) {
        this.addDate = addDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(name);
        dest.writeInt(type.ordinal());
        dest.writeString(suffixType);
        dest.writeString(filePath);
        dest.writeString(thumbnail);
        dest.writeLong(addDate);
        dest.writeLong(duration);
        dest.writeLong(size);
        dest.writeString(bucketId);
        dest.writeString(bucketName);
    }

    /**
     * 链式调用
     */
    public static class Builder {

        private MediaInfo mInfo;

        public Builder() {
            mInfo = new MediaInfo();
        }

        public MediaInfo build() {
            return mInfo;
        }

        public String getTitle() {
            return mInfo.title;
        }

        public Builder setTitle(String title) {
            mInfo.title = title;
            return this;
        }

        public String getName() {
            return mInfo.name;
        }

        public Builder setName(String name) {
            mInfo.name = name;
            return this;
        }

        public MediaPickerType getType() {
            return mInfo.type;
        }

        public Builder setType(MediaPickerType type) {
            mInfo.type = type;
            return this;
        }

        public String getSuffixType() {
            return mInfo.suffixType;
        }

        public Builder setSuffixType(String suffixType) {
            mInfo.suffixType = suffixType;
            return this;
        }

        public String getFilePath() {
            return mInfo.filePath;
        }

        public Builder setFilePath(String filePath) {
            mInfo.filePath = filePath;
            return this;
        }

        public String getThumbnail() {
            return mInfo.thumbnail;
        }

        public Builder setThumbnail(String thumbnail) {
            mInfo.thumbnail = thumbnail;
            return this;
        }

        public long getAddDate() {
            return mInfo.addDate;
        }

        public Builder setAddDate(long addDate) {
            mInfo.addDate = addDate;
            return this;
        }

        public long getDuration() {
            return mInfo.duration;
        }

        public Builder setDuration(long duration) {
            mInfo.duration = duration;
            return this;
        }

        public long getSize() {
            return mInfo.size;
        }

        public Builder setSize(long size) {
            mInfo.size = size;
            return this;
        }

        public String getBucketId() {
            return mInfo.bucketId;
        }

        public Builder setBucketId(String bucketId) {
            mInfo.bucketId = bucketId;
            return this;
        }

        public String getBucketName() {
            return mInfo.bucketName;
        }

        public Builder setBucketName(String bucketName) {
            mInfo.bucketName = bucketName;
            return this;
        }
    }
}
