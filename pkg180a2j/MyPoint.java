package pkg180a2j;

import java.awt.Point;

public class MyPoint extends Point {
      
      public MyPoint(int x, int y) {
            super(x,y);
      }
      
      @Override
      public String toString() {
            String str = new String();
            str += "(" + this.x + ", " + this.y + ")";
            return str;
      }
}
