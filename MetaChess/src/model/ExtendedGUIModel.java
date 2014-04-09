package model;

import userinterface.GUI;
import meta.MetaMapping.GUIPosition;

public class ExtendedGUIModel {
	private GUIPosition position;
	private GUI gui;
	
	public ExtendedGUIModel( GUI gui,GUIPosition position) {
		this.position = position;
		this.gui = gui;
	}
	public GUIPosition getPosition() {
		return position;
	}
	public void setPosition(GUIPosition position) {
		this.position = position;
	}
	public GUI getGui() {
		return gui;
	}
	
	
}
