package model;

import meta.MetaMapping.ControllerType;
import meta.MetaMapping.PieceRendererType;

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
	//the model the player is in
	private ExtendedPieceModel controlledModel;
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExtendedPlayerModel(int side,ExtendedPieceModel controlledModel,
			 String name) {
		this.controlledModel = controlledModel;
		this.name = name;
		this.side=side;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	
}
