package game;

import com.badlogic.gdx.math.Vector2;
import java.io.Serializable;

public class Index implements Serializable{
	private int x;
	int y;
	
	public Index(){
		
	}
	
	public Index(int x, int y) {
		this.setX(x);
		this.y = y;
	}
	
	public boolean sameAs(Index otherIndex) {
		return this.getX() == otherIndex.getX() && this.y == otherIndex.y;
	}
	
	//Pivot point of the Vector is not in the middle yet
	public Vector2 getGamePos(){
		return new Vector2(getX() * Globals.TILE_SIZE, y * Globals.TILE_SIZE);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
}
