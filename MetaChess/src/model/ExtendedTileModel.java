package model;

import meta.MetaMapping;

public class ExtendedTileModel {
	private float x;
	private float y;
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
		if (MetaMapping.getBoardModel().getModelOnPosition(this) != null)
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

	public ExtendedTileModel(float x, float y, int color, float size, int i,
			int j, int level, ExtendedTileModel parent) {
		this.x=x;
		this.y=y;
		this.color=color;
		this.parent=parent;
		this.size = size;
		this.level = level;
		this.i = i;
		this.j = j;
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
		childFraction=fraction;
		ExtendedTileModel board = ((ExtendedTileModel) MetaMapping
				.getBoardModel().getRootTile());
		int absoluteFraction = absoluteFraction() * fraction;
		children = new ExtendedTileModel[fraction][fraction];
		for (int i = 0; i < fraction; i++) {
			for (int j = 0; j < fraction; j++) {
				if (fraction % 2 == 1) {
					children[i][j] = new ExtendedTileModel(x
							+ (float) (1 / absoluteFraction) * i
							* board.getSize(), y
							+ (float) (1 / absoluteFraction) * j
							* board.getSize(), ((i + j) % 2 + color) % 2,
							(float) (1 / absoluteFraction) * board.getSize(),
							i, j, level + 1, this);
				} else {
					children[i][j] = new ExtendedTileModel(x
							+ ((float) 1 / absoluteFraction) * i
							* board.getSize(), y
							+ ((float) 1 / absoluteFraction) * j
							* board.getSize(), (i + j) % 2,
							((float) 1 / absoluteFraction) * board.getSize(),
							i, j, level + 1, this);
				}
			}
		}
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
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

	

	

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
