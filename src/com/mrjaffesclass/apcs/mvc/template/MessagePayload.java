package com.mrjaffesclass.apcs.mvc.template;

/**
 * This is the message payload class.  Instantiate this class when sending
 * field / value message data for the up/down buttons
 * Minefield Payload
 * @author Roger Jaffe
 * @version 1.0
 */
public class MessagePayload {
  
  private final int column;
  private final int row;
  
  /**
   * Class constructor
   * @param _column Column # of jButton
   * @param _row Row # of jButton
   */
  public MessagePayload(int _column, int _row) {
    column = _column;
    row = _row;
  }
  
  /**
   * Getter method for the direction
   * @return Field value
   */
  public int getColumn() {
    return column;
  }
  
  /**
   * Getter method for the 
   * @return 
   */
  public int getRow() {
    return row;
  }
  
}
