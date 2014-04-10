package model;

import java.util.HashMap;
import java.util.Map;

import meta.MetaUtil;
import action.MetaAction;

public class ExtendedBoardModel {
	private ExtendedTileModel rootTile;
	// the pieces and there position on the board
	private Map<ExtendedPieceModel, ExtendedTileModel> piecesOnBoard = new HashMap<>();
	// the tile and name of board MetaAction
	private Map<ExtendedTileModel, MetaAction> activeMetaActions = new HashMap<>();
	// the piece which executed the board MetaAction
	private Map<MetaAction, ExtendedPieceModel> activeMetaActionsActor = new HashMap<>();
	// the time that past since execution
	private Map<MetaAction, Integer> activeMetaActionsTimeLeft = new HashMap<>();
	// the absolute time at the moment of execution, needed to check if passed
	// time has to be increased.
	private Map<MetaAction, Integer> activeMetaActionsTimeStamp = new HashMap<>();

	public Map<ExtendedPieceModel, ExtendedTileModel> getPiecesOnBoard() {
		return piecesOnBoard;
	}

	public Map<ExtendedTileModel, MetaAction> getActiveMetaActions() {
		return activeMetaActions;
	}

	public Map<MetaAction, ExtendedPieceModel> getActiveMetaActionsActor() {
		return activeMetaActionsActor;
	}

	public Map<MetaAction, Integer> getActiveMetaActionsPassedTime() {
		return activeMetaActionsTimeLeft;
	}

	public Map<MetaAction, Integer> getActiveMetaActionsTimeStamp() {
		return activeMetaActionsTimeStamp;
	}

	private ExtendedPlayerModel player;

	public ExtendedPlayerModel getPlayer() {
		return player;
	}

	public ExtendedBoardModel(ExtendedTileModel rootTile) {
		this.rootTile = rootTile;
	}

	public ExtendedTileModel getRootTile() {
		return rootTile;
	}

	public void setRootTile(ExtendedTileModel rootTile) {
		this.rootTile = rootTile;
	}

	public void setPiecePosition(ExtendedPieceModel em,
			ExtendedTileModel position) {
		if (piecesOnBoard == null)
			return;
		piecesOnBoard.put(em, position);
	}

	public ExtendedTileModel getPiecePosition(ExtendedPieceModel model) {
		return piecesOnBoard.get(model);
	}

	public ExtendedPieceModel getModelOnPosition(ExtendedTileModel pos) {
		return MetaUtil.getKeyByValue(piecesOnBoard, pos);
	}

	public void setActiveMetaAction(MetaAction metaAction,
			ExtendedTileModel position, ExtendedPieceModel actor) {
		activeMetaActions.put(position, metaAction);
		activeMetaActionsActor.put(metaAction, actor);
		activeMetaActionsTimeLeft.put(metaAction, 0);
		activeMetaActionsTimeStamp.put(metaAction, actor.getAbsTime());
	}

	public void metaActionTurnChanged(MetaAction metaAction) {
		if (activeMetaActionsTimeLeft.get(metaAction) > 1) {
			activeMetaActionsTimeLeft.put(metaAction,
					activeMetaActionsTimeLeft.get(metaAction) - 1);
		} else {
			activeMetaActionsTimeLeft.remove(metaAction);
		}

	}

	public MetaAction getActiveMetaAction(ExtendedTileModel position) {
		if (activeMetaActions.containsKey(position))
			return activeMetaActions.get(position);
		return null;
	}

	public ExtendedPieceModel getMetaActionActor(MetaAction metaAction) {
		return activeMetaActionsActor.get(metaAction);
	}
	public int getMetaActionTimeStamp(MetaAction metaAction){
		return activeMetaActionsTimeStamp.get(metaAction);
	}

	public Map<ExtendedPieceModel, ExtendedTileModel> getEntityModels() {
		return piecesOnBoard;
	}

	public void setPlayerPosition(ExtendedPlayerModel player,
			ExtendedTileModel position) {
		this.player = player;
		if (!piecesOnBoard.containsKey(player))
			piecesOnBoard.put(player, position);
	}

	public void deleteModel(ExtendedTileModel pos) {
		piecesOnBoard.remove(MetaUtil.getKeyByValue(piecesOnBoard, pos));
	}

	public void deleteModel(ExtendedPieceModel model) {
		piecesOnBoard.remove(model);
	}

}
