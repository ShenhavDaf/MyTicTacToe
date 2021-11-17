package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class PlayersSetUp extends AppCompatActivity implements View.OnClickListener {

    private TextView playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGameBtn;
    //private Button backHomeBtn;
    private int rountCount;
    boolean activePlayer;

    /*    player1 => 0    player2 => 1    empty => 2    */
    int[] gameState = {
            2, 2, 2,
            2, 2, 2,
            2, 2, 2
    };

    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players_setup);

        playerStatus = (TextView) findViewById(R.id.playerStatus_tv);
        resetGameBtn = (Button) findViewById(R.id.reset_btn);

        for (int i = 0; i < buttons.length; i++) {
            int resourceID = getResources().getIdentifier("btn" + (i + 1), "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        activePlayer = true;
        playerStatus.setText("X Play");
    }
    ///////////////////////////////////////////////////////////////////

    public void playAgainOnClick(View view) {
        playAgain();
    }

    public void homeBackOnClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    ////////////////////////////////////////////////////////////////////


    public void playAgain() {
        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setBackgroundColor(getResources().getColor(R.color.grayBtn));
        }

        activePlayer = true;
        rountCount = 0;
        playerStatus.setText("X Play");
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals(""))
            return;

        String buttonID = view.getResources().getResourceEntryName(view.getId()); // return btn1,btn5

        //save only the number of the button
        int gameStatePointer = Integer.parseInt(
                buttonID.substring(buttonID.length() - 1, buttonID.length())
        ) - 1;

        if (activePlayer) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#FB194E"));
            gameState[gameStatePointer] = 0;
            playerStatus.setText("O Play");
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#446CFA"));
            gameState[gameStatePointer] = 1;
            playerStatus.setText("X Play");
        }

        rountCount++;

        if (checkWinner()) {

            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setEnabled(false);
            }

            if (activePlayer) {
                playerStatus.setText("X Won 🎉");
            } else {
                playerStatus.setText("O Won 🎉");
            }
        } else if (rountCount == 9) {
            playerStatus.setText("No Winner...");
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setEnabled(false);
            }

        } else {
            activePlayer = !activePlayer;
        }

//        resetGameBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                playAgain();
//            }
//        });

    }

    public boolean checkWinner() {
        boolean winnerResult = false;

        for (int[] winningPos : winningPositions) {
            if (gameState[winningPos[0]] != 2 &&
                    gameState[winningPos[0]] == gameState[winningPos[1]] &&
                    gameState[winningPos[1]] == gameState[winningPos[2]]
            ) {
                winnerResult = true;
//
                buttons[winningPos[0]].setBackgroundColor(getResources().getColor(R.color.winGreen));
                buttons[winningPos[1]].setBackgroundColor(getResources().getColor(R.color.winGreen));
                buttons[winningPos[2]].setBackgroundColor(getResources().getColor(R.color.winGreen));

            }
        }

        return winnerResult;
    }
}