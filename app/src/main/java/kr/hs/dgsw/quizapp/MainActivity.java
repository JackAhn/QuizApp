package kr.hs.dgsw.quizapp;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import kr.hs.dgsw.quizapp.model.QuestionDBHelper;

public class MainActivity extends AppCompatActivity {

    private RadioButton radioButton1;
    private RadioButton radiobutton2;
    private RadioGroup radioGroup;
    public static QuestionDBHelper questionDBHelper;
    private final int SET_QUIZ = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioButton1 = findViewById(R.id.radioButton);
        radiobutton2 = findViewById(R.id.radioButton2);
        radioGroup = findViewById(R.id.radioGroup);
        questionDBHelper = new QuestionDBHelper(this, "quiz", null, 1);
    }

    public void goQuiz(View v){
        int id = radioGroup.getCheckedRadioButtonId();
        Intent intent = new Intent(this, QuizActivity.class);
        if(id == radioButton1.getId()){
            intent.putExtra("mode", "easy");
            startActivity(intent);
        }
        else if(id == radiobutton2.getId()){
            intent.putExtra("mode", "hard");
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this, "난이도를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void onSetting(View v){
        startActivity(new Intent(this, QuestionListActivity.class));
    }
}
