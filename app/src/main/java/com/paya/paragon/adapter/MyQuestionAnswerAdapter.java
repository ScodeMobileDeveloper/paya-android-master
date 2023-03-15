package com.paya.paragon.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.myQuestions.MyQuestionAnswerData;

import java.util.ArrayList;

@SuppressWarnings("HardCodedStringLiteral")
public class MyQuestionAnswerAdapter extends RecyclerView.Adapter<MyQuestionAnswerAdapter.AnswerViewHolder>{

    private ArrayList<MyQuestionAnswerData> mMyQuestionAnswerList ;
    Context mContext;

    public MyQuestionAnswerAdapter(Context context,ArrayList<MyQuestionAnswerData> myQuestionAnswerList) {
        this.mMyQuestionAnswerList = myQuestionAnswerList;
        this.mContext = context;
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_questions_answer_model, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        MyQuestionAnswerData answerData = mMyQuestionAnswerList.get(position);
        holder.postedDate.setText(answerData.getAnswerDate());
        holder.postedAnswer.setText(answerData.getAnswerDetail());
        holder.answerLikes.setText(answerData.getLikeCount() +  " "+mContext.getString(R.string.likes));
        holder.answerDislikes.setText(answerData.getUnlikeCount() +  " "+mContext.getString(R.string.unlikes));
    }

    @Override
    public int getItemCount() {
        return mMyQuestionAnswerList.size();
    }

    static class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView postedDate;
        TextView postedAnswer;
        TextView answerLikes;
        TextView answerDislikes;
        AnswerViewHolder(View itemView) {
            super(itemView);
            this.postedDate = itemView.findViewById(R.id.posted_date_item_my_question_answer);
            this.postedAnswer = itemView.findViewById(R.id.posted_answer_item_my_question_answer);
            this.answerLikes = itemView.findViewById(R.id.answer_likes_item_my_question_answer);
            this.answerDislikes = itemView.findViewById(R.id.answer_dislikes_item_my_question_answer);
        }
    }
}
