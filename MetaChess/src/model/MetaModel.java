package model;

import graphic.PieceGraphic;
import graphic.TileGraphic;

import java.util.ArrayList;
import java.util.List;

//contains all models
public class MetaModel {
	private static List<PieceExtendedModel> pieceModels= new ArrayList<>();
	private static List<GUIExtendedModel> guiModels= new ArrayList<>();
	private static ExtendedModel boardModel;
	private static PlayerExtendedModel player;
	
	public static void addEntityModel(PieceExtendedModel em){
		if (pieceModels==null)
			return;
		pieceModels.add(em);
	}
	public static void addGuiModel(GUIExtendedModel em){
		if (guiModels==null)
			return;
		guiModels.add(em);
	}
	public static List<PieceExtendedModel> getEntityModels() {
		return pieceModels;
	}
	public static void setEntityModels(List<PieceExtendedModel> entityModels) {
		MetaModel.pieceModels = entityModels;
	}
	public static List<GUIExtendedModel> getGuiModels() {
		return guiModels;
	}
	
	public static ExtendedModel getBoardModel() {
		return boardModel;
	}
	public static void setBoardModel(ExtendedModel boardModel) {
		MetaModel.boardModel = boardModel;
	}
	public static PlayerExtendedModel getPlayer() {
		return player;
	}

	public static void setPlayer(PlayerExtendedModel player) {
		MetaModel.player = player;
		if (!pieceModels.contains(player))
			pieceModels.add(player);
	}
	public static void deleteModel(TileGraphic pos){
		for (PieceExtendedModel model: pieceModels){
			if (((PieceGraphic)model.getGraphic()).getTile()==pos){
				pieceModels.remove(model);
			}
		}
	}
	
	
}
