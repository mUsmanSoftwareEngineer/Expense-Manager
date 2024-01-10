package myapps.expensetracker.spendingmanager.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import myapps.expensetracker.spendingmanager.dao.BudgetDAO;
import myapps.expensetracker.spendingmanager.dao.SpendingDAO;
import myapps.expensetracker.spendingmanager.entity.Budget;
import myapps.expensetracker.spendingmanager.entity.Spending;

public class BudgetRepository {

    private BudgetDAO budgetDAO;

    AppDatabase db;

    public BudgetRepository(Application application) {

       db = AppDatabase.getDatabase(application);
       budgetDAO = db.budgetDAO();

    }


    public LiveData<List<Budget>> getBudgetMonthly(String date,int accountId) {

        return budgetDAO.getBudgetMonthly(date,accountId);
    }


    public void insert (Budget budget) {
        new insertAsyncTask(budgetDAO).execute(budget);
    }

    public void delete(Budget budget) {
        new DeleteProductAsyncTask(budgetDAO).execute(budget);
    }

    public void update(Budget budget) {
        new UpdateProductAsyncTask(budgetDAO).execute(budget);
    }

    private static class insertAsyncTask extends AsyncTask<Budget, Void, Void> {

        private BudgetDAO budgetDAO;

        insertAsyncTask(BudgetDAO dao) {
            this.budgetDAO = dao;
        }

        @Override
        protected Void doInBackground(final Budget... params) {
            budgetDAO.insert(params[0]);
            return null;
        }
    }

    private static class UpdateProductAsyncTask extends AsyncTask<Budget, Void, Void> {
        private BudgetDAO budgetDAO;

        private UpdateProductAsyncTask(BudgetDAO budgetDAO) {
            this.budgetDAO = budgetDAO;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            budgetDAO.update(budgets[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<Budget, Void, Void> {
        private BudgetDAO budgetDAO;

        private DeleteProductAsyncTask(BudgetDAO budgetDAO) {
            this.budgetDAO = budgetDAO;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            budgetDAO.delete(budgets[0]);
            return null;
        }
    }

}
