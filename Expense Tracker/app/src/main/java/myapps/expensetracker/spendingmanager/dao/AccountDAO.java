package myapps.expensetracker.spendingmanager.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import myapps.expensetracker.spendingmanager.entity.Accounts;
import myapps.expensetracker.spendingmanager.entity.Categories;

@Dao
public interface AccountDAO {

    @Insert
    void insert(Accounts accounts);

    @Update
    void update(Accounts accounts);

    @Delete
    void delete(Accounts accounts);

    @Query("DELETE FROM accounts")
    void deleteAll();

    @Query("SELECT * from accounts ORDER BY accountId ASC")
    LiveData<List<Accounts>> getAllAccounts();

    @Query("SELECT * from accounts ORDER BY accountId ASC")
    List<Accounts> getAllAccountsPlain();

    @Query("SELECT * from accounts Where accountId=:accId")
    LiveData<List<Accounts>> getAccount(int accId);

    @Query("SELECT COUNT(*) FROM accounts")
    LiveData<Integer> getCount();

    @Query("SELECT COUNT(*) FROM accounts")
    Integer getCountForever();

    @Query("SELECT COUNT(*) FROM accounts")
    int countUsers();

//    @Query("SELECT * from categories WHERE category_type = :categoryType")
//    LiveData<List<Categories>> getAllExpenseMonthlyCategories(int categoryType);
//
//   @Insert
//   void  insertCatList(List<Categories> categoriesList);
}
