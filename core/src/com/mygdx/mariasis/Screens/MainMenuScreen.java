package com.mygdx.mariasis.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.mariasis.Mariasis;

public class MainMenuScreen implements Screen{
    final Mariasis game;
    private Texture background;
    private Texture button;
    private BitmapFont gameStartFont;

    OrthographicCamera camera;

    public MainMenuScreen(final Mariasis game) {
        this.game = game;
        background = new Texture("BG.png");
        button = new Texture("Button.png");

        gameStartFont = new BitmapFont();
        gameStartFont.getData().setScale(2);


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background,0,0,800,480);
        game.batch.draw(button,(800/2) - (button.getWidth()/2), (480/2) - (button.getWidth()/2));
        gameStartFont.draw(game.batch, "Press Space to Start",(800/2) - (button.getWidth()/2) - 20, (480/2) - (button.getWidth())/2);
        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}


