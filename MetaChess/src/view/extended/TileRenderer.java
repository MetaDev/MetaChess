package view.extended;

import view.openglImpl.RectangleRenderer;

public class TileRenderer {

	public static void render(int[][] grid,   float cellSize){
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
					RectangleRenderer.drawRectangle(i*cellSize, j*cellSize, cellSize, cellSize, grid[i][j]);
				
			}
		}
	}
	public static void render(int[][] grid,   float cellSize, int color){
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
					RectangleRenderer.drawRectangle(i*cellSize, j*cellSize, cellSize, cellSize, (grid[i][j] +color+1)%2);
				
			}
		}
	}
}
