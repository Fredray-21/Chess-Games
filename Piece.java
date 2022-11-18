package chess;

public class Piece extends TypePiece{

    protected int type;
    protected int[] position;

    public String pieceImage;

    public Piece(int type, String pieceImage, int[] position) {
        this.type = type;
        this.pieceImage = pieceImage;
        this.position = position;

    }

    public String getPieceImage() {
        return pieceImage;
    }

    public int getType() {
        return type;
    }

    public String toString() {
        return "Piece [pieceImage=" + pieceImage + ", type=" + type + "]";
    }

    public int[] getPosition() {
        return new int[]{position[0], position[1]};
    }

    public void setPosition(int[] ints) {
        this.position = ints;
    }

    public String getPieceColor() {
        if (pieceImage.contains("white")) {
            return "white";
        } else {
            return "black";
        }
    }
}
