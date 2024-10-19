package jp.ac.jec.cm0199.currentlocationdemojava;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // TODO STEP-5 位置情報取得サービスを呼び出す - START
    @NonNull
    private AppLocationService appLocationService; // lateinit
    // END

    // TODO STEP-6 オプトインの処理: 位置情報をの権限リクエスト - START
    private final ActivityResultLauncher<String> fetchCurrentLocation =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                Log.d(TAG, "granted: " + granted);
                if (granted) {
                    // 位置情報の権限付与した場合、現在地取得を行う
                    appLocationService.fetchCurrentLocation();
                } else {
                    Snackbar.make(getWindow().getDecorView(), "位置情報取得の許可を行ってください", Snackbar.LENGTH_LONG).show();
                }
            });
    // END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // TODO STEP-5 位置情報取得サービスを呼び出す - START
        appLocationService = new AppLocationService(this);
        appLocationService.getLocationResult().observe(this, locationData -> {
            Log.d(TAG, "取得した位置情報:::::: " + locationData);
        });
        appLocationService.getFetchError().observe(this, s -> {
            Log.e(TAG, s);
        });
        // END

        // TODO STEP-6 オプトインの処理: 位置情報をの権限リクエスト - START
        findViewById(R.id.button_current_location).setOnClickListener(v -> {
            fetchCurrentLocation.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
        });
        // END

        // 同値比較の実験.
        // データサンプル Location[fused 37.421998,-122.084000 hAcc=5.0 et=+1h1m23s471ms alt=5.0 vAcc=0.5 vel=0.0 sAcc=0.5]
        /*
        Location loc1 = new Location("WiFi Access Point");
        loc1.setLatitude(37.421998); // fused
        loc1.setLongitude(-122.084000); // fused
        loc1.setAccuracy(5.0f); // hAcc
        loc1.setAltitude(5.0f); // alt
        loc1.setVerticalAccuracyMeters(0.5f); // vAcc
        loc1.setSpeed(0.0f); // vel
        loc1.setSpeedAccuracyMetersPerSecond(0.5f); // sAcc

        Location loc2 = new Location("WiFi Access Point");
        loc2.setLatitude(37.421998); // fused
        loc2.setLongitude(-122.084000); // fused
        loc2.setAccuracy(5.0f); // hAcc
        loc2.setAltitude(5.0f); // alt
        loc2.setVerticalAccuracyMeters(0.5f); // vAcc
        loc2.setSpeed(0.0f); // vel
        loc2.setSpeedAccuracyMetersPerSecond(0.5f); // sAcc

        Log.d(TAG, "Locationインスタンスの同値比較: " + loc1.equals(loc2)); // true
        */
    }
}