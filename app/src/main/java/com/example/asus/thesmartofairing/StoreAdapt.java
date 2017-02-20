package com.example.asus.thesmartofairing;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ASUS on 2017/2/17.
 */

public class StoreAdapt extends RecyclerView.Adapter<MyViewHolder> {
    private LayoutInflater mLayoutInflater ;
    private Context mContext;
    private List<StoreGson> mStoreGsonList;
    private List<String> tv_list;
    private List<String> iv_list1;
    private List<String> iv_list2;
    private List<String> iv_list3;
    private int Type_R = 1;
    private int Type_L = 0;

    public StoreAdapt(Context context, List<StoreGson> mStoreGsonList){
        this.mContext = context;
        this.mStoreGsonList = mStoreGsonList;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==Type_R){
            return new MyViewHolder(mLayoutInflater.inflate(R.layout.set_store_cardview1,parent,false));
        }else {
            return new MyViewHolder(mLayoutInflater.inflate(R.layout.store_cardview,parent,false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2==0){
            return Type_R;
        }
        else return Type_L;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mTextView.setText(mStoreGsonList.get(position).getName());
        Uri uri1 = Uri.parse(mStoreGsonList.get(position).getUrione());
        holder.mImageView1.setImageURI(uri1);
        Uri uri2 = Uri.parse(mStoreGsonList.get(position).getUritwo());
        holder.mImageView2.setImageURI(uri2);
        Uri uri3 = Uri.parse(mStoreGsonList.get(position).getUrithree());
        holder.mImageView3.setImageURI(uri3);
    }
    @Override
    public int getItemCount() {
        return mStoreGsonList.size();
    }
}
