package com.example.musiclovers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.musiclovers.databinding.FragmentMenuBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;
    private SpotifyPlayer mSpotifyPlayer;
    private final String TAG = "MenuFragment";

    // Access Cloud Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Create ListView for posts
    ListView listView;
    // Create CopyOnWriteArrayList for concurrent collection of posts from Firebase
    CopyOnWriteArrayList<String> feed = new CopyOnWriteArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.d(TAG, "onCreateView");
        binding = FragmentMenuBinding.inflate(inflater, container, false);

        listView = binding.listView.findViewById(R.id.list_view);
        // Collect the posts from Firebase and store them into the ArrayList 'feed'
        db.collection("Posts")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.d(TAG, "onSuccess: Retrieving posts");
                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot snapshot : snapshotList) {
                        Log.d(TAG, "onSuccess: " + Objects.requireNonNull(snapshot.getData()));
                        // get contents of post
                        String post = "User: " + Objects.requireNonNull(snapshot.get("userID")) + "\n\n"
                                + Objects.requireNonNull(snapshot.get("text")) + "\n\n"
                                + "Link: " + Objects.requireNonNull(snapshot.get("link"));
                        feed.add(post);
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "onFailure: ", e));

        ArrayAdapter arrayAdapter = new ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, feed);
        listView.setAdapter(arrayAdapter);

        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        mSpotifyPlayer = new SpotifyPlayer(getActivity());
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

        binding.buttonFirst.setOnClickListener(view1 -> NavHostFragment.findNavController(MenuFragment.this)
                .navigate(R.id.action_MenuFragment_to_FinalizingPostFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // faux generate feed just so that the feed can be populated with stuff until the real one is fixed
    public ArrayList<String> ginerateFeed(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("hello");
        arrayList.add("User: test \n\nthis song rocks \n\nLink:");
        arrayList.add("User: test \n\ntest \n\nLink: test");
        arrayList.add("User: test \n\nthis song bangs \n\nLink: osu.edu");
        arrayList.add("User: test \n\nThis is a really good song\n\nLink: youtube.com");
        arrayList.add("User: musiclover \n\nsong good \n\nLink: https://open.spotify.com/track/5UqCQaDshqbIk3pkhy4Pjg?si=febf62e52b43462a");
        arrayList.add("User: musiclover \n\nthis bangs \n\nLink: https://open.spotify.com/track/5LxvwujISqiB8vpRYv887S?si=a1cbffe31e0b408d");
        arrayList.add("User: test \n\ndds \n\nLink: d");
        return arrayList;
    }

    // Method to retrieve all posts from Firestore database --BROKEN
    public void generateFeed() {
        db.collection("Posts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "onSuccess: Retrieving posts");
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            String post = "";
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            // get username
                            post = snapshot.get("userID").toString() + "\n";
                            // get post contents
                            post = snapshot.get("text").toString() + "\n";
                            // get spotify link
                            post = snapshot.get("link").toString();
                            //feed.add(post);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }

}