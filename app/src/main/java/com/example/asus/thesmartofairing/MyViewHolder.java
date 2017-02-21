package com.example.asus.thesmartofairing;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by ASUS on 2017/2/17.
 */
public class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView mTextView;
    public TextView tv_card_view_1_1;
    public TextView tv_card_view_1_2;
    public TextView tv_card_view_2_1;
    public TextView tv_care_view_2_2;
    public TextView tv_card_view_3_1;
    public TextView tv_card_view_3_2;
    public SimpleDraweeView mImageView1;
    public SimpleDraweeView mImageView2;
    public SimpleDraweeView mImageView3;
    public MyViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.tv_heat_activity);
        tv_card_view_1_1 = (TextView) itemView.findViewById(R.id.card_view_1_1);
        tv_card_view_1_2 = (TextView) itemView.findViewById(R.id.card_view_1_2);
        tv_card_view_2_1 = (TextView) itemView.findViewById(R.id.card_view_2_1);
        tv_care_view_2_2 = (TextView) itemView.findViewById(R.id.card_view_2_2);
        tv_card_view_3_1 = (TextView) itemView.findViewById(R.id.card_view_3_1);
        tv_card_view_3_2 = (TextView) itemView.findViewById(R.id.card_view_3_2);
        mImageView1 = (SimpleDraweeView) itemView.findViewById(R.id.iv_store_image1);
        mImageView2 = (SimpleDraweeView) itemView.findViewById(R.id.iv_store_image2);
        mImageView3 = (SimpleDraweeView) itemView.findViewById(R.id.iv_store_image3);
    }
}
