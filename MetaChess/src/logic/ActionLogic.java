package logic;

import network.NetworkMessages;
import network.client.MetaClient;
import meta.MetaMapping;
import meta.MetaMapping.ActionType;
import model.ExtendedPieceModel;
import model.ExtendedPlayerModel;
import model.ExtendedTileModel;

//geef builder mee omdat controller het hele object moet kunnen vernietigen
public class ActionLogic implements Logic {

	public static void NODIRECTION(ExtendedPieceModel model) {
		model.setDirection(null);
	}

	public static void RANGEPLUS2(ExtendedPieceModel model) {
		model.setRange(model.getMovementRange() + 2);
	}

	public static void RANGEMIN2(ExtendedPieceModel model) {
		model.setRange(model.getMovementRange() - 2);
	}

	public static void RANGEPLUS4(ExtendedPieceModel model) {
		model.setRange(model.getMovementRange() + 4);
	}

	public static void RANGEMIN4(ExtendedPieceModel model) {
		model.setRange(model.getMovementRange() - 4);
	}

	public static void RANGEPLUS8(ExtendedPieceModel model) {
		model.setRange(model.getMovementRange() + 8);
	}

	public static void RANGEMIN8(ExtendedPieceModel model) {
		model.setRange(model.getMovementRange() - 8);
	}

	public static void RANGEPLUS1(ExtendedPieceModel model) {
		model.setRange(model.getMovementRange() + 1);
	}

	public static void RANGEMIN1(ExtendedPieceModel model) {
		model.setRange(model.getMovementRange() - 1);
	}

	public static void RANGE0(ExtendedPieceModel model) {
		model.setRange(1);
	}

	public static void UPRIGHT(ExtendedPieceModel model) {
		movement(model.getMovementRange(), model.getMovementRange(), model,
				false);
	}

	public static void UPLEFT(ExtendedPieceModel model) {
		movement(-model.getMovementRange(), model.getMovementRange(), model,
				false);
	}

	public static void DOWNLEFT(ExtendedPieceModel model) {
		movement(-model.getMovementRange(), -model.getMovementRange(), model,
				false);
	}

	public static void DOWNRIGHT(ExtendedPieceModel model) {
		movement(model.getMovementRange(), -model.getMovementRange(), model,
				false);
	}

	

	public static void DECISIONDIRECTIONNONE(ExtendedPieceModel model) {
		// when directional button released
		model.setDirection(null);
	}

	public static void TURN(ExtendedPieceModel model) {
		model.addTurn();
	}

	public static void UP(ExtendedPieceModel model) {
		//NETWORKING
		//add message to client queue
		MetaClient.addOutMessage(NetworkMessages.decisionOutMessage("UP", model));
		
		// if there's a pending decision
		model.setDirection("UP");
		//for king
		if (model.getPendingDecision() != null) {
			// execute pending decision
			model.makeDecision(model.getPendingDecision());
			// model.setPendingDecision(null);
		} else {
			//for pawn
			// the index of up in the turn array is 0
			String dir = MetaMapping.getDirectionWithTurn(0, model.getTurn());
			int[] direction = MetaMapping.getActionDirection(dir);
			movement(direction[0] * model.getMovementRange(), direction[1]
					* model.getMovementRange(), model, false);
		}

	}

	public static void DOWN(ExtendedPieceModel model) {
		// if there's a pending decision
		model.setDirection("DOWN");
		if (model.getPendingDecision() != null) {
			// execute pending decision
			model.makeDecision(model.getPendingDecision());
			// model.setPendingDecision(null);
		} else {
			movement(0, -model.getMovementRange(), model, false);
		}

	}

	public static void LEFT(ExtendedPieceModel model) {
		// if there's a pending decision
		model.setDirection("LEFT");
		if (model.getPendingDecision() != null) {
			// execute pending decision
			model.makeDecision(model.getPendingDecision());
			// model.setPendingDecision(null);
		} else {
			movement(-model.getMovementRange(), 0, model, false);
		}
	}

	public static void RIGHT(ExtendedPieceModel model) {
		// if there's a pending decision
		model.setDirection("RIGHT");
		if (model.getPendingDecision() != null) {
			// execute pending decision
			model.makeDecision(model.getPendingDecision());
			// model.setPendingDecision(null);
		} else {
			movement(model.getMovementRange(), 0, model, false);
		}

	}

	// mode is the horizontal movement
	// horizontal and vertical movement are seperated, because a diagonal move
	// behaves different, can be altered if desired, only the full 'L' is
	// allowed
	public static void UPLEFT21(ExtendedPieceModel model) {
		if (movement(0, model.getMovementRange() * 2, model, true))
			movement(-model.getMovementRange(), 0, model, false);
	}

	public static void UPLEFT12(ExtendedPieceModel model) {
		if (movement(0, model.getMovementRange(), model, true))
			movement(-model.getMovementRange() * 2, 0, model, false);
	}

	public static void UPRIGHT21(ExtendedPieceModel model) {
		if (movement(0, model.getMovementRange() * 2, model, true))
			movement(model.getMovementRange(), 0, model, false);
	}

	public static void UPRIGHT12(ExtendedPieceModel model) {
		if (movement(0, model.getMovementRange(), model, true))
			movement(model.getMovementRange() * 2, 0, model, false);
	}

	public static void DOWNRIGHT12(ExtendedPieceModel model) {
		if (movement(0, -model.getMovementRange(), model, true))
			movement(model.getMovementRange() * 2, 0, model, false);
	}

	public static void DOWNRIGHT21(ExtendedPieceModel model) {
		if (movement(0, -model.getMovementRange() * 2, model, true))
			movement(model.getMovementRange(), 0, model, false);
	}

	public static void DOWNLEFT21(ExtendedPieceModel model) {
		if (movement(0, -model.getMovementRange() * 2, model, true))
			movement(-model.getMovementRange(), 0, model, false);
	}

	public static void DOWNLEFT12(ExtendedPieceModel model) {
		if (movement(0, -model.getMovementRange(), model, true))
			movement(-model.getMovementRange() * 2, 0, model, false);
	}

	public static void TILEVIEWUP(ExtendedPieceModel model) {
		model.setNrOfViewTiles((model).getNrOfViewTiles() + 1);
	}

	public static void TILEVIEWDOWN(ExtendedPieceModel model) {
		(model).setNrOfViewTiles((model).getNrOfViewTiles() - 1);
	}

	public static boolean movement(int i, int j, ExtendedPieceModel model,
			boolean isSideStep) {
		// if there's a pending decision, you won't make a movement but
		// a decision in the direction
		if (model.getPendingDecision() != null) {
			return false;
		}

		ExtendedTileModel previousTile = MetaMapping.getBoardModel()
				.getPiecePosition(model);
		ExtendedTileModel newTile = BoardLogic.getTileNeighbour(previousTile,
				i, j, BoardLogic.isHoover(), model.isIgnoreOccupationOfTile(),
				model.isPenetrateLowerFraction());
		// movement did not succeed
		if (newTile == null)
			return false;
		// tile is occupied and the movement isn't a sidestep->there doesn't
		// immediately follow a next move
		if (!isSideStep
				&& MetaMapping.getBoardModel().getModelOnPosition(newTile) != null) {
			// here a piece loses a life or more or get's killed
			// when killed the Metamodel should be alerted

			// delete piece model
			// should this be the player, then it's game over
			MetaMapping.getBoardModel().deleteModel(newTile);

		}

		// set new position for model
		MetaMapping.getBoardModel().setPiecePosition(model, newTile);
		return true;
	}
	public static void step(String type,ExtendedPieceModel model) {
		//NETWORKING
		//add message to client queue
		MetaClient.addOutMessage(NetworkMessages.decisionOutMessage(type, model));
		
		// if there's a pending decision
		model.setDirection(type);
		//for king
		if (model.getPendingDecision() != null) {
			// execute pending decision
			model.makeDecision(model.getPendingDecision());
			// model.setPendingDecision(null);
		} else {
			//for pawn
			// the index of up in the turn array is 0
			String dir = MetaMapping.getDirectionWithTurn(0, model.getTurn());
			int[] direction = MetaMapping.getActionDirection(dir);
			movement(direction[0] * model.getMovementRange(), direction[1]
					* model.getMovementRange(), model, false);
		}
	}
	//horsteStep
	public static void horseStep(String type,ExtendedPieceModel model){
		
	}
	//parameter change
	//use range +1 as param
	
	public static void changeParam(String type,ExtendedPieceModel model){
		
	}
}
