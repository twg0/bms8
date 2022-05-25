package com.alddeul.capstone;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.firebase.firestore.model.ServerTimestamps;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.Duration;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private RequestQueue mqueue, mqueue2,queue;
    public static String message = "";
    public static String title = "";
    MainActivity m = new MainActivity();

    @Override
    public void onNewToken(@NonNull String token) {



/*
        sendtoken(token);
*/
        super.onNewToken(token);
        //token을 서버로 전송

    }



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        /*super.onMessageReceived(remoteMessage);
        super.onMessageReceived(remoteMessage);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(*//*CHANNEL_ID*//*"0") == null) {
                NotificationChannel channel = new NotificationChannel(*//*CHANNEL_ID*//*"0", *//*CHANNEL_NAME*//*getResources().getString(R.string.channel_name), NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            builder = new NotificationCompat.Builder(getApplicationContext(),*//* CHANNEL_ID*//*"0");
        }else {
            builder = new NotificationCompat.Builder(getApplicationContext());
        }

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        builder.setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_background);

        Notification notification = builder.build();
        notificationManager.notify(1, notification);
*/

        /* String message = "";
        String title = "";*/

        // Notifition 항목이 있을때.
        if (remoteMessage.getNotification() != null) {
            message = remoteMessage.getNotification().getBody();
            title = remoteMessage.getNotification().getTitle();
        }

      /*  // Data 항목이 있을때.
        Map<String, String> data = remoteMessage.getData();
        String messageData = data.get("message");
        String titleData = data.get("title");
        String nameData = data.get("name");*/








        sendNotification(title, message);






    }


    private void sendNotification(String title, String message) {

        Intent intent;
        PendingIntent pendingIntent;

        intent = new Intent(MyFirebaseMessagingService.this, MainActivity.class);
        intent.putExtra("message", message);
        intent.putExtra("title", title);
/*startActivity(intent);*/


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //SDK26부터 푸쉬에 채널항목에 대한 세팅이 필요하다.
        if (Build.VERSION.SDK_INT >= 26) {

            String channelId = "test push";
            String channelName = "test Push Message";
            String channelDescription = "New test Information";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);
            //각종 채널에 대한 설정
            channel.enableLights(true);
            /*channel.setLightColor();*/
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300});
            notificationManager.createNotificationChannel(channel);
            //channel이 등록된 builder
            notificationBuilder = new NotificationCompat.Builder(this, channelId);
        }
        else {
            notificationBuilder = new NotificationCompat.Builder(this);
        }

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background )
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setContentText(message);

        Date time= Calendar.getInstance().getTime();

        int localTime = (int)(time.getTime()/1000);

                /*new TimeUtil().getuniqTime();*/
        notificationManager.notify(localTime /* ID of notification */, notificationBuilder.build());

        Log.d("myfirebase","sent successs");
        Log.d("myfirebase","title"+title);
        Log.d("myfirebase","message"+message);

    }



    public void sendtoken(String token)
    {
        queue = Volley.newRequestQueue(this);
        String url2 = " http://10.0.2.2:8080/api/notification/token";

        String TAG = "token ";


        Log.d(" myfirebasemessaging ", " token" + token);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString() + " my firebase");
                Log.d(TAG, "send token success myfirebase");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", " send token myfirebase");


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token",token);



                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded;");
                return headers;
            }
        };


        stringRequest.setTag(TAG);
        queue.add(stringRequest);


    }

}

