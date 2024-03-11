package com.example.notification_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//primary components of notification
//1.notification manager-responsible to manage send,cancel or create notification channel
//2.notification compact-create notification
//3. notification channel-letting user decide if users want to see notifications
//pending intent-description of action to be performed
public class MainActivity extends AppCompatActivity {
Button show,cancel;
NotificationManager manager;
Notification notification;
NotificationChannel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show=findViewById(R.id.showBtn);
        cancel=findViewById(R.id.cancelBtn);
         manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
         //notification work from android 8 api 26
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //we can create notification-otherwise no need
            channel=new NotificationChannel("channel1","hello channel",NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                   //pending intent used to perform action somewhere at particular time..so we using
                    //pending intent not intent directly
                     PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),123,i,PendingIntent.FLAG_IMMUTABLE);
//image need to be bitmap format
                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.android);

                    notification=new NotificationCompat.Builder(getApplicationContext(),"channel1")
                            .setSmallIcon(R.drawable.baseline_brightness_4_24)
                            .setContentTitle("This  is my first notification")
                            .setContentText("This is the description of the notification above")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(pi)
                            .setAutoCancel(true)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText("this is expanded text while receiving notification we can expand and see the content of it."))
                          //  .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                            .addAction(R.drawable.baseline_brightness_4_24,"Action one",pi)
                            .build();
                    manager.notify(23,notification);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    manager.cancel(23);
                }
            });
        }
    }

}