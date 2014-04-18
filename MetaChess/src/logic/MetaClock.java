package logic;

import java.awt.image.TileObserver;

import meta.MetaMapping;
import model.ExtendedPieceModel;
import model.ExtendedPlayerModel;
import model.ExtendedTileModel;


//the clock never stops
public class MetaClock {
	// amount of ms the turn on the main board takes (turn= both players played)
	private static int maxWaitTime = 16 * 64 * 64;
	// minimum amount of ms a turn can take, if lower the game becomes real-time
	private static int minWaitTime = 4 * 64;

	// based on the fraction of the parent tile give absolute counter of turn on 
	public static int getTileTurn(int fraction) {
		int highestFraction = maxWaitTime / minWaitTime;
		// real-time
		if (highestFraction < fraction)
			fraction = highestFraction;
		return getTileTurn(fraction,getAbsoluteTime());
	}

	// return tile turn based on absolute time
	public static int getTileTurn(int fraction, int time) {
		int highestFraction = maxWaitTime / minWaitTime;
		// real-time
		if (highestFraction < fraction)
			fraction = highestFraction;
		return (int) (((float) (time) / maxWaitTime) * fraction *2);
	}
public static int getRelativeTileTurn(){
	ExtendedPlayerModel player = MetaMapping.getBoardModel().getPlayer();
	ExtendedTileModel playerTile = MetaMapping.getBoardModel().getPiecePosition(player);
	if(playerTile.getParent()!=null)
		return (getTileTurn()/2)%(playerTile.getParent().getChildFraction())+1;
	return 0;
}
	// return turn based on absolute time
	public static boolean getTurn(int fraction, int side, int time) {
		int absTurn = getTileTurn(fraction,time);
		return absTurn % 2 == side;
	}
	//return turn based on Piece
	public static boolean getTurn(ExtendedPieceModel piece){
		ExtendedTileModel tile = MetaMapping.getBoardModel().getPiecePosition(piece);
		return getTurn(tile.absoluteFraction(),piece.getSide());
	}
	public static int getAbsoluteTime() {
		return (int) (System.currentTimeMillis() % maxWaitTime);
	}
	// tile turn of piece
		public static int getTileTurn(ExtendedPieceModel piece) {
			ExtendedTileModel tile = MetaMapping.getBoardModel().getPiecePosition(piece);
			if (tile == null)
				return -1;
			int fraction =tile.absoluteFraction();
			return getTileTurn(fraction);
		}
	// absolute turn of player
	public static int getTileTurn() {
		return getTileTurn(MetaMapping.getBoardModel().getPlayer());
	}

	public static boolean getTurn(int fraction, int side) {
		int absTurn = getTileTurn(fraction);
		return absTurn % 2 == side;
	}

	// return if the player is in turn
	public static boolean getTurn() {
		ExtendedPlayerModel player = MetaMapping.getBoardModel().getPlayer();
		ExtendedTileModel playerTile = MetaMapping.getBoardModel().getPiecePosition(player);
		if (playerTile == null)
			return false;
		int fraction = (playerTile).absoluteFraction();
		int side =  player.getSide();
		return getTurn(fraction, side);
	}

	public static int getMaxFraction() {
		return maxWaitTime / minWaitTime;
	}
}
