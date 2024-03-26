
package com.minesweeper;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;
import java.time.LocalDate;
import javafx.stage.Popup;
import javafx.collections.*;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.util.Scanner;

import javafx.util.Duration;
import javafx.animation.*;

import com.InvalidDescriptionException;

/**
 * JavaFX App
 */

public class App extends Application {

    private static Scene scene;
    private int number, level, supermine, time, id, initialTime;

    private boolean end = false, W = false;

    @Override
    public void start(Stage stage) throws IOException {

        int[] read = new int[60];
        Menu m = new Menu("Application");
        Menu M2 = new Menu("Details");

        // menu objects for application menu
        MenuItem m1 = new MenuItem("Create");
        MenuItem m2 = new MenuItem("Load");
        MenuItem m3 = new MenuItem("Start");
        MenuItem m4 = new MenuItem("Exit");

        MenuItem m5 = new MenuItem("Rounds");
        MenuItem m6 = new MenuItem("Solution");
        StackPane r = new StackPane();
        StackPane sp = new StackPane();

        File f = new File("/MediaLab/");
        m.getItems().add(m1);
        m.getItems().add(m2);
        m.getItems().add(m3);
        m.getItems().add(m4);
        M2.getItems().add(m5);
        M2.getItems().add(m6);

        // create a menubar
        MenuBar mb = new MenuBar();

        // add menu to menubar
        mb.getMenus().add(m);
        mb.getMenus().add(M2);

        VBox vb = new VBox(mb);
        VBox exbox = new VBox(10);

        scene = new Scene(vb, 720, 720);

        stage.setScene(scene);

        // "create" popup buttons
        Popup popupCreate = new Popup();
        Popup popupEx1 = new Popup();
        TextField Idtextfield = new TextField("Scenario ID");
        TextField Id2 = new TextField("Scenario ID");
        TextField Leveltextfield = new TextField("Level");
        TextField MinesNumber = new TextField("Number of Mines");
        TextField SuperMine = new TextField("Supermine");
        TextField Timetextfield = new TextField("Time");
        Label exitingString = new Label("Are you sure you want to exit?");
        Button b = new Button("Submit");
        Button c = new Button("Submit");
        Button yes = new Button("Yes");
        Button no = new Button("No");

        sp.getChildren().add(c);
        r.getChildren().add(b);

        VBox CrBox = new VBox(10);
        CrBox.getChildren().addAll(Idtextfield, Leveltextfield, MinesNumber, SuperMine, Timetextfield, c);
        CrBox.setStyle("-fx-background-color: pink; -fx-padding: 50px;");
        popupCreate.getContent().add(CrBox);
        popupCreate.getContent().add(sp);
        popupCreate.setAutoHide(true);

        EventHandler<ActionEvent> roun = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                Popup roundsPopup = new Popup();
                VBox rbox = new VBox(10);

                String S = "";

                try {
                    File myObj = new File(".\\MediaLab\\" + "games.txt"); // open the games.txt file to get the results
                                                                          // of the last 5
                    // games
                    Scanner myReader = new Scanner(myObj);

                    while (myReader.hasNextLine()) { // read all the lines
                        String data = myReader.nextLine();
                        S = S + data;
                    }
                    myReader.close();
                } catch (IOException exc) {

                    exc.printStackTrace();
                }

                TableView<data> D;
                D = getLast(S, 5);

                rbox.getChildren().addAll(D);
                rbox.setStyle("-fx-background-color: pink; -fx-padding: 50px;");
                roundsPopup.getContent().addAll(rbox);
                roundsPopup.setAutoHide(true);

                roundsPopup.show(stage);

            }
        };

        m5.setOnAction(roun);

        EventHandler<ActionEvent> create = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (!popupCreate.isShowing()) // if popup isn't showing, show the popup
                    popupCreate.show(stage);

            }
        };
        m1.setOnAction(create);

        exbox.getChildren().addAll(exitingString, yes, no);
        exbox.setStyle("-fx-background-color: pink; -fx-padding: 50px;");
        popupEx1.getContent().add(exbox);
        // ExitDialog.setHeaderText("Are you sure you want to exit?");

        EventHandler<ActionEvent> ex = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Platform.exit();

            }
        };
        yes.setOnAction(ex);

        EventHandler<ActionEvent> ex3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                popupEx1.hide();

            }
        };
        no.setOnAction(ex3);

        EventHandler<ActionEvent> ex1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (!popupEx1.isShowing()) // if popup isn't showing, show the popup
                {
                    popupEx1.show(stage);

                }

            }
        };

        m4.setOnAction(ex1);

        EventHandler<ActionEvent> cclose = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    File myObj = new File(".\\medialab\\" + Idtextfield.getText());
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
                    FileWriter w = new FileWriter(".\\MediaLab\\" + Idtextfield.getText());
                    w.write(Leveltextfield.getText());
                    w.write("\n");
                    w.write(MinesNumber.getText());
                    w.write("\n");
                    w.write(Timetextfield.getText());
                    w.write("\n");
                    w.write(SuperMine.getText());
                    w.write("\n");
                    w.close();
                } catch (IOException exc) {
                    System.out.println("An error occurred.");
                    exc.printStackTrace();
                }
                popupCreate.hide();

            }
        };
        c.setOnAction(cclose);

        Popup popupLoad = new Popup();
        VBox Lbox = new VBox(10);
        Lbox.getChildren().addAll(Id2, b);
        Lbox.setStyle("-fx-background-color: pink; -fx-padding: 13px;");
        popupLoad.getContent().add(Lbox);
        popupLoad.getContent().add(r);
        popupLoad.setAutoHide(true);
        EventHandler<ActionEvent> load = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (!popupLoad.isShowing()) // if popup isn't showing, show the popup
                    popupLoad.show(stage);

            }
        };
        m2.setOnAction(load);

        EventHandler<ActionEvent> bclose = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                popupLoad.hide();

                // ID.getText();

                Scanner reader;
                int i = 0;
                int k;
                String Number = new String();
                String Level = new String();
                String Supermine = new String();
                String Id = new String();
                String Time = new String();
                boolean empty = false;

                try {

                    System.out.println("ID" + Id2.getText());
                    reader = new Scanner(new File(".\\MediaLab\\" + Id2.getText()));

                    while (reader.hasNextLine()) {
                        // System.out.println(line);
                        // read next line

                        if (i == 0) {
                            Level = reader.nextLine();
                            if (Level == "") {
                                empty = true;
                            }
                        } else if (i == 1) {
                            Number = reader.nextLine();
                            if (Number == "") {
                                empty = true;
                            }
                        } else if (i == 3) {
                            Supermine = reader.nextLine();
                            if (Supermine == "") {
                                empty = true;
                            }
                        } else if (i == 2) {
                            Time = reader.nextLine();
                            if (Time == "") {
                                empty = true;
                            }
                        }
                        // else if (i==0){
                        // Id= reader.readLine();
                        // }

                        i++;

                    }

                    reader.close();
                } catch (IOException exc) {
                    try {
                        throw new InvalidDescriptionException();
                    } catch (InvalidDescriptionException invalidDescriptionException) {
                        Popup invalidid = new Popup();
                        Label indString = new Label("You have entered an invalid Id");
                        VBox indBox = new VBox(10);
                        indBox.getChildren().addAll(indString);
                        indBox.setStyle("-fx-background-color: pink; -fx-padding: 13px;");
                        invalidid.getContent().add(indBox);
                        invalidid.show(stage);
                       // invalidid.show(stage);
                        invalidid.setAutoHide(true);





                    }

                    exc.printStackTrace();
                }

                boolean fail;
                fail = false;
                boolean flag = false;
                boolean flag2 = false;
                if (!empty) {
                    number = Integer.parseInt(Number);
                    time = Integer.parseInt(Time);
                    initialTime = time;
                    id = Integer.valueOf(Id2.getText());
                    level = Integer.parseInt(Level);
                    supermine = Integer.parseInt(Supermine);
                    System.out.println(number);


                }
                try {
                    if (empty == true) {
                        empty = false;
                        throw new InvalidDescriptionException();

                    }

                } catch (InvalidDescriptionException exc) {
                    flag2 = true;
                    Popup IND = new Popup();
                    Label DString = new Label("You have entered at least an empty value");
                    Button closeDS = new Button("close");
                    VBox DSBox = new VBox(10);
                    System.out.println("hi");
                    DSBox.getChildren().addAll(DString, closeDS);
                    DSBox.setStyle("-fx-background-color: pink; -fx-padding: 13px;");
                    IND.getContent().add(DSBox);
                    IND.show(stage);
                    EventHandler<ActionEvent> closeDSS = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent eee) {
                            IND.hide();

                        }

                    };

                    closeDS.setOnAction(closeDSS);

                }
                if (!flag2) {
                    try {

                        if (level == 1) {
                            if (number < 9 || number > 11) {
                                fail = true;
                                System.out.println("number 1");
                            } else if (time > 180 || time < 120) {
                                fail = true;
                                System.out.println("time 1 = ");
                                System.out.println(time);

                            } else if (supermine != 0) {
                                fail = true;
                                System.out.println("supermine 1");
                            }
                        } else if (level == 2) {
                            if (number < 35 || number > 45) {
                                fail = true;
                                System.out.println("number 2");
                            } else if (time > 360 || time < 240) {
                                fail = true;
                                System.out.println("time 2");

                            } else if (supermine != 1) {
                                fail = true;
                                System.out.println("supermine 2 = " + supermine);

                            }

                        } else {
                            fail = true;
                            System.out.println("level 1");
                        }

                        if (fail == true) {
                            fail = false;
                            throw new InvalidValueException();
                        }
                    } catch (InvalidValueException exc) {
                        flag = true;
                        Popup InvalidDesr = new Popup();
                        Label DescrString = new Label("The input you entered is invalid.\n" +
                                "1st level: 8 < Number of mines < 12, 119 < time < 121, supermine = 0 \n" +
                                "2nd level: 34 < Number of mines < 46, 239 < time <361, sumermine must be equal to 0 or 1");
                        Button closeD = new Button("close");
                        VBox DBox = new VBox(10);
                        System.out.println("hi");
                        DBox.getChildren().addAll(DescrString, closeD);
                        DBox.setStyle("-fx-background-color: pink; -fx-padding: 50px;");
                        InvalidDesr.getContent().add(DBox);
                        InvalidDesr.show(stage);
                        EventHandler<ActionEvent> closeDH = new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent eee) {
                                InvalidDesr.hide();

                            }

                        };

                        closeD.setOnAction(closeDH);
                    }
                }
                if (!flag && !flag2) {

                    Popup SuccesfulLoad = new Popup();
                    Label LoadString = new Label("Game Loaded Succesfully");
                    Button CloseLoad = new Button("close");
                    VBox SLBox = new VBox(10);
                    SLBox.getChildren().addAll(LoadString, CloseLoad);
                    SLBox.setStyle("-fx-background-color: pink; -fx-padding: 13px;");
                    SuccesfulLoad.getContent().add(SLBox);
                    SuccesfulLoad.show(stage);
                    SuccesfulLoad.setAutoHide(true);
                    EventHandler<ActionEvent> closeload = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent eee) {
                            SuccesfulLoad.hide();

                        }

                    };
                    CloseLoad.setOnAction(closeload);
                }
                Label TimeLabel = new Label("Time:" + Time);
                Label NumberOfMinesLabel = new Label("Number of Bombs:" + Number);
                Label MarkedTilesLabel = new Label("Marked Tiles:" + 0);
                HBox LabelBox = new HBox(10);
                LabelBox.getChildren().addAll(TimeLabel, NumberOfMinesLabel, MarkedTilesLabel);
                LabelBox.setTranslateY(40);
                StackPane s1 = new StackPane();
                s1.getChildren().addAll(vb, LabelBox);

                Scene scene1 = new Scene(s1, 720, 800);
                stage.setScene(scene1);
                stage.show();

                // mine board = new mine(id, number, time, stage);

                // Pane p = board.Board();
                // p.setTranslateY(40);

                // StackPane s1 = new StackPane();
                // s1.getChildren().addAll(vb, p);

                // Scene scene1 = new Scene(s1, 720, 800);
                // stage.setScene(scene1);
                // stage.show();

            }
        };

        b.setOnAction(bclose);

        EventHandler<ActionEvent> start = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                time = initialTime;
                // int localtime = time;
                Label TimeLabel = new Label("Time:" + time);
                Label NumberOfMinesLabel = new Label("Number of Bombs:" + number);
                Label MarkedTilesLabel = new Label("Marked Tiles: " + 0);
                HBox LabelBox = new HBox(10);
                LabelBox.getChildren().addAll(TimeLabel, NumberOfMinesLabel, MarkedTilesLabel);
                LabelBox.setTranslateY(40);
                if (level == 1) {

                    end = false;
                    mine board = new mine(id, number, time, stage);

                    Pane p = board.Board();
                    p.setTranslateY(70);

                    EventHandler<ActionEvent> Solution = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                            board.ShowSolution();
                        }
                    };
                    m6.setOnAction(Solution);

                    StackPane s1 = new StackPane();
                    s1.getChildren().addAll(vb, LabelBox, p);

                    Scene scene1 = new Scene(s1, 720, 800);
                    stage.setScene(scene1);
                    stage.show();
                    int localtime = time;

                    Timeline TL2 = new Timeline();
                    Timeline TL = new Timeline();

                    KeyFrame kf2 = new KeyFrame(Duration.seconds(1),
                            ev -> {
                                end = board.gameend();

                                if (end == false) {

                                    TimeLabel.setText("Time " + board.gettime());

                                    if (board.gettime() == 0) {
                                        Popup endingPopup = new Popup();
                                        Label endingString = new Label("You ran out of time");
                                        VBox endingBox = new VBox(10);
                                        endingBox.getChildren().addAll(endingString);
                                        endingBox.setStyle("-fx-background-color: pink; -fx-padding: 13px;");
                                        endingPopup.getContent().add(endingBox);
                                        endingPopup.show(stage);
                                        endingPopup.setAutoHide(true);

                                    }

                                }
                                // else board.writetofile();

                            });

                    // Timeline TL2 = new Timeline(new KeyFrame(Duration.seconds(0.01), ev -> {
                    // W = board.victory();
                    // if (W) {
                    // TL.stop();
                    // TL2.stop();

                    // }
                    // }));
                    // Timeline TL2 = new Timeline();
                    KeyFrame kf = new KeyFrame(Duration.seconds(0.01),
                            ev -> {
                                MarkedTilesLabel.setText("Marked Tiles = " + board.getmarked());
                                W = board.victory();
                                end = board.gameend();
                                if (W || end) {
                                    TL.stop();
                                    TL2.stop();
                                    // if(end) board.writetofile();

                                }
                            });

                    TL.getKeyFrames().addAll(kf2);
                    TL2.getKeyFrames().addAll(kf);

                    TL.setCycleCount(time);
                    TL.play();
                    TL2.setCycleCount(time * 100);
                    TL2.play();
                    end = false;

                }

                else if (level == 2) {
                    // end = false;
                    mineLevel2 board = new mineLevel2(id, number, time, stage, supermine);

                    Pane p = board.Board();
                    p.setTranslateY(70);

                    StackPane s1 = new StackPane();
                    s1.getChildren().addAll(vb, LabelBox, p);

                    Scene scene1 = new Scene(s1, 720, 800);
                    stage.setScene(scene1);
                    stage.show();

                    Timeline TL2 = new Timeline();
                    Timeline TL = new Timeline();
                    EventHandler<ActionEvent> Solution = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                            board.ShowSolution();
                        }
                    };
                    m6.setOnAction(Solution);

                    KeyFrame kf2 = new KeyFrame(Duration.seconds(1),
                            ev -> {
                                end = board.gameend();
                                if (end == false) {

                                    TimeLabel.setText("Time " + board.gettime());

                                    if (board.gettime() == 0) {

                                        board.ShowSolution();
                                        Popup endingPopup = new Popup();
                                        Label endString = new Label("You ran out of time");
                                        VBox endBox = new VBox(10);
                                        endBox.getChildren().addAll(endString);
                                        endBox.setStyle("-fx-background-color: pink; -fx-padding: 13px;");
                                        endingPopup.getContent().add(endBox);
                                        endingPopup.show(stage);
                                        endingPopup.setAutoHide(true);
                                        board.getmarked();

                                    }

                                }

                            });

                    KeyFrame kf = new KeyFrame(Duration.seconds(0.01),
                            ev -> {
                                end = board.gameend();
                                W = board.victory();
                                MarkedTilesLabel.setText("Marked Tiles = " + board.getmarked());
                                // if (end) board.writetofile();

                                if (W || end) {
                                    TL.stop();
                                    TL2.stop();
                                }
                            });
                    TL.getKeyFrames().addAll(kf2);
                    TL2.getKeyFrames().addAll(kf);

                    TL.setCycleCount(time);
                    TL.play();
                    TL2.setCycleCount(time * 100);
                    TL2.play();
                    end = false;

                }

            }

        };

        m3.setOnAction(start);

        stage.show();

    }

    public static TableView<data> getLast(String s, int t) {

        TableView<data> table = new TableView<data>();

        TableColumn<data, String> column1 = new TableColumn<>("Bombs");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("Bombs"));

        TableColumn<data, String> column2 = new TableColumn<>("Attempts");

        column2.setCellValueFactory(
                new PropertyValueFactory<>("Attempts"));

        TableColumn<data, String> column3 = new TableColumn<>("Time");

        column3.setCellValueFactory(
                new PropertyValueFactory<>("Time"));

        TableColumn<data, String> column4 = new TableColumn<>("Winner");

        column4.setCellValueFactory(
                new PropertyValueFactory<>("Winner"));

        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);
        table.getColumns().add(column4);

        // Vector to store individual strings.
        // Save all strings in the vector.
        String[] v = s.split("-");

        String result = new String("");

        if (v.length == 0) {
            return table;
        }

        // If the string has t lines
        if (v.length >= t) {
            for (int i = v.length - t; i < v.length; i++) {
                v[i] = v[i] + "\n";
                result = result + v[i];
            }
        } else {
            for (int i = 0; i < v.length; i++) {
                v[i] = v[i] + "\n";
                result = result + v[i];
            }
        }

        String[] split = result.split("\n");

        for (int i = 0; i < split.length; i++) {
            String[] attr = split[i].split(",");
            data e = new data(attr);
            table.getItems().add(e);
        }

        return table;
    }

    public static void main(String[] args) {
        launch();
    }

}
