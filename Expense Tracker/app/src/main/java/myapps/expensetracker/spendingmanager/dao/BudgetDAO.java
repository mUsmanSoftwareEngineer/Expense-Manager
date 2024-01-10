package myapps.expensetracker.spendingmanager.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import myapps.expensetracker.spendingmanager.entity.Budget;
import myapps.expensetracker.spendingmanager.entity.Spending;

@Dao
public interface BudgetDAO {

    @Insert
    void insert(Budget budget);

    @Update
    void update(Budget budget);

    @Delete
    void delete(Budget budget);

    @Query("SELECT * from budget WHERE strftime('%Y-%m', budgetDate) =:budgetDate  AND budget_account_id=:BudgetAccountId")
    LiveData<List<Budget>> getBudgetMonthly(String budgetDate,int BudgetAccountId);


}
