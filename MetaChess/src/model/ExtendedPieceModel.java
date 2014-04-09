package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import userinterface.TileGraphic;
import logic.MetaClock;
import meta.MetaMapping;
import meta.MetaMapping.ControllerType;
import meta.MetaMapping.PieceRendererType;
import action.MetaAction;

public class ExtendedPieceModel {
	protected int lives = 8;
	protected int color;
	protected int side;
	protected int absTime = 0;
	protected int maxLives ;
	protected int maxRange ;
	protected int movementRange = 1;
	protected int decisionRange = 1;
	protected int[] direction;
	protected boolean ignoreOccupationOfTile = false;
	protected boolean penetrateLowerFraction = false;
	protected PieceRendererType renderType;
	protected ControllerType controllerType;

	public PieceRendererType getRenderType() {
		return renderType;
	}

	public void setRenderType(PieceRendererType renderType) {
		this.renderType = renderType;
	}

	// save remaining cooldown of all metaActions,
	// >0 means cooling down
	protected Map<String, Integer> cooldownOfMetaActions;
	// save if actions is activated on model
	protected Map<String, Boolean> activityOfMetaActions;
	// save range of metaAction in model, empty list means that actions is
	// active on current tile only
	protected Map<String, List<TileGraphic>> rangeOfMetaActions;

	// if the piece already made a move this turn it's locked and can't be moved
	// again
	protected boolean locked = false;

	public boolean isPenetrateLowerFraction() {
		return penetrateLowerFraction;
	}

	public void setPenetrateLowerFraction(boolean penetrateLowerFraction) {
		this.penetrateLowerFraction = penetrateLowerFraction;
	}

	public void turnChange() {
		// with every change of turn check wether a MetaAction is still active
		for (Map.Entry<String, Integer> entry : cooldownOfMetaActions
				.entrySet()) {
			// cooldown metaactions that have a cooldown and are active
			if (entry.getValue() != null) {
				if (entry.getValue() > 0) {
					entry.setValue(entry.getValue() - 1);
				}

			}
		}
		// unlock piece
		locked = false;
		absTime = MetaClock.getAbsoluteTime();
	}

	public void revertMetaActions() {
		// if no longer active, do a revert
		for (Map.Entry<String, Integer> entry : cooldownOfMetaActions
				.entrySet()) {
			// revert if MetaAction is active on model
			if (activityOfMetaActions.get(entry.getKey())) {
				// revert if MetaAction is no longer active
				MetaAction action = MetaMapping.getAllMetaActions().get(
						entry.getKey());
				if (!action.isActive(this)) {
					MetaMapping.getAllMetaActions().get(entry.getKey())
							.revert(this);
					// set inactive
					setMetaActionActivity(entry.getKey(), false);
				}

			}
		}
	}

	public int getAbsTime() {
		return absTime;
	}

	public void setAbsTime(int absTime) {
		this.absTime = absTime;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isIgnoreOccupationOfTile() {
		return ignoreOccupationOfTile;
	}

	public void setIgnoreOccupationOfTile(boolean ignoreOccupationOfTile) {
		this.ignoreOccupationOfTile = ignoreOccupationOfTile;
	}

	public ExtendedPieceModel(PieceRendererType renderType, int side,
			ControllerType controllerType, int lives, int maxLives, int maxRange) {
		this.lives = lives;
		this.side = side;
		this.color = side;
		this.maxLives = maxLives;
		this.maxRange = maxRange;
		this.controllerType = controllerType;
		this.renderType=renderType;
		// fill with all available MetaActions
		cooldownOfMetaActions = new HashMap<>();
		activityOfMetaActions = new HashMap<>();
		rangeOfMetaActions = new HashMap<>();
		for (Map.Entry<String, MetaAction> pair : MetaMapping
				.getAllMetaActions().entrySet()) {
			cooldownOfMetaActions.put(pair.getKey(), 0);
			activityOfMetaActions.put(pair.getKey(), false);
			rangeOfMetaActions.put(pair.getKey(), new ArrayList<TileGraphic>());
		}

		direction = new int[2];
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public ControllerType getControllerType() {
		return controllerType;
	}

	public void setControllerType(ControllerType controllerType) {
		this.controllerType = controllerType;
	}

	public int getRange() {
		return Math.min(movementRange, maxRange);
	}

	// if range is set negative the directions are inverted
	public void setRange(int range) {
		this.movementRange = range;

	}

	public int getLives() {
		return lives;
	}

	public void setLives(int l) {
		lives = l;
	}

	public int getMaxLives() {
		return maxLives;
	}

	public void setMaxLives(int l) {
		maxLives = l;
	}

	public Integer getCooldown(String metaAction) {
		return cooldownOfMetaActions.get(metaAction);
	}

	public void setMetaActionCooldown(String name, int cooldown) {
		cooldownOfMetaActions.put(name, cooldown);
	}

	public boolean getMetaActionActivity(String metaAction) {
		return activityOfMetaActions.get(metaAction);
	}

	public void setMetaActionActivity(String name, boolean cooldown) {
		activityOfMetaActions.put(name, cooldown);
	}

	public List<TileGraphic> getMetaActionRange(String name) {
		return rangeOfMetaActions.get(name);
	}

	public int getDecisionRange() {
		return decisionRange;
	}

	public void setDecisionRange(int decisionRange) {
		this.decisionRange = decisionRange;
	}

	public void setMetaActionRange(String name, List<TileGraphic> list) {
		rangeOfMetaActions.put(name, list);
	}

	public void setDirection(int i, int j) {
		direction[0] = i;
		direction[1] = j;
	}

	public int[] getDirection() {
		return direction;
	}

	public float getWidth() {
		return MetaModel.getPiecePosition(this).getWidth();
	}

	public float getHeight() {
		return MetaModel.getPiecePosition(this).getHeight();
	}

	public float getX() {
		return MetaModel.getPiecePosition(this).getX();
	}

	public float getY() {
		return MetaModel.getPiecePosition(this).getY();
	}
}
