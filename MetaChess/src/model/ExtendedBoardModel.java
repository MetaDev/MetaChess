package model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import decision.DecisionLogic;
import logic.MetaClock;
import meta.MetaUtil;

public class ExtendedBoardModel {
	//to save board decisions, save range, balance and side and parameter, tileview or maxrange
	
	//save a mapping of clientid and ExtendedPieceModel to know where all player are
	
	private ExtendedTileModel rootTile;
	// the pieces and there position on the board
	private Map<ExtendedPieceModel, ExtendedTileModel> piecesOnBoard = new ConcurrentHashMap<>();
	// the tile and name of board MetaAction
	// made this a concurrent hashmap because the activity is constantly update
	// while in the logic loop this map is read all the time
	private Map<ExtendedTileModel, String> activeMetaActions = new ConcurrentHashMap<>();
	// the piece which executed the board MetaAction
	private Map<ExtendedTileModel, ExtendedPieceModel> activeMetaActionsActor = new ConcurrentHashMap<>();
	// the time that past since execution
	private Map<ExtendedTileModel, Integer> activeMetaActionsTimeLeft = new ConcurrentHashMap<>();
	// the absolute time at the moment of execution, needed to check if passed
	// time has to be increased.
	private Map<ExtendedTileModel, Integer> activeMetaActionsTimeStamp = new ConcurrentHashMap<>();
	// map of team-lives, first integer is team 0/1
	private Map<Integer, Integer> teamLives = new ConcurrentHashMap<>();

	public Map<ExtendedPieceModel, ExtendedTileModel> getPiecesOnBoard() {
		return piecesOnBoard;
	}

	public Map<ExtendedTileModel, String> getActiveMetaActions() {
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
		if (!activeMetaActionsTimeLeft.isEmpty())
			return activeMetaActionsTimeLeft.get(pos);
		return 0;
	}

	private ExtendedPlayerModel player;

	public ExtendedPlayerModel getPlayer() {
		return player;
	}

	public ExtendedBoardModel(ExtendedTileModel rootTile) {
		this.rootTile = rootTile;
		teamLives.put(0, 16);
		teamLives.put(1, 16);
	}

	public int getTeamLives(int team) {
		return teamLives.get(team);
	}

	public void setTeamLives(int team, int lives) {
		teamLives.put(team, lives);
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

	public void setActiveDecision(String decision,
			ExtendedTileModel position, ExtendedPieceModel actor) {
		activeMetaActions.put(position, decision);
		activeMetaActionsActor.put(position, actor);
		//stays active while king stays on same place and keeps corresponding key down
		activeMetaActionsTimeLeft.put(position, 1);
		//won't be necessary once we work with absolute cooldown
		activeMetaActionsTimeStamp.put(position, actor.getAbsTime());
	}

	public void metaActionTurnChanged(ExtendedTileModel position) {
		if (activeMetaActionsTimeLeft.get(position) > 1) {
			activeMetaActionsTimeLeft.put(position,
					activeMetaActionsTimeLeft.get(position) - 1);
			// save timestamp, to check next time if turn changed
			activeMetaActionsTimeStamp.put(position,
					MetaClock.getAbsoluteTime());
		} else {
			activeMetaActionsTimeLeft.remove(position);
			activeMetaActionsActor.remove(position);
			activeMetaActions.remove(position);
			activeMetaActionsTimeStamp.remove(position);
		}

	}

	public String getActiveMetaAction(ExtendedTileModel position) {
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
			piecesOnBoard.put(player.getControlledModel(), position);
	}

	public void deleteModel(ExtendedTileModel pos) {
		piecesOnBoard.remove(MetaUtil.getKeyByValue(piecesOnBoard, pos));
	}

	public void deleteModel(ExtendedPieceModel model) {
		piecesOnBoard.remove(model);
	}

}
