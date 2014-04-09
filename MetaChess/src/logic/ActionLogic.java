package logic;

import model.PieceExtendedModel;
import model.PlayerExtendedModel;
import graphic.PieceGraphic;
import graphic.TileGraphic;

//geef builder mee omdat controller het hele object moet kunnen vernietigen
public class ActionLogic implements Logic {

	public static void SWITCHMOVTODEC(PieceExtendedModel model) {
		model.setDecisionRange(model.getRange());
		model.setRange(0);
	}

	public static void SWITCHDECTOMOV(PieceExtendedModel model) {
		model.setRange(model.getDecisionRange());
		model.setDecisionRange(0);
	}

	public static void RANGEPLUS2(PieceExtendedModel model) {
		model.setRange(model.getRange() + 2);
	}

	public static void RANGEMIN2(PieceExtendedModel model) {
		model.setRange(model.getRange() - 2);
	}

	public static void RANGEPLUS4(PieceExtendedModel model) {
		model.setRange(model.getRange() + 4);
	}

	public static void RANGEMIN4(PieceExtendedModel model) {
		model.setRange(model.getRange() - 4);
	}

	public static void RANGEPLUS8(PieceExtendedModel model) {
		model.setRange(model.getRange() + 8);
	}

	public static void RANGEMIN8(PieceExtendedModel model) {
		model.setRange(model.getRange() - 8);
	}

	public static void RANGEPLUS1(PieceExtendedModel model) {
		model.setRange(model.getRange() + 1);
	}

	public static void RANGEMIN1(PieceExtendedModel model) {
		model.setRange(model.getRange() - 1);
	}

	public static void RANGE0(PieceExtendedModel model) {
		model.setRange(1);
	}

	public static void UPRIGHT(PieceExtendedModel model) {
		movement(model.getRange(), model.getRange(), model, false);
	}

	public static void UPLEFT(PieceExtendedModel model) {
		movement(-model.getRange(), model.getRange(), model, false);
	}

	public static void DOWNLEFT(PieceExtendedModel model) {
		movement(-model.getRange(), -model.getRange(), model, false);
	}

	public static void DOWNRIGHT(PieceExtendedModel model) {
		movement(model.getRange(), -model.getRange(), model, false);
	}

	public static void UP(PieceExtendedModel model) {
		movement(0, model.getRange(), model, false);
	}

	public static void DOWN(PieceExtendedModel model) {
		movement(0, -model.getRange(), model, false);
	}

	public static void LEFT(PieceExtendedModel model) {
		movement(-model.getRange(), 0, model, false);
	}

	public static void RIGHT(PieceExtendedModel model) {
		movement(model.getRange(), 0, model, false);
	}

	// mode is the horizontal movement
	// horizontal and vertical movement are seperated, because a diagonal move
	// behaves different, can be altered if desired, only the full 'L' is
	// allowed
	public static void UPLEFT21(PieceExtendedModel model) {
		if (movement(0, model.getRange() * 2, model, true))
			movement(-model.getRange(), 0, model, false);
	}

	public static void UPLEFT12(PieceExtendedModel model) {
		if (movement(0, model.getRange(), model, true))
			movement(-model.getRange() * 2, 0, model, false);
	}

	public static void UPRIGHT21(PieceExtendedModel model) {
		if (movement(0, model.getRange() * 2, model, true))
			movement(model.getRange(), 0, model, false);
	}

	public static void UPRIGHT12(PieceExtendedModel model) {
		if (movement(0, model.getRange(), model, true))
			movement(model.getRange() * 2, 0, model, false);
	}

	public static void DOWNRIGHT12(PieceExtendedModel model) {
		if (movement(0, -model.getRange(), model, true))
			movement(model.getRange() * 2, 0, model, false);
	}

	public static void DOWNRIGHT21(PieceExtendedModel model) {
		if (movement(0, -model.getRange() * 2, model, true))
			movement(model.getRange(), 0, model, false);
	}

	public static void DOWNLEFT21(PieceExtendedModel model) {
		if (movement(0, -model.getRange() * 2, model, true))
			movement(-model.getRange(), 0, model, false);
	}

	public static void DOWNLEFT12(PieceExtendedModel model) {
		if (movement(0, -model.getRange(), model, true))
			movement(-model.getRange() * 2, 0, model, false);
	}

	public static void TILEVIEWUP(PieceExtendedModel model) {
		((PlayerExtendedModel) model)
				.setNrOfViewTiles(((PlayerExtendedModel) model)
						.getNrOfViewTiles() + 1);
	}

	public static void TILEVIEWDOWN(PieceExtendedModel model) {
		((PlayerExtendedModel) model)
				.setNrOfViewTiles(((PlayerExtendedModel) model)
						.getNrOfViewTiles() - 1);
	}

	public static void PENETRATELFTILE(PieceExtendedModel model) {
		model.setPenetrateLowerFraction(true);
	}

	public static void NPENETRATELFTILE(PieceExtendedModel model) {
		model.setPenetrateLowerFraction(false);
	}

	public static boolean movement(int i, int j, PieceExtendedModel model,
			boolean isSideStep) {
		model.setDirection(i, j);
		PieceGraphic graphic = ((PieceGraphic) model.getGraphic());
		TileGraphic tile = BoardLogic.getTileNeighbour(graphic.getTile(), i, j,
				BoardLogic.isHoover(), model.isIgnoreOccupationOfTile(),
				model.isPenetrateLowerFraction());
		// movement did not succeed
		if (tile == null)
			return false;
		// tile is occupied and the movement isn't a sidestep->there doesn't
		// immediately follow a next move
		if (!isSideStep && tile.getPiece() != null) {
			// here a piece loses a life or more or get's killed
			// when killed the Metamodel should be alerted

			// delete current piece on new tile
			tile.getPiece().setTile(null);

		}
		graphic.setTile(tile);
		// tile.setPiece(model); done automatic
		return true;
	}

}
