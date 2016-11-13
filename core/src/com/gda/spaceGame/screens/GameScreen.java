package com.gda.spaceGame.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gda.spaceGame.GUI.Button;
import com.gda.spaceGame.GUI.MenuShip;
import com.gda.spaceGame.SpaceMain;
import com.gda.spaceGame.controllers.GameState;
import com.gda.spaceGame.controllers.PauseMenuController;
import com.gda.spaceGame.controllers.PlayerController;
import com.gda.spaceGame.entities.Bullet;
import com.gda.spaceGame.entities.Player;

import static com.gda.spaceGame.SpaceMain.SCALE;
import static com.gda.spaceGame.SpaceMain.gameState;
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

    private Texture texture;
    private int scrX = 0, scrY = 0;

    public GameScreen(SpaceMain gam, MenuShip currentShip) {
        game = gam;
        batch = new SpriteBatch();
        guiBatch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        stage = new Stage(viewport);

        player = new Player(currentShip);
        stage.addActor(player);

        playerController = new PlayerController(player);
        pauseMenuController = new PauseMenuController(Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth()/2, game);
        for (Actor button : pauseMenuController.getButtons()) {
            button.setVisible(false);
        }

        texture = new Texture(Gdx.files.internal("background.gif"));
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);


        input = new InputMultiplexer();
        input.addProcessor(pauseMenuController);
        input.addProcessor(stage);
        input.addProcessor(this);

        Gdx.input.setInputProcessor(input);
        Gdx.input.setCatchBackKey(true);

        gameState = RUN;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        guiBatch.begin();
        guiBatch.draw(texture, 0, 0, scrX, scrY,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiBatch.end();
        stage.draw();

        for (int j = 0; j < stage.getActors().size-1; j++) {
            for (int i = j; i < stage.getActors().size - 1; i++) {
                if (stage.getActors().get(j).getZIndex() > stage.getActors().get(j + 1).getZIndex()) {
                    stage.getActors().swap(j, j + 1);
                }
            }
        }

        //Begin of and acting part
        //___________________________
        if (gameState == RUN) {
            pauseMenuController.setVisible(false);
            pauseMenuController.getPauseButton().setVisible(true);

            scrX += player.getSpeed() * MathUtils.cosDeg(player.getAngle());
            scrY -= player.getSpeed() * MathUtils.sinDeg(player.getAngle());

            camera.update();
            cameraMove();
            stage.act();

            playerController.act(guiBatch, 0.5f);
        }
        else if (gameState == PAUSE) {
            pauseMenuController.setVisible(true);
        }

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

    }

    //Handle touch input


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }
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
