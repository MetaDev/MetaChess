package model.paramobjects;

public class ParamObjectsAcces {
	private static ParamObject poView;
	private static ParamObject poMovement;
	private static ParamObject poLives;
	private static ParamObject poNrOfTurn;

	public static ParamObject getPOView() {
		if (poView == null)
			poView = new POTileView();
		return poView;
	}

	public static ParamObject getPOMovement() {
		if (poMovement == null)
			poMovement = new PORange();
		return poMovement;
	}

	

	public static ParamObject getPoLives() {
		 if(poLives==null)
			 poLives=new POLives();
		return poLives;
	}


	public static ParamObject getPoNrOfTurn() {
		 if(poNrOfTurn==null)
			 poNrOfTurn=new PONrOfTurn();
		return poNrOfTurn;
	}

}
