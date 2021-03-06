package main;

import game.Board;
import game.GameState;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import screens.BoardScreen;
import screens.MenuScreen;
import screens.EditScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class TombEscapeGame extends Game {
	public static TombEscapeGame game;
	GameState state;
	public static String loadMe = "";
	public static BoardScreen boardscreen;
	public static MenuScreen menuscreen;
	public static EditScreen editscreen;
	public static Board activeBoard;
	public static boolean paused;
	
	private static int level = 0;
	private static ArrayList<String> levels = new ArrayList<String>();
	
	@Override
	public void create () {
		game = this;
		
		boardscreen = new BoardScreen();
		menuscreen = new MenuScreen();
		editscreen = new EditScreen();
		
		startMenu();
		
		paused = false;
	}
	
	
	public static Board getActiveBoard(){
		return activeBoard;
	}
	
	public static TombEscapeGame getGame(){
		return game;
	}
	public static void pauseGame(boolean p){
		paused = p;
	}
	
	public void startMenu() {
		if(activeBoard != null){
			activeBoard.deactivate();
		}

		state = GameState.MENU;
	
		setScreen(menuscreen);
	}

	public void startGame() {
		level = 0;
		state = GameState.GAME;
		boardscreen.won = false;
		try {
			levels.clear();
			File maps = new File("." + File.separator + "maps");
			String[] maplist= maps.list();
			for(String s : maplist){
				if(s.contains(".tomb")){
					String name = s.substring(0,s.length()-5);
					levels.add(name);
					System.out.println(name);
				}
			}
			if(levels.size() != 0){
				activeBoard = loadBoard(levels.get(level));
				setScreen(boardscreen);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public static void finishLevel(){
		level++;
		if(levels.size() > level){
			activeBoard.finished = true;
			activeBoard.deactivate();
			loadMe = levels.get(level);
			System.out.println("Loaded map. - " + getActiveBoard().name);
		}else{
			boardscreen.won = true;
			activeBoard.finished = true;
			//System.out.println("YOU HAVE WON THE GAME PRICK!!!!!!!!!!!!!!");
		}
	}



	public void startEditor() {
		Board editable = new Board();
		editscreen.setBoard(editable);
		setScreen(editscreen);
		activeBoard = editable;
	}

	public static Board loadBoard(String name) throws IOException, ClassNotFoundException{
		String path = "." + File.separator + "maps" + File.separator + name + ".tomb";
		System.out.println("Loading: " + path);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
		Board b =(Board) ois.readObject();
		ois.close();
		System.out.println("Activating entitties.");
		b.activateEntities();
		activeBoard = b;
		return b;
	}
	
	public static void saveBoard(Board board) throws FileNotFoundException, IOException{
		String path = "." + File.separator + "maps" + File.separator + board.name + ".tomb";
		System.out.println("Saving: " + path);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
		oos.writeObject(board);
		oos.close();
		System.out.println("Board saved at: " + board.name + ".tomb");
		//board.deactivate();
		//board = null;
	}

}
