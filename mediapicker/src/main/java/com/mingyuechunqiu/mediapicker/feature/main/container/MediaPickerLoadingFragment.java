package com.mingyuechunqiu.mediapicker.feature.main.container;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mingyuechunqiu.mediapicker.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/25
 *     desc   : 多媒体选择器等待加载界面
 *              继承自Fragment
 *     version: 1.0
 * </pre>
 */
public class MediaPickerLoadingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mp_fragment_loading, container, false);
    }
}
