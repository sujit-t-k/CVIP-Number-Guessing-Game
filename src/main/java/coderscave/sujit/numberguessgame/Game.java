package coderscave.sujit.numberguessgame;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
    private Pane paneBase, paneDetails, paneGameArena, paneHowToPlay;
    private int intSystemGuess, intGuessLeft, intTotalRoundsPlayed = 0, intRoundsWon = 0;
    private boolean blnListenToWindowResize = false, blnMouseActionAllowed = true;
    private boolean[] blnGuessed;
    private Line lineTop, lineBottom;
    private Text txtGameTitle, txtGuessDiff, txtGuessIs, txtGuessLeft, txtGuessLeftIs, txtPlayAgain, txtRoundsWon, txtRounds, txtHowToPlay, txtGameWorking, txtGotIt;
    private Rectangle rectPlayAgain, rectGuess, rectRoundsWon, rectHowToPlay, rectGotIt;
    private Label[] lblNumberTile;

    @Override
    public void start(Stage stage) {
        this.paneBase = new Pane();
        this.paneBase.setBackground(new Background(new BackgroundFill(Color.web(Values.STR_CLR_BG), CornerRadii.EMPTY, Insets.EMPTY)));
        this.paneBase.setPrefSize(1000D,620D);
        this.buildUI();
        Scene scene = new Scene(this.paneBase);
        stage.setTitle("Number Guess Game");
        stage.setScene(scene);
        stage.setMinWidth(Values.DBL_MIN_WINDOW_WIDTH+(2*Values.DBL_BUFFER_GAP));
        stage.setMinHeight(Values.DBL_MIN_WINDOW_HEIGHT+60.0D);
        stage.show();
        scene.widthProperty().addListener((ol,ov,nv) -> {
                this.rescaleUIByWidth(nv.doubleValue());
        });
        scene.heightProperty().addListener((ol,ov,nv) -> {
            this.rescaleUIByHeight(nv.doubleValue());
        });
        this.blnListenToWindowResize = true;
        this.setupParameters();
    }

    private void rescaleUIByWidth(final double DBL_WIDTH) {
        if (blnListenToWindowResize && DBL_WIDTH > Values.DBL_MIN_WINDOW_WIDTH) {
            final double DBL_NEW_BUFFER_WIDTH = (Values.DBL_BUFFER_GAP*(DBL_WIDTH/1000.0D));
            this.lineTop.setEndX(DBL_WIDTH - DBL_NEW_BUFFER_WIDTH);
            this.lineBottom.setEndX(DBL_WIDTH - DBL_NEW_BUFFER_WIDTH);
            this.txtGameTitle.setWrappingWidth(DBL_WIDTH);
            this.paneGameArena.setPrefWidth(DBL_WIDTH*0.75D);
            this.paneDetails.setLayoutX(2*DBL_NEW_BUFFER_WIDTH + ((DBL_WIDTH)*0.75D));
            this.paneDetails.setPrefWidth(DBL_WIDTH*0.19D);
            this.txtGuessIs.setWrappingWidth(DBL_WIDTH*0.19D);
            this.txtGuessIs.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 16D*((DBL_WIDTH*0.19D)/190.0D)));
            this.txtGuessDiff.setWrappingWidth(DBL_WIDTH*0.19D);
            this.txtGuessDiff.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 30D*((DBL_WIDTH*0.19D)/190.0D)));
            this.txtGuessLeftIs.setWrappingWidth(DBL_WIDTH*0.19D);
            this.txtGuessLeftIs.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 16D*((DBL_WIDTH*0.19D)/190.0D)));
            this.txtGuessLeft.setWrappingWidth(DBL_WIDTH*0.19D);
            this.txtGuessLeft.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 40D*((DBL_WIDTH*0.19D)/190.0D)));
            this.rectPlayAgain.setWidth(120.0D*(DBL_WIDTH*0.19D)/190.0D);
            this.rectPlayAgain.setLayoutX(35.0D*(DBL_WIDTH*0.19D)/190.0D);
            this.txtPlayAgain.setWrappingWidth(DBL_WIDTH*0.19D);
            this.rectHowToPlay.setWidth(120.0D*(DBL_WIDTH*0.19D)/190.0D);
            this.rectHowToPlay.setLayoutX(35.0D*(DBL_WIDTH*0.19D)/190.0D);
            this.txtHowToPlay.setWrappingWidth(DBL_WIDTH*0.19D);
            this.txtRounds.setWrappingWidth(DBL_WIDTH*0.19D);
            this.txtRoundsWon.setWrappingWidth(DBL_WIDTH*0.19D);
            this.txtRounds.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 16D*((DBL_WIDTH*0.19D)/190.0D)));
            this.txtRoundsWon.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 24D*((DBL_WIDTH*0.19D)/190.0D)));
            this.txtPlayAgain.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 16*((DBL_WIDTH*0.19D)/190.0D)));
            this.txtHowToPlay.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 16*((DBL_WIDTH*0.19D)/190.0D)));
            this.rectGuess.setLayoutX(27.0D*(DBL_WIDTH*0.19D)/190.0D);
            this.rectGuess.setWidth(140.0D*(DBL_WIDTH*0.19D)/190.0D);
            this.rectRoundsWon.setLayoutX(27.0D*(DBL_WIDTH*0.19D)/190.0D);
            this.rectRoundsWon.setWidth(140.0D*(DBL_WIDTH*0.19D)/190.0D);
            this.paneHowToPlay.setPrefWidth(960.0D*(DBL_WIDTH/1000.0D));
            this.txtGameWorking.setWrappingWidth(940.0D*(DBL_WIDTH/1000.0D));
            final double DBL_FONT_RESIZE_SCALE = Math.min((DBL_WIDTH/1000.0D),this.paneBase.getHeight()/620.0D);
            this.txtGameWorking.setFont(Font.font("Calibri", FontWeight.NORMAL, FontPosture.REGULAR, 17.5D*DBL_FONT_RESIZE_SCALE));
            this.txtGotIt.setLayoutX(760.0D*(DBL_WIDTH/1000.0D));
            this.txtGotIt.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 40*DBL_FONT_RESIZE_SCALE));
            this.rectGotIt.setLayoutX(750.0D*(DBL_WIDTH/1000.0D));
            this.rectGotIt.setWidth(170.0D*(DBL_WIDTH/1000.0D));
            final double DBL_GAP = DBL_WIDTH*0.01D, DBL_TILE_WIDTH = ((DBL_WIDTH*0.75D) - (11*DBL_GAP))/10.0D;
            for(int i = 0; i < 100; i++) {
                this.lblNumberTile[i].setPrefWidth(DBL_TILE_WIDTH);
                this.lblNumberTile[i].setLayoutX((i%10)*(DBL_TILE_WIDTH)+((i%10)+1)*DBL_GAP);
                this.lblNumberTile[i].setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 20.0D*(DBL_WIDTH/1000.0D)));
            }
        }
    }
    private void rescaleUIByHeight(final double DBL_HEIGHT) {
        if (blnListenToWindowResize && DBL_HEIGHT > Values.DBL_MIN_WINDOW_HEIGHT) {
            final double DBL_NEW_PANE_GAME_ARENA_HEIGHT = (DBL_HEIGHT-100.0D)*0.9615D;
            this.paneGameArena.setPrefHeight(DBL_NEW_PANE_GAME_ARENA_HEIGHT);
            this.paneDetails.setPrefHeight(DBL_NEW_PANE_GAME_ARENA_HEIGHT);
            final double DBL_GAP = DBL_HEIGHT/62.0D, DBL_TILE_HEIGHT = (DBL_NEW_PANE_GAME_ARENA_HEIGHT - (11*DBL_GAP))/10.0D;
            for(int i = 0; i < 100; i++) {
                this.lblNumberTile[i].setPrefHeight(DBL_TILE_HEIGHT);
                this.lblNumberTile[i].setLayoutY((i/10)*(DBL_TILE_HEIGHT)+((i/10)+1)*DBL_GAP);
            }
            this.txtGuessLeftIs.setLayoutY(70.0D*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.txtGuessLeft.setLayoutY(115.0D*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.txtGuessIs.setLayoutY(190.0D*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.txtGuessDiff.setLayoutY(240.0D*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.rectPlayAgain.setHeight(50.0D*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.rectPlayAgain.setLayoutY(410*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.txtPlayAgain.setLayoutY(440*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.rectHowToPlay.setHeight(50.0D*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.rectHowToPlay.setLayoutY(410*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.txtHowToPlay.setLayoutY(440*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.txtRounds.setLayoutY(320*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.txtRoundsWon.setLayoutY(360*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.rectRoundsWon.setLayoutY(298.0D*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.rectRoundsWon.setHeight(76.0D*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.rectGuess.setLayoutY(52.0D*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.rectGuess.setHeight(76.0D*DBL_NEW_PANE_GAME_ARENA_HEIGHT/500.0D);
            this.paneHowToPlay.setPrefHeight(510.0D*(DBL_NEW_PANE_GAME_ARENA_HEIGHT/510.0D));
            final double DBL_FONT_RESIZE_SCALE = Math.min((DBL_NEW_PANE_GAME_ARENA_HEIGHT/510.0D),this.paneBase.getWidth()/1000.0D);
            this.txtGameWorking.setFont(Font.font("Calibri", FontWeight.NORMAL, FontPosture.REGULAR, 17.5D*DBL_FONT_RESIZE_SCALE));
            this.txtGotIt.setLayoutY(484.0D*(DBL_NEW_PANE_GAME_ARENA_HEIGHT/510.0D));
            this.txtGotIt.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 40*DBL_FONT_RESIZE_SCALE));
            this.rectGotIt.setLayoutY(436.0D*(DBL_NEW_PANE_GAME_ARENA_HEIGHT/510.0D));
            this.rectGotIt.setHeight(67.0D*(DBL_NEW_PANE_GAME_ARENA_HEIGHT/510.0D));
        }
    }

    private void setupParameters() {
        this.blnGuessed = new boolean[100];
        this.intGuessLeft = 6;
        this.txtGuessLeft.setText(this.intGuessLeft+"");
        this.txtGuessIs.setText("Guess the number and click on it!");
        this.txtGuessDiff.setText("");
        this.intSystemGuess = 1 + (int)(Math.random()*100);
        this.blnMouseActionAllowed = true;
    }

    private void validateGuess(final int INT_TRIGGERED_NUMBER) {
        if(!this.txtGuessIs.getText().equals("Your GUESS is")) this.txtGuessIs.setText("Your GUESS is");
        if(INT_TRIGGERED_NUMBER == this.intSystemGuess) {
            this.blnMouseActionAllowed = false;
            this.txtGuessDiff.setText("CORRECT!!!");
            Timeline tl_fade = new Timeline();
            for(int i = 0; i < 100; i++) {
                if(i+1 != this.intSystemGuess) tl_fade.getKeyFrames().add(new KeyFrame(Duration.millis(800.0D), new KeyValue(this.lblNumberTile[i].opacityProperty(),0.4D)));
            }
            this.lblNumberTile[INT_TRIGGERED_NUMBER-1].setBackground(new Background(new BackgroundFill(Color.web(Values.STR_CORRECT_TILE_BG), CornerRadii.EMPTY, Insets.EMPTY)));
            tl_fade.playFromStart();
            tl_fade.setOnFinished(e->{
                this.txtRoundsWon.setText(++this.intRoundsWon + " / " + ++this.intTotalRoundsPlayed);
                this.rectHowToPlay.setVisible(false);
                this.txtHowToPlay.setVisible(false);
                this.rectPlayAgain.setVisible(true);
                this.txtPlayAgain.setVisible(true);
            });
        } else if (INT_TRIGGERED_NUMBER < this.intSystemGuess) {
            this.lblNumberTile[INT_TRIGGERED_NUMBER-1].setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, this.lblNumberTile[this.intSystemGuess-1].getFont().getSize()));
            this.txtGuessDiff.setText(((this.intSystemGuess - INT_TRIGGERED_NUMBER) < 16) ? "LOW" : "TOO LOW");
            for(int i = 0; i < INT_TRIGGERED_NUMBER; i++) {
                this.blnGuessed[i] = true;
                this.lblNumberTile[i].setBackground(new Background(new BackgroundFill(Color.web(Values.STR_WRONG_TILE_BG), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        } else {
            this.lblNumberTile[INT_TRIGGERED_NUMBER-1].setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, this.lblNumberTile[this.intSystemGuess-1].getFont().getSize()));
            this.txtGuessDiff.setText(((INT_TRIGGERED_NUMBER - this.intSystemGuess) < 16) ? "HIGH" : "TOO HIGH");
            for(int i = INT_TRIGGERED_NUMBER-1; i < 100; i++) {
                this.blnGuessed[i] = true;
                this.lblNumberTile[i].setBackground(new Background(new BackgroundFill(Color.web(Values.STR_WRONG_TILE_BG), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        if(--this.intGuessLeft == 0 && this.blnMouseActionAllowed) {
            this.blnMouseActionAllowed = false;
            Timeline tl_fade = new Timeline();
            this.lblNumberTile[INT_TRIGGERED_NUMBER-1].setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, this.lblNumberTile[INT_TRIGGERED_NUMBER-1].getFont().getSize()/1.3D));
            for(int i = 0; i < 100; i++) {
                if(i+1 != this.intSystemGuess) tl_fade.getKeyFrames().add(new KeyFrame(Duration.millis(800.0D), new KeyValue(this.lblNumberTile[i].opacityProperty(),0.4D)));
            }
            this.lblNumberTile[this.intSystemGuess-1].setBackground(new Background(new BackgroundFill(Color.web(Values.STR_CORRECT_TILE_REVEAL), CornerRadii.EMPTY, Insets.EMPTY)));
            this.lblNumberTile[this.intSystemGuess-1].setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, this.lblNumberTile[this.intSystemGuess-1].getFont().getSize()*1.3D));
            tl_fade.playFromStart();
            tl_fade.setOnFinished(e->{
                this.txtRoundsWon.setText(this.intRoundsWon + " / " + ++this.intTotalRoundsPlayed);
                this.rectHowToPlay.setVisible(false);
                this.txtHowToPlay.setVisible(false);
                this.rectPlayAgain.setVisible(true);
                this.txtPlayAgain.setVisible(true);
            });
        }
        this.txtGuessLeft.setText(this.intGuessLeft+"");
    }

    private void buildUI() {
        this.paneHowToPlay = new Pane();
        this.paneHowToPlay.setPrefSize(960.0D,510.0D);
        this.paneHowToPlay.setLayoutX((1000.0D-960.0D)/2.0D);
        this.paneHowToPlay.setLayoutY(621.0D+510.0D);
        this.paneHowToPlay.setBackground(new Background(new BackgroundFill(Color.web(Values.STR_GAME_ARENA_BG), CornerRadii.EMPTY, Insets.EMPTY)));
        this.txtGameWorking = new Text("HOW TO PLAY?\n" +
                                       "1.) A random number within 1 to 100 (inclusive) will be taken and considered as the CORRECT number by the system which has to be found by the player.\n" +
                                       "2.) The player has to start the game by guessing a number and click on the appropriate labelled button.\n" +
                                       "3.) If the player guesses the number correctly, then \"Your Guess is correct\" will be displayed and the score gets updated.\n" +
                                       "4.) If the player's guess is lesser than the CORRECT number, then \"Your guess is low/too low\" will be displayed.\n" +
                                       "5.) If the player's guess is greater than the CORRECT number, then \"Your guess is high/too high\" will be displayed.\n" +
                                       "6.) Once the player guesses and select a number, number of guesses available will be deducted by one for each guess made. Additionally, the player will be given a clue after each guess by highlighting a portion(either LEFT or RIGHT from the selected one) by red in which the CORRECT answer does not exist.\n" +
                                       "7.) The player has to consider the clues and should guess the CORRECT number accordingly within given number of guesses.\n" +
                                       "8.) A session ends either if the player guesses the CORRECT number or the player ran out of given number of guesses available.\n" +
                                       "9.) After the end of each session, score will be updated accordingly and the player can click \"PLAY AGAIN\" to play another session.\n" +
                                       "10.) The score is accumulative and will be carried on to further sessions.");
        this.txtGameWorking.setTextAlignment(TextAlignment.LEFT);
        this.txtGameWorking.setLineSpacing(12.0D);
        this.txtGameWorking.setFill(Color.WHITE);
        this.txtGameWorking.setWrappingWidth(940.0D);
        this.txtGameWorking.setFont(Font.font("Calibri", FontWeight.NORMAL, FontPosture.REGULAR, 17.5D));
        this.txtGameWorking.setLayoutX(10.0D);
        this.txtGameWorking.setLayoutY(20.0D);
        this.txtGotIt = new Text("GOT IT!");
        this.txtGotIt.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 40));
        this.txtGotIt.setTextAlignment(TextAlignment.CENTER);
        this.txtGotIt.setFill(Color.web(Values.STR_GAME_ARENA_BG));
        this.txtGotIt.setMouseTransparent(true);
        this.txtGotIt.setLayoutX(760.0D);
        this.txtGotIt.setLayoutY(484.0D);
        this.rectGotIt = new Rectangle();
        this.rectGotIt.setLayoutX(750.0D);
        this.rectGotIt.setLayoutY(436.0D);
        this.rectGotIt.setWidth(170.0D);
        this.rectGotIt.setHeight(67.0D);
        this.rectGotIt.setFill(Color.web(Values.STR_BTN_PLAY_AGAIN_BG));
        this.rectGotIt.setArcHeight(20.0D);
        this.rectGotIt.setArcWidth(20.0D);
        this.rectGotIt.setOnMouseEntered(e->{
            this.rectGotIt.setFill(Color.web(Values.STR_BTN_PLAY_AGAIN_HOVERED_BG));
        });
        this.rectGotIt.setOnMouseExited(e->{
            this.rectGotIt.setFill(Color.web(Values.STR_BTN_PLAY_AGAIN_BG));
        });
        this.rectGotIt.setOnMouseClicked(e->{
            Timeline tl_show = new Timeline(new KeyFrame(Duration.millis(700.0D), new KeyValue(this.paneGameArena.opacityProperty(),0.4D)),
                    new KeyFrame(Duration.millis(700.0D), new KeyValue(this.paneDetails.opacityProperty(),0.4D)),
                    new KeyFrame(Duration.millis(1400.0D), new KeyValue(this.paneGameArena.opacityProperty(),1.0D)),
                    new KeyFrame(Duration.millis(1400.0D), new KeyValue(this.paneDetails.opacityProperty(),1.0D)),
                    new KeyFrame(Duration.millis(800.0D), new KeyValue(this.paneHowToPlay.layoutYProperty(),this.paneBase.getHeight()+this.paneHowToPlay.getHeight())));
            tl_show.playFromStart();
        });
        this.paneHowToPlay.getChildren().addAll(this.rectGotIt, this.txtGameWorking, this.txtGotIt);
        this.lineTop = new Line(Values.DBL_BUFFER_GAP,20.0D,1000.0D - Values.DBL_BUFFER_GAP,20.0D);
        this.lineTop.setStroke(Color.web(Values.STR_CLR_CONTRAST_BG));
        this.lineBottom = new Line(Values.DBL_BUFFER_GAP,80.0D,1000.0D - Values.DBL_BUFFER_GAP,80.0D);
        this.lineBottom.setStroke(Color.web(Values.STR_CLR_CONTRAST_BG));
        this.txtGameTitle = new Text("NUMBER GUESS GAME");
        this.txtGameTitle.setFont(Font.font("Algerian", FontWeight.BOLD, FontPosture.REGULAR, 40));
        this.txtGameTitle.setWrappingWidth(1000.0D);
        this.txtGameTitle.setTextAlignment(TextAlignment.CENTER);
        this.txtGameTitle.setFill(Color.web(Values.STR_CLR_CONTRAST_BG));
        this.txtGameTitle.setLayoutX(0.0D);
        this.txtGameTitle.setLayoutY(64.0D);
        this.paneGameArena = new Pane();
        this.paneGameArena.setPrefSize(750.0D, 500.0D);
        this.paneGameArena.setLayoutX(Values.DBL_BUFFER_GAP);
        this.paneGameArena.setLayoutY(100.0D);
        this.paneGameArena.setScaleShape(true);
        this.paneGameArena.setBackground(new Background(new BackgroundFill(Color.web(Values.STR_GAME_ARENA_BG), CornerRadii.EMPTY, Insets.EMPTY)));
        this.lblNumberTile = new Label[100];
        final double DBL_GAP = 10.0D,DBL_TILE_WIDTH = (750.0D - 11*DBL_GAP)/10.0D, DBL_TILE_HEIGHT = (500.0D - 11*DBL_GAP)/10.0D;
        for(int i = 0; i < 100; i++) {
            final int I = i;
            this.lblNumberTile[I] = new Label(""+(i+1));
            this.lblNumberTile[I].setPrefSize(DBL_TILE_WIDTH,DBL_TILE_HEIGHT);
            this.lblNumberTile[I].setAlignment(Pos.CENTER);
            this.lblNumberTile[I].setBackground(new Background(new BackgroundFill(Color.web(Values.STR_TILE_NORMAL_BG), CornerRadii.EMPTY, Insets.EMPTY)));
            this.lblNumberTile[I].setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            this.paneGameArena.getChildren().add(this.lblNumberTile[i]);
            this.lblNumberTile[i].setLayoutX((i%10)*(DBL_TILE_WIDTH)+((i%10)+1)*DBL_GAP);
            this.lblNumberTile[i].setLayoutY((i/10)*(DBL_TILE_HEIGHT)+((i/10)+1)*DBL_GAP);
            this.lblNumberTile[I].setOnMouseEntered(e->{
                if(blnMouseActionAllowed && !this.blnGuessed[I]) {
                    this.lblNumberTile[I].setBackground(new Background(new BackgroundFill(Color.web(Values.STR_TILE_NORMAL_HOVERED_BG), CornerRadii.EMPTY, Insets.EMPTY)));
                    this.lblNumberTile[I].setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, this.lblNumberTile[I].getFont().getSize()*1.3D));
                }
            });
            this.lblNumberTile[I].setOnMouseExited(e->{
                if(blnMouseActionAllowed) {
                    if (!this.blnGuessed[I]) {
                        this.lblNumberTile[I].setBackground(new Background(new BackgroundFill(Color.web(Values.STR_TILE_NORMAL_BG), CornerRadii.EMPTY, Insets.EMPTY)));
                        this.lblNumberTile[I].setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, this.lblNumberTile[I].getFont().getSize()/1.3D));
                    } else {
                        this.lblNumberTile[I].setBackground(new Background(new BackgroundFill(Color.web(Values.STR_WRONG_TILE_BG), CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                }
            });
            this.lblNumberTile[I].setOnMouseClicked(e->{
                if(this.blnMouseActionAllowed) this.validateGuess(I+1);
            });
        }
        this.paneDetails = new Pane();
        this.paneDetails.setPrefSize(190.0D, 500.0D);
        this.paneDetails.setLayoutX(2*Values.DBL_BUFFER_GAP + 750.0D);
        this.paneDetails.setLayoutY(100.0D);
        this.txtGuessIs = new Text("Guess the number");
        this.txtGuessIs.setTextAlignment(TextAlignment.CENTER);
        this.txtGuessIs.setWrappingWidth(190.0D);
        this.txtGuessIs.setLayoutX(0.0D);
        this.txtGuessIs.setLayoutY(190.0D);
        this.txtGuessIs.setFill(Color.web(Values.STR_TEXT_CLR));
        this.txtGuessIs.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        this.txtGuessDiff = new Text();
        this.txtGuessDiff.setTextAlignment(TextAlignment.CENTER);
        this.txtGuessDiff.setWrappingWidth(190.0D);
        this.txtGuessDiff.setLayoutX(0.0D);
        this.txtGuessDiff.setLayoutY(240.0D);
        this.txtGuessDiff.setFill(Color.web(Values.STR_TEXT_CLR));
        this.txtGuessDiff.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 30));
        this.txtGuessLeftIs = new Text("Guesses Left");
        this.txtGuessLeftIs.setTextAlignment(TextAlignment.CENTER);
        this.txtGuessLeftIs.setWrappingWidth(190.0D);
        this.txtGuessLeftIs.setLayoutX(0.0D);
        this.txtGuessLeftIs.setLayoutY(70.0D);
        this.txtGuessLeftIs.setFill(Color.web(Values.STR_TEXT_CLR));
        this.txtGuessLeftIs.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        this.txtGuessLeft = new Text(this.intGuessLeft+"");
        this.txtGuessLeft.setTextAlignment(TextAlignment.CENTER);
        this.txtGuessLeft.setWrappingWidth(190.0D);
        this.txtGuessLeft.setLayoutX(0.0D);
        this.txtGuessLeft.setLayoutY(115.0D);
        this.txtGuessLeft.setFill(Color.web(Values.STR_TILE_NORMAL_HOVERED_BG));
        this.txtGuessLeft.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 40));
        this.txtPlayAgain = new Text("PLAY AGAIN");
        this.txtPlayAgain.setTextAlignment(TextAlignment.CENTER);
        this.txtPlayAgain.setWrappingWidth(190.0D);
        this.txtPlayAgain.setLayoutX(0.0D);
        this.txtPlayAgain.setLayoutY(440.0D);
        this.txtPlayAgain.setMouseTransparent(true);
        this.txtPlayAgain.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        this.rectPlayAgain = new Rectangle();
        this.rectPlayAgain.setLayoutX(35.0D);
        this.rectPlayAgain.setLayoutY(410.0D);
        this.rectPlayAgain.setWidth(120.0D);
        this.rectPlayAgain.setHeight(50.0D);
        this.rectPlayAgain.setFill(Color.web(Values.STR_BTN_PLAY_AGAIN_BG));
        this.rectPlayAgain.setArcHeight(20.0D);
        this.rectPlayAgain.setArcWidth(20.0D);
        this.rectPlayAgain.setOnMouseEntered(e->{
            this.rectPlayAgain.setFill(Color.web(Values.STR_BTN_PLAY_AGAIN_HOVERED_BG));
        });
        this.rectPlayAgain.setOnMouseExited(e->{
            this.rectPlayAgain.setFill(Color.web(Values.STR_BTN_PLAY_AGAIN_BG));
        });
        this.rectPlayAgain.setOnMouseClicked(e->{
            for(int i = 0; i < 100; i++) {
                this.lblNumberTile[i].setBackground(new Background(new BackgroundFill(Color.web(Values.STR_TILE_NORMAL_BG), CornerRadii.EMPTY, Insets.EMPTY)));
                this.lblNumberTile[i].setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 20.0D*(this.paneBase.getWidth()/1000.0D)));
                this.lblNumberTile[i].setOpacity(1.0D);
                this.blnGuessed[i] = false;
            }
            this.setupParameters();
            this.rectPlayAgain.setVisible(false);
            this.txtPlayAgain.setVisible(false);
            this.rectHowToPlay.setVisible(true);
            this.txtHowToPlay.setVisible(true);
        });
        this.txtHowToPlay = new Text("HOW TO PLAY?");
        this.txtHowToPlay.setTextAlignment(TextAlignment.CENTER);
        this.txtHowToPlay.setWrappingWidth(190.0D);
        this.txtHowToPlay.setLayoutX(0.0D);
        this.txtHowToPlay.setLayoutY(440.0D);
        this.txtHowToPlay.setMouseTransparent(true);
        this.txtHowToPlay.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        this.rectHowToPlay = new Rectangle();
        this.rectHowToPlay.setLayoutX(35.0D);
        this.rectHowToPlay.setLayoutY(410.0D);
        this.rectHowToPlay.setWidth(120.0D);
        this.rectHowToPlay.setHeight(50.0D);
        this.rectHowToPlay.setFill(Color.web(Values.STR_BTN_PLAY_AGAIN_BG));
        this.rectHowToPlay.setArcHeight(20.0D);
        this.rectHowToPlay.setArcWidth(20.0D);
        this.rectHowToPlay.setOnMouseEntered(e->{
            this.rectHowToPlay.setFill(Color.web(Values.STR_BTN_PLAY_AGAIN_HOVERED_BG));
        });
        this.rectHowToPlay.setOnMouseExited(e->{
            this.rectHowToPlay.setFill(Color.web(Values.STR_BTN_PLAY_AGAIN_BG));
        });
        this.rectHowToPlay.setOnMouseClicked(e->{
            this.paneHowToPlay.setLayoutX((this.paneBase.getWidth()-this.paneHowToPlay.getWidth())/2.0D);
            Timeline tl_fade = new Timeline(new KeyFrame(Duration.millis(800.0D), new KeyValue(this.paneGameArena.opacityProperty(),0.2D)),
                    new KeyFrame(Duration.millis(800.0D), new KeyValue(this.paneDetails.opacityProperty(),0.2D)),
                    new KeyFrame(Duration.millis(800.0D), new KeyValue(this.paneHowToPlay.layoutYProperty(),90.0D)));
            tl_fade.playFromStart();
        });
        this.txtRounds = new Text("ROUNDS WON");
        this.txtRounds.setTextAlignment(TextAlignment.CENTER);
        this.txtRounds.setWrappingWidth(190.0D);
        this.txtRounds.setLayoutX(0.0D);
        this.txtRounds.setLayoutY(320.0D);
        this.txtRounds.setMouseTransparent(true);
        this.txtRounds.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        this.txtRounds.setFill(Color.web(Values.STR_TEXT_CLR));
        this.txtRoundsWon = new Text("0 / 0");
        this.txtRoundsWon.setTextAlignment(TextAlignment.CENTER);
        this.txtRoundsWon.setWrappingWidth(190.0D);
        this.txtRoundsWon.setLayoutX(0.0D);
        this.txtRoundsWon.setLayoutY(360.0D);
        this.txtRoundsWon.setMouseTransparent(true);
        this.txtRoundsWon.setFont(Font.font("Consolas", FontWeight.NORMAL, FontPosture.REGULAR, 24));
        this.txtRoundsWon.setFill(Color.web(Values.STR_TILE_NORMAL_HOVERED_BG));
        this.rectPlayAgain.setVisible(false);
        this.txtPlayAgain.setVisible(false);
        this.rectGuess = new Rectangle();
        this.rectGuess.setLayoutX(27.0D);
        this.rectGuess.setLayoutY(52.0D);
        this.rectGuess.setWidth(140.0D);
        this.rectGuess.setHeight(76.0D);
        this.rectGuess.setFill(Color.web(Values.STR_CLR_BG));
        this.rectRoundsWon = new Rectangle();
        this.rectRoundsWon.setLayoutX(27.0D);
        this.rectRoundsWon.setLayoutY(298.0D);
        this.rectRoundsWon.setWidth(140.0D);
        this.rectRoundsWon.setHeight(76.0D);
        this.rectRoundsWon.setFill(Color.web(Values.STR_CLR_BG));
        this.paneDetails.getChildren().addAll(this.rectGuess, this.rectRoundsWon, this.rectHowToPlay, this.txtGuessIs, this.txtGuessDiff, this.txtGuessLeft,
                this.txtGuessLeftIs, this.rectPlayAgain, this.txtPlayAgain, this.txtRounds, this.txtRoundsWon, this.txtHowToPlay);
        this.paneDetails.setBackground(new Background(new BackgroundFill(Color.web(Values.STR_GAME_ARENA_BG), CornerRadii.EMPTY, Insets.EMPTY)));
        this.paneBase.getChildren().addAll(this.lineTop,this.lineBottom,this.txtGameTitle,this.paneGameArena,this.paneDetails,this.paneHowToPlay);
    }

    private static class Values {
        private static final String STR_CLR_BG = "#29102B", STR_CLR_CONTRAST_BG = "#AD44B8", STR_GAME_ARENA_BG = "#59174F",
            STR_TILE_NORMAL_BG = "#CC74A2", STR_TILE_NORMAL_HOVERED_BG = "#E8BB07", STR_WRONG_TILE_BG = "#FF175C", STR_CORRECT_TILE_BG = "#0BFF0A",
            STR_CORRECT_TILE_REVEAL = "#01ABFF", STR_BTN_PLAY_AGAIN_BG = "#FF0ECD", STR_BTN_PLAY_AGAIN_HOVERED_BG = "#B42AFF",
            STR_TEXT_CLR = "#CCCCCC";
        private static final double DBL_MIN_WINDOW_WIDTH = 800.0D, DBL_MIN_WINDOW_HEIGHT = 550.0D, DBL_BUFFER_GAP = 20.0D;
    }

    public static void main(String[] args) {
        launch();
    }
}