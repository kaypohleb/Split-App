package com.example.calebfoo.split;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Hashtable;

public class PopChooseActivity extends Activity{
    Hashtable foodTable = new Hashtable<String,Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_choose);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;


        getWindow().setLayout((int) width,(int)(height*.5));
        WindowManager.LayoutParams params= getWindow().getAttributes();
        params.gravity= Gravity.BOTTOM;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

        final TextView totalView = findViewById(R.id.foodTotal);

    }
    public void onADVSeperate2(View view){
        Intent i=new Intent();
        i.putExtra("choice",1);
        setResult(RESULT_OK,i);
        finish();
    }
}