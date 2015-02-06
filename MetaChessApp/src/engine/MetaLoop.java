/*
 * Dependencies: WorldModel
 * Responsibilities: handling the input from the user and pass it to all listening controls
 * Capabilities:
 */
package engine;

import meta.MetaConfig;
import engine.player.Player;

public class MetaLoop {

    public static void update() {
        //update every player model on the board

        for (Player player : MetaConfig.getBoardModel()
                .getPlayersOnBoard()) {
            player.decide();
        }

    }
}
