package kr.hs.dgsw.quizapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import kr.hs.dgsw.quizapp.RecyclerView.ItemClickListener;
import kr.hs.dgsw.quizapp.RecyclerView.QuizAdapter;
import kr.hs.dgsw.quizapp.model.QuestionBean;

public class QuestionListActivity extends AppCompatActivity implements ItemClickListener {

    private String pw = "1234";
    private RecyclerView quizList;
    private QuizAdapter adapter;
    private ArrayList<QuestionBean> data;
    private final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        data = MainActivity.questionDBHelper.select();
        adapter = new QuizAdapter(data, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        quizList = findViewById(R.id.recyclerView);
        quizList.setLayoutManager(layoutManager);
        quizList.setAdapter(adapter);
    }

    public void onConfirm(View v){
        EditText password = findViewById(R.id.pwText);
        if(pw.equals(password.getText().toString())){
            findViewById(R.id.layoutpw).setVisibility(View.GONE);
        }
        else
            finish();
    }

    public void addQuestion(View v){
        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("type", "new");
        //이동하고 돌아오면 RecyclerView에 퀴즈 추가되어야 함
        startActivityForResult(intent, REQUEST);
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("type", "modify");
        intent.putExtra("index", position);
        startActivityForResult(intent, REQUEST);
    }

    private void InitializeList(){
        data.clear();
        data = MainActivity.questionDBHelper.select();
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST){
            InitializeList();
        }
    }
}
