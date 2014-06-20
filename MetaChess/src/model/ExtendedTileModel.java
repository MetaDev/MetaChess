package model;

import meta.MetaConfig;

public class ExtendedTileModel {

	private float absSize;
	private float absPosX;
	private float absPosY;
	private float size;
	private int color;
	private ExtendedTileModel[][] children;
	private ExtendedTileModel parent;
	private int level;
	private int childFraction = 1;

	// relative position in parent
	private int i;
	private int j;

	public boolean isOccupied() {
		if (MetaConfig.getBoardModel().getModelOnPosition(this) != null)
			return true;
		if (children == null)
			return false;
		for (ExtendedTileModel[] childrow : children) {
			for (ExtendedTileModel child : childrow) {
				if (child.isOccupied()) {
					return true;
				}
			}
		}
		return false;
	}

	// mothertile aka "The Board"
	public ExtendedTileModel(int color, float size) {
		this.color = color;
		this.size = size;

	}

	// any child tile
	public ExtendedTileModel(int color, int i, int j, int level,
			ExtendedTileModel parent) {
		this.color = color;
		this.parent = parent;
		this.level = level;
		this.i = i;
		this.j = j;
		this.absPosX = getAbsX();
		this.absPosY = getAbsY();
		this.absSize = getAbsSize();

	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public void undivide() {
		children = null;
	}

	public int absoluteFraction() {
		if (parent != null) {
			return parent.getChildFraction() * parent.absoluteFraction();

		} else {
			return 1;
		}

	}

	// don't allow empty (null) children
	public void divide(int fraction) {
		childFraction = fraction;
		children = new ExtendedTileModel[fraction][fraction];
		for (int i = 0; i < fraction; i++) {
			for (int j = 0; j < fraction; j++) {
				if (fraction % 2 == 1) {
					children[i][j] = new ExtendedTileModel(
							((i + j) % 2 + color) % 2, i, j, level + 1, this);
				} else {
					children[i][j] = new ExtendedTileModel((i + j) % 2, i, j,
							level + 1, this);
				}
			}
		}
	}

	public int getChildFraction() {
		return childFraction;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void removeChild(int i, int j) {
		children[i][j] = null;
	}

	public ExtendedTileModel[][] getChildren() {
		return children;
	}

	public ExtendedTileModel getParent() {
		return parent;
	}

	// implement recursive positioning and size
	public float getRelX() {
		if (parent != null) {
			return i * getRelSize();
		} else {
			return 0;
		}
	}

	public float getRelY() {
		if (parent != null) {
			return j * getRelSize();
		} else {
			return 0;
		}
	}

	public float getAbsX() {
		if (absPosX == 0 && parent != null) {
			return getRelX() + parent.getAbsX();
		} else {
			return absPosX;
		}

	}

	public float getAbsY() {
		if (absPosY == 0 && parent != null) {
			return getRelY() + parent.getAbsY();
		} else {
			return absPosY;
		}
	}

	public int getAbsFraction() {
		if (parent != null) {
			return parent.childFraction + parent.getAbsFraction();
		} else {
			return 0;
		}
	}

	// get size relative to container

	public float getRelSize() {
		if (absSize == 0) {
			if (parent != null) {
				return (parent.getRelSize() / parent.getChildFraction());
			} else {
				return size;
			}
		} else {
			return absSize;
		}

	}

	public float getAbsSize() {
		if (parent != null) {
			return parent.getAbsSize() / parent.getChildFraction();
		} else {
			return size;
		}
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
