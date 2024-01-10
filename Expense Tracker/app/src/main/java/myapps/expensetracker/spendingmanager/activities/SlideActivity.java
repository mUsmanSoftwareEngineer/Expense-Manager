package myapps.expensetracker.spendingmanager.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.adapters.ClickableViewPager;
import myapps.expensetracker.spendingmanager.adapters.IntroAdapter;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;

public class SlideActivity extends AppCompatActivity {
    ImageView gobackslide;
    TextView Skiped;
    CheckBox chek;
    private ClickableViewPager view_pager_slide;
    private IntroAdapter slide_adapter;
//    private LinearLayout linear_layout_skip;
    private final List<Integer> slideList = new ArrayList<>();
    private DotsIndicator view_pager_indicator;
    private RelativeLayout relative_layout_slide;
    private LinearLayout linear_layout_next;
    private Button text_view_next_done;

    Context mContext;
    Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
//        prefManager= new PrefManager(SlideActivity.this);
        mActivity = SlideActivity.this;
        mContext = mActivity.getApplicationContext();

        slideList.add(1);
        slideList.add(2);
//        slideList.add(3);
//        slideList.add(4);
      /*  slideList.add(5);
        slideList.add(6);*/
//        slideList.add(7);

        this.text_view_next_done = findViewById(R.id.button_try_again_btn);
//        this.linear_layout_next=(LinearLayout) findViewById(R.id.linear_layout_next);
        this.Skiped = findViewById(R.id.skipeddd);
        this.gobackslide = findViewById(R.id.goBackk);

        this.view_pager_indicator = findViewById(R.id.dots_indicatorrr);
        this.view_pager_slide = findViewById(R.id.view_pager_slide);
        this.relative_layout_slide = findViewById(R.id.relative_layout_slide);
        chek = findViewById(R.id.checkBox);
        slide_adapter = new IntroAdapter(SlideActivity.this, slideList);
        view_pager_slide.setAdapter(this.slide_adapter);
        view_pager_slide.setOffscreenPageLimit(1);
        //view_pager_slide.setPageTransformer(false, new CarouselEffectTransformer(IntroActivity.this)); // Set transformer


        view_pager_slide.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position < 4) {
                    view_pager_slide.setCurrentItem(position + 1);
                } else {
                    Intent intent = new Intent(SlideActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                }
            }
        });


        this.text_view_next_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text_view_next_done.getText().equals("DONE")) {


                    if (chek.isChecked()) {
                        if(Constants.checkCurrencySymbol){
                            AppPreference.getInstance(mContext).setBoolean(PrefKey.ActivityFirstRun,false);
                            Intent intent = new Intent(SlideActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();
                        }
                        else {
                            Toast.makeText(SlideActivity.this, "Select Currency Symbol", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(SlideActivity.this, "Please Check", Toast.LENGTH_SHORT).show();
                    }

                }
                if (view_pager_slide.getCurrentItem() < slideList.size()) {
                    view_pager_slide.setCurrentItem(view_pager_slide.getCurrentItem() + 1);
                    return;
                }

            }
        });
        view_pager_slide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    gobackslide.setVisibility(View.GONE);
                } else {
                    gobackslide.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

                if (position + 1 == slideList.size()) {
                    text_view_next_done.setVisibility(View.VISIBLE);
                    text_view_next_done.setText("DONE");
                } else {
                    text_view_next_done.setVisibility(View.GONE);
                    text_view_next_done.setText("Continue");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        this.Skiped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SlideActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }
        });

        gobackslide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(SlideActivity.this, "here0"+view_pager_slide.getCurrentItem(), Toast.LENGTH_SHORT).show();

                if (view_pager_slide.getCurrentItem() > 0) {
//                    Toast.makeText(SlideActivity.this, "here1"+view_pager_slide.getCurrentItem(), Toast.LENGTH_SHORT).show();
//                return;
                    view_pager_slide.setCurrentItem(view_pager_slide.getCurrentItem() - 1);
//                    return;
                }

            }
        });
        this.view_pager_slide.setClipToPadding(false);
        this.view_pager_slide.setPageMargin(0);
        view_pager_indicator.setViewPager(view_pager_slide);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (view_pager_slide.getCurrentItem() > 0) {
//                    Toast.makeText(SlideActivity.this, "here1"+view_pager_slide.getCurrentItem(), Toast.LENGTH_SHORT).show();
//                return;
            view_pager_slide.setCurrentItem(view_pager_slide.getCurrentItem() - 1);
//                    return;
        } else {
            finish();
        }
    }
}
