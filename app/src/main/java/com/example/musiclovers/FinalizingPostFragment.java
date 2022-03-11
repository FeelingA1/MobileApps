package com.example.musiclovers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musiclovers.databinding.FragmentFinalizingPostBinding;
import com.example.musiclovers.databinding.FragmentSpotifySelectionBinding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FinalizingPostFragment extends Fragment{


    private FragmentFinalizingPostBinding binding;
    private String TAG = "FinalizingPostFragment";

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

        //Will have multiple setOnclickListeners to handle transitioning to other fragements or activities

        //Need to update the button so it isn't buttonFirst but maybe buttonPost
        binding.buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Must change the .nagigate to different fragments. needs to be a different id
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

}
