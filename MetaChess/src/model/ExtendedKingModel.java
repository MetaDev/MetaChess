package model;

import java.util.Map;

import logic.DecisionExecutionLogic;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;

public class ExtendedKingModel extends ExtendedPieceModel {
	// when position changes regret active ranged decision

	// the balance of the decision 0<=balance<=weight
	// used as parameter when executing valueObject
	// the balance is how much weight is in a decision in favor of your side
	private int balance = 4;
	// list of pawns around the king
	private ExtendedPawnModel[] pawnWall = new ExtendedPawnModel[8];
	// position of the head of pawns around the king, they coil around the king
	// clockwise, with index 0 left bottom, indexing is counterclockwise
	private int pawnWallHeadPos = 0;
	private int wallSize = 0;

	public int getWallSize() {
		return wallSize;
	}

	
	public void addPawnToWallWithBoardIndex(ExtendedPawnModel pawn, int boardIndex) {
		pawnWall[getWallIndexOfPawnFromBoard(boardIndex)] = pawn;
		wallSize++;
		pawn.setBound(true);
	}

	public void removePawnFromWall(ExtendedPawnModel pawn, int index) {
		pawnWall[index] = null;
		wallSize--;
		pawn.setBound(false);
	}

	public void removePawnFromWall(ExtendedPawnModel pawn) {
		pawnWall[indexInWall(pawn)] = null;
		wallSize--;
		pawn.setBound(false);
	}

	public int getIndexOfPawnInWallOnBoard(ExtendedPawnModel pawn) {
		int indexInWall = indexInWall(pawn);
		// pawn not in wall
		if (indexInWall == -1) {
			return -1;
		}
		return (pawnWallHeadPos + indexInWall) % 8;
	}

	public ExtendedPawnModel getPawnFromBoardIndex(int boardIndex) {
		return pawnWall[getWallIndexOfPawnFromBoard(boardIndex)];
	}
	public ExtendedPawnModel getPawnFromWallIndex(int wallIndex) {
		return pawnWall[wallIndex];
	}
	public int getBoardIndexOfPawnInWall(int wallIndex) {
		return (pawnWallHeadPos + wallIndex) % 8;
	}

	public int getWallIndexOfPawnFromBoard(int boardIndex) {
		return (boardIndex - pawnWallHeadPos+8) % 8;
	}

	private int indexInWall(ExtendedPawnModel pawn) {
		for (int i = 0; i < 8; i++) {
			if (pawnWall[i] != null && pawnWall[i].equals(pawn)) {
				return i;
			}
		}
		return -1;
	}

	public int getPawnWallHeadPos() {
		return pawnWallHeadPos;
	}

	@Override
	public void setCommand(int decideOrRegret) {
		if (!MetaConfig.hasRegret(decideOrRegret)) {
			if (wallSize != 0) {
				setPawnWallHeadPos();
			} else {
				setBalance();
			}
		}

	}

	@Override
	public int getCommand() {
		if (wallSize != 0) {
			return getPawnWallHeadPos();
		} else {
			return getBalance();
		}
	}

	public void setPawnWallHeadPos() {
		int newPawnHeadPos = (this.pawnWallHeadPos + getRange()) % 8;
		Map<ExtendedPawnModel, ExtendedTileModel> newPositions = DecisionExecutionLogic
				.handlePawnAndKingTurnCollision(this, newPawnHeadPos);
		if (newPositions != null) {
			this.pawnWallHeadPos = newPawnHeadPos;
			// move all pawns
			for (ExtendedPawnModel pawn : newPositions.keySet()) {
				pawn.setTilePosition(newPositions.get(pawn));
			}
		}
	}

	public ExtendedKingModel(int side) {
		super(PieceType.KING, side, 32);
	}

	@Override
	public int getMovementRange() {
		return 1;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance() {
		this.balance = (this.balance + getRange()) % 8;
	}

	public int getInfluence() {
		return 8 - wallSize;
	}

}
