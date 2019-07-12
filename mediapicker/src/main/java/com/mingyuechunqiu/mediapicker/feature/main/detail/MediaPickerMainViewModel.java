package com.mingyuechunqiu.mediapicker.feature.main.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MediaPickerMainViewModel extends ViewModel {

    private MutableLiveData<Integer> mSelectedCount;//已选择的Item数量

    public MutableLiveData<Integer> getSelectedCount() {
        if (mSelectedCount == null) {
            mSelectedCount = new MutableLiveData<>();
        }
        return mSelectedCount;
    }
}
