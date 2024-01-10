package myapps.expensetracker.spendingmanager.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.ConsoleHandler;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.entity.Accounts;


public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.AlertViewHolder> {

    private Context mContext;
    //    private ArrayList<CategoryModel> mList;
    private List<Integer> mAccountIcons;

    private DialogAdapter.ClickListener clickListener;

    private static int CheckedPos = 0;

    public DialogAdapter(Context mContext, List<Integer> mAccountIcons) {
        this.mContext = mContext;
        this.mAccountIcons = mAccountIcons;
    }

    public void setClickListener(DialogAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @NotNull
    @Override
    public DialogAdapter.AlertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_icon_item, parent, false);
        return new DialogAdapter.AlertViewHolder(view, viewType);
    }


    public class AlertViewHolder extends RecyclerView.ViewHolder {


//        private TextView alert_account_name;
        ImageView accountIcon;
        RelativeLayout item_category_layout;


        public AlertViewHolder(View v, int viewType) {
            super(v);
//            alert_account_name = (TextView) v.findViewById(R.id.account_name);
            accountIcon=(ImageView) v.findViewById(R.id.select_icon_img);
            item_category_layout = (RelativeLayout) v.findViewById(R.id.select_icon_linear);



            item_category_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (clickListener != null) {

                        clickListener.onItemClicked(getLayoutPosition(),mAccountIcons.get(getLayoutPosition()));

                    }
                }
            });




        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(DialogAdapter.AlertViewHolder holder, int position) {



//            holder.accountIcon.setImageResource();

//        holder.accountIcon.setImageResource(mAccountIcons.get(position));
//            holder.accountIcon.setImageResource(mAccountIcons,);

        Glide.with(mContext).load(mAccountIcons.get(position)).into(holder.accountIcon);

    }

    @Override
    public int getItemCount() {
        return mAccountIcons.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }





    public interface ClickListener {

        void onItemClicked(int layoutPosition, int account_icon);

    }




}
