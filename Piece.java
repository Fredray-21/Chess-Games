package chess;

public class Piece extends TypePiece{

    protected int type;

    public String pieceImage;

    public Piece(int type, String pieceImage) {
        this.type = type;
        this.pieceImage = pieceImage;
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
}
