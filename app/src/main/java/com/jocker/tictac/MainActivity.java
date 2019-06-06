package com.jocker.tictac;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // 0: yellow, 1: red, 2: empty

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    int activePlayer = 0;

    Dialog popAddPost;

    boolean gameActive = true;
    int noOneWin=0;
    LinearLayout mwinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view) {


        ImageView counter = (ImageView) view;
        mwinner=findViewById(R.id.linearLayout);

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive) {
            noOneWin++;

            gameState[tappedCounter] = activePlayer;

//            counter.setTranslationY(-1500);

            counter.animate().alpha(0);
            if (activePlayer == 0) {

                counter.setImageResource(R.drawable.yellow);

                activePlayer = 1;

            } else {

                counter.setImageResource(R.drawable.red);

                activePlayer = 0;

            }

//            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            counter.animate().alpha(1);
            for (int[] winningPosition : winningPositions) {

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                    // Somone has won!

                    gameActive = false;

                    String winner = "";

                    if (activePlayer == 1) {

                        winner = "Yellow";


                    } else if(activePlayer==0) {

                        winner = "Red";


                    }

                    iniPopup(winner + " has won!");

                    popAddPost.show();
                }


            }



            if(noOneWin==9 && gameActive)
            {
                iniPopup("No One Won !");
                popAddPost.show();
                noOneWin=0;
            }




        }
    }

    private void iniPopup(String Winnner) {
        popAddPost = new Dialog(this);
        popAddPost.setContentView(R.layout.popup);
        Objects.requireNonNull(popAddPost.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddPost.getWindow().getAttributes().gravity = Gravity.CENTER;


        TextView title = popAddPost.findViewById(R.id.pop_title);
        Button play_again = popAddPost.findViewById(R.id.pop_play_again);
        Button exit = popAddPost.findViewById(R.id.pop_exit);


        title.setText(Winnner);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain(v);
                popAddPost.dismiss();


            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        popAddPost.show();
        popAddPost.setCancelable(false);


    }



    public void playAgain(View view) {
        noOneWin=0;


        mwinner.setVisibility(View.INVISIBLE);

        GridLayout gridLayout =  findViewById(R.id.gridLayout);

        for(int i=0; i<gridLayout.getChildCount(); i++) {

            ImageView counter = (ImageView) gridLayout.getChildAt(i);

            counter.setImageDrawable(null);

        }

        for (int i=0; i<gameState.length; i++) {

            gameState[i] = 2;

        }

        activePlayer = 0;

        gameActive = true;

    }



}
