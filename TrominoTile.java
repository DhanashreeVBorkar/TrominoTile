import java.util.Scanner;

public class TrominoTile{
    static int[][] board;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter size of board (need to be 2^n and 0 to quit): ");
            int board_size  =0;
            try{
             board_size  = scanner.nextInt();
            }catch(java.util.InputMismatchException  e){
                System.out.println("Invalid input, please enter integer number as a input. Exiting...\n");
                System.exit(1);
                break;
            }
            if (board_size  == 0) {
                System.out.print("Exiting...\n");
                break;
            }
             if (!isPowerOfTwo(board_size )){
                System.out.print("The input for the board size should be 2^n (the length of the side is 2^n), not "+board_size +". Exiting ... \n");
                
                System.exit(1);
                break;
             }

            

            board = new int[board_size ][board_size ];

            int missingX = 0;
            int missingY = 0;
            System.out.print("Please enter coordinates of missing square (seperated by a space): ");
            try{
             missingX = scanner.nextInt();
             missingY = scanner.nextInt();
            }catch(java.util.InputMismatchException  e){
                System.out.println("Invalid input, please enter integer number as a input. Exiting... \n");
                System.exit(1);
                break;
            }
            int boardlimit = board_size -1;

            if(missingX >=0 && missingY >=0 && missingX <= boardlimit && missingY <= boardlimit ){
                tromino(0, 0, missingX, missingY, board_size );
            }else{
                System.out.print("Invalid input. Please input missing square in a range from 0 to "+boardlimit+ ", as board_size  is "+board_size +" Exiting... \n");
                System.exit(1);
                break;
            }

            

            // Print the resulting tromino arrangement
            for (int i = board_size  - 1; i >= 0; i--) {
                for (int j = 0; j < board_size ; j++) {
                   // System.out.print(board[j][i]);
                    if (j == missingX && i == missingY) {
                        System.out.print("MS ");
                    } else if (board[j][i] == 1) {
                        System.out.print("LR ");
                    } else if (board[j][i] == 2) {
                        System.out.print("LL ");
                    } else if (board[j][i] == 3) {
                        System.out.print("UR ");
                    } else if (board[j][i] == 4) {
                        System.out.print("UL ");
                    }
                }
                System.out.println();
            }
        }
    }

public static boolean isPowerOfTwo(int n) {
    if (n <= 0) {
        return false;
    }
    return (n & (n - 1)) == 0;
}
   public static void tromino(int x_board, int y_board, int x_missing, int y_missing, int board_size ) {
    if (board_size  == 2) {
        int startX = x_board;
        int startY = y_board;

        if (x_missing == startX && y_missing == startY + 1) { // Lower Right
            board[startX][startY] = 1;
            board[startX+1][startY] = 1;
            board[startX+1][startY+1] = 1;
        } else if (x_missing == startX + 1 && y_missing == startY + 1) { // Lower Left
            board[startX][startY] = 2;
            board[startX+1][startY] = 2;
            board[startX][startY+1] = 2;
        } else if (x_missing == startX && y_missing == startY) { // Upper Right
            board[startX][startY+1] = 3;
            board[startX+1][startY] = 3;
            board[startX+1][startY+1] = 3;
        } else if (x_missing == startX + 1 && y_missing == startY) { // Upper Left
            board[startX][startY] = 4;
            board[startX][startY+1] = 4;
            board[startX+1][startY+1] = 4;
        }
    } else {
       
        int halfSize = board_size  / 2;

        // Determine which quadrant the missing square is in
        int quadrant = 0;
        if (x_missing >= x_board + halfSize) {
            quadrant += 1;
        }
        if (y_missing >= y_board + halfSize) {
            quadrant += 2;
        }

        // Update the missing square coordinates based on the quadrant and call tromino
        if(quadrant == 0)
        {
            //if quadrant is 0 Then define coordinates for the opposite quadrant. here 3 (UR = 3)
            //define coordinates of 4 central tiles
            // 2nd quadrant
            int x_missingUpperLeft = x_board + halfSize - 1;
            int y_missingUpperLeft = y_board + halfSize;
            // 3rd quadrant
            int x_missingUpperRight = x_board + halfSize;
            int y_missingUpperRight = y_board + halfSize;
            // 1st quadrant
            int x_missingLowerRight = x_board + halfSize;
            int y_missingLowerRight = y_board + halfSize - 1;
       
            board[x_missingUpperRight][y_missingUpperRight] = 3; //new missing in 3rd quadrant
            tromino(x_board + halfSize, y_board + halfSize, x_missingUpperRight, y_missingUpperRight, halfSize); //conquer 3rd quadrant
           
            board[x_missingUpperLeft][y_missingUpperLeft] = 3; //new missing in 2nd quadrant
            tromino(x_board, y_board + halfSize, x_missingUpperLeft, y_missingUpperLeft, halfSize); //conquer 2nd quadrant
           
            board[x_missingLowerRight][y_missingLowerRight] = 3; //new missing in 1st quadrant
            tromino(x_board + halfSize, y_board, x_missingLowerRight, y_missingLowerRight, halfSize);
           
            tromino(x_board, y_board, x_missing, y_missing, halfSize); // new missing in 1st quadrant
        }
       
       
        if(quadrant == 1)
        {
            x_board = x_board + halfSize;
            y_board = y_board;
           
            //if quadrant is 1 Then define coordinates for the opposite quadrant. here 2 (UL = 4)
             // 2nd quadrant
            int x_missingUpperLeft = x_board - 1;
            int y_missingUpperLeft = y_board + halfSize;
            // 3rd quadrant
            int x_missingUpperRight = x_board;
            int y_missingUpperRight = y_board + halfSize;
            // 0th quadrant
            int x_missingLowerLeft = x_board - 1;
            int y_missingLowerLeft = y_board + halfSize - 1;
           
            board[x_missingUpperRight][y_missingUpperRight] = 4; //new missing in 3rd quadrant
            tromino(x_board, y_board + halfSize, x_missingUpperRight, y_missingUpperRight, halfSize);
           
            board[x_missingUpperLeft][y_missingUpperLeft] = 4; //new missing in 2nd quadrant
            tromino(x_board - halfSize, y_board + halfSize, x_missingUpperLeft, y_missingUpperLeft, halfSize);
           
            board[x_missingLowerLeft][y_missingLowerLeft] = 4; //new missing in 0 quadrant
            tromino(x_board - halfSize, y_board, x_missingLowerLeft, y_missingLowerLeft, halfSize);
           
            tromino(x_board, y_board, x_missing, y_missing, halfSize); // new missing in 1st quadrant
        }
       
       
        if(quadrant == 2)
        {
           
            x_board = x_board;
            y_board = y_board + halfSize;
           
            // if quadrant is 2 then define coordinates for the opposite quadrant. here 1 (LR = 1)
            // 1st quadrant
            int x_missingLowerRight = x_board + halfSize;
            int y_missingLowerRight = y_board - 1;
            // 3rd quadrant
            int x_missingUpperRight = x_board + halfSize;
            int y_missingUpperRight = y_board;
            // 0th quadrant
            int x_missingLowerLeft = x_board + halfSize -1;
            int y_missingLowerLeft = y_board - 1;
           
            board[x_missingLowerRight][y_missingLowerRight] = 1; //new missing in 1st quadrant
            tromino(x_board + halfSize, y_board - halfSize, x_missingLowerRight, y_missingLowerRight, halfSize);
           
            board[x_missingUpperRight][y_missingUpperRight] = 1; //new missing in 3rd quadrant
            tromino(x_board + halfSize, y_board, x_missingUpperRight, y_missingUpperRight, halfSize);
           
            board[x_missingLowerLeft][y_missingLowerLeft] = 1; //new missing in 0 quadrant
            tromino(x_board, y_board - halfSize, x_missingLowerLeft, y_missingLowerLeft, halfSize);
           
            tromino(x_board, y_board, x_missing, y_missing, halfSize); // new missing in 2nd quadrant
        }
       
        if(quadrant == 3)
        {
           
            x_board = x_board + halfSize;
            y_board = y_board + halfSize;
           
            //if quadrant is 3 Then define coordinates for the opposite quadrant. here 0 (LL= 2)
             // 1st quadrant
            int x_missingLowerRight = x_board;
            int y_missingLowerRight = y_board - 1;
            // 2nd quadrant
            int x_missingUpperLeft = x_board - 1;
            int y_missingUpperLeft = y_board;
            // 0th quadrant
            int x_missingLowerLeft = x_board - 1;
            int y_missingLowerLeft = y_board - 1;
           
            board[x_missingLowerRight][y_missingLowerRight] = 2; //new missing in 1st quadrant
            tromino(x_board, y_board - halfSize, x_missingLowerRight, y_missingLowerRight, halfSize);
           
            board[x_missingUpperLeft][y_missingUpperLeft] = 2; //new missing in 2nd quadrant
            tromino(x_board - halfSize, y_board, x_missingUpperLeft, y_missingUpperLeft, halfSize);
           
            board[x_missingLowerLeft][y_missingLowerLeft] = 2; //new missing in 0 quadrant
            tromino(x_board - halfSize, y_board - halfSize, x_missingLowerLeft, y_missingLowerLeft, halfSize);
           
            tromino(x_board, y_board, x_missing, y_missing, halfSize); // new missing in 3rd quadrant
        }
       
    }
   }
}