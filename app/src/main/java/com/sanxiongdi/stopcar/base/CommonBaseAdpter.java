//package com.sanxiongdi.stopcar.base;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.sanxiongdi.stopcar.R;
//
//import java.util.List;
//
///**
// * @desc :基础BaseAdpter的基类
// * @author: wuaomall@gmail.com
// * Created at 2016/11/2  14:50
// */
//
//public  abstract class CommonBaseAdpter<T>   extends BaseAdapter {
//
//      private Context mContext;
//      private List<T> mData;
//
//     public  CommonBaseAdpter(Context mcontext,List<T> datas){
//         this.mContext=mcontext;
//         this.mData=datas;
//     }
//
//
//
//    @Override
//    public int getCount() {
//        return mData.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mData.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//      CommonViewHolder commonViewHolder=CommonViewHolder.getInstance(mContext, R.layout.listview_item,position,convertView,parent);
//      convert(commonViewHolder,mData.get(position));
//        return commonViewHolder.getConverView();
//    }
//
//    /**
//     * 填充holder里面控件的数据
//     * @param holder
//     * @param bean
//     */
//    public abstract void convert(CommonViewHolder holder,T bean);
//}
