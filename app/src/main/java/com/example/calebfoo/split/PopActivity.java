package com.example.calebfoo.split;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DecimalFormat;

public class PopActivity extends Activity {
    Double value;
    Double GstPercent=0.7;
    Double SvcPercent=0.10;
    Double testValue;
    ToggleButton gstbutton;
    ToggleButton svcbutton;
    int svcCheck=0;
    int gstCheck=0;
    private static DecimalFormat df2=new DecimalFormat(".##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getDouble("totalPrice");
            totalView.setText("TOTAL: $ " + String.valueOf(value));
        }
        Button close = (Button)findViewById(R.id.closeBtn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();


            }
        });
        gstbutton = (ToggleButton)findViewById(R.id.gstbtn);
        gstbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onBothCheck(isChecked,svcbutton.isChecked(),GstPercent,SvcPercent);
                totalView.setText("TOTAL: $ " + String.valueOf(df2.format(testValue)));
            }
        });
        svcbutton = (ToggleButton)findViewById(R.id.svcbtn);
        svcbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onBothCheck(isChecked,gstbutton.isChecked(),SvcPercent,GstPercent);
                totalView.setText("TOTAL: $ " + String.valueOf(df2.format(testValue)));
            }
        });


    }
    public void onBothCheck(boolean checked,boolean otherBtnstate,Double percentage,Double otherp) {
        if(checked) {
            if (otherBtnstate) {
                testValue = value;
                testValue *= (1 + (GstPercent + SvcPercent));
            } else {
                testValue = value;
                testValue *= (1 + percentage);
            }
        }
        else{
            if (otherBtnstate) {
                testValue = value;
                testValue *=(1+ otherp);
            } else {
                testValue = value;
            }
        }
    }

}
