package com.gda.spaceGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gda.spaceGame.GUI.MenuShip;
import com.gda.spaceGame.SpaceMain;
import com.gda.spaceGame.controllers.GameState;
import com.gda.spaceGame.controllers.ShipChooseController;

import static com.gda.spaceGame.SpaceMain.SCALE;
import static com.gda.spaceGame.SpaceMain.gameState;

/**
 * Created by smith on 15.10.16.
 */


public class MainMenuScreen implements Screen, InputProcessor {

    private InputMultiplexer input;

    private final SpaceMain game;
    private Stage stage;

    private Batch batch;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private BitmapFont font;

    private ShipChooseController shipChooseController;

    public static MenuShip currentShip;

    private Texture background;
    private int srcY = 0;

    public MainMenuScreen(SpaceMain gam) {
        game = gam;
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        stage = new Stage(viewport, batch);

        shipChooseController = new ShipChooseController(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        stage.addActor(shipChooseController);
        stage.addActor(shipChooseController.getPrevShip());
        stage.addActor(shipChooseController.getNextShip());

        background = new Texture(Gdx.files.internal("background.gif"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        input = new InputMultiplexer();
        input.addProcessor(stage);
        input.addProcessor(this);

        Gdx.input.setInputProcessor(input);

        generateFont((int) (72 / SCALE));

        gameState = GameState.MAINMENU;

    }

    private void drawGUI() {
        batch.begin();

        font.draw(batch, "Tap to start", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()*7/8, 0, Align.center, false);

        batch.end();
    }

    private void generateFont(int size) {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/m12.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = size;
        font = gen.generateFont(parameter);

        gen.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, 0, srcY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        srcY -= 5;

        camera.update();

        stage.act(delta);
        stage.draw();

        drawGUI();
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
        batch.dispose();
        stage.dispose();
    }


    //Handle touch input from screen

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchdown");
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchup");
        currentShip = shipChooseController.getCurrentShip();
        game.setScreen(new GameScreen(game, currentShip));
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
