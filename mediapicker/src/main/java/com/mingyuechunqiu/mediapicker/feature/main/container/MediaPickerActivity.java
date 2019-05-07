package com.mingyuechunqiu.mediapicker.feature.main.container;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

import com.mingyuechunqiu.mediapicker.R;
import com.mingyuechunqiu.mediapicker.data.bean.MediaInfo;
import com.mingyuechunqiu.mediapicker.data.config.MediaPickerConfig;
import com.mingyuechunqiu.mediapicker.feature.main.detail.MediaPickerFragment;
import com.mingyuechunqiu.mediapicker.feature.main.detail.MediaPickerFragmentable;
import com.mingyuechunqiu.mediapicker.framework.MediaPickerCallback;
import com.mingyuechunqiu.mediapicker.ui.activity.BaseMediaPickerActivity;
import com.mingyuechunqiu.mediapicker.ui.fragment.BasePresenterFragment;
import com.mingyuechunqiu.mediapicker.util.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.mingyuechunqiu.mediapicker.data.constants.Constants.EXTRA_MEDIA_PICKER_CONFIG;
import static com.mingyuechunqiu.mediapicker.data.constants.Constants.EXTRA_PICKED_MEDIA_LIST;
import static com.mingyuechunqiu.mediapicker.data.constants.Constants.MP_HIDE_LOADING;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/23
 *     desc   : 多媒体选择界面
 *              继承自BaseMediaPickerActivity
 *     version: 1.0
 * </pre>
 */
public class MediaPickerActivity extends BaseMediaPickerActivity implements EasyPermissions.PermissionCallbacks, MediaPickerCallback, BasePresenterFragment.FragmentCallback {

    private static final String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private MediaPickerFragmentable mMediaPickerFgable;
    private MediaPickerLoadingFragment mLoadingFg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparentStatusAndControlNavigationLayout();
        setContentView(R.layout.mp_layout_frame);
        if (!EasyPermissions.hasPermissions(this, permissions)) {
            EasyPermissions.requestPermissions(this, getString(R.string.mp_warn_get_permissions_rationale), 1, permissions);
            finishActivity();
            return;
        }
        if (getIntent() != null) {
            initMediaPickerFragment((MediaPickerConfig) getIntent().getParcelableExtra(EXTRA_MEDIA_PICKER_CONFIG));
        }
        if (savedInstanceState != null) {
            mLoadingFg = (MediaPickerLoadingFragment) getSupportFragmentManager().findFragmentByTag(
                    MediaPickerLoadingFragment.class.getSimpleName());
        } else {
            mLoadingFg = new MediaPickerLoadingFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_mp_frame_layout_container, mLoadingFg,
                            MediaPickerLoadingFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPickerFgable = null;
        FragmentUtils.removeFragments(getSupportFragmentManager(),
                android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                mLoadingFg);
        mLoadingFg = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    /**
     * 设置状态栏透明，并控制底部虚拟导航栏出现与消失时的布局，确保显示内容不被其遮挡
     */
    protected void setTransparentStatusAndControlNavigationLayout() {
        //关键是这句还有if里面的两句
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initMediaPickerFragment(MediaPickerConfig config) {
        if (mMediaPickerFgable == null) {
            MediaPickerFragment fragment = MediaPickerFragment.newInstance(config);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_mp_frame_layout_container, fragment)
                    .commit();
            mMediaPickerFgable = fragment;
        }
    }

    @Override
    public void onConfirmMediaPicked(ArrayList<MediaInfo> list) {
        if (list == null || getIntent() == null) {
            return;
        }
        getIntent().putParcelableArrayListExtra(EXTRA_PICKED_MEDIA_LIST, list);
        setResult(RESULT_OK, getIntent());
        finishActivity();
    }

    @Override
    public void onCall(Fragment fragment, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (bundle.getBoolean(MP_HIDE_LOADING)) {
            removeFragments(mLoadingFg);
            mLoadingFg = null;
        }
    }

    public void finishActivity() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void removeFragments(Fragment... fragments) {
        FragmentUtils.removeFragments(getSupportFragmentManager(),
                android.R.anim.fade_in, android.R.anim.fade_out,
                fragments);
    }
}
