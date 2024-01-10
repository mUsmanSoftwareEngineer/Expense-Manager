package myapps.expensetracker.spendingmanager.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.CountryAndCurrenciesPickerListener;
import com.scrounger.countrycurrencypicker.library.CountryCurrencyPicker;
import com.scrounger.countrycurrencypicker.library.Currency;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.utilities.AppUtils;

/**
 * Created by hsn on 28/11/2017.
 */


public class IntroAdapter extends PagerAdapter {
    TextView timeText;
    RelativeLayout currencyRelative,cashRelative;
    private List<Integer> audioList = new ArrayList<Integer>();
    private final Context mContext;

    TextView currency_one,currency_two;
    EditText pre_save_amount;

    String preSaveAmount;

    public IntroAdapter(Context mContext, List<Integer> stringList) {
        this.audioList = stringList;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return audioList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_slide_1, container, false);
        switch (audioList.get(position)) {
            case 1:
                view = layoutInflater.inflate(R.layout.item_slide_1, container, false);
                timeText = view.findViewById(R.id.time);
                Calendar c = Calendar.getInstance();
                int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

                if (timeOfDay >= 0 && timeOfDay < 12) {
                    timeText.setText(mContext.getResources().getString(R.string.morning));
                } else if (timeOfDay >= 12 && timeOfDay < 16) {
                    timeText.setText(mContext.getResources().getString(R.string.afternoon));
                } else if (timeOfDay >= 16 && timeOfDay < 21) {
                    timeText.setText(mContext.getResources().getString(R.string.evening));
                } else if (timeOfDay >= 21 && timeOfDay < 24) {
                    timeText.setText(mContext.getResources().getString(R.string.night));
                }

                break;
            case 2:
                view = layoutInflater.inflate(R.layout.item_slide_2, container, false);
                currencyRelative = view.findViewById(R.id.change_currency);
                currency_one=view.findViewById(R.id.currency_symbol_sel);
                currency_two=view.findViewById(R.id.currency_symbol);
                pre_save_amount=view.findViewById(R.id.pre_save_amount);
                cashRelative=view.findViewById(R.id.cash_relative);


                pre_save_amount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                       preSaveAmount=s.toString();
                       if(!preSaveAmount.isEmpty()){
                           AppPreference.getInstance(mContext).setFloat(PrefKey.previous_amount,Float.valueOf(preSaveAmount));
                       }
                    }
                });

                currencyRelative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance(new CountryAndCurrenciesPickerListener() {

                            @Override

                            public void onSelect(Country country, Currency currency) {


//                        Toast.makeText(mContext,
//
//                                String.format("name: %s\ncurrencySymbol: %s", country.getName(), currency.getSymbol())
//
//                                , Toast.LENGTH_SHORT).show();

//                        String symbol=currency.getSymbol();

//                        Constants.currency_symbols=currency.getSymbol();
                                Constants.checkCurrencySymbol = true;
                                cashRelative.setVisibility(View.VISIBLE);
                                AppPreference.getInstance(mContext).setString(PrefKey.currencySymbol, currency.getSymbol());
                                currency_one.setText(currency.getSymbol());
                                currency_two.setText(currency.getSymbol());

                                DialogFragment dialogFragment =
                                        (DialogFragment) ((FragmentActivity)mContext).getSupportFragmentManager().findFragmentByTag(CountryCurrencyPicker.DIALOG_NAME);
                                dialogFragment.dismiss();


                                                                                                   }


                                                                                               }
                        );

                        pickerDialog.show(((FragmentActivity)mContext).getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);
                        pickerDialog.setDialogTitle(mContext.getString(R.string.country_currency_dialog_title));




                    }


                });
                break;
            case 3:
                view = layoutInflater.inflate(R.layout.item_slide_3, container, false);
                break;
            case 4:
                view = layoutInflater.inflate(R.layout.item_slide_4, container, false);
                break;
          /*  case 5:
                view = layoutInflater.inflate(R.layout.item_slide_6, container, false);
                break;*/
        /*    case 5:
                view = layoutInflater.inflate(R.layout.item_slide_7, container, false);
                break;
            case 6:
                view = layoutInflater.inflate(R.layout.item_slide_8, container, false);
                break;*/
        }


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }


    @Override
    public float getPageWidth(int position) {
        return 1f;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);

    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
