package logic;

import meta.MetaConfig;
import model.ExtendedPieceModel;
import model.ExtendedPlayerModel;
import model.ExtendedTileModel;

//the clock never stops
public class MetaClock {
	// amount of ms the turn on the main board takes (turn= both players played)
	private static int maxWaitTime = 16 * 64 * 64;
	//decides what the highest fraction allowed of a tile is, this is chosen such that the shortest turn before becoming real time is 4 *64 ms
	private static int maxTileFraction = 4 * 64;

	// based on the fraction of the parent tile give absolute counter of turn on
	public static int getTileTurn(int fraction) {
		return getTileTurn(Math.max(fraction, maxTileFraction),
				getAbsoluteTime());
	}

	// return tile turn based on absolute time
	public static int getTileTurn(int fraction, int time) {
		// real-time
		return (int) (((float) (time) / maxWaitTime)
				* Math.max(fraction, maxTileFraction) * 2);
	}

	public static int getRelativeTileTurn() {
		ExtendedPlayerModel player = MetaConfig.getBoardModel().getPlayer();
		ExtendedTileModel playerTile = MetaConfig.getBoardModel()
				.getPiecePosition(player.getControlledModel());
		if (playerTile.getParent() != null)
			return (getTileTurn() / 2)
					% (playerTile.getParent().getChildFraction()) + 1;
		return 0;
	}

	public static int getNrOfFractionsOfMinFraction(int absFraction) {
		return Math.max(absFraction, maxTileFraction) / maxTileFraction;
	}

	// return turn based on absolute time
	public static boolean getTurn(int fraction, int side, int time) {
		int absTurn = getTileTurn(fraction, time);
		return absTurn % 2 == side;
	}

	// return turn based on Piece
	public static boolean getTurn(ExtendedPieceModel piece) {
		ExtendedTileModel tile = MetaConfig.getBoardModel().getPiecePosition(
				piece);
		return getTurn(tile.absoluteFraction(), piece.getSide());
	}

	public static int getAbsoluteTime() {
		return (int) (System.currentTimeMillis() % maxWaitTime);
	}

	// tile turn of piece
	public static int getTileTurn(ExtendedPieceModel piece) {
		ExtendedTileModel tile = MetaConfig.getBoardModel().getPiecePosition(
				piece);
		if (tile == null)
			return -1;
		int fraction = tile.absoluteFraction();
		return getTileTurn(fraction);
	}

	// absolute turn of player
	public static int getTileTurn() {
		return getTileTurn(MetaConfig.getBoardModel().getPlayer()
				.getControlledModel());
	}

	public static boolean getTurn(int fraction, int side) {
		int absTurn = getTileTurn(fraction);
		return absTurn % 2 == side;
	}

	// return if the player is in turn
	public static boolean getTurn() {
		ExtendedPlayerModel player = MetaConfig.getBoardModel().getPlayer();
		ExtendedTileModel playerTile = MetaConfig.getBoardModel()
				.getPiecePosition(player.getControlledModel());
		if (playerTile == null)
			return false;
		int fraction = (playerTile).absoluteFraction();
		int side = player.getSide();
		return getTurn(fraction, side);
	}

	public static int getMaxFraction() {
		return maxTileFraction;
	}
}
