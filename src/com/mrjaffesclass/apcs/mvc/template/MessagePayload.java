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
  private final int[] columns;
  private final int[] rows;
  private final boolean bombState;
  private final int lives;
  private final int score;

  /**
   * Class constructor
   * @param _column Column # of jButton
   * @param _row Row # of jButton
   * @param _columns array of columns
   * @param _rows array of rows
   * @param _bombState whether the button is a bomb
   * @param _lives number of lives
   * @param _score score
   */
  public MessagePayload(int _column, int _row, int[] _columns, int[] _rows, boolean _bombState, int _lives, int _score) {
    column = _column;
    row = _row;
    columns = _columns;
    rows = _rows;
    bombState = _bombState;
    lives = _lives;
    score = _score;
  }
  
  /**
   * Getter method for column #
   * @return column of jButton
   */
  public int getColumn() {
    return column;
  }
  
  /**
   * Getter method for row #
   * @return row of jButton
   */
  public int getRow() {
    return row;
  }
  
  /**
   * Getter method for the column array
   * @return column array
   */
  public int[] getColumns() {
    return columns;
  }
  
  /**
   * Getter method for the row array 
   * @return row array
   */
  public int[] getRows() {
    return rows;
  }
  /**
   * Getter method for whether the button is a bomb
   * @return if the button is a bomb
   */
  public boolean getBombState() {
    return bombState; 
  }
  
  /**
   * Getter method for the lives 
   * @return number of lives
   */
  public int getLives() {
    return lives;
  }
  
  /**
   * Getter method for the score
   * @return score
   */
  public int getScore() {
    return score;
  }
}
