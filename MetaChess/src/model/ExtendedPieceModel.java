package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import logic.MetaClock;
import meta.MetaMapping;
import meta.MetaMapping.ControllerType;
import meta.MetaMapping.PieceRendererType;
import action.MetaAction;

public class ExtendedPieceModel {
	protected int color;
	protected int side;
	protected int absTime = 0;

	protected int maxMovementRange;
	protected int maxDecisionRange;
	protected int range = 1;
	protected int[] direction;
	protected boolean ignoreOccupationOfTile = false;
	protected boolean penetrateLowerFraction = false;
	protected boolean decisionLeavesFraction = false;
	protected PieceRendererType renderType;
	protected ControllerType controllerType;
	// this MetaAction is special because the cooldown and turns of activity
	// don't have to lowered with every turn change
	// and it's revert if the piece changes position to a tile without the
	// MetaAction
	protected MetaAction boardMetaAction;

	public PieceRendererType getRenderType() {
		return renderType;
	}

	public void setRenderType(PieceRendererType renderType) {
		this.renderType = renderType;
	}

	// save remaining cooldown of all metaActions applied on this model
	protected Map<MetaAction, Integer> cooldownOfMetaActions;
	// save metaActions turnsOfActivity if it has a fixed amount of active turns
	// only decreased of the MetaAction has a cooldown on current model
	// this means that MetaActions without a cooldown don't change
	protected Map<MetaAction, Integer> turnsActiveOfmetaActions;
	// saves all MetaActions executed on this activated on model, needed to
	// verify if MetaAction has
	// to be reversed
	protected Map<MetaAction, Boolean> metaActionIsActive;

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
		for (Map.Entry<MetaAction, Integer> entry : cooldownOfMetaActions
				.entrySet()) {
			// metaactions that have a cooldown and are active
			if (entry.getValue() != null) {
				if (entry.getValue() > 0) {
					// decrease cooldown
					entry.setValue(entry.getValue() - 1);
					// if it has a cooldown
					if (turnsActiveOfmetaActions.get(entry.getKey()) > 0) {
						// decrease turns of activity
						turnsActiveOfmetaActions
								.put(entry.getKey(), turnsActiveOfmetaActions
										.get(entry.getKey()) - 1);
					}
				}

			}
		}
		// unlock piece
		locked = false;
		absTime = MetaClock.getAbsoluteTime();
	}

	public void regret() {
		// check if board MetaAction has to be reversed
		if (boardMetaAction != null) {
			ExtendedTileModel position = MetaMapping.getBoardModel()
					.getPiecePosition(this);
			if (MetaMapping.getBoardModel().getActiveMetaAction(position) != boardMetaAction) {
				boardMetaAction.revert(this);
				boardMetaAction = null;
			}
		}
		// if no longer active, do a revert
		// for all active decisions
		for (Map.Entry<MetaAction, Boolean> entry : metaActionIsActive
				.entrySet()) {
			// revert if MetaAction is active on model
			if (entry.getValue()) {
				MetaAction metaAction = entry.getKey();
				// And if it has still turns active on this model
				if (turnsActiveOfmetaActions.get(metaAction) == 0) {
					// maybe optimasation is removing the decision from the map
					// all together

					// set inactive in model
					entry.setValue(false);
					// regret decision
					metaAction.revert(this);
				}

			}
		}
	}

	public void actMetaActions() {

		// act if on a tile with a MetaAction, a board MetaAction never locks
		// the piece
		// it also doesn't affect the MetaActions cooldown of this piece

		// check if this MetaAction is already registered
		// if so, check if not already active
		// if the board Decision is null, this means that the previous decision
		// has been reverted
		ExtendedTileModel position = MetaMapping.getBoardModel()
				.getPiecePosition(this);
		if (boardMetaAction == null) {
			// get current position
			boardMetaAction = MetaMapping.getBoardModel().getActiveMetaAction(
					position);
			// decide board decision
			if (boardMetaAction != null)
				boardMetaAction.act(this);
		}

		// iterate over all MetaAction that the piece can execute
		for (Map.Entry<MetaAction, Integer> entry : cooldownOfMetaActions
				.entrySet()) {
			MetaAction metaAction = entry.getKey();

			// act if the activity conditions of the MetaAction are met
			if (metaAction.isActive(this)) {
				// lock if needed
				if (metaAction.isLocking()) {
					locked = true;
				}
				Set<ExtendedTileModel> range = metaAction.getRange(this);
				// if the range is non empty, it's a ranged decision
				if (!range.isEmpty()) {

					// apply range to board
					// while applying range calculated absolute affected tile
					// weight
					int tileWeight = 0;
					for (ExtendedTileModel tile : range) {
						tileWeight += ((float) position.getAbsFraction())
								/ ((float) tile.getAbsFraction());
						// apply for each tile if not already applied to board
						MetaMapping.getBoardModel().setActiveMetaAction(
								metaAction, tile, this);
					}
					// set cooldown
					entry.setValue(metaAction.getCooldown(tileWeight));

				}

				// if it's not a ranged MetaAction, you can execute it
				// directly if MetaAction is not active on this model

				else if (!metaActionIsActive.get(metaAction)) {
					// set active on model if the metaAction has a lasting, not
					// a switch
					// effect->has to revert
					// no MetaAction is permanent
					if (metaAction.reverts()) {
						metaActionIsActive.put(metaAction, true);
					}
					// set cooldown, only 1 tile affected, your own
					int cooldown = metaAction.getCooldown(1);
					// set turns of activity if it has a cooldown
					if (cooldown > 0) {
						entry.setValue(cooldown);
						turnsActiveOfmetaActions.put(metaAction,
								metaAction.getTurnsActive());
					}
					// decide decision
					metaAction.act(this);

				}

			}
		}

	}

	public boolean isMetaActionActive(MetaAction metaAction) {
		return metaActionIsActive.get(metaAction);
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
			ControllerType controllerType, int maxRange) {
		this.side = side;
		this.color = side;
		this.maxMovementRange = maxRange;
		this.controllerType = controllerType;
		this.renderType = renderType;
		// fill with all available MetaActions
		cooldownOfMetaActions = new HashMap<>();
		metaActionIsActive = new HashMap<>();
		turnsActiveOfmetaActions = new HashMap<>();
		for (Map.Entry<String, MetaAction> pair : MetaMapping
				.getAllMetaActions().entrySet()) {
			cooldownOfMetaActions.put(pair.getValue(), 0);
			turnsActiveOfmetaActions.put(pair.getValue(), 0);
			metaActionIsActive.put(pair.getValue(), false);
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

	public int getMovementRange() {
		return Math.min(range, maxMovementRange);
	}

	public int getDecisionRange() {
		return Math.min(range, maxDecisionRange);
	}

	// if range is set negative the directions are inverted
	public void setRange(int range) {
		this.range = range;

	}

	public Integer getCooldown(MetaAction metaAction) {
		return cooldownOfMetaActions.get(metaAction);
	}

	public Integer getTurnsOfActivity(MetaAction metaAction) {
		return turnsActiveOfmetaActions.get(metaAction);
	}

	public void setMetaActionCooldown(MetaAction metaAction, int cooldown) {
		cooldownOfMetaActions.put(metaAction, cooldown);
	}

	public void setDirection(int i, int j) {
		direction[0] = i;
		direction[1] = j;
	}

	public int[] getDirection() {
		return direction;
	}

	public float getRelSize() {
		return MetaMapping.getBoardModel().getPiecePosition(this).getRelSize();
	}

	public ExtendedTileModel getTilePosition() {
		return MetaMapping.getBoardModel().getPiecePosition(this);
	}
}
