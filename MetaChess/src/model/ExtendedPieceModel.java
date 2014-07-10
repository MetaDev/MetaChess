package model;

import java.util.HashMap;
import java.util.Map;

import logic.DecisionLogic;
import meta.MetaClock;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;
import meta.MetaUtil;

public class ExtendedPieceModel {
	protected int color;
	protected int side;
	protected int absTime = 0;
	private int lives;
	private int previousFraction = 8;
	// board variables
	protected int influencedTileView = 0;
	protected int influencedMaxRange = 0;
	// only the rook will adapt this
	protected int viewing = 0;
	// only the bishop will adapt this
	protected int maxRange = 8;

	protected boolean hoover=false;
	public boolean isHoover() {
		return hoover;
	}

	public void setHoover(boolean hoover) {
		this.hoover = hoover;
	}

	// viewing doubles the view
	public int getNrOfViewTiles() {
		if (getTilePosition().getParent().getLevel() == 0) {
			return 4;
		}
		return Math.min((8 + influencedTileView) * (1 + viewing),
				getTilePosition().getAbsFraction() / 2);
	}

	protected int range = 1;

	protected boolean ignoreOccupationOfTile = false;
	protected boolean lethal = true;

	public boolean isLethal() {
		return lethal;
	}

	public void setLethal(boolean lethal) {
		this.lethal = lethal;
	}

	protected boolean penetrateLowerFraction = false;
	protected PieceType type;

	// this MetaAction is special because the cooldown and turns of activity
	// don't have to lowered with every turn change
	// and it's revert if the piece changes position to a tile without the
	// MetaAction

	public PieceType getType() {
		return type;
	}

	// save remaining cooldown of all metaActions applied on this model
	protected Map<String, Integer> cooldownOfDecisions = new HashMap<>();
	// save metaActions turnsOfActivity if it has a fixed amount of active turns
	// only decreased of the MetaAction has a cooldown on current model
	// this means that MetaActions without a cooldown don't change
	protected Map<String, Integer> turnsActiveOfDecisions = new HashMap<>();
	// saves all MetaActions executed on this activated on model, needed to
	// verify if MetaAction has
	// to be reversed
	protected Map<String, Boolean> activeDecisions = new HashMap<>();
	// decisions that have to be reverted at the end of the turn
	protected Map<String, Boolean> regrettedDecisions = new HashMap<>();
	// kept decision that will receive an increase of cooldwon when the turn
	// changes
	// the saved cooldown is the max cooldown of that turn based on range
	// so if the user change range in the turn it pays the cooldown for the
	// highest range
	protected Map<String, Integer> keptDecisionCooldown = new HashMap<>();;

	// if the piece already made a move this turn it's locked and can't be moved
	// again
	protected boolean locked = false;

	public boolean isPenetrateLowerFraction() {
		return penetrateLowerFraction;
	}

	public void setPenetrateLowerFraction(boolean penetrateLowerFraction) {
		this.penetrateLowerFraction = penetrateLowerFraction;
	}

	public void clearInfluence() {
		influencedTileView = 0;
		influencedMaxRange = 0;
	}

	public void setInfluence(int effect) {
		// only influence tileview if it's positive
		if (effect > 0)
			influencedTileView = effect;
		influencedMaxRange = effect;
	}

	// on the turn change, you should add cooldown to the currently active
	// decisions
	// cooldown is paid after every turn, with the highest range in that turn
	// cooldown is payed after making decision, and while it stays active after
	// every turn change
	public void turnChange() {
		// turn regretted decision non-active, and active turn=0 and regret them
		for (Map.Entry<String, Boolean> entry : regrettedDecisions.entrySet()) {
			if (entry.getValue()) {
				keptDecisionCooldown.put(entry.getKey(), 0);
				activeDecisions.put(entry.getKey(), false);
				turnsActiveOfDecisions.put(entry.getKey(), 0);
				DecisionLogic.regret(entry.getKey(), this);
				// remove from regretted list
				entry.setValue(false);
			}
		}
		ExtendedTileModel position = MetaConfig.getBoardModel()
				.getPiecePosition(this);
		int newFraction = position.getAbsFraction();
		if (previousFraction <= newFraction) {
			// decrease cooldown of non-active decisions with cooldown, if on
			// same fraction or higher as previous turn
			for (Map.Entry<String, Integer> entry : cooldownOfDecisions
					.entrySet()) {
				if (!activeDecisions.get(entry.getKey())) {
					if (entry.getValue() > 0) {
						// decrease by number of minimum turns passed on this
						// fraction
						entry.setValue(Math.max(
								entry.getValue()
										- (Math.max(MetaClock.getMaxFraction()
												/ position.getAbsFraction(), 1)),
								0));
						turnsActiveOfDecisions.put(entry.getKey(),
								turnsActiveOfDecisions.get(entry.getKey()) - 1);
					}
				}
			}
		}

		// increase cooldown and turnsactive of kept decisions
		for (Map.Entry<String, Integer> entry : keptDecisionCooldown.entrySet()) {
			// increase
			if (entry.getValue() > 0) {
				cooldownOfDecisions.put(
						entry.getKey(),
						cooldownOfDecisions.get(entry.getKey())
								+ entry.getValue());
				turnsActiveOfDecisions.put(entry.getKey(),
						turnsActiveOfDecisions.get(entry.getKey()) + 1);
				// and after using the max cooldown to increase, reset it
				entry.setValue(0);
			}

		}
		// unlock piece
		locked = false;
		absTime = MetaClock.getAbsoluteTime();
	}

	public int[][] getGrid() {
		return MetaConfig.getIcon(type.name());
	}

	/*
	 * makes decisions or regrets them based on the input And the current tile
	 * standing on
	 */
	public void decideAndRegret(String inputSequence) {

		// no input, no decisions or regretes
		// TODO: here can be saved wether a team makes at least 1 decision every
		// main turn
		if (inputSequence.equals("")) {
			return;
		}

		String[] inputs = inputSequence.split(";");
		int i = 0;
		// regret or decide in pressed key
		while (i < inputs.length
				&& (inputs[i].startsWith("RELEASE") || inputs[i]
						.startsWith("PRESS"))) {

			// a key has been released
			// always a single key
			// translate key to decision
			String regretOrDecision = MetaConfig.getKeyMapping().get(type)
					.get(inputs[i].split(":")[1]);
			// does this type contains this decision
			if (regretOrDecision != null) {
				// first handle if range change
				if (MetaUtil.isNumeric(regretOrDecision)) {
					int changeRange = MetaUtil
							.convertToInteger(regretOrDecision);
					if (inputs[i].startsWith("RELEASE")) {
						range -= changeRange;
					} else if (inputs[i].startsWith("PRESS")) {
						range += changeRange;
					}

				} else {
					// regret
					if (inputs[i].startsWith("RELEASE")) {
						regret(regretOrDecision);
					}
					// decision
					if (inputs[i].startsWith("PRESS")) {
						// if the conditions: activity, cooldown and turn are
						// okay
						if (DecisionLogic.conditionsMet(regretOrDecision, this)) {
							// lock if its a movement, unless under lowest
							// fraction
							if (MetaConfig.getDirectionArray(regretOrDecision,
									this) != null
									&& getTilePosition().getAbsFraction() <= MetaClock
											.getMaxFraction()) {
								locked = true;
							}
							decide(regretOrDecision);
						}
					}
				}

			}
			// skip seen input
			i++;
		}

		String keptDecision = "";
		// save the largest cooldown when a decision is kept
		// or redecide if a movement is being held down
		for (; i < inputs.length; i++) {
			keptDecision = MetaConfig.getKeyMapping().get(type)
					.get(inputs[i].split(":")[1]);
			if (MetaConfig.getKeyMapping().get(type)
					.containsValue(keptDecision)) {

				// if this decision is a special one and belongs to this piece
				// type, don't redecide but
				// increase cooldown and turnsactive
				// the corresponding decision should already be active
				if (MetaConfig.getSpecialsSet().containsKey(keptDecision)
						&& MetaConfig.getKeyMapping().get(type)
								.containsValue(keptDecision)) {
					raiseCooldownAndTurnsActiveAfterTurn(keptDecision);
				} else if (getTilePosition().getAbsFraction() <= MetaClock
						.getMaxFraction()) {
					// a movement
					if (DecisionLogic.conditionsMet(keptDecision, this)) {
						decide(keptDecision);
						locked = true;
					}

				}

			}

		}

	}

	private void raiseCooldownAndTurnsActiveNow(String decision) {
		ExtendedTileModel position = MetaConfig.getBoardModel()
				.getPiecePosition(this);
		cooldownOfDecisions.put(decision,
				DecisionLogic.getCooldown(position.getAbsFraction(), range));
		turnsActiveOfDecisions.put(decision, 1);
	}

	// save max cooldown of this turn
	private void raiseCooldownAndTurnsActiveAfterTurn(String decision) {
		ExtendedTileModel position = MetaConfig.getBoardModel()
				.getPiecePosition(this);
		keptDecisionCooldown.put(decision, Math.max(
				DecisionLogic.getCooldown(position.getAbsFraction(), range),
				keptDecisionCooldown.get(decision)));
	}

	// handles all that has to be done to regret decision
	// turns active 0, active false, start countdown cooldown-> by turning on
	// non-active
	// regret only initialises the regretting process, a method can only be
	// undone after a turn is over
	public void regret(String regret) {
		regrettedDecisions.put(regret, true);

	}

	// handle the decision
	// set initial cooldown, set active, set turnsactive to 1
	public void decide(String decision) {
		DecisionLogic.decide(decision, this);
		// if it's a movent decision no cooldown is added or made active on the
		// model
		if (MetaConfig.getSpecialsSet().keySet().contains(decision)) {
			activeDecisions.put(decision, true);
			// initial cooldown is payed directly
			raiseCooldownAndTurnsActiveNow(decision);
		}
	}

	// executes incoming decision from server
	// input shouldn't be checked
	// turn, activity and cooldown shouldn't be checked either, but maybe later
	// as check to be sure no mistakes are made
	// the cooldown and activity of this model should be set, because it could
	// be that the player switches of piece
	// public void makeServerDecision(DecisionLogic decisionLogic) {
	// // decide decision
	// dDecisionLogic.decide(decision,this);
	// }

	public boolean isMetaActionActive(DecisionLogic metaAction) {
		return activeDecisions.get(metaAction);
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

	public int getLives() {
		return lives;
	}

	public ExtendedPieceModel(PieceType type, int side, int lives) {
		this.side = side;
		this.color = side;
		this.type = type;
		this.lives = lives;
		// fill all mappings
		for (String decision : MetaConfig.getKeyMapping().get(type).values()) {
			cooldownOfDecisions.put(decision, 0);
			turnsActiveOfDecisions.put(decision, 0);
			activeDecisions.put(decision, false);
			regrettedDecisions.put(decision, false);
			keptDecisionCooldown.put(decision, 0);
		}
	}

	public Map<String, Integer> getCooldownOfDecisions() {
		return cooldownOfDecisions;
	}

	public Map<String, Integer> getTurnsActiveOfDecisions() {
		return turnsActiveOfDecisions;
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

	// the range is ranged between 1 and 8 + influencedRange
	public int getRange() {
		return Math.max(Math.min(range, maxRange + influencedMaxRange), 1);
	}

	// if range is set negative the directions are inverted
	public void setRange(int range) {
		this.range = range;

	}

	public int getCooldown(String decision) {
		if (cooldownOfDecisions.containsKey(decision)) {
			return cooldownOfDecisions.get(decision);
		} else {
			return 0;
		}

	}

	public Integer getTurnsOfActivity(String decision) {
		if (turnsActiveOfDecisions.containsKey(decision)) {
			return turnsActiveOfDecisions.get(decision);
		} else {
			return 0;
		}

	}

	public void setDecisionCooldown(String decision, int cooldown) {
		cooldownOfDecisions.put(decision, cooldown);
	}

	public float getRelSize() {
		return MetaConfig.getBoardModel().getPiecePosition(this).getRelSize();
	}

	public ExtendedTileModel getTilePosition() {
		return MetaConfig.getBoardModel().getPiecePosition(this);
	}

	public String getPendingDecision() {
		return null;
	}

	public int isDragon() {
		return 0;
	}

	public void setDragon(int dragon) {
		// do nothing
	}

	public int getViewing() {
		return viewing;
	}

	public void setViewing(int viewing) {
		// do nothing
	}

	public void setMaxRange(int maxRange) {
		// do nothing
	}

	public int getMaxRange() {
		return 8;
	}

	public void setTurn(int turn) {
		// do nothing
	}

	public int getTurn() {
		return 0;
	}

	public void setDirection(String direction) {
		// do nothing
	}

	public String getDirection() {
		return null;
	}

	public int getRogue() {
		return 0;
	}

	public void setRogue(int rogue) {
		// do nothing
	}

}
