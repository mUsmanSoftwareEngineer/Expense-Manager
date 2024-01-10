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

import androidx.room.Room;

import com.google.android.gms.common.api.ApiException;
import com.google.api.services.drive.Drive;

import java.io.File;

import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.app.App;
import myapps.expensetracker.spendingmanager.database.AppDatabase;
import myapps.expensetracker.spendingmanager.google.GoogleDriveActivity;
import myapps.expensetracker.spendingmanager.google.GoogleDriveApiDataRepository;
import myapps.expensetracker.spendingmanager.utilities.DBConstants;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;
import static myapps.expensetracker.spendingmanager.activities.Settings_Screen.GOOGLE_DRIVE_DB_NAME;


public class RestoreDBScreen extends GoogleDriveActivity {

    AlertDialog alert;
    private Activity mActivity;
    private Context mContext;
    private GoogleDriveApiDataRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_dbscreen);


        initVars();
        initViews();
        initFunctionality();
        initListener();
    }


    private void initVars() {
        mActivity = RestoreDBScreen.this;
        mContext = mActivity.getApplicationContext();
    }

    private void initViews() {

    }

    private void initFunctionality() {
        startGoogleDriveSignIn();

    }


    private void initListener() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GOOGLE_SIGN_IN_REQUEST:
                showProgressDialog(mActivity);
                if (repository == null) {
                    showMessage(R.string.message_google_sign_in_failed);
                    return;
                }

                File db = new File(DBConstants.DB_LOCATION);
                db.getParentFile().mkdirs();
                db.delete();
                repository.downloadFile(db, GOOGLE_DRIVE_DB_NAME)
                        .addOnSuccessListener(r -> {
//                            InfoRepository repository = new InfoRepository();
//                            String infoText = repository.getInfo();
//                            inputToDb.setText(infoText);
                            Log.d("chechkDBFile",db.getPath()+"");
                            Room.databaseBuilder(mContext, AppDatabase.class, "Sample.db")
                                    .createFromFile(new File("/data/data/myapps.expensetracker.spendingmanager/databases/app_database"))
                                    .build();
                            alert.dismiss();
                            showMessage("Retrieved");
                        })
                        .addOnFailureListener(e -> {
                            Log.e(LOG_TAG, "error download file", e);
                            showMessage("Error download");
                            alert.dismiss();
                        });
                break;



        }
    }


    private void retrievedMessage()
    {
        alert.dismiss();
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
}