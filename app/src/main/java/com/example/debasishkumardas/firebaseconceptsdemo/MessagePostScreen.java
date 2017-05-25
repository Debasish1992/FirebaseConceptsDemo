package com.example.debasishkumardas.firebaseconceptsdemo;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.debasishkumardas.firebaseconceptsdemo.model.MessageModel;
import com.example.debasishkumardas.firebaseconceptsdemo.model.UserModel;
import com.example.debasishkumardas.firebaseconceptsdemo.utils.Constants;
import com.firebase.client.Firebase;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MessagePostScreen extends AppCompatActivity {

    @InjectView(R.id.input_name)
    EditText etPostTitle;
    @InjectView(R.id.input_email)
    EditText etPostContent;
    @InjectView(R.id.input_password)
    EditText etPostPassword;
    @InjectView(R.id.input_hobby)
    EditText etPostOccasion;
    @InjectView(R.id.input_about_me)
    EditText etPostType;
    @InjectView(R.id.btn_signup)
    Button btnPost;
    ProgressDialog prg;
    SharedPreferences srh;
    String loggedinUserId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_post_screen);
        ButterKnife.inject(this);


        srh = PreferenceManager.getDefaultSharedPreferences(MessagePostScreen.this);
        loggedinUserId = srh.getString("UserId", null);
        prg = new ProgressDialog(this);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prg.setMessage("Updating Please Wait...");
                prg.show();
                String postTitle = etPostTitle.getText().toString().trim();
                String postContent = etPostContent.getText().toString().trim();
                String postPassword = etPostPassword.getText().toString().trim();
                String postOccasion = etPostOccasion.getText().toString().trim();
                String postType = etPostType.getText().toString().trim();
                String message_id = Constants.getChannelId();

                MessageModel messageModel = new MessageModel();
                messageModel.setMessageId(message_id);
                messageModel.setMessageTitle(postTitle);
                messageModel.setMessageContent(postContent);
                messageModel.setMessagePassword(postPassword);
                messageModel.setMessageOccasion(postOccasion);
                messageModel.setMessageType(postType);
                storeUserMessage(loggedinUserId, message_id, messageModel);
            }
        });
    }

    public void storeUserMessage(String userId, String messageId,
                                 MessageModel messageModel){
        Firebase.setAndroidContext(MessagePostScreen.this);
        Firebase postRef = new Firebase(Constants.BASE_URL_FIREBASE);
        postRef = postRef.child("Messages").child(userId).child(messageId);
        postRef.setValue(messageModel);
        prg.dismiss();
        redirectUserToHomeScreen();
    }

    public void redirectUserToHomeScreen(){
        MessagePostScreen.this.finish();
    }
}
