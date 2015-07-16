package gametest;

import game.Globals;
import game.Index;
import game.entities.Escaper;
import game.entities.Spinner;
import junit.framework.Assert;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

@SuppressWarnings("deprecation")
public class TombEscapeGameTest {

	@Test
	public void testGamePos() {
	Index index = new Index(3, 2);
	Vector2 position = index.getGamePos();
	float result = position.x;
	Assert.assertEquals(120f, result, 0);
	}
	
	@Test
	public void testSpinnergetSize() {
	Spinner s = new Spinner(new Vector2(0f,0f), new Vector2(10f,10f));
	float result = s.getSize();
	Assert.assertEquals(20f, result, 0);
	}
	
	@Test
	public void testgetIndex() {
		Vector2 v = new Vector2(230.56f,129.03f);
		int result = Globals.getIndex(v).getX();
		Assert.assertEquals(5, result, 0);
		}
	
	@Test
	public void testEscaperFacing(){
		Escaper e = new Escaper(new Vector2(0f,0f));
		double result = e.getFacing();
		Assert.assertEquals(0., result);
	}
	
	@Test
	public void vectorTest(){
		Vector2 v = new Vector2(20.5f, 30.1f);
		v.add(new Vector2(19.5f, 10.1f));
		Assert.assertEquals(40f, v.x);
	}
}
