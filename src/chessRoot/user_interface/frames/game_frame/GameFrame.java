package chessRoot.user_interface.frames.game_frame;

import chessRoot.assets.board_colors.BoardColors;
import chessRoot.assets.board_colors.ColorSet;
import chessRoot.logic.Board;
import chessRoot.user_interface.game_flow.GameControl;
import chessRoot.user_interface.game_flow.GameStates;
import chessRoot.logic.pieces.ChessPiece;
import chessRoot.logic.pieces.PieceColor;
import chessRoot.logic.pieces.Position;
import chessRoot.logic.moves.Move;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Optional;

public class GameFrame extends JFrame {
    private final BoardPanel boardPanel;
    private final PiecePanel piecePanel;
    private final IndicationPanel indicPanel;

    private final Board board;
    private int boardSize = 512;
    private ColorSet colorSet = BoardColors.OPTION1;
    private GameControl gameControl;
    private GameStates gameStatus;
    private ChessPiece selectedPiece = null;

    public GameFrame(Board board, GameControl gameControl) {
        this.setUndecorated(true);

        this.setBounds(0, 0, boardSize, boardSize);
        this.gameControl = gameControl;
        this.gameStatus = gameControl.getGameStatus();
        this.board = board;
        this.boardPanel = new BoardPanel(boardSize, colorSet);
        this.piecePanel = new PiecePanel(boardSize, board);
        this.indicPanel = new IndicationPanel(boardSize, board, colorSet);

        this.add(indicPanel);
        this.add(piecePanel);
        this.add(boardPanel);

        indicPanel.setSize(boardSize, boardSize);
        indicPanel.setOpaque(false);

        this.createMouseListener();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void playerWhiteTurnEvent(MouseEvent e) {
        ChessPiece clickedPiece = board.getPieceAt(getPositionOnTheBoard(e.getX(), e.getY()));
        if (clickedPiece != null && clickedPiece.getPieceColor() == PieceColor.WHITE) {
            gameStatus = GameStates.PLAYER_WHITE_SELECTED_PIECE;
            selectedPiece = clickedPiece;
            indicPanel.updateSelectedPiece(clickedPiece);
        }
    }

    private void playerBlackTurnEvent(MouseEvent e) {
        ChessPiece clickedPiece = board.getPieceAt(getPositionOnTheBoard(e.getX(), e.getY()));
        if (clickedPiece != null && clickedPiece.getPieceColor() == PieceColor.BLACK) {
            gameStatus = GameStates.PLAYER_BLACK_SELECTED_PIECE;
            selectedPiece = clickedPiece;
            indicPanel.updateSelectedPiece(clickedPiece);
        }
    }

    private void playerWhiteSelectedAPieceEvent(MouseEvent e) {
        Position clickedPosition = getPositionOnTheBoard(e.getX(), e.getY());

        Move moveToMake = selectedPiece.calculateMoves(board).stream()
                .filter(move -> move.getEndPosition().equals(clickedPosition))
                .findFirst().orElse(null);

        if (moveToMake != null) {
            board.makeMove(moveToMake);
            piecePanel.repaint();
            if(board.isMate(PieceColor.BLACK)){
                System.out.println("black you lose bitch");
            }
            gameStatus = GameStates.PLAYER_BLACK_TURN;
        } else gameStatus = GameStates.PLAYER_WHITE_TURN;

        indicPanel.removeAll(); piecePanel.repaint();
        selectedPiece = null;
    }

    private void playerBlackSelectedAPieceEvent(MouseEvent e) {
        Position clickedPosition = getPositionOnTheBoard(e.getX(), e.getY());

        Move moveToMake = selectedPiece.calculateMoves(board).stream()
                .filter(move -> move.getEndPosition().equals(clickedPosition))
                .findFirst().orElse(null);

        if (moveToMake != null) {
            board.makeMove(moveToMake);
            piecePanel.repaint();
            if(board.isMate(PieceColor.WHITE)){
                System.out.println("white you lose bitch");
            }
            gameStatus = GameStates.PLAYER_WHITE_TURN;
        } else gameStatus = GameStates.PLAYER_BLACK_TURN;

        indicPanel.removeAll(); piecePanel.repaint();
        selectedPiece = null;
    }

    private void onMousePress(MouseEvent e) {
        if (gameStatus == GameStates.PLAYER_WHITE_TURN) playerWhiteTurnEvent(e);
        else if (gameStatus == GameStates.PLAYER_BLACK_TURN) playerBlackTurnEvent(e);
        else if (gameStatus == GameStates.PLAYER_BLACK_SELECTED_PIECE) playerBlackSelectedAPieceEvent(e);
        else if (gameStatus == GameStates.PLAYER_WHITE_SELECTED_PIECE) playerWhiteSelectedAPieceEvent(e);
    }

    private void createMouseListener() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                onMousePress(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void updatePieces() {
        this.piecePanel.repaint();
    }

    private int squareSize() {
        return this.boardSize / 8;
    }


    private Position getPositionOnTheBoard(int x, int y) {
        int row = y / squareSize();
        int col = x / squareSize();

        return new Position((char) (col + 'a'), (8 - row));
    }
}