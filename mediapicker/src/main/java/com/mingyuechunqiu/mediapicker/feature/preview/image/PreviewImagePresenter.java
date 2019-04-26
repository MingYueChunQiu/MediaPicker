package com.mingyuechunqiu.mediapicker.feature.preview.image;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.feature.main.detail.MediaPickerMainViewModel;
import com.mingyuechunqiu.mediapicker.feature.picker.MediaPicker;
import com.mingyuechunqiu.mediapicker.ui.adapter.BaseMediaPickerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class PreviewImagePresenter extends PreviewImageContract.Presenter<PreviewImageContract.View> {

    @Override
    public void release() {

    }

    @Override
    void initPreviewImageList(RecyclerView rvList, List<MediaAdapterItem> list, int index) {
        if (rvList == null) {
            return;
        }
        rvList.setLayoutManager(new LinearLayoutManager(mViewRef.get().getCurrentContext(), LinearLayoutManager.HORIZONTAL, false));
        if (list == null) {
            list = new ArrayList<>();
        }
        MediaPickerConfig config = MediaPicker.INSTANCE.getMediaPickerControl().getMediaPickerStore().getMediaPickerConfig();
        rvList.setAdapter(new PreviewImageAdapter(R.layout.mp_rv_preview_image_item, list, config.getMaxSelectMediaCount(),
                config.getLimitSize(), config.getLimitDuration(), new BaseMediaPickerAdapter.OnItemSelectChangedListener() {
            @Override
            public void onItemSelectChanged(boolean canConfirm, int selectedCount, int maxSelectedCount, MediaAdapterItem item) {
                ViewModelProviders.of(mViewRef.get().getCurrentParentFragment())
                        .get(MediaPickerMainViewModel.class)
                        .getSelectedCount().setValue(selectedCount);
            }
        }));
        new PagerSnapHelper().attachToRecyclerView(rvList);
        if (index < 0 || index > list.size()) {
            index = 0;
        }
        rvList.scrollToPosition(index);
    }
}
