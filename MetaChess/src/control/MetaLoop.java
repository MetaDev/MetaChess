/*
 * Dependencies: WorldModel
 * Responsibilities: handling the input from the user and pass it to all listening controls
 * Capabilities:
 */
package control;

import logic.MetaClock;
import meta.MetaConfig;
import model.ExtendedBoardModel;
import model.ExtendedPieceModel;
import model.ExtendedPlayerModel;
import model.ExtendedTileModel;

public class MetaLoop {

	public static void decisionTurn() {
		ExtendedBoardModel board = MetaConfig.getBoardModel();

		//if the turn calculated from current is different from turn calculated from the abs time from the latest registered turnchange, alert piece
		ExtendedPlayerModel player = MetaConfig.getBoardModel().getPlayer();
		ExtendedTileModel tile = player.getControlledModel().getTilePosition();
		if (MetaClock.getTurn(tile.getAbsFraction(), player.getSide()) != MetaClock
				.getTurn(tile.getAbsFraction(), player.getSide(), player
						.getControlledModel().getAbsTime())) {
			player.getControlledModel().turnChange();
		}
		// handle input
		String inputSequence = MetaKeyboard.processInput();

		// decide and regret
		player.getControlledModel().decideAndRegret(inputSequence);

		// handle MetaActions acting on other piece models, here will come the
		// multiplayer, I think

		for (ExtendedPieceModel model : MetaConfig.getBoardModel()
				.getEntityModels().keySet()) {
			// if not player
			if (model != player.getControlledModel()) {
				// alert the piece the turn changed for it
				if (MetaClock.getTurn(tile.getAbsFraction(), model.getSide()) != MetaClock
						.getTurn(tile.getAbsFraction(), model.getSide(),
								model.getAbsTime())) {
					model.turnChange();
				}
				// check if piece is on a tile of decision from the server

				// make decision for this piece

			}
		}

	}
}
