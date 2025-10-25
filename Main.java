import java.util.Scanner;

public class Main {

    // ==========================
    // VARIABLES GLOBALES
    // ==========================
    static char[][] grid = new char[3][3];  // Grille du morpion

    // ==========================
    // FONCTION PRINCIPALE
    // ==========================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialisation de la grille avec des espaces vides
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = ' ';
            }
        }

        // Boucle principale de la partie (max 9 tours)
        for (int turn = 0; turn < 9; turn++) {

            displayGrid();  // Affiche la grille avant chaque tour

            // ==========================
            // TOUR DU JOUEUR 1
            // ==========================
            while (true) {
                System.out.println("Joueur 1 : Entrer les coordonnées souhaitées (x y entre 1 et 3) : ");
                int coordinateX = scanner.nextInt();
                int coordinateY = scanner.nextInt();

                if (checkWinner('0')) {
                    displayGrid();
                    System.out.println("Le gagnant est le joueur 1 !");
                    return; // Fin de la partie
                }

                if (grid[coordinateX - 1][coordinateY - 1] == ' ') {
                    grid[coordinateX - 1][coordinateY - 1] = '0';
                    break; // Case valide choisie
                } else {
                    System.out.println("Case déjà prise !");
                }
            }

            displayGrid(); // Affiche la grille après le tour du joueur

            // ==========================
            // TOUR DE L'IA (Joueur 2)
            // ==========================
            IA_Moove();
            if (checkWinner('X')) {
                displayGrid();
                System.out.println("Le gagnant est le joueur 2 ! (IA)");
                return; // Fin de la partie
            }
        }

        System.out.println("Match nul !");
    }

    // ==========================
    // AFFICHAGE DE LA GRILLE
    // ==========================
    static void displayGrid() {
        for (int i = 0; i < 3; i++) {
            System.out.println(grid[i][0] + " | " + grid[i][1] + " | " + grid[i][2]);
            if (i < 2) System.out.println("---------");
        }
    }

    // ==========================
    // VÉRIFICATION DU GAGNANT
    // ==========================
    static boolean checkWinner(char player) {
        // Vérifie les lignes et les colonnes
        for (int i = 0; i < 3; i++) {
            if ((grid[i][0] == player && grid[i][1] == player && grid[i][2] == player) ||
                (grid[0][i] == player && grid[1][i] == player && grid[2][i] == player)) {
                return true;
            }
        }

        // Vérifie les diagonales
        if ((grid[0][0] == player && grid[1][1] == player && grid[2][2] == player) ||
            (grid[0][2] == player && grid[1][1] == player && grid[2][0] == player)) {
            return true;
        }

        return false;
    }

    // Version pour le minimax (travaille sur un tableau passé en paramètre)
    static boolean checkWinnerAI(char[][] board, char player) {
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

    // ==========================
    // ALGORITHME MINIMAX
    // ==========================
    static int minimax(char[][] board, boolean isMaximizing) {
        if (checkWinnerAI(board, 'X')) return 10;
        if (checkWinnerAI(board, '0')) return -10;

        // Vérifie s'il reste des coups possibles
        boolean movesLeft = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') movesLeft = true;
            }
        }
        if (!movesLeft) return 0; // Match nul

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Parcours toutes les cases vides
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = isMaximizing ? 'X' : '0';
                    int score = minimax(board, !isMaximizing);
                    board[i][j] = ' ';

                    if (isMaximizing) {
                        bestScore = Math.max(score, bestScore);
                    } else {
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }

        return bestScore;
    }

    // ==========================
    // TOUR DE L'IA
    // ==========================
    static void IA_Moove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMooveX = -1;
        int bestMooveY = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == ' ') {
                    grid[i][j] = 'X';
                    int score = minimax(grid, false);
                    grid[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMooveX = i;
                        bestMooveY = j;
                    }
                }
            }
        }

        if (bestMooveX != -1 && bestMooveY != -1) {
            grid[bestMooveX][bestMooveY] = 'X';
        }
    }
}