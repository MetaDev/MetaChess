package model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import logic.MetaClock;
import meta.MetaUtil;
import action.MetaAction;

public class ExtendedBoardModel {
	private ExtendedTileModel rootTile;
	// the pieces and there position on the board
	private Map<ExtendedPieceModel, ExtendedTileModel> piecesOnBoard = new ConcurrentHashMap<>();
	// the tile and name of board MetaAction
	// made this a concurrent hashmap because the activity is constantly update while in the logic loop this map is read all the time
	private Map<ExtendedTileModel, MetaAction> activeMetaActions = new ConcurrentHashMap <>();
	// the piece which executed the board MetaAction
	private Map<ExtendedTileModel, ExtendedPieceModel> activeMetaActionsActor = new ConcurrentHashMap<>();
	// the time that past since execution
	private Map<ExtendedTileModel, Integer> activeMetaActionsTimeLeft = new ConcurrentHashMap<>();
	// the absolute time at the moment of execution, needed to check if passed
	// time has to be increased.
	private Map<ExtendedTileModel, Integer> activeMetaActionsTimeStamp = new ConcurrentHashMap<>();

	public Map<ExtendedPieceModel, ExtendedTileModel> getPiecesOnBoard() {
		return piecesOnBoard;
	}

	public Map<ExtendedTileModel, MetaAction> getActiveMetaActions() {
		return activeMetaActions;
	}

	public Map<ExtendedTileModel, ExtendedPieceModel> getActiveMetaActionsActor() {
		return activeMetaActionsActor;
	}

	public Map<ExtendedTileModel, Integer> getActiveMetaActionsTimeLeft() {
		return activeMetaActionsTimeLeft;
	}

	public Map<ExtendedTileModel, Integer> getActiveMetaActionsTimeStamp() {
		return activeMetaActionsTimeStamp;
	}

	public int getActiveMetaActionTimeLeft(ExtendedTileModel pos) {
		return activeMetaActionsTimeLeft.get(pos);
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
			ExtendedTileModel position, ExtendedPieceModel actor, int turnsOfActivity) {
		activeMetaActions.put(position, metaAction);
		activeMetaActionsActor.put(position, actor);
		activeMetaActionsTimeLeft.put(position, turnsOfActivity);
		activeMetaActionsTimeStamp.put(position, actor.getAbsTime());
	}
	
	public void metaActionTurnChanged(ExtendedTileModel position) {
		System.out.println(activeMetaActionsTimeLeft.get(position)+"  "+ position);
		if (activeMetaActionsTimeLeft.get(position) > 1) {
			activeMetaActionsTimeLeft.put(position,
					activeMetaActionsTimeLeft.get(position) - 1);
			//save timestamp, to check next time if turn changed
			activeMetaActionsTimeStamp.put(position, MetaClock.getAbsoluteTime());
			System.out.println(activeMetaActionsTimeLeft.get(position));
		} else {
			System.out.println("test");
			activeMetaActionsTimeLeft.remove(position);
			activeMetaActionsActor.remove(position);
			activeMetaActions.remove(position);
			activeMetaActionsTimeStamp.remove(position);
		}
		

	}

	public MetaAction getActiveMetaAction(ExtendedTileModel position) {
		if (activeMetaActions.containsKey(position))
			return activeMetaActions.get(position);
		return null;
	}

	public ExtendedPieceModel getMetaActionActor(ExtendedTileModel position) {
		return activeMetaActionsActor.get(position);
	}

	public int getMetaActionTimeStamp(ExtendedTileModel position) {
		return activeMetaActionsTimeStamp.get(position);
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
