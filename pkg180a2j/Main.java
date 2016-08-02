package pkg180a2j;

import java.util.*;

public class Main {

      Scanner scan = new Scanner(System.in);

      // init as row x col
      int[][] b = new int[7][8];
      String com = "computer", hum = "human", line = "  -----------------------";
      String chars = "  A  B  C  D  E  F  G  H";
      int rows = 7;
      MoveGenerator mvGen = new MoveGenerator();
      boolean gameOver = false;
      boolean playersTurn;
      String inputBuffer;
      String empty = "-- ";
      Random rng = new Random();
      final int DEPTH = 4;
      boolean printOn = false;
      GridGui2 grid = new GridGui2();
      Piece[] cont = new Piece[13];

      public Main() {
            playerOrder();
            initBoard();
            initPieces();
            printFullBoard();
            initGUI();
            loop();
      }

      public void playerOrder() {
            System.out.print("Go first? (y for yes anything else for no): ");
            inputBuffer = scan.nextLine();
            playersTurn = Character.toLowerCase(inputBuffer.charAt(0)) == 121;
            System.out.println();
      }

      public void initGUI() {
            updateGUI(grid);
      }

      public void updateGUI(GridGui2 g) {
            for (int i = 0; i < b.length; i++) {
                  for (int j = 0; j < b[i].length; j++) {
                        if (cont[b[i][j]].toString().compareTo("-- ") == 0) {
                              g.myPanel.setButtonText(i, j+1, " ");
                        } else {
                              g.myPanel.setButtonText(i, j+1, cont[b[i][j]].toString());
                        }
                  }

            }
      }

      // retracting is messy because of object referncing
      public void computerMove() {
//            System.out.println("Computer has possible moves: ");
            ArrayList<MyPoint> mvBuffer;

            // insert minimax here
            // traverse the computer pieces
            int bestScore = -Integer.MAX_VALUE;
            String bestMove = "";

            // for each coputer piece
            for (int i = 1; i < 7; i++) {
                  // if it is not a captured piece
                  if (!(((Piece) cont[i]).captured)) {
                        // if it is a computers move; also bypasses null (captured) objects 
                        // mvBuffer contains all of the moves (array of points)

                        mvBuffer = (genPieceMoves((Piece) cont[i]));
                        ArrayList<MyPoint> stableMvBuffer = new ArrayList<>();
                        stableMvBuffer.addAll(mvBuffer);

                        // start at 1; 0 is initial position
                        int size = stableMvBuffer.size();
                        // take size out; calling mvBuffer.size() calls genPieceMoves

                        // for every move that the piece can make
                        for (int j = 1; j < size; j++) {
                              MyPoint oldPoint = stableMvBuffer.get(0);
                              MyPoint newPoint = stableMvBuffer.get(j);

                              String str = moveP(oldPoint, newPoint);

                              int oldGas = oldGas = ((Piece) cont[b[oldPoint.x][oldPoint.y]]).gas;
                              int holder = 0;
                              // if there is a piece occupied there save it
                              if (b[newPoint.x][newPoint.y] != 0) {
                                    holder = b[newPoint.x][newPoint.y];
                              }

                              // make the move
                              // save the capture flag as well
                              Piece p = (Piece) (cont[(b[oldPoint.x][oldPoint.y])]);

//                                    System.out.println(mvBuffer);
                              this.movePieceEC(str);

                              Boolean captured = p.capturedElse;

                              if (printOn) {
                                    System.out.println("Made a move - " + str);
                                    printFullBoard();
                                    System.out.println("calling min...");
                              }

                              int score = min(DEPTH, bestScore);

//                              int score = 0;
                              if (score > bestScore) {
                                    bestScore = score;
                                    bestMove = str;
                              }

                              // make sure you have enough gas to unmove it
                              p.gas++;

                              this.unMove(str);
                              // restore the move
                              b[newPoint.x][newPoint.y] = holder;

                              // retract the move
                              if (captured) {
                                    ((Piece) (cont[(b[newPoint.x][newPoint.y])])).captured = false;
                              }
                              if (printOn) {
                                    System.out.println("Retracting move...");
                                    printFullBoard();
                                    System.out.println();
                              }
                              p.gas = oldGas;
                        }
                  }
            }

            // make the actual move here
            System.out.println("Computer moves - " + bestMove + " (" + this.convertToOpponentMove(bestMove) + ")");
            System.out.println();
            this.movePieceEC(bestMove);

            printFullBoard();

      }

      public String convertToOpponentMove(String s) {
            String str = "";
            str += s.charAt(0);
            int n1 = Integer.parseInt(s.substring(1, 2));
            n1 = -1 * (n1 - 8);
            str += n1;
            str += s.charAt(2);
            int n2 = Integer.parseInt(s.substring(3, 4));
            n2 = -1 * (n2 - 8);
            str += n2;
            return str;
      }

      public ArrayList<String> genCompMoves() {
            ArrayList<MyPoint> mvB;
            ArrayList<String> possibleMoves = new ArrayList<>();

            // for every computer piece
            for (int i = 1; i < 7; i++) {
                  if (cont[i] != null) {
                        mvB = (genPieceMoves((Piece) cont[i]));

                        int size = mvB.size();
                        // for every move that piece can make
                        for (int j = 1; j < size; j++) {
                              MyPoint oldPoint = mvB.get(0);
                              MyPoint newPoint = mvB.get(j);
                              String str = moveP(oldPoint, newPoint);
                              possibleMoves.add(str);
                        }
                  }
            }

            return possibleMoves;
      }

      public ArrayList<String> genHumMoves() {
            ArrayList<MyPoint> mvB;
            ArrayList<String> possibleMoves = new ArrayList<>();

            // for every computer piece
            for (int i = 7; i < 13; i++) {
                  if (cont[i] != null) {
                        mvB = (genPieceMoves((Piece) cont[i]));

                        int size = mvB.size();
                        // for every move that piece can make
                        for (int j = 1; j < size; j++) {
                              MyPoint oldPoint = mvB.get(0);
                              MyPoint newPoint = mvB.get(j);
                              String str = moveP(oldPoint, newPoint);
                              possibleMoves.add(str);
                        }
                  }
            }

            return possibleMoves;
      }

      public int getPieceWorth(String s) {
            String str = "";
            char c = s.charAt(0);
            int n = 0;
            switch (c) {
                  case 'K':
                        n += 20000;
                        break;
                  case 'k':
                        n += 2500;
                        break;
                  case 'b':
                        n += 500;
                        break;
                  case 'q':
                        n += 1000;
                        break;
                  case 'n':
                        n += 500;
                        break;
            }
            return n;
      }

      public int sumCompGas() {
            int n = 0;
            for (int i = 1; i < 7; i++) {
                  if (!(((Piece) cont[i]).captured)) {
                        n += ((Piece) cont[i]).gas;
                  }
            }
            return n;
      }

      public int sumPlayerGas() {
            int n = 0;
            for (int i = 7; i < 13; i++) {
                  if (!(((Piece) cont[i]).captured)) {
                        n += ((Piece) cont[i]).gas;
                  }
            }
            return n;
      }

      public int countCompWorth() {
            int n = 0;
            for (int i = 1; i < 7; i++) {
                  if (!(((Piece) cont[i]).captured)) {
                        n += getPieceWorth(cont[i].type);
                  }
            }
            return n;
      }

      public int countPlayerWorth() {
            int n = 0;
            for (int i = 7; i < 13; i++) {
                  if (!(((Piece) cont[i]).captured)) {
                        n += getPieceWorth(cont[i].type);
                  }
            }
            return n;
      }

      public int max(int level, int parentScore) {
            if (gameOver) {
                  return evalEnd();
            } else if (level <= 0) {
                  if (printOn) {
                        System.out.println("Max has ended");
                  }
                  return eval();
            }

            ArrayList<MyPoint> mvBuffer;
            int bestScore = -Integer.MAX_VALUE;

            // for each coputer piece
            for (int i = 1; i < 7; i++) {
                  if (!(((Piece) cont[i]).captured)) {
                        mvBuffer = (genPieceMoves((Piece) cont[i]));
                        ArrayList<MyPoint> stableMvBuffer = new ArrayList<>();
                        stableMvBuffer.addAll(mvBuffer);
                        int size = stableMvBuffer.size();

                        // for every piece move
                        for (int j = 1; j < size; j++) {
                              MyPoint oldPoint = stableMvBuffer.get(0);
                              MyPoint newPoint = stableMvBuffer.get(j);

                              String str = moveP(oldPoint, newPoint);

                              // if there is a piece occupied there save it
                              // also save the old gas since it changes due to capture
                              int oldGas = oldGas = ((Piece) cont[b[oldPoint.x][oldPoint.y]]).gas;
                              int holder = 0;
                              // if there is a piece occupied there save it
                              if (b[newPoint.x][newPoint.y] != 0) {
                                    holder = b[newPoint.x][newPoint.y];
                              }

                              Piece p = (Piece) (cont[(b[oldPoint.x][oldPoint.y])]);

                              this.movePieceEC(str);

                              Boolean captured = p.capturedElse;

                              if (printOn) {
                                    System.out.println("made a move - " + str);
                                    printFullBoard();
                                    System.out.println("calling min...");
                              }

                              int score = min(level - 1, bestScore);

                              if (score > bestScore) {
                                    bestScore = score;
                              }
                              // make sure you have enough gas to unmove it
                              p.gas++;

                              this.unMove(str);
                              // restore the move
                              b[newPoint.x][newPoint.y] = holder;

                              // retract the move
                              if (captured) {
                                    ((Piece) (cont[(b[newPoint.x][newPoint.y])])).captured = false;
                              }

                              if (printOn) {
                                    System.out.println("Retracting move...");
                                    printFullBoard();

                              }
                              p.gas = oldGas;

                              // alphabeta pruning here after retracting move
                              if (bestScore >= parentScore) {
                                    break;
                              }
                        }
                  }
            }

            return bestScore;
      }

      public int min(int level, int parentScore) {
            if (gameOver) {
                  return evalEnd();
            } else if (level <= 0) {
                  if (printOn) {
                        System.out.println("Min has ended");
                  }
                  return eval();
            }

            ArrayList<MyPoint> mvBuffer;
            int bestScore = Integer.MAX_VALUE;

            // for each human piece
            for (int i = 7; i < cont.length; i++) {
                  if (!(((Piece) cont[i]).captured)) {
                        mvBuffer = (genPieceMoves((Piece) cont[i]));
                        ArrayList<MyPoint> stableMvBuffer = new ArrayList<>();
                        stableMvBuffer.addAll(mvBuffer);
                        int size = stableMvBuffer.size();

                        // for every piece move
                        for (int j = 1; j < size; j++) {
                              MyPoint oldPoint = stableMvBuffer.get(0);
                              MyPoint newPoint = stableMvBuffer.get(j);

                              String str = moveP(oldPoint, newPoint);

                              // if there is a piece occupied there save it
                              // also save the old gas since it changes due to capture
                              int oldGas = oldGas = ((Piece) cont[b[oldPoint.x][oldPoint.y]]).gas;
                              int holder = 0;
                              // if there is a piece occupied there save it
                              if (b[newPoint.x][newPoint.y] != 0) {
                                    holder = b[newPoint.x][newPoint.y];
                              }

                              Piece p = (Piece) (cont[(b[oldPoint.x][oldPoint.y])]);

                              this.movePieceEC(str);

                              Boolean captured = p.capturedElse;

                              if (printOn) {
                                    System.out.println("made a move - " + str);
                                    printFullBoard();
                                    System.out.println("calling max...");
                              }

                              int score = max(level - 1, bestScore);

                              // update the current best score
                              if (score < bestScore) {
                                    bestScore = score;
                              }

                              // make sure you have enough gas to unmove it
                              p.gas++;

                              this.unMove(str);
                              // restore the move
                              b[newPoint.x][newPoint.y] = holder;

                              // retract the move
                              if (captured) {
                                    ((Piece) (cont[(b[newPoint.x][newPoint.y])])).captured = false;
                              }

                              if (printOn) {
                                    System.out.println("Retracting move...");
                                    printFullBoard();
                              }

                              p.gas = oldGas;

                              // alphabeta pruning here after retracting move
                              if (bestScore <= parentScore) {
                                    break;
                              }
                        }
                  }

            }

            return bestScore;
      }

      // takes a string move and reverses it
      public void unMove(String s) {
            String str = "";
            str += s.substring(2, 4);
            str += s.substring(0, 2);

            // make the reversed move
            movePieceEC(str);
      }

      // set of heuristics 
      public int eval() {
            int score = 0;

            // add points for every piece
            score -= 100 * countPlayerWorth();
            score += 100 * countCompWorth();

            // add points based on gas
            score -= 20 * sumPlayerGas();
            score += 20 * sumCompGas();

            // compensate gas heuristic if gas reaches zero
            if (printOn) {
                  System.out.println("Eval called");
                  System.out.println(score);

            }
            return score;
      }

      public boolean checkGameOver() {
            // [4] for computer, [10] for human
            return cont[4].captured || cont[10].captured;
      }

      public boolean checkGameOverGas(char c) {
            if (c == 'h') {
                  return sumPlayerGas() == 0;
            } else if (c == 'c') {
                  return sumCompGas() == 0;
            }
            return false;
      }

      // check who won/lost
      public int evalEnd() {
            // [4] for computer, [10] for human
            if (((Piece) cont[4]).captured) {
                  return -999999;
            } else if (((Piece) cont[10]).captured) {
                  return 999999;
            }
            return 0;
      }

      // go from point to string
      public String translateMove(MyPoint p) {
            String str = "";
            switch (p.y) {
                  case 0:
                        str += "a";
                        break;
                  case 1:
                        str += "b";
                        break;
                  case 2:
                        str += "c";
                        break;
                  case 3:
                        str += "d";
                        break;
                  case 4:
                        str += "e";
                        break;
                  case 5:
                        str += "f";
                        break;
                  case 6:
                        str += "g";
                        break;
                  case 7:
                        str += "h";
                        break;
            }

            switch (p.x) {
                  case 0:
                        str += "7";
                        break;
                  case 1:
                        str += "6";
                        break;
                  case 2:
                        str += "5";
                        break;
                  case 3:
                        str += "4";
                        break;
                  case 4:
                        str += "3";
                        break;
                  case 5:
                        str += "2";
                        break;
                  case 6:
                        str += "1";
                        break;
                  case 7:
                        str += "0";
                        break;
            }

            return str;
      }

      public void loop() {
            while (!gameOver) {
                  // with computer moves
                  if (playersTurn) {
                        while (true) {
                              if (checkGameOverGas('h')) {
                                    gameOver = true;
                                    break;
                              }
                              System.out.print("Make a move: ");
                              inputBuffer = scan.nextLine();
                              if (movePieceEC(inputBuffer)) {
                                    break;
                              } else if (inputBuffer.compareTo("c") == 0) {
                                    printController();
                              }
                        }
                        gameOver = checkGameOver();
                        playersTurn = !playersTurn;
                  } else {
                        if (checkGameOverGas('c')) {
                              gameOver = true;
                              break;
                        }

                        computerMove();
                        // after moving check for game over
                        gameOver = checkGameOver();
                        playersTurn = !playersTurn;
                  }

                  // no computer moves
//                  printFullBoard();
//                  this.genPlayerMoves('c');
//                  this.genPlayerMoves('h');
//                  System.out.println(this.genCompMoves());
//                  System.out.println(this.genHumMoves());
//                  System.out.print("Make a move: ");
//                  inputBuffer = scan.nextLine();
//                  movePieceEC(inputBuffer);
                  // udpate the gui after a move has been made
                  updateGUI(grid);
            }
            getWinner();
      }

      public void getWinner() {
            // computers's piece is catpured
            if (cont[10].captured) {
                  System.out.println("computer wins!");
                  printFullBoard();
            } // player's piece is catpured
            else if (cont[4].captured) {
                  System.out.println("player wins!");
                  printFullBoard();
            } else if (checkGameOverGas('h')) {
                  System.out.println("computer wins because player is out of gas!");
                  printFullBoard();
            } else if (checkGameOverGas('c')) {
                  System.out.println("player wins because computer is out of gas!");
                  printFullBoard();
            }
      }

      // move with number argument; convert points to string; ret string
      public String moveP(MyPoint p0, MyPoint p1) {
            String str = "";
            str += translateMove(p0);
            str += translateMove(p1);

            return str;

      }

      // need error checking with moves
      public boolean movePieceEC(String s) {
            // check for the controller
            if (s.compareTo("c") == 0 || s.compareTo("cg") == 0) {
                  System.out.println("script activated");
                  return false;
            }

            if (s.length() != 4) {
                  System.out.println("error - Invalid input length");
                  System.out.println();
                  return false;
            } else {

                  char c0 = Character.toLowerCase(s.charAt(0));
                  char c1 = (s.charAt(1));
                  char c2 = Character.toLowerCase(s.charAt(2));
                  char c3 = (s.charAt(3));

                  // check if it is the same spot
                  if (c0 == c2 && c1 == c3) {
                        System.out.println("error - Cannot move to the same place");
                        System.out.println();
                        return false;
                  }

                  if (!(c0 > 96 && c0 < 105) | !(c2 > 96 && c0 < 105)
                          | !(c1 >= 49 && c1 <= 55) | !(c3 >= 49 && c3 <= 55)) {
                        System.out.println("error - Improper input for one or more indices");
                        System.out.println();
                        return false;
                  }

                  // manipulate ascii values to get desired mappings to board
                  int i0 = -1 * (c1 - 55);
                  int j0 = c0 - 97;
                  int i1 = -1 * (c3 - 55);
                  int j1 = c2 - 97;

                  // checks to see if you are really moving a piece
                  if (b[i0][j0] != 0) {
                        // checks to see if there is enough gas to move it
                        if (((Piece) (cont[b[i0][j0]])).gas <= 0) {
                              System.out.println("error - Cannot move " + (Piece) (cont[b[i0][j0]]) + "without gas");
                              printFullBoard();

                              System.out.println();
                              return false;
                        }

                        Piece tmp = (Piece) cont[b[i0][j0]];

                        // check if the player made a valid move
                        MyPoint pendingMove = new MyPoint(i1, j1);
                        ArrayList<MyPoint> check = mvGen.generateMovesP(tmp, b);
                        if (!check.contains(pendingMove)) {
                              System.out.println(tmp.p + " to " + pendingMove + " Not a valid move for " + tmp.getID() + tmp.gas);
                              System.out.println();
                              return false;
                        }

                        // refill the gas if it captures another piece
                        tmp.capturedElse = b[i1][j1] != 0;

                        // adjust the gas
                        if (tmp.capturedElse) {
                              // delete the old piece
                              ((Piece) cont[b[i1][j1]]).captured = true;
                              tmp.gas = 3;
                        } else {
                              tmp.gas--;
                        }

                        // update the board
                        b[i1][j1] = b[i0][j0];
                        b[i0][j0] = 0;

                        // update position on the map & the piece values
                        tmp.p.x = i1;
                        tmp.p.y = j1;
                        genPieceMoves(tmp);
                        return true;

                  } else {
                        System.out.println("error - " + i0 + ", " + j0 + " That is not a piece on the board");
                        System.out.println();
                        return false;
                  }

            }
      }

      // this returns an array of each piece's move (which are arrays)
      public void genPlayerMoves(char ch) {

            if (ch == 'h') {
                  for (int i = 7; i < cont.length; i++) {
                        if (cont[i] instanceof Piece) {
                              if (((Piece) cont[i]).player == ch) {
                                    System.out.println(genPieceMoves((Piece) cont[i]));
                              }
                        }
                  }
            } else if (ch == 'c') {
                  for (int i = 1; i < 7; i++) {
                        if (cont[i] instanceof Piece) {
                              if (((Piece) cont[i]).player == ch) {
                                    System.out.println(genPieceMoves((Piece) cont[i]));
                              }

                        }
                  }
            }

            System.out.println();
      }

      public ArrayList<MyPoint> genPieceMoves(Piece p) {
            return mvGen.generateMovesP(p, b);
      }

      public void printController() {
            for (int i = 0; i < cont.length; i++) {
                  System.out.println(cont[i]);
            }
            System.out.println();
      }

      public void initBoard() {
            for (int i = 0; i < b.length; i++) {
                  for (int j = 0; j < b[i].length; j++) {
                        b[i][j] = 0;
                  }
            }
      }

      public void printBoard() {
            for (int i = 0; i < b.length; i++) {
                  for (int j = 0; j < b[i].length; j++) {
                        System.out.print(b[i][j] + "\t");
                  }
                  System.out.println();
            }
            System.out.println();
      }

      public void printFullBoard() {
            for (int i = 0; i < b.length; i++) {

                  // print out the left column of numbers
                  if (rows > 0) {
                        System.out.print(rows - i + " ");
                  }
                  // use the controller to print it out
                  for (int j = 0; j < b[i].length; j++) {
                        if (i == b[i].length) {
                              System.out.print(cont[b[i][j]] + com);
                        } else {

                        }
                        System.out.print(cont[b[i][j]]);
                  }

                  // display the computer or human before new line
                  if (i == 0) {
                        System.out.print(com);
                  }
                  if (i == 6) {
                        System.out.print(hum);
                  }
                  System.out.println();

            }
            System.out.println(line);
            System.out.println(chars);
            System.out.println();
//            printController();

      }

      public void initPieces() {
            // generate the pieces and add them to player's hand
            // this will be numbered 1 - 12 in order for the controller
            // then add the objects to the controller
            b[1][3] = 1;
            b[0][2] = 2;
            b[0][3] = 3;
            b[0][4] = 4;
            b[0][5] = 5;
            b[1][5] = 6;

            b[5][3] = 7;
            b[6][2] = 8;
            b[6][3] = 9;
            b[6][4] = 10;
            b[6][5] = 11;
            b[5][5] = 12;
//
            cont[0] = new Piece("0", 0, 0, 'z', 0);
            cont[0].captured = true;
            cont[1] = new Piece("N", 1, 3, 'c', 1);      // computer 1 - 7
            cont[2] = new Piece("B", 0, 2, 'c', 2);
            cont[3] = new Piece("Q", 0, 3, 'c', 3);
            cont[4] = new Piece("K", 0, 4, 'c', 4);
            cont[5] = new Piece("B", 0, 5, 'c', 5);
            cont[6] = new Piece("N", 1, 5, 'c', 6);

            cont[7] = new Piece("n", 5, 3, 'h', 7);      // human 7 - 13
            cont[8] = new Piece("b", 6, 2, 'h', 8);
            cont[9] = new Piece("q", 6, 3, 'h', 9);
            cont[10] = new Piece("k", 6, 4, 'h', 10);
            cont[11] = new Piece("b", 6, 5, 'h', 11);
            cont[12] = new Piece("n", 5, 5, 'h', 12);

            // used for gas testing
//            cont[1].gas = 0;
//            cont[2].gas = 0;
//            cont[3].gas = 0;
//            cont[4].gas = 0;
//            cont[5].gas = 2;
//            cont[6].gas = 0;
//            cont[7].gas = 0;
//            cont[8].gas = 0;
//            cont[9].gas = 1;
//            cont[10].gas = 0;
//            cont[11].gas = 0;
//            cont[12].gas = 0;
      }

      public static void main(String[] args) {
            new Main();
      }

}
