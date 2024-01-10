package myapps.expensetracker.spendingmanager.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import myapps.expensetracker.spendingmanager.dao.AccountDAO;
import myapps.expensetracker.spendingmanager.entity.Accounts;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.entity.Spending;

public class AccountRepository {

    private AccountDAO accountDAO;
    private LiveData<List<Accounts>> mAllAccounts;


    AppDatabase db;


   public AccountRepository(Application application) {
         db = AppDatabase.getDatabase(application);
       accountDAO = db.accountDAO();
       mAllAccounts = accountDAO.getAllAccounts();

   }



    public LiveData<List<Accounts>> getAccount(int accountId) {

        return accountDAO.getAccount(accountId);

    }

    public LiveData<List<Accounts>> getAllAccounts() {
        return mAllAccounts;
    }



//
//    public List<Accounts> getAllUsers(){
//        return (List<Accounts>) new GetUsersAsyncTask().execute();
//    }
//
//
//    private class GetUsersAsyncTask extends AsyncTask<Void, Void,List<Accounts>>
//    {
//        @Override
//        protected List<Accounts> doInBackground(Void... url) {
//            return db.accountDAO().getAllAccountsPlain();
//        }
//    }

    public LiveData<Integer> getCount() {
        return accountDAO.getCount();
    }

    public int countUsers(){
        return accountDAO.countUsers();
    }

    public void insert (Accounts accounts) {
        new insertAsyncTask(accountDAO).execute(accounts);
    }

//    public void get () {
//        new getAsyncTask(accountDAO).execute();
//    }

    public void insertCategoriesList (List<Categories> categories) {

//               new insertAsyncTask(categoryDAO).execute((Runnable) categories);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    db.categoryDAO().insertCatList(categories);
                }
            }).start();

    }

    public void delete(Accounts accounts) {
        new DeleteProductAsyncTask(accountDAO).execute(accounts);
    }

    public void update(Accounts accounts) {
        new UpdateProductAsyncTask(accountDAO).execute(accounts);
    }



    private static class insertAsyncTask extends AsyncTask<Accounts, Void, Void> {

        private AccountDAO accountDAO;

        insertAsyncTask(AccountDAO dao) {
            accountDAO = dao;
        }

        @Override
        protected Void doInBackground(final Accounts... params) {
            accountDAO.insert(params[0]);
            return null;
        }
    }

    private static class UpdateProductAsyncTask extends AsyncTask<Accounts, Void, Void> {
        private AccountDAO accountDAO;

        private UpdateProductAsyncTask(AccountDAO accountDAO) {
            this.accountDAO = accountDAO;
        }

        @Override
        protected Void doInBackground(Accounts... accounts) {
            accountDAO.update(accounts[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<Accounts, Void, Void> {
        private AccountDAO accountDAO;

        private DeleteProductAsyncTask(AccountDAO accountDAO) {
            this.accountDAO = accountDAO;
        }

        @Override
        protected Void doInBackground(Accounts... accounts) {
            accountDAO.delete(accounts[0]);
            return null;
        }
    }
}
