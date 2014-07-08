package model;

import java.util.Random;

import logic.BoardLogic;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;

//contains all info about player
public class ExtendedPlayerModel {
	private int[][] core;

	public ExtendedPieceModel getControlledModel() {
		return controlledModel;
	}

	public void setControlledModel(ExtendedPieceModel controlledModel) {
		this.controlledModel = controlledModel;
		// unbind if current piece is a pawn and it is bound
		if (controlledModel.getType() == PieceType.PAWN) {
			((ExtendedKingModel) MetaConfig.getBoardModel()
					.getPieceByTypeAndSide(PieceType.KING, getSide()))
					.removePawnFromWall((ExtendedPawnModel) controlledModel);

		}
	}

	private String name;
	// the model the player is in
	private ExtendedPieceModel controlledModel;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExtendedPlayerModel(int side, ExtendedPieceModel controlledModel,
			String name, int[][] core) {
		this.controlledModel = controlledModel;
		this.name = name;
		this.core = core;
	}

	// the side of the player is what side the first piece of control is
	public int getSide() {
		return controlledModel.getSide();
	}


	// a mode can be defined, for switch, like switch to nearest, furthest or a
	// priorityqueue with types can be made
	// will be implemented later
	// you can't switch to piece occupied by another player
	// the switch algorythm is defined by the range given
	// if mode=0 this means the switch key has been released, mode=1 is the
	// switch key pressed
	public void switchPiece(int in) {
		double minDist = Double.MAX_VALUE;
		double tempDist;
		ExtendedPieceModel newPiece = null;
		int mode = in * controlledModel.getRange();
		// random
		if (mode == 1) {
			Random generator = new Random();
			Object[] entries = MetaConfig.getBoardModel().getEntityModels()
					.keySet().toArray();
			controlledModel = (ExtendedPieceModel) entries[generator
					.nextInt(entries.length)];
		}
		// nearest
		else if (mode == 2) {
			for (ExtendedPieceModel piece : MetaConfig.getBoardModel()
					.getEntityModels().keySet()) {
				if (controlledModel != piece
						&& (tempDist = BoardLogic.calculateDistance(
								controlledModel.getTilePosition(),
								piece.getTilePosition())) < minDist) {
					newPiece = piece;
					minDist = tempDist;
				}
			}
			controlledModel = newPiece;
		}
	}

	public int[][] getCore() {
		return core;
	}

	public void setCore(int[][] core) {
		this.core = core;
	}
}
