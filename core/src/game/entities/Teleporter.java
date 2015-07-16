package game.entities;

import game.Globals;

import java.io.Serializable;

import main.TombEscapeGame;

import com.badlogic.gdx.math.Vector2;

public class Teleporter extends Entity{
	private Vector2 destination;

	public Teleporter(Vector2 pos, Vector2 dest){
		super(pos);
		this.destination = new Vector2(dest);
		
	}
	
	@Override
	public void update(){
		float dst = pos.dst(TombEscapeGame.getActiveBoard().escaper.pos) ;
		if( dst < Globals.CHECKPOINT_SIZE ){
			System.out.println("ITTVAN");
			TombEscapeGame.getActiveBoard().escaper.pos = new Vector2(destination);
			System.out.println("going from " + TombEscapeGame.getActiveBoard().escaper.pos.toString() + " to " + destination);
			TombEscapeGame.getActiveBoard().escaper.order_dest = new Vector2(destination);
		}

	}
}
