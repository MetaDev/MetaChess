package decision;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import logic.BoardLogic;
import logic.MetaClock;
import meta.MetaMapping;
import meta.MetaMapping.ControllerType;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;

public class Decision {
	public enum WillType {
		KEYHOLD, KEYRELEASE, KEYPRESS
	}

	private String name;

	// weight of the decision
	// if the decision has no weight it has no cooldown
	private int weight;
	// the balance of the decision 0<=balance<=weight
	// used as parameter when executing valueObject
	// the balance is how much weight is in a decision in favor of your side
	private int balance;
	// number of turn the decision is active
	private int turnsActive;

	// methods to execute when input conditions are met
	private List<Method> consequences;
	// methods to execute when the MetaAction isn't active anymore
	private List<Method> revert;

	// the MetaAction locks the piece for 1 turn
	private boolean locking;
	// defines how the decision handles the will of the actor
	private WillType willType;
	// boolean for will
	private boolean willing = false;
	//
	private boolean reaching;

	public boolean isReaching() {
		return reaching;
	}

	public boolean isLocking() {
		return locking;
	}

	public void setLocking(boolean locking) {
		this.locking = locking;
	}

	// get effect from decision, based on side
	public int getEffect(boolean friendly) {
		if (friendly) {
			return balance;
		}
		return weight - balance;
	}

	public void decide(ExtendedPieceModel model) {

		try {

			// all methods have signature "static XXX(model)"
			for (Method method : consequences)
				method.invoke(null, model);
			//
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void regret(ExtendedPieceModel model) {

		try {
			// all methods have signature "static XXX(model)"
			for (Method method : revert)
				method.invoke(null, model);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getName() {
		return name;
	}

	// every MetaAction has a key
	// 1, is released, 0, hold, -1 pressed

	// works because, the decision is executed in smaller periods then input is
	// checked
	public void handleInput(int in) {
		ExtendedPieceModel player = MetaMapping.getBoardModel().getPlayer();
		// if there is a range
		if (player.getDecisionRange() > 0 && reaching) {
			// handle the key combination for direction of a reaching
			// decision
			// the willtype is always the same for reaching decisions

			// if no direction
			if (player.getDirection() == null) {
				// when the key is released, the decision is made
				if (in == 1) {
					willing = true;
					// remove this decision from pending position
					MetaMapping.getBoardModel().getPlayer()
							.setPendingDecision(null);
				}
				// but the decision can also be made by the direction keys
				// set pending decision if the player
				if (in == -1) {
					MetaMapping.getBoardModel().getPlayer()
							.setPendingDecision(this);
				}
				return;
			} else {
				//when there is a direction and the directional button is held down
				//the decision is made upon releasing the key
				if (in == 1) {
					willing = true;
				}
				return;
			}
		}
		if (willType == WillType.KEYHOLD) {
			if (in == 0 || in == -1) {
				willing = true;
				return;
			}
		}
		if (willType == WillType.KEYPRESS) {
			if (in == -1) {
				willing = true;
				return;
			}

		}
		if (willType == WillType.KEYRELEASE) {
			if (in == 1) {
				willing = true;
				return;
			}
		}
		willing = false;
	}

	public Decision(String name, int weight, int balance, int turnsActive,
			List<Method> acts, List<Method> revertActs, boolean locking,
			int key, WillType willType, boolean reaching) {
		this.name = name;
		this.weight = weight;
		this.balance = balance;
		this.turnsActive = turnsActive;
		this.consequences = acts;
		this.revert = revertActs;
		this.reaching = reaching;
		this.locking = locking;
		this.willType = willType;
		MetaMapping.addDecision(name, this);
		MetaMapping.setKeyBinding(key, this);
	}

	public int getTurnsActive() {
		return turnsActive;
	}

	public int getCooldown(int tileWeight) {
		return weight * turnsActive * tileWeight;
	}

	public void setKeyBinding(int key) {
		MetaMapping.setKeyBinding(key, this);
	}

	public boolean reverts() {
		if (revert != null)
			return !revert.isEmpty();
		return false;
	}

	// returns wether the MetaAction is still active based on the current
	// cooldown

	public boolean conditionsMet(ExtendedPieceModel model) {
		// check if your turn and model is locked
		// check if not action locking
		// if there's a pending decision, the next decision even locking should
		// execute
		// check if cooldown is 0
		if ((MetaClock.getTurn(model) && !model.isLocked()) || !locking
				|| model.getPendingDecision() != null) {
			if (model.getCooldown(this) == 0) {
				return true;
			}
		}
		return false;

	}

	public boolean isWilling() {
		return willing;
	}

	public boolean veto(ExtendedPieceModel model) {

		if (!conditionsMet(model)) {
			willing = false;
		}
		return willing;
	}

	// this will become the main range implementation
	public Set<ExtendedTileModel> getReach(ExtendedPieceModel model) {
		Set<ExtendedTileModel> list = new HashSet<ExtendedTileModel>();
		Set<int[]> directions = new HashSet<>();

		int[] dir = MetaMapping.getActionDirection(model.getDirection());

		int range = model.getDecisionRange();
		// no direction
		if (dir == null || dir[0] == 0 && dir[1] == 0) {
			// no range, local decision
			if (range == 0) {
				return list;
			}
			// ranged, undirected decision, unidirected
			else {
				// depends upon controllertype
				ControllerType type = model.getControllerType();
				List<String> decisions = MetaMapping.getPieceMetaActions(type);
				for (String decision : decisions) {
					dir = MetaMapping.getActionDirection(decision);
					if (dir != null)
						directions.add(dir);
				}
			}
		}
		// directed decision
		else {
			// depends upon controllertype
			directions.add(dir);
		}
		// iterate all directions and save all tiles on path
		ExtendedTileModel currentTile = MetaMapping.getBoardModel()
				.getPiecePosition(model);
		for (int[] direction : directions) {

			ExtendedTileModel iterateTile = currentTile;
			int i = range;
			while (iterateTile != null && i > 0) {
				iterateTile = BoardLogic.getTileNeighbour(iterateTile,
						direction[0], direction[1], false, true, false);
				// reach has ended
				if (iterateTile == null)
					break;
				list.add(iterateTile);
				i--;
			}
		}

		return list;
	}
}
