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

	public ExtendedPawnModel[] getPawnWall() {
		return pawnWall;
	}

	public void addPawnToWall(ExtendedPawnModel pawn, int index) {
		pawnWall[index] = pawn;
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
		return (pawnWallHeadPos + indexInWall(pawn)) % 8;
	}

	public int getIndexOfPawnInWallOnBoard(int index) {
		return (pawnWallHeadPos + index) % 8;
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
		return wallSize;
	}

	public void setPawnWallHeadPos() {
		int newPawnHeadPos = (this.pawnWallHeadPos + getRange()) % 8;
		Map<ExtendedPawnModel, ExtendedTileModel> newPositions = DecisionExecutionLogic
				.handlePawnAndKingTurnCollision(this, newPawnHeadPos);
		if (newPositions != null) {
			this.pawnWallHeadPos = newPawnHeadPos;
			//move all pawns 
			for(ExtendedPawnModel pawn: newPositions.keySet()){
				pawn.setTilePosition(newPositions.get(pawn));
			}
		}
	}

	// the model is thinking about a decision
	private String pendingDecision;
	// direction for ranged decision
	private String direction;

	@Override
	public String getPendingDecision() {
		return pendingDecision;
	}

	public void setPendingDecision(String pendingDecision) {
		this.pendingDecision = pendingDecision;
	}

	public ExtendedKingModel(int side) {
		super(PieceType.KING, side, 32);
	}

	@Override
	public int getRange() {
		return 1;
	}

	@Override
	public int getMaxRange() {
		return 1;
	}

	@Override
	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String getDirection() {
		return direction;
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
