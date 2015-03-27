/*
 * Dependencies: WorldModel
 * Responsibilities: handling the input from the user and pass it to all listening controls
 * Capabilities:
 */
package engine;

import engine.board.ExtendedBoardModel;
import engine.player.Player;

public class MetaLoop {

    public static void update(ExtendedBoardModel board) {
        //update every player model on the board

        for (Player player : board
                .getPlayersOnBoard()) {
            player.decide();
        }

    }
}
