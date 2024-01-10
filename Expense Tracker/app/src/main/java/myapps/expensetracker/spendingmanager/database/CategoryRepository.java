package myapps.expensetracker.spendingmanager.database;

import android.app.Application;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;



import java.util.List;

import myapps.expensetracker.spendingmanager.dao.CategoryDAO;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.entity.Categories;

public class CategoryRepository {
    private CategoryDAO categoryDAO;
    private LiveData<List<Categories>> mAllProducts;
    private LiveData<List<Categories>> mCategories;
    AppDatabase db;


   public CategoryRepository(Application application) {
         db = AppDatabase.getDatabase(application);
       categoryDAO = db.categoryDAO();
       mAllProducts = categoryDAO.getAllCategories();
       mCategories=categoryDAO.getAllExpenseMonthlyCategories(Constants.catType);
    }

    public LiveData<List<Categories>> getAllCategories() {
        return mAllProducts;
    }

    public LiveData<List<Categories>> getCategoriesByType(int catType) {
        return categoryDAO.getAllExpenseMonthlyCategories(catType);
    }

    public LiveData<List<Categories>> getSingleCategories(int categoryid) {
        return categoryDAO.getSingleCategories(categoryid);
    }




    public void insert (Categories product) {
        new insertAsyncTask(categoryDAO).execute(product);
    }

    public void insertCategoriesList (List<Categories> categories) {

//               new insertAsyncTask(categoryDAO).execute((Runnable) categories);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    db.categoryDAO().insertCatList(categories);
                }
            }).start();

    }

    public void delete(Categories product) {
        new DeleteProductAsyncTask(categoryDAO).execute(product);
    }

    public void update(Categories product) {
        new UpdateProductAsyncTask(categoryDAO).execute(product);
    }

    private static class insertAsyncTask extends AsyncTask<Categories, Void, Void> {

        private CategoryDAO mAsyncTaskDao;

        insertAsyncTask(CategoryDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Categories... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class UpdateProductAsyncTask extends AsyncTask<Categories, Void, Void> {
        private CategoryDAO productDAO;

        private UpdateProductAsyncTask(CategoryDAO productDAO) {
            this.productDAO = productDAO;
        }

        @Override
        protected Void doInBackground(Categories... products) {
            productDAO.update(products[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<Categories, Void, Void> {
        private CategoryDAO productDao;

        private DeleteProductAsyncTask(CategoryDAO productDAO) {
            this.productDao = productDAO;
        }

        @Override
        protected Void doInBackground(Categories... products) {
            productDao.delete(products[0]);
            return null;
        }
    }
}
