package com.paya.paragon.adapter;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.myQuestions.MyQuestionAnswerData;
import com.paya.paragon.api.myQuestions.MyQuestionsData;
import com.paya.paragon.base.commonClass.CircularImageView;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterMyQuestions extends RecyclerView.Adapter<AdapterMyQuestions.QuestionViewHolder> {

    private ArrayList<MyQuestionsData> mMyQuestionsList;
    Context mContext;
    private   String imageURL;

    public AdapterMyQuestions(Context context, ArrayList<MyQuestionsData> myQuestionsList,String imageURL) {
        this.mMyQuestionsList = myQuestionsList;
        this.mContext = context;
        this.imageURL = imageURL;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_questions_model, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionViewHolder holder, int position) {
        MyQuestionsData questionsData = mMyQuestionsList.get(position);
        holder.recyclerQuestionAnswers.setVisibility(View.GONE);
        Utils.collapse(holder.recyclerQuestionAnswers, 10);
        holder.isExpanded = false;
        holder.answerList = new ArrayList<>();
        holder.answerList = questionsData.getAnswers();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerQuestionAnswers.setLayoutManager(linearLayoutManager);
        MyQuestionAnswerAdapter answerAdapter = new MyQuestionAnswerAdapter(mContext,holder.answerList);
        holder.recyclerQuestionAnswers.setAdapter(answerAdapter);
        Utils.loadUrlImage(holder.userIcon, imageURL+questionsData.getUserProfilePicture(), R.drawable.no_image_user, false);
        holder.answerCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isExpanded) {
                    Utils.collapse(holder.recyclerQuestionAnswers, 200);
                    holder.isExpanded = false;
                } else {
                    Utils.expand(holder.recyclerQuestionAnswers, 200);
                    holder.recyclerQuestionAnswers.setVisibility(View.VISIBLE);
                    holder.isExpanded = true;
                }
            }
        });

        if (questionsData.getQuestionPostedBy() != null && !questionsData.getQuestionPostedBy().equals("")){
            holder.postedByUser.setText(mContext.getString(R.string.by_text)+" " + questionsData.getQuestionPostedBy());
        } else {
            holder.postedByUser.setText("");
        }
        if (questionsData.getQuestionDate() != null && !questionsData.getQuestionDate().equals("")){
            holder.postedDate.setText(Utils.convertDateFormat(questionsData.getQuestionDate()));
        } else {
            holder.postedDate.setText("");
        }
        if (questionsData.getQuestionCategoryName() != null && !questionsData.getQuestionCategoryName().equals("")){
            holder.questionCategory.setText(questionsData.getQuestionCategoryName());
        } else {
            holder.questionCategory.setText("");
        }
        if (questionsData.getQuestionTitle() != null && !questionsData.getQuestionTitle().equals("")){
            holder.postedQuestion.setText(questionsData.getQuestionTitle());
        } else {
            holder.postedQuestion.setText("");
        }
        if (questionsData.getLikeCount() != null && !questionsData.getLikeCount().equals("")){
            holder.likeCount.setText(questionsData.getLikeCount() + " "+mContext.getString(R.string.likes));
        } else {
            holder.likeCount.setText("");
        }
        if (questionsData.getUnlikeCount() != null && !questionsData.getUnlikeCount().equals("")){
            holder.dislikeCount.setText(questionsData.getUnlikeCount() + " "+mContext.getString(R.string.unlikes));
        } else {
            holder.dislikeCount.setText("");
        }
        if (questionsData.getAnswerCount() != null && !questionsData.getAnswerCount().equals("")){
            holder.answerCount.setText(questionsData.getAnswerCount() +" "+ mContext.getString(R.string.answers));
        } else {
            holder.answerCount.setText("");
        }
        if (questionsData.getUserProfilePicture() != null && !questionsData.getUserProfilePicture().equals("")){

        } else {
            holder.answerCount.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return mMyQuestionsList.size();
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        CircularImageView userIcon;
        TextView postedByUser;
        TextView postedDate;
        TextView questionCategory;
        TextView postedQuestion;
        TextView likeCount;
        TextView dislikeCount;
        TextView answerCount;
        RecyclerView recyclerQuestionAnswers;
        ArrayList<MyQuestionAnswerData> answerList;
        boolean isExpanded;

        QuestionViewHolder(View itemView) {
            super(itemView);
            this.userIcon = itemView.findViewById(R.id.user_icon_item_my_question);
            this.postedByUser = itemView.findViewById(R.id.posted_user_item_my_question);
            this.postedDate = itemView.findViewById(R.id.posted_date_item_my_question);
            this.questionCategory = itemView.findViewById(R.id.posted_category_item_my_question);
            this.postedQuestion = itemView.findViewById(R.id.posted_question_item_my_question);
            this.likeCount = itemView.findViewById(R.id.posted_question_likes_item_my_question);
            this.dislikeCount = itemView.findViewById(R.id.posted_question_dislikes_item_my_question);
            this.answerCount = itemView.findViewById(R.id.posted_question_answers_item_my_question);
            this.recyclerQuestionAnswers = itemView.findViewById(R.id.recycler_my_questions_answer);
            this.answerList = new ArrayList<>();
            isExpanded = false;
        }
    }
}
