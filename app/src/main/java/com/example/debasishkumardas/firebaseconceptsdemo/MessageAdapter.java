package com.example.debasishkumardas.firebaseconceptsdemo;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.debasishkumardas.firebaseconceptsdemo.model.MessageModel;

import java.util.List;

/**
 * Created by Debasish Kumar Das on 5/25/2017.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private Context mContext;
    private List<MessageModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, occasion;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.count);
            occasion = (TextView) view.findViewById(R.id.visibility);
        }
    }


    public MessageAdapter(Context mContext, List<MessageModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_message_card_view, parent, false);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.wave_scale);
        animation = new ScaleAnimation((float) 1.0, (float) 1.0,
                (float) 0, (float) 1.0);
        animation.setDuration(500);
        itemView.startAnimation(animation);
        animation = null;

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //MessageModel album = albumList.get(position);
        String title = albumList.get(position).getMessageTitle();
        holder.title.setText(title);
        holder.description.setText(albumList.get(position).getMessageContent());
        holder.occasion.setText(albumList.get(position).getMessageOccasion());

    }

   /* *//**
     * Showing popup menu when tapping on 3 dots
     *//*
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    *//**
     * Click listener for popup menu items
     *//*
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }*/

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
