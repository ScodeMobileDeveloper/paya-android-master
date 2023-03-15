package com.paya.paragon.utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.paya.paragon.R;
import com.paya.paragon.activity.localExpert.ActivityLocalExpertDetails;

public class LocalExportRatingBox extends Dialog {
    Context context;
    RatingBar rate_bar;
    TextView btn_rate;
    EditText edtReviewTitle,edtComment;
    String title,comment,rating;
    ImageView ivClose;
    public LocalExportRatingBox(Context context) {
        super(context);
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.local_export_rating_dialog_list_style);
        rate_bar=findViewById(R.id.rate_bar);
        rate_bar.setNumStars(5);
        btn_rate=findViewById(R.id.btn_rate);
        edtReviewTitle=findViewById(R.id.edt_review_title);
        edtComment=findViewById(R.id.edt_comment);
        ivClose=findViewById(R.id.iv_close);
        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title=edtReviewTitle.getText().toString().trim();
                comment=edtComment.getText().toString().trim();
                rating=String.valueOf(rate_bar.getRating());
                if(rating.contains(".")){
                    rating = rating.substring(0,rating.indexOf("."));
                }
                if(!title.equals("")){
                    if(!comment.equals("")){
                        if(rating.equals("0")){
                            Toast.makeText(context, context.getString(R.string.please_select_rating),Toast.LENGTH_SHORT).show();
                        }else{
                            ActivityLocalExpertDetails.rate="yes";
                            ActivityLocalExpertDetails.rateTitle=title;
                            ActivityLocalExpertDetails.rateComment=comment;
                            ActivityLocalExpertDetails.rateCount=rating;
                            LocalExportRatingBox.this.dismiss();
                        }
                    }else{
                        Toast.makeText(context, context.getString(R.string.please_write_comment),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, context.getString(R.string.please_enter_review_title),Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalExportRatingBox.this.dismiss();
            }
        });

    }
}
