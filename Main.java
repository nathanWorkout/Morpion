import java.util.Scanner;

public class Main {
    
    static char[][] grid = new char[3][3];
    
    public static void main(String[] args) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                grid[i][j] = ' ';
            }
        }        
        
        Scanner scanner = new Scanner(System.in);
        
        
        for(int turn = 0; turn < 9; turn++) {
            
            displayGrid();
            
            while(true){
            System.out.println("Joueur 1 : Entrer les coordonnées souhaitées(entre 1 et 3, x et y) : ");
            int coordinateX = scanner.nextInt(); 
            int coordinateY = scanner.nextInt();
            
            int movePlayer = grid[coordinateX - 1][coordinateY - 1];
            if(checkWinner('0')){
                displayGrid();
                System.out.println("Le gagnant est le joueur 1 !");
                break;
             }
                if(grid[coordinateX - 1][coordinateY - 1] == ' '){
                    grid[coordinateX - 1][coordinateY - 1] = '0';
                    break;
                }  else{
                    System.out.println("Case déja prise !");
                } 
                
            }       
            
            displayGrid();
            
            IA_Moove();
            if(checkWinner('X')){
                displayGrid();
                System.out.println("Le gagnant est le joueur 2 !(IA)");
                break;
            }
               
            
            
            
            
        
        }
    }
    
    static void displayGrid() {
        for(int i = 0; i < 3; i++) {
            System.out.println(grid[i][0] + " | " + grid[i][1] + " | " + grid[i][2]);
            if(i < 2) System.out.println("---------");
        }
    }
    
    
    static boolean checkWinner(char player) {
        
        for (int i = 0; i < 3; i++) {
            if ((grid[i][0] == player && grid[i][1] == player && grid[i][2] == player) ||
                (grid[0][i] == player && grid[1][i] == player && grid[2][i] == player)) {
                return true;
            }
        }
        
        if ((grid[0][0] == player && grid[1][1] == player && grid[2][2] == player) ||
            (grid[0][2] == player && grid[1][1] == player && grid[2][0] == player)) {
            return true;
        }
        return false;
    }
    
    
    static boolean checkWinnerAI(char[][] board, char player){
        
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }
        return false;
    }
    
    
    
    static int minimax(char[][] board, boolean isMaximizing){
        
        if(checkWinnerAI(board, 'X')) return 10;
        if(checkWinnerAI(board, '0')) return -10;
        
        boolean movesLeft = false;
        for(int i=0;i<3;i++) {
            for(int j=0; j<3; j++) {
                if(board[i][j] == ' ') movesLeft = true;
            }
        }
        if(!movesLeft) return 0;
        
        
        int bestScore;
        if(isMaximizing){
            bestScore = Integer.MIN_VALUE;
        } else {
            bestScore = Integer.MAX_VALUE;
        }
        
        
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(board[i][j]==' '){
                    if(isMaximizing == true) {
                        board[i][j] = 'X';
                    } else if(!isMaximizing){
                        board[i][j] = '0';
                    }
                    
                    int score = minimax(board, !isMaximizing);
                    board[i][j] = ' ';
                    
                    if(isMaximizing) {
                        bestScore = Math.max(score, bestScore);
                    } else{
                        bestScore = Math.min(score, bestScore);
                    }
                    
                    
                    
                }
                
            }
        }
        
        
        return bestScore;
    }
    
    
    
    static void IA_Moove(){
        //l'IA doit :
        //1 : parcourir les possibilités qu'il y a dans le teableau(dans minimax)
        //2 : voir si elle peut gagner en 1 coup(dans minimax)
        //3 : sinon choisir la meilleure option
        
        
        int bestScore = Integer.MIN_VALUE;
        int bestMooveX = -1;
        int bestMooveY= -1;
        
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if(grid[i][j] == ' ') {
                    grid[i][j] = 'X';
                    int score = minimax(grid, false);
                    grid[i][j] = ' ';
                    if(score>bestScore){
                       bestScore = score;
                       bestMooveX = i;
                       bestMooveY = j;
                   }
                }
            }
        }
        
        if(bestMooveX != -1 && bestMooveY != -1){
                    grid[bestMooveX][bestMooveY] = 'X';
                }
        
    }
    
    

}


