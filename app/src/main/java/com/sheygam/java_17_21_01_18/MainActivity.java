package com.sheygam.java_17_21_01_18;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyFragment.Callback {

    public static float SCALE_X = 1.0F;
    public static float SCALE_Y = 1.0F;
    private Button addBtn, replaceBtn, removeBtn, attachBtn, detachBtn;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBtn = findViewById(R.id.add_btn);
        replaceBtn = findViewById(R.id.replace_btn);
        removeBtn = findViewById(R.id.remove_btn);
        attachBtn = findViewById(R.id.attach_btn);
        detachBtn = findViewById(R.id.detach_btn);
        addBtn.setOnClickListener(this);
        replaceBtn.setOnClickListener(this);
        removeBtn.setOnClickListener(this);
        attachBtn.setOnClickListener(this);
        detachBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                MyFragment myFragment = MyFragment.newInstance(getColor(), SCALE_X);
                Bundle args = new Bundle();
                args.putString("TITLE", "Fragment " + count);
//                myFragment.setArguments(args);

                myFragment.setOnClickListener(this);

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container,
                                myFragment,
                                "FRAG" + count++)
                        .addToBackStack(null)
                        .commit();
                SCALE_X-=0.1;
                break;
            case R.id.replace_btn:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,new MyFragment())
                        .commit();
                break;
            case R.id.remove_btn:
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("FRAG3");
                if(fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .remove(fragment)
                            .commit();
                }
                break;
            case R.id.attach_btn:
                Fragment frag = getSupportFragmentManager().findFragmentByTag("FRAG2");
                if(frag != null){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .attach(frag)
                            .commit();
                }
                break;
            case R.id.detach_btn:
                Fragment frag2 = getSupportFragmentManager().findFragmentByTag("FRAG2");
                if(frag2 != null){
                    Bundle args1 = frag2.getArguments();
                    Toast.makeText(this, args1.getString("TITLE"), Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .detach(frag2)
                            .commit();
                }
                break;
        }
    }

    private int getColor(){
        Random rnd = new Random();
        return Color.rgb(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
    }

    @Override
    public void onButtonClick() {
        Toast.makeText(this, "Was clicked fragment btn", Toast.LENGTH_SHORT).show();
    }
}
