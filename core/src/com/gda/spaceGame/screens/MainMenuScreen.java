package com.gda.spaceGame.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gda.spaceGame.GUI.Button;
import com.gda.spaceGame.GUI.MenuShip;
import com.gda.spaceGame.SpaceMain;
import com.gda.spaceGame.controllers.GameState;
import com.gda.spaceGame.controllers.ShipChooseController;
import com.gda.spaceGame.utilities.parallax.ParallaxBackground;
import com.gda.spaceGame.utilities.parallax.ParallaxLayer;

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
    private Viewport viewport;

    private BitmapFont labelFont, gameDataFont;

    private ShipChooseController shipChooseController;

    public static MenuShip currentShip;

    private Texture background;
    private int srcY = 0;

    private ParallaxBackground bck;

    private Preferences gameData;

    //Game data variables
    private int money, highscore;

    public MainMenuScreen(SpaceMain gam) {
        game = gam;
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
//        viewport.apply();

        stage = new Stage(viewport, batch);

        shipChooseController = new ShipChooseController(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(shipChooseController);
        stage.addActor(shipChooseController.getPrevShip());
        stage.addActor(shipChooseController.getNextShip());

        background = new Texture(Gdx.files.internal("background.gif"));
//        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        bck = new ParallaxBackground(new ParallaxLayer[]{
                new ParallaxLayer(new TextureRegion(background), new Vector2(0, 5), new Vector2(0, 0))
        }, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Vector2(0, 100));

        input = new InputMultiplexer();
        input.addProcessor(stage);
        input.addProcessor(this);

        Gdx.input.setInputProcessor(input);

        generateFont();

        gameState = GameState.MAINMENU;

        gameData = Gdx.app.getPreferences("GameData");
        loadGameData();
    }

    private void loadGameData() {
        if (gameData.contains("money")) money = gameData.getInteger("money");
        else {
            gameData.putInteger("money", 0);
            money = 0;
        }

        if (gameData.contains("highscore")) highscore = gameData.getInteger("highscore");
        else {
            gameData.putInteger("highscore", 0);
            highscore = 0;
        }

        gameData.flush();
    }

    private void drawGUI() {
        batch.begin();

        gameDataFont.draw(batch, "" + money, 90/SCALE, Gdx.graphics.getHeight() - 20/SCALE, 0, Align.left, false);
        gameDataFont.draw(batch, "" + highscore, 90/SCALE, Gdx.graphics.getHeight() - 90 / SCALE, 0, Align.left, false);

        batch.end();
    }

    private void generateFont() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/m12.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = (int) (72 / SCALE);
        labelFont = gen.generateFont(parameter);

        parameter.size = (int) (36 / SCALE);
        gameDataFont = gen.generateFont(parameter);

        gen.dispose();
    }

    private void addButton() {

    }

    @Override
    public void show() {
        stage.addActor(new Button(new Texture(Gdx.files.internal("StartE.png")), 4, /*new Texture(Gdx.files.internal("Empty.png")),*/ Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 7 / 8) {
            private float alphaColor = 1;
            private float alphaChange = 0.015f;

            @Override
            public void act() {
                currentShip = shipChooseController.getCurrentShip();
                game.setScreen(new GameScreen(game, currentShip));
            }

            @Override
            public void draw(Batch batch, float alpha) {
                getSprite().setPosition(getX(), getY());
                getSprite().draw(batch);
                if (alphaColor >= 1 || alphaColor <= 0.3f) alphaChange *= -1;
                alphaColor += alphaChange;
                getSprite().setAlpha(alphaColor);
            }
        });

        stage.addActor(new Button(new Texture(Gdx.files.internal("gui/exit.png")), 0.3f, Gdx.graphics.getWidth() - 72 / SCALE, Gdx.graphics.getHeight() - 72 / SCALE) {
            @Override
            public void act() {
                System.exit(0);
            }
        });
        //Isn't button
        stage.addActor(new Button(new Texture(Gdx.files.internal("gui/money.png")), 0.15f, 36/SCALE, Gdx.graphics.getHeight() - 36/SCALE) {
            @Override
            public void act() {
            }
        });
        stage.addActor(new Button(new Texture(Gdx.files.internal("gui/timer.png")), 0.15f, 36/SCALE, Gdx.graphics.getHeight() - 108/SCALE) {
            @Override
            public void act() {
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bck.render(delta);

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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
