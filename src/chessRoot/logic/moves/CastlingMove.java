package chessRoot.logic.moves;

import chessRoot.logic.pieces.Position;

/**
 * Standard castling rules apply to this type of move.
 */
public class CastlingMove extends Move {
    public CastlingMove(Position startPosition, Position endPosition) {
        super(startPosition, endPosition);
    }
}