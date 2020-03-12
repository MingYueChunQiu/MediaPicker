package com.mingyuechunqiu.mediapickerproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;
import com.mingyuechunqiu.mediapicker.feature.picker.MediaPicker;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.EXTRA_PICKED_MEDIA_LIST;
import static com.mingyuechunqiu.mediapicker.data.constants.Constants.MP_REQUEST_START_MEDIA_PICKER;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvTest = findViewById(R.id.tv_test);
//        final ArrayList<String> list = new ArrayList<>();
//        list.add(MediaSuffixType.ImageSuffixType.TYPE_PNG);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPicker.init(MainActivity.this)
//                        .setMediaPickerConfig(new MediaPickerConfig.Builder()
//                                .setThemeConfig(new MediaPickerThemeConfig.Builder()
//                                .buildDarkTheme())
//                                .setMediaPickerType(MediaPickerType.TYPE_VIDEO)
//                                .setLimitDuration(10 * 1000)
//                                .setLimitSize(10 * 1024 * 1024L)
//                                .setMaxSelectMediaCount(3)
//                                .setStartPreviewByThird(true)
//                                .setColumnCount(3)
//                                .setLimitSuffixTypeList(list)
//                                .setMediaPickerFilter(new MediaPickerFilter() {
//                                    @Override
//                                    public boolean filter(MediaInfo info) {
//                                        if (info.getSize() > 10 * 1024 * 1024L) {
//                                            return true;
//                                        }
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public String getFilteredHint() {
//                                        return "测试";
//                                    }
//
//                                    @Override
//                                    public boolean hideFiltered() {
//                                        return false;
//                                    }
//                                })
//                                .setFilterLimitSuffixType(true)
//                                .setFilterLimitMedia(true)
//                                .build())
                        .pick();
            }
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_test, new MainFragment())
                .commit();
    }

    private String getTmpDir(Activity activity) {
        String tmpDir = activity.getCacheDir() + "/uCrop";
        new File(tmpDir).mkdir();

        return tmpDir;
    }

    private void startCropping(final Activity activity, final Uri uri) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(100);
        options.setCircleDimmedLayer(false);
        options.setFreeStyleCropEnabled(true);
        options.setShowCropGrid(true);
        options.setShowCropFrame(true);
        options.setHideBottomControls(false);

//        if (cropperToolbarTitle != null) {
//            options.setToolbarTitle(cropperToolbarTitle);
//        }

        // UCropActivity.ALL = enable both rotation & scaling
        options.setAllowedGestures(
                UCropActivity.ALL, // When 'scale'-tab active
                UCropActivity.ALL, // When 'rotate'-tab active
                UCropActivity.ALL  // When 'aspect ratio'-tab active
        );

        UCrop uCrop = UCrop
                .of(uri, Uri.fromFile(new File(this.getTmpDir(activity), UUID.randomUUID().toString() + ".jpg")))
                .withOptions(options);

//        if (width > 0 && height > 0) {
//            uCrop.withAspectRatio(width, height);
//        }

        uCrop.start(activity);
    }

    private void croppingResult(Activity activity, final Intent data) {
        Uri resultUri = UCrop.getOutput(data);

        if (resultUri != null) {
            try {
                String path = resolveRealPath(activity, resultUri);
                Log.i(TAG, "croppingResult: path " + path);

                getCroppedRectMap(data);
            } catch (Exception ex) {
                Log.e(TAG, "croppingResult:E_NO_IMAGE_DATA_FOUND " + ex.getMessage());
            }
        } else {
            Log.i(TAG, "croppingResult:E_NO_IMAGE_DATA_FOUND Cannot find image data");
        }
    }


    private String resolveRealPath(Activity activity, Uri uri) throws IOException {
        return RealPathUtil.getRealPathFromURI(activity, uri);
    }

    private static void getCroppedRectMap(Intent data) {
        final int DEFAULT_VALUE = -1;

        Log.i(TAG, "getCroppedRectMap: x " + data.getIntExtra(UCrop.EXTRA_OUTPUT_OFFSET_X, DEFAULT_VALUE));
        Log.i(TAG, "getCroppedRectMap: y " + data.getIntExtra(UCrop.EXTRA_OUTPUT_OFFSET_Y, DEFAULT_VALUE));
        Log.i(TAG, "getCroppedRectMap: width " + data.getIntExtra(UCrop.EXTRA_OUTPUT_IMAGE_WIDTH, DEFAULT_VALUE));
        Log.i(TAG, "getCroppedRectMap: height " + data.getIntExtra(UCrop.EXTRA_OUTPUT_IMAGE_HEIGHT, DEFAULT_VALUE));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == MP_REQUEST_START_MEDIA_PICKER && resultCode == RESULT_OK) {
            ArrayList<MediaInfo> list = data.getParcelableArrayListExtra(EXTRA_PICKED_MEDIA_LIST);
            for (MediaInfo info : list) {
                Log.d("份", info.getTitle() + "   fds    " + info.getName() + " " + info.getFilePath() + " " +
                        info.getSize() + " " + info.getDuration() + " " + info.getBucketId() + " "
                        + info.getBucketName());
            }
            if (list.size() > 0) {
                MediaInfo mediaInfo = list.get(0);
                File dataFile = new File(mediaInfo.getFilePath());
                Uri uri;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    uri = Uri.fromFile(dataFile);
                } else {
                    uri = FileProvider.getUriForFile(this,
                            this.getApplicationContext().getPackageName() + ".provider",
                            dataFile);
                }
                startCropping(this, uri);
            }
        } else if (data != null && requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            croppingResult(this, data);
        }
    }
}
