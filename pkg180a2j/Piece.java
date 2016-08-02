package pkg180a2j;

public class Piece {

      String type;
      int gas = 3;
      MyPoint p = new MyPoint(0, 0);
      boolean capturedElse = false;
      boolean captured = false;
      char player;
      int id;
      String empty = "-- ";

      public Piece(String s, int x, int y, char player, int id) {
            this.player = player;
            type = s;
            p.x = x;
            p.y = y;
            this.id = id;
      }

      public String getID() {
            return type;
      }

      public String toString() {
            String str = new String();
            if (captured) {
                  return empty;
            } else {
                  str += type + gas + " ";
                  return str;
            }
      }

      public void copy(Piece p) {
            this.type = p.type;
            this.gas = p.gas;
            this.capturedElse = p.capturedElse;
            this.p.x = p.p.x;
            this.p.y = p.p.y;
            this.player = p.player;
            this.id = p.id;
      }
}
