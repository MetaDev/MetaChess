package model;

import meta.MetaMapping.ControllerType;
import meta.MetaMapping.GUIPosition;
import graphic.Graphic;

public class GUIExtendedModel extends ExtendedModel {
	protected GUIPosition position;

	public GUIExtendedModel(Graphic graphic, ControllerType controllerType,
			GUIPosition position) {
		super(graphic, controllerType);
		this.position = position;
	}

	public GUIPosition getPosition() {
		return position;
	}

	public void setPosition(GUIPosition position) {
		this.position = position;
	}

}
