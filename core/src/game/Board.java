package game;

import game.entities.Entity;
import game.entities.Escaper;
import game.entities.FinishPoint;
import game.entities.Spinner;
import game.entities.StartPoint;
import game.entities.Teleporter;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class Board implements java.io.Serializable{
	private static final long serialVersionUID = -404216002267032901L;
	public String name;
	public Tile[][] tiles = new Tile[Globals.BOARD_SIZE][Globals.BOARD_SIZE];
	public Escaper escaper;
	public boolean finished = false;
	public StartPoint startPoint;
	public LinkedList<Spinner> spinners = new LinkedList<Spinner>();
	public LinkedList<Teleporter> ports = new LinkedList<Teleporter>();
	public FinishPoint finishPoint;
	
	public Board(){
		for (int i = 0; i < Globals.BOARD_SIZE; i++) {
			for(int j = 0; j < Globals.BOARD_SIZE; j++){
//				if(pattern[i][j] == 1){
//					tiles[i][j] = new Tile(new Index(i,j), TileType.WALKABLE);
//					System.out.println("Creating Walkable");
//				}else{
					tiles[i][j] = new Tile(new Index(i,j), TileType.UNWALKABLE);
//				}
			}
			
		}
		name = "New Level";

	}
	
	public Tile getGameTile(Index i){
		
		Tile tmp = null;
		if (i.getX()<0 || i.getX() >= Globals.BOARD_SIZE || i.y >= Globals.BOARD_SIZE || i.y < 0){
			throw new IndexOutOfBoundsException("Board index out of bounds.");
		}else{
			tmp = tiles[i.getX()][i.y];
		}
		return tmp;
	}

	public void activateEntities(){
		if(escaper != null){
			Entity.addEntity(escaper);
		}
		if(startPoint != null){
			Entity.addEntity(startPoint);
		}
		if(finishPoint != null){
			Entity.addEntity(finishPoint);
		}
		if(spinners!= null){
			for (Spinner s : spinners) {
				Entity.addEntity(s);
			}
		}
		if(ports!= null){
			for (Teleporter t : ports){
				Entity.addEntity(t);
			}
		}
		System.out.println("Activated Board");
	}

	public void deactivate() {
		if(escaper != null){
			Entity.removeEntity(escaper);
		}
		if(startPoint!= null){
			Entity.removeEntity(startPoint);
		}
		if(finishPoint != null){
			Entity.removeEntity(finishPoint);
		}
		if(spinners!= null){
			for (Spinner s : spinners) {
				Entity.removeEntity(s);
			}
		}
		if(ports!= null){
			for (Teleporter t : ports) {
				Entity.removeEntity(t);
			}
		}
		System.out.println("Deactivated board.");
	}
	
}
