package pre.pkh.mobile_programming_googlemap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PassedList extends Activity {

    private ListView                m_ListView;
    private ArrayAdapter<String>    m_Adapter;
    private ArrayList<MarkerList> marker = new ArrayList() ;
    int meal, date , picnic, work ;// 식사 , 데이트 , 놀러감, 용무
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        marker = (ArrayList<MarkerList>) getIntent().getSerializableExtra("MYLIST");
        meal = 0 ; date = 0 ; picnic = 0 ; work = 0 ;
        // Android에서 제공하는 string 문자열 하나를 출력 가능한 layout으로 어댑터 생성
        m_Adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listview);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        // ListView 아이템 터치 시 이벤트 추가
        m_ListView.setOnItemClickListener(onClickListItem);
        for(int x=0 ; x<marker.size() ; x++) {
            if ( Integer.parseInt(marker.get(x).getCategory()) == 1) meal++ ;
            else if ( Integer.parseInt(marker.get(x).getCategory()) == 2) date++ ;
            else if ( Integer.parseInt(marker.get(x).getCategory()) == 3) picnic++ ;
            else if ( Integer.parseInt(marker.get(x).getCategory()) == 4) work++ ;
        }

        // ListView에 아이템 추가
        for(int x=0 ; x<marker.size() ; x++) {
            m_Adapter.add(marker.get(x).getTitle());
            m_Adapter.add(marker.get(x).getWhen() + " " + marker.get(x).getWhere());
        }
        m_Adapter.add("식사 : " + meal + "  데이트 : " + date + "  피크닉 : " + picnic + "  용무 : " + work);
    }

    // 아이템 터치 이벤트
    private OnItemClickListener onClickListItem = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
            Toast.makeText(getApplicationContext(), m_Adapter.getItem(arg2), Toast.LENGTH_SHORT).show();
            finish() ;
        }
    };

}