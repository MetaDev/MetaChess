package logic;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;

import meta.MetaConfig;
import meta.MetaUtil;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;

public class BoardLogic {

	public static ExtendedTileModel getTile(int[] I, int[] J) {
		ExtendedTileModel currTile = (ExtendedTileModel) MetaConfig
				.getBoardModel().getRootTile();
		int i = 0;
		while (currTile.getChildren() != null && i < I.length && i < J.length) {
			currTile = currTile.getChildren()[I[i]][J[i]];
			i++;
		}
		return currTile;
	}

	// reverse method from above, give position array from tile
	public static MetaPosition getTilePost(ExtendedTileModel tile) {
		if (tile.getLevel() > 0) {
			int[] I = new int[tile.getLevel()];
			int[] J = new int[tile.getLevel()];
			while (tile.getParent() != null) {
				// add position at the tail
				I[tile.getLevel() - 1] = tile.getI();
				J[tile.getLevel() - 1] = tile.getJ();
				tile = tile.getParent();
			}
			return new MetaPosition(I, J);
		}
		return null;
	}

	/*
	 * implementation; go to neighbour tile do remainingmovement -1 if
	 * remainingmovement>0 do recursion parameters: floating, never go to lower
	 * fraction, float on it ignoreoccupation, allowed to jump over pieces
	 * penetraLowerFraction, continue movement when entering a lower fractioned
	 * tile result: null if movement not allowed else desired tile save alle
	 * encountered pieces on movement in a list
	 */

	public static ExtendedTileModel getTileNeighbour(ExtendedTileModel tile,
			int horDir, int vertDir, boolean hoover, boolean ignoreOccupation,
			boolean penetrateLowerFraction, List<ExtendedTileModel> tilePath) {
		ExtendedTileModel it = tile;
		int startFraction = it.getAbsFraction();
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
				// when hoover is on and the piece doesn't stop on the
				// fractioning
				if (hoover
						&& Math.abs(remainingHorMov)
								+ Math.abs(remainingVerMov) != 0)
					break;
				ExtendedTileModel temp = enterLowerFractionOfTile(it, horMov,
						verMov);
				// avoid loop
				if (temp == it) {
					break;
				}
				it = temp;
			}

		}

		// only continue movement if the piece didn't land on a lower fractioned
		// tile or if penetraLowerFraction is on
		if (startFraction <= it.getAbsFraction() || penetrateLowerFraction) {
			// recursion if needed-> movement is not finished
			if (Math.abs(remainingHorMov) + Math.abs(remainingVerMov) != 0) {
				// if a tile in the movement is occupied the movement is not
				// allowed
				if (it.isOccupied() && !ignoreOccupation) {

					if (tilePath != null) {
						tilePath.add(it);
					}
				}
				// if a piece is encountered and occupation can't be ignored,
				// stop move
				if (it.isOccupied() && ignoreOccupation) {
					return null;
				}
				return getTileNeighbour(it, remainingHorMov, remainingVerMov,
						hoover, ignoreOccupation, penetrateLowerFraction,
						tilePath);
			}
		}

		return it;

	}

	// method for finding direct neighbour no need for floating and penetration
	// and occupation
	// options
	public static ExtendedTileModel getTileNeighbour(ExtendedTileModel tile,
			int horDir, int vertDir) {
		return getTileNeighbour(tile, horDir, vertDir, false, true, false, null);
	}

	// return a random child with the highest fraction and the opposit colour as
	// the
	// parent that is positioned on the border accesed by the movement.
	public static ExtendedTileModel enterLowerFractionOfTile(
			ExtendedTileModel tile, int horMov, int verMov) {
		ExtendedTileModel it = tile;
		// c==1 -> tile your on is white, if you want to use colour in your
		// function
		// int c = (int) it.getColor().b;
		Random rn = new Random();
		double i = rn.nextDouble();
		double j = rn.nextDouble();
		// go to second lowest fraction with random position
		while (it.getChildren() != null) {
			it = it.getChildren()[(int) (i * (it.getChildFraction() - 1))][(int) (j * (it
					.getChildFraction() - 1))];
		}
		ExtendedTileModel[][] siblings= it.getChildren();
		//once in second lowest fraction choose a tile with opposite color from the lowest fractioned tiles
		do{
			it = siblings[(int) (i * (it.getChildFraction() - 1))][(int) (j * (it
					.getChildFraction() - 1))];
		}while(it.getColor()!=tile.getColor());

		return it;
	}

	// Euclidian distance of tiles
	public static double calculateDistance(ExtendedTileModel tile1,
			ExtendedTileModel tile2) {
		return Math
				.sqrt(Math.pow((double) tile1.getAbsX() - tile2.getAbsX(), 2)
						+ Math.pow((double) tile1.getAbsY() - tile2.getAbsY(),
								2));
	}

	public static boolean isInrange(ExtendedPieceModel viewer,
			ExtendedPieceModel subject) {
		// calculate viewsquare boundaries
		float x = viewer.getTilePosition().getAbsX()
				- viewer.getTilePosition().getAbsSize()
				* viewer.getNrOfViewTiles();
		float y = viewer.getTilePosition().getAbsY()
				- viewer.getTilePosition().getAbsSize()
				* viewer.getNrOfViewTiles();
		float s = viewer.getTilePosition().getAbsSize()
				* (2 * viewer.getNrOfViewTiles() + 1);

		if (subject.getTilePosition().getAbsX() >= x
				&& subject.getTilePosition().getAbsX() <= x + s) {
			if (subject.getTilePosition().getAbsY() >= y
					&& subject.getTilePosition().getAbsY() <= y + s) {
				return true;
			}
		}
		return false;
	}

	// return a tile with an abs fraction smaller then the one given
	public static ExtendedTileModel getRandomTile(int maxAbsFraction,
			boolean canBeOccupied) {
		ExtendedTileModel tileIt;
		do {
			// start from root tile always
			tileIt = MetaConfig.getBoardModel().getRootTile();
			// now choose random tile
			int randCol;
			int randRow;
			// pick a random max level of fraction, different from root (0)
			int randAbsFraction = MetaUtil.randInt(8, maxAbsFraction);

			while (tileIt.getChildren() != null
					&& tileIt.getAbsFraction() <= randAbsFraction) {

				// pick random child on current tile
				randCol = MetaUtil.randInt(0, tileIt.getChildren().length - 1);
				randRow = MetaUtil.randInt(0, tileIt.getChildren().length - 1);
				tileIt = tileIt.getChildren()[randCol][randRow];
			}
		}
		// if the tile can't be occupied restart the search if the found random
		// tile is occupied
		while (!canBeOccupied
				&& MetaConfig.getBoardModel().getModelOnPosition(tileIt) != null);

		return tileIt;

	}

	public static void printAllPiecePositions() {
		for (Map.Entry<ExtendedPieceModel, ExtendedTileModel> pair : MetaConfig
				.getBoardModel().getEntityModels().entrySet()) {

		}
	}
}
