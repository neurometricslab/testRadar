package la.neurometrics.radarmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.radar.sdk.Radar;
import io.radar.sdk.RadarTrackingOptions;
import io.radar.sdk.model.RadarEvent;
import io.radar.sdk.model.RadarUser;


public class MainActivity extends AppCompatActivity {

    Button btnLocation;
    Button btnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLocation = findViewById(R.id.btnLocation);
        btnOff = findViewById(R.id.btnOff);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 2);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        Radar.initialize("prj_test_pk_e2109117d835eadc7b3fb6c7ffa83a9698d9390a");

        Radar.setUserId("newPrueba5");
        Radar.setDescription("poco");

        RadarTrackingOptions trackingOptions = new RadarTrackingOptions.Builder()
                .priority(Radar.RadarTrackingPriority.RESPONSIVENESS) // use EFFICIENCY instead to reduce location update frequency
                .sync(Radar.RadarTrackingSync.ALL)

                // use REPLAY_OFF instead to disable offline replay
                // use ALL instead to sync all location updates
                .build();

        Radar.startTracking(trackingOptions);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadarTrackingOptions trackingOptions = new RadarTrackingOptions.Builder()
                        .priority(Radar.RadarTrackingPriority.RESPONSIVENESS) // use EFFICIENCY instead to reduce location update frequency
                        .sync(Radar.RadarTrackingSync.ALL)
                        // use REPLAY_OFF instead to disable offline replay
                        // use ALL instead to sync all location updates
                        .build();

                Radar.startTracking(trackingOptions);
                Toast.makeText(MainActivity.this, String.valueOf(Radar.RadarStatus.values()), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, String.valueOf(Radar.isTracking()), Toast.LENGTH_SHORT).show();
                Log.d("TAG1", String.valueOf(Radar.getUserId()));

                Log.d("TAG1", String.valueOf(Radar.isTracking()));

            }
        });
        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Radar.stopTracking();
            }
        });
    }
}
