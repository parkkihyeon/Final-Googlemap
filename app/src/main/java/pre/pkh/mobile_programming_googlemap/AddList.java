package pre.pkh.mobile_programming_googlemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddList extends AppCompatActivity {

    private Button Back ; //Checkbox_Title
    private Button OK ;
    private EditText title , who, where , what , when ,detail ;
    private LinearLayout LinearCheck ;
    private TextView EssentailText, T1, T2, T3 ,T4, Checkbox_Title ;
    private CheckBox cb1, cb2, cb3, cb4 ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addlist_popup); // 여기까지 activity 띄우기 관련

        LinearCheck = (LinearLayout) findViewById(R.id.Checkboxes);
        title = (EditText) findViewById(R.id.Title);
        who = (EditText) findViewById(R.id.who);
        where = (EditText) findViewById(R.id.where);
        detail = (EditText) findViewById(R.id.detail);
        EssentailText = (TextView) findViewById(R.id.textView7);
        T1 = (TextView) findViewById(R.id.textView);
        T2 = (TextView) findViewById(R.id.textView2);
        T3 = (TextView) findViewById(R.id.textView3);
        T4 = (TextView) findViewById(R.id.textView4);

        Checkbox_Title = (TextView) findViewById(R.id.Checkbox_Title) ;
        cb1 = (CheckBox)findViewById(R.id.checkBox) ;
        cb2 = (CheckBox)findViewById(R.id.checkBox1) ;
        cb3 = (CheckBox)findViewById(R.id.checkBox2) ;
        cb4 = (CheckBox)findViewById(R.id.checkBox3) ;

        LinearCheck.setVisibility(View.INVISIBLE);
        Checkbox_Title.setVisibility(View.INVISIBLE);

        Back = (Button) findViewById(R.id.back);
        Back.setOnClickListener(new Button.OnClickListener() { //퀴즈결과
            public void onClick(View v) {
                finish();
            }
        });

        OK = (Button) findViewById(R.id.add);
        OK.setOnClickListener(new Button.OnClickListener() { //
            public void onClick(View v) {
                try {
                    if (title.getText().toString().isEmpty()) { // getText().toString() 은 null값이 나오지 않아서 isEmpty()라는 함수를 사용하여햐 한다.
                        Toast.makeText(AddList.this.getApplicationContext(), "제목은 필수입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(AddList.this.getApplicationContext(), "1개를 체크 하십시오.", Toast.LENGTH_SHORT).show();
                        ExchangeVisible() ; //  onClick이 된다면 Exchange!
                        OK.setOnClickListener(new Button.OnClickListener() { //
                            public void onClick(View v) {
                                if ( (cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked() && !cb4.isChecked()) ||
                                        (!cb1.isChecked() && cb2.isChecked() && !cb3.isChecked() && !cb4.isChecked()) ||
                                        (!cb1.isChecked() && !cb2.isChecked() && cb3.isChecked() && !cb4.isChecked()) ||
                                        (!cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked() && cb4.isChecked())) {
                                    Calendar oCalendar = Calendar.getInstance( );
                                    String  WHEN = oCalendar.get(Calendar.YEAR) +  "/" + (oCalendar.get(Calendar.MONTH)+1) + "/" + oCalendar.get(Calendar.DAY_OF_MONTH) + "/" +
                                            oCalendar.get(Calendar.HOUR_OF_DAY) + ":" + oCalendar.get(Calendar.MINUTE)  ;

                                    int Checking ;
                                    if (cb1.isChecked()) Checking = 1 ;
                                    else if (cb2.isChecked()) Checking = 2;
                                    else if (cb3.isChecked()) Checking = 3;
                                    else Checking = 4;
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("TITLE", title.getText().toString()); // 메인 액티비티로 넘긴다.
                                    intent.putExtra("WHO", who.getText().toString()); // 메인 액티비티로 넘긴다.
                                    intent.putExtra("WHERE", where.getText().toString()); // 메인 액티비티로 넘긴다.
                                    intent.putExtra("WHEN",WHEN); // 메인 액티비티로 넘긴다.
                                    intent.putExtra("DETAIL", detail.getText().toString()); // 메인 액티비티로 넘긴다.
                                    intent.putExtra("CATEGORY", Checking) ;
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                                else if (cb1.isChecked() == false && cb2.isChecked() == false && cb3.isChecked() == false) {
                                    Toast.makeText(AddList.this.getApplicationContext(), "1개를 반드시 선택하여야 합니다.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(AddList.this.getApplicationContext(), "다중 선택이 불가능 합니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                } catch (Exception e) {
                }
            }
        });
    }

    public void ExchangeVisible() {
        title.setVisibility(View.INVISIBLE);
        who.setVisibility(View.INVISIBLE);
        where.setVisibility(View.INVISIBLE);
        detail.setVisibility(View.INVISIBLE);
        EssentailText.setVisibility(View.INVISIBLE);
        T1.setVisibility(View.INVISIBLE);
        T2.setVisibility(View.INVISIBLE);
        T3.setVisibility(View.INVISIBLE);
        T4.setVisibility(View.INVISIBLE);
        LinearCheck.setVisibility(View.VISIBLE);
        Checkbox_Title.setVisibility(View.VISIBLE);
    }
}
