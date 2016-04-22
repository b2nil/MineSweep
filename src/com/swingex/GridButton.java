package com.swingex;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import static com.swingex.MineSweeper.*;

public class GridButton extends JButton implements MouseListener {
    private int row, col;
    private boolean hasMine;
    private boolean isRevealed;
    private boolean hasFlag;
    private boolean gameOver;
    private boolean won;

    public GridButton(int row, int col){
        super();
        this.row = row;
        this.col = col;
        hasMine = false;
        isRevealed = false;
        hasFlag = false;
        gameOver = false;
        won = false;
        addMouseListener(this);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean hasMine() {
        return hasMine;
    }

    public void setMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
        if(revealed){
            setEnabled(false);
            setBackground(BGCOLOR_REVEALED);
            setForeground(FGCOLOR_REVEALED);
        }else {
            setEnabled(true);
            setBackground(BGCOLOR_NOT_REVEALED);
            setForeground(FGCOLOR_NOT_REVEALED);
        }

    }

    public boolean isGameOver(){
        return gameOver;
    }

    public void  setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public boolean hasFlag() {
        return hasFlag;
    }

    public void setFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
        if(hasFlag){
           setFont(FONT_NUMBERS);
           setText("B");
        }else {
           setFont(FONT_NUMBERS);
           setText("");
        }
    }

    public void autoReveal(GridButton[][] btns){
        List<GridButton> btnList = getNeighbors(btns);
        setRevealed(true);
        setAnchorNumbers(btns, btnList);
        removeMouseListener(this);

        if(isNeighborsSafe(btnList)){
            btnList.stream().forEach(btn -> {
                btn.setRevealed(true);
                btn.setAnchorNumbers(btns, btn.getNeighbors(btns));
                btn.removeMouseListener(this);
            });
        }
    }

    public void setAnchorNumbers(GridButton[][] btns, List<GridButton> btnList){
        int mineCounter = 0;
        for(GridButton btn : btnList){
            if(btn.hasMine())
                ++mineCounter;
        }
        if(mineCounter != 0) {
            this.setFont(FONT_NUMBERS);
            this.setText(String.valueOf(mineCounter));
        }
    }


    public List<GridButton> getNeighbors(GridButton[][] btns){
        List<GridButton> btnList = new ArrayList<>();
        if(this.row == 0){
            if(this.col == 0){
                btnList.add(btns[row][col + 1]);
                btnList.add(btns[row + 1][col]);
                btnList.add(btns[row + 1][col + 1]);
            }else if(this.col == GRID_COLS - 1){
                btnList.add(btns[row][col - 1]);
                btnList.add(btns[row + 1][col]);
                btnList.add(btns[row + 1][col - 1]);
            }else{
                btnList.add(btns[row][col + 1]);
                btnList.add(btns[row][col - 1]);
                btnList.add(btns[row + 1][col + 1]);
                btnList.add(btns[row + 1][col]);
                btnList.add(btns[row + 1][col - 1]);
            }
        }else if(this.row == GRID_ROWS - 1){
            if(this.col == 0){
                btnList.add(btns[row][col + 1]);
                btnList.add(btns[row - 1][col]);
                btnList.add(btns[row - 1][col + 1]);
            }else if(this.col == GRID_COLS - 1){
                btnList.add(btns[row][col - 1]);
                btnList.add(btns[row - 1][col]);
                btnList.add(btns[row - 1][col - 1]);
            }else{
                btnList.add(btns[row][col + 1]);
                btnList.add(btns[row][col - 1]);
                btnList.add(btns[row - 1][col + 1]);
                btnList.add(btns[row - 1][col]);
                btnList.add(btns[row - 1][col - 1]);
            }
        }else{
            if(this.col == 0){
                btnList.add(btns[row - 1][col]);
                btnList.add(btns[row - 1][col + 1]);
                btnList.add(btns[row][col + 1]);
                btnList.add(btns[row + 1][col]);
                btnList.add(btns[row + 1][col + 1]);
            }else if(this.col == GRID_COLS - 1){
                btnList.add(btns[row - 1][col]);
                btnList.add(btns[row - 1][col - 1]);
                btnList.add(btns[row][col - 1]);
                btnList.add(btns[row + 1][col]);
                btnList.add(btns[row + 1][col - 1]);
            }else{
                btnList.add(btns[row - 1][col + 1]);
                btnList.add(btns[row - 1][col]);
                btnList.add(btns[row - 1][col - 1]);
                btnList.add(btns[row][col + 1]);
                btnList.add(btns[row][col - 1]);
                btnList.add(btns[row + 1][col + 1]);
                btnList.add(btns[row + 1][col]);
                btnList.add(btns[row + 1][col - 1]);
            }
        }
        return btnList;
    }

    public boolean isNeighborsSafe(List<GridButton> btnList){
        boolean neighborsSafe = false;
        for (GridButton btn : btnList) {
            if (btn.hasMine()) {
                neighborsSafe = false;
                break;
            } else {
                neighborsSafe = true;
            }
        }
        return neighborsSafe;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int rowSelected = -1;
        int colSelected = -1;
        GridButton source = (GridButton) e.getSource();
        boolean found = false;

        for (int i = 0; i < GRID_ROWS && !found; i++) {
            for (int j = 0; j < GRID_COLS && !found; j++) {
                if(source == gridButtons[i][j]){
                    rowSelected = i;
                    colSelected = j;
                    found = true;
                }
            }
        }

        GridButton btnSelected = gridButtons[rowSelected][colSelected];

        if(SwingUtilities.isLeftMouseButton(e)){
            if(btnSelected.hasMine()) {
                gameOver = true;
                JOptionPane.showMessageDialog(null, "Game Over!");
                setGameOverState();
            }else{
                btnSelected.autoReveal(MineSweeper.gridButtons);
                checkIfWon();
            }
        }else if(SwingUtilities.isRightMouseButton(e)){
            btnSelected.setFlag("".equals(btnSelected.getText()));
        }
    }

    private void addBtnListener(){ //TODO 1: move to addGridButton()
        for (int i = 0; i < GRID_ROWS; i++) {
            for (int j = 0; j < GRID_COLS; j++) {
                gridButtons[i][j].addMouseListener(this);
            }
        }
    }

    private void removeBtnListener(){
        for (int i = 0; i < GRID_ROWS; i++) {
            for (int j = 0; j < GRID_COLS; j++) {
                MouseListener[] listeners = gridButtons[i][j].getMouseListeners();
                for(MouseListener listener : listeners)
                    gridButtons[i][j].removeMouseListener(listener);
            }
        }
    }

    private void setGameOverState(){

            for(GridButton[] btns : gridButtons) {
                for (GridButton btn : btns) {
                    if(btn.hasMine()) {
                        btn.setFlag(true);
                        btn.setEnabled(false);
                    }
                    else {
                        btn.setAnchorNumbers(gridButtons, btn.getNeighbors(gridButtons));
                        btn.setRevealed(gameOver);
                    }
                }
            }
        removeBtnListener();
    }

    private void checkIfWon(){
        won = true;
        int counterRevealed = 0;
        int counterMines = 0;
        for(GridButton[] btns : gridButtons) {
            for (GridButton btn : btns) {
                if(btn.hasMine())
                    ++counterMines;
                else if(btn.isRevealed())
                    ++counterRevealed;
            }
        }
        if((counterRevealed + counterMines) == GRID_ROWS * GRID_COLS && !gameOver){
            JOptionPane.showMessageDialog(null, "YOU WIN!");
            removeBtnListener();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
