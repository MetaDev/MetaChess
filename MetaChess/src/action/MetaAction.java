package action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import userinterface.generic.GUITile;
import userinterface.generic.GUITileSquare;
import meta.MetaMapping;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;

public class MetaAction {

	private String name;

	// number of turns the actions can't be used

	private int cooldown;
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

	public MetaAction(int cooldown, MetaActionActivity activity,
			List<Method> methods, String name, boolean locking) {
		this.cooldown = cooldown;
		this.acts = methods;
		this.name = name;
		this.locking = locking;
		this.activity = activity;
		MetaMapping.addMetaAction(name, this);
	}

	public Integer getCooldown() {
		return cooldown;
	}

	public void setCooldown(Integer cooldown) {
		this.cooldown = cooldown;
	}

	public List<Method> getMethods() {
		return acts;
	}

	public void setMethods(List<Method> methods) {
		this.acts = methods;
	}

	public int getTurnsOfActivity(ExtendedPieceModel model) {
		if (activity != null)
			return activity.getTurnsOfActivity(model, this);
		return 0;
	}

	public void setActivity(MetaActionActivity activity) {
		this.activity = activity;
	}

	public List<ExtendedTileModel> getRange(ExtendedPieceModel model) {
		if (range != null)
			return range.getRange(model, this);
		return null;
	}

	public void setRange(MetaActionRange range) {
		this.range = range;
	}

	public void setRevertMethods(List<Method> methods) {
		revertActs = methods;
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
