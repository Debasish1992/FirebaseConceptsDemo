package com.example.debasishkumardas.firebaseconceptsdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.debasishkumardas.firebaseconceptsdemo.model.MessageModel;
import com.example.debasishkumardas.firebaseconceptsdemo.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity
        implements View.OnClickListener{

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    RecyclerView recyclerView;
    MessageAdapter adapter;
    SharedPreferences srh;
    String loggedinUserId = null;
    ArrayList<MessageModel> messageArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        srh = PreferenceManager.getDefaultSharedPreferences(HomeScreen.this);
        loggedinUserId = srh.getString("UserId", null);

        messageArray = new ArrayList<>();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MessageAdapter(this, messageArray);
        recyclerView.setAdapter(adapter);

        fetchValue();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                closeFab();
                startActivity(new Intent(HomeScreen.this, MessagePostScreen.class));
                //displayDialog();
                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab2:
                Log.d("Raj", "Fab 2");
                break;
        }
    }

    public void displayDialog(){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Do You Want To Post Message")
                .content("The Message will be displayed to everyone, " +
                        "if you set it as public and vice versa.")
                .positiveText("OK");
        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    public void animateFAB(){
        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");
        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");
        }
    }

    public void closeFab(){
        fab.startAnimation(rotate_forward);
        fab1.startAnimation(fab_open);
        fab2.startAnimation(fab_open);
        fab1.setClickable(true);
        fab2.setClickable(true);
    }

    /**
     * Fetching all the values from a channel
     */
    public void fetchValue() {
        Firebase.setAndroidContext(HomeScreen.this);
        final Firebase postRef = new Firebase(Constants.BASE_URL_FIREBASE).child("Messages");

        postRef.child(loggedinUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                MessageModel post = null;
                //Removing all the values from the chat array
                messageArray.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    post = child.getValue(MessageModel.class);
                    //Adding the model data into the array
                    messageArray.add(post);
                }
                refreshMessageList();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void refreshMessageList(){
        if(messageArray.size() > 0){
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(HomeScreen.this, "No Message Found", Toast.LENGTH_SHORT).show();
        }

    }
}

