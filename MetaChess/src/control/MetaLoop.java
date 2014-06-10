/*
 * Dependencies: WorldModel
 * Responsibilities: handling the input from the user and pass it to all listening controls
 * Capabilities:
 */
package control;

import java.util.Map;

import decision.DecisionLogic;
import logic.MetaClock;
import meta.MetaConfig;
import model.ExtendedBoardModel;
import model.ExtendedPieceModel;
import model.ExtendedPlayerModel;
import model.ExtendedTileModel;

public class MetaLoop {

	public static void decisionTurn() {
		ExtendedBoardModel board = MetaConfig.getBoardModel();

		// TODO
		// if highest fraction reached, the piece won't be locked
		// anymore,
		// but the cooldwon still counts with
		// the min turn-time

		// alert the board if the turn changed for it's active MetaAcations
		for (Map.Entry<ExtendedTileModel, String> pair : MetaConfig
				.getBoardModel().getActiveMetaActions().entrySet()) {
			ExtendedTileModel tile = pair.getKey();
			ExtendedPieceModel model = board.getMetaActionActor(tile);

			if (MetaClock.getTurn(tile.absoluteFraction(), model.getSide()) != MetaClock
					.getTurn(tile.absoluteFraction(), model.getSide(),
							board.getMetaActionTimeStamp(tile))) {
				MetaConfig.getBoardModel().metaActionTurnChanged(tile);
			}
		}

		ExtendedPlayerModel player = MetaConfig.getBoardModel().getPlayer();
		ExtendedTileModel tile = player.getControlledModel().getTilePosition();
		if (MetaClock.getTurn(tile.absoluteFraction(), player.getSide()) != MetaClock
				.getTurn(tile.absoluteFraction(), player.getSide(), player
						.getControlledModel().getAbsTime())) {
			player.getControlledModel().turnChange();
		}
		// handle input
		String inputSequence = MetaKeyboard.processInput();

		//decide and regret
		player.getControlledModel().decideAndRegret(inputSequence);
		

		// handle MetaActions acting on other piece models, here will come the
		// multiplayer, I think

		for (ExtendedPieceModel model : MetaConfig.getBoardModel()
				.getEntityModels().keySet()) {
			// if not player
			if (model != player.getControlledModel()) {
				// alert the piece the turn changed for it
				if (MetaClock.getTurn(tile.absoluteFraction(), model.getSide()) != MetaClock
						.getTurn(tile.absoluteFraction(), model.getSide(),
								model.getAbsTime())) {
					model.turnChange();
				}
				// check if piece is on a tile of decision from the server

				// make decision for this piece

			}
		}

	}
}
