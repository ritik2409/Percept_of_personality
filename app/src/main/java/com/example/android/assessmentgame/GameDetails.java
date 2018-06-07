package com.example.android.assessmentgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class GameDetails extends AppCompatActivity {

    RecyclerView rv;
    ChooseGameAdapter adapter;
    String[] story;
    Integer[] drawable;
//    private EditText name_text;
//    private Spinner gender_spinner;
//    private Spinner prof_spinner;
//    private ArrayAdapter genderAdapter;
//    private ArrayAdapter profAdapter;
//    private String gender;
//    private String name;
//    private String profession;
//    private Button btn;
//    int gender_pos, prof_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        story = new String[]{"Smitha's Story","Rahul's Story","Saurabh's Story","Boss Story"};
        drawable = new Integer[]{R.drawable.smitha_story,R.drawable.rahul_story,
                R.drawable.saurabh_story,R.drawable.boss_story};

        rv = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new ChooseGameAdapter(this,story,drawable,R.layout.story_card);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new DividerItemDecoration(this,GridLayoutManager.VERTICAL));
        rv.addItemDecoration(new DividerItemDecoration(this,GridLayoutManager.HORIZONTAL));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

    }
}

//        name_text = (EditText) findViewById(R.id.name);
//        gender_spinner = (Spinner) findViewById(R.id.gender);
//        prof_spinner = (Spinner) findViewById(R.id.profession);
//
//        genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
//        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        gender_spinner.setAdapter(genderAdapter);
//
//
//        profAdapter = ArrayAdapter.createFromResource(this, R.array.profession, android.R.layout.simple_spinner_item);
//        profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        prof_spinner.setAdapter(profAdapter);
//
//
//        btn = (Button) findViewById(R.id.submit);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name = name_text.getText().toString();
//                gender = gender_spinner.getSelectedItem().toString();
//                profession = prof_spinner.getSelectedItem().toString();
//                gender_pos = gender_spinner.getSelectedItemPosition();
//                prof_pos = prof_spinner.getSelectedItemPosition();
//
//
//                if ((TextUtils.isEmpty(name) || (gender_pos == 0) || (prof_pos == 0))) {
//                    Toast.makeText(GameDetails.this, "Please fill all the details!", Toast.LENGTH_LONG).show();
//
//                } else {
//                    Intent i = new Intent(GameDetails.this, GameBegins.class);
//                    i.putExtra("Gender", gender);
//                    i.putExtra("Profession", profession);
//                    i.putExtra("Name", name);
//                    startActivity(i);
//
//                }
//
//
//            }
//        });
//
//
