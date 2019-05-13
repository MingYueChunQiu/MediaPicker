package com.mingyuechunqiu.mediapickerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;
import com.mingyuechunqiu.mediapicker.feature.picker.MediaPicker;

import java.util.ArrayList;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.EXTRA_PICKED_MEDIA_LIST;
import static com.mingyuechunqiu.mediapicker.data.constants.Constants.MP_REQUEST_START_MEDIA_PICKER;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvTest = findViewById(R.id.tv_test);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPicker.init(MainActivity.this)
//                        .setMediaPickerType(MediaPickerType.TYPE_IMAGE)
//                        .setMaxSelectMediaCount(4)
//                        .setThemeConfig(new MediaPickerThemeConfig.Builder().buildDarkTheme())
//                        .setLimitSize(3 * 1024 * 1024)
                        .pick();
            }
        });
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
        }
    }
}
