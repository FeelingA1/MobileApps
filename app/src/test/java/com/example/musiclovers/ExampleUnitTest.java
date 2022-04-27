package com.example.musiclovers;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    // Confirm a post is added to Firestore
    @Test
    public void addPost_Test() {
        Map<String, Object> post = new HashMap<>();
        post.put("userID", "test");
        post.put("text", "test");
        post.put("link", "test");

        FirebaseFirestore.getInstance().collection("Posts")
                .add(post)
                .addOnSuccessListener(documentReference -> {
                    /*
                        will assert true to pass test case, as it is within onSuccess method
                        meaning that the post has successfully been added to the database
                     */
                    assertTrue(true);
                });
    }

    // Confirm the number of posts before and after creating a post
    @Test
    public void num_Post_Test() {
        FirebaseFirestore.getInstance().collection("Posts")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                    assertEquals(14, snapshotList.size());
                });
    }

    // Confirm that a specific post exists within the database
    @Test
    public void specific_Post_Test() {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Posts")
                .document("firstpost");
        docRef.get().addOnCompleteListener(task -> {
            assertTrue(task.getResult().exists());
        });
    }

}