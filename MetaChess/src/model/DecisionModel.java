package model;

public class DecisionModel {
	private boolean regrets;
	// this is to compensate if a power is OP or not
	private int cooldownConstant = 1;

	private CooldownType cooldownType;

	private ButtonType buttonType;
	private int key;
	private int[] direction;
	private String name;

	public boolean isRegrets() {
		return regrets;
	}

	public int getCooldownConstant() {
		return cooldownConstant;
	}

	public CooldownType getCooldownType() {
		return cooldownType;
	}

	public ButtonType getButtonType() {
		return buttonType;
	}

	
	public int getKey() {
		return key;
	}

	public int[] getDirection() {
		return direction;
	}

	public String getName() {
		return name;
	}

	public enum ButtonType {
		HOLD, RELEASE,PRESS
	}

	public enum CooldownType {
		NONE, CONSTANT, RELATIVETORANGE
	}

	public enum MovementType {
		NONE, DIAGONAL, ORTHOGONAL, LSHAPED
	}

	public DecisionModel(boolean regrets,
			CooldownType cooldownType, ButtonType buttonType,
			int key, int[] direction, String name) {
		super();
		this.regrets = regrets;
		this.cooldownType = cooldownType;
		this.buttonType = buttonType;
		this.key = key;
		this.direction = direction;
		this.name = name;
	}
}
