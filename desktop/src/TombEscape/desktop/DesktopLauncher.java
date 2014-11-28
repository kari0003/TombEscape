package game.desktop;

import main.TombEscapeGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tomb Escape";
	    config.width = 640;
	    config.height = 640;
	    config.resizable = false;
		new LwjglApplication(new TombEscapeGame(), config);
	}
}
