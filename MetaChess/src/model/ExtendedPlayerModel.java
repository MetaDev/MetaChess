package model;

import logic.BoardLogic;
import meta.MetaConfig;

//contains all info about player
public class ExtendedPlayerModel {
	public ExtendedPieceModel getControlledModel() {
		return controlledModel;
	}

	public void setControlledModel(ExtendedPieceModel controlledModel) {
		this.controlledModel = controlledModel;
	}

	private int side;
	private String name = "Gray";
	// the model the player is in
	private ExtendedPieceModel controlledModel;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExtendedPlayerModel(int side, ExtendedPieceModel controlledModel,
			String name) {
		this.controlledModel = controlledModel;
		this.name = name;
		this.side = side;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}
	//a mode can be defined, for switch, like switch to nearest, furthest or a priorityqueue with types can be made
	//will be implemented later
	//you can't switch to piece occupied by another player
	public void switchPiece(int mode) {
		double minDist=Double.MAX_VALUE;
		double tempDist;
		ExtendedPieceModel newPiece=null;
		if(mode==1){
			for(ExtendedPieceModel piece: MetaConfig.getBoardModel().getEntityModels().keySet()){
				if((tempDist=BoardLogic.calculateDistance(controlledModel.getTilePosition(), piece.getTilePosition()))<minDist){
					newPiece=piece;
					minDist=tempDist;
				}
			}
			controlledModel=newPiece;
		}
	}
}
