package com.example.asus.thesmartofairing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {

    private SliderLayout mSliderLayout;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider_layout);
        initSlider();
        // Inflate the layout for this fragment
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_store);
    }

    private void initSlider(){
        TextSliderView textSliderView = new TextSliderView(this.getActivity());
        textSliderView.description("秒杀")
                .image("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(StoreFragment.this.getActivity(),"秒杀",Toast.LENGTH_LONG).show();
            }
        });

        TextSliderView textSliderView1 = new TextSliderView(this.getActivity());
        textSliderView1.description("女主角上场")
                .image("http://www.91baihui.com/images//20111018/83ff140f8e124bbe.jpg");
        textSliderView1.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(StoreFragment.this.getActivity(),"女主角上场",Toast.LENGTH_LONG).show();
            }
        });

        TextSliderView textSliderView2 = new TextSliderView(this.getActivity());
        textSliderView2.description("帆布态度")
                .image("http://sc.jb51.net/uploads/allimg/140305/10-140305220335391.jpg");
        textSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(StoreFragment.this.getActivity(),"帆布态度",Toast.LENGTH_LONG).show();
            }
        });

        TextSliderView textSliderView3 = new TextSliderView(this.getActivity());
        textSliderView3.description("轻松淘")
                .image("http://pic2.ooopic.com/11/72/89/06b1OOOPICe1.jpg");
        textSliderView3.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(StoreFragment.this.getActivity(),"轻松淘",Toast.LENGTH_LONG).show();
            }
        });

        mSliderLayout.addSlider(textSliderView);
        mSliderLayout.addSlider(textSliderView1);
        mSliderLayout.addSlider(textSliderView2);
        mSliderLayout.addSlider(textSliderView3);


        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        mSliderLayout.setDuration(3000);


        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
