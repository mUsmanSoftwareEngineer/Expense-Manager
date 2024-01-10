//package myapps.expensetracker.spendingmanager.adapters;
//
//import android.content.Context;
//import android.os.Build;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.RequiresApi;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.List;
//
//import myapps.expensetracker.spendingmanager.R;
//import myapps.expensetracker.spendingmanager.entity.Accounts;
//import myapps.expensetracker.spendingmanager.entity.Spending;
//
//
//public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
//
//    private Context mContext;
////    private ArrayList<CategoryModel> mList;
//    private List<Spending> mSpending;
//
//    private ClickListener clickListener;
//
//    int account_id,account_color;
//
//    String account_name;
//
//
//    public ExpenseAdapter(Context context) {
//        this.mContext = context;
//    }
//
//    public void setClickListener(ClickListener clickListener) {
//        this.clickListener = clickListener;
//    }
//
//
//    @NotNull
//    @Override
//    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
//        return new ExpenseViewHolder(view, viewType);
//    }
//
//
//    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
//
//
//        private  TextView expense_type,expense_amount;
//        private LinearLayout item_category_layout;
//
//
//        public ExpenseViewHolder(View v, int viewType) {
//            super(v);
//            expense_type = (TextView) v.findViewById(R.id.exp_type);
//            expense_amount = (TextView) v.findViewById(R.id.exp_amount);
//            item_category_layout = (LinearLayout) v.findViewById(R.id.item_category_layout);
//
//
//
//            item_category_layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, "Clicked"+getLayoutPosition(), Toast.LENGTH_SHORT).show();
//
//                    if (clickListener != null) {
//
//
//                    }
//                }
//            });
//
//
//
//
//        }
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
//
//
//        if(mSpending!=null){
//            Spending current = mSpending.get(position);
//
//            holder.expense_type.setText(current.getSpendingCategoryName());
//            holder.expense_amount.setText(current.getSpendingAmount()+"");
//
//
//        }
//
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }
//
//    public void  setExpenses(List<Spending> spendings){
//        mSpending = spendings;
////        notify();
//        notifyDataSetChanged();
//    }
//
//
//
//    public interface ClickListener {
//
//        void onItemClicked(int layoutPosition, int account_id, String account_name, int account_color);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mSpending != null)
//            return mSpending.size();
//        else return 0;
//    }
//
//    public Spending getExpenseAt(int position) {
//        return mSpending.get(position);
//    }
//}