package view.renderer;

import view.zgpu.RectangleRenderer;

public class GridRenderer {

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
	public static void transparentRender(int[][] grid,   float cellSize, int color){
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				//don't overwrite underlying color if pixel is not colored
				if(grid[i][j]==1){
					RectangleRenderer.drawRectangle(i*cellSize, j*cellSize, cellSize, cellSize, (color)%2);
				}
				
			}
		}
	}
}
