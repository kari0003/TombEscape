package screens;

import game.GameCamera;
import game.GameDrawer;
import game.GameState;
import game.entities.Entity;

import java.io.FileNotFoundException;
import java.io.IOException;

import main.TombEscapeGame;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class BoardScreen implements Screen, InputProcessor{
//etc, copy from TEGame the needed parts, then set the screen in TEGame

	GameDrawer drawer;
	public static GameCamera cam;
	
	public BoardScreen(){
		
		cam = new GameCamera();
		drawer = new GameDrawer();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawer.batch.setProjectionMatrix(cam.combined);
		drawer.render();
		cam.update();
		System.out.println("Drawer:render");
		if(!TombEscapeGame.paused){
			Entity.updateEntities();
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
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 ordPos = new Vector3(screenX, screenY, 0);
		cam.unproject(ordPos);
		if(button == Buttons.LEFT){
			TombEscapeGame.getActiveBoard().escaper.order_dest = new Vector2(ordPos.x,ordPos.y);
			
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
	//	if(Gdx.input.isButtonPressed(Buttons.LEFT)){
//			Vector3 ordPos = new Vector3(screenX, screenY, 0);
//			cam.unproject(ordPos);
//			TombEscapeGame.getActiveBoard().escaper.order_dest = new Vector2(ordPos.x,ordPos.y);
//			
	//	}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
