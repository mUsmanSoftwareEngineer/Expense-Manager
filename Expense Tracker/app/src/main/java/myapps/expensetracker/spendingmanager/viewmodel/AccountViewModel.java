package myapps.expensetracker.spendingmanager.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import myapps.expensetracker.spendingmanager.database.AccountRepository;
import myapps.expensetracker.spendingmanager.entity.Accounts;
import myapps.expensetracker.spendingmanager.entity.Spending;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository mRepository;

    private LiveData<List<Accounts>> mAllAccounts;


    private final MutableLiveData<Integer> currentCount = new MutableLiveData<>();

    public AccountViewModel(Application application) {
        super(application);
        mRepository = new AccountRepository(application);
        mAllAccounts = mRepository.getAllAccounts();

    }

    public LiveData<List<Accounts>> getAllAccounts() { return mAllAccounts; }

    public LiveData<List<Accounts>> getAccount(int accountID) {

        return mRepository.getAccount(accountID);

    }

    public LiveData<Integer> getCount() { return mRepository.getCount(); }

    public void insert(Accounts accounts) { mRepository.insert(accounts); }
    public void update(Accounts accounts) { mRepository.update(accounts); }
    public void delete(Accounts accounts) { mRepository.delete(accounts); }
}