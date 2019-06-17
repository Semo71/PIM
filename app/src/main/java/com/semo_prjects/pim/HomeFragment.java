package com.semo_prjects.pim;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.semo_prjects.pim.Todo.MainTaskActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private Button mReligionButton;
    private Button mCommunityButton;
    private Button mHealthButton;
    private Button mWorkButton;
    private Button mAddButton;
    private MeFragment mMeFragment;



    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mMeFragment = new MeFragment();
        Button mRelegionButton= view.findViewById(R.id.relegionButton);
        mRelegionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReligionFragment religionFragment = new ReligionFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainFrame,religionFragment);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });



        return view;

    }

}
