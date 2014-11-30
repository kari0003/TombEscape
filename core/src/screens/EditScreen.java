package screens;

import game.Board;
import game.GameCamera;
import game.GameDrawer;
import game.Globals;
import game.Tile;
import game.TileType;
import game.entities.Escaper;
import game.entities.FinishPoint;
import game.entities.Spinner;
import game.entities.StartPoint;

import java.io.FileNotFoundException;
import java.io.IOException;

import main.TombEscapeGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class EditScreen implements Screen,InputProcessor {

	Board board;
	GameDrawer drawer;
	public static GameCamera cam;
	Vector2 mouseposition;
	
	Vector2 spinnerstart;
	boolean spinnerplacing;
	
	boolean saving;
	
	Stage stage;
	TextField textfield;
	TextButton button;
	
	public EditScreen(){
		drawer = new GameDrawer();

		cam = new GameCamera();
		
		spinnerplacing = false;
		mouseposition = new Vector2(0,0);
		
		Skin ui = new Skin(Gdx.files.internal("uiskin.json"));
		textfield = new TextField(" ", ui);
		button = new TextButton("Ok.", ui);
		
		button.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				board.name = textfield.getText();
				save();
				saving = false;
				setListener();
			}
			
		});
		
		stage = new Stage();
		Table t = new Table();
		
		t.add(new Label("Map name: ", ui));
		t.setFillParent(true);
		t.add(textfield).row();
		t.add(button);
		stage.addActor(t);
	}
	
	public void setBoard(Board b){
		board = b;
	}
	
	public void setListener(){
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawer.batch.setProjectionMatrix(cam.combined);
		drawer.render();
		
		if (spinnerplacing){
			float off = - drawer.spinner.getHeight()/2;
			drawer.batch.begin();
			drawer.spinner.setPosition(spinnerstart.x + off, spinnerstart.y + off);
			drawer.spinner.setAlpha(0.7f);
			drawer.spinner.draw(drawer.batch);
			drawer.spinner.setPosition(mouseposition.x + off, mouseposition.y + off);
			drawer.spinner.setAlpha(0.5f);
			drawer.spinner.draw(drawer.batch);
			drawer.batch.end();
			drawer.spinner.setAlpha(1);
		}
		if(saving){
			stage.draw();
		}
		
		cam.update();
		
	}
	
	public void save(){
		try {
			TombEscapeGame.saveBoard(board);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.ESCAPE){
			TombEscapeGame.getGame().startMenu();
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		if(character == 'e' || character == 'f'){
			Vector2 pos = board.getGameTile(Globals.getIndex(mouseposition)).pos.getGamePos().add(new Vector2(Globals.TILE_SIZE/2, Globals.TILE_SIZE/2));
			if(character == 'e'){
				board.escaper = new Escaper(pos);
				board.startPoint = new StartPoint(pos);
				
			}else{
				board.finishPoint = new FinishPoint(pos);
			}
		}else if(character == 'r' && !spinnerplacing){
			spinnerplacing = true;
			spinnerstart = new Vector2( mouseposition.x, mouseposition.y);
		}else if(character == 's'){
			if(board.finishPoint != null && board.escaper!= null && board.startPoint != null){
				saving = true;
				Gdx.input.setInputProcessor(stage);
			}
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 ordPos = new Vector3(screenX, screenY, 0);
		cam.unproject(ordPos);
		Vector2 pos = new Vector2(ordPos.x,ordPos.y);
		if(button == Buttons.LEFT){
			if (!spinnerplacing){
				Tile t = board.getGameTile(Globals.getIndex(pos));
				t.type = t.type == (TileType.UNWALKABLE) ? TileType.WALKABLE : TileType.UNWALKABLE; 
			
			}else{
			board.spinners.add(new Spinner(spinnerstart, pos));
			spinnerplacing = false;
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
		Vector3 pos = new Vector3(screenX,screenY,0);
		cam.unproject(pos);
		mouseposition.set(pos.x,pos.y);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
