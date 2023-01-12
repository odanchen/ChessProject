package root.ui;

import root.logic.Board;
import root.ui.frames.components.BaseFrame;
import root.ui.frames.end_frame.EndFrame;
import root.ui.frames.game_frame.GameFrame;
import root.ui.frames.menu_frame.MenuFrame;
import root.ui.game_flow.GameResult;
import root.ui.game_flow.GameStatus;
import root.ui.graphics.GraphicsManager;

public class GameManager {
    private BaseFrame currentFrame;
    private final GraphicsManager graphicsManager;

    public GameManager() {
        graphicsManager = new GraphicsManager();
    }

    public void runChess() {
        Board board = new Board();
        board.fillStandardBoard();
        currentFrame = new GameFrame(this, graphicsManager);
    }

    public void swapToEndFrame(GameStatus gameStatus) {
        currentFrame = new EndFrame(this, graphicsManager, gameStatus);
    }

    public void runMenu() {
        currentFrame = new MenuFrame(this, graphicsManager);
    }
}
