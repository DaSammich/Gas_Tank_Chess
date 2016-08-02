package pkg180a2j;

import java.awt.*;
import javax.swing.*;

public class GridPanel extends JPanel {

      JButton[][] myButtons = new JButton[8][9];
//      JLabel[][] myButtons = new JLabel[7][8];

      public GridPanel() {
            this.setLayout(new GridLayout(8, 9));

            initButtons();
      }

      public void setButtonText(int i, int j, String s) {
            myButtons[i][j].setText(s);
            this.repaint();
      }

      public void initButtons() {
            for (int i = 0; i < 8; i++) {
                  for (int j = 0; j < 9; j++) {

                        JButton myButton = new JButton("");
                        myButton.setFont(new Font("Arial", Font.BOLD, 48));
                        myButtons[i][j] = myButton;
                        this.add(myButtons[i][j]);
                        
                        if (j == 0) {
                              switch (i) {
                                    case 0:
                                          myButtons[i][j].setText("7");
                                          break;
                                    case 1:
                                          myButtons[i][j].setText("6");
                                          break;
                                    case 2:
                                          myButtons[i][j].setText("5");
                                          break;
                                    case 3:
                                          myButtons[i][j].setText("4");
                                          break;
                                    case 4:
                                          myButtons[i][j].setText("3");
                                          break;
                                    case 5:
                                          myButtons[i][j].setText("2");
                                          break;
                                    case 6:
                                          myButtons[i][j].setText("1");
                                          break;
                              }
                        }

                        if (i == 7) {
                              switch (j) {
                                    case 1:
                                          myButtons[i][j].setText("A");
                                          break;
                                    case 2:
                                          myButtons[i][j].setText("B");
                                          break;
                                    case 3:
                                          myButtons[i][j].setText("C");
                                          break;
                                    case 4:
                                          myButtons[i][j].setText("D");
                                          break;
                                    case 5:
                                          myButtons[i][j].setText("E");
                                          break;
                                    case 6:
                                          myButtons[i][j].setText("F");
                                          break;
                                    case 7:
                                          myButtons[i][j].setText("G");
                                          break;
                                    case 8:
                                          myButtons[i][j].setText("H");
                                          break;
                              }

                        }

                  }

            }
      }

}
