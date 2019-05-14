package kr.hs.dgsw.quizapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.hs.dgsw.quizapp.model.QuestionBean;

public class QuizActivity extends AppCompatActivity {

    private Intent intent;
    private String mode;
    private ArrayList<QuestionBean> quizData;
    private LinearLayout quizImageLayout, quizTextLayout;
    private TextView scoreText, questionText, quizText1, quizText2, quizText3, quizText4;
    private ImageView quizImage1, quizImage2, quizImage3, quizImage4;
    private RadioGroup radioGroup;
    private EditText hardText;
    private int index = 0;
    private String quiztype;
    private int quizcount = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        quizImageLayout = findViewById(R.id.QuizImageLayout);
        quizTextLayout = findViewById(R.id.QuizTextLayout);
        scoreText = findViewById(R.id.scoreText);
        questionText = findViewById(R.id.questionText);
        radioGroup = findViewById(R.id.quizradioGroup);
        hardText = findViewById(R.id.hardAnswer);
        quizText1 = findViewById(R.id.QuizText1);
        quizText2 = findViewById(R.id.QuizText2);
        quizText3 = findViewById(R.id.QuizText3);
        quizText4 = findViewById(R.id.QuizText4);
        quizImage1 = findViewById(R.id.image1);
        quizImage2 = findViewById(R.id.image2);
        quizImage3 = findViewById(R.id.image3);
        quizImage4 = findViewById(R.id.image4);
        intent = getIntent();
        if(intent.getStringExtra("mode").equals("easy")){
            mode = "easy";
        }
        else if(intent.getStringExtra("mode").equals("hard")){
            mode = "hard";
        }
        setQuizData();
        setLayout();
    }

    private void setQuizData(){
        quizData = MainActivity.questionDBHelper.select();
    }

    private void setLayout(){
        if(quizData.size() == 0){
            Toast.makeText(QuizActivity.this, "문제가 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(quizData.size() == index){
            Toast.makeText(QuizActivity.this, "최종 점수 "+score+"점!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            scoreText.setText("Score : "+score);
            radioGroup.clearCheck();
            if(quizData.get(index).getType() == QuestionBean.TYPE_TEXT){
                if(mode.equals("easy")){
                    radioGroup.setVisibility(View.VISIBLE);
                    quizTextLayout.setVisibility(View.VISIBLE);
                    hardText.setVisibility(View.INVISIBLE);
                    quizImageLayout.setVisibility(View.INVISIBLE);
                    setData("easy");
                }
                else if(mode.equals("hard")){
                    radioGroup.setVisibility(View.INVISIBLE);
                    quizTextLayout.setVisibility(View.INVISIBLE);
                    hardText.setVisibility(View.VISIBLE);
                    quizImageLayout.setVisibility(View.INVISIBLE);
                    setData("hard");
                }
            }
            else if(quizData.get(index).getType() == QuestionBean.TYPE_IMAGE){
                radioGroup.setVisibility(View.VISIBLE);
                quizTextLayout.setVisibility(View.INVISIBLE);
                hardText.setVisibility(View.INVISIBLE);
                quizImageLayout.setVisibility(View.VISIBLE);
                setData("image");
            }
        }
    }

    private void setData(String type){
        quiztype = type;
        questionText.setText(quizData.get(index).getQuestion());
        if(type.equals("easy")){
            quizText1.setText(quizData.get(index).getChoices()[0]);
            quizText2.setText(quizData.get(index).getChoices()[1]);
            quizText3.setText(quizData.get(index).getChoices()[2]);
            quizText4.setText(quizData.get(index).getChoices()[3]);
        }
        else if(type.equals("hard")){
            hardText.setText("");
        }
        else if(type.equals("image")){
            quizImage1.setImageURI(Uri.parse(quizData.get(index).getChoices()[0]));
            quizImage2.setImageURI(Uri.parse(quizData.get(index).getChoices()[1]));
            quizImage3.setImageURI(Uri.parse(quizData.get(index).getChoices()[2]));
            quizImage4.setImageURI(Uri.parse(quizData.get(index).getChoices()[3]));
        }
    }

    public void checkAnswer(View v){
        if(quiztype.equals("easy") || quiztype.equals("image")){
            int select = 0;
            select = radioGroup.getCheckedRadioButtonId();
            int answer = quizData.get(index).getAnswer();
            if(answer == 1 && select == R.id.quizButton1){
                Toast.makeText(QuizActivity.this, "정답! "+quizData.get(index).getScore()+"점 획득!", Toast.LENGTH_SHORT).show();
                score = score + quizData.get(index).getScore();
            }
            else if(answer == 2 && select == R.id.quizButton2){
                Toast.makeText(QuizActivity.this, "정답! "+quizData.get(index).getScore()+"점 획득!", Toast.LENGTH_SHORT).show();
                score = score + quizData.get(index).getScore();
            }
            else if(answer == 3 && select == R.id.quizButton3){
                Toast.makeText(QuizActivity.this, "정답! "+quizData.get(index).getScore()+"점 획득!", Toast.LENGTH_SHORT).show();
                score = score + quizData.get(index).getScore();
            }
            else if(answer == 4 && select == R.id.quizButton4){
                Toast.makeText(QuizActivity.this, "정답! "+quizData.get(index).getScore()+"점 획득!", Toast.LENGTH_SHORT).show();
                score = score + quizData.get(index).getScore();
            }
            else if(answer == 0){
                Toast.makeText(QuizActivity.this, "정답을 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                Toast.makeText(QuizActivity.this, "오답이에유", Toast.LENGTH_SHORT).show();
            }
        }
        else if(quiztype.equals("hard")){
            int answerind = quizData.get(index).getAnswer();
            String answer = quizData.get(index).getChoices()[answerind-1];
            if(hardText.getText().toString().equals(answer)){
                Toast.makeText(QuizActivity.this, "정답! "+quizData.get(index).getScore()+"점 획득!", Toast.LENGTH_SHORT).show();
                score = score + quizData.get(index).getScore();
            }
            else{
                Toast.makeText(QuizActivity.this, "오답이에유", Toast.LENGTH_SHORT).show();
            }
        }
        index++;
        setLayout();
    }
}
