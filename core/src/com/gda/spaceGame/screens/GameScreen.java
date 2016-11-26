package com.gda.spaceGame.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gda.spaceGame.GUI.MenuShip;
import com.gda.spaceGame.SpaceMain;
import com.gda.spaceGame.controllers.*;
import com.gda.spaceGame.entities.Player;
import com.gda.spaceGame.utilities.parallax.ParallaxBackground;
import com.gda.spaceGame.utilities.parallax.ParallaxLayer;

import java.util.Comparator;

import static com.gda.spaceGame.SpaceMain.SCALE;
import static com.gda.spaceGame.SpaceMain.gameState;
import static com.gda.spaceGame.controllers.GameState.FINISH;
import static com.gda.spaceGame.controllers.GameState.PAUSE;
import static com.gda.spaceGame.controllers.GameState.RUN;

/**
 * Created by smith on 15.10.16.
 */
public class GameScreen implements Screen, InputProcessor {

    private final SpaceMain game;
    private Stage stage;
    private Batch batch, guiBatch;

    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private InputMultiplexer input;

    private Player player;
    private PlayerController playerController;
    private PauseMenuController pauseMenuController;
    private EnemyController enemyController;

    private Texture background;
    private int srcX, srcY;

    private BitmapFont font;

    public static Preferences gameData = Gdx.app.getPreferences("GameData");
    public static int money, score;
    private float scoreTimer = 1, finishTimer = 1.5f;

    public GameScreen(SpaceMain gam, MenuShip currentShip) {
        game = gam;
        batch = new SpriteBatch();
        guiBatch = new SpriteBatch();

        money = 0;
        score = 0;

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        stage = new Stage(viewport);

        player = new Player(currentShip);
        stage.addActor(player);

        playerController = new PlayerController(player);
        pauseMenuController = new PauseMenuController(Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth()/2, game, this);
        for (Actor button : pauseMenuController.getButtons()) {
            button.setVisible(false);
        }
        enemyController = new EnemyController(playerController, stage);
        enemyController.addEnemy(enemyController.enemyLevel0());

        background = new Texture(Gdx.files.internal("background.gif"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        input = new InputMultiplexer();
        input.addProcessor(pauseMenuController);
        input.addProcessor(stage);
        input.addProcessor(this);

        Gdx.input.setInputProcessor(input);
        Gdx.input.setCatchBackKey(true);

        generateFont();

        gameState = RUN;

        srcX = 0;
        srcY = 0;
    }

    private void generateFont() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/m12.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (36/SCALE);

        font = gen.generateFont(parameter);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        guiBatch.begin();
        guiBatch.draw(background, 0, 0, srcX, srcY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiBatch.end();

        guiBatch.begin();
        font.draw(guiBatch, money + "$", 32/SCALE, Gdx.graphics.getHeight() - 32/SCALE, 0, Align.left, false);
        font.draw(guiBatch, "Time: " + score, 32/SCALE, Gdx.graphics.getHeight() - 68/SCALE, 0, Align.left, false);
        guiBatch.end();

        //Begin of and acting part
        //___________________________
        if (gameState == RUN) {
            pauseMenuController.setVisible(false);
            pauseMenuController.getPauseButton().setVisible(true);

            camera.update();
            cameraMove();
            stage.act();

            srcX += player.getSpeed() * MathUtils.cosDeg(player.getAngle());
            srcY -= player.getSpeed() * MathUtils.sinDeg(player.getAngle());

            playerController.act(guiBatch, 0.5f);
            enemyController.updatePlayerPosition(player.getX(), player.getY());
            enemyController.act();

            if (scoreTimer <= 0) {
                score++;
                scoreTimer = 1;
            }
            scoreTimer -= Gdx.graphics.getDeltaTime();
        }
        else if (gameState == PAUSE) {
            pauseMenuController.setVisible(true);
        }
        else if (gameState == FINISH) {
            if (finishTimer > 0) {
                player.setVisible(false);
                finishTimer -= Gdx.graphics.getDeltaTime();
                stage.act();
                enemyController.updatePlayerPosition(player.getX(), player.getY());
                enemyController.act();
            }
            else pauseMenuController.showFinishMenu();
        }

        stage.getActors().sort(new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                return Integer.compare(o1.getZIndex(), o2.getZIndex());
            }
        });
        stage.draw();
        System.out.println(stage.getActors());

        guiBatch.begin();
        pauseMenuController.draw(guiBatch);
        guiBatch.end();
        //_______________________
        //end of an acting part
    }

    private void cameraMove() {
        camera.position.x = player.getX();
        camera.position.y = player.getY();
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
        background.dispose();
        batch.dispose();
        guiBatch.dispose();
        stage.dispose();
        font.dispose();
    }

    public void saveData() {
        gameData.putInteger("money", gameData.getInteger("money") + money);
        if (score > gameData.getInteger("highscore")) {
            gameData.putInteger("highscore", score);
        }
        System.out.println("Game is saved");
        gameData.flush();
    }

    //Handle touch input


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
            toMainMenu();
        }
        return false;
    }

    private void toMainMenu() {
        dispose();
        saveData();
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.UP) player.setSpeed(player.getSpeed() + 1);
        if (keycode == Input.Keys.DOWN) player.setSpeed(player.getSpeed() - 1);
        return true;
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
        return false;
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
