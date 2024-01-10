package myapps.expensetracker.spendingmanager.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.models.CategoryModel;
import myapps.expensetracker.spendingmanager.entity.Categories;

import static android.app.Activity.RESULT_OK;

public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.ViewHolder> {

    private Context mContext;
    List<Integer> iconsList;

    private IconsAdapter.ClickListener clickListener;

    int iconsData;

    public IconsAdapter(Context mContext, List<Integer> iconsList) {
        this.mContext = mContext;
        this.iconsList = iconsList;
    }

    public void setClickListener(IconsAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @NotNull
    @Override
    public IconsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icons_layout, parent, false);
        return new IconsAdapter.ViewHolder(view, viewType);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView only_icon;

        private RelativeLayout item_category_layout;


        public ViewHolder(View v, int viewType) {
            super(v);

            only_icon = (ImageView) v.findViewById(R.id.icons);

            item_category_layout = (RelativeLayout) v.findViewById(R.id.icons_id);

            item_category_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, "Item Clicked", Toast.LENGTH_SHORT).show();
                    if (clickListener != null) {

//                            Log.d("imageId",iconsList.get(getLayoutPosition()).intValue()+"");
                            iconsData=iconsList.get(getLayoutPosition());
                            clickListener.onItemClicked(getLayoutPosition(),iconsData);



                    }
                }
            });

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(IconsAdapter.ViewHolder holder, int position) {


            Drawable drawable = mContext.getResources().getDrawable(iconsList.get(position));
            if(drawable!=null){
                holder.only_icon.setImageDrawable(drawable);
            }


    }

    @Override
    public int getItemCount() {
        return iconsList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface ClickListener {

        public void onItemClicked(int position,int id);


    }

}
