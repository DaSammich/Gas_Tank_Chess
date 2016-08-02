package pkg180a2j;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class GridGui2 extends JFrame {

      GridPanel myPanel;

      public GridGui2() {
            setSize(1040, 910);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setVisible(true);

            myPanel = new GridPanel();
            
            this.add(myPanel, BorderLayout.CENTER);

            repaint();
      }

}
