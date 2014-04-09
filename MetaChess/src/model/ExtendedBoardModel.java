package model;

import userinterface.TileGraphic;

public class ExtendedBoardModel {
 private TileGraphic rootTile;

public ExtendedBoardModel(TileGraphic rootTile) {
	this.rootTile = rootTile;
}

public TileGraphic getRootTile() {
	return rootTile;
}

public void setRootTile(TileGraphic rootTile) {
	this.rootTile = rootTile;
}
}
