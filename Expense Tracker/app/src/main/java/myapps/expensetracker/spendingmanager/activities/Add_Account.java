package myapps.expensetracker.spendingmanager.activities;

import android.Manifest;
import android.accounts.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.ketzalv.validationedittext.ValidationEditText;
import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.adapters.DialogAdapter;
import myapps.expensetracker.spendingmanager.data.constant.Constants;
import myapps.expensetracker.spendingmanager.data.preference.PrefKey;
import myapps.expensetracker.spendingmanager.entity.Accounts;
import myapps.expensetracker.spendingmanager.entity.Categories;
import myapps.expensetracker.spendingmanager.libs.BitmapManager;
import myapps.expensetracker.spendingmanager.utilities.AppUtils;
import myapps.expensetracker.spendingmanager.utilities.ImageCaptureManager;
import myapps.expensetracker.spendingmanager.viewmodel.AccountViewModel;
import myapps.expensetracker.spendingmanager.viewmodel.CategoriesViewModel;

public class Add_Account extends AppCompatActivity {


    Activity mActivity;
    Context mContext;

    ValidationEditText editText_account_name,getEditText_account_income,getEditText_account_budget;
    ImageView back_icon,add_new_btn;
    TextView toolBarTitle,doneTxt;

    ImageView add_icon,addGalleryImage,addCameraImage,saveBitmap;
    LinearLayout linearLayout;

    Button confirm;

    boolean checkIntent=false;
    private ImageCaptureManager imageCaptureManager;

    List<Integer> accountIcons;
    DialogAdapter dialogAdapter;

    int account_type,account_id,account_income,account_budget;

    String account_name,account_img;

    Bitmap accountBitMap;


    public static final int PICK_IMAGE = 3;

    TextView doneSave,topTextView,entered_coloured;

    private AccountViewModel mAccountViewModel;

//    View addColor;

    RelativeLayout iconTrayRelative;

    String imageString;

    byte[] image;

    public static final int CAMERA_REQUEST_CODE=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        initVars();
        initViews();
        initFunctionality();
        initListeners();

    }



    private void initVars() {

        mActivity=this;
        mContext=getApplicationContext();

        accountIcons=new ArrayList<>();

        mAccountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);

    }



    private void initViews() {

        //EditText
        editText_account_name=(ValidationEditText) findViewById(R.id.edit_account_name);
        getEditText_account_income=(ValidationEditText) findViewById(R.id.total_income);
        getEditText_account_budget=(ValidationEditText) findViewById(R.id.total_budget);



        //ImageViews
        back_icon=(ImageView) findViewById(R.id.back);
        add_new_btn=(ImageView) findViewById(R.id.add_new_cat);
        add_icon=(ImageView) findViewById(R.id.IconImg);
        addGalleryImage=(ImageView) findViewById(R.id.galleryImg);
        addCameraImage=(ImageView) findViewById(R.id.cameraImg);
        saveBitmap=(ImageView) findViewById(R.id.accountImg);


        //TextViews
        toolBarTitle=(TextView) findViewById(R.id.iconTxt);
        doneTxt=(TextView) findViewById(R.id.done);

        //LinearLayout
        linearLayout=(LinearLayout) findViewById(R.id.transaction_linear_6);

        //Button
        confirm=(Button) findViewById(R.id.confirm);

    }



    private void initFunctionality() {

        toolBarTitle.setVisibility(View.VISIBLE);
        toolBarTitle.setText(R.string.account_details);
        add_new_btn.setVisibility(View.GONE);
        doneTxt.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);





        populateAccountIconList();



        Bundle b = getIntent().getExtras();

        if (b != null) {

            account_type = b.getInt(PrefKey.Fragment_position);
            account_id=b.getInt(PrefKey.Account_id);
            account_name=b.getString(PrefKey.Account_name);
//            image=b.getByteArray(PrefKey.Account_img);
        }


        if(account_type == Constants.NEW_Account_Add_FRAGMENT){
            Drawable d = getResources().getDrawable(R.drawable.account);
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            image= BitmapManager.bitmapToByte(bitmap);

        }
        else if(account_type==Constants.Account_UPDATE_FRAGMENT){
            Toast.makeText(mActivity, "checkUpdateFragment", Toast.LENGTH_SHORT).show();
            image=BitmapManager.bitmapToByte(Constants.accountBitmap);

                if(!account_name.isEmpty()){
                    editText_account_name.setText(account_name);
                }

                if(Constants.accountBitmap!=null){
                    linearLayout.setVisibility(View.VISIBLE);
                    saveBitmap.setImageBitmap(Constants.accountBitmap);
                }


        }



    }



    private void populateAccountIconList() {

        accountIcons.add(R.drawable.man);
        accountIcons.add(R.drawable.man_1);
        accountIcons.add(R.drawable.man_2);
        accountIcons.add(R.drawable.woman);
        accountIcons.add(R.drawable.woman_2);
        accountIcons.add(R.drawable.woman_3);

    }

    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return result;
    }

    private void initListeners() {


        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAccountDialog(mActivity,mContext, accountIcons,saveBitmap,linearLayout);
            }
        });

        addGalleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIntent=false;
                if(checkWritePermission()){
                    galleryImage();
                }
            }
        });

        addCameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIntent=true;
                checkCameraPermission();
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(account_type==Constants.NEW_Account_Add_FRAGMENT){

                    account_name= Objects.requireNonNull(editText_account_name.getText()).toString();

                    if(!account_name.isEmpty()){
                        Accounts accounts=new Accounts(account_name,image);
                        mAccountViewModel.insert(accounts);
                        finish();
                    }
                    else{
                        Toast.makeText(mActivity, "Account Name can't be empty", Toast.LENGTH_SHORT).show();
                    }

                }
                else if(account_type==Constants.Account_UPDATE_FRAGMENT){
//                    Toast.makeText(mActivity, "here", Toast.LENGTH_SHORT).show();
                    account_name= Objects.requireNonNull(editText_account_name.getText()).toString();

                    if(!account_name.isEmpty()){
                        Accounts accounts=new Accounts(account_name,image);
                        accounts.setAccountId(account_id);
                        mAccountViewModel.update(accounts);
                        finish();
                    }
                    else{
                        Toast.makeText(mActivity, "Account Name can't be empty", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }

    private boolean checkWritePermission() {
        if ((ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.PERMISSION_REQ);
        } else {
            return true;
        }
        return false;
    }


    public void showAccountDialog(final Activity activity, Context context, List<Integer> iconList, ImageView img, LinearLayout linearLayout){



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
                Drawable d = img.getDrawable();
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                image= BitmapManager.bitmapToByte(bitmap);
                alert.dismiss();

            }


        });

        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.80); //<-- int width=400;
//        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.20);
//        alert.getWindow().setLayout(width, height);

    }

    private void galleryImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        //set intent type to image
        intent.setType("image/*");
//        galleryActivityResultLauncher.launch(intent);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void checkCameraPermission() {
        if ((ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    Constants.PERMISSION_REQ);
        } else {
            if(checkWritePermission()){
                startCameraActivity();
            }



//           return true;
        }
//        return false;
    }

    private void startCameraActivity() {
//        this.imageCaptureManager = new ImageCaptureManager(this);
//        try {
//            startActivityForResult(this.imageCaptureManager.dispatchTakePictureIntent(), 2);
//        } catch (IOException | ActivityNotFoundException e) {
//            e.printStackTrace();
//        }
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSION_REQ) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.CAMERA)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {

                        checkWritePermission();
                        if(checkWritePermission()){
                            startCameraActivity();
                        }

                    } else {

                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted));
                    }
                }
                else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {

                        if(checkIntent){
                            startCameraActivity();
                        }
                        else {
                            galleryImage();
                        }


                    } else {
                        AppUtils.showToast(mContext, getString(R.string.permission_not_granted));
                    }
                }
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==101){
//            if(resultCode==RESULT_OK){

//            if (this.imageCaptureManager == null) {
//                this.imageCaptureManager = new ImageCaptureManager(this);
//            }

            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(this.imageCaptureManager.getCurrentPhotoPath()));
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(this.imageCaptureManager.getCurrentPhotoPath())));

//            Bitmap newBitmap=getResizedBitmap(bitmap,400);

            linearLayout.setVisibility(View.VISIBLE);
            saveBitmap.setImageBitmap(bitmap);
            image= BitmapManager.bitmapToByte(bitmap);

        }
        else if(requestCode==3){
            if(data!=null){
                Uri selectedImage=null;
                try{
                    selectedImage = data.getData();
                }
                catch (Exception e){
                    Toast.makeText(mActivity, "File Corrupted", Toast.LENGTH_SHORT).show();
                }

                InputStream baseStream = null;
                InputStream imageStream = null;
                try {
                    //getting the image
                    baseStream = mActivity.getContentResolver().openInputStream(selectedImage);
                    imageStream = mActivity.getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    Toast.makeText(mContext, getResources().getString(R.string.error_file), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }

                // ChecksumException
                try {
                    //decoding bitmap for get width
                    Bitmap base = BitmapFactory.decodeStream(baseStream);
                    //set options for resize image
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = base.getWidth() / 1000; //get compress coef  (set image px size (compressing big image))

                    //decoding bitmap
                    Bitmap bMap = BitmapFactory.decodeStream(imageStream, null, options); //get image with options

//                    Constants.imgBitmap = ((BitmapDrawable)Constants.imgDrawable).getBitmap();
//                    Constants.imgStr=Constants.convertBitmapToString(Constants.imgBitmap);

                    linearLayout.setVisibility(View.VISIBLE);
                    saveBitmap.setImageBitmap(bMap);
                    image= BitmapManager.bitmapToByte(bMap);


//                    saveBitmap=convertBitmapToString(bMap);
//                    imageViewLinear.setVisibility(View.VISIBLE);
//                    setImg.setImageBitmap(bMap);
//                            Log.d("checkBitmapFromGallery",bMap+"");
                    //Convert Bitmap to ByteArray
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        bMap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                        byte[] byteArray = stream.toByteArray();



                } catch (Exception e) {
//                        Toast.makeText(mContext, getResources().getString(R.string.error_nothing_find), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }

    }
}