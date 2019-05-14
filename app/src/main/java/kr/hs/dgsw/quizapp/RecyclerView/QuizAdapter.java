package kr.hs.dgsw.quizapp.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import kr.hs.dgsw.quizapp.R;
import kr.hs.dgsw.quizapp.model.QuestionBean;

public class QuizAdapter extends RecyclerView.Adapter<QuizItemViewHolder> {

    private ArrayList<QuestionBean> questionData;
    private ItemClickListener listener;

    public QuizAdapter(ArrayList<QuestionBean> data, ItemClickListener listener){
        questionData = data;
        this.listener = listener;
    }

    public void setData(ArrayList<QuestionBean> data){
        questionData = data;
    }

    @NonNull
    @Override
    public QuizItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quiz_item, viewGroup, false);
        return new QuizItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizItemViewHolder viewHolder, int i) {
        QuestionBean questionBean = questionData.get(i);
        viewHolder.textQuizTitle.setText(questionBean.getQuestion());
        if(questionBean.getType() == QuestionBean.TYPE_TEXT){
            viewHolder.textQuizType.setText("TEXT");
        }
        else if(questionBean.getType() == QuestionBean.TYPE_IMAGE){
            viewHolder.textQuizType.setText("IMAGE");
        }
        viewHolder.textQuizSave.setText(questionBean.getQuizDate());
        final int index = questionBean.getId()-1;
        viewHolder.itemView.setOnClickListener(v -> {
            listener.onItemClick(v, index);
        });
    }

    @Override
    public int getItemCount() {
        if(questionData == null)
            return 0;
        else
            return questionData.size();
    }
}
