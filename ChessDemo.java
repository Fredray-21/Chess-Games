package chess;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static chess.TypePiece.getTypePiece;

// Modules used :
//import chess.ChessGUI;
//import chess.ChessMouseEvent;
//import chess.ChessGraphicTool;
//import chess.LayerManagement;

public class ChessDemo {

    public static void main(String[] args) {

        // Initialization
        int width = 1200;
        int height = 800;
        LayerManagement mgrLayers = new LayerManagement();
        mgrLayers.setPreferredSize(new Dimension(width, height));
        ChessGraphicTool chessGraphicTool = new ChessGraphicTool();
        String imagePath = new String("images/");

        ChessGUI.showOnFrame(mgrLayers, "Chess game");
        ChessGUI.setResizable(false);
        ChessMouseEvent chessMouseEvent = ChessGUI.getChessMouseEvent();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        BufferedImage emptyLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // create a GREY background
        BufferedImage foreground = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        mgrLayers.addLayer(foreground);
        Graphics2D foregroundGC = (Graphics2D) foreground.getGraphics();
        foregroundGC.setRenderingHints(rh);
        foregroundGC.setColor(Color.DARK_GRAY);
        foregroundGC.fill3DRect(0, 0, width, height, true);

        // drawing a chess board
        BufferedImage chessBoard = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        mgrLayers.addLayer(chessBoard);
        Graphics2D chessBoardGC = (Graphics2D) chessBoard.getGraphics();
        chessBoardGC.setColor(Color.WHITE);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    chessBoardGC.fill3DRect(i * 100, j * 100, 100, 100, true);
                }
            }
        }
        chessBoardGC.setColor(Color.BLACK);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 != 0) {
                    chessBoardGC.fill3DRect(i * 100, j * 100, 100, 100, true);
                }
            }
        }

        ArrayList<Piece> piecesBlack = new ArrayList<Piece>();
        piecesBlack.add(new Piece(TypePiece.TOUR, "rook-black.png"));
        piecesBlack.add(new Piece(TypePiece.CAVALIER, "knight-black.png"));
        piecesBlack.add(new Piece(TypePiece.FOU, "bishop-black.png"));
        piecesBlack.add(new Piece(TypePiece.REINE, "queen-black.png"));
        piecesBlack.add(new Piece(TypePiece.ROI, "king-black.png"));
        piecesBlack.add(new Piece(TypePiece.FOU, "bishop-black.png"));
        piecesBlack.add(new Piece(TypePiece.CAVALIER, "knight-black.png"));
        piecesBlack.add(new Piece(TypePiece.TOUR, "rook-black.png"));
        for (int i = 0; i < 8; i++) {
            piecesBlack.add(new Piece(TypePiece.PION, "pawn-black.png"));
        }

        for (Piece unePiece : piecesBlack) {
            System.out.println("URL Pièce : " + unePiece.getPieceImage() + " | Pièce Type : " + getTypePiece(unePiece.getType()));
        }

        // add all piece BLACK in the chess board in all squares
        int x_piece = 30;
        int y_piece = 30;
        for (int i = 0; i < piecesBlack.size(); i++) {
            BufferedImage chessPieceImage = ChessGraphicTool.load(imagePath + piecesBlack.get(i).getPieceImage());
            if (chessPieceImage == null) {
                System.out.println("Error image not found : " + imagePath + piecesBlack.get(i).getPieceImage());
            }
            BufferedImage chessPieceLayer = chessGraphicTool.createImage(chessPieceImage,
                    width, height, x_piece, y_piece);
            // ici possible set data
            mgrLayers.addLayer(chessPieceLayer);
            x_piece += 100;
            if (x_piece >= 800) {
                x_piece = 30;
                y_piece += 100;
            }
        }

        ArrayList<Piece> piecesWhite = new ArrayList<Piece>();
        for (int i = 0; i < 8; i++) {
            piecesWhite.add(new Piece(TypePiece.PION, "pawn-white.png"));
        }
        piecesWhite.add(new Piece(TypePiece.TOUR, "rook-white.png"));
        piecesWhite.add(new Piece(TypePiece.CAVALIER, "knight-white.png"));
        piecesWhite.add(new Piece(TypePiece.FOU, "bishop-white.png"));
        piecesWhite.add(new Piece(TypePiece.REINE, "queen-white.png"));
        piecesWhite.add(new Piece(TypePiece.ROI, "king-white.png"));
        piecesWhite.add(new Piece(TypePiece.FOU, "bishop-white.png"));
        piecesWhite.add(new Piece(TypePiece.CAVALIER, "knight-white.png"));
        piecesWhite.add(new Piece(TypePiece.TOUR, "rook-white.png"));


        for (Piece unePiece : piecesWhite) {
            System.out.println("URL Pièce : " + unePiece.getPieceImage() + " | Pièce Type : " + getTypePiece(unePiece.getType()));
        }

        // add all piece WHITE in the chess board in all squares
        x_piece = 30;
        y_piece = 630;
        for (int i = 0; i < piecesWhite.size(); i++) {
            BufferedImage chessPieceImage = ChessGraphicTool.load(imagePath + piecesWhite.get(i).getPieceImage());
            if (chessPieceImage == null) {
                System.out.println("Error image not found : " + imagePath + piecesWhite.get(i).getPieceImage());
            }
            BufferedImage chessPieceLayer = chessGraphicTool.createImage(chessPieceImage,
                    width, height, x_piece, y_piece);
            mgrLayers.addLayer(chessPieceLayer);
            x_piece += 100;
            if (x_piece >= 800) {
                x_piece = 30;
                y_piece += 100;
            }
        }
        // display all the layers
        mgrLayers.repaint();


        // -- main loop waiting drag and drop user events

        int counter = 0;
        while (true) {
            // waiting in millisecond
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (chessMouseEvent.drag() == true) {
                int x = chessMouseEvent.getX();
                int y = chessMouseEvent.getY();
                System.out.println("Position x=" + x + " y=" + y);

            }
        }
    }
}
