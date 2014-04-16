/*
 * Dependencies: WorldModel
 * Responsibilities: handling the input from the user and pass it to all listening controls
 * Capabilities:
 */
package control;

import java.util.Map;

import logic.MetaClock;
import meta.MetaMapping;
import model.ExtendedBoardModel;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;
import action.MetaAction;

public class MetaLoop {

	public static void decisionTurn() {
		ExtendedBoardModel board = MetaMapping.getBoardModel();

		// alert the board if the turn changed for it's active MetaAcations
		for (Map.Entry<ExtendedTileModel, MetaAction> pair : MetaMapping
				.getBoardModel().getActiveMetaActions().entrySet()) {
			ExtendedTileModel tile = pair.getKey();
			ExtendedPieceModel model = board.getMetaActionActor(tile);

			if (MetaClock.getTurn(tile.absoluteFraction(), model.getSide()) != MetaClock
					.getTurn(tile.absoluteFraction(), model.getSide(),
							board.getMetaActionTimeStamp(tile))) {
				System.out.println("board turn changed");
				MetaMapping.getBoardModel().metaActionTurnChanged(tile);
			}
		}
		// handle MetaActions acting on piece models
		for (ExtendedPieceModel model : MetaMapping.getBoardModel()
				.getEntityModels().keySet()) {
			// alert the piece the turn changed for it
			ExtendedTileModel tile = MetaMapping.getBoardModel()
					.getPiecePosition(model);
			if (MetaClock.getTurn(tile.absoluteFraction(), model.getSide()) != MetaClock
					.getTurn(tile.absoluteFraction(), model.getSide(),
							model.getAbsTime())) {
				model.turnChange();
			}
			// revert all active piece MetaAcions that turned inactive, included
			// board MetaActions
			model.revertMetaActions();
			// act all MetaActions: execute piece MetaActions and board
			// MetaActions , set range of ranged piece metaActions
			model.actMetaActions();

			// TODO
			// if highest fraction reached, the piece won't be locked anymore,
			// but the cooldwon still counts with
			// the min turn-time

		}

	}
}
