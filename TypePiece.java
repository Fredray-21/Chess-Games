package chess;

public class TypePiece {

    public static int PION = 0;
    public static final int TOUR = 1;
    public static final int CAVALIER = 2;
    public static final int FOU = 3;
    public static final int REINE = 4;
    public static final int ROI = 5;


    public static String getTypePiece(int type) {
        switch (type) {
            case 0 -> {
                return "PION";
            }
            case 1 -> {
                return "TOUR";
            }
            case 2 -> {
                return "CAVALIER";
            }
            case 3 -> {
                return "FOU";
            }
            case 4 -> {
                return "REINE";
            }
            case 5 -> {
                return "ROI";
            }
            default -> {
                return "UNKNOWN";
            }
        }
    }
}
