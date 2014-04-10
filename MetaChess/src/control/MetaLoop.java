/*
 * Dependencies: WorldModel
 * Responsibilities: handling the input from the user and pass it to all listening controls
 * Capabilities:
 */
package control;

import java.util.ArrayList;
import java.util.Map;

import logic.MetaClock;
import meta.MetaMapping;
import meta.MetaMapping.ControllerType;
import meta.MetaMapping.ControllerType.ControllerGroup;
import model.ExtendedBoardModel;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;
import model.TileGraphic;
import action.MetaAction;

public class MetaLoop {

	public static void decisionTurn() {
		ExtendedBoardModel board = MetaMapping.getBoardModel();
		
		//alert the board if the turn changed for it's active MetaAcations
		for(Map.Entry<ExtendedTileModel, MetaAction> pair: MetaMapping.getBoardModel().getActiveMetaActions().entrySet()){
			ExtendedTileModel tile = pair.getKey();
			MetaAction metaAction = pair.getValue();
			ExtendedPieceModel model = board.getMetaActionActor(metaAction);
			
			
			if (MetaClock.getTurn(tile.absoluteFraction(), model.getSide()) != MetaClock
					.getTurn(tile.absoluteFraction(), model.getSide(),
							board.getMetaActionTimeStamp(metaAction))) {
				MetaMapping.getBoardModel().metaActionTurnChanged(metaAction);
			}
		}
		//handle MetaActions acting on piece models
		for (ExtendedPieceModel model: MetaMapping.getBoardModel().getEntityModels().keySet()) {
			//an active piece
			ControllerType type = model.getControllerType();

			//alert the piece the turn changed for it
			ExtendedTileModel tile = MetaMapping.getBoardModel().getPiecePosition(model);
			if (MetaClock.getTurn(tile.absoluteFraction(), model.getSide()) != MetaClock
					.getTurn(tile.absoluteFraction(),model.getSide(),
							model.getAbsTime())) {
				model.turnChange();
			}

			
			//revert all active piece MetaAcions that turned inactive, included board MetaActions
			model.revertMetaActions();
			//act all MetaActions: execute piece MetaActions and board MetaActions , set range of ranged piece metaActions
			model.actMetaActions();
			
			// TODO
			// if highest fraction reached, the piece won't be locked anymore,
			// but the cooldwon still counts with
			// the min turn-time

			
			//change this
			//the model should check if makes a move not the metaloop
			
			// check if piece is allowed to take an action
			if (type != null) {
				// handle input
				if (type.isInGroup(ControllerGroup.INPUT)) {
					MetaKeyboard.processInput(model);
				}

			}
			
			// handle random AI
			// pass model to a AI class which will return a MetaAction to
			// execute for that model
			else if (type.isInGroup(ControllerGroup.RANDOMAI)) {

			}

		}

	}
}
