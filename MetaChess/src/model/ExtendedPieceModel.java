package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import logic.MetaClock;
import meta.MetaMapping;
import meta.MetaMapping.ActionType;
import meta.MetaMapping.ControllerType;
import meta.MetaMapping.PieceRendererType;
import decision.Decision;

public class ExtendedPieceModel {
	protected int color;
	protected int side;
	protected int absTime = 0;

	protected int maxMovementRange = 8;
	protected int maxDecisionRange = 8;
	protected int range = 1;
	protected String direction;
	protected boolean ignoreOccupationOfTile = false;
	protected boolean penetrateLowerFraction = false;
	protected PieceRendererType renderType;
	protected ControllerType controllerType;
	// the model is thinking about a decision
	private Decision pendingDecision;

	public Decision getPendingDecision() {
		return pendingDecision;
	}

	public void setPendingDecision(Decision pendingDecision) {
		this.pendingDecision = pendingDecision;
	}

	// this MetaAction is special because the cooldown and turns of activity
	// don't have to lowered with every turn change
	// and it's revert if the piece changes position to a tile without the
	// MetaAction
	protected Decision boardMetaAction;

	public PieceRendererType getRenderType() {
		return renderType;
	}

	public void setRenderType(PieceRendererType renderType) {
		this.renderType = renderType;
	}

	// save remaining cooldown of all metaActions applied on this model
	protected Map<Decision, Integer> cooldownOfMetaActions;
	// save metaActions turnsOfActivity if it has a fixed amount of active turns
	// only decreased of the MetaAction has a cooldown on current model
	// this means that MetaActions without a cooldown don't change
	protected Map<Decision, Integer> turnsActiveOfmetaActions;
	// saves all MetaActions executed on this activated on model, needed to
	// verify if MetaAction has
	// to be reversed
	protected Map<Decision, Boolean> metaActionIsActive;

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
		for (Map.Entry<Decision, Integer> entry : cooldownOfMetaActions
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
				boardMetaAction.regret(this);
				boardMetaAction = null;
			}
		}
		// if no longer active, do a revert
		// for all active decisions
		for (Map.Entry<Decision, Boolean> entry : metaActionIsActive.entrySet()) {
			// revert if MetaAction is active on model
			// and no longer active in the game
			Decision decision = entry.getKey();
			if (entry.getValue() && !decision.veto(this)) {

				// And if it has still turns active on this model
				if (turnsActiveOfmetaActions.get(decision) == 0) {
					// maybe optimasation is removing the decision from the map
					// all together

					// set inactive in model
					entry.setValue(false);
					// regret decision
					decision.regret(this);
				}

			}
		}
	}

	// special method to invoke reaching decisions when a directional button is
	// pressed
	public void makeDecision(Decision decision) {
		// act if the activity conditions of the MetaAction are met, already checked if input is OK
		if (decision.conditionsMet(this)) {
			// lock if needed
			if (decision.isLocking()) {
				locked = true;
			}
			ExtendedTileModel position = MetaMapping.getBoardModel()
					.getPiecePosition(this);
			Set<ExtendedTileModel> reach = decision.getReach(this);
			// is the decision is reaching and it's range is not empty
			if (decision.isReaching() && !reach.isEmpty()) {
				// apply range to board
				// while applying range calculated absolute affected tile
				// weight
				int tileWeight = 0;
				for (ExtendedTileModel tile : reach) {
					tileWeight += ((float) position.getAbsFraction())
							/ ((float) tile.getAbsFraction());
					// apply for each tile if not already applied to board
					MetaMapping.getBoardModel().setActiveMetaAction(decision,
							tile, this);
				}
				// set cooldown
				cooldownOfMetaActions.put(decision,
						decision.getCooldown(tileWeight));

			}
		}
	}

	public void makeDecisions() {
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
				boardMetaAction.decide(this);
		}

		// iterate over all MetaAction that the piece can execute
		for (Map.Entry<Decision, Integer> entry : cooldownOfMetaActions
				.entrySet()) {
			Decision decision = entry.getKey();
			// act if the decision has veto
			
			if (decision.veto(this)) {
				
				// lock if needed
				if (decision.isLocking()) {
					locked = true;
				}
				Set<ExtendedTileModel> reach = decision.getReach(this);
				// is the decision is reaching and it's range is not empty
				if (decision.isReaching() && !reach.isEmpty()) {
					
					// apply range to board
					// while applying range calculated absolute affected
					// tile
					// weight
					int tileWeight = 0;
					for (ExtendedTileModel tile : reach) {
						tileWeight += ((float) position.getAbsFraction())
								/ ((float) tile.getAbsFraction());
						// apply for each tile if not already applied to
						// board
						MetaMapping.getBoardModel().setActiveMetaAction(
								decision, tile, this);
					}
					// set cooldown
					entry.setValue(decision.getCooldown(tileWeight));

				}
				// if it's not a ranged MetaAction, you can execute it
				// directly if MetaAction is not active on this model

				else if (!metaActionIsActive.get(decision)) {
					// set active on model if the metaAction has a lasting, not
					// a switch
					// effect->has to revert
					// no MetaAction is permanent
					if (decision.reverts()) {
						metaActionIsActive.put(decision, true);
					}
					// set cooldown, only 1 tile affected, your own
					int cooldown = decision.getCooldown(1);
					// set turns of activity if it has a cooldown
					if (cooldown > 0) {
						entry.setValue(cooldown);
						turnsActiveOfmetaActions.put(decision,
								decision.getTurnsActive());
					}
					// decide decision
					decision.decide(this);

				}

			}
		}

	}

	public boolean isMetaActionActive(Decision metaAction) {
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

	public int getRange() {
		return range;
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
		for (Map.Entry<String, Decision> pair : MetaMapping.getAllDecisions()
				.entrySet()) {
			cooldownOfMetaActions.put(pair.getValue(), 0);
			turnsActiveOfmetaActions.put(pair.getValue(), 0);
			metaActionIsActive.put(pair.getValue(), false);
		}
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

	// the range is always at least 1
	public int getMovementRange() {
		return Math.max(Math.min(range, maxMovementRange), 1);
	}

	public int getDecisionRange() {
		return Math.max(Math.min(range, maxDecisionRange) - 1, 0);
	}

	// if range is set negative the directions are inverted
	public void setRange(int range) {
		this.range = range;

	}

	public Integer getCooldown(Decision metaAction) {
		return cooldownOfMetaActions.get(metaAction);
	}

	public Integer getTurnsOfActivity(Decision metaAction) {
		return turnsActiveOfmetaActions.get(metaAction);
	}

	public void setMetaActionCooldown(Decision metaAction, int cooldown) {
		cooldownOfMetaActions.put(metaAction, cooldown);
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}

	public float getRelSize() {
		return MetaMapping.getBoardModel().getPiecePosition(this).getRelSize();
	}

	public ExtendedTileModel getTilePosition() {
		return MetaMapping.getBoardModel().getPiecePosition(this);
	}
}
