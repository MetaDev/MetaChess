package view.renderer;

import userinterface.generic.GUI1Tile;
import view.RectangleRenderer;

public class GridRenderer {

    public static void render(int[][] grid, float cellSize) {
        render(grid, cellSize, 1);
    }

    public static void render(int[][] grid, float cellSize, int color) {
        if (grid == null) {
            return;
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                RectangleRenderer.drawRectangle(i * cellSize, j * cellSize, cellSize, cellSize, (grid[i][j] + color) % 2);

            }
        }
    }

    public static void transparentRender(int[][] grid, float cellSize, int color) {
        if (grid == null) {
            return;
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                //don't overwrite underlying color if pixel is not colored
                if (grid[i][j] == 1) {
                    RectangleRenderer.drawRectangle(i * cellSize, j * cellSize, cellSize, cellSize, (color) % 2);
                }

            }
        }
    }

    public static void render(GUI1Tile tile) {
        int[][] grid = tile.getGrid();
        float cellSize = tile.getHeight() / 8;
        if (grid == null) {
            return;
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                //draw rectangle if not transparent or if the rectangle in the grid is the same color as the tile
                if (!tile.isTransparant() || grid[i][j] == tile.getColor()) {
                    RectangleRenderer.drawRectangle(i * cellSize, j * cellSize, cellSize, cellSize, (grid[i][j] + tile.getColor()) % 2);
                }

            }
        }
    }
}
