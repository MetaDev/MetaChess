package meta;

import model.ExtendedPieceModel;
import model.PlayerModel;
import model.ExtendedTileModel;

//the clock never stops
public class MetaClock {
	// amount of ms the turn on the main board takes (turn= both players played)
	private static int maxWaitTime = 16 * 64 * 64;
	//private static int maxWaitTime = 64* 64;
	//decides what the highest fraction allowed of a tile is, this is chosen such that the shortest turn before becoming real time is 4 *64 ms
	private static int maxTileFraction = 4 * 64;

	// based on the fraction of the parent tile give absolute counter of turn on
	public static int getTileTurn(int fraction) {
		return getTileTurn(Math.min(fraction, maxTileFraction),
				getAbsoluteTime());
	}

	// return tile turn based on absolute time
	public static int getTileTurn(int fraction, int time) {
		// real-time
		return (int) (((float) (time) / maxWaitTime)
				* Math.min(fraction, maxTileFraction) * 2);
	}

	public static int getRelativeTileTurn(PlayerModel player) {
		ExtendedTileModel playerTile = player.getControlledModel().getTilePosition();
		if (playerTile.getParent() != null)
			return (getTileTurn(player.getControlledModel()) / 2)
					% (playerTile.getParent().getChildFraction()) + 1;
		return 0;
	}

	public static int getNrOfFractionsOfMinFraction(int absFraction) {
		return Math.min(absFraction, maxTileFraction) / maxTileFraction;
	}

	// return turn based on absolute time
	public static boolean getTurn(int fraction, int side, int time) {
		int absTurn = getTileTurn(fraction, time);
		return absTurn % 2 == side;
	}

	// return turn based on Piece
	public static boolean getTurn(PlayerModel player) {
		ExtendedTileModel tile = player.getControlledModel().getTilePosition();
		return getTurn(tile.getAbsFraction(),player.getSide());
	}

	public static int getAbsoluteTime() {
		return (int) (System.currentTimeMillis() % maxWaitTime);
	}

	// tile turn of piece
	public static int getTileTurn(ExtendedPieceModel piece) {
		ExtendedTileModel tile = piece.getTilePosition();
		if (tile == null)
			return -1;
		int fraction = tile.getAbsFraction();
		return getTileTurn(fraction);
	}

	

	public static boolean getTurn(int fraction, int side) {
		int absTurn = getTileTurn(fraction);
		return absTurn % 2 == side;
	}

	

	public static int getMaxFraction() {
		return maxTileFraction;
	}
}
