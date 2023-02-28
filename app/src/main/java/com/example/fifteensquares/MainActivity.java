package com.example.fifteensquares;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int BOARD_SIZE = 4;
    private Button[][] buttons = new Button[BOARD_SIZE][BOARD_SIZE];
    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private int emptyRow, emptyCol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button shuffle = findViewById(R.id.shuffle);
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shuffleBoard();
            }
        });


        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        //sets each button with a click listener
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button button = new Button(this);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //finds the button being clicked
                        int row = 0, col = 0;
                        for (int i = 0; i < BOARD_SIZE; i++) {
                            for (int j = 0; j < BOARD_SIZE; j++) {
                                if (v == buttons[i][j]) {
                                    row = i;
                                    col = j;
                                    break;
                                }
                            }
                        }
                        //calls the moveTile function and updates the button
                        moveTile(row,col);
                        updateButtons();
                    }
                });
                buttons[row][col] = button;
                board[row][col] = row * BOARD_SIZE + col + 1;
                gridLayout.addView(button);
            }
        }
        emptyRow = BOARD_SIZE - 1;
        emptyCol = BOARD_SIZE - 1;
        board[emptyRow][emptyCol] = 0;
        buttons[emptyRow][emptyCol].setVisibility(View.INVISIBLE);
        shuffleBoard();
        updateButtons();
    }

    private void shuffleBoard() {
        // Shuffle the board by swapping tiles
        int n = BOARD_SIZE-1;
        int m = BOARD_SIZE-1;
        Random rand = new Random();
        while(n>0 && m >0){
            int randNumX = rand.nextInt(n--);
            int randNumY = rand.nextInt(m--);
            int temp = board [n][m];
            board [randNumX][randNumY] = board [n][m];
            board [n][m] = temp;
        }

        if (isSolved())
            shuffleBoard();
    }

    private void updateButtons() {
    //updates the buttons on the grid
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int value = board[row][col];
                if (value == 0) {
                    // Empty space
                    buttons[row][col].setVisibility(View.INVISIBLE);
                }
                else {
                    // Tile with a number
                    buttons[row][col].setVisibility(View.VISIBLE);
                    buttons[row][col].setText(String.valueOf(value));
                }
            }
        }
    }


    private void moveTile(int row, int col) {
        //checks if the clicked button is empty
        if (row == emptyRow) {
            int diff = col - emptyCol;
            if (diff == 1 || diff == -1) {
                // Move the tile
                board[row][col] = 0;
                board[emptyRow][emptyCol] = row * BOARD_SIZE + col + 1;
                emptyCol = col;
                updateButtons();
                if (isSolved()) {
                    System.out.print("You Won!");
                }
            }
        }
        else if (col == emptyCol) {
            int diff = row - emptyRow;
            if (diff == 1 || diff == -1) {
                // Move the tile
                board[row][col] = 0;
                board[emptyRow][emptyCol] = row * BOARD_SIZE + col + 1;
                emptyRow = row;
                updateButtons();
                if (isSolved()) {
                    System.out.print("You Won!");
                }
            }
        }
    }


    private boolean isSolved() {
        // Check if the board is solved
        int count = 0;
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j=0; j < i; j++){
                if(board[i][j] > board[i][i]){
                    count++;
                }
            }
        }
        return count%2 == 0;
    }
}
