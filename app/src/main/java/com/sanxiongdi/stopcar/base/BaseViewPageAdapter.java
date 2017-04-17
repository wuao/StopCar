package com.sanxiongdi.stopcar.base;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 *
 * Created by wuaomall@gmail.com on 2017/4/10.
 */

public     class BaseViewPageAdapter extends PagerAdapter {


    /**
     * 获取View的总数
     *
     * @return View总数
     */
    @Override
    public int getCount() {
        return 0;
    }

    /**
     * 当ViewPager的内容有所变化时,进行调用。
     *
     * @param container ViewPager本身
     */
    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    /**
     * 为给定的位置创建相应的View。创建View之后,需要在该方法中自行添加到container中。
     *
     * @param container ViewPager本身
     * @param position  给定的位置
     * @return 提交给ViewPager进行保存的实例对象
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    /**
     * 为给定的位置移除相应的View。
     *
     * @param container ViewPager本身
     * @param position  给定的位置
     * @param object    在instantiateItem中提交给ViewPager进行保存的实例对象
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    /**
     * ViewPager调用该方法来通知PageAdapter当前ViewPager显示的主要项,提供给用户对主要项进行操作的方法。
     *
     * @param container ViewPager本身
     * @param position  给定的位置
     * @param object    在instantiateItem中提交给ViewPager进行保存的实例对象
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    /**
     * 当ViewPager的内容变化结束时,进行调用。当该方法被调用时,必须确定所有的操作已经结束。
     *
     * @param container ViewPager本身
     */
    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    /**
     * 确认View与实例对象是否相互对应。ViewPager内部用于获取View对应的ItemInfo。
     *
     * @param view   ViewPager显示的View内容
     * @param object 在instantiateItem中提交给ViewPager进行保存的实例对象
     * @return 是否相互对应
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    /**
     * 保存与PagerAdapter关联的任何实例状态。
     *
     * @return PagerAdapter保存状态
     */
    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    /**
     * 恢复与PagerAdapter关联的任何实例状态。
     *
     * @param state  PagerAdapter保存状态
     * @param loader 用于实例化还原对象的类加载器
     */
    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    /**
     * 当ViewPager试图确定某个项的位置是否已更改时调用。默认有两个可选项:POSITION_UNCHANGED和POSITION_NONE。
     * POSITION_UNCHANGED:给定项的位置未变更
     * POSITION_NONE:给定项不再用于PagerAdapter中
     * 其他值:可以根据具体的情况进行调整
     *
     * @param object 在instantiateItem中提交给ViewPager进行保存的实例对象
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    /**
     * 新增方法,目前较多用于Design库中的TabLayout与ViewPager进行绑定时,提供显示的标题。
     *
     * @param position 给定的位置
     * @return 显示的标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    /**
     * 获取给定位置的View的显示宽度比例,该比例是相对于ViewPager。
     *
     * @param position 给定的位置
     * @return View显示的宽度比例
     */
    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }





}
