package chessRoot.logic.moves;

import chessRoot.logic.Board;
import chessRoot.logic.pieces.ChessPiece;
import chessRoot.logic.pieces.Position;

/**
 * If the pawn has reached the last tile. While not attacking any chessRoot.logic.pieces.
 */
public class PromotionMove extends Move {
    private final ChessPiece newPiece;
    public PromotionMove(Position startPosition, Position endPosition, ChessPiece newPiece) {
        super(startPosition, endPosition);
        this.newPiece = newPiece;
    }

    public ChessPiece getNewPiece() {
        return newPiece;
    }

    @Override
    public boolean isPossible(Board board) {
        Board copyBoard = new Board(board);
        copyBoard.makeMove(this);
        return !copyBoard.isCheck(copyBoard.getPieceAt(this.getEndPosition()).getPieceColor());
    }
}
