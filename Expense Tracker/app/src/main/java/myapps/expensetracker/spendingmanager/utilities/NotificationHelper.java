package myapps.expensetracker.spendingmanager.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;


import myapps.expensetracker.spendingmanager.R;
import myapps.expensetracker.spendingmanager.activities.MainActivity;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class NotificationHelper {
    private static final String NOTIFICATION_CHANNEL_ID = "1001";
    private static int notifid = 100;
    private final Context mContext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int val;
    NotificationHelper(Context context) {
        mContext = context;
    }

    void createNotification() {



        Intent notificationIntent = new Intent(mContext, MainActivity.class);//on tap this activity will open

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);//getting the pendingIntent

        Notification.Builder builder = new Notification.Builder(mContext);//building the notification

        Notification notification = builder.setContentTitle("Demo App Notification")
                .setContentText("New Notification From Demo App..")
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//below creating notification channel, because of androids latest update, O is Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "NotificationDemo",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notification);
    }

//        Intent intent = new Intent(mContext, MainActivity.class);
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
//                0 /* Request code */, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//        CharSequence message = "Scan QR codes to find Details of Products";
//
////
////        mBuilder.setSmallIcon(R.drawable.qr_code_logo);
//        mBuilder.setContentTitle("QR code Scanner & Generator")
//                .setContentText(message)
//                .setSmallIcon(R.drawable.qr_code_logo)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                .setAutoCancel(false)
//                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                .setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        val = getData(mContext);
//        if(val<0 || val==0){
//            val=100;
//            saveData(mContext,val);
//        }
//
//
//        if(val != 0){
//            val=val+1;
//            saveData(mContext,val);
////            Log.d("notifyid", String.valueOf(val));
//
//            if(val == 101)
//            {
//                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
//                        R.drawable.notifiy1);
//
//                message = "Scan QR codes to find Details of Products\n";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.qr_code_logo);
//                mBuilder.setContentTitle("QR code Scanner & Generator")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
//            }
//            if(val == 102)
//            {
//                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
//                        R.drawable.notify2);
//
//                message = "Simplify details by Making Your Information \"QR Codes\"";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.qr_code_logo);
//                mBuilder.setContentTitle("QR code Scanner & Generator")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 103)
//            {
//                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
//                        R.drawable.notifiy1);
//
//                message = "QR Code is Unique way to pack your Information";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.qr_code_logo);
//                mBuilder.setContentTitle("QR code Scanner & Generator")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
//            }
//            if(val == 104)
//            {
//                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
//                        R.drawable.notify2);
//
//                message = "Create Customized QR Codes";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.qr_code_logo);
//                mBuilder.setContentTitle("QR code Scanner & Generator")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 105)
//            {
//                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
//                        R.drawable.notifiy1);
//
//                message = "You can find any previous QR Code record from History";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.qr_code_logo);
//                mBuilder.setContentTitle("QR code Scanner & Generator")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 106)
//            {
//                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
//                        R.drawable.notify2);
//
//                message = "You feedback is valuable to us. Please give Your feedback";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.qr_code_logo);
//                mBuilder.setContentTitle("QR code Scanner & Generator")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
//                val=100;
////                saveData(mContext,100);
//            }
//            if(val == 107)
//            {
//                message = "Love is a thing that is full of cares and fears.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 108)
//            {
//                message = "Love is not love until love’s vulnerable.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 109)
//            {
//                message = "Love is like the wind, you can’t see it but you can feel it.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 110)
//            {
//                message = "Love is the magician that pulls man out of his own hat.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 111)
//            {
//                message = "It is better to be hated for what you are than to be loved for what you are not.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 112)
//            {
//                message = "This has been my life, I found it worth living.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 113)
//            {
//                message = "We must be our own before we can be another’s.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
//                val=100;
//                saveData(mContext, val);
//            }





//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.BLUE);
//            notificationChannel.enableVibration(true);
//            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            assert mNotificationManager != null;
//            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
//            mNotificationManager.createNotificationChannel(notificationChannel);
//        }
//
////        Log.d("notifid", String.valueOf(notifid));
//
//        assert mNotificationManager != null;
//        mNotificationManager.notify(notifid /* Request Code */, mBuilder.build());


    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 50, 50, false);
        return new BitmapDrawable(mContext.getResources(), bitmapResized);
    }


    public void saveData(Context context, int notifid){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        SharedPreferences.Editor spEditor = sharedPref.edit();
        spEditor.putInt("DATA", notifid);
        spEditor.commit();
    }

    public int getData(Context context){
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        int sPSavedData= sharedPref.getInt("DATA", 0);
        return sPSavedData;
    }



}
