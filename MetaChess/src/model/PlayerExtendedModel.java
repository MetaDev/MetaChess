package model;

import graphic.Graphic;
import meta.MetaMapping.ControllerType;

//contains all info about player
public class PlayerExtendedModel extends PieceExtendedModel {
	private int nrOfViewTiles = 4;
	private String name = "Gray";

	public PlayerExtendedModel(Graphic graphic, ControllerType controllerType,
			int lives, int maxLives, int range) {
		super(graphic, controllerType, lives, maxLives, range);
	}

	public void setNrOfViewTiles(int nrOfViewTiles) {
		this.nrOfViewTiles = nrOfViewTiles;
	}

	public int getNrOfViewTiles() {
		return nrOfViewTiles;
	}
}
