package com.example.musiclovers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.musiclovers.databinding.FragmentFinalizingPostBinding;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.HashMap;
import java.util.Map;

public class FinalizingPostFragment extends Fragment{

    //Thought this would get us to the next fragment, but maybe not right. Might want activity
    private FragmentFinalizingPostBinding binding;
    private String TAG = "FinalizingPostFragment";
    private EditText mPostContentEditText;
    private EditText mLinkEditText;

    // Initialize Cloud Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.d(TAG, "onCreateView");
        binding = FragmentFinalizingPostBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLinkEditText = (EditText) view.findViewById(R.id.LinkTextBox);
        mPostContentEditText = (EditText) view.findViewById(R.id.ThoughtsTextBox);

        //Will have multiple setOnclickListeners to handle transitioning to other fragments or activities

        //Need to update the button so it isn't buttonFirst but maybe buttonPost
        binding.buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPost();

                //Must change the .navigate to different fragments. needs to be a different id
                NavHostFragment.findNavController(FinalizingPostFragment.this)
                        .navigate(R.id.action_FinalizingPost_to_Main);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Add post to the database
    public void addPost() {
        Map<String, Object> post = new HashMap<>();
        post.put("userID", "test");
        post.put("text", mPostContentEditText.getText().toString());
        post.put("link", mLinkEditText.getText().toString());

        // Add document with generated ID
        db.collection("Posts")
                .add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
