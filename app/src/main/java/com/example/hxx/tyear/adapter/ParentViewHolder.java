package com.example.hxx.tyear.adapter;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.statisticsFragmentModel.TableNameItem;


/**
 * Created by hbh on 2017/4/20.
 * 父布局ViewHolder
 */

public class ParentViewHolder extends BaseViewHolder {

    private Context mContext;
    private View view;
    private RelativeLayout containerLayout;
    private TextView parentLeftView;
    private TextView parentRightView;

    private View parentDashedView;
    TextView textName;
    TextView textSum;
    ImageView expand;
    public ParentViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final TableNameItem dataBean, final int pos, final ItemClickListener listener){

        containerLayout = (RelativeLayout) view.findViewById(R.id.container);
        textName = (TextView) view.findViewById(R.id.table_name);//加载每个子项里的内容-题目！！
        textSum = (TextView) view.findViewById(R.id.table_sum);//加载每个子项里的内容-题目！！
        expand=(ImageView) view.findViewById(R.id.table_open);//加载每个子项里的内容-内容！！

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) expand
                .getLayoutParams();//获取布局控件
        expand.setLayoutParams(params);//再设置好控件？？
        textName.setText(dataBean.getName());
        textSum.setText(""+dataBean.getSum());
//        
//        parentLeftView.setText(dataBean.getParentLeftTxt());
//        parentRightView.setText(dataBean.getParentRightTxt());

        if (dataBean.isExpand()) {
            expand.setRotation(90);//动画 图标九十度展开
           // parentDashedView.setVisibility(View.INVISIBLE);
        } else {
            expand.setRotation(0);
          //  parentDashedView.setVisibility(View.VISIBLE);
        }

        //父布局OnClick监听
        containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {//若设置了监听
                    if (dataBean.isExpand()) {//不展开
                        listener.onHideChildren(dataBean);//隐藏子布局
                     //   parentDashedView.setVisibility(View.VISIBLE);//虚线搞出来
                        dataBean.setExpand(false);
                        rotationExpandIcon(90, 0);
                    } else {
                        listener.onExpandChildren(dataBean);//展开子布局
                      //  parentDashedView.setVisibility(View.INVISIBLE);//虚线没
                        dataBean.setExpand(true);
                        rotationExpandIcon(0, 90);
                    }
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void rotationExpandIcon(float from, float to) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);//属性动画
            valueAnimator.setDuration(500);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    expand.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }
}
