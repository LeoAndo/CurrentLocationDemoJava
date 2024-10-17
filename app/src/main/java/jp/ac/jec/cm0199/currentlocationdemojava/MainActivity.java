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

    // TODO STEP-4 位置情報取得サービスを呼び出す - START
//    @NonNull
//    private AppLocationService appLocationService; // lateinit
    // END

    // TODO STEP-5 オプトインの処理: 位置情報をの権限リクエスト - START
//    private final ActivityResultLauncher<String> fetchCurrentLocation =
//            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
//                Log.d(TAG, "granted: " + granted);
//                if (granted) {
//                    // 位置情報の権限付与した場合、現在地取得を行う
//                    appLocationService.fetchCurrentLocation();
//                } else {
//                    Snackbar.make(getWindow().getDecorView(), "位置情報取得の許可を行ってください", Snackbar.LENGTH_LONG).show();
//                }
//            });
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

        // TODO STEP-4 位置情報取得サービスを呼び出す - START
//        appLocationService = new AppLocationService(this);
//        appLocationService.getLocationResult().observe(this, locationData -> {
//            Log.d(TAG, "取得した位置情報:::::: " + locationData);
//        });
//        appLocationService.getFetchError().observe(this, s -> {
//            Log.e(TAG, s);
//        });
        // END

        // TODO STEP-5 オプトインの処理: 位置情報をの権限リクエスト - START
//        findViewById(R.id.button_current_location).setOnClickListener(v -> {
//            fetchCurrentLocation.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
//        });
        // END
    }
}