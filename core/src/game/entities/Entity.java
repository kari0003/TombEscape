package game.entities;

import game.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import main.TombEscapeGame;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity  implements Serializable{
	private static transient LinkedList<Entity> instances = new LinkedList<Entity>();
	public Vector2 pos;
	boolean active = true;
	boolean dead = false;
	
	public Entity(Vector2 pos){
		this.pos = new Vector2(pos);
		instances.add(this);
	}
	
	public static void updateEntities(){
		System.out.println("Updating Entities:");
		for(Iterator<Entity> it = instances.iterator(); it.hasNext();) {
			Entity e = it.next();
			if(e.dead) {
				it.remove();
			}else {
				e.update();
			}
		}
		// Load new Map if finished
		if(TombEscapeGame.loadMe.length() > 0) {
			try {
				TombEscapeGame.loadBoard(TombEscapeGame.loadMe);
			} catch (Exception e) {
				e.printStackTrace();
			}
			TombEscapeGame.loadMe = "";
		}
	}
	
	abstract protected void update();
	
	public Vector2 getPos(){
		return new Vector2(pos.x,pos.y);
	}
	
	public static void addEntity(Entity e){
		instances.add(e);
	}
	public static void removeEntity(Entity e){
		e.dead = true;
	}
}

