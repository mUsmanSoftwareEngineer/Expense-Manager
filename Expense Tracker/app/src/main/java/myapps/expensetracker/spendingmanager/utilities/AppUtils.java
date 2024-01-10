package myapps.expensetracker.spendingmanager.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.jetbrains.annotations.NotNull;

import myapps.expensetracker.spendingmanager.R;
//import myapps.expensetracker.spendingmanager.adapters.DialogAdapter;
import myapps.expensetracker.spendingmanager.adapters.DialogAdapter;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.AppPreference;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.entity.Accounts;
import myapps.expensetracker.spendingmanager.viewmodel.AccountViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;


public class AppUtils {

    private static long backPressed = 0;
    private final int IDD_RATE_DIALOG = 0;
    AlertDialog.Builder ad;
    private static int sel=0;


    public static Date getCurrentDateTime(){
        Date currentDate =  Calendar.getInstance().getTime();
        return currentDate;
    }



    public static String getFormattedDateString(Date date) {

        try {

            SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = spf.format(date);

            Date newDate = spf.parse(dateString);
            spf= new SimpleDateFormat("dd/MM/yyyy");
            return spf.format(newDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    public static void tapToExit(Activity activity) {
//        if (backPressed + 2500 > System.currentTimeMillis()) {
//            activity.finishAffinity();
//        } else {
//            showToast(activity.getApplicationContext(), activity.getResources().getString(R.string.tapAgain));
//        }
//        backPressed = System.currentTimeMillis();
//    }

//    public static void showRateDialog(final Activity activity){
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
//
//        alertDialog.setTitle(activity.getResources().getString(R.string.rate_menu_title));
//        alertDialog.setMessage(activity.getResources().getString(R.string.rate_menu_message));
//        //alertDialog.setIcon(R.drawable.save);
//
//        alertDialog.setPositiveButton(activity.getResources().getString(R.string.rate_menu_yes_btn), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // User pressed YES button. Write Logic Here
//                rateThisApp(activity);
//                AppPreference.getInstance(activity.getApplicationContext()).setInteger(PrefKey.RATE_DIALOG_VALUE,1);
//            }
//        });
//
//        alertDialog.setNegativeButton(activity.getResources().getString(R.string.rate_menu_no_btn), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // User pressed No button. Write Logic Here
//                AppPreference.getInstance(activity.getApplicationContext()).setInteger(PrefKey.RATE_DIALOG_VALUE,1);
//                activity.finishAffinity();
//            }
//        });
//
//        alertDialog.setNeutralButton(activity.getResources().getString(R.string.rate_menu_maybe_btn), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // User pressed Cancel button. Write Logic Here
//                activity.finishAffinity();
//            }
//        });
//
//        alertDialog.show();
//    }






//    public static void showPermissionDialog(final Activity activity, Context context){
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
//
//        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View customLayoutpermission = vi.inflate(R.layout.app_permission_dialog, null);
//        AlertDialog alert= alertDialog.create();
//        alert.setView(customLayoutpermission);
//        //alertDialog.setIcon(R.drawable.save);
//        TextView gotIt=(TextView) customLayoutpermission.findViewById(R.id.got);
//        TextView t1,t2,t3,t4,t5,t6,t7,t8;
//        t1=(TextView) customLayoutpermission.findViewById(R.id.txt8);
//        t2=(TextView) customLayoutpermission.findViewById(R.id.txt9);
//        t3=(TextView) customLayoutpermission.findViewById(R.id.txt10);
//        t4=(TextView) customLayoutpermission.findViewById(R.id.txt11);
//        t5=(TextView) customLayoutpermission.findViewById(R.id.txt12);
//        t6=(TextView) customLayoutpermission.findViewById(R.id.txt13);
//        t7=(TextView) customLayoutpermission.findViewById(R.id.txt14);
//
////        t1.setText(  context.getResources().getString(R.string.read));
////        t2.setText( " " + context.getResources().getString(R.string.write));
////        t3.setText(  " " + context.getResources().getString(R.string.vibrate));
////        t4.setText( " " + context.getResources().getString(R.string.camera));
////        t5.setText( " " + context.getResources().getString(R.string.wifi));
////        t6.setText( " " + context.getResources().getString(R.string.contact));
////        t7.setText( " " + context.getResources().getString(R.string.account));
//
//
//
//        gotIt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alert.dismiss();
//            }
//        });
////        alert.setPositiveButton(activity.getResources().getString(R.string.got_it), new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int which) {
////                // User pressed YES button. Write Logic Here
////                alert.dismiss();
////            }
////        });
//
//        alert.show();
//        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//    }

//    public static void showAdDialog(final Activity activity, Context context){
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
//
//        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View customLayoutpermission = vi.inflate(R.layout.ad_dialog_layout, null);
//        AlertDialog alert= alertDialog.create();
//        alert.setView(customLayoutpermission);
//        alert.setCancelable(false);
//
//        alert.show();
//        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.80); //<-- int width=400;
//        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.20);
//        alert.getWindow().setLayout(width, height);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                alert.dismiss();
//            }
//        },2000);
//    }


    public static void showRatingDialog(final Activity activity){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customLayoutpermission = vi.inflate(R.layout.rate_dialog, null);
        AlertDialog alert= alertDialog.create();
        alert.setView(customLayoutpermission);
        alert.setCancelable(false);

        ImageView closeIcon=customLayoutpermission.findViewById(R.id.add_close_img);

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });


        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.80); //<-- int width=400;
//        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.0);
//        alert.getWindow().setLayout(width, height);


    }

        public static void showAccountDialog(final Activity activity, Context context, List<Integer> iconList, ImageView img, LinearLayout linearLayout){


//        Log.d("checkIconList",iconList.size()+"");
        DialogAdapter dialogAdapter=new DialogAdapter(context,iconList);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customLayoutpermission = vi.inflate(R.layout.select_icon_tray, null);
        AlertDialog alert= alertDialog.create();
        alert.setView(customLayoutpermission);
        alert.setCancelable(true);

          RecyclerView setRecycler=customLayoutpermission.findViewById(R.id.iconAccountAdapter);

            setRecycler.setLayoutManager(new GridLayoutManager(context,3));
            setRecycler.setAdapter(dialogAdapter);


            dialogAdapter.setClickListener(new DialogAdapter.ClickListener() {
                @Override
                public void onItemClicked(int layoutPosition, int account_icon) {


                    linearLayout.setVisibility(View.VISIBLE);
                    img.setImageResource(account_icon);
                    Constants.imgDrawable = img.getDrawable();
                    Constants.imgBitmap = ((BitmapDrawable)Constants.imgDrawable).getBitmap();
//                    Constants.imgStr=Constants.convertBitmapToString(Constants.imgBitmap);
//                    Log.d("checkItem",account_icon+"");
                    alert.dismiss();

                }


            });

        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.80); //<-- int width=400;
//        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.20);
//        alert.getWindow().setLayout(width, height);







    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

//    public static void showSettingsRateDialog(final Activity activity){
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//
//        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View customLayout = vi.inflate(R.layout.rate_dilaog_settings, null);
//        //alertDialog.setIcon(R.drawable.save);
//        AlertDialog alertDialog = builder.create();
//        alertDialog.setView(customLayout);
//
//        Button dislike=customLayout.findViewById(R.id.dislike_btn);
//        Button noThanks=customLayout.findViewById(R.id.no_thanks);
//        Button rate=customLayout.findViewById(R.id.rate_me);
//
//        dislike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                alertDialog.setCancelable(true);
//                alertDialog.dismiss();
//            }
//        });
//
//        noThanks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////              alertDialog.setCancelable(true);
//                alertDialog.dismiss();
//            }
//        });
//
//        rate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rateThisApp(activity);
//            }
//        });
//
//        alertDialog.show();
//    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void share(Activity activity, String text) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            sendIntent.setType("text/plain");
            activity.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareApp(Activity activity) {
        share(activity, activity.getResources().getString(R.string.share) + " https://play.google.com/store/apps/details?id=" + activity.getPackageName());
    }

    public static void rateThisApp(Activity activity) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void PrivacyApp(Activity activity) {
//       Intent intent=new Intent(activity, PrivacyPolicy.class);
//       activity.startActivity(intent);


    }






    public static void saveImage(Activity activity, String imageName, Bitmap bitmap) {
        String folderName = Constants.SAVE_TO;
        //to immediately display the saved image in the default gallery

        try {

            OutputStream fos;
            String imagePath;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                imagePath = Environment.DIRECTORY_PICTURES + File.separator + folderName;
                ContentResolver resolver = activity.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageName);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, imagePath);
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
            } else {

                imagePath = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).toString() + File.separator + folderName;
                File file = new File(imagePath);
                if (!file.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    file.mkdir();
                }
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                imageName=imageName+n;
//                Log.d("img007",imageName);
                if(imageName.contains("/") || imageName.contains("/")){
                    imageName=imageName.replace("/","");
                    imageName=imageName.replace("/","");
//                    Log.d("img008",imageName);
                }
//                Log.d("img009",imageName);
                File image = new File(imagePath, imageName + ".png");
                fos = new FileOutputStream(image);
            }

            boolean saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Objects.requireNonNull(fos).flush();
            fos.close();

            if (saved) {
                new SingleMediaScanner(activity.getApplicationContext(), imagePath + File.separator + imageName + ".png");
                Toast.makeText(activity.getApplicationContext(),
                        activity.getString(R.string.saved_to) + " '" + Environment.DIRECTORY_PICTURES + File.separator + folderName + "'",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity.getApplicationContext(), R.string.error_unknown, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), e+"", Toast.LENGTH_SHORT).show();
        }
    }

    public static void shareImage(Activity activity, Bitmap bitmap) {
        try {
            //saving file in cache directory
            File imagesFolder = new File(activity.getCacheDir(), "images");
            if (!imagesFolder.exists()) {
                //noinspection ResultOfMethodCallIgnored
                imagesFolder.mkdir();
            }
            File file = new File(imagesFolder, "shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            stream.flush();
            stream.close();

            //for avoid some problem
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            //get file uri from file provider
            Uri uri = FileProvider.getUriForFile(activity.getApplicationContext(),
                    "app.scanqrcode.generateqrcode.fileprovider", file);

            //share
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

//            List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
//
//            for (ResolveInfo resolveInfo : resInfoList) {
//                String packageName = resolveInfo.activityInfo.packageName;
//                activity.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            }
            activity.startActivity(Intent.createChooser(shareIntent, "Share via"));

        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), e+"share", Toast.LENGTH_SHORT).show();
            Log.i("ShareError", e+"");
        }
    }


    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
        showToast(context, context.getResources().getString(R.string.copied));
    }









//
//    public static void LocalizeApp(Activity mActivity,int selectedIndex) {
//
//
//
//        new MaterialDialog.Builder(mActivity)
//                .title(R.string.localization)
//                .items(R.array.local)
//                .itemsCallbackSingleChoice(selectedIndex, (dialog, view, which, text) -> {
////                    showToast(which + ": " + text);
//
//                    if(which==0){
//                        setLocale(mActivity,"en");
//                        sel=which;
//                        Constants.selectedIndex=which;
//                    }else if(which==1){
//                        setLocale(mActivity,"it");
//                        sel=which;
//                        Constants.selectedIndex=which;
//                    }
//                    else if(which==2){
//                        setLocale(mActivity,"fr");
//                        sel=which;
//                        Constants.selectedIndex=which;
//                    }else if(which==3){
//                        setLocale(mActivity,"hi");
//                        sel=which;
//                        Constants.selectedIndex=which;
//                    }
//                    else if(which==4){
//                        setLocale(mActivity,"ru");
//                        sel=which;
//                        Constants.selectedIndex=which;
//                    }
////                    else if(which==4){
////                        setLocale(mActivity,"ar");
////                        sel=which;
////                        Constants.selectedIndex=which;
////                    }
////                    setLocale(mActivity,"fr");
////                    Toast.makeText(mActivity, which + ": " + text, Toast.LENGTH_SHORT).show();
//                    return true; // allow selection
//                })
//                .positiveText(R.string.md_choose_label)
////                .onPositive(new MaterialDialog.SingleButtonCallback() {
////                    @Override
////                    public void onClick(@NonNull @NotNull MaterialDialog dialog, @NonNull @NotNull DialogAction which) {
////                            AppPreference.getInstance(mActivity.getApplicationContext()).setInteger("selected",sel);
////                    }
////                })
//                .show();
//
//    }

    public static void setLocale(Context context,String lang) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(lang.toLowerCase()));
        resources.updateConfiguration(configuration, displayMetrics);
        configuration.locale = new Locale(lang.toLowerCase());
        resources.updateConfiguration(configuration, displayMetrics);
    }
}


