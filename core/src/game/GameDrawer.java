package game;

import game.entities.Escaper;
import game.entities.FinishPoint;
import game.entities.Spinner;
import game.entities.Teleporter;

import javax.swing.JFrame;

import main.TombEscapeGame;
import screens.BoardScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class GameDrawer{
	JFrame frame;
	public SpriteBatch batch;
	
	Sprite unwalkable_tile;
	Sprite walkable_tile;
	Sprite finishpaint;
	public Sprite teleporter_sprite;
	Sprite rallypoint;
	Sprite escaper;
	public Sprite spinner;
	public Vector2 tileoffset;
	
	
	public GameDrawer(){
		batch = new SpriteBatch();
		
		unwalkable_tile = new Sprite(new Texture("lava.png"));
		walkable_tile = new Sprite(new Texture("bricks.png"));
		finishpaint = new Sprite(new Texture("finishpoint.png"));
		teleporter_sprite = new Sprite(new Texture("teleporter.png"));
		escaper = new Sprite(new Texture("escaper.png"));
		spinner = new Sprite(new Texture("blades.png"));
		rallypoint = new Sprite(new Texture("blades.png"));
		
		unwalkable_tile.setBounds(0, 0, Globals.TILE_SIZE, Globals.TILE_SIZE);
		walkable_tile.setBounds(0, 0, Globals.TILE_SIZE, Globals.TILE_SIZE);
		finishpaint.setBounds(0, 0, Globals.TILE_SIZE, Globals.TILE_SIZE);
		teleporter_sprite.setBounds(0, 0, Globals.TILE_SIZE, Globals.TILE_SIZE);
		spinner.setBounds(0, 0, Globals.SPINNER_SIZE*2, Globals.SPINNER_SIZE*2);
		spinner.setOrigin(Globals.SPINNER_SIZE, Globals.SPINNER_SIZE);

		escaper.setBounds(0, 0, Globals.EXPLORER_SIZE*2 + 8, Globals.EXPLORER_SIZE*2 + 8);
		escaper.setOrigin(Globals.EXPLORER_SIZE + 4, Globals.EXPLORER_SIZE + 4);
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
			if(board.finishPoint != null){
				drawFinish(board.finishPoint);
			}
			System.out.println("GameDrawer - drawSpinner-s");
			for (Spinner spinner : board.spinners) {
				drawSpinner(spinner);
			}
			if(board.ports != null) {
				for (Teleporter t : board.ports) {
					drawTeleporter(t);
				}
			}
			if(board.escaper != null){
				drawEscaper( board.escaper);		
			}
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
			unwalkable_tile.setPosition(pos.x, pos.y);
			unwalkable_tile.draw(batch);
		}
		if(t.type == TileType.WALKABLE){
			Vector2 pos = t.pos.getGamePos();
			walkable_tile.setPosition(pos.x, pos.y);
			walkable_tile.draw(batch);
		}
	}
	
	private static ShapeRenderer sr = new ShapeRenderer();
	
	public void drawEscaper( Escaper e ){
		batch.end();
		sr.setProjectionMatrix(BoardScreen.cam.combined);
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
		//spinner.setScale(0.4f);
		float offset = - spinner.getHeight()/2;
		spinner.setRotation( s.facing );
		spinner.setPosition( s.pos.x + offset,s.pos.y + offset );
		batch.end();
			sr.setProjectionMatrix(BoardScreen.cam.combined);
			sr.begin(ShapeType.Line);
			sr.setColor(1,0,0,0.5f);
			sr.circle(s.pos.x, s.pos.y, s.getSize());
		sr.end();
		batch.begin();
		spinner.draw(batch);		
	}
	
	private void drawFinish(FinishPoint f) {
		System.out.println("GameDrawer - drawfinishPoint");
		Vector2 pos = new Vector2(f.pos.x - Globals.TILE_SIZE/2, f.pos.y - Globals.TILE_SIZE/2);
		finishpaint.setPosition(pos.x,pos.y);
		finishpaint.draw(batch);
	}
	
	private void drawTeleporter(Teleporter t) {
		System.out.println("GameDrawer - drawPort");
		Vector2 pos = new Vector2(t.pos.x - Globals.TILE_SIZE/2, t.pos.y - Globals.TILE_SIZE/2);
		teleporter_sprite.setPosition(pos.x,pos.y);
		teleporter_sprite.draw(batch);
	}

}
