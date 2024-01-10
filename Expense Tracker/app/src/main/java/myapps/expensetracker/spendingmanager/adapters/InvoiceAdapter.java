package myapps.expensetracker.spendingmanager.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.models.SpendingModel;
import myapps.expensetracker.spendingmanager.entity.Accounts;
import myapps.expensetracker.spendingmanager.libs.BitmapManager;


public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    private Context mContext;
//    private ArrayList<CategoryModel> mList;
    private List<SpendingModel> mModel;

    private ClickListener clickListener;

    public InvoiceAdapter(Context context) {
        this.mContext = context;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @NotNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_layout, parent, false);
        return new InvoiceViewHolder(view, viewType);
    }


    public class InvoiceViewHolder extends RecyclerView.ViewHolder {

   
        private  TextView invoice_name,invoice_amount,invoiceCurrencySymbol;
        private LinearLayout item_category_layout;
        CardView bgCard;
        ImageView img;

        public InvoiceViewHolder(View v, int viewType) {
            super(v);
            
            invoice_name = (TextView) v.findViewById(R.id.invoice_category_name);
            invoice_amount=(TextView) v.findViewById(R.id.invoice_currecny_amount);
            bgCard=(CardView) v.findViewById(R.id.background_card);
            img=(ImageView) v.findViewById(R.id.invoice_category_icon);
            invoiceCurrencySymbol=(TextView) v.findViewById(R.id.invoice_currency_symbols);

            item_category_layout = (LinearLayout) v.findViewById(R.id.invoice_linear);



            item_category_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   

                    if (clickListener != null) {

                       

                    }
                }
            });


           




        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(InvoiceViewHolder holder, int position) {

        holder.invoice_name.setText(mModel.get(position).getCategoryName());
        holder.invoice_amount.setText(mModel.get(position).getAmount().toString());
        holder.bgCard.setCardBackgroundColor(mModel.get(position).getCategoryColor());
        if(mModel.get(position).getCategoryIcon()!=null){
            Bitmap image= BitmapManager.byteToBitmap(mModel.get(position).getCategoryIcon());
            holder.img.setImageBitmap(image);
        }
        else {
            holder.img.setVisibility(View.GONE);
        }

        holder.invoiceCurrencySymbol.setText(Constants.currency_symbols);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }





    public interface ClickListener {

        void onItemClicked(int layoutPosition, int account_id, String account_name, int account_color);

     
    }

    @Override
    public int getItemCount() {
       return mModel.size();
    }

    public InvoiceAdapter(Context mContext, List<SpendingModel> mModel) {
        this.mContext = mContext;
        this.mModel = mModel;
    }
}