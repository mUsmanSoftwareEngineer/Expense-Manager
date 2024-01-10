package myapps.expensetracker.spendingmanager.adapters;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.activities.Add_Account;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.models.CategoryModel;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.entity.Accounts;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.libs.BitmapManager;
import myapps.expensetracker.spendingmanager.utilities.ActivityUtils;


public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private Context mContext;
//    private ArrayList<CategoryModel> mList;
    private List<Accounts> mAccounts;

    private ClickListener clickListener;

    int account_id,account_color;

    String account_name;

    private CompoundButton lastCheckedRB = null;

    int selPos=0;
    public int selectedPosition  = -1;
    private static int sSelected = 0;
    public AccountAdapter(Context context) {
        this.mContext = context;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @NotNull
    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_layout, parent, false);
        return new AccountViewHolder(view, viewType);
    }


    public class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView account_edit_icon;
        CircularImageView account_icon;
        private  TextView account_name;
        private RadioButton radioButton;
        private RelativeLayout item_account_layout,radioRelative;


        public AccountViewHolder(View v, int viewType) {
            super(v);
            account_icon = (CircularImageView) v.findViewById(R.id.acc_img);
            account_name = (TextView) v.findViewById(R.id.acc_name);
            account_edit_icon= (ImageView) v.findViewById(R.id.edit_acc);
            radioButton=(RadioButton) v.findViewById(R.id.rb);
            item_account_layout=(RelativeLayout) v.findViewById(R.id.accountItemLayout);
            radioRelative=(RelativeLayout) v.findViewById(R.id.radio_relative);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sSelected = getAdapterPosition();
                    clickListener.onRadioClickListener(getAdapterPosition(),v);
                }
            });


//            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
//                        selectedPosition=getLayoutPosition();
//                    }
//                    else {
//                        selectedPosition=-1;
//                    }
//
//                }
//            });

//            radioRelative.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    selectedPosition=getLayoutPosition();
//                    sSelected = getAdapterPosition();
//                    clickListener.onRadioClickListener(getAdapterPosition(),v);
//
//                }
//            });


//            v.setOnClickListener((View.OnClickListener) mContext);

            item_account_layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (clickListener != null) {
                        clickListener.onItemLongClicked(mAccounts.get(getLayoutPosition()).getAccountId());
                    }
                    return false;
                }
            });






        }

        @Override
        public void onClick(View v) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {


        if(mAccounts!=null){
            Accounts current = mAccounts.get(position);

            Bitmap image= BitmapManager.byteToBitmap(current.getImage());
            String accName=current.getAccountName();
            holder.account_name.setText(current.getAccountName());


            holder.account_icon.setImageBitmap(image);

            holder.account_edit_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Constants.accountBitmap=image;
                    ActivityUtils.getInstance().startActivity(mContext, Add_Account.class, Constants.Account_UPDATE_FRAGMENT,current.getAccountId(),accName);
                }
            });

            if (sSelected == position) {
                holder.radioButton.setChecked(true);
                AppPreference.getInstance(mContext).setInteger(PrefKey.accountSelection,current.getAccountId());
//                Constants.accountId=current.getAccountId();

            } else {
                holder.radioButton.setChecked(false);
            }



        }

    }

    public void selectedItem() {
        notifyDataSetChanged();
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void  setAccount(List<Accounts> accounts){
        mAccounts = accounts;
//        notify();
        notifyDataSetChanged();
    }



    public interface ClickListener {

        void onItemClicked(int layoutPosition, int account_id, String account_name, int account_color);

        void onDeleteClicked(int layoutPosition, int account_id, String account_name, int account_color);
        void onRadioClickListener(int position, View view);
        public void onItemLongClicked(int accountId);
    }

    @Override
    public int getItemCount() {
        if (mAccounts != null)
            return mAccounts.size();
        else return 0;
    }

    public Accounts getAccountAt(int position) {
        return mAccounts.get(position);
    }
}