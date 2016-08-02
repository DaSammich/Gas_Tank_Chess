package pkg180a2j;

import java.util.ArrayList;

public class MoveGenerator {

      int[][] b = new int[7][8];
      ArrayList<MyPoint> mvs = new ArrayList<>();

      // this generates the moves as points
      public ArrayList<MyPoint> generateMovesP(Piece p, int[][] b) {
            // reset the array after we use it
            mvs.clear();
            this.b = b; // the board is reference and therefore always updated
            
            // checks to see there is any gas left in the piece
            if (p.gas <= 0) {
                  return mvs;
            }

            // check what piece it is
            if (Character.toLowerCase(p.type.charAt(0)) == 110) {
                  // knight
//                  System.out.println("Found a knight!");
                  return nMoves(p.p);
            } else if (Character.toLowerCase(p.type.charAt(0)) == 98) {
                  // bishop
//                  System.out.println("Found a bishop!");
                  return bMoves(p.p);
            } else if (Character.toLowerCase(p.type.charAt(0)) == 113) {
                  // queen
//                  System.out.println("Found a queen!");
                  return qMoves(p.p);
            } else if (Character.toLowerCase(p.type.charAt(0)) == 107) {
                  // king
//                  System.out.println("Found a king!");
                  return kMoves(p.p);

            } else {
//                  System.out.println("Failed to find a piece");
                  return null;
            }
      }

      // this takes the points and converts them into input strings
      // the following move generation only generates the points where they can move
      public ArrayList<String> convertToString(ArrayList<MyPoint> a, Piece p) {
            System.out.println(p.p);
            return null;
      }

      // for each move method, the initial position of the piece is
      // in the first element of the array list
      // knight moves
      public ArrayList<MyPoint> nMoves(MyPoint p) {
            int r = p.x;
            int c = p.y;

            mvs.add(new MyPoint(r, c));

            // check for out of bounds 
            // left side
            if (!(c - 2 < 0)) {
                  if (!(r - 1 < 0)) {
                        mvs.add(new MyPoint(r - 1, c - 2));
                  }
                  if (!(r + 1 > 6)) {
                        mvs.add(new MyPoint(r + 1, c - 2));
                  }
            }

            // right side
            if (!(c + 2 > 7)) {
                  if (!(r - 1 < 0)) {
                        mvs.add(new MyPoint(r - 1, c + 2));
                  }
                  if (!(r + 1 > 6)) {
                        mvs.add(new MyPoint(r + 1, c + 2));
                  }
            }

            // up side
            if (!(r - 2 < 0)) {
                  if (!(c - 1 < 0)) {
                        mvs.add(new MyPoint(r - 2, c - 1));
                  }
                  if (!(c + 1 > 7)) {
                        mvs.add(new MyPoint(r - 2, c + 1));
                  }
            }

            // down side
            if (!(r + 2 > 6)) {
                  if (!(c - 1 < 0)) {
                        mvs.add(new MyPoint(r + 2, c - 1));
                  }
                  if (!(c + 1 > 7)) {
                        mvs.add(new MyPoint(r + 2, c + 1));
                  }
            }

            return mvs;
      }

      // bishop moves
      public ArrayList<MyPoint> bMoves(MyPoint p) {
            int r = p.x;
            int c = p.y;
            int j;
            mvs.add(new MyPoint(r, c));

            // check going up left
            j = c;
            for (int i = r - 1; i >= 0; i--) {
                  if (j <= 0) {
                        break;
                  }
                  j--;

                  mvs.add(new MyPoint(i, j));

                  // capturing another piece is as far as you can ago
                  if (b[i][j] != 0) {
                        break;
                  }

            }

            // check going up right
            j = c;
            for (int i = r - 1; i >= 0; i--) {
                  if (j >= 7) {
                        break;
                  }

                  j++;
                  mvs.add(new MyPoint(i, j));
                  // capturing another piece is as far as you can ago
                  if (b[i][j] != 0) {
                        break;
                  }
            }

            // check going down left
            j = c;
            for (int i = r + 1; i < 7; i++) {
                  if (j <= 0) {
                        break;
                  }

                  j--;
                  mvs.add(new MyPoint(i, j));
                  // capturing another piece is as far as you can ago
                  if (b[i][j] != 0) {
                        break;
                  }
            }

            // check going down right
            j = c;
            for (int i = r + 1; i < 7; i++) {
                  if (j >= 7) {
                        break;
                  }

                  j++;
                  mvs.add(new MyPoint(i, j));
                  // capturing another piece is as far as you can ago
                  if (b[i][j] != 0) {
                        break;
                  }

            }

            return mvs;
      }

      // queen moves
      public ArrayList<MyPoint> qMoves(MyPoint p) {
            // combine bishop and knight moves

            // the following is bishop moves
            int r = p.x;
            int c = p.y;
            int j;
            mvs.add(new MyPoint(r, c));

            // check going up left
            j = c;
            for (int i = r - 1; i >= 0; i--) {
                  if (j <= 0) {
                        break;
                  }
                  j--;

                  mvs.add(new MyPoint(i, j));

                  // capturing another piece is as far as you can ago
                  if (b[i][j] != 0) {
                        break;
                  }

            }

            // check going up right
            j = c;
            for (int i = r - 1; i >= 0; i--) {
                  if (j >= 7) {
                        break;
                  }

                  j++;
                  mvs.add(new MyPoint(i, j));
                  // capturing another piece is as far as you can ago
                  if (b[i][j] != 0) {
                        break;
                  }
            }

            // check going down left
            j = c;
            for (int i = r + 1; i < 7; i++) {
                  if (j <= 0) {
                        break;
                  }

                  j--;
                  mvs.add(new MyPoint(i, j));
                  // capturing another piece is as far as you can ago
                  if (b[i][j] != 0) {
                        break;
                  }
            }

            // check going down right
            j = c;
            for (int i = r + 1; i < 7; i++) {
                  if (j >= 7) {
                        break;
                  }

                  j++;
                  mvs.add(new MyPoint(i, j));
                  // capturing another piece is as far as you can ago
                  if (b[i][j] != 0) {
                        break;
                  }

            }

            // the following is knight moves
            // check for out of bounds 
            // left side
            if (!(c - 2 < 0)) {
                  if (!(r - 1 < 0)) {
                        mvs.add(new MyPoint(r - 1, c - 2));
                  }
                  if (!(r + 1 > 6)) {
                        mvs.add(new MyPoint(r + 1, c - 2));
                  }
            }

            // right side
            if (!(c + 2 > 7)) {
                  if (!(r - 1 < 0)) {
                        mvs.add(new MyPoint(r - 1, c + 2));
                  }
                  if (!(r + 1 > 6)) {
                        mvs.add(new MyPoint(r + 1, c + 2));
                  }
            }

            // up side
            if (!(r - 2 < 0)) {
                  if (!(c - 1 < 0)) {
                        mvs.add(new MyPoint(r - 2, c - 1));
                  }
                  if (!(c + 1 > 7)) {
                        mvs.add(new MyPoint(r - 2, c + 1));
                  }
            }

            // down side
            if (!(r + 2 > 6)) {
                  if (!(c - 1 < 0)) {
                        mvs.add(new MyPoint(r + 2, c - 1));
                  }
                  if (!(c + 1 > 7)) {
                        mvs.add(new MyPoint(r + 2, c + 1));
                  }
            }

            return mvs;

      }

      // king moves
      public ArrayList<MyPoint> kMoves(MyPoint p) {
            int r = p.x;
            int c = p.y;

            mvs.add(new MyPoint(r, c));

            if (!(r - 1 < 0)) {
                  mvs.add(new MyPoint(r - 1, c));
            }

            if (!(r + 1 > 6)) {
                  mvs.add(new MyPoint(r + 1, c));
            }

            if (!(c - 1 < 0)) {
                  mvs.add(new MyPoint(r, c - 1));
            }
            if (!(c + 1 > 7)) {
                  mvs.add(new MyPoint(r, c + 1));
            }

            // diagonals here
            if (!(r - 1 < 0) && !(c - 1 < 0)) {
                  mvs.add(new MyPoint(r - 1, c - 1));
            }
            if (!(r - 1 < 0) && !(c + 1 > 7)) {
                  mvs.add(new MyPoint(r - 1, c + 1));
            }
            if (!(r + 1 > 6) && !(c - 1 < 0)) {
                  mvs.add(new MyPoint(r + 1, c - 1));
            }
            if (!(r + 1 > 6) && !(c + 1 > 7)) {
                  mvs.add(new MyPoint(r + 1, c + 1));
            }

            return mvs;
      }

}
