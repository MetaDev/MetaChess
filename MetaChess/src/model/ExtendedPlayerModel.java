package model;

import userinterface.Graphic;
import meta.MetaMapping.ControllerType;
import meta.MetaMapping.PieceRendererType;

//contains all info about player
public class ExtendedPlayerModel extends ExtendedPieceModel {
	private int nrOfViewTiles = 4;
	private String name = "Gray";

	

	public void setNrOfViewTiles(int nrOfViewTiles) {
		this.nrOfViewTiles = nrOfViewTiles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExtendedPlayerModel(PieceRendererType renderType, int side,
			ControllerType controllerType, int lives, int maxLives, int range,
			int nrOfViewTiles, String name) {
		super(renderType, side, controllerType, lives, maxLives, range);
		this.nrOfViewTiles = nrOfViewTiles;
		this.name = name;
	}

	public int getNrOfViewTiles() {
		return nrOfViewTiles;
	}
}
