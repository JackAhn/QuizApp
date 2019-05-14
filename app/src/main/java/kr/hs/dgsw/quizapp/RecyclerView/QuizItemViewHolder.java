package kr.hs.dgsw.quizapp.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kr.hs.dgsw.quizapp.R;

public class QuizItemViewHolder extends RecyclerView.ViewHolder {

    TextView textQuizTitle;
    TextView textQuizSave;
    TextView textQuizType;
    int count = 0;

    public QuizItemViewHolder(@NonNull View itemView) {
        super(itemView);
        textQuizTitle = itemView.findViewById(R.id.QuizTitleText);
        textQuizSave = itemView.findViewById(R.id.QuizSave);
        textQuizType = itemView.findViewById(R.id.QuizType);
    }
}
