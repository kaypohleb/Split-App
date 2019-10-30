package com.example.calebfoo.split;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    Hashtable foodTable = new Hashtable<String,Double>();
    Double foodTotalPrice;
    Hashtable nameList = new Hashtable<String,String>();
    List<Boolean> checked = new ArrayList<>();
    boolean noerror=false;
    int s = 0;
    String allPersons="";
    static final int PICK_CONTACT=1;
    int[] darkColors;
    public static final int REQUEST_CODE=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        darkColors=getResources().getIntArray(R.array.darkcolorset);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void addFood(View view) {

        LinearLayout linear = findViewById(R.id.LineatLayout);
        EditText foodname = findViewById(R.id.foodName);
        EditText foodprice = findViewById(R.id.foodPrice);
        if((TextUtils.isEmpty(foodname.getText()))||(TextUtils.isEmpty(foodprice.getText()))){
            Toast.makeText(getApplicationContext(),"Please Fill Both Boxes", Toast.LENGTH_LONG).show();}
        else {
            foodTable.put(foodname.getText().toString(), Double.parseDouble(foodprice.getText().toString()));
            String newText = foodname.getText().toString() + " " + foodprice.getText().toString();
            float[] outerRadii = new float[]{30,30,30,30,30,30,30,30};
            float[] innerRadii = new float[]{5,5,5,5,5,5,5,5};

            // Set the shape border
            ShapeDrawable borderDrawable = new ShapeDrawable(new RoundRectShape(
                    outerRadii,
                    null, // Inset
                    innerRadii
            ));
            borderDrawable.getPaint().setColor(getResources().getColor(R.color.White));
            borderDrawable.getPaint().setStyle(Paint.Style.FILL);
            borderDrawable.setIntrinsicWidth(1500);
            borderDrawable.setIntrinsicHeight(300);
            // Define the border width
            borderDrawable.setPadding(2,2,2,2);

            // Set the shape background
            ShapeDrawable backgroundShape = new ShapeDrawable(new RoundRectShape(
                    outerRadii,
                    null,
                    innerRadii
            ));
            backgroundShape.getPaint().setColor(darkColors[new Random().nextInt(darkColors.length)]); // background color
            backgroundShape.getPaint().setStyle(Paint.Style.FILL); // Define background
            backgroundShape.getPaint().setAntiAlias(true);
            backgroundShape.setIntrinsicWidth(1500);
            backgroundShape.setIntrinsicHeight(300);
            // Initialize an array of drawables
            Drawable[] drawables = new Drawable[]{
                    borderDrawable,
                    backgroundShape
            };
            // Set shape padding
            backgroundShape.setPadding(15,15,15,15);
            LayerDrawable layerDrawable = new LayerDrawable(drawables);

            // Initialize a layer drawable object

            TextView tv1 = new TextView(this);
            tv1.setTextSize(15f);
            tv1.setText(newText);
            tv1.setGravity(Gravity.CENTER);
            tv1.setBackground(layerDrawable);
            tv1.setTextColor(getResources().getColor(R.color.White));

            linear.addView(tv1);
            foodname.setText("");
            foodprice.setText("");
            noerror = true;
        }
    }
    public void onConfirm(View view) {

        if (noerror) {

            double total = 0;
//            setContentView(R.layout.check_main);
//            LinearLayout newLinear = (LinearLayout)findViewById(R.id.dropView);
            Set<String> keys = foodTable.keySet();
            for (String key : keys) {
                total = total + Double.parseDouble(foodTable.get(key).toString());
//                String newText = key + " " + foodTable.get(key).toString();
//                TextView tv1 = new TextView(this);
//                tv1.setText(newText);
//                newLinear.addView(tv1);
            }
            foodTotalPrice = total;
//            TextView newText = (TextView)findViewById(R.id.TotalView);
//            newText.setText(String.valueOf(foodTotalPrice));

            Intent i = new Intent(getApplicationContext(), PopActivity.class);
            i.putExtra("totalPrice", total);

            startActivityForResult(i,REQUEST_CODE);


        }

        else{
            Toast.makeText(getApplicationContext(),"Please Add A Item",Toast.LENGTH_SHORT).show();
        }
        noerror=false;
    }


    public void onCorrect(){
        LinearLayout popup = this.findViewById(R.id.popUP);
        popup.setVisibility(View.VISIBLE);
    }
    public void onSplit(View view) {
        setContentView(R.layout.splitterlayout);
        final LinearLayout split = findViewById(R.id.splitLayout);
        split.setVisibility(View.VISIBLE);
        final TextView splitText = findViewById(R.id.SplitTextView);
        Spinner spinner = findViewById(R.id.spinner2);
        spinner.setVisibility(View.VISIBLE);
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {

            list.add(String.valueOf(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                String text1 = "Each person has to pay $" + String.valueOf(Double.valueOf(foodTotalPrice / Integer.parseInt(item.toString())));
                splitText.setText(text1);
                splitText.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }
    public void onADVSeperate(View view){
        setContentView(R.layout.seperatelayout);

    }
    public void onSeperate(View view){
        LinearLayout linear = findViewById(R.id.nameList);
        EditText nameadd = findViewById(R.id.nameAdder);
        nameList.put(nameadd.getText().toString(),"");
        TextView tv1 = new TextView(this);
        tv1.setText(nameadd.getText().toString());
        linear.addView(tv1);
        nameadd.setText("");

    }
    public void onContact(View view){

                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 101) {
            if(resultCode == Activity.RESULT_OK) {
//                setContentView(R.layout.check_main);
//                LinearLayout popup = this.findViewById(R.id.popUP);
//                popup.setVisibility(View.VISIBLE);
                Intent i2 = new Intent(getApplicationContext(),PopChooseActivity.class);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                startActivityForResult(i2,102);


            }

            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if(requestCode==102){
            if(resultCode== Activity.RESULT_OK){
                Bundle extras = getIntent().getExtras();
                int value=1;
                if (extras != null) {
                    value = extras.getInt("choice");
                }
                if(value==1)
                {
                    setContentView(R.layout.seperatelayout);
                }

            }
            if(resultCode==Activity.RESULT_CANCELED){

            }
        }

    }//onActivityResult
    public void contactFixer(String namer){
        LinearLayout linear = findViewById(R.id.nameList);
        nameList.put(namer,"");
        TextView tv1 = new TextView(this);
        tv1.setText(namer);
        linear.addView(tv1);
    }
    public void addButtons(List<String> nameLister,LinearLayout foodButton ,TextView SetText){
        SetText.setText(nameLister.get(s));
        ViewGroup layout = findViewById(R.id.foodBtns);
        for (int i = 0; i < foodButton.getChildCount(); i++) {

            checked.set(i, false);
            View child = layout.getChildAt(i);
            if (child instanceof ToggleButton) {
                ToggleButton btn2 = (ToggleButton) child;
                btn2.setChecked(false);

            }


        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onDone(View view) {
        LinearLayout nameLayout = this.findViewById(R.id.nameLayout);
        nameLayout.setVisibility(View.INVISIBLE);
        final LinearLayout chooseLayout = this.findViewById(R.id.chooseLayout);
        chooseLayout.setVisibility(View.VISIBLE);
        final LinearLayout foodButtons = findViewById(R.id.foodBtns);
        final TextView nameChosen = findViewById(R.id.selectedName);
//        final LinearLayout fablay=findViewById(R.id.fablay);
        final List<String> nameListed = Collections.list(nameList.keys());
        final List<String> foodies = Collections.list(foodTable.keys());

        final List<Integer> foodChosenchoices= new ArrayList<Integer>();
        for(int i=0;i<nameListed.size();i++)
        {
            foodChosenchoices.add(0);
        }
        nameChosen.setText(nameListed.get(s));
        for(int i=0; i<foodies.size();i++)
        {
            if(s<1)
            {
                checked.add(false);
            }
            else {
                checked.set(i, false);
            }
            final ToggleButton btn1 = new ToggleButton(MainActivity.this);
            btn1.setId(i+1);
            btn1.setTextOff(foodies.get(i));
            btn1.setTag(i);
            btn1.setTextOn(foodies.get(i));
            foodButtons.addView(btn1);
            btn1.setChecked(false);
            btn1.setFocusable(false);
            btn1.setFocusableInTouchMode(false);
            btn1.setBackground(getResources().getDrawable(R.drawable.check));
            btn1.setTextColor(R.drawable.toggle);
            btn1.setOnClickListener(new AdapterView.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(btn1.isChecked()) {
                        checked.set((Integer.parseInt(v.getTag().toString())), true);


                    }else if(!btn1.isChecked()){
                        checked.set((Integer.parseInt(v.getTag().toString())), false);

                    }
                }
            });

        }
        final FloatingActionButton doneBtn= findViewById(R.id.fab);

        doneBtn.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected="";
                for(int i=0;i<checked.size();i++){
                    if(checked.get(i))
                    {
                        selected = selected + String.valueOf(i) + " ";
                        addPersonFoodCount(foodChosenchoices,i);
                    }
                }
                nameList.put(nameListed.get(s),selected);
                Toast.makeText(getApplicationContext(),selected, Toast.LENGTH_SHORT).show();
                s+=1;
                if(s<nameListed.size()) {
                    addButtons(nameListed,foodButtons,nameChosen);
                }
                if((nameListed.size()) == s) {
                    setContentView(R.layout.final_layoutmain);
                    TextView finalView = findViewById(R.id.finalView);
                    TextView totalview = findViewById(R.id.totalView2);
                    finalView.setTextSize(14f);
                    Double price;
                    List<String> selectedList = new ArrayList<>(nameList.values());
                    List<Double> totalPriceList = new ArrayList<>();
                    int selectLength =selectedList.size();
                    for (int i = 0; i < selectedList.size(); i++) {
                        price = 0.0;
                        String selector = selectedList.get(i);
                        String[] selectorList = selector.split(" ");
                        for(int b=0;b<selectLength;b++){
                            if (b==selectorList.length) break;
                            List<Double> priceList = new ArrayList<>(foodTable.values());
                            price = price + priceList.get(Integer.parseInt(selectorList[b]))/foodChosenchoices.get(b);
                        }
                        totalPriceList.add(price);
                        allPersons = nameListed.get(i) + " " + String.valueOf(price) + "\n" + allPersons;

                    }
                    finalView.setText(allPersons);
                    totalview.setText(String.valueOf(foodTotalPrice));
                }

            }

        });
//        fablay.setVisibility(View.INVISIBLE);
    }
    public void addPersonFoodCount(List<Integer> counter, int count){
        int b = counter.get(count);
        counter.set(count,b+1);
    }


}


