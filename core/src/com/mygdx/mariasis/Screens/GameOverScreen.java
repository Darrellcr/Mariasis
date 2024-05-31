package com.mygdx.mariasis.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.mariasis.Mariasis;

public class GameOverScreen implements Screen{
    final Mariasis game;
    private BitmapFont gameOverFont;
    private BitmapFont playAgain;

    OrthographicCamera camera;

    public GameOverScreen(final Mariasis game) {
        this.game = game;

        gameOverFont = new BitmapFont();
        gameOverFont.getData().setScale(3);
        playAgain = new BitmapFont();
        playAgain.getData().setScale(2);


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        gameOverFont.draw(game.batch, "Game Over",290, 360);
        playAgain.draw(game.batch, "Press space to play again",240 , 230 );
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

