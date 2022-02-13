package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ClearTheCovid.R;

public class GuideFragment_3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.guide_fragment_3, container, false);

        Button goToMenu = rootView.findViewById(R.id.fragment_3_to_menu);
        goToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        goToMenu.setOnTouchListener(new View.OnTouchListener() {
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
        });

        Animation arrowAnim = AnimationUtils.loadAnimation(container.getContext(), R.anim.arrow_left);
        ImageView arrow = rootView.findViewById(R.id.arrow);
        arrow.startAnimation(arrowAnim);


        Animation covidAnim = AnimationUtils.loadAnimation(container.getContext(), R.anim.resize_covid);
        ImageButton covidBtn = rootView.findViewById(R.id.img_enemy);

        covidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                covidBtn.startAnimation(covidAnim);
            }
        });


        return rootView;

    }

}
