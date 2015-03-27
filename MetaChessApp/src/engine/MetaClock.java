package engine;

import engine.board.ExtendedBoardModel;
import engine.player.Player;
import engine.board.ExtendedTileModel;

//the clock never stops
public class MetaClock {

    // amount of ms the turn on the main board takes (turn= both players played)
    private static int maxWaitTime = 16 * 64 * 64;

	// based on the fraction of the parent tile give absolute counter of turn on
    // return tile turn based on absolute time
    public static int getTileTurn(int fraction, int time) {
        // real-time
        return (int) (((float) (time) / maxWaitTime)
                * Math.min(fraction, ExtendedBoardModel.maxTileFraction) * 2);
    }

    // return turn based on given time
    public static boolean getTurn(int fraction, int side, int time) {
        int absTurn = getTileTurn(fraction, time);
        return absTurn % 2 == side;
    }
 
    public static boolean getTurn(Player player, int time) {
        ExtendedTileModel tile = player.getControlledModel().getTilePosition();
        return getTurn(tile.getAbsFraction(), player.getSide(), time);
    }

    // return turn based on player

    public static boolean getTurn(Player player) {
       return getTurn(player, getAbsoluteTime());
    }

    public static int getAbsoluteTime() {
        return (int) (System.currentTimeMillis() % maxWaitTime);
    }

   
    public static int getMaxFraction() {
        return ExtendedBoardModel.maxTileFraction;
    }
}
