package chess;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// Modules used :
//import chess.ChessGUI;
//import chess.ChessMouseEvent;
//import chess.ChessGraphicTool;
//import chess.LayerManagement;

public class ChessDemo {

    public static int pieceSize = 60;
    public static int piecePosition = (100 - pieceSize) / 2;
    public static String colorYellow = "#f7f769";
    public static String colorRed = "#ec7d6a";
    public static String colorGreen = "#769656";

    public static void main(String[] args) {

        // Initialization
        int width = 1200;
        int height = 800;

        LayerManagement mgrLayers = new LayerManagement();
        mgrLayers.setPreferredSize(new Dimension(width, height));
        String imagePath = "images/";

        ChessGUI.showOnFrame(mgrLayers, "CHESS GAME - Echec & Math.. ématique ?? hihi");
        ChessGUI.setIconPath("images/icon_chess.jpg");
        ChessGUI.setResizable(false);
        ChessMouseEvent chessMouseEvent = ChessGUI.getChessMouseEvent();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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
        chessBoardGC.setColor(Color.decode(colorGreen));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 != 0) {
                    chessBoardGC.fill3DRect(i * 100, j * 100, 100, 100, true);
                }
            }
        }


        ArrayList<Piece> Pieces = new ArrayList<>();
        Pieces.add(new Piece(TypePiece.TOUR, "rook-black.png", new int[]{0, 0}));
        Pieces.add(new Piece(TypePiece.CAVALIER, "knight-black.png", new int[]{1, 0}));
        Pieces.add(new Piece(TypePiece.FOU, "bishop-black.png", new int[]{2, 0}));
        Pieces.add(new Piece(TypePiece.REINE, "queen-black.png", new int[]{3, 0}));
        Pieces.add(new Piece(TypePiece.ROI, "king-black.png", new int[]{4, 0}));
        Pieces.add(new Piece(TypePiece.FOU, "bishop-black.png", new int[]{5, 0}));
        Pieces.add(new Piece(TypePiece.CAVALIER, "knight-black.png", new int[]{6, 0}));
        Pieces.add(new Piece(TypePiece.TOUR, "rook-black.png", new int[]{7, 0}));
        for (int i = 0; i < 8; i++) {
            Pieces.add(new Piece(TypePiece.PION, "pawn-black.png", new int[]{i, 1}));
        }
        Pieces.add(new Piece(TypePiece.TOUR, "rook-white.png", new int[]{0, 7}));
        Pieces.add(new Piece(TypePiece.CAVALIER, "knight-white.png", new int[]{1, 7}));
        Pieces.add(new Piece(TypePiece.FOU, "bishop-white.png", new int[]{2, 7}));
        Pieces.add(new Piece(TypePiece.REINE, "queen-white.png", new int[]{3, 7}));
        Pieces.add(new Piece(TypePiece.ROI, "king-white.png", new int[]{4, 7}));
        Pieces.add(new Piece(TypePiece.FOU, "bishop-white.png", new int[]{5, 7}));

        Pieces.add(new Piece(TypePiece.CAVALIER, "knight-white.png", new int[]{6, 7}));
        Pieces.add(new Piece(TypePiece.TOUR, "rook-white.png", new int[]{7, 7}));
        for (int i = 0; i < 8; i++) {
            Pieces.add(new Piece(TypePiece.PION, "pawn-white.png", new int[]{i, 6}));
        }

        // add all piece in the chess board in a square with position (x,y)
        for (Piece unePiece : Pieces) {
            BufferedImage piece = ChessGraphicTool.load(imagePath + unePiece.getPieceImage());
            chessBoardGC.drawImage(piece, unePiece.getPosition()[0] * 100 + piecePosition, unePiece.getPosition()[1] * 100 + piecePosition, pieceSize, pieceSize, null);
        }

        // display all the layers
        mgrLayers.repaint();


        // -- main loop waiting drag and drop user events

        int[] oldPosition = null;
        ArrayList<int[]> oldMouvement = null;
        boolean isPieceCliked = false;
        ArrayList<int[]> mouvementsJaune = new ArrayList<>();
        Piece pieceClikedForMouvement = new Piece();

        while (true) {
            // waiting in millisecond
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (isPieceCliked) {
                if (chessMouseEvent.click()) {
                    int x = chessMouseEvent.getX();
                    int y = chessMouseEvent.getY();
                    int x_currentSquare = x / 100;
                    int y_currentSquare = y / 100;
                    int[] newPosition = null;

                    for (int[] mouvement : mouvementsJaune) {

                        if (mouvement[0] + pieceClikedForMouvement.getPosition()[0] == x_currentSquare && mouvement[1] + pieceClikedForMouvement.getPosition()[1] == y_currentSquare) {

                            if ((pieceClikedForMouvement.getPosition()[0] + pieceClikedForMouvement.getPosition()[1]) % 2 == 0) {
                                chessBoardGC.setColor(Color.WHITE);
                            } else {
                                chessBoardGC.setColor(Color.decode(colorGreen));
                            }
                            chessBoardGC.fill3DRect(pieceClikedForMouvement.getPosition()[0] * 100, pieceClikedForMouvement.getPosition()[1] * 100, 100, 100, true);


                            newPosition = new int[]{mouvement[0] + pieceClikedForMouvement.getPosition()[0], mouvement[1] + pieceClikedForMouvement.getPosition()[1]};
                            pieceClikedForMouvement.setPosition(newPosition);

                            BufferedImage piece = ChessGraphicTool.load(imagePath + pieceClikedForMouvement.getPieceImage());
                            chessBoardGC.drawImage(piece, pieceClikedForMouvement.getPosition()[0] * 100 + piecePosition, pieceClikedForMouvement.getPosition()[1] * 100 + piecePosition, pieceSize, pieceSize, null);

                        }

                    }

                    System.out.println(mouvementsJaune.size() > 0);
                    System.out.println(chessMouseEvent.click());
                    System.out.println(newPosition == null);
                    System.out.println(isPieceCliked == false);


                    if (mouvementsJaune.size() > 0  && newPosition == null) {
                        isPieceCliked = true;
                    } else {
                    chessBoardGC.setColor(Color.WHITE);
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if ((i + j) % 2 == 0) {
                                chessBoardGC.fill3DRect(i * 100, j * 100, 100, 100, true);
                            }
                        }
                    }
                    chessBoardGC.setColor(Color.decode(colorGreen));
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if ((i + j) % 2 != 0) {
                                chessBoardGC.fill3DRect(i * 100, j * 100, 100, 100, true);
                            }
                        }
                    }

                    for (Piece unePiece : Pieces) {
                        BufferedImage piece = ChessGraphicTool.load(imagePath + unePiece.getPieceImage());
                        chessBoardGC.drawImage(piece, unePiece.getPosition()[0] * 100 + piecePosition, unePiece.getPosition()[1] * 100 + piecePosition, pieceSize, pieceSize, null);
                    }
                        isPieceCliked = false;
                        mouvementsJaune.clear();
                        pieceClikedForMouvement = new Piece();
                    }
                }

                System.out.println("isPieceCliked:" + isPieceCliked);
                mgrLayers.repaint();
            }


            if (chessMouseEvent.click() && isPieceCliked == false) {
                int x = chessMouseEvent.getX();
                int y = chessMouseEvent.getY();
                int x_currentSquare = x / 100;
                int y_currentSquare = y / 100;


                // si l'user ne re click pas sur la meme piece
                if (oldPosition == null || oldPosition[0] != x_currentSquare || oldPosition[1] != y_currentSquare) {
                    if (x < 800 && y < 800) { // chess board
                        // find if the piece is in the square clicked
                        for (Piece unePiece : Pieces) {
                            if (unePiece.getPosition()[0] == x_currentSquare && unePiece.getPosition()[1] == y_currentSquare && unePiece.getPieceColor().equals("white")) {

                                isPieceCliked = true;
                                pieceClikedForMouvement = unePiece;

                                // default color for the square where old position was before the click and oldMouvement was before the click
                                if (oldMouvement != null && oldPosition != null) {
                                    for (int[] mouvement : oldMouvement) {
                                        if (mouvement[0] + oldPosition[0] < 8 && mouvement[1] + oldPosition[1] < 8) {
                                            if ((mouvement[0] + oldPosition[0] + mouvement[1] + oldPosition[1]) % 2 == 0) {
                                                chessBoardGC.setColor(Color.WHITE);
                                            } else {
                                                chessBoardGC.setColor(Color.decode(colorGreen));
                                            }
                                            chessBoardGC.fill3DRect((mouvement[0] + oldPosition[0]) * 100, (mouvement[1] + oldPosition[1]) * 100, 100, 100, true);
                                        }
                                    }
                                    mgrLayers.repaint();
                                }

                                ArrayList<int[]> mouvements = MouvementsPieces.getMouvements(unePiece);
                                for (int[] mouvement : mouvements) {
                                    if (mouvement[0] + x_currentSquare < 8 && mouvement[1] + y_currentSquare < 8) {

                                        // TODO : regarder si le mouvement est possible (collision avec une autre piece)
                                        // 1. si oui afficher le mouvement en vert
                                        // 2. Si non, car il y a une piece qui bloque le mouvement ne pas afficher le mouvement

                                        // regarder les pieces au tour de la piece selectionnee pour voir si il y a une piece qui bloque le mouvement
                                        boolean mouvementPossible = true;
                                        for (Piece piece : Pieces) {
                                            if (piece.getPosition()[0] == mouvement[0] + x_currentSquare && piece.getPosition()[1] == mouvement[1] + y_currentSquare) {
                                                mouvementPossible = false;
                                            }
                                        }


                                        if (!mouvementPossible) {
                                            chessBoardGC.setColor(Color.decode(colorRed));
                                            chessBoardGC.fill3DRect((mouvement[0] + x_currentSquare) * 100, (mouvement[1] + y_currentSquare) * 100, 100, 100, true);
                                        } else {
                                            mouvementsJaune.add(mouvement);
                                            chessBoardGC.setColor(Color.decode(colorYellow));
                                            chessBoardGC.fill3DRect((mouvement[0] + x_currentSquare) * 100, (mouvement[1] + y_currentSquare) * 100, 100, 100, true);
                                        }
                                    }


                                    // get the piece the image and draw it in square with position (x,y)
                                    for (Piece PieceMovement : Pieces) {
                                        if (PieceMovement.getPosition()[0] == mouvement[0] + x_currentSquare && PieceMovement.getPosition()[1] == mouvement[1] + y_currentSquare) {
                                            BufferedImage piece = ChessGraphicTool.load(imagePath + PieceMovement.getPieceImage());
                                            chessBoardGC.drawImage(piece, PieceMovement.getPosition()[0] * 100 + piecePosition, PieceMovement.getPosition()[1] * 100 + piecePosition, pieceSize, pieceSize, null);
                                        }
                                    }
                                }

                                // re draw the older piece in the square old position
                                if (oldMouvement != null) {
                                    for (int[] mouvement : oldMouvement) {
                                        for (Piece unePiece2 : Pieces) {
                                            if (unePiece2.getPosition()[0] == mouvement[0] + oldPosition[0] && unePiece2.getPosition()[1] == mouvement[1] + oldPosition[1]) {
                                                BufferedImage piece2 = ChessGraphicTool.load(imagePath + unePiece2.getPieceImage());
                                                chessBoardGC.drawImage(piece2, (mouvement[0] + oldPosition[0]) * 100 + piecePosition, (mouvement[1] + oldPosition[1]) * 100 + piecePosition, pieceSize, pieceSize, null);
                                            }
                                        }
                                    }
                                }
                                mgrLayers.repaint();
                                oldPosition = new int[]{x_currentSquare, y_currentSquare};
                                oldMouvement = mouvements;
                            }
                        }
                    }
                }
            }

        }
    }
}