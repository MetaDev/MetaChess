package logic;

import meta.MetaConfig;
import meta.MetaConfig.PieceType;
import meta.MetaUtil;
import model.ExtendedKingModel;
import model.ExtendedPawnModel;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;
import model.paramobjects.ParamObject;
import network.NetworkMessages;
import network.client.MetaClient;

//geef builder mee omdat controller het hele object moet kunnen vernietigen
public class ActionLogic {

	public static void decisionMediator(int decideOrRegret, String type,
			ExtendedPieceModel model) {
		// NETWORKING
		// add message to client queue
		// todo: not all decisions have to be communicated

		if (MetaConfig.multiPlayer) {
			MetaClient.addOutMessage(NetworkMessages.decisionOutMessage(type,
					model));
		}

		// special decision
		if (MetaConfig.getSpecialsSet().keySet().contains(type)) {
			changeParam(decideOrRegret, type, model);
			return;
		}
		// diagonal or orthogonal step
		if (MetaConfig.getDiagonalSet().containsKey(type)
				|| MetaConfig.getOrthogonalSet().containsKey(type)) {
			step(type, model);
			return;
		}
		// horse step
		if (MetaConfig.getHorseSet().containsKey(type)) {
			horseStep(type, model);
			return;
		}

	}

	public static boolean movement(int i, int j, ExtendedPieceModel piece,
			boolean isSideStep) {

		ExtendedTileModel previousTile = MetaConfig.getBoardModel()
				.getPiecePosition(piece);
		ExtendedTileModel newTile = BoardLogic.getTileNeighbour(previousTile,
				i, j, BoardLogic.isHoover(), isSideStep,
				piece.isPenetrateLowerFraction());
		// movement did not succeed
		if (newTile == null)
			return false;
		// reset old influence of king
		piece.clearInfluence();
		// set new influence
		// search for king our king
		ExtendedKingModel king = (ExtendedKingModel) MetaConfig.getBoardModel()
				.getPieceByTypeAndSide(PieceType.KING, piece.getSide());
		if (king != null) {
			// king found, in range?
			if (BoardLogic.isInrange(piece, king)) {
				int effect = DecisionLogic.getEffect(king.getBalance(),
						king.getInfluence(),
						(king.getSide() + piece.getSide()) % 2);
				// set influence of king
				piece.setInfluence(effect);
			}

		}
		// if there's a piece on the new tile and it's from the opposite team
		ExtendedPieceModel pieceOnnewTile = MetaConfig.getBoardModel()
				.getModelOnPosition(newTile);
		if (pieceOnnewTile != null
				&& pieceOnnewTile.getSide() != piece.getSide()) {

			// the model is a dragon knight or it isn't a sidestep
			// and the target tile is occupied
			if ((piece.isDragon() != 0 || !isSideStep)) {

				// decrease teamlifes
				ExtendedPieceModel takenPiece = MetaUtil.getKeyByValue(
						MetaConfig.getBoardModel().getEntityModels(), newTile);
				MetaConfig.getBoardModel().decreaseSideLives(
						takenPiece.getSide(), takenPiece.getLives());
				// put the player in a free other piece, if there are left
				// get correct player from piece
				// networking
				MetaConfig.getSpecialsSet().get("SWITCH").setParam(1);
			}
		}
		// the piece on the new tile is from same side
		else {
			// check if pawn binding can be done
			if (piece.getType() == PieceType.KING
					&& pieceOnnewTile.getType() == PieceType.PAWN) {
				((ExtendedKingModel) piece)
						.addPawnToWall((ExtendedPawnModel) pieceOnnewTile);
			}
			// end movement
			return true;
		}
		// set new position for model
		MetaConfig.getBoardModel().setPiecePosition(piece, newTile);
		return true;
	}

	public static void step(String type, ExtendedPieceModel model) {
		// if the stepping model is a king, check if the pawns don't have to be
		// move with him
		//TODO, adapt algo: reposition the pawns with every movement-> move king, then position pawns on king then move according to the wall if possible
		if (model.getType() == PieceType.KING) {
			ExtendedKingModel king = (ExtendedKingModel) model;
			for (ExtendedPawnModel pawn : king.getPawnWall()) {
				int pawnWallIndex = king.getIndexOfPawnInWall(pawn);
				boolean sideStep = false;
				// the pawn in which his step would lead to step on the king can
				// ignore
				// occupation, just like the king
				// the pawn with the position opposite to the stepping direction
				if ((MetaConfig.getDirectionWithIndex((pawnWallIndex + 4) % 8)
						.equals(type))) {
					sideStep = true;
				}
				// if the move can't be made skip
				if (BoardLogic.getTileNeighbour(pawn.getTilePosition(),
						ExtendedKingModel.getIndexMapping()[pawnWallIndex][0],
						ExtendedKingModel.getIndexMapping()[pawnWallIndex][1],
						BoardLogic.isHoover(), sideStep,
						pawn.isPenetrateLowerFraction()) == null) {
					return;
				}

			}
			int[] direction;
			// if all moves are allowed make them
			for (ExtendedPawnModel pawn : king.getPawnWall()) {
				int pawnWallIndex = king.getIndexOfPawnInWall(pawn);
				direction = MetaConfig.getDirectionArray(MetaConfig.getDirectionWithIndex(pawnWallIndex), pawn);
				movement(direction[0] * pawn.getRange(),
						direction[1] * pawn.getRange(), pawn, true);
			}
			//also for the king
			direction = MetaConfig.getDirectionArray(type, model);
			movement(direction[0] * model.getRange(),
					direction[1] * model.getRange(), model, true);
			//and end
			return;
		}
		int[] direction = MetaConfig.getDirectionArray(type, model);
		movement(direction[0] * model.getRange(),
				direction[1] * model.getRange(), model, false);

	}

	// horsteStep
	public static void horseStep(String type, ExtendedPieceModel model) {
		// TODO to implement dragon, movent like 2,1 should be done 1,0 1,0 0,1
		String dir = type;

		int[] direction = MetaConfig.getDirectionArray(dir, model);

		if (movement(0, direction[1], model, true))
			movement(direction[0], 0, model, false);
	}

	// int decideOrRegret, tells wether the change of param is positive or
	// negative
	public static void changeParam(int decideOrRegret, String type,
			ExtendedPieceModel model) {
		// check if type of decision is allowed by piecetype
		if (MetaConfig.getKeyMapping().get(model.getType()).containsValue(type)) {
			ParamObject param = MetaConfig.getSpecialsSet().get(type);
			param.setParam(decideOrRegret);
		}

	}
}
