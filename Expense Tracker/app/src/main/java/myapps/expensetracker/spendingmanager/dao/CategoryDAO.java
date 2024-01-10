package myapps.expensetracker.spendingmanager.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import myapps.expensetracker.spendingmanager.entity.Categories;

@Dao
public interface CategoryDAO {
    @Insert
    void insert(Categories categories);

    @Update
    void update(Categories categories);

    @Delete
    void delete(Categories categories);

    @Query("DELETE FROM categories")
    void deleteAll();

    @Query("SELECT * from categories")
    LiveData<List<Categories>> getAllCategories();

    @Query("SELECT * from categories Where categoryId=:categoryId")
    LiveData<List<Categories>> getSingleCategories(int categoryId);

    @Query("SELECT * from categories WHERE category_type = :categoryType")
    LiveData<List<Categories>> getAllExpenseMonthlyCategories(int categoryType);

    @Insert
    void  insertCatList(List<Categories> categoriesList);


}
