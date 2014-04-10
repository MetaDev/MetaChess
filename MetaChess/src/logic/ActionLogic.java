package logic;

import meta.MetaMapping;
import model.ExtendedPieceModel;
import model.ExtendedPlayerModel;
import model.ExtendedTileModel;

//geef builder mee omdat controller het hele object moet kunnen vernietigen
public class ActionLogic implements Logic {

	public static void SWITCHMOVTODEC(ExtendedPieceModel model) {
		model.setDecisionRange(model.getRange());
		model.setRange(0);
	}

	public static void SWITCHDECTOMOV(ExtendedPieceModel model) {
		model.setRange(model.getDecisionRange());
		model.setDecisionRange(0);
	}

	public static void RANGEPLUS2(ExtendedPieceModel model) {
		model.setRange(model.getRange() + 2);
	}

	public static void RANGEMIN2(ExtendedPieceModel model) {
		model.setRange(model.getRange() - 2);
	}

	public static void RANGEPLUS4(ExtendedPieceModel model) {
		model.setRange(model.getRange() + 4);
	}

	public static void RANGEMIN4(ExtendedPieceModel model) {
		model.setRange(model.getRange() - 4);
	}

	public static void RANGEPLUS8(ExtendedPieceModel model) {
		model.setRange(model.getRange() + 8);
	}

	public static void RANGEMIN8(ExtendedPieceModel model) {
		model.setRange(model.getRange() - 8);
	}

	public static void RANGEPLUS1(ExtendedPieceModel model) {
		model.setRange(model.getRange() + 1);
	}

	public static void RANGEMIN1(ExtendedPieceModel model) {
		model.setRange(model.getRange() - 1);
	}

	public static void RANGE0(ExtendedPieceModel model) {
		model.setRange(1);
	}

	public static void UPRIGHT(ExtendedPieceModel model) {
		movement(model.getRange(), model.getRange(), model, false);
	}

	public static void UPLEFT(ExtendedPieceModel model) {
		movement(-model.getRange(), model.getRange(), model, false);
	}

	public static void DOWNLEFT(ExtendedPieceModel model) {
		movement(-model.getRange(), -model.getRange(), model, false);
	}

	public static void DOWNRIGHT(ExtendedPieceModel model) {
		movement(model.getRange(), -model.getRange(), model, false);
	}

	public static void UP(ExtendedPieceModel model) {
		movement(0, model.getRange(), model, false);
	}

	public static void DOWN(ExtendedPieceModel model) {
		movement(0, -model.getRange(), model, false);
	}

	public static void LEFT(ExtendedPieceModel model) {
		movement(-model.getRange(), 0, model, false);
	}

	public static void RIGHT(ExtendedPieceModel model) {
		movement(model.getRange(), 0, model, false);
	}

	// mode is the horizontal movement
	// horizontal and vertical movement are seperated, because a diagonal move
	// behaves different, can be altered if desired, only the full 'L' is
	// allowed
	public static void UPLEFT21(ExtendedPieceModel model) {
		if (movement(0, model.getRange() * 2, model, true))
			movement(-model.getRange(), 0, model, false);
	}

	public static void UPLEFT12(ExtendedPieceModel model) {
		if (movement(0, model.getRange(), model, true))
			movement(-model.getRange() * 2, 0, model, false);
	}

	public static void UPRIGHT21(ExtendedPieceModel model) {
		if (movement(0, model.getRange() * 2, model, true))
			movement(model.getRange(), 0, model, false);
	}

	public static void UPRIGHT12(ExtendedPieceModel model) {
		if (movement(0, model.getRange(), model, true))
			movement(model.getRange() * 2, 0, model, false);
	}

	public static void DOWNRIGHT12(ExtendedPieceModel model) {
		if (movement(0, -model.getRange(), model, true))
			movement(model.getRange() * 2, 0, model, false);
	}

	public static void DOWNRIGHT21(ExtendedPieceModel model) {
		if (movement(0, -model.getRange() * 2, model, true))
			movement(model.getRange(), 0, model, false);
	}

	public static void DOWNLEFT21(ExtendedPieceModel model) {
		if (movement(0, -model.getRange() * 2, model, true))
			movement(-model.getRange(), 0, model, false);
	}

	public static void DOWNLEFT12(ExtendedPieceModel model) {
		if (movement(0, -model.getRange(), model, true))
			movement(-model.getRange() * 2, 0, model, false);
	}

	public static void TILEVIEWUP(ExtendedPieceModel model) {
		((ExtendedPlayerModel) model)
				.setNrOfViewTiles(((ExtendedPlayerModel) model)
						.getNrOfViewTiles() + 1);
	}

	public static void TILEVIEWDOWN(ExtendedPieceModel model) {
		((ExtendedPlayerModel) model)
				.setNrOfViewTiles(((ExtendedPlayerModel) model)
						.getNrOfViewTiles() - 1);
	}

	public static void PENETRATELFTILE(ExtendedPieceModel model) {
		model.setPenetrateLowerFraction(true);
	}

	public static void NPENETRATELFTILE(ExtendedPieceModel model) {
		model.setPenetrateLowerFraction(false);
	}

	public static boolean movement(int i, int j, ExtendedPieceModel model,
			boolean isSideStep) {
		model.setDirection(i, j);
		ExtendedTileModel previousTile = MetaMapping.getBoardModel()
				.getPiecePosition(model);
		ExtendedTileModel newTile = BoardLogic.getTileNeighbour(previousTile,
				i, j, BoardLogic.isHoover(), model.isIgnoreOccupationOfTile(),
				model.isPenetrateLowerFraction());
		// movement did not succeed
		if (newTile == null)
			return false;
		// tile is occupied and the movement isn't a sidestep->there doesn't
		// immediately follow a next move
		if (!isSideStep
				&& MetaMapping.getBoardModel().getModelOnPosition(newTile) != null) {
			// here a piece loses a life or more or get's killed
			// when killed the Metamodel should be alerted

			// delete piece model
			// should this be the player, then it's game over
			MetaMapping.getBoardModel().deleteModel(newTile);

		}

		// set new position for model
		MetaMapping.getBoardModel().setPiecePosition(model, newTile);
		return true;
	}

}
