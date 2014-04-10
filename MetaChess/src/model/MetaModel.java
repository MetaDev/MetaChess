package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meta.MetaUtil;
import userinterface.TileGraphic;

//contains all models
public class MetaModel {
	private static Map<ExtendedPieceModel, TileGraphic> piecesOnBoard = new HashMap<>();
	private static Map<TileGraphic, String> activeMetaActions = new HashMap<>();
	private static List<ExtendedGUIModel> guiModels = new ArrayList<>();
	private static ExtendedBoardModel boardModel;
	private static ExtendedPlayerModel player;

	public static void setEntityModel(ExtendedPieceModel em,
			TileGraphic position) {
		if (piecesOnBoard == null)
			return;
		piecesOnBoard.put(em, position);
	}
	public static TileGraphic getPiecePosition(ExtendedPieceModel model){
		return piecesOnBoard.get(model);
	}
	public static ExtendedPieceModel getModelOnPosition(TileGraphic pos){
		return MetaUtil.getKeyByValue(piecesOnBoard, pos);
	}
	public static void addGuiModel(ExtendedGUIModel em) {
		if (guiModels == null)
			return;
		guiModels.add(em);
	}

	public static void setActiveMetaAction(String metaAction,
			TileGraphic position) {
		activeMetaActions.put(position,metaAction);
	}

	public static String getActiveMetaAction(TileGraphic position) {
		if (activeMetaActions.containsKey(position))
			activeMetaActions.get(position);
		return null;
	}

	public static Map<ExtendedPieceModel, TileGraphic> getEntityModels() {
		return piecesOnBoard;
	}

	public static List<ExtendedGUIModel> getGuiModels() {
		return guiModels;
	}

	public static ExtendedBoardModel getBoardModel() {
		return boardModel;
	}

	public static void setBoardModel(ExtendedBoardModel boardModel) {
		MetaModel.boardModel = boardModel;
	}

	public static ExtendedPlayerModel getPlayer() {
		return player;
	}

	public static void setPlayer(ExtendedPlayerModel player,
			TileGraphic position) {
		MetaModel.player = player;
		if (!piecesOnBoard.containsKey(player))
			piecesOnBoard.put(player, position);
	}

	public static void deleteModel(TileGraphic pos) {
		piecesOnBoard.remove(MetaUtil.getKeyByValue(piecesOnBoard, pos));
	}

	public static void deleteModel(ExtendedPieceModel model) {
		piecesOnBoard.remove(model);
	}
	

}
