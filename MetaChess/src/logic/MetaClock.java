package logic;

import graphic.PieceGraphic;
import model.MetaModel;

//the clock never stops
public class MetaClock {
	// amount of ms the turn on the main board takes (turn= both players played)
	private static int maxWaitTime = 16 * 64 * 64;
	// minimum amount of ms a turn can take, if lower the game becomes real-time
	private static int minWaitTime = 4 * 64;

	// based on the fraction of the parent tile give absolute counter of turn
	public static int getTileTurn(int fraction) {
		int highestFraction = maxWaitTime / minWaitTime;
		// real-time
		if (highestFraction < fraction)
			fraction = highestFraction;
		return (int) (((float) (System.currentTimeMillis() % maxWaitTime) / maxWaitTime)
				* fraction * 2);
	}

	// return tile turn based on absolute time
	public static int getTileTurn(int fraction, int time) {
		int highestFraction = maxWaitTime / minWaitTime;
		// real-time
		if (highestFraction < fraction)
			fraction = highestFraction;
		return (int) (((float) (time) / maxWaitTime) * fraction * 2);
	}

	// return turn based on absolute time
	public static boolean getTurn(int fraction, int side, int time) {
		int absTurn = getTileTurn(fraction,time);
		return absTurn % 2 == side;
	}

	public static int getAbsoluteTime() {
		return (int) (System.currentTimeMillis() % maxWaitTime);
	}

	// absolute turn of player
	public static int getTileTurn() {
		if (((PieceGraphic) MetaModel.getPlayer().getGraphic()).getTile() == null)
			return -1;
		int fraction = ((PieceGraphic) MetaModel.getPlayer().getGraphic())
				.getTile().absoluteFraction();
		return getTileTurn(fraction);
	}

	public static boolean getTurn(int fraction, int side) {
		int absTurn = getTileTurn(fraction);
		return absTurn % 2 == side;
	}

	// return if the player is in turn
	public static boolean getTurn() {
		if (((PieceGraphic) MetaModel.getPlayer().getGraphic()).getTile() == null)
			return false;
		int fraction = ((PieceGraphic) MetaModel.getPlayer().getGraphic())
				.getTile().absoluteFraction();
		int side = ((PieceGraphic) MetaModel.getPlayer().getGraphic())
				.getColor();
		return getTurn(fraction, side);
	}

	public static int getMaxFraction() {
		return maxWaitTime / minWaitTime;
	}
}
