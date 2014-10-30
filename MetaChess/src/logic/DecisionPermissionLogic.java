package logic;

import java.util.Set;

import meta.MetaClock;
import meta.MetaConfig;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;

public class DecisionPermissionLogic {

	
	// add this cooldown after every turn that the decision is active
	public static int getCooldown(int tileFraction, int range) {
		return range * Math.max(MetaClock.getMaxFraction() / tileFraction, 1);
	}

	public static int getCooldown(int[] tileFractions, int range) {
		int cooldown = 0;
		for (int i = 0; i < tileFractions.length; i++) {
			// for the moment the range doesn't influence the type of decision
			// neither the cooldown
			// cooldown += range
			// * Math.max(MetaClock.getMaxFraction() / tileFractions[i], 1);
			cooldown += 1 * Math.max(MetaClock.getMaxFraction()
					/ tileFractions[i], 1);
		}
		return cooldown;
	}

	// get effect from decision, based on side
	// 0 means same side-> friendly, 1 means opposite
	public static int getEffect(int balance, int influence, int friendly) {
		if (friendly == 0) {
			return influence - balance;
		}
		return -1 * (balance - influence);
	}

	// returns wether the decision is still active based on the current
	// cooldown, if it's your turn and if you haven't already made a move

	public static boolean conditionsMet(String name, ExtendedPieceModel model) {
		// check if your turn and model is locked
		// check if not action locking

		// check if cooldown is 0
		if ((MetaClock.getTurn(model) && !model.isLocked())
				|| MetaConfig.getSpecialsSet().keySet().contains(name)) {
			if (model.getCooldown(name) == 0) {
				return true;
			}
		}
		return false;

	}

	public static Set<ExtendedTileModel> getReach(String name,
			ExtendedPieceModel model) {
		// if it's a ranged decision give reach
		if (name.startsWith("RANGED")) {

		}
		return null;
	}

}
