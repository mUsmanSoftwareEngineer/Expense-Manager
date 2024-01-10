package myapps.expensetracker.spendingmanager.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.models.CategoryModel;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.libs.BitmapManager;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<CategoryModel> mList;
    private List<Categories> mCategory;

    private ClickListener clickListener;

    int cat_id,catColor;
    byte catIcon;
    String category_name;


    Bitmap imageBitmap;

    public CategoryAdapter(Context context) {
        this.mContext = context;


    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view, viewType);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView cat_icon,delete_icon;
        private  TextView cat_name;
        private RelativeLayout item_category_layout;


        public ViewHolder(View v, int viewType) {
            super(v);
            cat_icon = (ImageView) v.findViewById(R.id.cat_icon);
            cat_name = (TextView) v.findViewById(R.id.cat_name);
            delete_icon=(ImageView) v.findViewById(R.id.deleteBtn);
            item_category_layout = (RelativeLayout) v.findViewById(R.id.item_category_layout);




            item_category_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {

                        if(mCategory.get(getLayoutPosition()).getCategoryImage()!=null){
                            imageBitmap =  BitmapManager.byteToBitmap(mCategory.get(getLayoutPosition()).getCategoryImage());
                            Constants.categoryBitmap=imageBitmap;
                        }
                        else {
                            Constants.categoryBitmap=null;
                        }

                        cat_id=mCategory.get(getLayoutPosition()).getCategoryId();
                        category_name=mCategory.get(getLayoutPosition()).getCategoryName();
//                        catIcon=mCategory.get(getLayoutPosition()).getCategoryImage();
                        catColor=mCategory.get(getLayoutPosition()).getCategoryColour();

                        clickListener.onItemClicked(getLayoutPosition(),cat_id,category_name,catColor);

                    }
                }
            });

            delete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap imageBitmap=  BitmapManager.byteToBitmap(mCategory.get(getLayoutPosition()).getCategoryImage());
                    cat_id=mCategory.get(getLayoutPosition()).getCategoryId();
                    category_name=mCategory.get(getLayoutPosition()).getCategoryName();
                    Constants.categoryBitmap=imageBitmap;
//                    catIcon=mCategory.get(getLayoutPosition()).getCategoryIcon();
                    clickListener.onItemDeleteClicked(getLayoutPosition(),cat_id,category_name);
                }
            });

            item_category_layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if(clickListener!=null){
                        clickListener.onItemLongClicked(mCategory.get(getLayoutPosition()).getCategoryId(),mCategory.get(getLayoutPosition()).getCategoryName());
                    }

                    return false;
                }
            });



        }

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if(mCategory!=null){
            Categories current = mCategory.get(position);
            if(current.getCategoryImage()!=null){

                Bitmap image = BitmapManager.byteToBitmap(current.getCategoryImage());
                holder.cat_icon.setImageBitmap(image);
            }
            else {

                holder.cat_icon.setVisibility(View.GONE);
            }

            holder.cat_name.setText(current.getCategoryName());



        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void  setmCategory(List<Categories> categories){
        mCategory = categories;
//        notify();
        notifyDataSetChanged();
    }



    public interface ClickListener {

        public void onItemClicked(int position,int id,String categoryName,int catColor);


        void onItemDeleteClicked(int layoutPosition, int cat_id, String category_name);

        public void onItemLongClicked(int categoryId,String category_name);


    }

    @Override
    public int getItemCount() {
        if (mCategory != null)
            return mCategory.size();
        else return 0;
    }

    public Categories getCategoryAt(int position) {
        return mCategory.get(position);
    }
}