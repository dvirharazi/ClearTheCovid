package Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ClearTheCovid.R;

import java.util.ArrayList;

public class InfoFragment extends Fragment {

    public static InfoFragment newInstance(ArrayList<Integer> enemyList, int lives, int level){
        InfoFragment fragment1 = new InfoFragment();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("enemiesList", enemyList);
        bundle.putInt("lives", lives);
        bundle.putInt("level", level);
        bundle.putBoolean("fragmentIsOpen", true);
        fragment1.setArguments(bundle);
        return fragment1;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.info_fregment, container, false);

        LinearLayout linearLayout = rootView.findViewById(R.id.list_of_enemies);
        LinearLayout heartsLayout = rootView.findViewById(R.id.list_of_hearts);

        LinearLayout enemiesPic = new LinearLayout(getContext());
        LinearLayout heartsPic = new LinearLayout(getContext());

        LinearLayout.LayoutParams enemiesLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        enemiesLayoutParams.setMargins(0, 10, 0, 10);

        enemiesPic.setLayoutParams(enemiesLayoutParams);
        enemiesPic.setOrientation(LinearLayout.HORIZONTAL);

        heartsPic.setLayoutParams(enemiesLayoutParams);
        heartsPic.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(100, 100);


        ArrayList<Integer> arr = (ArrayList<Integer>) getArguments().get("enemiesList");
        for (Integer i :arr) {
            ImageView imageView = new ImageView(rootView.getContext());
            imageView.setLayoutParams(imageViewLayoutParams);
            imageView.setImageDrawable(getResources().getDrawable(i));
            enemiesPic.addView(imageView);
        }
        linearLayout.addView(enemiesPic);

        int lives = (int) getArguments().get("lives");

        for (int i = 0; i < lives; i++) {
            ImageView imageView = new ImageView(rootView.getContext());
            imageView.setLayoutParams(imageViewLayoutParams);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.heart));
            heartsPic.addView(imageView);
        }
        heartsLayout.addView(heartsPic);

        TextView levelTxt = rootView.findViewById(R.id.level_txt);
        levelTxt.setText(levelTxt.getText() + " " + getArguments().get("level"));


        Button nextBtn = rootView.findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getArguments() != null;
                getArguments().putBoolean("fragmentIsOpen", false);
                getArguments().putIntegerArrayList("enemy_for_screen", (ArrayList<Integer>) getArguments().get("enemiesList"));
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        View.OnTouchListener shakeButtonListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final Animation animShake = AnimationUtils.loadAnimation(container.getContext(), R.anim.btn_animation);
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.startAnimation(animShake);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.clearAnimation();
                        break;
                }
                return false;
            }
        };

        nextBtn.setOnTouchListener(shakeButtonListener);

        return rootView;
    }
}
