/*
 * Dependencies: WorldModel
 * Responsibilities: handling the input from the user and pass it to all listening controls
 * Capabilities:
 */
package control;

import meta.MetaConfig;
import model.PlayerModel;

public class MetaLoop {

    public static void update() {
        //update every player model on the board

        for (PlayerModel player : MetaConfig.getBoardModel()
                .getPlayersOnBoard()) {
            player.decide();
        }

    }
}
