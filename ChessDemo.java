package chess;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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

        // create a black background
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

        // create a builder layer for the chess piece
        int xorigin = 510; // size of piece ~ 40 -> 500+(60-40)/2
        int yorigin = 210; // size of the piece ~ 40 -> 200+(60-40)/2

        // create a layer with the image file
        BufferedImage blackPawnImage = ChessGraphicTool.load(imagePath + "pawn-black.png");
        if (blackPawnImage == null) {
            System.out.println("Error image not found : " + imagePath + "pawn-black.png");
        }
        // create a dedicated layer and copy the new pawn image at the right coordinates
        xorigin = 510;
        yorigin = 330;
        BufferedImage blackPawnLayer = chessGraphicTool.createImage(blackPawnImage,
                width, height, xorigin, yorigin);

        // add the new layers
        mgrLayers.addLayer(blackPawnLayer);

        // The list of the images :
        // king-black.png knight-white.png queen-black.png rook-white.png
        // bishop-black.png king-white.png pawn-black.png queen-white.png
        // bishop-white.png knight-black.png pawn-white.png rook-black.png

        HashMap<String, Integer> piecesBlacks = new HashMap<>();
        piecesBlacks.put(new String("rook-black.png"), TypePiece.TOUR);
        piecesBlacks.put(new String("knight-black.png"), TypePiece.CAVALIER);
        piecesBlacks.put(new String("bishop-black.png"), TypePiece.FOU);
        piecesBlacks.put(new String("queen-black.png"), TypePiece.REINE);
        piecesBlacks.put(new String("king-black.png"), TypePiece.ROI);
        piecesBlacks.put(new String("bishop-black.png"), TypePiece.FOU);
        piecesBlacks.put(new String("knight-black.png"), TypePiece.CAVALIER);
        piecesBlacks.put(new String("rook-black.png"), TypePiece.TOUR);
        for (int i = 0; i < 8; i++) {
            piecesBlacks.put(new String("pawn-black.png"), TypePiece.PION);
        }

        // add all piece BLACK in the chess board in all squares
        int x_piece = 30;
        int y_piece = 30;
        for (int i = 0; i < piecesBlacks.size(); i++) {
            BufferedImage chessPieceImage = ChessGraphicTool.load(imagePath + piecesBlacks.keySet().toArray()[i]);
            if (chessPieceImage == null) {
                System.out.println("Error image not found : " + imagePath + piecesBlacks.keySet().toArray()[i]);
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

        ArrayList<String> chessPieceWHITE = new ArrayList<String>();
        for (int i = 0; i < 8; i++) {
            chessPieceWHITE.add(new String("pawn-white.png"));
        }
        chessPieceWHITE.add(new String("rook-white.png"));
        chessPieceWHITE.add(new String("knight-white.png"));
        chessPieceWHITE.add(new String("bishop-white.png"));
        chessPieceWHITE.add(new String("queen-white.png"));
        chessPieceWHITE.add(new String("king-white.png"));
        chessPieceWHITE.add(new String("bishop-white.png"));
        chessPieceWHITE.add(new String("knight-white.png"));
        chessPieceWHITE.add(new String("rook-white.png"));

        ArrayList<BufferedImage> chessPieceLayerWHITE = new ArrayList<BufferedImage>();
        ArrayList<BufferedImage> chessPieceImageWHITE = new ArrayList<BufferedImage>();

        // add all piece WHITE in the chess board in all squares
        x_piece = 30;
        y_piece = 630;
        for (int i = 0; i < chessPieceWHITE.size(); i++) {
            BufferedImage chessPieceImage = ChessGraphicTool.load(imagePath + chessPieceWHITE.get(i));
            if (chessPieceImage == null) {
                System.out.println("Error image not found : " + imagePath + chessPieceWHITE.get(i));
            }
            BufferedImage chessPieceLayer = chessGraphicTool.createImage(chessPieceImage,
                    width, height, x_piece, y_piece);
            chessPieceLayerWHITE.add(chessPieceLayer);
            chessPieceImageWHITE.add(chessPieceImage);
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
                System.out.println("Position x="+x+" y="+y);

            }

        }
    }
}
