package com.example.musiclovers;

import android.os.Bundle;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SingleFragmentActivity {

    /* private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding; */
    private String TAG = "MainActivity";

    // Access Cloud Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Create ListView for posts
    ListView listView;

    @Override
    protected Fragment createFragment() {
        return new MenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> arrayList = new ArrayList<>();
        listView = findViewById(R.id.list_view);
        arrayList = generateFeed();

        ArrayList<String> finalArrayList = arrayList;
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
                            post = post + "User: " + snapshot.get("userID").toString() + "\n\n";
                            // get post contents
                            post = post + snapshot.get("text").toString() + "\n\n";
                            // get spotify link
                            post = post + "Link: " + snapshot.get("link").toString();
                            Log.d(TAG, "POST: " + post);
                            finalArrayList.add(post);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Method to retrieve all posts from Firestore database
    public void ginerateFeed() {
        ArrayList<String> feed = new ArrayList<>();
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
                            feed.add(post);
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

    public ArrayList<String> generateFeed(){
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
}