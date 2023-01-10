package chess;

import javax.swing.*;
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

    public static Boolean isWhite = true;

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

        int[] oldPosition = null; // anciene position de la piece
        ArrayList<int[]> oldMouvement = null; // ancien mouvement de la piece
        ArrayList<int[]> mouvementsJaune = new ArrayList<>(); // mouvement possible de la piece
        ArrayList<int[]> mouvementsRouge = new ArrayList<>(); // mouvement possible eat de la piece
        Piece currentPiece = new Piece(); // piece selectionné
        ArrayList<int[]> ListPositionMoveJaunes = new ArrayList<>(); // liste des positions possibles en jaune
        ArrayList<int[]> ListPositionMoveRouges = new ArrayList<>(); // liste des positions possibles en rouge
        boolean pieceJustMoved = false; // si la piece vient d'etre bougé
        int viesNoir = 3;
        int viesBlanc = 3;
        int[] positionPieceWhiteEat = new int[]{8, 0}; // position de la prochaine piece blanche mangé
        int[] positionPieceBlackEat = new int[]{8, 7}; // position de la prochaine piece noir mangé

        while (true) {
            // waiting in millisecond
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (chessMouseEvent.click()) {
                int x = chessMouseEvent.getX();
                int y = chessMouseEvent.getY();
                int x_currentSquare = x / 100;
                int y_currentSquare = y / 100;

                // si la piece vient d'etre bougé
                ListPositionMoveJaunes.clear(); // on vide la liste des positions possible
                ListPositionMoveRouges.clear(); // on vide la liste des positions possible
                for (int[] mouvementJ : mouvementsJaune) { // on parcours les mouvements possibles Jaune
                    int[] move = new int[]{mouvementJ[0] + oldPosition[0], mouvementJ[1] + oldPosition[1]}; // on transforme les mouvements jaunes en position possible
                    ListPositionMoveJaunes.add(move);
                }

                for (int[] mouvementR : mouvementsRouge) {
                    int[] move = new int[]{mouvementR[0] + oldPosition[0], mouvementR[1] + oldPosition[1]}; // on transforme les mouvements rouges en position possible
                    ListPositionMoveRouges.add(move);
                }

                for (int[] move : ListPositionMoveJaunes) {
                    if (move[0] == x_currentSquare && move[1] == y_currentSquare) { // if user click on a mouvement square
                        // move the piece
                        currentPiece.setPosition(new int[]{x_currentSquare, y_currentSquare});

                        // remove the old piece
                        if ((oldPosition[0] + oldPosition[1]) % 2 == 0) {
                            chessBoardGC.setColor(Color.WHITE);
                        } else {
                            chessBoardGC.setColor(Color.decode(colorGreen));
                        }
                        chessBoardGC.fill3DRect(oldPosition[0] * 100, oldPosition[1] * 100, 100, 100, true);
                        pieceJustMoved = true; // set flag to indicate that a piece has just been moved
                        break;
                    }
                }


                for (int[] move : ListPositionMoveRouges) { // si on a cliqué sur une case rouge
                    if (move[0] == x_currentSquare && move[1] == y_currentSquare) { // if user click on a mouvement square
                        // get the piece to remove
                        for (Piece unePiece : Pieces) {
                            if (unePiece.getPosition()[0] == x_currentSquare && unePiece.getPosition()[1] == y_currentSquare) {
                                if (unePiece.getPieceColor().equals("black")) {
                                    unePiece.setPosition(new int[]{positionPieceBlackEat[0], positionPieceBlackEat[1]}); // set the position of the piece to remove out of the board
                                    if (positionPieceBlackEat[0] == 11) { // si on est sur la premiere ligne
                                        positionPieceBlackEat[0] = 8; // on remet la position a 8
                                        positionPieceBlackEat[1] = positionPieceBlackEat[1] - 1; // on passe a la ligne précédente
                                    } else {
                                        positionPieceBlackEat[0] = positionPieceBlackEat[0] + 1; // on passe a la colonne suivante
                                    }
                                } else {
                                    unePiece.setPosition(new int[]{positionPieceWhiteEat[0], positionPieceWhiteEat[1]}); // set the position of the piece to remove out of the board
                                    if (positionPieceWhiteEat[0] == 11) { // si on est sur la premiere ligne
                                        positionPieceWhiteEat[0] = 8; // on remet la position a 8
                                        positionPieceWhiteEat[1] = positionPieceWhiteEat[1] + 1; // on passe a la ligne suivante
                                    } else {
                                        positionPieceWhiteEat[0] = positionPieceWhiteEat[0] + 1; // on passe a la colonne suivante
                                    }
                                }

                                BufferedImage piece = ChessGraphicTool.load(imagePath + unePiece.getPieceImage());
                                chessBoardGC.drawImage(piece, unePiece.getPosition()[0] * 100 + piecePosition, unePiece.getPosition()[1] * 100 + piecePosition, pieceSize, pieceSize, null);
                                break;
                            }
                        }

                        // move the piece
                        currentPiece.setPosition(new int[]{x_currentSquare, y_currentSquare});

                        // remove the old piece
                        if ((oldPosition[0] + oldPosition[1]) % 2 == 0) {
                            chessBoardGC.setColor(Color.WHITE);
                        } else {
                            chessBoardGC.setColor(Color.decode(colorGreen));
                        }
                        chessBoardGC.fill3DRect(oldPosition[0] * 100, oldPosition[1] * 100, 100, 100, true);
                        pieceJustMoved = true; // set flag to indicate that a piece has just been moved*
                        break;
                    }
                }

                // si l'user ne re click pas sur la meme piece
                if (x < 800 && y < 800) { // chess board
                    // find if the piece is in the square clicked
                    for (Piece unePiece : Pieces) {
                        if (unePiece.getPosition()[0] == x_currentSquare && unePiece.getPosition()[1] == y_currentSquare && unePiece.getPieceColor().equals(isWhite ? "white" : "black")) {
                            mouvementsJaune.clear();
                            mouvementsRouge.clear();
                            currentPiece = unePiece;

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


                            ArrayList<int[]> mouvements = new ArrayList<>();
                            if (!pieceJustMoved) {
                                mouvements = MouvementsPieces.getMouvements(unePiece);
                                if (unePiece.getType() == TypePiece.PION && unePiece.getPieceColor().equals("black")) { // si le pion est en position départ il peut avancer de 2 cases
                                    mouvements.add(new int[]{0, 1});
                                    if (y_currentSquare == 1) {
                                        mouvements.add(new int[]{0, 2});
                                    }
                                    for (Piece piece : Pieces) {
                                        if (piece.getPosition()[0] == x_currentSquare + 1 && piece.getPosition()[1] == y_currentSquare + 1 && piece.getPieceColor().equals("white")) {
                                            mouvements.add(new int[]{1, 1});
                                        }
                                        if (piece.getPosition()[0] == x_currentSquare - 1 && piece.getPosition()[1] == y_currentSquare + 1 && piece.getPieceColor().equals("white")) {
                                            mouvements.add(new int[]{-1, 1});
                                        }
                                    }
                                }

                                if (unePiece.getType() == TypePiece.PION && unePiece.getPieceColor().equals("white")) { // si le pion est en position départ il peut avancer de 2 cases
                                    mouvements.add(new int[]{0, -1});
                                    if (y_currentSquare == 6) {
                                        mouvements.add(new int[]{0, -2});
                                    }

                                    for (Piece piece : Pieces) {
                                        if (piece.getPosition()[0] == x_currentSquare + 1 && piece.getPosition()[1] == y_currentSquare - 1 && piece.getPieceColor().equals("black")) {
                                            mouvements.add(new int[]{1, -1});
                                        }
                                        if (piece.getPosition()[0] == x_currentSquare - 1 && piece.getPosition()[1] == y_currentSquare - 1 && piece.getPieceColor().equals("black")) {
                                            mouvements.add(new int[]{-1, -1});
                                        }
                                    }
                                }

                                for (int[] mouvement : mouvements) {
                                    int x_mouvement = mouvement[0] + x_currentSquare;
                                    int y_mouvement = mouvement[1] + y_currentSquare;
                                    int x_delta = mouvement[0];
                                    int y_delta = mouvement[1];

                                    // Check if the movement is within the 8x8 board
                                    if (x_mouvement < 8 && y_mouvement < 8) {
                                        // Check if the path to the destination square is blocked by any pieces
                                        boolean pathBlocked = false;
                                        // check if the current piece is a cavalier
                                        if (unePiece.getType() != TypePiece.CAVALIER) {
                                            for (int i = 1; i < Math.max(Math.abs(x_delta), Math.abs(y_delta)); i++) {
                                                int x_intermediate = x_currentSquare + i * Integer.signum(x_delta);
                                                int y_intermediate = y_currentSquare + i * Integer.signum(y_delta);
                                                for (Piece piece : Pieces) {
                                                    if (piece.getPosition()[0] == x_intermediate && piece.getPosition()[1] == y_intermediate) {
                                                        pathBlocked = true;
                                                        break;
                                                    }
                                                }
                                                if (pathBlocked) {
                                                    break;
                                                }
                                            }
                                        }

                                        // Check if the destination square is occupied by a piece
                                        boolean destinationOccupied = false;
                                        for (Piece piece : Pieces) {
                                            if (piece.getPosition()[0] == x_mouvement && piece.getPosition()[1] == y_mouvement) {
                                                destinationOccupied = true;
                                                break;
                                            }
                                        }

                                        if (!pathBlocked && !destinationOccupied) {
                                            mouvementsJaune.add(mouvement);
                                            chessBoardGC.setColor(Color.decode(colorYellow));
                                            chessBoardGC.fill3DRect(x_mouvement * 100, y_mouvement * 100, 100, 100, true);
                                        } else if (destinationOccupied && !pathBlocked) {

                                            // Check if the destination square is occupied by a piece of the same color
                                            boolean destinationOccupiedBySameColor = false;
                                            for (Piece piece : Pieces) {
                                                if (piece.getPosition()[0] == x_mouvement && piece.getPosition()[1] == y_mouvement && piece.getPieceColor().equals(unePiece.getPieceColor())) {
                                                    destinationOccupiedBySameColor = true;
                                                    break;
                                                }
                                            }
                                            if (!destinationOccupiedBySameColor) {
                                                mouvementsRouge.add(mouvement);
                                                chessBoardGC.setColor(Color.decode(colorRed));
                                                chessBoardGC.fill3DRect(x_mouvement * 100, y_mouvement * 100, 100, 100, true);
                                            }

                                            // if the piece is a pion, he must not eat in front of him
                                            if (unePiece.getType() == TypePiece.PION) {
                                                if (mouvement[0] == 0) {
                                                    mouvementsRouge.remove(mouvement);
                                                    // white square or green square
                                                    if ((x_mouvement + y_mouvement) % 2 == 0) {
                                                        chessBoardGC.setColor(Color.WHITE);
                                                    } else {
                                                        chessBoardGC.setColor(Color.decode(colorGreen));
                                                    }
                                                    chessBoardGC.fill3DRect(x_mouvement * 100, y_mouvement * 100, 100, 100, true);
                                                }
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
                                }
                                // ici
                            } else {
                                isWhite = isWhite ? false : true;
                                for (Piece piece : Pieces) {
                                    for (int[] Position : ListPositionMoveRouges) {
                                        if (piece.getType() == TypePiece.ROI && piece.getPosition()[0] == Position[0] && piece.getPosition()[1] == Position[1]) {
                                            if (piece.getPieceColor().equals("white")) {
                                                viesBlanc--;
                                            } else {
                                                viesNoir--;
                                            }
                                            if (viesNoir == 0 || viesBlanc == 0) {
                                                endGame(piece.getPieceColor());
                                            }
                                        }
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
                            pieceJustMoved = false;
                        }
                    }
                }
            }
        }
    }

    public static void endGame(String joueur) {
        final JFrame parent = new JFrame();
        parent.setVisible(true);
        if (joueur == "black") {
            // show message dialogue
            JOptionPane.showMessageDialog(parent, "Les blancs ont gagné");
        } else {
            // show message dialogue
            JOptionPane.showMessageDialog(parent, "Les noirs ont gagné");
        }
    }
}