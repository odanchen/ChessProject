package root.logic.pieces;

import root.logic.Board;
import root.logic.moves.AttackMove;
import root.logic.moves.Move;
import root.logic.moves.RelocationMove;
import root.logic.pieces.properties.PieceColor;
import root.logic.pieces.properties.Position;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(Position position, PieceColor color) {
        super(position, color);
    }

    public Bishop(Bishop bishop) {
        super(Position.copyOf(bishop.getPosition()), bishop.getPieceColor());
    }

    private List<Move> checkLine(Position startPosition, int colDifference, int rowDifference, Board board) {
        List<Move> moves = new ArrayList<>();
        Position endPosition = new Position(getPosition(), colDifference, rowDifference);

        while (endPosition.insideBoard() && board.isEmptyAt(endPosition)) {
            moves.add(new RelocationMove(startPosition, endPosition));
            endPosition = new Position(endPosition, colDifference, rowDifference);
        }

        if (endPosition.insideBoard() && differentColorFrom(board.getPieceAt(endPosition)))
            moves.add(new AttackMove(startPosition, endPosition, endPosition));

        return moves;
    }

    @Override
    public List<Move> calculatePotentialMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        moves.addAll(checkLine(getPosition(), 1, 1, board));
        moves.addAll(checkLine(getPosition(), -1, 1, board));
        moves.addAll(checkLine(getPosition(), 1, -1, board));
        moves.addAll(checkLine(getPosition(), -1, -1, board));

        return moves;
    }

    @Override
    public ChessPiece copy() {
        return new Bishop(this);
    }

    @Override
    public String getPieceSignature() {
        return pieceColor.getColorSign() + "b";
    }

    @Override
    public String getNotationSignature() {
        return "B";
    }
}
