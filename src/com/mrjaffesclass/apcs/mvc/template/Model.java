package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.*;
import java.util.Random;

/**
 * The model represents the data that the app uses.
 * @author Roger Jaffe
 * @version 1.0
 */
public class Model implements MessageHandler {

  // Messaging system for the MVC
  private final Messenger mvcMessaging;

  // Model's data variables
  public int lives = 3;
  public int score = 0;
  Random rand = new Random();
  /**
   * Model constructor: Create the data representation of the program
   * @param messages Messaging class instantiated by the Controller for 
   *   local messages between Model, View, and controller
   */
  public Model(Messenger messages) {
    mvcMessaging = messages;
  }
  
  /**
   * Initialize the model here and subscribe to any required messages
   */
  public void init() {
    mvcMessaging.subscribe("view:buttonClicked", this);
    mvcMessaging.subscribe("controller:init", this);
    plantBombs();
  }
  
  private MessagePayload createPayload(int columnNumber, int rowNumber, int[] columns, int[] rows, boolean bombState, int lives, int score) {
    MessagePayload payload = new MessagePayload(columnNumber, rowNumber, columns, rows, bombState, lives, score);
    return payload;
  }
  
  public void plantBombs() {
    double mineArray[] = new double[10];
    int columnArray[] = new int[10];
    int rowArray[] = new int[10];
    for (int i = 0; i < 10; i++) {
        mineArray[i] = (double)rand.nextInt(64);
        columnArray[i] = (int)Math.floor(mineArray[i]/8);
        if (mineArray[i]%8 == 0) {
            rowArray[i] = (int)(mineArray[i]/8);
        }
        else {
            rowArray[i] = (int)(mineArray[i]%8) - 1;
        }
        mvcMessaging.notify("model:bombsPlanted", createPayload(0, 0, columnArray, rowArray, true, 0, 0), true);
        System.out.println("Bomb planted at Column: " + columnArray[i] + " and Row: " + rowArray[i]);
    }
    
  }
  
       
  @Override
  public void messageHandler(String messageName, Object messagePayload) {
    if (messagePayload != null) {
      System.out.println("MSG: received by model: "+messageName+" | "+messagePayload.toString());
    } else {
      System.out.println("MSG: received by model: "+messageName+" | No data sent");
    }
    MessagePayload payload = (MessagePayload)messagePayload;
    int column = payload.getColumn();
    int row = payload.getRow();
    boolean bombState = payload.getBombState();
    
      switch (messageName) {
            case "controller:init":
                break;
            case "view:buttonClicked":
              if (bombState == true){
                  mvcMessaging.notify("model:bombClicked", createPayload(column, row, null, null, bombState, 0, 0), true);
                  System.out.println("Bomb clicked!");
                  decreaseLives();
              }
              else {
                  mvcMessaging.notify("model:safeClicked", createPayload(column, row, null, null, bombState, 0, 0), true);
                  System.out.println("Not bomb clicked!");
                  increaseScore();
              }
              break;
      }
    /*
    if (column == 0) {
      if (row == 0) {
        System.out.println("Received column: " + column + " and row: " + row);
      } else {
        //setVariable2(getVariable2()+Constants.FIELD_2_INCREMENT);
      }
    } 
    else {
      if (column == 1) {
        setVariable1(getVariable1()-Constants.FIELD_1_INCREMENT);
      } else {
        setVariable2(getVariable2()-Constants.FIELD_2_INCREMENT);
      }      
    } */
  } 
   
    
  /**
   * Getter function for lives
   * @return # of lives left
   */
  public int getLives() {
    return lives;
  }

  /**
   * Decreases life count by 1
   */
  public void decreaseLives() {
    lives = lives + (-1);
    if (lives == 0){
        mvcMessaging.notify("model:gameOver");
    }
    mvcMessaging.notify("model:livesChanged", createPayload(0, 0, null, null, true, lives, score), true);
  }
  
  /**
   * Getter function for score
   * @return Value of score
   */
  public int getScore() {
    return score;
  }
  
  /**
   * Increases score by 1
   */
  public void increaseScore() {
    score = score + 1;
    mvcMessaging.notify("model:scoreChanged", createPayload(0, 0, null, null, true, lives, score), true);
  }

}
