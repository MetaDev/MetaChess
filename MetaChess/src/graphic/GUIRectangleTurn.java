package graphic;

import logic.MetaClock;

public class GUIRectangleTurn extends GUIRectangle {

	public GUIRectangleTurn() {
		super(37, 25, 25, 50, 0);
	}

	@Override
	public float getAngle() {
		boolean turn = MetaClock.getTurn();
		// get fraction and side form player singleton
		if (turn){
			return 0;
		}
		else{
			return 90;
		}
	}

	@Override
	public void setAngle(float angle) {
		// not allowed
	}
}
