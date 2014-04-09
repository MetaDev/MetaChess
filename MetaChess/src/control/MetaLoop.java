/*
 * Dependencies: WorldModel
 * Responsibilities: handling the input from the user and pass it to all listening controls
 * Capabilities:
 */
package control;

import logic.MetaClock;
import meta.MetaMapping.ControllerType;
import meta.MetaMapping.ControllerType.ControllerGroup;
import model.ExtendedPieceModel;
import model.MetaModel;
import userinterface.TileGraphic;

public class MetaLoop {

	public static void decisionTurn() {
		for (ExtendedPieceModel model: MetaModel.getEntityModels().keySet()) {
			ControllerType type = model.getControllerType();

			// check if the turn changed for the model
			// if so, call the change method and set new absolute turn and
			// frationOnTurnChange

			TileGraphic tile = MetaModel.getPiecePosition(model);
			// save absolute time
			// then check if the turn changed based on that
			// check if turn changed based on absolute turntime and fraction
			if (MetaClock.getTurn(tile.absoluteFraction(), tile.getColor()) != MetaClock
					.getTurn(tile.absoluteFraction(), tile.getColor(),
							model.getAbsTime())) {
				model.turnChange();
			}
			//revert all inactive MetaAcions
			model.revertMetaActions();
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
