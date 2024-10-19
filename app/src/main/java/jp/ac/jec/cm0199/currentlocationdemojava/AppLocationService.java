package jp.ac.jec.cm0199.currentlocationdemojava;

// TODO STEP-3 アプリで利用する位置情報取得サービスの追加- START

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

class AppLocationService {
    // logcatで使うTAG
    private static final String TAG = "AppLocationService";

    @NonNull
    private final Context context;

    // 位置情報サービス
    @NonNull
    private final FusedLocationProviderClient locationClient;

    // 取得成功時のLiveData
    @NonNull
    private final MutableLiveData<Location> _locationResult;

    @NonNull
    public LiveData<Location> getLocationResult() {
        return _locationResult;
    }

    // 取得エラー時のLiveData
    @NonNull
    private final MutableLiveData<String> _fetchError;

    @NonNull
    public LiveData<String> getFetchError() {
        return _fetchError;
    }

    public AppLocationService(@NonNull final Context context) {
        this.context = context;
        this.locationClient = LocationServices.getFusedLocationProviderClient(context);
        this._locationResult = new MutableLiveData<>();
        this._fetchError = new MutableLiveData<>();
    }

    /**
     * インスタンスを取得する (シングルトンデザインパターンを採用)
     * roomライブラリの内部実装を見ると、Double-checked_lockingのデザインパターンを採用していたので真似た
     *
     * @param context コンテキスト
     * @return AppLocationServiceのインスタンス
     * @see <a href="https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java">Double-checked_locking</a>
     * @see <a href="https://en.wikipedia.org/wiki/Singleton_pattern">Singleton_pattern</a>
     */
    /*
    private static volatile AppLocationService instance;
    @NonNull
    public static AppLocationService getInstance(@NonNull final Context context) {
        if (instance != null) {
            return instance;
        } else {
            synchronized (AppLocationService.class) {
                if (instance == null) {
                    instance = new AppLocationService(context);
                }
                return instance;
            }
        }
    }
     */

    /**
     * 現在の位置情報を取得する.
     */
    public void fetchCurrentLocation() {

        if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            _fetchError.setValue("アプリの位置情報パーミッションを許可する必要があります");
            return;
        }
        locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.d(TAG, "location: " + location);

                if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    _fetchError.setValue("アプリの位置情報パーミッションを許可する必要があります");
                    return;
                }

                final LocationRequest request = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 500).build();
                locationClient.requestLocationUpdates(request, context.getMainExecutor(), new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult result) {
                        super.onLocationResult(result);

                        final Location lastLocation = result.getLastLocation();
                        if (lastLocation == null) {
                            _fetchError.setValue("現在地取得に失敗しました");
                            return;
                        }

                        Log.d(TAG, "onLocationResult latitude: " + lastLocation.getLatitude() + " longitude: " + lastLocation.getLongitude());
                        _locationResult.setValue(lastLocation);
                        // 現在地だけ欲しいので、1回取得したら位置情報取得をやめる
                        locationClient.removeLocationUpdates(this);
                    }

                    @Override
                    public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                        super.onLocationAvailability(locationAvailability);
                        // 位置情報OFFの時にここに入る
                        Log.d(TAG, "onLocationAvailability");
                        _fetchError.setValue("端末の位置情報設定をONにする必要があります");
                    }
                });
            }
        }).addOnFailureListener(e -> {
            Log.d(TAG, "error: " + e);
            _fetchError.setValue("現在地取得に失敗しました\n" + e.getLocalizedMessage());
        });
    }
}
// END
