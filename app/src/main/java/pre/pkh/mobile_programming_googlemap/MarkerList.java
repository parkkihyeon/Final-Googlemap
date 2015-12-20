package pre.pkh.mobile_programming_googlemap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

public class MarkerList implements Serializable {
    private double lat, lng;
    private int id ;
    private Marker marker ;
    private String title ;
    private String Who ;
    private String Where ;
    private String When ;
    private String Detail ;
    private String Category ; // 식사 , 데이트 , 놀러감, 용무

    public MarkerList(double lat, double lng) {
        this.lat = lat ;
        this.lng = lng ;
        id = 0 ;
        title = null ;
        Where = null ;
        When = null ;
        Who = null ;
        Detail = null ;
        Category = null ;
    }
    public MarkerList() {
        lat = 0 ;
        lng = 0 ;
        id = 0 ;
        title = null ;
        Where = null ;
        When = null ;
        Who = null ;
        Detail = null ;
        Category = null ;
    }
    public void SetPosition(double lat, double lng) { this.lat = lat ; this.lng = lng ;}
    public void SetTitle(String title) { this.title = title ;}
    public void SetWhere(String where) { this.Where = where ; }
    public void SetWhen(String when) {   this.When = when ; }
    public void SetWho(String who) { this.Who = who ; }
    public void SetDetail(String detail) {  this.Detail = detail ;  }
    public void SetCategory(String Category) {   this.Category = Category ; }
    public void SetId(int id) {  this.id = id ; }

    public int getId() { return this.id ;}
    public double getLat() {  return this.lat ; }
    public double getLng() {
        return this.lng ;
    }
    public String getTitle() {
        return this.title ;
    }
    public String getWhere() {
        return this.Where ;
    }
    public String getWhen() {
        return this.When ;
    }
    public String getWho() {  return this.Who ; }
    public String getDetail() { return this.Detail ;  }
    public String getCategory() {return this.Category ; }
    public String getComplete() {
        String cate = null ;
        if(Category.equals("1")) cate = "식사"  ;
        else if (Category.equals("2")) cate = "데이트" ;
        else if (Category.equals("3")) cate = "놀러감" ;
        else if (Category.equals("4")) cate = "용무" ;
        String complete = " 누가 : " + this.Who + "\n 어디서 : " + Where + "\n 언제 : " + When + "\n 무엇을 : " + Detail + "\n 카테고리 : " + cate  ;
        return complete ;
    }
    public Marker getMarker(GoogleMap map) {
        marker = map.addMarker(new MarkerOptions().position(new LatLng(this.lat,this.lng)).title(title)) ;
        return marker ; }
}