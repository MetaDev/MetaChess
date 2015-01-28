/*
 * Dependencies: WorldModel
 * Responsibilities: handling the input from the user and pass it to all listening controls
 * Capabilities:
 */
package control;

import meta.MetaClock;
import meta.MetaConfig;
import model.ExtendedBoardModel;
import model.ExtendedPieceModel;
import model.ExtendedPlayerModel;
import model.ExtendedTileModel;

public class MetaLoop {
       
	public static void update() {
		//update every piece model
		for (ExtendedPieceModel model : MetaConfig.getBoardModel()
				.getEntityModels().keySet()) {
			model.update();
		}

	}
}
