package userinterface.specific;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import meta.MetaMapping;

public class iconDrawer {
	public static void drawMetaActionIcons() {
		// use map from MetaMapping to draw icons in
		int[][] icon;
		icon = new int[][]{{0,0,0,0,0,0,0,0},
				{0,0,0,1,1,0,0,0},
				{0,1,1,0,0,1,1,0},
				{1,0,0,1,1,0,0,1},
				{1,0,0,1,1,0,0,1},
				{0,1,0,0,0,0,1,0},
				{0,0,1,1,1,1,0,0},
				{0,0,0,0,0,0,0,0}};
		MetaMapping.setMetaActionIcon("TILEVIEW", icon);
		icon = new int[][]{{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,1,0,0},
				{0,0,1,1,0,0,1,0},
				{0,1,1,1,1,0,0,1},
				{0,1,1,1,1,0,0,1},
				{0,0,1,1,0,0,1,0},
				{0,0,0,0,0,1,0,0},
				{0,0,0,0,0,0,0,0}};
		MetaMapping.setMetaActionIcon("MOVRANGE", icon);

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
