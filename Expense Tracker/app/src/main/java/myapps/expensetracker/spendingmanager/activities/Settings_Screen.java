package myapps.expensetracker.spendingmanager.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;


import com.google.android.gms.common.api.ApiException;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.util.DateTime;
import com.google.api.services.drive.Drive;
import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.CountryAndCurrenciesPickerListener;
import com.scrounger.countrycurrencypicker.library.CountryCurrencyPicker;
import com.scrounger.countrycurrencypicker.library.Currency;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.google.GoogleDriveActivity;
import myapps.expensetracker.spendingmanager.google.GoogleDriveApiDataRepository;
import myapps.expensetracker.spendingmanager.utilities.ActivityUtils;
import myapps.expensetracker.spendingmanager.utilities.DBConstants;

public class Settings_Screen extends GoogleDriveActivity {

    public static final int REQUEST_CODE_SIGN_IN = 0;
    public static final int REQUEST_CODE_OPENING = 1;
    public static final int REQUEST_CODE_CREATION = 2;
    public static final int REQUEST_CODE_PERMISSIONS = 2;
    private static final String LOG_TAG = "SettingsScreen";
    public static String GOOGLE_DRIVE_DB_NAME = "ExpenseTrackerBackUp";
    //
//    private RemoteBackup remoteBackup;
    ToggleButton darkMode;
    RelativeLayout changeCurrency;
    TextView currencySymbol;
    RelativeLayout uploadDrive, downloadDrive;
    AlertDialog alert;
    private Activity mActivity;
    private Context mContext;
    private GoogleDriveApiDataRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        initVars();
        initViews();
        initFunctionality();
        initListener();

    }

    private void initVars() {
        mActivity = Settings_Screen.this;
        mContext = mActivity.getApplicationContext();

//        remoteBackup = new RemoteBackup(Settings_Screen.this);
    }


    private void initViews() {

        darkMode = findViewById(R.id.chkState);
        changeCurrency = findViewById(R.id.change_currency);
        currencySymbol = findViewById(R.id.currency_symbol);
        uploadDrive = findViewById(R.id.upload_drive_backup);
        downloadDrive = findViewById(R.id.restore_drive_backup);
    }

    private void initFunctionality() {

        Toast.makeText(mActivity, new DateTime(new Date())+"", Toast.LENGTH_SHORT).show();
        currencySymbol.setText(Constants.currency_symbols);
        darkMode.setChecked(Constants.checkDarkMode);

    }


    private void initListener() {

        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    AppPreference.getInstance(mContext).setBoolean(PrefKey.enableDarkMode, isChecked);
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_YES);
                } else {
                    AppPreference.getInstance(mContext).setBoolean(PrefKey.enableDarkMode, isChecked);
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_NO);
                }

            }
        });

        changeCurrency.setOnClickListener(new View.OnClickListener() {
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
                                                                                               Constants.resume = true;

                                                                                               AppPreference.getInstance(mContext).setString(PrefKey.currencySymbol, currency.getSymbol());

                                                                                               currencySymbol.setText(currency.getSymbol());
                                                                                               DialogFragment dialogFragment = (DialogFragment) getSupportFragmentManager().findFragmentByTag(CountryCurrencyPicker.DIALOG_NAME);
                                                                                               dialogFragment.dismiss();
                                                                                           }
                                                                                       }
                );

                pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);
                pickerDialog.setDialogTitle(getString(R.string.country_currency_dialog_title));
            }
        });

        uploadDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGoogleDriveSignIn();
//                remoteBackup.connectToDrive(true);

            }
        });

        downloadDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startGoogleDriveSignIn();
                startActivity(new Intent(mContext,RestoreDBScreen.class));

            }
        });


    }


    public void showProgressDialog(final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customLayoutpermission = vi.inflate(R.layout.progress_dialog, null);
        alert = alertDialog.create();
        alert.setView(customLayoutpermission);
        alert.setCancelable(false);

        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case GOOGLE_SIGN_IN_REQUEST:
                showProgressDialog(mActivity);
                File db = new File(DBConstants.DB_LOCATION);
                if (repository == null) {
                    showMessage(R.string.message_google_sign_in_failed);
                    return;
                }

//                Calendar c=Calendar.getInstance();
//                GOOGLE_DRIVE_DB_NAME=c.getTime()+"BackUpDB";


//                repository.upload();
                Toast.makeText(mActivity, "Upload Success", Toast.LENGTH_SHORT).show();
                repository.uploadFile(db, GOOGLE_DRIVE_DB_NAME)
                        .addOnSuccessListener(r ->
                                alertDismiss())
                        .addOnFailureListener(e -> {
                            Log.e(LOG_TAG, "error upload file", e);
                            showMessage("Error upload");
                            alertDismiss();
                        });
                break;

            case REQUEST_CODE_CREATION:
                // Called after a file is saved to Drive.
//                if (resultCode == RESULT_OK) {
//                    Log.i(TAG, "Backup successfully saved.");
//                    Toast.makeText(this, "Backup successufly loaded!", Toast.LENGTH_SHORT).show();
//                }
                break;

            case REQUEST_CODE_OPENING:
//                if (resultCode == RESULT_OK) {
//                    DriveId driveId = data.getParcelableExtra(
//                            OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID);
//                    remoteBackup.mOpenItemTaskSource.setResult(driveId);
//                } else {
//                    remoteBackup.mOpenItemTaskSource.setException(new RuntimeException("Unable to open file"));
//                }

        }
    }




    private void alertDismiss() {
        alert.dismiss();
        Toast.makeText(mActivity, "Upload Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        ActivityUtils.getInstance().invokeActivity(mActivity, MainActivity.class, 0);
    }

    @Override
    protected void onGoogleDriveSignedInSuccess(Drive driveApi) {

        showMessage(R.string.message_drive_client_is_ready);
        repository = new GoogleDriveApiDataRepository(driveApi);

    }

    @Override
    protected void onGoogleDriveSignedInFailed(ApiException exception) {
        Log.e(LOG_TAG, "error google drive sign in", exception);
    }
}