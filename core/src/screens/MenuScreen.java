package screens;

import main.TombEscapeGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen implements Screen {

	private Stage stage;
	private Table table;
	
	private Label tombescape;
	private TextButton play;
	private TextButton edit;
	private TextButton quit;
	
	public MenuScreen(){
		stage = new Stage();
		
		Skin ui =  new Skin(Gdx.files.internal("uiskin.json"));
		tombescape = new Label("Tomb Escape! - the game", ui);
		play = new TextButton("Play!",ui);
		edit = new TextButton("Editor",ui);
		quit = new TextButton("Quit :(",ui);
		
		play.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
				TombEscapeGame.getGame().startGame();
				return false;
			}
		});
		
		edit.addListener(new ChangeListener(){
			
			@Override
			public void changed(ChangeEvent event, Actor actor){
				TombEscapeGame.getGame().startEditor();
			}
		});
		
		quit.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//Quit!!
				System.out.println("QuIT");
		    };
		});
		
		table = new Table();
	    table.setFillParent(true);
	    table.add(tombescape).height(200f).row();
		table.add(play).height(30f);
		table.row().height(30f).row();
		table.add(edit).height(30f);
		table.row().height(30f).row();
		table.add(quit).height(30f);
		
		
		stage.addActor(table);
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT );
		// TODO Auto-generated method stub
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
		stage.draw();
		
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

}
