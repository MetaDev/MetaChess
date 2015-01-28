package model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import meta.MetaConfig.PieceType;
import meta.MetaUtil;

public class ExtendedBoardModel {

	// save a mapping of clientid and ExtendedPieceModel to know where all
	// player are

	private ExtendedTileModel rootTile;
	// the pieces and there position on the board
	private Map<ExtendedPieceModel, ExtendedTileModel> piecesOnBoard = new ConcurrentHashMap<>();

	private Map<Integer, Integer> teamLives = new ConcurrentHashMap<>();

	private List<ExtendedPlayerModel> otherPlayers;

	public Map<ExtendedPieceModel, ExtendedTileModel> getPiecesOnBoard() {
		return piecesOnBoard;
	}
	public ExtendedPieceModel getPieceByTypeAndSide(PieceType type,int side){
		for(ExtendedPieceModel piece:piecesOnBoard.keySet()){
			if(piece.getType()==type&& piece.getSide()==side){
				return piece;
			}
		}
		return null;
	}
	private ExtendedPlayerModel player;

	public ExtendedPlayerModel getPlayer() {
		return player;
	}

	public void setPlayer(ExtendedPlayerModel player) {
		this.player = player;
	}

	public ExtendedBoardModel(ExtendedTileModel rootTile) {
		this.rootTile = rootTile;
		teamLives.put(0, 32);
		teamLives.put(1, 32);
	}

	public int getSideLives(int team) {
		return teamLives.get(team);
	}

	public void decreaseSideLives(int team, int lives) {
		teamLives.put(team, getSideLives(team) - lives);
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

	public Map<ExtendedPieceModel, ExtendedTileModel> getEntityModels() {
		return piecesOnBoard;
	}



	public void deleteModel(ExtendedTileModel pos) {
		piecesOnBoard.remove(MetaUtil.getKeyByValue(piecesOnBoard, pos));
	}

	public void deleteModel(ExtendedPieceModel model) {
		piecesOnBoard.remove(model);
	}

}
