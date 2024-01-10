package myapps.expensetracker.spendingmanager.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.entity.Spending;
import myapps.expensetracker.spendingmanager.libs.BitmapManager;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.HistoryViewHolder> {

    private Context mContext;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
    private List<Spending> mSpending;
    String dateFormat;
    private ClickListener clickListener;

    Date adapterDate=null;

    int transaction_category_id,transaction_type_id,transaction_account_id,transaction_id;

    String transaction_category_name,transaction_type_name,transaction_account_name,transaction_date_db;
    String transactionNotes=null;

    SimpleDateFormat passDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

    double transaction_amount_db;
    private List<Categories> mCategory;

    Bitmap image=null;
    byte[] img;


    public TransactionAdapter(Context context) {
        this.mContext = context;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @NotNull
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout, parent, false);
        return new HistoryViewHolder(view, viewType);
    }


    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private final ImageView history_icon;
        private final TextView cat_type,transaction_date,transaction_amount,transaction_currency_symbol,cat_note;

        private LinearLayout item_category_layout;

        public HistoryViewHolder(View v, int viewType) {
            super(v);

            history_icon = (ImageView) v.findViewById(R.id.spending_icon);
            transaction_amount = (TextView) v.findViewById(R.id.txt_amount);
            cat_type=(TextView) v.findViewById(R.id.spending_cat_name);
            cat_note=(TextView) v.findViewById(R.id.spending_notes);
            transaction_date=(TextView) v.findViewById(R.id.txt_date);
            transaction_currency_symbol=(TextView) v.findViewById(R.id.currency_symbol);
            item_category_layout = (LinearLayout) v.findViewById(R.id.transaction_linear);

            item_category_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (clickListener != null) {

                        transaction_id=mSpending.get(getLayoutPosition()).getSpendingId();
                        transaction_category_id=mSpending.get(getLayoutPosition()).getSpendingCategoryId();
                        transaction_type_id=mSpending.get(getLayoutPosition()).getSpendingType();
                        transaction_account_id=mSpending.get(getLayoutPosition()).getSpendingAccountHolderId();
                        transaction_amount_db=mSpending.get(getLayoutPosition()).getSpendingAmount();
                        transaction_date_db=dateFormat;
                        transactionNotes=mSpending.get(getLayoutPosition()).getSpendingNotes();

                        img=mSpending.get(getLayoutPosition()).getSpendingImage();
                        if(img!=null){
                            Constants.TransactionBitmap=BitmapManager.byteToBitmap(img);
                        }
                        else {
                            Constants.TransactionBitmap=null;
                        }

                        clickListener.onItemClicked(getLayoutPosition(),transaction_id,transaction_category_id,transaction_type_id,transaction_account_id,transaction_amount_db,transaction_date_db,transactionNotes);


                    }
                }
            });


            item_category_layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if (clickListener != null) {
                        clickListener.onItemLongClicked(mSpending.get(getLayoutPosition()).getSpendingId());
                    }
                    return false;
                }
            });

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

        holder.cat_type.setText("");

        if(mSpending!=null){
            Spending current = mSpending.get(position);

            if(current.getSpendingAmount()!=0.0){
                holder.transaction_amount.setText(current.getSpendingAmount()+"");
            }

            adapterDate=current.getTransactionDate();
            dateFormat=passDateFormat.format(adapterDate);
            if(adapterDate!=null){
                SimpleDateFormat sdf = new SimpleDateFormat("EEE");
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM");
                String dayOfTheWeek = sdf.format(adapterDate);
                String date=sdf1.format(adapterDate);

                holder.transaction_date.setText(date+", "+ dayOfTheWeek);
            }

            if(current.getSpendingNotes()!=null){
                holder.cat_note.setText(current.getSpendingNotes());
                transactionNotes=current.getSpendingNotes();
            }
            else {
                holder.cat_note.setVisibility(View.GONE);
            }



            holder.transaction_currency_symbol.setText(Constants.currency_symbols);



            if(mCategory!=null){
                for(Categories item : mCategory) {

                    if(item.getCategoryId()==current.getSpendingCategoryId()) {
                        // here you are getting item which matches inside your list
                        if(item.getCategoryImage()!=null){
                            Bitmap image= BitmapManager.byteToBitmap(item.getCategoryImage());
                            holder.history_icon.setImageBitmap(image);
                        }
                        else {
                            holder.history_icon.setVisibility(View.GONE);
                        }

                        holder.cat_type.setText(item.getCategoryName());
                    }
                }
            }


        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void  setTransaction(List<Spending> spendings){
        mSpending = spendings;

        notifyDataSetChanged();
    }

    public interface ClickListener {

        void onItemClicked(int layoutPosition, int transaction_id, int transaction_category_id, int transaction_type_id, int transaction_account_id, double transaction_amount, String transaction_date,String transactionNotes);
        public void onItemLongClicked(int transactionId);

    }

    @Override
    public int getItemCount() {
        if (mSpending != null)
            return mSpending.size();
        else return 0;
    }

    public void  setmCategory(List<Categories> categories){
        mCategory = categories;
        notifyDataSetChanged();
    }

    public Spending getSpendingAt(int position) {
        return mSpending.get(position);
    }
}