package com.longerdriver.provider.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.longerdriver.provider.Activity.MainActivity;
import com.longerdriver.provider.R;
import com.longerdriver.provider.Utilities.Utilities;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "MyFirebaseMsgService";
    Utilities utils = new Utilities();
    Context context;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData() != null) {
            utils.print(TAG, "From: " + remoteMessage.getFrom());
            utils.print(TAG, "Notification Message Body: " + remoteMessage.getData());
            //Calling method to generate notification
            sendNotification(remoteMessage.getData().get("message"));
        } else {
            utils.print(TAG, "FCM Notification failed");
        }
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {

        if (!Utilities.isAppIsInBackground(context.getApplicationContext())) {
            // app is in foreground, broadcast the push message
            utils.print(TAG, "foreground");
            Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("push", true);
            PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context.getApplicationContext())
                    .setContentTitle(context.getString(R.string.app_name)).setContentText(messageBody).setAutoCancel(true).setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentIntent(pendingIntent);

            notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder), 1);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());

        } else {
            utils.print(TAG, "background");
            // app is in background, show the notification in notification tray
            if (messageBody.equalsIgnoreCase("New Incoming Ride")) {
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context.getApplicationContext())
                        .setContentTitle(context.getString(R.string.app_name)).setContentText(messageBody).setAutoCancel(true)
                        .setSound(Uri.parse("android.resource://" + this.context.getPackageName() + "/" + R.raw.alert_tone))
                        .setContentIntent(pendingIntent);

                notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder), 1);

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0, notificationBuilder.build());
            } else {
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("push", true);
                PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context.getApplicationContext())
                        .setContentTitle(context.getString(R.string.app_name)).setContentText(messageBody).setAutoCancel(true).setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setContentIntent(pendingIntent);

                notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder), 1);

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0, notificationBuilder.build());
            }
        }
    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(ContextCompat.getColor(context.getApplicationContext(), R.color.colorPrimary));
            return R.drawable.notification_white;
        } else {
            return R.mipmap.longer;
        }
    }


//FOR FUTURE REFERENCE

//    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
//
//    private NotificationUtils notificationUtils;
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        utils.print(TAG, "From: " + remoteMessage.getFrom());
//
//        if (remoteMessage == null)
//            return;
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            utils.print(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
//            handleNotification(remoteMessage.getNotification().getBody());
//        }
//
//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            utils.print(TAG, "Data Payload: " + remoteMessage.getData().toString());
//
//            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
//                handleDataMessage(json);
//            } catch (Exception e) {
//                utils.print(TAG, "Exception: " + e.getMessage());
//            }
//        }
//    }
//
//    private void handleNotification(String message) {
//        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//            // app is in foreground, broadcast the push message
//            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//            pushNotification.putExtra("message", message);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//            // play notification sound
//            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//            notificationUtils.playNotificationSound();
//        }else{
//            // If the app is in background, firebase itself handles the notification
//        }
//    }
//
//    private void handleDataMessage(JSONObject json) {
//        utils.print(TAG, "push json: " + json.toString());
//
//        try {
//            JSONObject data = json.getJSONObject("data");
//
//            String title = data.getString("title");
//            String message = data.getString("message");
//            boolean isBackground = data.getBoolean("is_background");
//            String imageUrl = data.getString("image");
//            String timestamp = data.getString("timestamp");
//            JSONObject payload = data.getJSONObject("payload");
//
//            utils.print(TAG, "title: " + title);
//            utils.print(TAG, "message: " + message);
//            utils.print(TAG, "isBackground: " + isBackground);
//            utils.print(TAG, "payload: " + payload.toString());
//            utils.print(TAG, "imageUrl: " + imageUrl);
//            utils.print(TAG, "timestamp: " + timestamp);
//
//
//            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//                // app is in foreground, broadcast the push message
//                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//                pushNotification.putExtra("message", message);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//                // play notification sound
//                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSound();
//            } else {
//                // app is in background, show the notification in notification tray
//                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
//                resultIntent.putExtra("message", message);
//
//                // check for image attachment
//                if (TextUtils.isEmpty(imageUrl)) {
//                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
//                } else {
//                    // image is present, show notification with image
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//                }
//            }
//        } catch (JSONException e) {
//            utils.print(TAG, "Json Exception: " + e.getMessage());
//        } catch (Exception e) {
//            utils.print(TAG, "Exception: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Showing notification with text only
//     */
//    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
//        notificationUtils = new NotificationUtils(context);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
//    }
//
//    /**
//     * Showing notification with text and image
//     */
//    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
//        notificationUtils = new NotificationUtils(context);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
//    }
}
