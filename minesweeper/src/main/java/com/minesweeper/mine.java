package com.minesweeper;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.*;
import java.io.*;
import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.scene.input.KeyCode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.time.LocalDate;
import javafx.stage.Popup;
import javafx.collections.*;
import javafx.scene.layout.StackPane;

import java.io.*;
import javafx.scene.input.MouseEvent;
import javafx.beans.property.DoubleProperty;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;
import javafx.scene.Group;
import javafx.animation.*;
import javafx.util.Duration;

import java.util.Scanner;

public class mine {
    private int tile_x;
    private int tile_y;
    private int time;
    private int noOfBombs;
    private int ID;
    private int tile_size;
    private int width, length;
    public Tile[][] grid;
    private Pane root = new Pane();
    private Stage stage;
    private int RemainingTiles = 81;
    private int NumberofRedTiles;
    private boolean end = false;
    private boolean W = false;
    private Timeline TL = new Timeline();
    private Timeline TL3 = new Timeline();
    private int attempts = 0;
    private int initialTime;
    private boolean computer=true;


    public mine(int Id, int number, int T, Stage stage) {
        // constructor

        this.end = false;
        this.W = false;
        tile_x = 9;
        tile_y = 9;
        grid = new Tile[tile_x][tile_y];
        width = length = 720;
        tile_size = length / 9;
        ID = Id;
        noOfBombs = number;
        time = T;
        initialTime=T;
        this.stage=stage;
        RemainingTiles=81-noOfBombs;
        NumberofRedTiles = 0;
        KeyFrame kf2 = new KeyFrame(Duration.seconds(1),
        ev -> {
            end = gameend();
            if (end == false) {

                time--;
                if (time == 0) {
                    writetofile(false);
                }
                
                

            }

        });
       
        
        TL.getKeyFrames().addAll(kf2);
        TL.setCycleCount(time);
        TL.play();

    }


   
    public Pane Board() {

        


        int[] b_x = new int[noOfBombs];
        int[] b_y = new int[noOfBombs];
        ArrayList<Integer> b = new ArrayList<Integer>();
        boolean bo = false;

        // public Board (){

        root.setPrefSize(length, length);

        // setting bombs
        for (int i = 0; i < 81; i++) {
            b.add(i);
        }

        Collections.shuffle(b);

        for (int i = 0; i < noOfBombs; i++) {
            int l = b.get(i);
            b_x[i] = l / 9;
            b_y[i] = l % 9;
        }

        for (int y = 0; y < tile_y; y++) {
            for (int x = 0; x < tile_x; x++) {
                for (int i = 0; i < noOfBombs; i++) {
                    if (b_x[i] == x && b_y[i] == y) {
                        bo = true;
                        System.out.println(b_x[i] + "and" + b_y[i]);
                        break;
                    } else {
                        bo = false;
                    }
                }

                Tile tile = new Tile(x, y, bo);
                grid[x][y] = tile;
                bo = false;
            }
        }

        for (int y = 0; y < tile_y; y++) {
            for (int x = 0; x < tile_x; x++) {
                Tile tile = grid[x][y];

                tile.setbombs();

                root.getChildren().add(tile);

            }

        }
        try {
            File myObj = new File(".\\medialab\\" + "mine.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());

            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException exc) {
            System.out.println("An error occurred.");
            exc.printStackTrace();
        }
        try {
            FileWriter w = new FileWriter(".\\MediaLab\\" + "mine.txt");

            for (int i = 0; i<noOfBombs; i++) {
                w.write(String.valueOf(b_x[i]));
                w.write(", ");
                w.write(String.valueOf(b_y[i]));
                w.write(", ");
                w.write("0\n");
            }
          
            w.close();
        } catch (IOException exc) {
            System.out.println("An error occurred.");
            exc.printStackTrace();
        }

        return root;

    }


    private List<Tile> getNeighbours(Tile tile) {
        List<Tile> neighbours = new ArrayList<>();

        int[] points = new int[] {
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1

        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (newX >= 0 && newX < tile_x && newY >= 0 && newY < tile_y) {
                neighbours.add(grid[newX][newY]);

            }

        }

        return neighbours;
    }

    public boolean victory() {
           // computer=false;
           if(W) computer = false;
            return W;
    }

    public boolean gameend() {

        if (time == 0) {
            for (int x = 0; x<tile_x; x++) {
                for(int y = 0; y<tile_y; y++) {
                    grid[x][y].OpenBombs();
                }
            }
        }

        return end;
    }


    public int gettime() {
        return time;
    }

    public int getmarked() {
        return (NumberofRedTiles);
    }

    public int getAttempts() {
        return(attempts);
    }
    public void ShowSolution () {
        end = true;
        for (int x =0; x<9; x++){
            for (int y = 0; y<9; y++) {
                grid[x][y].OpenBombs();
            }
            
        }
    }

    private class Tile extends StackPane {
        private int x, y;
        private boolean bomb;
        private boolean isOpen = false;
        private boolean red = false;

        private Rectangle border = new Rectangle(tile_size - 2, tile_size - 2);
        private Text t = new Text();

/**
 * H methodos tile einai auth pou ftiaxnei kathe tile (exei invisible text to opoio mporei na einai
 * eite keno, eite arithmos geitonikwn narkwn, eite X gia narkh) tou grid tou narkaleuth kai otan 
 * kanoume aristero click me to pontiki apokalyptei to periexomeno tou tile mesw ths open. 
 * Me deksi click markarei pithanh narkh
 * @param x h grammh tou tile
 * @param y h sthlh tou tile
 * @param bomb true ean yparxei narkh, false ean oxi
 */
        public Tile(int x, int y, boolean bomb) {
            this.x = x;
            this.y = y;
            this.bomb = bomb;

            border.setStroke(Color.LIGHTGRAY);

            t.setFont(Font.font(18));
            t.setText(bomb ? "X" : "");
            t.setVisible(false);

            getChildren().addAll(border, t);

            setTranslateX(y * tile_size);
            setTranslateY(x * tile_size);

            // MouseEvent event;
            // MouseButton button =  event.getButton();
            //  if (button == MouseButton.SECONDARY) {
            //      setOnMouseClicked(e -> RightClick());
            //  }
            //  else {
            //    setOnMouseClicked(e -> open()); }

            setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    MouseButton button = event.getButton();
                    if(!end) {
                    if(button==MouseButton.PRIMARY ){  
                        open();
                        if (!bomb) attempts++;
                        
                    }else if(button==MouseButton.SECONDARY  ){     
                        RightClick();
                    }
                }
                }
            });

          


            


        }

        
        private void setbombs() {
            if(bomb) return;
            List<Tile> list = getNeighbours(this);
            int bombs = 0;

            for(int i=0; i < list.size(); i++) {
                if(list.get(i).bomb){
                    bombs++;
                }
            }

            if(bombs>0){
                t.setText(Integer.toString(bombs));
            }
        }

        private void open() {
            if (isOpen) {
                return;
            }
            System.out.println("Hey this is " + t.getText());
            if (bomb) {
                end = true;
                
                Popup GameOver = new Popup();
                writetofile(false);
                VBox GObox = new VBox(10);
                Button Close = new Button("Close");
                Label GOString = new Label("Game Over! \n Click on close to show the solution ");
                GObox.getChildren().addAll(GOString, Close);
                GObox.setStyle("-fx-background-color: pink; -fx-padding: 50px;");
                GameOver.getContent().add(GObox);
                // EventHandler<ActionEvent> gameoverevent = new EventHandler<ActionEvent>() {
                //     public void handle(ActionEvent e) {
                //         for (int x = 0; x<noOfBombs; x++ ){
                //             for (int y = 0; y<noOfBombs; y++) {
                //                 grid[x][y].open();

                //             }
                //         }
                //         GameOver.hide();

        
                //     }
                // };
                GameOver.show(stage);

                for (int x = 0; x< tile_x; x++ ){
                    for (int y = 0; y< tile_y; y++) {
                        grid[x][y].OpenBombs();

                    }
                }
                


                EventHandler<ActionEvent> closeGameOver = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        GameOver.hide();
        
                    }
                };
                 Close.setOnAction(closeGameOver);

                



                System.out.println("game over");

            }
            isOpen = true;

            border.setFill(Color.WHITE);
            t.setFill(Color.BLUE);
            t.setVisible(true);
            RemainingTiles --;
            // if(t.isVisible()){
            //     System.out.println("hiiiiii");
            // }
            RemainingTiles --;
            if (t.getText().isEmpty()) {
              //  getNeighbours(this).count;
                getNeighbours(this).forEach(Tile::open);
               // RemainingTiles -- ;
            }
           // boolean W = false;
            W = wincheck();
            if (W) {
            writetofile(true);
            Popup Win = new Popup();
            VBox winBox = new VBox(10);
            Label WString = new Label("You won!");
            Button Close2 = new Button("Close");
            winBox.getChildren().addAll(WString, Close2);
            winBox.setStyle("-fx-background-color: pink; -fx-padding: 50px;");
            Win.getContent().add(winBox);
            Win.show(stage);

            EventHandler<ActionEvent> closeWin= new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    Win.hide();
    
                }
            };
            Close2.setOnAction(closeWin);


        }


        }


        /**
         * h wincheck pernaei olo to board gia na dei ean ola ta tiles pou 
         * DEN exoun narkes einai anoixta. Se periptwsh pou brei estw kleisto tile  
         * pou den periexei bomba epistrefei false
         * Otan exei oloklhrwsei to  perasma olou tou board einai sigouro pws ola ta kleista
         * tiles einai auta pou periexoun bombes opote epistrefei true
         * to opoio isodynamei me th nikh tou xrhsth
         * @return
         */
        public boolean wincheck() {
            for (int x=0; x<tile_x; x++) {
                for (int y = 0; y<tile_y; y++) {
                    if(!grid[x][y].isOpen && !grid[x][y].bomb) {
                        return false;
                    }
                }
            }
            
            return true;
        }


        /**
         * H methodos auth xrhsimopoieitai gia na anoiksei ola ta tiles tou board akoma kai tis bombes
         * xwris na bgalei mhnyma sfalmatos (px sto solutions)
         */
        public void OpenBombs() {
            // if (isOpen) {
            //     return;
            // }
           // isOpen = true;
            border.setFill(Color.WHITE);
            t.setFill(Color.BLUE);
            t.setVisible(true);
            


        }

        private void RightClick () {
            if (isOpen) {
                return;
            }
            else if(!red && NumberofRedTiles < noOfBombs) {
                NumberofRedTiles++;
                border.setFill(Color.RED );
                red = true;
                return;
            }
            else if (red) {
                NumberofRedTiles--;
                border.setFill(Color.BLACK);
                red=false;
                return;
            }
            else return;
        }

    }
    public void writetofile(boolean win) {
        
        
        try {
            File myObj = new File(".\\medialab\\" + "games.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());

            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException exc) {
            System.out.println("An error occurred.");
            exc.printStackTrace();
        }
        try {
            
            FileWriter w = new FileWriter(".\\MediaLab\\" + "games.txt", true);
            w.write(String.valueOf(noOfBombs));
            w.write(",");
            w.write(String.valueOf(attempts));
            w.write(",");
            w.write(String.valueOf(initialTime));
            w.write(",");
            if(win) {
                w.write("user");}
            else w.write("computer");
            w.write("-\n");
            



            
          
            w.close();
        } catch (IOException exc) {
            System.out.println("An error occurred.");
            exc.printStackTrace();
        }
    }
}
