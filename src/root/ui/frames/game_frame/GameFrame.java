package root.ui.frames.game_frame;

import root.logic.Board;
import root.logic.pieces.properties.PieceColor;
import root.ui.GameManager;
import root.ui.frames.BaseFrame;
import root.ui.frames.game_frame.panels.GamePanel;
import root.ui.graphics.GraphicsManager;
import root.ui.frames.menu_frame.CustomButton;
import root.ui.game_flow.GameStatus;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends BaseFrame {
    private final GraphicsManager graphicsManager;
    private final GamePanel gamePanel;

    private void addFlipButton() {
        JButton flipButton = new CustomButton("flipButtonReleased", graphicsManager,75,75);
        flipButton.setBackground(graphicsManager.getBlackSquareColor());
        flipButton.setForeground(graphicsManager.getWhiteSquareColor());
        flipButton.setContentAreaFilled(true);
        flipButton.setFont(new Font("Serif", Font.BOLD, 20));
        getContentPane().add(flipButton);
        flipButton.setBounds(graphicsManager.getFlipButtonBounds(75,75));
        flipButton.addActionListener(e -> gamePanel.flipPanel());
    }

    private Board createBoard() {
        Board board = new Board();
        board.fillStandardBoard();
        return board;
    }

    public GameFrame(GameManager gameManager, GraphicsManager graphicsManager) {
        super(gameManager, graphicsManager);
        this.graphicsManager = graphicsManager;
        GameStatus gameStatus = new GameStatus(createBoard(), PieceColor.WHITE);
        this.gamePanel = new GamePanel(gameStatus, graphicsManager);

        getContentPane().setBackground(graphicsManager.getWhiteSquareColor());
        getContentPane().add(gamePanel);
        addFlipButton();
    }
}
