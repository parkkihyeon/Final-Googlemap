package pre.pkh.mobile_programming_googlemap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView logView;
    private GoogleMap map;
    private double lat, lng;
    private double Nowlat, Nowlng ;
    private Button ZoomIn ;
    private Button ZoomOut ;
    private Button Add ;
    private Button list ;
    private SQLiteDatabase db;
    private String dbName = "idList.db"; // name of Database;
    public String tableName = "idListTable"; // name of Table;
    private int dbMode = Context.MODE_PRIVATE;
    private int CamerSize = 15 ;
    private ArrayList<MarkerList> mylist = new ArrayList() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = openOrCreateDatabase(dbName, dbMode, null);
        createTable() ; // 테이블 생성.
        selectAll();

        logView = (TextView) findViewById(R.id.log);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        Toast.makeText(MainActivity.this.getApplicationContext(), "위치를 잡을 때 까지 기다려주세요", Toast.LENGTH_LONG).show() ;

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() { // 이동할 때 마다 위치 바뀌는것.
            public void onLocationChanged(Location location) {
                map.clear();  // 기존 마커를 지운다.
                lat = location.getLatitude();
                lng = location.getLongitude();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), CamerSize));
                map.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("내위치")).showInfoWindow(); ;
                for(int x=0 ; x< mylist.size() ; x++) {
                    mylist.get(x).getMarker(map).showInfoWindow(); ;
                }
            }
            public void onStatusChanged(String provider, int status, Bundle extras) { logView.setText("onStatusChanged");  }
            public void onProviderEnabled(String provider) {
                logView.setText("onProviderEnabled");
            }
            public void onProviderDisabled(String provider) { logView.setText("onProviderDisabled");   }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        ZoomIn = (Button)findViewById(R.id.Zoomin) ;
        ZoomOut = (Button)findViewById(R.id.Zoomout) ;
        Add = (Button)findViewById(R.id.add) ;
        list = (Button)findViewById(R.id.list) ;
        // 맵을 확대하는 기능이다.
        ZoomIn.setOnClickListener(new Button.OnClickListener() { //줌인
            public void onClick(View v) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), ++CamerSize));
            }
        });
        // 맵을 축소하는 기능이다.
        ZoomOut.setOnClickListener(new Button.OnClickListener() { //줌아웃
            public void onClick(View v) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), --CamerSize));
            }
        });
        // 추가할 수 있는 액티비티로 이동한다.
        Add.setOnClickListener(new Button.OnClickListener() { //추가 액티비티로 이동.
            public void onClick(View v) {
                Nowlat = lat ;
                Nowlng = lng ;
                Intent intent = new Intent(MainActivity.this, AddList.class);
                startActivityForResult(intent, 1);
            }
        });
        list.setOnClickListener(new Button.OnClickListener() { //추가 액티비티로 이동.
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, ListView.class);
                    intent.putExtra("ANSWER", mylist);
                    startActivity(intent);
                }
                catch(Exception e) {}
            }
        });

        // 맵에 있는 마커를 클릭했을때에 대한 리스너 들이다.
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            MarkerList temp = null ;
            public boolean onMarkerClick(Marker marker) {
                for (int x=0 ; x< mylist.size() ; x++) {
                    if(marker.getPosition().latitude == mylist.get(x).getLat() && marker.getPosition().longitude == mylist.get(x).getLng()) {
                        temp = mylist.get(x) ; // 마커에대한 객체를 찾는다.
                    }
                }
                if(temp != null) {// 현재위치일 경우, 현재정보를 보고 삭제기능이 존재한다.
                    new AlertDialog.Builder(MainActivity.this).setTitle(temp.getTitle()).setMessage(temp.getComplete()).setNeutralButton("삭제", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {
                            new AlertDialog.Builder(MainActivity.this).setTitle("정말로 삭제하시겠습니까?").setNeutralButton("삭제", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dlg, int TEMP) {
                                            Toast.makeText(MainActivity.this.getApplicationContext(), "삭제중입니다.", Toast.LENGTH_LONG).show();
                                            removeData(temp.getId());
                                            mylist.clear(); // 삭제후에는 해야됨.
                                            selectAll();
                                        }
                                    }
                            )
                                    .setNegativeButton("닫기", new DialogInterface.OnClickListener() {// 취소 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    }).show();
                        }
                    })
                            .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                                // 취소 버튼 클릭시 설정
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).show() ;
                }
                return false;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // 인텐트로 받는다.
        super.onActivityResult(requestCode, resultCode, data);
        MarkerList temp = new MarkerList(Nowlat, Nowlng);
        try {
            temp.SetTitle(data.getStringExtra("TITLE"));
            temp.SetWho(data.getStringExtra("WHO")); // 메인 액티비티로 넘긴다.
            temp.SetWhere(data.getStringExtra("WHERE"));
            temp.SetWhen(data.getStringExtra("WHEN"));
            temp.SetDetail(data.getStringExtra("DETAIL")); // 일단 인텐트는 된다.
            String cate = String.valueOf(data.getIntExtra("CATEGORY", 0)) ;
            temp.SetCategory(cate);
            Toast.makeText(MainActivity.this.getApplicationContext(), "잠시만 기다려 주세요.", Toast.LENGTH_LONG).show() ;
            insertData(temp);
            selectAll();
        }
        catch (Exception e) {}
    }
    // 테이블 생성
    public void createTable() {
        try {
            String sql = "CREATE TABLE " + tableName + "(id integer primary key autoincrement, my_Lat TEXT, my_Lng TEXT, xmy_Title TEXT, my_Who TEXT, my_Where TEXT, my_When TEXT, my_Detail TEXT, my_Category TEXT)";
            Log.d("Lab sqlite", "생성되었습니다. :  ");
            db.execSQL(sql);
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite", "error 입니다. :  " + e);
        }
    }
    // Data 추가
    public void insertData(MarkerList Temp) {
        String sql = "INSERT INTO " + tableName + " VALUES (NULL, '" + Temp.getLat() +  "', '" + Temp.getLng() + "', '" + Temp.getTitle() + "', '"
                + Temp.getWho() + "', '" + Temp.getWhere() +  "', '" + Temp.getWhen() + "', '"  + Temp.getDetail() + "', '" + Temp.getCategory() + "');";
        db.execSQL(sql);
    }
    // Data 삭제
    public void removeData(int index) {
        try {
            String sql = "delete from " + tableName + " where id = " + index + ";";
            db.execSQL(sql);
        }
        catch (Exception e) { }
    }
    // 보여주기.
    public void selectAll() {
        String sql = "select * from " + tableName + ";"; // 오도바이 디크 , 디비 셀렉트 정렬 쳐서
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            MarkerList temp = new MarkerList() ;
            int id =  results.getInt(0) ;
            temp.SetId(results.getInt(0));
            temp.SetPosition(Double.parseDouble(results.getString(1)), Double.parseDouble(results.getString(2)));
            temp.SetTitle(results.getString(3));
            temp.SetWho(results.getString(4));
            temp.SetWhere(results.getString(5));
            temp.SetWhen(results.getString(6));
            temp.SetDetail(results.getString(7)); ;
            temp.SetCategory(results.getString(8)); ;

            Log.d("lab_sqlite", "index= " + id + " title=" + results.getString(3) + " " + results.getString(4) + " " + results.getString(5) + " " +
                    results.getString(6) + " " + results.getString(7) + " " + results.getString(8)) ;
            mylist.add(temp);
            results.moveToNext();
        }
        results.close();
    }

}