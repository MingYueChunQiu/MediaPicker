package com.mingyuechunqiu.mediapickerproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.data.constants.MediaPickerType;
import com.mingyuechunqiu.mediapicker.feature.picker.MediaPicker;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.mingyuechunqiu.mediapicker.data.constants.Constants.EXTRA_PICKED_MEDIA_LIST;
import static com.mingyuechunqiu.mediapicker.data.constants.Constants.MP_REQUEST_START_MEDIA_PICKER;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/5/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        AppCompatButton btnTest = view.findViewById(R.id.btn_main);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPicker.init(MainFragment.this)
                        .setMediaPickerConfig(new MediaPickerConfig.Builder()
                                .setMediaPickerType(MediaPickerType.TYPE_VIDEO)
                                .build())
                        .pick();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == MP_REQUEST_START_MEDIA_PICKER && resultCode == RESULT_OK) {
            ArrayList<MediaInfo> list = data.getParcelableArrayListExtra(EXTRA_PICKED_MEDIA_LIST);
            for (MediaInfo info : list) {
                Log.d("份dfbdf", info.getTitle() + "   fds    " + info.getName() + " " + info.getFilePath() + " " +
                        info.getSize() + " " + info.getDuration() + " " + info.getBucketId() + " "
                        + info.getBucketName());
            }
        }
    }
}
