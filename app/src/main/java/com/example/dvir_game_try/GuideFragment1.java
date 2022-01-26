package com.example.dvir_game_try;

import android.content.Intent;
import android.content.res.Resources;
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
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class GuideFragment1 extends Fragment {

    Stage stage;
    public static GuideFragment1 newInstance(ArrayList<Integer> enemyList, boolean fragmentIsOpen){
        GuideFragment1 fragment1 = new GuideFragment1();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("enemiesList", enemyList);
        bundle.putBoolean("fragmentIsOpen", true);
        fragment1.setArguments(bundle);
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.guide_fragment_1, container, false);
        LinearLayout linearLayout = rootView.findViewById(R.id.list_of_enemies);

        LinearLayout enemiesPic = new LinearLayout(getContext());

        LinearLayout.LayoutParams enemiesLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        enemiesLayoutParams.setMargins(0, 10, 0, 10);

        enemiesPic.setLayoutParams(enemiesLayoutParams);
        enemiesPic.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(100, 100);
        imageViewLayoutParams.setMargins(10, 10, 10, 10);
        imageViewLayoutParams.weight =1;


        ArrayList<Integer> arr = (ArrayList<Integer>) getArguments().get("enemiesList");
        for (Integer i :arr) {
            System.out.println(i);
            ImageView imageView = new ImageView(rootView.getContext());
            imageView.setLayoutParams(imageViewLayoutParams);
            imageView.setImageDrawable(getResources().getDrawable(i));
            enemiesPic.addView(imageView);
        }
        linearLayout.addView(enemiesPic);

        Button nextBtn = rootView.findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(rootView.getContext(), GameActivity.class);
//                intent.putExtra("level", stage.getLevel());
//                intent.putExtra("enemiesPic", stage.updateEnemiesPicArray());
//                intent.putExtra("peoplesPic", stage.updatePeoplesPicArray());
//                intent.putExtra("time", stage.getTime());
//                startActivity(intent);
                getArguments().putBoolean("fragmentIsOpen", false);
                requireActivity().onBackPressed();
            }
        });
        return rootView;
    }
}
