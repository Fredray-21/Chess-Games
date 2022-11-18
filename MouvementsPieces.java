package chess;

import java.util.ArrayList;

public class MouvementsPieces {

    public static ArrayList<int[]> getMouvements(Piece piece) {
        ArrayList<int[]> mouvements = new ArrayList<>(); // Liste des mouvements possibles
        switch (piece.getType()) {
            case 0 -> {
                mouvements.add(new int[]{0, -1});
                mouvements.add(new int[]{0, -2});
            }
            case 1 -> {
                for (int i = 1; i <= 8; i++) {
                    mouvements.add(new int[]{i, 0});
                    mouvements.add(new int[]{0, i});
                    mouvements.add(new int[]{-i, 0});
                    mouvements.add(new int[]{0, -i});
                }
            }
            case 2 -> {
                mouvements.add(new int[]{1, 2});
                mouvements.add(new int[]{2, 1});
                mouvements.add(new int[]{-1, 2});
                mouvements.add(new int[]{-2, 1});
                mouvements.add(new int[]{1, -2});
                mouvements.add(new int[]{2, -1});
                mouvements.add(new int[]{-1, -2});
                mouvements.add(new int[]{-2, -1});
            }
            case 3 -> {
                for (int i = 1; i <= 8; i++) {
                    mouvements.add(new int[]{i, i});
                    mouvements.add(new int[]{-i, i});
                    mouvements.add(new int[]{i, -i});
                    mouvements.add(new int[]{-i, -i});
                }
            }
            case 4 -> {
                for (int i = 1; i <= 8; i++) {
                    mouvements.add(new int[]{i, 0});
                    mouvements.add(new int[]{0, i});
                    mouvements.add(new int[]{-i, 0});
                    mouvements.add(new int[]{0, -i});
                    mouvements.add(new int[]{i, i});
                    mouvements.add(new int[]{-i, i});
                    mouvements.add(new int[]{i, -i});
                    mouvements.add(new int[]{-i, -i});
                }
            }
            case 5 -> {
                mouvements.add(new int[]{0, 1});
                mouvements.add(new int[]{0, -1});
                mouvements.add(new int[]{1, 0});
                mouvements.add(new int[]{-1, 0});
                mouvements.add(new int[]{1, 1});
                mouvements.add(new int[]{-1, -1});
                mouvements.add(new int[]{1, -1});
                mouvements.add(new int[]{-1, 1});
                return mouvements;
            }
            default -> {
                return mouvements;
            }
        }
        return mouvements;
    }
}
