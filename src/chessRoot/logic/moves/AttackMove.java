package chessRoot.logic.moves;

import chessRoot.logic.Board;
import chessRoot.logic.pieces.ChessPiece;
import chessRoot.logic.pieces.Pawn;
import chessRoot.logic.pieces.Position;

/**
 * A type of move where the enemy piece is being taken. Includes an attacked position.
 */
public class AttackMove extends Move {
    private final Position attackedPosition;

    public ChessPiece getAttackedPiece(Board board) {
        return board.getPieceAt(this.attackedPosition);
    }

    public Position getAttackedPosition() {
        return this.attackedPosition;
    }

    public AttackMove(Position startPosition, Position endPosition, Position attackedPosition) {
        super(startPosition, endPosition);
        this.attackedPosition = attackedPosition;
    }

    public boolean isAttackedPosition(Position position) {
        return attackedPosition.equals(position);
    }

    @Override
    public boolean isPossible(Board board) {
        Board copyBoard = new Board(board);
        copyBoard.makeMove(this);
        return !copyBoard.isCheck(copyBoard.getPieceAt(this.getEndPosition()).getPieceColor());
    }

    @Override
    public String toString(Board board) {
        if (getPieceAtEnd(board) instanceof Pawn)
            return getStartPosition().getCol() + "x" + getEndPosition().toString();
        return getPieceAtEnd(board).getNotationSignature() + "x" + getEndPosition().toString();
    }

}
