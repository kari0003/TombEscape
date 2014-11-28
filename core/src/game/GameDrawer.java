package game;

import game.Board;
import game.Globals;
import game.Tile;
import game.TileType;
import game.entities.Entity;
import game.entities.Escaper;
import game.entities.FinishPoint;
import game.entities.Spinner;

import javax.swing.JFrame;

import main.TombEscapeGame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.sun.prism.GraphicsPipeline.ShaderType;

public class GameDrawer{
	JFrame frame;
	SpriteBatch batch;
	
	Texture unwalkable_tile;
	Texture walkable_tile;
	Texture finishpaint;
	Sprite rallypoint;
	Sprite escaper;
	Sprite spinner;
	
	public GameDrawer(){
		batch = new SpriteBatch();
		
		unwalkable_tile = new Texture("lava.png");
		walkable_tile = new Texture("bricks.png");
		finishpaint = new Texture("finishpoint.png");
		escaper = new Sprite(new Texture("escaper.png"));
		spinner = new Sprite(new Texture("blades.png"));
		rallypoint = new Sprite(new Texture("blades.png"));
	}
	
	public void render(){
			System.out.println("GameDrawer - render");
			drawGame();
		
	}
	
	public void drawGame(){
		System.out.println("GameDrawer - drawGame");
		
		if(TombEscapeGame.getActiveBoard() != null){
			batch.begin();
			Board board = TombEscapeGame.getActiveBoard();
			drawBoard( board);
			drawFinish(board.finishPoint);
			System.out.println("GameDrawer - drawSpinner-s");
			for (Spinner spinner : board.spinners) {
				drawSpinner(spinner);
			}
			
			drawEscaper( board.escaper);
			batch.end();
			
		}
		
	}
	
	public void drawBoard(Board b){
		System.out.println("GameDrawer - drawBoard");
		for(int i = 0; i < Globals.BOARD_SIZE ; i++){
			for(int j = 0; j < Globals.BOARD_SIZE ; j++){
				drawTile(b.tiles[i][j]);
			}
		}
	}
	
	public void drawTile(Tile t){
		if(t.type == TileType.UNWALKABLE){
			Vector2 pos = t.pos.getGamePos();
			batch.draw(unwalkable_tile, pos.x, pos.y);
		}
		if(t.type == TileType.WALKABLE){
			Vector2 pos = t.pos.getGamePos();
			batch.draw(walkable_tile, pos.x, pos.y);
		}
	}
	
	private static ShapeRenderer sr = new ShapeRenderer();
	
	public void drawEscaper( Escaper e ){
		batch.end();
		sr.setProjectionMatrix(TombEscapeGame.cam.combined);
		sr.begin(ShapeType.Line);
		sr.setColor(1,1,0,0.5f);
		sr.circle(e.pos.x, e.pos.y, Globals.EXPLORER_SIZE);
		sr.end();
		batch.begin();
		escaper.setPosition( (float)e.pos.x - escaper.getWidth()/2,  (float) e.pos.y - escaper.getHeight()/2);
		escaper.setRotation((float) (e.getFacing() ) );
		if(e.order_dest != null){
			rallypoint.setScale(0.125f);
			rallypoint.setColor(Color.GREEN);
			rallypoint.setPosition( (float) e.order_dest.x - 64,(float) e.order_dest.y - 64);
			rallypoint.draw(batch);
		}
		escaper.draw(batch);
	}

	private void drawSpinner(Spinner s) {
		spinner.setScale(0.6f);
		float offset = - spinner.getHeight()/2;
		spinner.setRotation( s.facing );
		spinner.setPosition( s.pos.x + offset,s.pos.y + offset );
		batch.end();
			sr.setProjectionMatrix(TombEscapeGame.cam.combined);
			sr.begin(ShapeType.Line);
			sr.setColor(1,0,0,0.5f);
			sr.circle(s.pos.x, s.pos.y, s.getSize());
		sr.end();
		batch.begin();
		spinner.draw(batch);		
	}
	
	private void drawFinish(FinishPoint f) {
		System.out.println("GameDrawer - drawfinishPoint");
		batch.draw(finishpaint, f.pos.x - Globals.TILE_SIZE/2, f.pos.y - Globals.TILE_SIZE/2);
	}

}
