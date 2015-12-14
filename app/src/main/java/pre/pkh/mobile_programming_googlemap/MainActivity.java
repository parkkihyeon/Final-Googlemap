package pre.pkh.mobile_programming_googlemap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    TextView logView;
    private GoogleMap map;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logView = (TextView) findViewById(R.id.log);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() { // 이동할 때 마다 위치 바뀌는것.
            public void onLocationChanged(Location location) {
                map.clear();  // 기존 마커를 지운다.
                lat = location.getLatitude();
                lng = location.getLongitude();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
                map.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("내위치")).showInfoWindow(); ;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                logView.setText("onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                logView.setText("onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                logView.setText("onProviderDisabled");
            }
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
}