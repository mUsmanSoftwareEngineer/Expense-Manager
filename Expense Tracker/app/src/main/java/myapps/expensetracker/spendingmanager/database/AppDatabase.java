package myapps.expensetracker.spendingmanager.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import myapps.expensetracker.spendingmanager.dao.AccountDAO;
import myapps.expensetracker.spendingmanager.dao.BudgetDAO;
import myapps.expensetracker.spendingmanager.dao.CategoryDAO;
import myapps.expensetracker.spendingmanager.dao.SpendingDAO;
import myapps.expensetracker.spendingmanager.entity.Accounts;
import myapps.expensetracker.spendingmanager.entity.Budget;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.entity.Spending;

@Database(entities = {Categories.class , Accounts.class, Spending.class, Budget.class}, version = 2,exportSchema = false)

public abstract  class AppDatabase extends RoomDatabase {


    public abstract CategoryDAO categoryDAO();
    public abstract AccountDAO accountDAO();
    public abstract SpendingDAO spendingDAO();
    public abstract BudgetDAO budgetDAO();


    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
