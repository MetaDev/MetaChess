package logic;

import meta.MetaConfig;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;

public class PieceLogic {
	public static void setPosition(ExtendedPieceModel model, int[] I, int[] J) {
		MetaConfig.getBoardModel().setPiecePosition(model,
				BoardLogic.getTile(I, J));
	}
	public static void setPosition(ExtendedPieceModel model, ExtendedTileModel tile) {
		MetaConfig.getBoardModel().setPiecePosition(model,
				tile);
	}
}
