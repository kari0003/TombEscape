package game.entities;

import game.Globals;

import java.io.Serializable;

import main.TombEscapeGame;

import com.badlogic.gdx.math.Vector2;

public class Teleporter extends Entity implements Serializable{
	private Vector2 destination;

	public Teleporter(Vector2 pos, Vector2 dest){
		super(pos);
		this.destination = dest;
		
	}
	
	@Override
	public void update(){
		if( pos.dst(TombEscapeGame.getActiveBoard().escaper.pos) < Globals.CHECKPOINT_SIZE ){
			TombEscapeGame.getActiveBoard().escaper.pos = destination;
			TombEscapeGame.getActiveBoard().escaper.order_dest = destination;
		}

	}
}