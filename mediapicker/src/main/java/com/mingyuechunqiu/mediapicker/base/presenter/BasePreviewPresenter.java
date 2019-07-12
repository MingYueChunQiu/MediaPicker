package com.mingyuechunqiu.mediapicker.base.presenter;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mingyuechunqiu.mediapicker.base.view.IPreviewView;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.feature.main.detail.MediaPickerMainViewModel;
import com.mingyuechunqiu.mediapicker.feature.picker.MediaPicker;
import com.mingyuechunqiu.mediapicker.ui.adapter.BasePreviewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/28
 *     desc   : 预览功能Presenter层的抽象基类
 *              继承自BaseAbstractPresenter
 *     version: 1.0
 * </pre>
 */
public abstract class BasePreviewPresenter<V extends IPreviewView> extends BaseAbstractPresenter<V> {

    public void initPreviewImageList(RecyclerView rvList, List<MediaAdapterItem> list, int index) {
        if (rvList == null) {
            return;
        }
        rvList.setLayoutManager(new LinearLayoutManager(mViewRef.get().getCurrentContext(), LinearLayoutManager.HORIZONTAL, false));
        if (list == null) {
            list = new ArrayList<>();
        }
        rvList.setAdapter(getPreviewAdapter(list,
                MediaPicker.getInstance().getMediaPickerControl().getMediaPickerStore().getMediaPickerConfig()));
        new PagerSnapHelper().attachToRecyclerView(rvList);
        if (index < 0 || index > list.size()) {
            index = 0;
        }
        rvList.scrollToPosition(index);
    }

    /**
     * 更新Item选中情况
     *
     * @param selectedCount 已选中的Item个数
     */
    protected void updateItemSelected(int selectedCount) {
        ViewModelProviders.of(mViewRef.get().getCurrentParentFragment())
                .get(MediaPickerMainViewModel.class)
                .getSelectedCount().setValue(selectedCount);
    }

    protected abstract BasePreviewAdapter getPreviewAdapter(List<MediaAdapterItem> list, MediaPickerConfig config);
}
