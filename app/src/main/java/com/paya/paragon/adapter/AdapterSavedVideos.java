package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.postProperty.loadVideo.SavedVideoInfo;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

@SuppressWarnings({"ConstantConditions", "SameParameterValue", "HardCodedStringLiteral"})
@SuppressLint("SetTextI18n")
public class AdapterSavedVideos extends RecyclerView.Adapter<AdapterSavedVideos.SavedVideoViewHolder> {

    private ArrayList<SavedVideoInfo> mSavedVideos;
    private Context mContext;

    public AdapterSavedVideos(Context context, ArrayList<SavedVideoInfo> videoList) {
        this.mContext = context;
        mSavedVideos = videoList;
    }

    public void removeVideoFromList(int i) {
        mSavedVideos.remove(i);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mSavedVideos = new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavedVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_video_model,
                parent, false);
        return new SavedVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedVideoViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final SavedVideoInfo info = mSavedVideos.get(position);
        if (info.getPropertyVideoTitle() != null && !info.getPropertyVideoTitle().equals("")) {
            holder.title.setText(info.getPropertyVideoTitle());
            holder.title.setVisibility(View.VISIBLE);
        } else {
            holder.title.setVisibility(View.GONE);
        }

        Utils.loadUrlImage(holder.videoImage, "https://img.youtube.com/vi/" + info.getPropertyVideoYoutubeID() + "/default.jpg", R.drawable.icon_youtube, false);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firePlayVideoInfo(info.getPropertyVideoYoutubeID());
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDeleteVideo(mContext.getString(R.string.are_you_sure_want_to_delete),
                        mSavedVideos.get(position).getPropertyVideoID(), position);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertEditVideo(mSavedVideos.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mSavedVideos.size();
    }

    static class SavedVideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoImage;
        ImageView edit;
        ImageView play;
        ImageView delete;
        TextView title;

        SavedVideoViewHolder(View itemView) {
            super(itemView);
            this.videoImage = itemView.findViewById(R.id.image_view_item_saved_video_model);
            this.edit = itemView.findViewById(R.id.edit_item_saved_video_model);
            this.play = itemView.findViewById(R.id.button_play_saved_video);
            this.delete = itemView.findViewById(R.id.delete_item_saved_video_model);
            this.title = itemView.findViewById(R.id.video_title_item_saved_video_model);
        }
    }


    //TODO Alert
    private void alertEditVideo(final SavedVideoInfo savedVideoInfo) {
        final Dialog alertDialog = new Dialog(mContext);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout =
                factory.inflate(R.layout.alert_edit_saved_video_info_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_video_info_popup);
        final EditText title = alert_layout.findViewById(R.id.edit_title_video_info_popup);
        final EditText overview = alert_layout.findViewById(R.id.edit_overview_video_info_popup);
        TextView update = alert_layout.findViewById(R.id.button_update_video_info_popup);

        if (savedVideoInfo.getPropertyVideoTitle() != null && !savedVideoInfo.getPropertyVideoTitle().equals("")) {
            title.setText(savedVideoInfo.getPropertyVideoTitle());
        }
        if (savedVideoInfo.getPropertyVideoDescription() != null && !savedVideoInfo.getPropertyVideoDescription().equals("")) {
            overview.setText(savedVideoInfo.getPropertyVideoDescription());
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTitle = title.getText().toString();
                String strOverview = overview.getText().toString();
                if (!Utils.NoInternetConnection(mContext)) {
                    fireEditVideoInfo(alertDialog, strTitle, strOverview, savedVideoInfo.getPropertyVideoID());
                } else {
                    alertDialog.dismiss();
                    Utils.showAlertNoInternet(mContext);
                }
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void alertDeleteVideo(String alertText, final String videoID, final int position) {
        final Dialog alertDialog = new Dialog(mContext);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout =
                factory.inflate(R.layout.alert_delete_video_popup, null);
        LinearLayout cancel = alert_layout.findViewById(R.id.CancelPopUp);
        TextView title = alert_layout.findViewById(R.id.alertTitle);
        TextView ok = alert_layout.findViewById(R.id.alert_ok_text);
        title.setText(alertText);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireDeleteVideoInfo(alertDialog, videoID, position);
                alertDialog.dismiss();

            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    //TODO Interface Edit
    public interface EditVideoInfoInterface {
        void onVideoInfoEdited(Dialog alertDialog, String title, String overview, String videoId);
    }

    private EditVideoInfoInterface mEditVideoInfoInterface;

    public void setEditVideoInfoInterface(EditVideoInfoInterface deleteImageInterface) {
        this.mEditVideoInfoInterface = deleteImageInterface;
    }

    private void fireEditVideoInfo(Dialog alertDialog, String title, String overview, String videoId) {
        if (mEditVideoInfoInterface != null) {
            mEditVideoInfoInterface.onVideoInfoEdited(alertDialog, title, overview, videoId);
        }
    }


    //TODO Interface Delete
    public interface DeleteVideoInfoInterface {
        void onVideoInfoDelete(Dialog alertDialog, String videoId, int position);
    }

    private DeleteVideoInfoInterface mDeleteVideoInfoInterface;

    public void setDeleteVideoInfoInterface(DeleteVideoInfoInterface deleteImageInterface) {
        this.mDeleteVideoInfoInterface = deleteImageInterface;
    }

    private void fireDeleteVideoInfo(Dialog alertDialog, String videoId, int position) {
        if (mDeleteVideoInfoInterface != null) {
            mDeleteVideoInfoInterface.onVideoInfoDelete(alertDialog, videoId, position);
        }
    }

    //TODO Interface Play video
    public interface PlayVideoInfoInterface {
        void onVideoPlayed(String videoId);
    }

    private PlayVideoInfoInterface mPlayVideoInfoInterface;

    public void setPlayVideoInfoInterface(PlayVideoInfoInterface playVideoInfoInterface) {
        this.mPlayVideoInfoInterface = playVideoInfoInterface;
    }

    private void firePlayVideoInfo(String videoId) {
        if (mPlayVideoInfoInterface != null) {
            mPlayVideoInfoInterface.onVideoPlayed(videoId);
        }
    }
}
