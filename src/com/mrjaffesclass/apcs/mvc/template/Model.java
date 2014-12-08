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
  private int variable1;
  private int variable2;
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
        /*System.out.println(mineArray[i]);
        System.out.println(columnArray[i]);
        System.out.println(rowArray[i]); */
        View.buttonArray[columnArray[i]][rowArray[i]].putClientProperty("bomb", true);
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
    System.out.println("Special: Received column: " + column + " and row: " + row);
    
      switch (messageName) {
          case "controller:init":
              break;
          case "view:buttonClicked":
              boolean bombState = (boolean)View.buttonArray[column][row].getClientProperty("bomb");
              if (bombState == true){
                  mvcMessaging.notify("model:bombClicked");
                  System.out.println("Bomb clicked!");
              }
              else {
                  mvcMessaging.notify("model:safeClicked");
                  System.out.println("Not bomb clicked!");
              }
              break;
      }
    if (column == 0) {
      if (row == 0) {
        System.out.println("Received column: " + column + " and row: " + row);
      } else {
        //setVariable2(getVariable2()+Constants.FIELD_2_INCREMENT);
      }
    } 
    /*else {
      if (column == 1) {
        setVariable1(getVariable1()-Constants.FIELD_1_INCREMENT);
      } else {
        setVariable2(getVariable2()-Constants.FIELD_2_INCREMENT);
      }      
    } */
  } 
   
    
  /**
   * Getter function for variable 1
   * @return Value of variable1
   */
  public int getVariable1() {
    return variable1;
  }

  /**
   * Setter function for variable 1
   * @param v New value of variable1
   */
  public void setVariable1(int v) {
    variable1 = v;
    // When we set a new value to variable 1 we need to also send a
    // message to let other modules know that the variable value
    // was changed
    mvcMessaging.notify("model:variable1Changed", variable1, true);
  }
  
  /**
   * Getter function for variable 1
   * @return Value of variable2
   */
  public int getVariable2() {
    return variable2;
  }
  
  /**
   * Setter function for variable 2
   * @param v New value of variable 2
   */
  public void setVariable2(int v) {
    variable2 = v;
    // When we set a new value to variable 2 we need to also send a
    // message to let other modules know that the variable value
    // was changed
    mvcMessaging.notify("model:variable2Changed", variable2, true);
  }

}
