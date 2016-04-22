package com.swingex;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MineSweeper extends JFrame {
    public static final int GRID_ROWS = 10;
    public static final int GRID_COLS = 10;

    public static final int GRID_SIZE = 60;
    public static final int GRID_WIDTH = GRID_COLS * GRID_SIZE;
    public static final int GRID_HEIGHT = GRID_ROWS * GRID_SIZE;
    public static final Color BGCOLOR_NOT_REVEALED = Color.PINK;
    public static final Color FGCOLOR_NOT_REVEALED = Color.RED;
    public static final Color BGCOLOR_REVEALED = Color.DARK_GRAY;
    public static final Color FGCOLOR_REVEALED = Color.LIGHT_GRAY;
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
    public boolean[][] mines = new boolean[GRID_ROWS][GRID_COLS];
    public boolean[][] flags = new boolean[GRID_ROWS][GRID_COLS];
    public boolean gameOver = false;
    public boolean won = false;
    public static GridButton[][] gridButtons = new GridButton[GRID_ROWS][GRID_COLS];

    public MineSweeper(){
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        MineGrid mineGrid = new MineGrid();
        cp.add(mineGrid, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("My Mine Sweeper");
        setSize(GRID_WIDTH, GRID_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        intGame();
    }

    private void intGame() {
        gameOver = false;
        for (int i = 0; i < GRID_ROWS; i++) {
            for (int j = 0; j < GRID_COLS; j++) {
                gridButtons[i][j].setEnabled(true);
                gridButtons[i][j].setBackground(BGCOLOR_NOT_REVEALED);
                gridButtons[i][j].setForeground(FGCOLOR_NOT_REVEALED);
                gridButtons[i][j].setFont(FONT_NUMBERS);
                gridButtons[i][j].setText("");
                mines[i][j] = false;
                flags[i][j] = false;
            }
        }
        setMines();
    }

    private void setMines(){
        Random r = new Random();
        int level = 10;
        for (int i = 1; i <= level; i++) {
            int row = r.nextInt(GRID_ROWS);
            int col = r.nextInt(GRID_COLS);
            mines[row][col] = true;
            gridButtons[row][col].setMine(true);
        }
    }

    class MineGrid extends JPanel {

        public MineGrid(){
            super();
            setLayout(new GridLayout(GRID_ROWS, GRID_COLS, 1, 1));
            setSize(WIDTH - 100, HEIGHT - 100);
            addGridButton();
        }

        private void addGridButton() {
            for (int i = 0; i < GRID_ROWS; i++) {
                for (int j = 0; j < GRID_COLS; j++) {
                    gridButtons[i][j] = new GridButton(i, j);
                    add(gridButtons[i][j]);
                }
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MineSweeper::new);

//TODO 1: Beautify your graphical interface, e.g., color, font, layout, etc.
//TODO 2: Choice of difficulty levels.
//TODO 3: Create a status bar (JTextField at the south zone of BorderLayout) to show the messages (e.g., the number of mines remaining). (Google "java swing statusbar").
//TODO 4: Create a menu bar for options such as "File" ("New Game", "Reset Game", "Exit"), "Options", and "Help" (Use JMenuBar, JMenu, and JMenuItem classes).
//TODO 5: Timer (pause/resume), score, progress bar.
//TODO 6: A side panel for command, display, strategy?
//TODO 7: Choice of game board - there are many variations of game board!
//TODO 8: Sound effect, background music, enable/disable sound?
//TODO 9: High score and player name?
//TODO 10: Hints and cheats?
//TODO 11: Choice of display "theme"?
//TODO 12: Use of images and icons?
    }
}
