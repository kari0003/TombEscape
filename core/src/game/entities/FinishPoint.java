package game.entities;

import game.Globals;

import java.io.Serializable;

import main.TombEscapeGame;

import com.badlogic.gdx.math.Vector2;

public class FinishPoint extends Entity implements Serializable{

	public FinishPoint(Vector2 pos){
		super(pos);
		
	}
	
	@Override
	public void update(){
		if( pos.dst(TombEscapeGame.getActiveBoard().escaper.pos) < Globals.CHECKPOINT_SIZE ){
			TombEscapeGame.finishLevel();
			active = false;
			dead = true;
			//TombEscapeGame.pauseGame(true);
		}

	}
}
