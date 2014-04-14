package userinterface.specific;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import meta.MetaMapping;

public class iconDrawer {
	public static void drawMetaActionIcons() {
		// use map from MetaMapping to draw icons in

		int[][] icon = getGrid(new int[][] { { 3, 6 }, { 4, 6 }, { 2, 5 },
				{ 3, 5 }, { 4, 5 }, { 5, 5 }, { 1, 4 }, { 2, 4 }, { 3, 4 },
				{ 4, 4 }, { 5, 4 }, { 6, 4 }, { 5, 5 }, { 3, 3 }, { 4, 3 },
				{ 1, 2 }, { 2, 2 }, { 3, 2 }, { 4, 2 }, { 5, 2 }, { 6, 2 },
				{ 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 },
				{ 3, 0 }, { 4, 0 } });
		MetaMapping.setMetaActionIcon("TILEVIEW", icon);

	}

	public static void drawNumbers() {
		List<int[]> allPoints = new ArrayList<>();
		allPoints.addAll(getLine(new int[] { 3, 6 }, new int[] { 3, 1 }));
		allPoints.addAll(getLine(new int[] { 4, 6 }, new int[] { 4, 1 }));
		allPoints.add(new int[]{2,6});
		allPoints.add(new int[]{5,6});
		allPoints.add(new int[]{2,1});
		allPoints.add(new int[]{5,1});
		allPoints.add(new int[]{1,5});
		allPoints.add(new int[]{2,5});
		System.out.println(allPoints);
		MetaMapping.setMetaActionIcon("1",getGrid(allPoints));

	}

	public static int[][] getGrid(int[][] points) {
		int[][] icon = new int[points.length][points.length];
		for (int[] point : points) {
			icon[point[0]][point[1]] = 1;
		}
		return icon;
	}
	public static int[][] getGrid(List<int[]> points) {
		int[][] icon = new int[points.size()][points.size()];
		for (int[] point : points) {
			System.out.println(point[0]);
			icon[point[0]][point[1]] = 1;
		}
		return icon;
	}

	

	public static List<int[]> addPoints(List<int[][]> points) {
		List<int[]> concat = new ArrayList<>();
		for (int[][] point : points) {
			concat.addAll(Arrays.asList(point));
		}

		return  concat;
	}

	public static List<int[]> getLine(int[] from, int[] to) {
		List<int[]> line = new ArrayList<>();
		// vertical line
		if (from[0] == to[0]) {
			for (int i = to[1]; i <= from[1]; i++) {
				line.add(new int[]{from[0],i});
			}
		}
		// rectangle line
		else {
			for (int i = to[0]; i <= from[0]; i++) {
				line.add(new int[]{i,from[1]});
			}
		}
		return line;

	}
}
