package kr.hs.dgsw.quizapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.net.URI;
import java.util.Date;

import kr.hs.dgsw.quizapp.model.QuestionBean;
import kr.hs.dgsw.quizapp.model.QuestionDBHelper;

public class QuestionActivity extends AppCompatActivity {


    private EditText question;
    private EditText score;
    private EditText text1;
    private EditText text2;
    private EditText text3;
    private EditText text4;
    private LinearLayout TextLayout;
    private LinearLayout ImageLayout;
    private RadioGroup radioGroup;
    private ToggleButton toggleButton;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private int selectImg;
    private int answer;
    private int type;
    private final int REQ_CODE_SELECT_IMAGE = 100;
    private final int REQ_MANAGE_DOCUMENTS = 50;
    private Intent intent;

    private String choice1="";
    private String choice2="";
    private String choice3="";
    private String choice4="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        question = findViewById(R.id.editTextQuestion);
        score = findViewById(R.id.score);
        text1 = findViewById(R.id.Questext1);
        text2 = findViewById(R.id.Questext2);
        text3 = findViewById(R.id.Questext3);
        text4 = findViewById(R.id.Questext4);
        img1 = findViewById(R.id.imageChoice1);
        img2 = findViewById(R.id.imageChoice2);
        img3 = findViewById(R.id.imageChoice3);
        img4 = findViewById(R.id.imageChoice4);
        this.TextLayout = findViewById(R.id.TextLayout);
        this.ImageLayout = findViewById(R.id.ImageLayout);
        radioGroup = findViewById(R.id.radioGroup2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton1)
                    answer = 1;
                else if(checkedId == R.id.radioButton2)
                    answer = 2;
                else if(checkedId == R.id.radioButton3)
                    answer = 3;
                else if(checkedId == R.id.radioButton4)
                    answer = 4;
            }
        });
        toggleButton = findViewById(R.id.toggleButton);
        answer = 0;
        type = QuestionBean.TYPE_TEXT;
        TextLayout.setVisibility(LinearLayout.VISIBLE);
        ImageLayout.setVisibility(LinearLayout.INVISIBLE);
        intent = getIntent();
        toggleButton.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    type = QuestionBean.TYPE_TEXT;
                    TextLayout.setVisibility(LinearLayout.VISIBLE);
                    ImageLayout.setVisibility(LinearLayout.INVISIBLE);

                }
                else if(isChecked == false){
                    type = QuestionBean.TYPE_IMAGE;
                    TextLayout.setVisibility(LinearLayout.INVISIBLE);
                    ImageLayout.setVisibility(LinearLayout.VISIBLE);
                }
                choice1="";
                choice2="";
                choice3="";
                choice4="";
                img1.setImageResource(R.drawable.noimage);
                img2.setImageResource(R.drawable.noimage);
                img3.setImageResource(R.drawable.noimage);
                img4.setImageResource(R.drawable.noimage);
            }
        });
        if(intent.getStringExtra("type").equals("modify")){
            setQuestionData();
        }
    }


    private void setQuestionData(){
        int id = intent.getIntExtra("index", 0);
        QuestionBean data =  MainActivity.questionDBHelper.select(id+1);
        question.setText(data.getQuestion());
        score.setText(data.getScore()+"");
        if(data.getType() == QuestionBean.TYPE_TEXT){
            TextLayout.setVisibility(LinearLayout.VISIBLE);
            ImageLayout.setVisibility(LinearLayout.INVISIBLE);
            text1.setText(data.getChoices()[0]);
            text2.setText(data.getChoices()[1]);
            text3.setText(data.getChoices()[2]);
            text4.setText(data.getChoices()[3]);
        }
        else if(data.getType() == QuestionBean.TYPE_IMAGE){
            TextLayout.setVisibility(LinearLayout.INVISIBLE);
            ImageLayout.setVisibility(LinearLayout.VISIBLE);
            try{
                img1.setImageURI(Uri.parse(data.getChoices()[0]));
                img2.setImageURI(Uri.parse(data.getChoices()[1]));
                img3.setImageURI(Uri.parse(data.getChoices()[2]));
                img4.setImageURI(Uri.parse(data.getChoices()[3]));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        switch(data.getAnswer()){
            case 1:
                radioGroup.check(R.id.radioButton1);
                break;
            case 2:
                radioGroup.check(R.id.radioButton2);
                break;
            case 3:
                radioGroup.check(R.id.radioButton3);
                break;
            case 4:
                radioGroup.check(R.id.radioButton4);
                break;
        }
        choice1 = data.getChoices()[0];
        choice2 = data.getChoices()[1];
        choice3 = data.getChoices()[2];
        choice4 = data.getChoices()[3];
    }

    public void deleteQuestion(View v){
        if(intent.getStringExtra("type").equals("new"))
            finish();
        int id = intent.getIntExtra("index", 0);
        MainActivity.questionDBHelper.delete(id+1);
        Toast.makeText(QuestionActivity.this, "문제를 삭제했습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void setQuestion(View v){
        String title = question.getText().toString();
        String sco = score.getText().toString();
        if(type == QuestionBean.TYPE_TEXT){
            choice1 = text1.getText().toString();
            choice2 = text2.getText().toString();
            choice3 = text3.getText().toString();
            choice4 = text4.getText().toString();
        }

        if(title.equals("") || sco.equals("") || choice1.equals("") || choice2.equals("") || choice3.equals("") || choice4.equals("")){
            Toast.makeText(QuestionActivity.this, "공백이 존재합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(answer == 0){
            Toast.makeText(QuestionActivity.this, "정답을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        String [] choices = {choice1, choice2, choice3, choice4};
        QuestionBean questionBean = new QuestionBean();
        questionBean.setType(type);
        questionBean.setChoices(choices);
        questionBean.setQuestion(title);
        questionBean.setScore(Integer.parseInt(sco));
        questionBean.setAnswer(answer);
        questionBean.setQuizDate(new Date());
        if(intent.getStringExtra("type").equals("new")){
            MainActivity.questionDBHelper.insert(questionBean);
            Toast.makeText(QuestionActivity.this, "문제가 저장되었습니다.", Toast.LENGTH_SHORT).show();
        }
        else if(intent.getStringExtra("type").equals("modify")){
            questionBean.setId(intent.getIntExtra("index", 0) + 1);
            MainActivity.questionDBHelper.update(questionBean);
            Toast.makeText(QuestionActivity.this, "문제가 수정되었습니다.", Toast.LENGTH_SHORT).show();
        }
        //이전 화면으로 이동
        finish();
    }

    public void setPicture(View v){
        switch(v.getId()){
            case R.id.Choiceimg1:
                selectImg = R.id.imageChoice1;
                break;
            case R.id.Choiceimg2:
                selectImg = R.id.imageChoice2;
                break;
            case R.id.Choiceimg3:
                selectImg = R.id.imageChoice3;
                break;
            case R.id.Choiceimg4:
                selectImg = R.id.imageChoice4;
                break;
        }
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_SELECT_IMAGE){
            if(resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
                try{
                    Uri uri = data.getData();
                    String filePath = getImageNameToUri(uri);
                    ImageView imageView = findViewById(selectImg);
                    switch(selectImg){
                        case R.id.imageChoice1:
                            choice1 = filePath;
                            break;
                        case R.id.imageChoice2:
                            choice2 = filePath;
                            break;
                        case R.id.imageChoice3:
                            choice3 = filePath;
                            break;
                        case R.id.imageChoice4:
                            choice4 = filePath;
                            break;
                    }
                    imageView.setImageURI(uri);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        //String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgPath;
    }
}
