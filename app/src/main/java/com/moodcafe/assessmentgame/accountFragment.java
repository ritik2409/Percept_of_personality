package com.moodcafe.assessmentgame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.HashMap;

/**
 * Created by ritik on 6/20/2018.
 */

public class accountFragment extends Fragment {

    EditText fname,lname,mail;
    SessionManager sessionManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Account");
        fname = view.findViewById(R.id.fname);
        lname = view.findViewById(R.id.lname);
        mail = view.findViewById(R.id.mail);

        sessionManager = new SessionManager(getActivity());

        HashMap<String,String> details = new HashMap<>();
        details = sessionManager.getUserDetails();
        fname.setText(details.get(sessionManager.KEY_FIRSTNAME));
        lname.setText(details.get(sessionManager.KEY_LASTNAME));
        mail.setText(details.get(sessionManager.KEY_EMAIL));

    }
}
