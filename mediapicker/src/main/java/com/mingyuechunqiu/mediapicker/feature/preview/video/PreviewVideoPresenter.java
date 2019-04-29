package com.mingyuechunqiu.mediapicker.feature.preview.video;

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
 *     time   : 2019/4/28
 *     desc   : 预览视频MVP中P层
 *              继承自PreviewVideoContract.Presenter
 *     version: 1.0
 * </pre>
 */
class PreviewVideoPresenter extends PreviewVideoContract.Presenter<PreviewVideoContract.View> {

    @Override
    protected BasePreviewAdapter getPreviewAdapter(List<MediaAdapterItem> list, MediaPickerConfig config) {
        return new PreviewVideoAdapter(R.layout.mp_rv_preview_item, list, config.getMaxSelectMediaCount(),
                config.getLimitSize(), config.getLimitDuration(), new BaseMediaPickerAdapter.OnItemSelectChangedListener() {
            @Override
            public void onItemSelectChanged(boolean canConfirm, int selectedCount, int maxSelectedCount, MediaAdapterItem item) {
                updateItemSelected(selectedCount);
            }
        });
    }

    @Override
    public void release() {

    }
}
