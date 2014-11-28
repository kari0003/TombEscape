package main;

import game.Board;
import game.GameCamera;
import game.GameDrawer;
import game.GameState;
import game.entities.Entity;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TombEscapeGame extends Game {
	GameState state;
	GameDrawer drawer;
	public static String loadMe = "";
	public static GameCamera cam;
	public static Board activeBoard;
	public static boolean paused;
	
	private static int level = 0;
	private static ArrayList<String> levels = new ArrayList<String>();
	
	@Override
	public void create () {
		levels.add("Level1");
		levels.add("Level2");
		
		state = GameState.GAME;
		
		//activeBoard = new Board();

		if(false){
			activeBoard = new Board();
		}else{
			try {
				activeBoard = loadBoard(levels.get(level));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		drawer = new GameDrawer();
		
		cam = new GameCamera();
		
		paused = false;
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render () {
		System.out.println("Rendering Game...");
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(state == GameState.GAME){
			cam.update();
			drawer.batch.setProjectionMatrix(cam.combined);
			System.out.println("Drawer:render");
			drawer.render();
			if(!paused){
				Entity.updateEntities();	
			}	
		}
		
	}
	
	public static Board getActiveBoard(){
		return activeBoard;
	}
	
	public static void pauseGame(boolean p){
		paused = p;
	}
	public static void finishLevel(){
		level++;
		if(levels.size() < level){
			activeBoard.finished = true;
			activeBoard.deactivate();
			loadMe = levels.get(level);
			System.out.println("Loaded map. - " + getActiveBoard().name);
		}
	}
	
	public static Board loadBoard(String name) throws IOException, ClassNotFoundException{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("maps\\" + name +".tomb"));
		Board b =(Board) ois.readObject();
		ois.close();
		System.out.println("Activating entitties.");
		b.activateEntities();
		activeBoard = b;
		return b;
	}
	
	public static void saveBoard(Board board) throws FileNotFoundException, IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("maps\\" + board.name + ".tomb"));
		oos.writeObject(board);
		oos.close();
		System.out.println("Board saved at: " + board.name + ".tomb");
		board.deactivate();
		//board = null;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		if(character == 's'){
			if(state == GameState.GAME){
				try {
					saveBoard(getActiveBoard());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 ordPos = new Vector3(screenX, screenY, 0);
		cam.unproject(ordPos);
		if(button == Buttons.LEFT){
			if(state == GameState.GAME){
				getActiveBoard().escaper.order_dest = new Vector2(ordPos.x,ordPos.y);
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
//		if(Gdx.input.isButtonPressed(Buttons.LEFT)){
//			Vector3 ordPos = new Vector3(screenX, screenY, 0);
//			cam.unproject(ordPos);
//			if(state == GameState.GAME){
//				getActiveBoard().escaper.order_dest = new Vector2(ordPos.x,ordPos.y);
//			}
//		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
