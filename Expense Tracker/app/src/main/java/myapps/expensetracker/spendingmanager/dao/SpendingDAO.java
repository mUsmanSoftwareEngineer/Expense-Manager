package myapps.expensetracker.spendingmanager.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import myapps.expensetracker.spendingmanager.entity.Spending;

@Dao
public interface SpendingDAO {

    @Insert
    void insert(Spending spending);

    @Update
    void update(Spending spending);

    @Delete
    void delete(Spending spending);

    @Query("DELETE FROM spending")
    void deleteAll();


    @Query("DELETE FROM spending WHERE spending_account_holder_id =:account_id")
    void deleteById(int account_id);

    @Query("DELETE FROM spending WHERE spending_category_id =:categoryId")
    void deleteByIdCategory(int categoryId);

    @Query("SELECT * from spending")
    LiveData<List<Spending>> getAllSpending();

    @Query("SELECT COUNT(*) FROM spending")
    LiveData<Integer> getCount();

    //monthly basis Queries
    @Query("SELECT SUM(spending_amount) as total FROM spending where strftime('%Y-%m', transactionDate) =:incomeDate AND spending_type=:spendingType AND spending_account_holder_id=:accountId")
    LiveData<Float> getTotalIncomeMonthly(String incomeDate,int spendingType,int accountId);

    @Query("SELECT SUM(spending_amount) as totalExpense FROM spending where strftime('%Y-%m', transactionDate) =:ExpenseDate  AND spending_type=:ExpenseSpendingType AND spending_account_holder_id=:ExpenseAccountId Group BY transactionDate")
    LiveData<Double> getTotalExpenseMonthly(String ExpenseDate,int ExpenseSpendingType,int ExpenseAccountId);

    @Query("SELECT SUM(spending_amount) FROM spending WHERE strftime('%Y-%m', transactionDate) BETWEEN  :startDate AND :endDate AND spending_type=:ExpenseSpendingType AND spending_account_holder_id=:ExpenseAccountId")
    LiveData<Float> getTotalExpense(String startDate,String endDate,int ExpenseSpendingType,int ExpenseAccountId);

    @Query("SELECT * from spending WHERE strftime('%Y-%m', transactionDate) =:spendingDate AND spending_type=:spendingExpenseType AND spending_account_holder_id=:SpendingAccountId")
    LiveData<List<Spending>> getAllExpenseMonthly(String spendingDate,int spendingExpenseType,int SpendingAccountId);

    @Query("SELECT * from spending WHERE spending_account_holder_id=:SpendingAccountId")
    LiveData<List<Spending>> getAllExpenseMonthlyHistory(int SpendingAccountId);

    @Query("SELECT SUM(spending_amount) from spending WHERE strftime('%Y-%m', transactionDate) =:spendingDate AND spending_type=:spendingExpenseType AND spending_account_holder_id=:SpendingAccountId AND spending_category_id=:spendingCategoryId GROUP BY spending_category_id")
    LiveData<Float> getLastSixMonthExpAmount(String spendingDate,int spendingExpenseType,int SpendingAccountId,int spendingCategoryId);

    @Query("SELECT SUM(spending_amount) from spending WHERE strftime('%Y-%m-%d', transactionDate) =:spendingDate AND spending_type=:spendingExpenseType AND spending_account_holder_id=:SpendingAccountId")
    LiveData<Float> getDataDayWise(String spendingDate,int spendingExpenseType,int SpendingAccountId);

    @Query("SELECT SUM(spending_amount) FROM spending WHERE strftime('%Y-%m', transactionDate) BETWEEN :startingMonth AND :currentMonth AND spending_category_id=:spendingCategoryID  AND spending_type=:spendingExpenseType AND spending_account_holder_id=:SpendingAccountId")
    LiveData<Float> getLastSixMonthExpCategoryOneByOne(String startingMonth,String currentMonth,int spendingCategoryID,int spendingExpenseType,int SpendingAccountId);

    @Query("SELECT * FROM spending WHERE strftime('%Y-%m', transactionDate) BETWEEN :startingMonth AND :currentMonth AND spending_account_holder_id=:spendingAccountId")
    LiveData<List<Spending>> getAllSpending(String startingMonth, String currentMonth, int spendingAccountId);

    @Query("SELECT * FROM spending WHERE strftime('%Y-%m', transactionDate) =:spendingDate AND spending_category_id=:spendingSpendingCategoryId AND spending_account_holder_id=:SpendingAccountId")
    LiveData<List<Spending>> getTransactionByCategory(String spendingDate,int spendingSpendingCategoryId,int SpendingAccountId);

}
