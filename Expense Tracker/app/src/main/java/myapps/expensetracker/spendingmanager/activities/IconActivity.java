package myapps.expensetracker.spendingmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import myapps.expensetracker.spendingmanager.R;
//import myapps.expensetracker.spendingmanager.adapters.AccountAdapter;
import myapps.expensetracker.spendingmanager.adapters.IconsAdapter;
import myapps.expensetracker.spendingmanager.adapters.TextAdapter;

public class IconActivity extends AppCompatActivity {

    Activity mActivity;
    Context mContext;

    TextAdapter adapter;

    RecyclerView mRecyclerView;

    List<String> iconsText=new ArrayList<>();

    ImageView back,addNewBtn;
    TextView iconText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);

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

        mRecyclerView = (RecyclerView) findViewById(R.id.text_icons_recycler);

        back=(ImageView) findViewById(R.id.back);
        addNewBtn=(ImageView) findViewById(R.id.add_new_cat);

        iconText=(TextView) findViewById(R.id.iconTxt);

    }

    private void initFunctionality() {

        addNewBtn.setVisibility(View.GONE);
        iconText.setVisibility(View.VISIBLE);

        addDataToList();
        buildRecycler();

    }

    private void addDataToList() {

            iconsText.add("Baby Icons");
            iconsText.add("Bills");
            iconsText.add("Clothing");
            iconsText.add("Entertainment");
            iconsText.add("Food");
            iconsText.add("Health");
            iconsText.add("Nature");
            iconsText.add("Money");
            iconsText.add("Travelling");

    }

    private void buildRecycler() {

        adapter = new TextAdapter(mContext,iconsText);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void initListeners() {


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter.setClickListener(new TextAdapter.ClickListener() {
            @Override
            public void onItemClicked(int position, int icons_id) {

                Intent intent=new Intent();
                intent.putExtra("iconsId", icons_id);

                mActivity.setResult(Activity.RESULT_OK,intent);
                mActivity.finish();

            }
        });
    }


}