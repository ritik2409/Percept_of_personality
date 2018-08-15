package com.moodcafe.assessmentgame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by ritik on 6/20/2018.
 */

public class settingFragment extends android.support.v4.app.Fragment {
//    Button clearall;
    RecyclerView rv;
    ResumeManager resumeManager;
    GamesPlayedAdapter adapter;
    ArrayList<GamesPlayed> gamesPlayed = new ArrayList<>();
    GameResumeParser gameResumeParser;
    ArrayList<GameProgress> gameProgresses = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Settings");
        rv = view.findViewById(R.id.rv_played);
//        clearall = view.findViewById(R.id.clearall);
        resumeManager = new ResumeManager(getContext());
//        clearall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                confirm(0, 0, 2);
//            }
//        });

        storeValues();
        if (gamesPlayed.size() == 0) {
            rv.setVisibility(View.GONE);
            view.findViewById(R.id.textview).setVisibility(View.GONE);
//            clearall.setVisibility(View.GONE);
        }
        else {
            adapter = new GamesPlayedAdapter(getContext(), gamesPlayed, R.layout.gamesplayedlist);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            rv.setLayoutManager(layoutManager);
            rv.setHasFixedSize(true);
            rv.setAdapter(adapter);
            view.findViewById(R.id.empty).setVisibility(View.GONE);
        }

    }

    private void storeValues() {
        String json = resumeManager.myData();
        Gson gson = new Gson();
        gameResumeParser = gson.fromJson(json, GameResumeParser.class);
        gameProgresses = gameResumeParser.getValues();
        GamesPlayed gamesplayedobj;
        for (int i = 0; i < gameProgresses.size(); i++) {
            if (gameProgresses.get(i).getSceneId() != 0) {
                gamesplayedobj = new GamesPlayed(gameProgresses.get(i).getStoryId(), gameProgresses.get(i).getProgress(), gameProgresses.get(i).getStoryTitle());
                gamesPlayed.add(gamesplayedobj);

            }

        }
    }

    public class GamesPlayed {
        public GamesPlayed(int storyId, int progressvalue, String storyTitle) {
            this.storyId = storyId;
            this.progressvalue = progressvalue;
            this.storyTitle = storyTitle;
        }

        int storyId;
        int progressvalue;
        String storyTitle;

        public int getStoryId() {
            return storyId;
        }


        public int getProgressvalue() {
            return progressvalue;
        }


        public String getStoryTitle() {
            return storyTitle;
        }


    }


    public class GamesPlayedAdapter extends RecyclerView.Adapter<GamesPlayedAdapter.ViewHolder> {

        int resource;
        ArrayList<GamesPlayed> gamesPlayed = new ArrayList<>();
        Context context;


        public GamesPlayedAdapter(Context context, ArrayList<GamesPlayed> gamesPlayed, int gamesplayedlist) {
            this.gamesPlayed = gamesPlayed;
            this.resource = gamesplayedlist;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
            return new ViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

            holder.storyheader.setText(gamesPlayed.get(position).getStoryTitle());
            holder.progressvalue.setText(String.valueOf(gamesPlayed.get(position).getProgressvalue()) + "%");
            holder.progressgame.setProgress(resumeManager.getGameProgress(gamesPlayed.get(position).getStoryId()));
            holder.settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), view, Gravity.END);

                    popupMenu.inflate(R.menu.gamesettingspopup);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.clear:
                                    confirm(gamesPlayed.get(position).getStoryId(), position, 1);
                                    break;
                                case R.id.changename:
                                    takename(gamesPlayed.get(position).getStoryId());
                                    break;
                                case R.id.result_view:
                                    if (gamesPlayed.get(position).getProgressvalue() == 100) {
                                        Intent intent = new Intent(getContext(), ResultActivity.class);
                                        intent.putExtra("Story Id", gamesPlayed.get(position).getStoryId());
                                        startActivity(intent);
                                    } else {
                                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                        alert.setTitle("Analysis");
                                        alert.setMessage("Story is not completed. Complete the story to get the result.");
                                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                        alert.show();

                                    }

                            }


                            return false;
                        }
                    });
                    popupMenu.show();


                }
            });

        }

        @Override
        public int getItemCount() {
            return gamesPlayed.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ProgressBar progressgame;
            TextView storyheader;
            TextView progressvalue;
            ImageButton settings;

            public ViewHolder(View itemView) {
                super(itemView);
                progressgame = itemView.findViewById(R.id.game_progressbar);
                progressvalue = itemView.findViewById(R.id.game_progressvalue);
                storyheader = itemView.findViewById(R.id.game_title);
                settings = itemView.findViewById(R.id.game_settings);
//                settings.setOnClickListener(this);
//                settings.setClickable(true);

            }

//            @Override
//            public void onClick(View view) {
//
//                PopupMenu popupMenu = new PopupMenu(getContext(), view, Gravity.END);
//
//                popupMenu.inflate(R.menu.gamesettingspopup);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        switch (menuItem.getItemId()) {
//                            case R.id.clear:
//                                confirm(gamesPlayed.get(getAdapterPosition()).getStoryId());
//                                break;
//                            case R.id.changename:
//                                takename(gamesPlayed.get(getAdapterPosition()).getStoryId());
//                                break;
//
//                        }
//
//
//                        return false;
//                    }
//                });
//                popupMenu.show();
//
//            }
        }
    }

    private void takename(final int storyId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final EditText name = new EditText(getContext());
        name.setMaxLines(1);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        name.setLayoutParams(lp);
        builder.setTitle("Update Protagonist");
        builder.setView(name);
        builder.setIcon(R.drawable.user);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!TextUtils.isEmpty(name.getText())) {
                    resumeManager.updateProtagonist(storyId, name.getText().toString());
                    Toast.makeText(getContext(), "Name updated with " + name.getText().toString(), Toast.LENGTH_SHORT).show();
                    dialogInterface.cancel();
                } else

                    Toast.makeText(getContext(), "Name can't be blank!", Toast.LENGTH_SHORT).show();


            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .show();
    }

    private void confirm(final int storyId, final int position, final int type) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Are you sure you want to delete current progress?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (type == 1) {
                            resumeManager.clearGameProgress(storyId);
                            gamesPlayed.remove(position);
                            adapter.notifyItemRemoved(position);
                            adapter.notifyItemRangeChanged(position, gamesPlayed.size());
                        }
// else resumeManager.clearAllGame();

//                        Fragment frg = null;
//                        frg = getFragmentManager().findFragmentByTag("Settings");
//                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        ft.detach(frg);
//                        ft.attach(frg);
//                        ft.commit();
                        Toast.makeText(getContext(), "Cleared Progress!", Toast.LENGTH_SHORT).show();
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }


}
