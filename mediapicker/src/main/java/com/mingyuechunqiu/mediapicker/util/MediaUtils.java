package com.mingyuechunqiu.mediapicker.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;
import com.mingyuechunqiu.mediapicker.data.constants.MediaPickerType;

import java.lang.reflect.Field;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.SET_INVALID;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/06/13
 *     desc   : 多媒体工具类
 *     version: 1.0
 * </pre>
 */
public class MediaUtils {

    /**
     * 启动选择本地图片界面
     *
     * @param activity    启动界面
     * @param requestCode 启动请求码
     */
    public static void startPickImage(@NonNull Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), requestCode);
    }

    /**
     * 启动选择本地图片界面
     *
     * @param fragment    启动界面
     * @param requestCode 启动请求码
     */
    public static void startPickImage(@NonNull Fragment fragment, int requestCode) {
        fragment.startActivityForResult(getPickImageIntent(), requestCode);
    }

    /**
     * 启动选择本地视频界面
     *
     * @param activity    启动界面
     * @param requestCode 启动请求码
     */
    public static void startPickVideo(@NonNull Activity activity, int requestCode) {
        activity.startActivityForResult(getPickVideoIntent(), requestCode);
    }

    /**
     * 启动选择本地视频界面
     *
     * @param fragment    启动界面
     * @param requestCode 启动请求码
     */
    public static void startPickVideo(@NonNull Fragment fragment, int requestCode) {
        fragment.startActivityForResult(getPickVideoIntent(), requestCode);
    }

    /**
     * 查询系统数据库地址中视频信息
     *
     * @param resolver Android组件
     * @param uri      视频本地地址
     * @return 如果成功获取数据，则返回MediaInfo，否则返回null
     */
    @Nullable
    public static MediaInfo queryVideoInfo(@NonNull ContentResolver resolver, @NonNull Uri uri) {
        Cursor cursor = resolver.query(uri, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            MediaInfo info = new MediaInfo();
            info.setName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)));
            info.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
            info.setThumbnail(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
            info.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));
            info.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));
            cursor.close();
            return info;
        }
        return null;
    }

    /**
     * 根据缩略图路径获取缩略图
     *
     * @param path 缩略图路径
     * @return 返回生成的缩略图
     */
    public static Bitmap getThumbnail(String path) {
        return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND);
    }

    /**
     * 设置视频播放声音
     *
     * @param volume 声音音量（0--1）
     * @param o      播放的对象
     */
    public static void setVolume(float volume, Object o) {
        if (volume < 0 || volume > 1 || o == null) {
            return;
        }
        try {
            Class c = Class.forName("android.widget.VideoView");
            Field field = c.getDeclaredField("mMediaPlayer");
            field.setAccessible(true);
            MediaPlayer mediaPlayer = (MediaPlayer) field.get(o);
            mediaPlayer.setVolume(volume, volume);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片资源
     *
     * @param context  上下文
     * @param callback 浏览资源回调
     */
    public static void getImages(final Context context, BrowseMediaInfoCallback callback) {
        if (context == null || callback == null) {
            return;
        }
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns.TITLE, MediaStore.Images.ImageColumns.DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.SIZE, MediaStore.Images.ImageColumns.BUCKET_ID,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME
        };
        Cursor cursor = contentResolver.query(imageUri, projection, null, null, null);
        if (cursor == null) {
            return;
        }
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                String fileName =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                long addDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                String bucketName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));

                callback.onBrowseMediaInfo(newMediaInfo(title, fileName, MediaPickerType.TYPE_IMAGE,
                        path, addDate, size, SET_INVALID, bucketId, bucketName));
            }
        }
        cursor.close();
    }

    /**
     * 获取音频资源
     *
     * @param context  上下文
     * @param callback 浏览资源回调
     */
    public static void getAudios(final Context context, BrowseMediaInfoCallback callback) {
        if (context == null || callback == null) {
            return;
        }
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        String[] projection = new String[]{
                MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.DISPLAY_NAME,
                MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.DATE_ADDED,
                MediaStore.Audio.AudioColumns.SIZE, MediaStore.Audio.AudioColumns.DURATION,
        };
        Cursor cursor = contentResolver.query(audioUri, projection, null, null, null);
        if (cursor == null) {
            return;
        }
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String fileName =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                long addDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                callback.onBrowseMediaInfo(newMediaInfo(title, fileName, MediaPickerType.TYPE_AUDIO,
                        path, addDate, size, duration, null, null));
            }
        }
        cursor.close();
    }

    /**
     * 获取视频资源
     *
     * @param context  上下文
     * @param callback 浏览资源回调
     */
    public static void getVideos(final Context context, BrowseMediaInfoCallback callback) {
        if (context == null || callback == null) {
            return;
        }
        Uri audioUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        String[] projection = new String[]{
                MediaStore.Video.VideoColumns.TITLE, MediaStore.Video.VideoColumns.DISPLAY_NAME,
                MediaStore.Video.VideoColumns.DATA, MediaStore.Video.VideoColumns.DATE_ADDED,
                MediaStore.Video.VideoColumns.SIZE, MediaStore.Video.VideoColumns.DURATION,
                MediaStore.Video.VideoColumns.BUCKET_ID, MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME
        };
        Cursor cursor = contentResolver.query(audioUri, projection, null, null, null);
        if (cursor == null) {
            return;
        }
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String fileName =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                long addDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String buckedId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));
                String bucketName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));

                callback.onBrowseMediaInfo(newMediaInfo(title, fileName, MediaPickerType.TYPE_VIDEO,
                        path, addDate, size, duration, buckedId, bucketName));
            }
        }
        cursor.close();
    }

    @NonNull
    private static Intent getPickImageIntent() {
        return new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    /**
     * 获取选择视频的启动意图
     *
     * @return 返回启动意图
     */
    @NonNull
    private static Intent getPickVideoIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        //必须加类型，否则会连图片也一起查找到
        intent.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, MediaStore.Video.Media.CONTENT_TYPE);
        return intent;
    }

    /**
     * 创建新的多媒体数据对象
     *
     * @param title      标题
     * @param fileName   文件名
     * @param type       多媒体类型
     * @param filePath   文件路径
     * @param addDate    添加到多媒体库时间
     * @param size       文件大小
     * @param duration   文件时长
     * @param buckedId   文件所属文件夹ID
     * @param bucketName 文件所属文件夹名称
     * @return 返回生成的多媒体数据对象
     */
    @NonNull
    private static MediaInfo newMediaInfo(String title, String fileName, MediaPickerType type, String filePath,
                                          long addDate, long size, long duration, String buckedId, String bucketName) {
        MediaInfo info = new MediaInfo();
        info.setTitle(title);
        info.setName(fileName);
        info.setType(type);
        info.setFilePath(filePath);
        info.setAddDate(addDate);
        info.setSize(size);
        info.setDuration(duration);
        info.setBucketId(buckedId);
        info.setBucketName(bucketName);
        return info;
    }

    /**
     * 浏览多媒体信息回调
     */
    public interface BrowseMediaInfoCallback {

        /**
         * 浏览多媒体资源信息时调用
         *
         * @param info 多媒体信息数据
         */
        void onBrowseMediaInfo(@NonNull MediaInfo info);
    }
}
