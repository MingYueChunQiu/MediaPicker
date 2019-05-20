package com.mingyuechunqiu.mediapicker.feature.preview.image;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.ui.adapter.BaseMediaPickerAdapter;
import com.mingyuechunqiu.mediapicker.ui.adapter.BasePreviewAdapter;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/25
 *     desc   : 预览图片MVP中P层
 *              继承自PreviewImageContract.Presenter
 *     version: 1.0
 * </pre>
 */
class PreviewImagePresenter extends PreviewImageContract.Presenter<PreviewImageContract.View> {

    @Override
    public void release() {

    }

    @Override
    protected BasePreviewAdapter getPreviewAdapter(List<MediaAdapterItem> list, MediaPickerConfig config) {
        return new PreviewImageAdapter(R.layout.mp_rv_preview_item, list, new BaseMediaPickerAdapter.OnItemSelectChangedListener() {
            @Override
            public void onItemSelectChanged(boolean canConfirm, int selectedCount, int maxSelectedCount, MediaAdapterItem item) {
                updateItemSelected(selectedCount);
            }
        });
    }
}
