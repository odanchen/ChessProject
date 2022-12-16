package pieces;

import board_package.Board;
import pieces.moves.AttackMove;
import pieces.moves.Move;
import pieces.moves.RelocationMove;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {

    // This method is used by the calculateMoves method. It calculates the moves for one line of direction of a bishop.
    private List<Move> checkLine(Position startPosition, int colDifference, int rowDifference, Board board) {
        List<Move> moves = new ArrayList<>();
        Position endPosition = new Position(this.getPosition(), colDifference, rowDifference);

        while (endPosition.isInsideBoard() && board.getPieceAt(endPosition) == null) {
            moves.add(new RelocationMove(startPosition, endPosition));
            endPosition = new Position(endPosition, colDifference, rowDifference);
        }

        if (endPosition.isInsideBoard() && this.notSameColorAs(board.getPieceAt(endPosition)))
            moves.add(new AttackMove(startPosition, endPosition, endPosition));

        return moves;
    }

    @Override
    public void calculatePotentialMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        moves.addAll(checkLine(this.getPosition(), 1, 1, board));
        moves.addAll(checkLine(this.getPosition(), -1, 1, board));
        moves.addAll(checkLine(this.getPosition(), 1, -1, board));
        moves.addAll(checkLine(this.getPosition(), -1, -1, board));

        this.setMoves(moves);
    }

    public Bishop(Position position, PieceColor color) {
        super(position, color);
    }

    public Bishop(Bishop bishop) {
        super(Position.copyOf(bishop.getPosition()), bishop.getPieceColor());
        this.moves = new ArrayList<>(bishop.getMoves());
    }

    @Override
    public ChessPiece copy() {
        return new Bishop(this);
    }
}
