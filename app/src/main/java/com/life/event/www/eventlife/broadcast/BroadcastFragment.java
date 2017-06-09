package com.life.event.www.eventlife.broadcast;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.life.event.www.eventlife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu_s on 6/1/17.
 */

public class BroadcastFragment extends Fragment implements ChildEventListener {
    private DatabaseReference mDatabase;
    private List<BroadcastMessage> broadcastMessages;
    private BroadcastMessageAdapter broadcastAdapter;
    private RecyclerView broadcastListRecyclerView;
    private View fragmentLayout;
    private Button button_sent;
    private EditText messageInput;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFirebase();
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_broadcast, container, false);
        Activity ctx = this.getActivity();
        fragmentLayout = ctx.getLayoutInflater().inflate(R.layout.fragment_broadcast,
                null);


        initializeFirebase();
        setupAdapter();
        viewsAndListenerRegistra();
        return view;
    }

    private void viewsAndListenerRegistra(){
        broadcastListRecyclerView = (RecyclerView) fragmentLayout.findViewById(R.id.broadcastView);
        button_sent = (Button)fragmentLayout.findViewById(R.id.button_sent);
        messageInput = (EditText) fragmentLayout.findViewById(R.id.input_message);
        button_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void initializeFirebase() {
        if(mDatabase == null)
            mDatabase = FirebaseDatabase.getInstance().getReference();;
        mDatabase.addChildEventListener(this);
    }

    private void setupAdapter(){
        broadcastMessages = new ArrayList<>();
        broadcastAdapter = new BroadcastMessageAdapter(broadcastMessages);
    }

    private void setupList() {
        broadcastListRecyclerView.setLayoutManager(new LinearLayoutManager(broadcastListRecyclerView.getContext()));
        broadcastListRecyclerView.setAdapter(broadcastAdapter);
    }

    public void sendMessage() {
        String message = messageInput.getText().toString();
        if (!message.isEmpty()) mDatabase.push().setValue(new BroadcastMessage(message, "abc"));
        messageInput.setText("");
    }

    public void fireBaseOnChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot != null && dataSnapshot.getValue() != null) {
            BroadcastMessage model = dataSnapshot.getValue(BroadcastMessage.class);
            broadcastMessages.add(model);
            broadcastListRecyclerView.scrollToPosition(broadcastMessages.size() - 1);
            broadcastAdapter.notifyItemInserted(broadcastMessages.size() - 1);
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        fireBaseOnChildAdded(dataSnapshot, s);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    }
}
