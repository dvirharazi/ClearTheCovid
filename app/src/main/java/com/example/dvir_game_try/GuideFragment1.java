package com.example.dvir_game_try;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GuideFragment1 extends Fragment {

//    List<Integer> enemiesPic = new ArrayList<>();
    ArrayList<Integer> peoples = new ArrayList<>(Arrays.asList(
            R.drawable.people_1,R.drawable.people_2,R.drawable.people_3,R.drawable.people_4,
            R.drawable.people_5, R.drawable.people_6, R.drawable.people_7,R.drawable.people_8, R.drawable.people_9,
            R.drawable.people_10, R.drawable.people_11, R.drawable.people_12, R.drawable.people_13,
            R.drawable.people_14,R.drawable.people_15,R.drawable.people_16,R.drawable.people_18,R.drawable.people_19,
            R.drawable.people_20, R.drawable.people_21
    ));
    int specificPEnemy;
    int[] enemiesPicArr = new int[3];
    Random random = new Random();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.guide_fragment_1, container, false);
        LinearLayout linearLayout = rootView.findViewById(R.id.list_of_enemies);

        LinearLayout enemies = new LinearLayout(getContext());

        LinearLayout.LayoutParams enemiesLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        enemiesLayoutParams.setMargins(0, 10, 0, 10);

        enemies.setLayoutParams(enemiesLayoutParams);
        enemies.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(100, 100);
        imageViewLayoutParams.setMargins(10, 10, 10, 10);
        imageViewLayoutParams.weight =1;

        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(rootView.getContext());
            imageView.setLayoutParams(imageViewLayoutParams);
            specificPEnemy = random.nextInt(peoples.size());
            imageView.setImageDrawable(getResources().getDrawable(peoples.get(specificPEnemy)));
//            enemiesPic.add(peoples.get(specificPEnemy));
            enemiesPicArr[i] = peoples.get(specificPEnemy);
            peoples.remove(peoples.get(specificPEnemy));
            enemies.addView(imageView);
        }
        linearLayout.addView(enemies);

        Button nextBtn = rootView.findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), GameActivity.class);
                intent.putExtra("enemies", enemiesPicArr);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
