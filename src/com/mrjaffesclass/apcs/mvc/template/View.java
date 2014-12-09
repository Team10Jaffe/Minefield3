package com.mrjaffesclass.apcs.mvc.template;
import com.mrjaffesclass.apcs.messenger.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
/**
 * MVC Template
 * This is a template of an MVC framework used by APCS for the 
 * LandMine project (and others)
 * @author Roger Jaffe
 * @version 1.0
 * 
 */
public class View extends javax.swing.JFrame implements MessageHandler, ActionListener {

  private final Messenger mvcMessaging;
  
  /**
   * Creates a new view
   * @param messages mvcMessaging object
   */
  public View(Messenger messages) {
    mvcMessaging = messages;   // Save the calling controller instance
    initComponents();           // Create and init the GUI components
  }
  public static JButton buttonArray[][] = new JButton[8][8];
  public void createArray(){
    
    buttonArray[0][0] = jButton1;
    buttonArray[0][1] = jButton2;
    buttonArray[0][2] = jButton3;
    buttonArray[0][3] = jButton4;
    buttonArray[0][4] = jButton5;
    buttonArray[0][5] = jButton6;
    buttonArray[0][6] = jButton7;
    buttonArray[0][7] = jButton8;
    buttonArray[1][0] = jButton9;
    buttonArray[1][1] = jButton10;
    buttonArray[1][2] = jButton11;
    buttonArray[1][3] = jButton12;
    buttonArray[1][4] = jButton13;
    buttonArray[1][5] = jButton14;
    buttonArray[1][6] = jButton15;
    buttonArray[1][7] = jButton16;
    buttonArray[2][0] = jButton17;
    buttonArray[2][1] = jButton18;
    buttonArray[2][2] = jButton19;
    buttonArray[2][3] = jButton20;
    buttonArray[2][4] = jButton21;
    buttonArray[2][5] = jButton22;
    buttonArray[2][6] = jButton23;
    buttonArray[2][7] = jButton24;
    buttonArray[3][0] = jButton25;
    buttonArray[3][1] = jButton26;
    buttonArray[3][2] = jButton27;
    buttonArray[3][3] = jButton28;
    buttonArray[3][4] = jButton29;
    buttonArray[3][5] = jButton30;
    buttonArray[3][6] = jButton31;
    buttonArray[3][7] = jButton32;
    buttonArray[4][0] = jButton33;
    buttonArray[4][1] = jButton34;
    buttonArray[4][2] = jButton35;
    buttonArray[4][3] = jButton36;
    buttonArray[4][4] = jButton37;
    buttonArray[4][5] = jButton38;
    buttonArray[4][6] = jButton39;
    buttonArray[4][7] = jButton40;
    buttonArray[5][0] = jButton41;
    buttonArray[5][1] = jButton42;
    buttonArray[5][2] = jButton43;
    buttonArray[5][3] = jButton44;
    buttonArray[5][4] = jButton45;
    buttonArray[5][5] = jButton46;
    buttonArray[5][6] = jButton47;
    buttonArray[5][7] = jButton48;
    buttonArray[6][0] = jButton49;
    buttonArray[6][1] = jButton50;
    buttonArray[6][2] = jButton51;
    buttonArray[6][3] = jButton52;
    buttonArray[6][4] = jButton53;
    buttonArray[6][5] = jButton54;
    buttonArray[6][6] = jButton55;
    buttonArray[6][7] = jButton56;
    buttonArray[7][0] = jButton57;
    buttonArray[7][1] = jButton58;
    buttonArray[7][2] = jButton59;
    buttonArray[7][3] = jButton60;
    buttonArray[7][4] = jButton61;
    buttonArray[7][5] = jButton62;
    buttonArray[7][6] = jButton63;
    buttonArray[7][7] = jButton64;
  }
  
  public void assignClientProperties(){
      for (int i = 0; i <= 7; i++){
        for (int j = 0; j <= 7; j++){
            buttonArray[i][j].putClientProperty("column", i);
            buttonArray[i][j].putClientProperty("row", j);
            buttonArray[i][j].putClientProperty("bomb", false);
            buttonArray[i][j].addActionListener(this);
        }
    }
  }
  /**
   * Initialize the model here and subscribe
   * to any required messages
   */
  public void init() {
    // Subscribe to messages here
    mvcMessaging.subscribe("controller:init", this);
    mvcMessaging.subscribe("model:bombsPlanted", this);
    mvcMessaging.subscribe("model:bombClicked", this);
    mvcMessaging.subscribe("model:safeClicked", this);
    mvcMessaging.subscribe("model:livesChanged", this);
    mvcMessaging.subscribe("model:scoreChanged", this);
    mvcMessaging.subscribe("model:gameOver", this);
  }
  
 @Override
  public void messageHandler(String messageName, Object messagePayload) {
    if (messagePayload != null) {
      System.out.println("MSG: received by view: "+messageName+" | "+messagePayload.toString());
    } else {
      System.out.println("MSG: received by view: "+messageName+" | No data sent");
    }
    MessagePayload payload = (MessagePayload)messagePayload;
        switch (messageName) {
            case "controller:init":
                createArray();
                assignClientProperties();
                jLabel3.setVisible(false);
                break;
            case "model:bombsPlanted":
                for (int i = 0; i < 10; i++){
                    int[] column = payload.getColumns();
                    int[] row = payload.getRows();  
                    buttonArray[column[i]][row[i]].putClientProperty("bomb", true);
                }
                break;
            case "model:bombClicked":
                buttonArray[payload.getColumn()][payload.getRow()].setBackground(Color.red);
                break;
            case "model:safeClicked":
                buttonArray[payload.getColumn()][payload.getRow()].setBackground(Color.green);
                buttonArray[payload.getColumn()][payload.getRow()].setForeground(Color.green);
                break;
            case ("model:livesChanged"):
                jTextField2.setText(payload.getLives() + "");
                break;
            case "model:scoreChanged":
                jTextField1.setText(payload.getScore() + "");
                break;
            case "model:gameOver":
                jLabel3.setVisible(true);
                break;
      }
  }

  /**
   * Instantiate an object with the field number that was clicked (1 or 2) and
   * the direction that the number should go (up or down)
   * @param fieldNumber 1 or 2 for the field being modified
   * @param direction this.UP (1) or this.DOWN (-1), constants defined above
   * @return the HashMap payload to be sent with the message
   */
  private MessagePayload createPayload(int columnNumber, int rowNumber, int[] columns, int[] rows, boolean bombState, int lives, int score) {
    MessagePayload payload = new MessagePayload(columnNumber, rowNumber, columns, rows, bombState, lives, score);
    return payload;
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        jButton50 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        jButton61 = new javax.swing.JButton();
        jButton62 = new javax.swing.JButton();
        jButton63 = new javax.swing.JButton();
        jButton64 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, -1, 25));
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 45, -1, 25));
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 77, -1, 25));
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 109, -1, 25));
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 141, -1, 25));
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 173, -1, 25));
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 205, -1, 25));
        getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 237, -1, 25));
        getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 13, -1, 25));
        getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 45, -1, 25));
        getContentPane().add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 77, -1, 25));
        getContentPane().add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 109, -1, 25));
        getContentPane().add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 141, -1, 25));
        getContentPane().add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 173, -1, 25));
        getContentPane().add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 205, -1, 25));
        getContentPane().add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 237, -1, 25));
        getContentPane().add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 13, -1, 25));
        getContentPane().add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 45, -1, 25));
        getContentPane().add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 77, -1, 25));
        getContentPane().add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 109, -1, 25));
        getContentPane().add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 141, -1, 25));
        getContentPane().add(jButton22, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 173, -1, 25));
        getContentPane().add(jButton23, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 205, -1, 25));
        getContentPane().add(jButton24, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 237, -1, 25));
        getContentPane().add(jButton25, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 13, -1, 25));
        getContentPane().add(jButton26, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 45, -1, 25));
        getContentPane().add(jButton27, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 77, -1, 25));
        getContentPane().add(jButton28, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 109, -1, 25));
        getContentPane().add(jButton29, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 141, -1, 25));
        getContentPane().add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 173, -1, 25));
        getContentPane().add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 205, -1, 25));
        getContentPane().add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 237, -1, 25));
        getContentPane().add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 13, -1, 25));
        getContentPane().add(jButton34, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 45, -1, 25));
        getContentPane().add(jButton35, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 77, -1, 25));
        getContentPane().add(jButton36, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 109, -1, 25));
        getContentPane().add(jButton37, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 141, -1, 25));
        getContentPane().add(jButton38, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 173, -1, 25));
        getContentPane().add(jButton39, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 205, -1, 25));
        getContentPane().add(jButton40, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 237, -1, 25));
        getContentPane().add(jButton41, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 13, -1, 25));
        getContentPane().add(jButton42, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 45, -1, 25));
        getContentPane().add(jButton43, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 77, -1, 25));
        getContentPane().add(jButton44, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 109, -1, 25));
        getContentPane().add(jButton45, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 141, -1, 25));
        getContentPane().add(jButton46, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 173, -1, 25));
        getContentPane().add(jButton47, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 205, -1, 25));
        getContentPane().add(jButton48, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 237, -1, 25));
        getContentPane().add(jButton49, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 13, -1, 25));
        getContentPane().add(jButton50, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 45, -1, 25));
        getContentPane().add(jButton51, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 77, -1, 25));
        getContentPane().add(jButton52, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 109, -1, 25));
        getContentPane().add(jButton53, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 141, -1, 25));
        getContentPane().add(jButton54, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 173, -1, 25));
        getContentPane().add(jButton55, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 205, -1, 25));
        getContentPane().add(jButton56, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 237, -1, 25));
        getContentPane().add(jButton57, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 13, -1, 25));
        getContentPane().add(jButton58, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 45, -1, 25));
        getContentPane().add(jButton59, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 77, -1, 25));
        getContentPane().add(jButton60, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 109, -1, 25));
        getContentPane().add(jButton61, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 141, -1, 25));
        getContentPane().add(jButton62, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 173, -1, 25));
        getContentPane().add(jButton63, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 205, -1, 25));
        getContentPane().add(jButton64, new org.netbeans.lib.awtextra.AbsoluteConstraints(292, 237, -1, 25));

        jLabel1.setText("Score:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        jLabel2.setText("Lives:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 283, -1, -1));

        jTextField1.setText("0");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 280, 100, -1));

        jTextField2.setText("3");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 280, 90, -1));

        jLabel3.setText("GAME OVER");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 315, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

  @Override
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton)e.getSource();
        int buttonColumn = (int)button.getClientProperty("column");
        int buttonRow = (int)button.getClientProperty("row");
        boolean bombState = (boolean)button.getClientProperty("bomb");
        System.out.println("Column: " + buttonColumn);
        System.out.println("Row: " + buttonRow);
        System.out.println("Bomb?: " + bombState);
        mvcMessaging.notify("view:buttonClicked", createPayload(buttonColumn, buttonRow, null, null, bombState, 0, 0), true);   
    }
  /**
   * @param args the command line arguments
   */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton63;
    private javax.swing.JButton jButton64;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    
    
}
