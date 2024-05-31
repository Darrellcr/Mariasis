package com.mygdx.mariasis;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mariasis.Screens.MainMenuScreen;


public class Mariasis extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public static final int width = 800;
	public static final int height = 480;
	public static final float PPM = 100;


	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		this.setScreen(new MainMenuScreen(this));

	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
