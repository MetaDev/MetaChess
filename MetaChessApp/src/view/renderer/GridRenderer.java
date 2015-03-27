package view.renderer;

import engine.board.ExtendedBoardModel;
import res.BitGrids;
import userinterface.generic.GUI1Tile;

public class GridRenderer {

    public static void render(String grid, float cellSize) {
        render(grid, cellSize, 1);
    }
    
    public static void render(String grid, float cellSize, float color) {
        if (grid == null) {
            return;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                RectangleRenderer.drawRectangle(i * cellSize, j * cellSize, cellSize, cellSize, (BitGrids.getBit(grid, i, j) + color) % 2);

            }
        }
    }
    

    public static void transparentRender(String grid, float cellSize, float color) {
        if (grid == null) {
            return;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //don't overwrite underlying color if pixel is not colored
                if (BitGrids.getBit(grid, i, j) == 1) {
                    RectangleRenderer.drawRectangle(i * cellSize, j * cellSize, cellSize, cellSize, (color) % 2);
                }

            }
        }
    }

    public static void render(ExtendedBoardModel board,GUI1Tile tile) {
        String grid = tile.getGrid(board);
        float cellSize = tile.getHeight() / 8;
        if (grid == null) {
            return;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //draw rectangle if not transparent or if the rectangle in the grid is the same color as the tile
                if (!tile.isTransparant() || BitGrids.getBit(grid, i, j) == tile.getColor()) {
                    RectangleRenderer.drawRectangle(i * cellSize, j * cellSize, cellSize, cellSize, (BitGrids.getBit(grid, i, j) + tile.getColor()) % 2);
                }

            }
        }
    }
}
