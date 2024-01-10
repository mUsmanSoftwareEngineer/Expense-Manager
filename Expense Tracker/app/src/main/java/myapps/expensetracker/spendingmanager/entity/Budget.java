package myapps.expensetracker.spendingmanager.entity;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import myapps.expensetracker.spendingmanager.database.TimestampConverter;


@Entity(tableName = "budget")
public class Budget {

    @PrimaryKey(autoGenerate = true)
    private int budgetId;



    @ColumnInfo(name = "budget_amount")
    private Double budgetAmount;

    @ColumnInfo(name = "budget_account_id")
    private int budgetAccountId;


    @TypeConverters({TimestampConverter.class})
    private Date budgetDate;


//    @ColumnInfo(name = "category_image")
//    private Drawable categoryImg;


    public int getBudgetAccountId() {
        return budgetAccountId;
    }

    public void setBudgetAccountId(int budgetAccountId) {
        this.budgetAccountId = budgetAccountId;
    }

    public Budget(Double budgetAmount, int budgetAccountId, Date budgetDate) {
        this.budgetAmount = budgetAmount;
        this.budgetAccountId = budgetAccountId;
        this.budgetDate = budgetDate;
    }

    public Budget() {

    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public Date getBudgetDate() {
        return budgetDate;
    }

    public void setBudgetDate(Date budgetDate) {
        this.budgetDate = budgetDate;
    }

}
