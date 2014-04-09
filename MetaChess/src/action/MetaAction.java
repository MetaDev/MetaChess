package action;

import graphic.PieceGraphic;
import graphic.TileGraphic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import logic.MetaClock;
import meta.MetaMapping;
import model.PieceExtendedModel;

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
	private boolean locks;

	public void act(PieceExtendedModel model) {
		// if actions still cooling down
		
		System.out.println(model.getRange());
		if (model.getCooldown(name) > 0)
			return;
		// if action already active and reverts
		if (!revertActs.isEmpty()&& model.getMetaActionActivity(name))
			return;
		
		// if it's not your turn or the model is locked and the action locks
		if ((!MetaClock.getTurn() || model.isLocked()) && locks)
			return;

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

		// set cooldown for MetaAction on model
		if (locks) {
			// lock model
			// if not playing real-time
			// TODO change to model.getAbsfraction
			if (MetaClock.getMaxFraction() > ((PieceGraphic) model.getGraphic())
					.getTile().absoluteFraction()) {
				model.setLocked(true);
			}
		}
		// set cooldown
		model.setMetaActionCooldown(name, cooldown);
		// set active if it has a revert
		model.setMetaActionActivity(name, true);
	}

	public void revert(PieceExtendedModel model) {
		// don't revert
		// if MetaAction has no revert or
		// if it's still active
		if (activity.isActive(model,this))
			return;
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
			List<Method> methods, String name, boolean locks) {
		this.cooldown = cooldown;
		this.acts = methods;
		this.name = name;
		this.locks = locks;
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

	public boolean isActive(PieceExtendedModel model) {
		if (activity != null)
			return activity.isActive(model,this);
		return true;
	}

	public void setActivity(MetaActionActivity activity) {
		this.activity = activity;
	}

	public List<TileGraphic> getRange(PieceExtendedModel model) {
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
}
