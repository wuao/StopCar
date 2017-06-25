package com.sanxiongdi.stopcar.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseAdapter;
import com.sanxiongdi.stopcar.base.BaseRecyclerViewHolder;
import com.sanxiongdi.stopcar.entity.QueryOrderEntity;
import com.sanxiongdi.stopcar.holder.AuthOrderHolder;
import com.sanxiongdi.stopcar.holder.CancelOrderHolder;
import com.sanxiongdi.stopcar.holder.FinishOrderHolder;
import com.sanxiongdi.stopcar.holder.ProceedOrderHolder;
import com.sanxiongdi.stopcar.presenter.QueryOrderPresenter;

import java.util.List;

/**
 * 进行中的order 的Adapter 数据类型
 * <p>
 * Created by wuaomall@gmail.com on 2017/4/10.
 */

public class OrderListAdapter extends BaseAdapter<QueryOrderEntity> {
    private static final int FINISH = 0;//完成
    private static final int PROCEED = 1;//未完成
    private static final int CANCEL = 2;//取消
    private static final int AUTH = 3;//转移


    public OrderListAdapter(Context context, List<QueryOrderEntity> mDatas) {
        super(context, mDatas);
    }

    @Override
    public int getItemViewType(int position) {
        switch (data.get(position).car_order_state) {
            case QueryOrderPresenter.ORDER_STATE_FINISH:
                return FINISH;
            case QueryOrderPresenter.ORDER_STATE_PROCEED:
                return PROCEED;
            case QueryOrderPresenter.ORDER_STATE_CANCEL:
                return CANCEL;
            default:
//                QueryOrderPresenter.ORDER_STATE_AUTH:
                return AUTH;
        }
    }

    /**
     * 渲染具体的holder
     *
     * @param viewGroup ViewHolder的容器 ViewHolder的容器
     * @param viewType  一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        BaseRecyclerViewHolder holder = null;
        switch (viewType) {
            case FINISH:
                holder = new FinishOrderHolder(inflater.inflate(R.layout.order_uitls_item_view, viewGroup, false));
                break;
            case PROCEED:
                holder = new ProceedOrderHolder(inflater.inflate(R.layout.order_uitls_item_view, viewGroup, false));
                break;
            case CANCEL:
                holder = new CancelOrderHolder(inflater.inflate(R.layout.order_uitls_item_view, viewGroup, false));
                break;
            case AUTH:
                holder = new AuthOrderHolder(inflater.inflate(R.layout.order_uitls_item_view, viewGroup, false));
                break;
        }
        return holder;
    }


}
