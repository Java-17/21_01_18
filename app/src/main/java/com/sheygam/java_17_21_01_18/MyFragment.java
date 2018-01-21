package com.sheygam.java_17_21_01_18;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by gregorysheygam on 21/01/2018.
 */

public class MyFragment extends Fragment {
    private float scale;
    private int color;
    private String title;
    private Callback callback;

    public static MyFragment newInstance(int color, float scale){
        MyFragment fragment = new MyFragment();
        fragment.scale = scale;
        fragment.color = color;
        return fragment;
    }

    public void setOnClickListener(Callback callback){
        this.callback = callback;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("MY_FRAGMENT", "onAttach: ");
        if(activity instanceof Callback){
            callback = (Callback) activity;
        }else{
            throw new RuntimeException(activity.getClass().getSimpleName() + "Must implements MyFragment.Callback interface!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            title = args.getString("TITLE","No title");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("MY_FRAGMENT", "onCreateView: ");
        return inflater.inflate(R.layout.my_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setBackgroundColor(color);
        view.setScaleX(scale);
        view.setScaleY(scale);
        TextView titleTxt = view.findViewById(R.id.title_txt);
        titleTxt.setText(title);

        view.findViewById(R.id.my_btn)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(callback != null){
                            callback.onButtonClick();
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.fragment_container,
                                            MyFragment.newInstance(Color.rgb(0,0,0),0.5f))
                                    .commit();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        Log.d("MY_FRAGMENT", "onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.d("MY_FRAGMENT", "onDetach: ");
        super.onDetach();
    }

    interface Callback{
        void onButtonClick();
    }
}
