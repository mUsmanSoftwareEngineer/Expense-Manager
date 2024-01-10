package myapps.expensetracker.spendingmanager.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;


import myapps.expensetracker.spendingmanager.dao.SpendingDAO;
import myapps.expensetracker.spendingmanager.entity.Spending;

public class SpendingRepository {
    
    private SpendingDAO spendingDAO;
    private LiveData<List<Spending>> mAllSpending;
    private LiveData<List<Spending>> mAllExpenses;

    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();

    AppDatabase db;


   public SpendingRepository(Application application) {
         db = AppDatabase.getDatabase(application);
       spendingDAO = db.spendingDAO();
       mAllSpending = spendingDAO.getAllSpending();


    }

    public MutableLiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public LiveData<List<Spending>> getAllSpending(String startingMonth, String currentMonth, int spendingAccountId) {
        return spendingDAO.getAllSpending(startingMonth,currentMonth,spendingAccountId);
    }

    public LiveData<List<Spending>> getTransactionByCategoryId(String month, int spendingCatId, int spendingAccountId) {
        return spendingDAO.getTransactionByCategory(month,spendingCatId,spendingAccountId);
    }


    public void delById(int id){
       spendingDAO.deleteById(id);
    }

    public void delByIdCategory(int id){
        spendingDAO.deleteByIdCategory(id);
    }

    public LiveData<Float> getTotalIncomeMonthly(String date, int spendingType, int accountId) {
        return spendingDAO.getTotalIncomeMonthly(date,spendingType,accountId);
    }





    public LiveData<Double> getTotalExpenseMonthly(String date, int spendingType, int accountId) {
        return spendingDAO.getTotalExpenseMonthly(date,spendingType,accountId);
    }

    public LiveData<List<Spending>> getExpensesMonthly(String date, int spendingType, int accountId) {

        return spendingDAO.getAllExpenseMonthly(date,spendingType,accountId);
    }

    public LiveData<Float> getTotalExpense(String strtDate, String endDate, int spendingType, int accountId) {

        return spendingDAO.getTotalExpense(strtDate,endDate,spendingType,accountId);
    }

    public LiveData<List<Spending>> getExpensesMonthlyHistory(int accountId) {

        return spendingDAO.getAllExpenseMonthlyHistory(accountId);
    }

    public LiveData<Float> getLastSixMonthAmountExp(String spendingDate, int spendingExpenseType, int SpendingAccountId, int spendingCategoryID) {

        return spendingDAO.getLastSixMonthExpAmount(spendingDate,spendingExpenseType,SpendingAccountId,spendingCategoryID);

    }

    public LiveData<Float> getLastSixMonthExpCategoryOneByOne(String startingMonth, String currentMonth, int spendingCategoryID, int spendingExpenseType, int SpendingAccountId) {

        return spendingDAO.getLastSixMonthExpCategoryOneByOne(startingMonth,currentMonth,spendingCategoryID,spendingExpenseType,SpendingAccountId);

    }

    public LiveData<Float> getDataDayWise(String startingMonth ,int spendingExpenseType, int SpendingAccountId) {

        return spendingDAO.getDataDayWise(startingMonth,spendingExpenseType,SpendingAccountId);

    }



    public void insert (Spending spending) {
        new insertAsyncTask(spendingDAO).execute(spending);
    }



    public void delete(Spending spending) {
        new DeleteProductAsyncTask(spendingDAO).execute(spending);
    }




    public void update(Spending spending) {
        new UpdateProductAsyncTask(spendingDAO).execute(spending);
    }

    private static class getIncomeAsyncTask extends AsyncTask<Spending, Void, Void> {

        private SpendingDAO spendingDAO;

        getIncomeAsyncTask(SpendingDAO dao) {
            spendingDAO = dao;
        }

        @Override
        protected Void doInBackground(final Spending... params) {

            return null;
        }
    }


    void deleteTransaction(Spending spending){
       spendingDAO.delete(spending);
    }

    private static class insertAsyncTask extends AsyncTask<Spending, Void, Void> {

        private SpendingDAO spendingDAO;

        insertAsyncTask(SpendingDAO dao) {
            spendingDAO = dao;
        }

        @Override
        protected Void doInBackground(final Spending... params) {
            spendingDAO.insert(params[0]);
            return null;
        }
    }

    private static class UpdateProductAsyncTask extends AsyncTask<Spending, Void, Void> {
        private SpendingDAO spendingDAO;

        private UpdateProductAsyncTask(SpendingDAO spendingDAO) {
            this.spendingDAO = spendingDAO;
        }

        @Override
        protected Void doInBackground(Spending... spendings) {
            spendingDAO.update(spendings[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<Spending, Void, Void> {
        private SpendingDAO spendingDAO;

        private DeleteProductAsyncTask(SpendingDAO spendingDAO) {
            this.spendingDAO = spendingDAO;
        }

        @Override
        protected Void doInBackground(Spending... spendings) {
            spendingDAO.delete(spendings[0]);
            return null;
        }
    }


//    private static class DeleteTransactionAsyncTask extends AsyncTask<Spending, Void, Void> {
//        private SpendingDAO spendingDAO;
//
//        private DeleteTransactionAsyncTask(SpendingDAO spendingDAO) {
//            this.spendingDAO = spendingDAO;
//        }
//
//        @Override
//        protected Void doInBackground(Spending... spendings) {
//            spendingDAO.deleteById(spendings[0]);
//            return null;
//        }
//    }
}
