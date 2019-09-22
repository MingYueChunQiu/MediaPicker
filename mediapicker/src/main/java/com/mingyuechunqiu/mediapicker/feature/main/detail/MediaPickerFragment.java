package com.mingyuechunqiu.mediapicker.feature.main.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaAdapterItem;
import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.data.constants.Constants;
import com.mingyuechunqiu.mediapicker.data.constants.MediaPickerType;
import com.mingyuechunqiu.mediapicker.feature.main.container.MediaPickerActivity;
import com.mingyuechunqiu.mediapicker.feature.preview.audio.PreviewAudioDialogFragment;
import com.mingyuechunqiu.mediapicker.feature.preview.image.PreviewImageFragment;
import com.mingyuechunqiu.mediapicker.feature.preview.video.play.PlayVideoFragment;
import com.mingyuechunqiu.mediapicker.feature.preview.video.preview.PreviewVideoFragment;
import com.mingyuechunqiu.mediapicker.framework.KeyBackCallback;
import com.mingyuechunqiu.mediapicker.framework.MediaPickerCallback;
import com.mingyuechunqiu.mediapicker.ui.fragment.BasePresenterFragment;
import com.mingyuechunqiu.mediapicker.ui.fragment.BasePreviewFragment;
import com.mingyuechunqiu.mediapicker.util.FragmentUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.MP_HIDE_LOADING;
import static com.mingyuechunqiu.mediapicker.feature.preview.video.play.PlayVideoFragment.BUNDLE_VIDEO_FILE_PATH;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择器界面
 *              继承自BasePresenterFragment
 *     version: 1.0
 * </pre>
 */
public class MediaPickerFragment extends BasePresenterFragment<MediaPickerContract.View<MediaPickerContract.Presenter>, MediaPickerContract.Presenter>
        implements MediaPickerFragmentable, MediaPickerContract.View<MediaPickerContract.Presenter>, BasePresenterFragment.FragmentCallback {

    private RecyclerView rvList;

    private MediaPickerConfig mConfig;
    private boolean beInPreview;//标记是否处于预览状态
    private PreviewImageFragment mPreviewImageFg;
    private PreviewVideoFragment mPreviewVideoFg;
    private PlayVideoFragment mPlayVideoFg;
    private List<MediaAdapterItem> mAllData;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setStatusBar();
        final View view = inflater.inflate(R.layout.mp_fragment_media_picker, container, false);
        final Toolbar toolbar = view.findViewById(R.id.tb_mp_media_picker_bar);
        final AppCompatTextView tvConfirm = view.findViewById(R.id.tv_mp_media_picker_confirm);
        rvList = view.findViewById(R.id.rv_mp_media_picker_list);
        final AppCompatTextView tvBucket = view.findViewById(R.id.tv_mp_media_picker_bucket);
        final View vBucket = view.findViewById(R.id.v_mp_media_picker_bucket_container);

        tvBucket.setTextColor(mConfig.getThemeConfig().getBottomTextColor());
        vBucket.setBackgroundColor(mConfig.getThemeConfig().getBottomBackgroundColor());
        mPresenter.setBucketViewDrawableBounds(tvBucket, mConfig.getThemeConfig().getUpTriangleIconResId());
        tvBucket.setText(mPresenter.getBucketName(getContext()));
        tvBucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.handleOnClickBucketName(getContext(), v, tvBucket, vBucket, rvList);
            }
        });

        mPresenter.initToolbar(getActivity(), toolbar);
        tvConfirm.setEnabled(false);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvList != null && rvList.getAdapter() instanceof MediaPickerMainAdapter) {
                    List<MediaInfo> list = ((MediaPickerMainAdapter) rvList.getAdapter()).getSelectedMediaList();
                    if (getActivity() instanceof MediaPickerCallback) {
                        ((MediaPickerCallback) getActivity()).onConfirmMediaPicked((ArrayList<MediaInfo>) list);
                    }
                }
            }
        });
        mPresenter.initMediaItemList(rvList, tvConfirm);

        MediaPickerMainViewModel viewModel = ViewModelProviders.of(this).get(MediaPickerMainViewModel.class);
        viewModel.getSelectedCount().removeObservers(this);
        viewModel.getSelectedCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                updateItemSelectedStatus(integer, tvConfirm);
            }
        });
        if (getActivity() instanceof KeyBackCallback) {
            ((KeyBackCallback) getActivity()).addOnKeyBackListener(new KeyBackCallback.OnKeyBackListener() {
                @Override
                public boolean onClickKeyBack(KeyEvent event) {
                    if (mPlayVideoFg != null) {
                        removePlayVideoFragment();
                        return true;
                    }
                    //如果有预览界面，先清除预览界面
                    handleBack();
                    return true;
                }
            });
        }
        return view;
    }

    @Override
    protected MediaPickerContract.Presenter<MediaPickerContract.View> initPresenter() {
        return new MediaPickerPresenter(mConfig);
    }

    @Override
    public void setPresenter(@NonNull MediaPickerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void releaseOnDestroyView() {
        if (mAllData != null) {
            mAllData.clear();
            mAllData = null;
        }
        mConfig = null;
        removeAllPreviewFragments();
        removePlayVideoFragment();
    }

    @Override
    protected void releaseOnDestroy() {

    }

    @Override
    public void handleBack() {
        removePlayVideoFragment();
        if (beInPreview) {
            removeAllPreviewFragments();
        } else {
            if (getActivity() instanceof MediaPickerActivity) {
                ((MediaPickerActivity) getActivity()).finishActivity();
            }
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() instanceof FragmentCallback) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(MP_HIDE_LOADING, true);
            ((FragmentCallback) getActivity()).onCall(this, bundle);
        }
    }

    @Override
    public void showPreview(List<MediaAdapterItem> list, int index, MediaPickerType type) {
        if (list == null) {
            return;
        }
        BasePreviewFragment fragment = null;
        switch (type) {
            case TYPE_IMAGE:
                fragment = mPreviewImageFg = PreviewImageFragment.newInstance(list, index);
                break;
            case TYPE_AUDIO:
//                if (mConfig.isStartPreviewByThird()) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(Uri.parse(list.get(index).getInfo().getFilePath()), "audio/*");
//                    startActivity(intent);
//                    return;
//                }
                showPreviewAudio(list, index);
                break;
            case TYPE_VIDEO:
                if (mConfig.isStartPreviewByThird()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(list.get(index).getInfo().getFilePath()), "video/*");
                    startActivity(intent);
                    return;
                }
                fragment = mPreviewVideoFg = PreviewVideoFragment.newInstance(list, index);
                break;
            default:
                break;
        }
        showPreviewFragment(fragment);
    }

    @Override
    public void onCall(Fragment fragment, Bundle bundle) {
        if (bundle != null && !TextUtils.isEmpty(bundle.getString(BUNDLE_VIDEO_FILE_PATH))) {
            mPlayVideoFg = PlayVideoFragment.newInstance(bundle.getString(BUNDLE_VIDEO_FILE_PATH));
            getChildFragmentManager().beginTransaction()
                    .add(R.id.fl_mp_media_picker_container, mPlayVideoFg)
                    .commitAllowingStateLoss();
        }
    }

    public static MediaPickerFragment newInstance(MediaPickerConfig config) {
        MediaPickerFragment fragment = new MediaPickerFragment();
        fragment.mConfig = config;
        if (fragment.mConfig == null) {
            fragment.mConfig = new MediaPickerConfig();
        }
        return fragment;
    }

    /**
     * 更新Item被选中状态
     *
     * @param integer   已选中的Item数量
     * @param tvConfirm 确认控件
     */
    private void updateItemSelectedStatus(@Nullable Integer integer, AppCompatTextView tvConfirm) {
        if (integer == null || mConfig == null) {
            return;
        }
        mPresenter.handelConfirmView(integer != 0, integer,
                mConfig.getMaxSelectMediaCount(), new WeakReference<>(tvConfirm));
        if (rvList != null && rvList.getAdapter() != null) {
            rvList.getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * 显示预览音频界面
     *
     * @param list  多媒体数据列表
     * @param index 数据在列表中的索引位置
     */
    private void showPreviewAudio(List<MediaAdapterItem> list, int index) {
        if (list == null || index < 0 || index > list.size() - 1 || getFragmentManager() == null) {
            return;
        }
        PreviewAudioDialogFragment dialogFragment = PreviewAudioDialogFragment.newInstance(list.get(index).getInfo());
        dialogFragment.show(getFragmentManager(), PreviewAudioDialogFragment.class.getSimpleName());
    }

    /**
     * 显示预览界面
     *
     * @param fragment 预览界面
     */
    private void showPreviewFragment(BasePreviewFragment fragment) {
        if (fragment == null) {
            return;
        }
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.mp_scale_in_magnify, R.anim.mp_scale_out_shrink)
                .add(R.id.fl_mp_media_picker_preview_container, fragment)
                .commitAllowingStateLoss();
        beInPreview = true;
    }

    /**
     * 移除所有预览界面
     */
    private void removeAllPreviewFragments() {
        if (!beInPreview) {
            return;
        }
        removePreviewFragment(mPreviewImageFg, mPreviewVideoFg);
        mPreviewImageFg = null;
        mPreviewVideoFg = null;
    }

    /**
     * 移除预览界面
     *
     * @param fragments 要被移除的界面
     */
    private void removePreviewFragment(Fragment... fragments) {
        FragmentUtils.removeFragments(getChildFragmentManager(),
                R.anim.mp_scale_in_magnify, R.anim.mp_scale_out_shrink, fragments);
        beInPreview = false;
    }

    private void removePlayVideoFragment() {
        FragmentUtils.removeFragments(getChildFragmentManager(),
                android.R.anim.fade_in, android.R.anim.fade_out, mPlayVideoFg);
        mPlayVideoFg = null;
        //从播放视频界面回来时要移除全屏效果
        if (getActivity() != null) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 设置状态栏
     */
    private void setStatusBar() {
        if (mConfig == null) {
            mConfig = new MediaPickerConfig();
        }
        if (mConfig.getThemeConfig().getThemeType() == Constants.ThemeTypeConstants.TYPE_LIGHT) {
            setDarkStatusBar();
        } else {
            setLightStatusBar();
        }
    }

    /**
     * 设置状态栏为轻色调，避免白色字体被白色活动条遮挡
     */
    private void setLightStatusBar() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 设置状态栏为深色调，避免黑色字体被白色活动条遮挡
     */
    private void setDarkStatusBar() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
}
