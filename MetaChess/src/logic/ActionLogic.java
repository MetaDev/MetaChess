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
		if (pieceOnnewTile != null) {
			if (pieceOnnewTile.getSide() != piece.getSide()) {

				// the model is a dragon knight or it isn't a sidestep
				// and the target tile is occupied
				if ((piece.isDragon() != 0 || !isSideStep)) {

					// decrease teamlifes
					ExtendedPieceModel takenPiece = MetaUtil.getKeyByValue(
							MetaConfig.getBoardModel().getEntityModels(),
							newTile);
					MetaConfig.getBoardModel().decreaseSideLives(
							takenPiece.getSide(), takenPiece.getLives());
					// put player in other piece and delete piece form play if
					// on main board
					if (takenPiece.getTilePosition().getAbsFraction() == 8) {
						MetaConfig.getBoardModel().deleteModel(takenPiece);
					} else {
						MetaConfig.getBoardModel().setPiecePosition(
								takenPiece,
								BoardLogic.getRandomTile(
										MetaClock.getMaxFraction(), false));
					}

					//
					// TODO networking
					// switch on correct player the switch under
					// MetaConfig.getSpecialsSet().get("SWITCH").setParam(1);
					System.out.println("killed piece");

				}
			}
			// the piece on the new tile is from same side
			else {
				return false;
			}
		}
		// set new position for model
		MetaConfig.getBoardModel().setPiecePosition(piece, newTile);
		return true;
	}

	public static void step(String type, ExtendedPieceModel model) {
		// TODO apply influence to pawns, pawns in wall always are under
		// influence, when moving king, check of first of
		int[] direction = MetaConfig.getDirectionArray(type, model);
		// if the stepping model is a king, check if it's pawns can be moved
		// with him

		if (model.getType() == PieceType.KING) {
			ExtendedKingModel king = (ExtendedKingModel) model;
			ExtendedTileModel kingPos = king.getTilePosition();
			ExtendedTileModel newKingPos = BoardLogic.getTileNeighbour(kingPos,
					direction[0], direction[1], false);
			// king makes a legal move, if king moves on a pawn not in wall
			// add to wall
			if (newKingPos != null) {
				ExtendedPieceModel piecOnKingNewPos = MetaConfig
						.getBoardModel().getModelOnPosition(newKingPos);
				// check if any condition of the kings's new tile could end the
				// movement
				if (piecOnKingNewPos != null) {
					// the piece on the king new position is a pawn
					if (piecOnKingNewPos.getType() == PieceType.PAWN) {
						// pawn is not in wall
						if (king.getIndexOfPawnInWallOnBoard((ExtendedPawnModel) piecOnKingNewPos) != -1) {
							// add to wall
							// end movement
							return;
						}// movement is allowed if pawn already in wall
					} else if (piecOnKingNewPos.getSide() == king.getSide()) {
						// not a pawn and from same side
						// end movement
						return;
					}

				}
				if (king.getWallSize() > 0) {
					// save the new legal positions of the pawns
					ExtendedTileModel[] newPawnPositions = new ExtendedTileModel[8];

					// check if every pawn around the king can be moved
					for (int i = 0; i < 8; i++) {
						ExtendedPawnModel pawn = king.getPawnWall()[i];
						// there's a pawn
						if (pawn != null) {
							// position of pawn in wall
							int[] pawnPosInWall = MetaConfig.getDirectionArray(
									MetaConfig.getDirectionWithIndex(king
											.getIndexOfPawnInWallOnBoard(i)),
									pawn);
							// get new pawn position, now we allow that a tile
							// would be occupied
							ExtendedTileModel newPawnPos = BoardLogic
									.getTileNeighbour(newKingPos,
											pawnPosInWall[0], pawnPosInWall[1],
											true);
							ExtendedPieceModel pieceOnnewTile = MetaConfig
									.getBoardModel().getModelOnPosition(
											newPawnPos);
							// if occupied by king ,pawn in wall or nothing
							if (pieceOnnewTile == null
									|| pieceOnnewTile == king
									|| (pieceOnnewTile.getType() == PieceType.PAWN && king
											.getIndexOfPawnInWallOnBoard((ExtendedPawnModel) pieceOnnewTile) != -1)) {
								// legal position
								newPawnPositions[i] = newPawnPos;
							} else {
								// one of the tile moves if illegal, the move is
								// illegal
								return;
							}

						}
					}

				}
				// handle king move
				// if code is reached until here all pawn wall movement is legal
				
				// if there's a piece on the kings new position and it's not of his side, kill 
				if (piecOnKingNewPos!= null && piecOnKingNewPos.getSide() != king.getSide()) {
					// kill piece

				}
				// everything is checked, move king and it's wall of pawns

			}
		} else {
			// normal movement
			movement(direction[0] * model.getRange(),
					direction[1] * model.getRange(), model, false);
		}

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
