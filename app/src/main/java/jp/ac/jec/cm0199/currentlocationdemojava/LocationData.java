package jp.ac.jec.cm0199.currentlocationdemojava;

import androidx.annotation.NonNull;

import java.util.Objects;

// TODO STEP-2 アプリで利用する位置情報取得サービスの追加- START
/*
class LocationData {
    private final double latitude;
    private final double longitude;

    public LocationData(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // kotlinの data classに合わせてオブジェクトの同値比較ができるように - START
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationData that = (LocationData) o;
        return Double.compare(latitude, that.latitude) == 0 && Double.compare(longitude, that.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
    // END

    @NonNull
    @Override
    public String toString() {
        return "LocationData{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
 */
// END