package logic;

import graphic.TileGraphic;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Random;

import model.MetaModel;

public class BoardLogic implements Logic {

	private static double[][][][] enterTileMapping;
	private static int enterTileMode = 1;
	private static boolean hoover = true;

	public static int getEnterTileMode() {
		return enterTileMode;
	}

	public static void setEnterTileMode(int enterTileMode) {
		BoardLogic.enterTileMode = enterTileMode;
	}

	public static boolean isHoover() {
		return hoover;
	}

	public static void setHoover(boolean hoover) {
		BoardLogic.hoover = hoover;
	}

	public static void init() {
		fillMapping(enterTileMode);
	}

	// public float tileToAxis(ArrayList<Integer> list) {
	// float sum = 0;
	// for (int i = 0; i < list.size(); i++) {
	// sum += list.get(i) * Math.pow(2, -i) * floor.getWidth();
	// }
	// return sum;
	// }

	// public float axisToTile(float f) {
	// return 0;
	// }

	// Use the recursive coordinates I and J to find a tile;
	public static TileGraphic getTile(int[] I, int[] J) {
		TileGraphic currTile =(TileGraphic) MetaModel.getBoardModel().getGraphic();
		int i = 0;
		while (currTile.getChildren() != null && i < I.length && i < J.length) {
			currTile = currTile.getChildren()[I[i]][J[i]];
			i++;
		}
		return currTile;
	}

	public static void changeTileMapping(int mode) {
		enterTileMode = mode;
		fillMapping(mode);
	}

	// fill entertilemapping array
	// this array consists the inter-tile mapping to it's children depending on
	// movement direction and neigbour color
	private static void fillMapping(int mode) {
		// moded: 0: random (hoover) 1:opposite color + corner(in-tile movement)
		// 2:...
		// value -1 means that the movement in combination with the color is not
		// mapped on the children of the tile
		// this mapping will later be made editable -> one of the pawn's spells
		// mapping: movement (1,-1) -> (2,0) in array
		enterTileMapping = new double[3][3][2][2];

		double[] temp1 = { -1, -1 };

		for (double[][][] m2 : enterTileMapping) {
			for (double[][] m1 : m2) {
				Arrays.fill(m1, temp1);
			}
		}
		switch (mode) {
		case 0:
			Random rn = new Random();
			// movement-color combinations with same mapping are combined
			enterTileMapping[1][0][0] = enterTileMapping[1][0][1] = new double[] {
					rn.nextDouble(), 1 };
			enterTileMapping[1][2][0] = enterTileMapping[1][2][1] = new double[] {
					rn.nextDouble(), 0 };
			enterTileMapping[2][1][0] = enterTileMapping[2][1][1] = new double[] {
					0, rn.nextDouble() };
			enterTileMapping[0][1][0] = enterTileMapping[0][1][1] = new double[] {
					1, rn.nextDouble() };
			enterTileMapping[2][2][0] = enterTileMapping[2][2][1] = new double[] {
					0, 0 };
			enterTileMapping[0][0][0] = enterTileMapping[0][0][1] = new double[] {
					1, 1 };
			enterTileMapping[2][0][0] = enterTileMapping[2][0][1] = new double[] {
					0, 1 };
			enterTileMapping[0][2][0] = enterTileMapping[0][1][1] = new double[] {
					1, 0 };
			enterTileMapping[2][0][0] = enterTileMapping[2][0][1] = new double[] {
					0, 1 };
			break;
		case 1:
			// not correct, but it's just an example
			// movement-color combinations with same mapping are combined
			enterTileMapping[1][2][1] = enterTileMapping[0][1][1] = enterTileMapping[0][2][1] = new double[] {
					1, 0 };
			enterTileMapping[2][1][1] = enterTileMapping[1][0][1] = enterTileMapping[2][0][1] = new double[] {
					0, 1 };
			enterTileMapping[1][0][0] = enterTileMapping[0][1][0] = enterTileMapping[0][0][0] = new double[] {
					1, 1 };
			enterTileMapping[1][2][0] = enterTileMapping[2][1][0] = enterTileMapping[2][2][0] = new double[] {
					0, 0 };
			break;
		}

	}

	// implementation; go to neighbour tile do remainingmovement -1
	// if remainingmovement>0 do recursion
	//parameters: floating, never go to lower fraction, float on it
	// ignoreoccupation, allowed to jump over pieces
	// penetraLowerFraction, continue movement when entering a lower fractioned tile
	public static TileGraphic getTileNeighbour(TileGraphic tile, int horDir,
			int vertDir, boolean floating, boolean ignoreOccupation, boolean penetrateLowerFraction) {
		TileGraphic it = tile;
		int startFraction = it.absoluteFraction();
		// root tile
		if (it.getParent() == null) {
			return null;
		}

		Deque<Double> stackI = new ArrayDeque<Double>();
		Deque<Double> stackJ = new ArrayDeque<Double>();
		int verMov = Integer.signum(vertDir);
		int horMov = Integer.signum(horDir);
		int remainingHorMov = horDir - horMov;

		int remainingVerMov = vertDir - verMov;

		// on the border of parent tile
		// iterate up until parent which contains neighbour is found
		while (it.getI() + horMov > it.getParent().getChildFraction() - 1
				|| it.getI() + horMov < 0
				|| it.getJ() + verMov > it.getParent().getChildFraction() - 1
				|| it.getJ() + verMov < 0) {
			// on the border of the floor

			// save relative recursive position
			stackI.push(((double) it.getI())
					/ it.getParent().getChildFraction());
			stackJ.push(((double) it.getJ())
					/ it.getParent().getChildFraction());
			it = it.getParent();
			if (it.getParent() == null)
				return null;
		}

		// once parent with neighbour on the same fraction is found
		// change 'it' to neighbour
		it = it.getParent().getChildren()[it.getI() + horMov][it.getJ()
				+ verMov];

		// once correct neighbour is found then iterate down to lowest fraction,
		// using corresponding relative recursive position
		while (it.getChildren() != null) {
			if (stackI.peek() != null && stackJ.peek() != null) {
				it = it.getChildren()[((int) Math.round(stackI.pop()
						* it.getChildFraction())
						+ horMov + it.getChildFraction())
						% it.getChildFraction()][((int) Math.round(stackJ.pop()
						* it.getChildFraction())
						+ verMov + it.getChildFraction())
						% it.getChildFraction()];
			} else {
				// when hover is on and the piece doesn't stop on the
				// fractioning
				if (floating
						&& Math.abs(remainingHorMov)
								+ Math.abs(remainingVerMov) != 0)
					break;
				TileGraphic temp = enterLowerFractionOfTile(it, horMov, verMov);
				// avoid loop
				if (temp == it) {
					break;
				}
				it = temp;
			}

		}
		// if a tile in the movement is occupied the movement is not allowed
		if (it.isOccupied() && !ignoreOccupation) {
			return null;
		}
		// only continue movement if the piece didn't land on a lower fractioned
		// tile or if penetraLowerFraction is on
		if (startFraction <= it.absoluteFraction() || penetrateLowerFraction) {
			// recursion if needed-> movement is not finished
			if (Math.abs(remainingHorMov) + Math.abs(remainingVerMov) != 0) {
				return getTileNeighbour(it, remainingHorMov, remainingVerMov,
						floating, ignoreOccupation,penetrateLowerFraction);
			}
		}
		return it;

	}

	// one of many implementations to come
	// return a child with the highest fraction and the opposit colour as the
	// parent that is positioned on the border accesed by the movement.
	public static TileGraphic enterLowerFractionOfTile(TileGraphic tile,
			int horMov, int verMov) {
		TileGraphic it = tile;
		// c==1 -> tile your on is white, if you want to use colour in your
		// function
		// int c = (int) it.getColor().b;

		double i = enterTileMapping[horMov + 1][verMov + 1][it.getColor()][0];
		double j = enterTileMapping[horMov + 1][verMov + 1][it.getColor()][1];
		// movement implies deeper level positioning
		if (i != -1 && j != -1) {
			while (it.getChildren() != null) {
				// map movement (i,j), like (-1,0)-> left, to a position in a
				// higher
				// fractioned tile

				it = it.getChildren()[(int) (i * (it.getChildFraction() - 1))][(int) (j * (it
						.getChildFraction() - 1))];
			}
		}

		return it;
	}

	

}
