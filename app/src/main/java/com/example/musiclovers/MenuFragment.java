package com.example.musiclovers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.musiclovers.databinding.FragmentMenuBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;
    private SpotifyPlayer mSpotifyPlayer;
    private Button darkToggleBtn;
    private final String TAG = "MenuFragment";

    // Access Cloud Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Create ListView for posts
    ListView listView;
    // Create CopyOnWriteArrayList for concurrent collection of posts from Firebase
    CopyOnWriteArrayList<String> feed = new CopyOnWriteArrayList<>();
    // Adapter for ListView
    ArrayAdapter arrayAdapter;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // Collect the posts from Firebase and store them into the ArrayList 'feed'
        getPosts();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.d(TAG, "onCreateView");
        binding = FragmentMenuBinding.inflate(inflater, container, false);

        listView = binding.listView.findViewById(R.id.list_view);

        arrayAdapter = new ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, feed);

        Log.d(TAG, "feed population: " + feed.toString());
        if (feed.isEmpty()) feed = generateStaticFeed();

        arrayAdapter = new ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, feed);
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
        setDarkModeButton();
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrayAdapter.notifyDataSetChanged();
            }
        });
        binding.buttonFirst.setOnClickListener(view1 -> NavHostFragment.findNavController(MenuFragment.this)
                .navigate(R.id.action_MenuFragment_to_FinalizingPostFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setDarkModeButton() {
        darkToggleBtn = (Button) getView().findViewById(R.id.toggleButton);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", getActivity().MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean darkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if(darkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            darkToggleBtn.setText("Day Mode");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            darkToggleBtn.setText("Dark Mode");
        }
        darkToggleBtn.setOnClickListener(
                view -> {
                    if(darkModeOn) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.putBoolean("isDarkModeOn", false);
                        editor.apply();
                        darkToggleBtn.setText("Dark Mode");
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.putBoolean("isDarkModeOn", true);
                        editor.apply();
                        darkToggleBtn.setText("Day Mode");
                    }
                }
        );
    }

    public void getPosts() {
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
    }

    // faux generate feed just so that the feed can be populated with stuff until the real one is fixed
    public CopyOnWriteArrayList<String> generateStaticFeed(){
        CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
        arrayList.add("User: test \n\nthis song rocks \n\nLink:");
        arrayList.add("User: test \n\ntest \n\nLink: test");
        arrayList.add("User: test \n\nthis song bangs \n\nLink: osu.edu");
        arrayList.add("User: test \n\nThis is a really good song\n\nLink: youtube.com");
        arrayList.add("User: musiclover \n\nsong good \n\nLink: https://open.spotify.com/track/5UqCQaDshqbIk3pkhy4Pjg?si=febf62e52b43462a");
        arrayList.add("User: musiclover \n\nthis bangs \n\nLink: https://open.spotify.com/track/5LxvwujISqiB8vpRYv887S?si=a1cbffe31e0b408d");
        arrayList.add("User: test \n\ndds \n\nLink: d");
        arrayAdapter.notifyDataSetChanged();
        return arrayList;
    }

}