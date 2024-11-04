package com.example.ventz;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TreinarTela extends AppCompatActivity {

    private CardView cardFront, cardBack;
    private Boolean isFront = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_treinar_tela);

        cardFront = findViewById(R.id.cardFront);
        cardBack = findViewById(R.id.cardBack);

        RelativeLayout cardMain = findViewById(R.id.cardMain);

        cardMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCardAnim();
            }
        });
    }

    private void flipCardAnim() {
        AnimatorSet setOut = (AnimatorSet) AnimatorInflater.loadAnimator(TreinarTela.this, R.animator.card_flip_out);
        AnimatorSet setIn = (AnimatorSet) AnimatorInflater.loadAnimator(TreinarTela.this, R.animator.card_flip_in);

        setOut.setTarget(isFront ? cardFront : cardBack);
        setIn.setTarget(isFront ? cardBack : cardFront);

        setOut.start();
        setIn.start();

        isFront = !isFront;
    }
}
