package pre.pkh.mobile_programming_googlemap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ListView extends AppCompatActivity {

    private ArrayList<MarkerList> Result = new ArrayList();
    private Button back ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.markerstate);

        back = (Button)findViewById(R.id.back) ;
        back.setOnClickListener(new Button.OnClickListener() { //추가 액티비티로 이동.
            public void onClick(View v) {
                finish();
            }
        });
        Result = (ArrayList<MarkerList>) getIntent().getSerializableExtra("ANSWER"); // intent를 통하여 arraylist를 받음.



    }

}