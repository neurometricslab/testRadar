package la.neurometrics.radarmap;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import org.jetbrains.annotations.NotNull;

import io.radar.sdk.Radar;
import io.radar.sdk.RadarReceiver;
import io.radar.sdk.model.RadarEvent;
import io.radar.sdk.model.RadarUser;

public class MyRadarReceiver extends RadarReceiver {
    @Override
    public void onError(@NotNull Context context, @NotNull Radar.RadarStatus radarStatus) {
        Intent intent = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent  = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Log.d("TAG1",radarStatus.toString());
        Toast.makeText(context, radarStatus.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEventsReceived(@NotNull Context context, @NotNull RadarEvent[] radarEvents, @NotNull RadarUser radarUser) {
        Intent intent = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent  = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Log.d("TAG1",radarUser.toString());
        Toast.makeText(context, "Evento", Toast.LENGTH_SHORT).show();
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("radar",
                    "Radar",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Notificaciones");
            mNotificationManager.createNotificationChannel(channel);
        }
        Uri souUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(context,"radar")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Bienvenido!!")
                .setContentText( "Usted entro a : " + radarEvents[0].getGeofence().getDescription())

                .setAutoCancel(false)
                .setSound(souUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notBuilder.build());
    }

    @Override
    public void onLocationUpdated(@NotNull Context context, @NotNull Location location, @NotNull RadarUser user) {
        Intent intent = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent  = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Toast.makeText(context, "Se actualizo la ubicación", Toast.LENGTH_SHORT).show();

    /*    NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                    "Radar",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Notificaciones");
            mNotificationManager.createNotificationChannel(channel);
        }
        Uri souUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(context,"YOUR_CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Se actualizo su ubicación")
                .setContentText("Ubicación actualizada")
                .setAutoCancel(false)
                .setSound(souUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notBuilder.build());
        super.onLocationUpdated(context, location, user);*/
    }
}
