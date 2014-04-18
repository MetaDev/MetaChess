package action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import meta.MetaMapping;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;

public class MetaAction {

	private String name;

	//weight of the decision
	//if the decision has no weight it has no cooldown
	private int weight;
	// the balance of the decision 0<=balance<=weight
	//used as parameter when executing valueObject
	//the balance is how much weight is in a decision in favor of your side
	private int balance;
	// number of turn the decision is active
	private int turnsActive;
	

	// methods to execute when input conditions are met
	private List<Method> acts;
	// methods to execute when the MetaAction isn't active anymore
	private List<Method> revertActs;
	// this class returns whether the MetaAction is still active
	private MetaActionActivity activity;
	// this class returns a range of tiles
	private MetaActionRange range;
	// the MetaAction locks the piece for 1 turn
	private boolean locking;

	public boolean isLocking() {
		return locking;
	}

	public void setLocking(boolean locking) {
		this.locking = locking;
	}

	// get effect from decision, based on side
	public int getEffect(boolean friendly) {
		if(friendly){
			return balance;
		}
		return weight-balance;
	}

	public void act(ExtendedPieceModel model) {

		try {

			// all methods have signature "static XXX(model)"
			for (Method method : acts)
				method.invoke(null, model);
			//
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void revert(ExtendedPieceModel model) {

		try {
			// all methods have signature "static XXX(model)"
			for (Method method : revertActs)
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

	

	public MetaAction(String name, int weight, int balance, int turnsActive,
			List<Method> acts, List<Method> revertActs,
			MetaActionActivity activity, MetaActionRange range, boolean locking) {
		this.name = name;
		this.weight = weight;
		this.balance = balance;
		this.turnsActive = turnsActive;
		this.acts = acts;
		this.revertActs = revertActs;
		this.activity = activity;
		this.range = range;
		this.locking = locking;
		MetaMapping.addMetaAction(name, this);
	}

	

	public boolean isActive(ExtendedPieceModel model) {
		return activity.getTurnsOfActivity(model, this);
	}

	public Set<ExtendedTileModel> getRange(ExtendedPieceModel model) {
		if(range!=null)
		return range.getRange(model, this);
		return	new HashSet<>();
	}



	public int getTurnsActive() {
		return turnsActive;
	}

	public int getCooldown(int tileWeight) {
		return weight * turnsActive * tileWeight;
	}


	public boolean isRanged() {
		return range != null;
	}

	public boolean reverts() {
		if (revertActs != null)
			return !revertActs.isEmpty();
		return false;
	}
}
