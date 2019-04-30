package com.mingyuechunqiu.mediapicker.feature.main.detail;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.mingyuechunqiu.mediapicker.R;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/30
 *     desc   : 多媒体所属文件夹下拉列表适配器
 *              继承自BaseAdapter
 *     version: 1.0
 * </pre>
 */
class BucketSpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private List<BucketAdapterItem> mList;

    BucketSpinnerAdapter(Context context, List<BucketAdapterItem> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.mp_sp_bucket_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tv_mp_sp_bucket_item_name);
            viewHolder.rbSelected = convertView.findViewById(R.id.rb_mp_sp_bucket_item_selected);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mList != null && mList.get(position) != null) {
            viewHolder.tvName.setText(mList.get(position).bucketName);
            viewHolder.rbSelected.setChecked(mList.get(position).selected);
        }
        return convertView;
    }

    private static class ViewHolder {

        private AppCompatTextView tvName;
        private RadioButton rbSelected;
    }

    static class BucketAdapterItem {

        private String bucketId;//多媒体所属文件夹ID
        private String bucketName;//多媒体所属文件夹名称
        private boolean selected;//是否被选中

        public String getBucketId() {
            return bucketId;
        }

        public void setBucketId(String bucketId) {
            this.bucketId = bucketId;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
