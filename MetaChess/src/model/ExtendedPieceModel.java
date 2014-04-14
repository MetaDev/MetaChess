package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	

	protected int maxLives;
	protected int maxRange;
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

	// save remaining cooldown of all metaActions applied on this model
	protected Map<MetaAction, Integer> cooldownOfMetaActions;
	//save metaActions turnsOfActivity if it has a fixed amount of active turns
	//only decreased of the MetaAction has a cooldown on current model
	//this means that MetaActions without a cooldown don't change
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
			// cooldown metaactions that have a cooldown and are active
			if (entry.getValue() != null) {
				if (entry.getValue() > 0) {
					//decrease cooldown
					entry.setValue(entry.getValue() - 1);
					//if it has a cooldown and is not ranged
					if(turnsActiveOfmetaActions.get(entry.getKey())>0 && !entry.getKey().isRanged()){
						//decrease turns of activity
						turnsActiveOfmetaActions.put(entry.getKey(), turnsActiveOfmetaActions.get(entry.getKey())-1);
					}
				}
				

			}
		}
		// unlock piece
		locked = false;
		absTime = MetaClock.getAbsoluteTime();
	}

	public void revertMetaActions() {
		// if no longer active, do a revert
		for (Map.Entry<MetaAction, Boolean> entry : metaActionIsActive
				.entrySet()) {
			// revert if MetaAction is active on model
			if (entry.getValue()) {
				// if the MetaAction is ranged, it's activity is based on the
				// models position on the board and not it's cooldown
				// the MetaAction returns a range, which means that it's a
				// ranged MetaAction
				if (entry.getKey().getRange(this) != null) {
					// check if model is still in range
					// revert board MetaAction if current tile no longer has
					// this
					// MetaAction
					ExtendedTileModel position = MetaMapping.getBoardModel()
							.getPiecePosition(this);
					if (MetaMapping.getBoardModel().getActiveMetaAction(
							position) != entry.getKey()) {
						entry.getKey().revert(this);
						metaActionIsActive.put(entry.getKey(), false);
						//set turns active back to 0
						turnsActiveOfmetaActions.put(entry.getKey(), 0);
					}
				}

				// it's not a ranged MetaAction
				else
				// revert if MetaAction is no longer active, but has been active
				if (turnsActiveOfmetaActions.get(entry.getKey()) == 0 ) {
					entry.getKey().revert(this);
					// set inactive in model
					entry.setValue(false);
				}

			}
		}
	}

	public void actMetaActions() {
		// get current position
		ExtendedTileModel position = MetaMapping.getBoardModel()
				.getPiecePosition(this);
		// act if on a tile with a MetaAction, a board MetaAction never locks
		// the piece
		// it also doesn't affect the MetaActions cooldown of this piece
		MetaAction boardMetaAction = MetaMapping.getBoardModel()
				.getActiveMetaAction(position);
		// check if this MetaAction is already registered
		// if so, check if not already active

		if (boardMetaAction != null) {
			if (metaActionIsActive.containsKey(boardMetaAction)
					&& !metaActionIsActive.get(boardMetaAction)) {
				boardMetaAction.act(this);
				// set active, because every MetaAction can only be executed
				// once on
				// a model
				metaActionIsActive.put(boardMetaAction, true);
				//save remaining turnsOfActivity
				ExtendedBoardModel board =  MetaMapping.getBoardModel();
				turnsActiveOfmetaActions.put(boardMetaAction, board.getActiveMetaActionTimeLeft(position));
				System.out.println(board.getActiveMetaActionTimeLeft(position));
			}
		}

		// iterate over all MetaAction that the piece can execute
		for (Map.Entry<MetaAction, Integer> entry : cooldownOfMetaActions
				.entrySet()) {

			MetaAction metaAction = entry.getKey();
			int turnsOfActivity = metaAction.getTurnsOfActivity(this);
			
			// act if the activity conditions of the MetaAction are met
			if (turnsOfActivity > 0) {
				// if the MetaAction is ranged,the execution of the
				// MetaAction
				// doesn't act, unless the model is on a tile of the
				// range
				// but set's range of the MetaAction on the board
				List<ExtendedTileModel> range = metaAction.getRange(this);
				// range!=null means it's a ranged MetaAction
				
				if (range != null) {
					// apply range to board
					for (ExtendedTileModel tile : range){
						entry.setValue(metaAction.getCooldown());
						//apply for each tile if not already applied to board
						MetaMapping.getBoardModel().setActiveMetaAction(
								metaAction, tile, this,turnsOfActivity);
					}
					if (metaAction.isLocking()) {
						locked = true;
					}
				}

				// if it's not a ranged MetaAction, you can execute it
				// directly if MetaAction is not active on this model
				
				else if (!metaActionIsActive.get(metaAction)) {
					
					//lock if needed
					if (metaAction.isLocking()) {
						locked = true;
					}
					//set active on model if the metaAction has a lasting effect->has to revert
					//no MetaAction is permanent
					if(metaAction.reverts()){
						metaActionIsActive.put(metaAction, true);
					}
					//set cooldown
					entry.setValue(metaAction.getCooldown());
					System.out.println(turnsOfActivity);
					//set turns of activity
					turnsActiveOfmetaActions.put(entry.getKey(), turnsOfActivity);
					//act
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
			ControllerType controllerType, int lives, int maxLives, int maxRange) {
		this.lives = lives;
		this.side = side;
		this.color = side;
		this.maxLives = maxLives;
		this.maxRange = maxRange;
		this.controllerType = controllerType;
		this.renderType = renderType;
		// fill with all available MetaActions
		cooldownOfMetaActions = new HashMap<>();
		metaActionIsActive = new HashMap<>();
		turnsActiveOfmetaActions= new HashMap<>();
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

	public Integer getCooldown(MetaAction metaAction) {
		return cooldownOfMetaActions.get(metaAction);
	}
	public Integer getTurnsOfActivity(MetaAction metaAction){
		return turnsActiveOfmetaActions.get(metaAction);
	}
	public void setMetaActionCooldown(MetaAction metaAction, int cooldown) {
		cooldownOfMetaActions.put(metaAction, cooldown);
	}

	public int getDecisionRange() {
		return decisionRange;
	}

	public void setDecisionRange(int decisionRange) {
		this.decisionRange = decisionRange;
	}

	public void setDirection(int i, int j) {
		direction[0] = i;
		direction[1] = j;
	}

	public int[] getDirection() {
		return direction;
	}

	public float getSize() {
		return MetaMapping.getBoardModel().getPiecePosition(this).getSize();
	}

	public float getX() {
		return MetaMapping.getBoardModel().getPiecePosition(this).getX();
	}

	public float getY() {
		return MetaMapping.getBoardModel().getPiecePosition(this).getY();
	}

	public ExtendedTileModel getTilePosition() {
		return MetaMapping.getBoardModel().getPiecePosition(this);
	}
}
