package com.sanxiongdi.stopcar.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @desc :
 * @author: wuaomall@gmail.com
 * Created at 2016/11/21  17:39
 */

public      class CommonViewHolder {

       private int mPoaition;

       private SparseArray<View>  mViewSparseArray;

       private  View  commonView;


    private  CommonViewHolder (Context context, int poaition, int layoutID, ViewGroup mViewGroup){

        this.mPoaition=poaition;
        mViewSparseArray=new SparseArray<View>();
        commonView= LayoutInflater.from(context).inflate(layoutID,mViewGroup,false);
        commonView.setTag(this);

    }


    public   static  CommonViewHolder getInstance(Context mContext,int layoutID,int position,View mview,ViewGroup parent) {

         if (mview==null){
             return  new CommonViewHolder(mContext,position,layoutID,parent);
         }else {
             CommonViewHolder holder=(CommonViewHolder) mview.getTag();
             holder.mPoaition=position;
             return  holder;
         }
    }

    /**
     * @Description: 通过resourID获取item 中的view
     * @Author: wuaomall@gmail.com
     * creat at  2016/11/22 10:22
     */
    public  <T extends  View>T getView(int resourID){

        View view=mViewSparseArray.get(resourID);
           if (view==null){
               view=commonView.findViewById(resourID);
               mViewSparseArray.put(resourID,view   );
           }
        return  (T)view;

    }

    /**
     * @Description: 为text 填充内容
     * @Author: wuaomall@gmail.com
     * creat at  2016/11/22 10:25
     */
     public  CommonViewHolder settext(int resourID,CharSequence  text){
         ((TextView) getView(resourID)).setText(text);
         return  this;
     }
    public  CommonViewHolder settext(int resourID,int  text){
        ((TextView) getView(resourID)).setText(text);
        return  this;
    }

    /**
     * @Description: 为背景图片设置内容
     * @Author: wuaomall@gmail.com
     * creat at  2016/11/22 10:31
     */


    public  CommonViewHolder setBimap(int resourceID, Bitmap bm){
        ((ImageView)getView(resourceID)).setImageBitmap(bm);
        return  this;
    }
    public  CommonViewHolder setBimapDrawable(int resourceID, Drawable bm){
        ((ImageView)getView(resourceID)).setImageDrawable(bm);
        return  this;
    }
    public  CommonViewHolder setImageResourc(int resourceID, int bm){
        ((ImageView)getView(resourceID)).setImageResource(bm);
        return  this;
    }


      public  View getConverView(){
          return  commonView;
      }

     public  int getPosition(){
         return  mPoaition;
     }
}