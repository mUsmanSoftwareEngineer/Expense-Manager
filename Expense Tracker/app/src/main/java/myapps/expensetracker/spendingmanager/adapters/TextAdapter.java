package myapps.expensetracker.spendingmanager.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import myapps.expensetracker.spendingmanager.R;

import static android.app.Activity.RESULT_OK;

public class TextAdapter  extends RecyclerView.Adapter<TextAdapter.ViewHolder>{

    private Context mContext;
    List<String> iconsText;
    List<Integer> iconsImages;

    int icons_id;


    private TextAdapter.ClickListener clickListener;


    public TextAdapter(Context mContext, List<String> iconsText) {
        this.mContext = mContext;
        this.iconsText = iconsText;
    }

    public void setClickListener(TextAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @NotNull
    @Override
    public TextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_recycler, parent, false);
        return new TextAdapter.ViewHolder(view, viewType);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView only_text;

        private RecyclerView recyclerView;

        private LinearLayout item_category_layout;

        public ViewHolder(View v, int viewType) {
            super(v);

            only_text = (TextView) v.findViewById(R.id.icons_txt);
            recyclerView=(RecyclerView) v.findViewById(R.id.icons_recycler);

//            item_category_layout = (LinearLayout) v.findViewById(R.id.item_category_layout);



//            item_category_layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (clickListener != null) {
//
//
//                    }
//                }
//            });

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(TextAdapter.ViewHolder holder, int position) {

        iconsImages=new ArrayList<>();
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 8, GridLayoutManager.VERTICAL, false);

        holder.recyclerView.setLayoutManager(layoutManager);
//        holder.recyclerView.setNestedScrollingEnabled(false);
        holder.recyclerView.setHasFixedSize(true);

        holder.only_text.setText(iconsText.get(position));


        if(position==0){

            addBabyIconsDataToList();
        }
        else if(position==1){
            addBillsIconsDataToList();
        }
        else if(position==2){
            addClothingIconsDataToList();
        }
        else if(position==3){
            addEntertainmentIconsDataToList();
        }else if(position==4){
            addFoodIconsDataToList();
        }else if(position==5){
            addHealthIconsDataToList();
        }
        else if(position==6){
            addNatureIconsDataToList();
        }else if(position==7){
            addMoneyIconsDataToList();
        }else if(position==8){
            addTravellingIconsDataToList();
        }else if(position==9){
            addTravellingIconsDataToList();
        }

        IconsAdapter iconsAdapter=new IconsAdapter(mContext,iconsImages);
        holder.recyclerView.setAdapter(iconsAdapter);
        iconsAdapter.notifyDataSetChanged();

        iconsAdapter.setClickListener(new IconsAdapter.ClickListener() {
            @Override
            public void onItemClicked(int position, int id) {

                icons_id=id;
                clickListener.onItemClicked(position,icons_id);

//                        ((Activity)mContext).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return iconsText.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void addBabyIconsDataToList(){

        iconsImages.add(R.drawable.cot);
        iconsImages.add(R.drawable.clothes);
        iconsImages.add(R.drawable.clothes2);
        iconsImages.add(R.drawable.toy);
    }

    private void addBillsIconsDataToList(){

        iconsImages.add(R.drawable.electicity);
        iconsImages.add(R.drawable.gas);
        iconsImages.add(R.drawable.globe);
        iconsImages.add(R.drawable.internet);
        iconsImages.add(R.drawable.light);
        iconsImages.add(R.drawable.screen);
        iconsImages.add(R.drawable.water);

    }

    private void addClothingIconsDataToList(){

        iconsImages.add(R.drawable.backpack);
        iconsImages.add(R.drawable.bow);
        iconsImages.add(R.drawable.dress);
        iconsImages.add(R.drawable.hand_bag);
        iconsImages.add(R.drawable.hanger);
        iconsImages.add(R.drawable.hat);
        iconsImages.add(R.drawable.heels);
        iconsImages.add(R.drawable.jacket);
        iconsImages.add(R.drawable.joggers);
        iconsImages.add(R.drawable.long_shoe);
        iconsImages.add(R.drawable.pants);
        iconsImages.add(R.drawable.scarf);
        iconsImages.add(R.drawable.shirt2);
        iconsImages.add(R.drawable.shirts);
        iconsImages.add(R.drawable.shopping);
        iconsImages.add(R.drawable.skirt);
        iconsImages.add(R.drawable.skirt2);
        iconsImages.add(R.drawable.smart_shoe);
        iconsImages.add(R.drawable.sock);
        iconsImages.add(R.drawable.tiee);

    }

    private void addEntertainmentIconsDataToList(){

        iconsImages.add(R.drawable.cards);
        iconsImages.add(R.drawable.cd_player);
        iconsImages.add(R.drawable.cheers);
        iconsImages.add(R.drawable.dice);
        iconsImages.add(R.drawable.game);
        iconsImages.add(R.drawable.gaming);
        iconsImages.add(R.drawable.guitar);
        iconsImages.add(R.drawable.headphones);
        iconsImages.add(R.drawable.laptop);
        iconsImages.add(R.drawable.led_tv);
        iconsImages.add(R.drawable.cheers);
        iconsImages.add(R.drawable.mic);
        iconsImages.add(R.drawable.mobile);
        iconsImages.add(R.drawable.monitor);
        iconsImages.add(R.drawable.music);
        iconsImages.add(R.drawable.party);
        iconsImages.add(R.drawable.playstation);
        iconsImages.add(R.drawable.tab);
        iconsImages.add(R.drawable.tune);
        iconsImages.add(R.drawable.video);

    }

    private void addFoodIconsDataToList(){

        iconsImages.add(R.drawable.bottles);
        iconsImages.add(R.drawable.bowl);
        iconsImages.add(R.drawable.breadd);
        iconsImages.add(R.drawable.burgerr);
        iconsImages.add(R.drawable.cake);
        iconsImages.add(R.drawable.chicken);
        iconsImages.add(R.drawable.chocolate);
        iconsImages.add(R.drawable.cocktail);
        iconsImages.add(R.drawable.coffeee);
        iconsImages.add(R.drawable.cookies);
        iconsImages.add(R.drawable.cheers);
        iconsImages.add(R.drawable.donut);
        iconsImages.add(R.drawable.eggs);
        iconsImages.add(R.drawable.fishh);
        iconsImages.add(R.drawable.fruits);
        iconsImages.add(R.drawable.hot_coffee);
        iconsImages.add(R.drawable.icecream);
        iconsImages.add(R.drawable.juice);
        iconsImages.add(R.drawable.lunch);
        iconsImages.add(R.drawable.meal);
        iconsImages.add(R.drawable.meatt);
        iconsImages.add(R.drawable.milkshake);
        iconsImages.add(R.drawable.noodles);
        iconsImages.add(R.drawable.pizza);
        iconsImages.add(R.drawable.sandwich);
        iconsImages.add(R.drawable.tea);
        iconsImages.add(R.drawable.vegetables);

    }

    private void addHealthIconsDataToList(){

        iconsImages.add(R.drawable.bandage);
        iconsImages.add(R.drawable.doctor);
        iconsImages.add(R.drawable.eyeshadow);
        iconsImages.add(R.drawable.face_mask);
        iconsImages.add(R.drawable.first_aid);
        iconsImages.add(R.drawable.hair_color);
        iconsImages.add(R.drawable.hair_cut);
        iconsImages.add(R.drawable.hair_style);
        iconsImages.add(R.drawable.injection);
        iconsImages.add(R.drawable.kids);
        iconsImages.add(R.drawable.lipstick);
        iconsImages.add(R.drawable.makeup);
        iconsImages.add(R.drawable.man);
        iconsImages.add(R.drawable.medicine);
        iconsImages.add(R.drawable.nail_art);
        iconsImages.add(R.drawable.pragnent);
        iconsImages.add(R.drawable.saloon);
        iconsImages.add(R.drawable.scissors);
        iconsImages.add(R.drawable.skin_care);
        iconsImages.add(R.drawable.smoking);
        iconsImages.add(R.drawable.spa);
        iconsImages.add(R.drawable.straightner);
        iconsImages.add(R.drawable.tooth);
        iconsImages.add(R.drawable.woman);


    }

    private void addNatureIconsDataToList(){

        iconsImages.add(R.drawable.ac);
        iconsImages.add(R.drawable.bed);
        iconsImages.add(R.drawable.coocker);
        iconsImages.add(R.drawable.cooler);
        iconsImages.add(R.drawable.cutains);
        iconsImages.add(R.drawable.hair_color);
        iconsImages.add(R.drawable.decor);
        iconsImages.add(R.drawable.fan);
        iconsImages.add(R.drawable.floor_mat);
        iconsImages.add(R.drawable.kids);
        iconsImages.add(R.drawable.heater);
        iconsImages.add(R.drawable.home);
        iconsImages.add(R.drawable.paint);
        iconsImages.add(R.drawable.plant);
        iconsImages.add(R.drawable.plants);
        iconsImages.add(R.drawable.repair);
        iconsImages.add(R.drawable.room);
        iconsImages.add(R.drawable.sink);
        iconsImages.add(R.drawable.sofa);
        iconsImages.add(R.drawable.table_chair);
        iconsImages.add(R.drawable.utensils);
        iconsImages.add(R.drawable.vaccum);
        iconsImages.add(R.drawable.wardrobe);
        iconsImages.add(R.drawable.washing_machine);
        iconsImages.add(R.drawable.water_heater);

    }

    private void addMoneyIconsDataToList(){

        iconsImages.add(R.drawable.amazon);
        iconsImages.add(R.drawable.calculator);
        iconsImages.add(R.drawable.card);
        iconsImages.add(R.drawable.coins);
        iconsImages.add(R.drawable.credit_card);
        iconsImages.add(R.drawable.dollars);
        iconsImages.add(R.drawable.ebay);
        iconsImages.add(R.drawable.euros);
        iconsImages.add(R.drawable.graph1);
        iconsImages.add(R.drawable.graph2);
        iconsImages.add(R.drawable.money);
        iconsImages.add(R.drawable.pounds);
        iconsImages.add(R.drawable.russian);
        iconsImages.add(R.drawable.saving);
        iconsImages.add(R.drawable.student);
        iconsImages.add(R.drawable.wallet);
        iconsImages.add(R.drawable.walmart);

    }

    private void addTravellingIconsDataToList(){

        iconsImages.add(R.drawable.aeroplane);
        iconsImages.add(R.drawable.bike);
        iconsImages.add(R.drawable.boat);
        iconsImages.add(R.drawable.bus);
        iconsImages.add(R.drawable.car);
        iconsImages.add(R.drawable.cycle);
        iconsImages.add(R.drawable.fuel);
        iconsImages.add(R.drawable.helicopter);
        iconsImages.add(R.drawable.scooter);
        iconsImages.add(R.drawable.ship);
        iconsImages.add(R.drawable.tent);
        iconsImages.add(R.drawable.train);
        iconsImages.add(R.drawable.russian);
        iconsImages.add(R.drawable.travel);
        iconsImages.add(R.drawable.travelling_bag);
        iconsImages.add(R.drawable.truck);

    }





    public interface ClickListener {

        void onItemClicked(int position, int icons_id);
    }
}
