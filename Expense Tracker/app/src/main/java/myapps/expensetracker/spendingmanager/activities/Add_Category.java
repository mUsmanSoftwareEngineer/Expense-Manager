package myapps.expensetracker.spendingmanager.activities;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.cameron.materialcolorpicker.ColorPicker;
import com.cameron.materialcolorpicker.ColorPickerCallback;

import java.util.Random;

import io.github.ketzalv.validationedittext.ValidationEditText;
import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.libs.BitmapManager;
import myapps.expensetracker.spendingmanager.viewmodel.CategoriesViewModel;


public class Add_Category extends AppCompatActivity {


    Activity mActivity;
    Context mContext;

    ValidationEditText editText_cat_name;
    ImageView cat_icon,remove_icon,back_icon, add_new;


    int category_type,category_position,category_icon;

    String category_name;

    TextView doneSave,no_selected_icon;

    TextView iconTxt,doneTxt;

    private CategoriesViewModel mCategoryViewModel;

    int icons_value=0;
    byte[] iconImage;
    Bitmap iconBitmap;
    Drawable iconDrawable;

    RelativeLayout add_color_relative;

    View addColor;
    int colorValue;
    Random rnd = new Random();
    int red=0;
    int green=0;
    int blue=0;
    ColorPicker colorPicker ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        initVars();
        initViews();
        initFunctionality();
        initListeners();

    }



    private void initVars() {
        mActivity=this;
        mContext=getApplicationContext();
    }



    private void initViews() {

        editText_cat_name=(ValidationEditText) findViewById(R.id.edit_category);

        cat_icon=(ImageView) findViewById(R.id.cat_icon);
        remove_icon=(ImageView)  findViewById(R.id.remove_icon);
        doneSave=(TextView) findViewById(R.id.done);
        no_selected_icon=(TextView) findViewById(R.id.no_icon);
        back_icon=(ImageView) findViewById(R.id.back);
        add_new=(ImageView) findViewById(R.id.add_new_cat);
        iconTxt=(TextView) findViewById(R.id.iconTxt);
//        doneTxt=(TextView) findViewById(R.id.done);

        add_color_relative=(RelativeLayout) findViewById(R.id.linear_color);
        addColor=findViewById(R.id.cat_color);
        colorPicker = new ColorPicker(mActivity, red, green, blue);
    }



    private void initFunctionality() {




        add_new.setVisibility(View.GONE);
        iconTxt.setVisibility(View.GONE);
        doneSave.setVisibility(View.VISIBLE);
//        icons_value=R.drawable.fuel;
//        iconDrawable=getResources().getDrawable(icons_value);
//        iconBitmap=((BitmapDrawable)iconDrawable).getBitmap();
//        iconImage= BitmapManager.bitmapToByte(iconBitmap);

        mCategoryViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);

        Bundle b = getIntent().getExtras();

        if (b != null) {

            category_type = b.getInt(PrefKey.Fragment_position);
            category_position=b.getInt(PrefKey.Adapter_position);
            category_name=b.getString(PrefKey.Category_name);
            colorValue=b.getInt(PrefKey.Category_color);
        }



//        Toast.makeText(mActivity, "checK icon Val" + icons_value, Toast.LENGTH_SHORT).show();

        if(category_type == Constants.UPDATE_EXPENSE_CATEGORIES_FRAGMENT){

            editText_cat_name.setText(category_name);
            addColor.setBackgroundColor(colorValue);
            Toast.makeText(mActivity, Constants.categoryBitmap+"", Toast.LENGTH_SHORT).show();
            if(Constants.categoryBitmap==null){
                cat_icon.setVisibility(View.GONE);
                remove_icon.setVisibility(View.GONE);
                no_selected_icon.setVisibility(View.VISIBLE);
            }
            else {
                cat_icon.setVisibility(View.VISIBLE);
                remove_icon.setVisibility(View.VISIBLE);
                cat_icon.setImageBitmap(Constants.categoryBitmap);
                iconImage= BitmapManager.bitmapToByte(Constants.categoryBitmap);
            }
        }
        else if(category_type==Constants.UPDATE_INCOME_CATEGORIES_FRAGMENT){
            editText_cat_name.setText(category_name);
            addColor.setBackgroundColor(colorValue);
            if(Constants.categoryBitmap==null){
                cat_icon.setVisibility(View.GONE);
                remove_icon.setVisibility(View.GONE);
                no_selected_icon.setVisibility(View.VISIBLE);
            }
            else {
                cat_icon.setVisibility(View.VISIBLE);
                remove_icon.setVisibility(View.VISIBLE);
                cat_icon.setImageBitmap(Constants.categoryBitmap);
                iconImage= BitmapManager.bitmapToByte(Constants.categoryBitmap);
            }
        }
        else if(category_type==Constants.NEW_EXPENSE_CATEGORIES_FRAGMENT){
            icons_value=R.drawable.fuel;
            cat_icon.setImageResource(icons_value);
            iconDrawable=getResources().getDrawable(icons_value);
            iconBitmap=((BitmapDrawable)iconDrawable).getBitmap();
            iconImage= BitmapManager.bitmapToByte(iconBitmap);
            colorValue = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        }
        else if(category_type==Constants.NEW_INCOME_CATEGORIES_FRAGMENT){
            icons_value=R.drawable.money;
            iconDrawable=getResources().getDrawable(icons_value);
            iconBitmap=((BitmapDrawable)iconDrawable).getBitmap();
            iconImage= BitmapManager.bitmapToByte(iconBitmap);
            cat_icon.setImageResource(icons_value);
            colorValue = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        }


    }

    private void initListeners() {


        colorPicker.setDialogButtonText("CONFIRM")    // The default text is "SUBMIT"
                .setCloseOnBackPressed(true)         // The default value is true
                .showButtonAsTransparent(true)    ;    // The default value is false; // The default value is true
// Set a new Listener called when user click "select"
        colorPicker.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color, String hex, String hexNoAlpha) {
                Log.d("Pure color", String.valueOf(color));
                Log.d("Alpha", Integer.toString(Color.alpha(color)));
                Log.d("Red", Integer.toString(Color.red(color)));
                Log.d("Green", Integer.toString(Color.green(color)));
                Log.d("Blue", Integer.toString(Color.blue(color)));
                Log.d("Hex with alpha", hex);
                Log.d("Hex no alpha", hexNoAlpha);
                // Once the dialog's select button has been pressed, we
                // can get the selected color and use it for the
                // background of our view
                colorValue=color;
                addColor.setBackgroundColor(color);
            }

            /**
             * When the color values from the dialog are changed, this method will
             * be called. Here, we'll just change the color of the dialog's button.
             */
            @Override
            public void onColorChanged(@ColorInt int color, String hex, String hexNoAlpha) {
                Log.d("Color", String.valueOf(color));
                Log.d("Hex", hex);
                Log.d("Hex no alpha", hexNoAlpha);
                // Save the color selected so we can retrieve it again
                // when the device is rotated
//                currentColor = color;
                colorValue=color;
                colorPicker.setDialogButtonTextColor(color);
            }
        });

        doneSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText_cat_name.setOnValidationListener(new ValidationEditText.OnValidationListener() {
                    @Override
                    public void onValidEditText(ValidationEditText editText, String text) {

                    }

                    @Override
                    public void onInvalidEditText(ValidationEditText editText) {

                    }
                });


                String editTextVal=editText_cat_name.getText().toString();
                if(editTextVal.isEmpty()){
                    Toast.makeText(mActivity, "Category Name can't be empty", Toast.LENGTH_SHORT).show();
                }else {
                    if(category_type==Constants.NEW_EXPENSE_CATEGORIES_FRAGMENT){

//                    iconDrawable=getResources().getDrawable(icons_value);
//                    iconBitmap=((BitmapDrawable)iconDrawable).getBitmap();
//                    iconImage= BitmapManager.bitmapToByte(iconBitmap);

                        Categories categories = new Categories(editTextVal,0,iconImage,colorValue);
                        mCategoryViewModel.insert(categories);
                        finish();

                    }
                    else if(category_type==Constants.NEW_INCOME_CATEGORIES_FRAGMENT){

//                    iconDrawable=getResources().getDrawable(icons_value);
//                    iconBitmap=((BitmapDrawable)iconDrawable).getBitmap();
//                    iconImage= BitmapManager.bitmapToByte(iconBitmap);
                        Categories categories = new Categories(editTextVal,1,iconImage,colorValue);
                        mCategoryViewModel.insert(categories);
                        finish();
                    }
                    else if(category_type==Constants.UPDATE_EXPENSE_CATEGORIES_FRAGMENT){

                        Categories categories = new Categories(editTextVal,0,iconImage,colorValue);
                        categories.setCategoryId(category_position);
                        mCategoryViewModel.update(categories);
                        finish();
                    }
                    else if(category_type==Constants.UPDATE_INCOME_CATEGORIES_FRAGMENT){

                        Categories categories = new Categories(editTextVal,1,iconImage,colorValue);
                        categories.setCategoryId(category_position);
                        mCategoryViewModel.update(categories);
                        finish();
                    }
                }


            }
        });

        cat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, IconActivity.class);
                Bundle b = new Bundle();
                intent.putExtras(b);
                startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        remove_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_icon.setImageResource(0);
                cat_icon.setVisibility(View.GONE);
                remove_icon.setVisibility(View.GONE);
                no_selected_icon.setVisibility(View.VISIBLE);
                cat_icon.setImageResource(0);
                icons_value=0;
                iconImage=null;
            }
        });

        no_selected_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, IconActivity.class);
                Bundle b = new Bundle();
                intent.putExtras(b);
                startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add_color_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    colorPicker.show();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if(resultCode == RESULT_OK) {

                icons_value=data.getIntExtra("iconsId",0);
                iconDrawable=getResources().getDrawable(icons_value);
                iconBitmap=((BitmapDrawable)iconDrawable).getBitmap();
                iconImage= BitmapManager.bitmapToByte(iconBitmap);
                cat_icon.setImageBitmap(iconBitmap);
                if(!(cat_icon.getVisibility() ==View.VISIBLE)){
                    cat_icon.setVisibility(View.VISIBLE);
                    remove_icon.setVisibility(View.VISIBLE);
                    no_selected_icon.setVisibility(View.GONE);
                }


            }
        }
    }


}