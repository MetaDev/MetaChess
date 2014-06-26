package logic;

import meta.MetaConfig;
import meta.MetaConfig.PieceType;
import meta.MetaUtil;
import model.ExtendedKingModel;
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

	public static boolean movement(int i, int j, ExtendedPieceModel model,
			boolean isSideStep) {
		// if there's a pending decision, you won't make a movement but
		// a decision in the direction
		if (model.getPendingDecision() != null) {
			return false;
		}

		ExtendedTileModel previousTile = MetaConfig.getBoardModel()
				.getPiecePosition(model);
		ExtendedTileModel newTile = BoardLogic.getTileNeighbour(previousTile,
				i, j, BoardLogic.isHoover(), model.isIgnoreOccupationOfTile(),
				model.isPenetrateLowerFraction());
		// movement did not succeed
		if (newTile == null)
			return false;
		// reset influence of king
		model.clearInfluence();
		// check if a king is within range of view
		for (ExtendedPieceModel piece : MetaConfig.getBoardModel()
				.getEntityModels().keySet()) {
			if (piece.getType() == PieceType.KING) {
				if (BoardLogic.isInrange(model, piece)) {
					int effect = DecisionLogic.getEffect(
							((ExtendedKingModel) piece).getBalance(),
							((ExtendedKingModel) piece).getInfluence(),
							(piece.getSide() + model.getSide()) % 2);
					// set influence of king
					model.setInfluence(effect);
				}
			}
		}
		// the model is a dragon knight or it isn't a sidestep
		// and the target tile is occupied
		if ((model.isDragon()==1 || !isSideStep)
				&& MetaConfig.getBoardModel().getModelOnPosition(newTile) != null) {
			
			// decrease teamlifes
			ExtendedPieceModel takenPiece = MetaUtil.getKeyByValue(MetaConfig
					.getBoardModel().getEntityModels(), newTile);
			MetaConfig.getBoardModel().decreaseSideLives(takenPiece.getSide(),
					takenPiece.getLives());
			// put the player in a free other piece, if there are left
			MetaConfig.getSpecialsSet().get("SWITCH").setParam(1);
		}

		// set new position for model
		MetaConfig.getBoardModel().setPiecePosition(model, newTile);
		return true;
	}

	public static void step(String type, ExtendedPieceModel model) {
		// if there's a pending decision
		// pending decision are for ranged decisions, which are for the king
		model.setDirection(type);
		// for king
		if (model.getPendingDecision() != null) {
			// execute pending decision
			DecisionLogic.decide(model.getPendingDecision(), model);
			// model.setPendingDecision(null);
		} else {
			String dir = type;
			// for pawn
			// the index of up in the turn array is 0
			if (model.getType() == PieceType.PAWN) {
				dir = MetaConfig.getDirectionWithTurn(dir, model.getTurn());
			}
			int[] direction = MetaConfig.getDirectionArray(dir);
			movement(direction[0] * model.getRange(),
					direction[1] * model.getRange(), model, false);
		}
	}

	// horsteStep
	public static void horseStep(String type, ExtendedPieceModel model) {

		String dir = type;

		int[] direction = MetaConfig.getDirectionArray(dir);

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
