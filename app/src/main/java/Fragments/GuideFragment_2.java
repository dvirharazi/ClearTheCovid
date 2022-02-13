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
import androidx.navigation.Navigation;

import com.example.dvir_game_try.R;

public class GuideFragment_2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.guide_fragment_2, container, false);

        Button gotoFragment3 = rootView.findViewById(R.id.move_to_fragment_3);
        gotoFragment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
                requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_containerplay, new GuideFragment_3(), null).addToBackStack("Second Guidance").commit();
            }
        });

        gotoFragment3.setOnTouchListener(new View.OnTouchListener() {
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

        Animation arrowAnim = AnimationUtils.loadAnimation(container.getContext(), R.anim.arrow_right);
        ImageView arrow = rootView.findViewById(R.id.arrow);
        arrow.startAnimation(arrowAnim);

        ImageButton changeToMask = rootView.findViewById(R.id.img_enemy);
        changeToMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);
            }
        });

        return rootView;

    }
}
