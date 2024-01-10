package myapps.expensetracker.spendingmanager.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import myapps.expensetracker.spendingmanager.fragments.AccountFragment;
import myapps.expensetracker.spendingmanager.fragments.CategoriesFragment;
//import myapps.expensetracker.spendingmanager.fragments.HistoryFragment;
import myapps.expensetracker.spendingmanager.fragments.HistoryFragment;
import myapps.expensetracker.spendingmanager.fragments.SpendingFragment;
import myapps.expensetracker.spendingmanager.fragments.StatsFragment;

/**
 * Created by Bezruk on 16/10/18.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> mFragmentItems;

    public MainPagerAdapter(FragmentManager fm, ArrayList<String> fragmentItems) {
        super(fm);
        this.mFragmentItems = fragmentItems;
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = null;

        if(i == 0) {
            fragment = new SpendingFragment();
        } else if(i == 1){
            fragment = new HistoryFragment();
        }
        else if(i == 2){
            fragment = new CategoriesFragment();
        }
        else if(i == 3){
            fragment = new StatsFragment();
        }
        else if(i == 4){
            fragment = new AccountFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentItems.size();
    }

}
