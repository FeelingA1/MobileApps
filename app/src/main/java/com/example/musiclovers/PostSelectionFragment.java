package com.example.musiclovers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musiclovers.databinding.FragmentPostSelectionBinding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class PostSelectionFragment extends Fragment {

    private FragmentPostSelectionBinding binding;
    private String TAG = "PostSelectionFragment";
    // private final Intent SpotifySelectionIntent = new Intent(String.valueOf(R.id.action_PostSelection_to_SpotifySelection));

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.d(TAG, "onCreateView");
        binding = FragmentPostSelectionBinding.inflate(inflater, container, false);
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

        //Will have multiple setOnclickListeners to handle transitioning to other fragements or activities

        //Need to update the button so it isn't buttonFirst but maybe buttonPost
        /* binding.buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Must change the .nagigate to different fragments
                NavHostFragment.findNavController(PostSelectionFragment.this)
                        .navigate(R.id.action_PostSelection_to_SpotifySelection);
            }
        });

        //maybe redundant if going to same fragment
        binding.buttonAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Must change the .nagigate to different fragments
                NavHostFragment.findNavController(PostSelectionFragment.this)
                        .navigate(R.id.action_PostSelection_to_SpotifySelection);
            }
        }); */


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
