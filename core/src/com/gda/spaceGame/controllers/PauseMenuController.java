package com.gda.spaceGame.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.gda.spaceGame.GUI.Button;
import com.gda.spaceGame.SpaceMain;
import com.gda.spaceGame.screens.GameScreen;
import com.gda.spaceGame.screens.MainMenuScreen;

import java.util.HashSet;
import java.util.Set;

import static com.gda.spaceGame.SpaceMain.SCALE;
import static com.gda.spaceGame.SpaceMain.gameState;
import static com.gda.spaceGame.controllers.GameState.*;
import static com.gda.spaceGame.screens.GameScreen.gameData;
import static com.gda.spaceGame.screens.MainMenuScreen.currentShip;

/**
 * Created by Smith on 28.10.2016.
 */
public class PauseMenuController implements InputProcessor {

    private Texture background;
    private Button restart, toMenu, resume, pause;
    private Vector2 basePosition;
    private final GameScreen screen;

    private Set<Actor> buttons;

    public PauseMenuController(float baseX, float baseY, final SpaceMain game, final Screen screen1) {
        background = new Texture(Gdx.files.internal("gui/pauseBack.png"));
        basePosition = new Vector2(baseX - background.getWidth()/2/SCALE, baseY - background.getHeight()/2/SCALE);
        screen = (GameScreen) screen1;

        restart = new Button(new Texture(Gdx.files.internal("gui/restart.png")),
                basePosition.x + background.getWidth()/4/SCALE, basePosition.y + background.getHeight()*2/3/SCALE) {
            @Override
            public void act() {
                if (isVisible()) {
                    if (gameState == FINISH)
                        screen.saveData();
                    GameScreen.score = 0;
                    GameScreen.money = 0;
                    gameState = RUN;
                    game.setScreen(new GameScreen(game, currentShip));
                }
            }
        };

        toMenu = new Button(new Texture(Gdx.files.internal("gui/toMenu.png")),
                basePosition.x + background.getWidth()*3/4/SCALE, basePosition.y + background.getHeight()*2/3/SCALE) {
            @Override
            public void act() {
                if (isVisible()) {
                    if (gameState == FINISH)
                        screen.saveData();
                    gameState = MAINMENU;
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        };

        resume = new Button(new Texture(Gdx.files.internal("gui/resume.png")),
                basePosition.x + background.getWidth()/2/SCALE, basePosition.y + background.getHeight()/3/SCALE) {
            @Override
            public void act() {
                if (isVisible())
                    gameState = RUN;
            }
        };
        pause = new Button(new Texture(Gdx.files.internal("gui/pause.png")),
                Gdx.graphics.getWidth() - 128/2/SCALE - 8, Gdx.graphics.getHeight() - 128/2/SCALE - 8) {
            @Override
            public void act() {
                if (gameState != FINISH) {
                    if (gameState == RUN) {
                        gameState = PAUSE;
                    } else if (gameState == PAUSE) {
                        gameState = RUN;
                    }
                }
                else {
                    setVisible(false);
                }
            }
        };

        buttons = new HashSet<Actor>();
        buttons.add(restart);
        buttons.add(toMenu);
        buttons.add(resume);
        setVisible(false);

        for (Actor button : buttons) {
            button.setTouchable(Touchable.enabled);
        }

        buttons.add(pause);
    }

    public void showFinishMenu() {
        restart.setVisible(true);
        toMenu.setVisible(true);
        resume.setVisible(false);
        pause.setVisible(false);
    }

    public void draw(Batch batch) {
        if (restart.isVisible()) {
            batch.draw(background, basePosition.x, basePosition.y, background.getWidth()/SCALE, background.getHeight()/SCALE);
        }
        for (Actor button : buttons) {
            if (button.isVisible())
                button.draw(batch, 1f);
        }
    }

    public void setVisible(boolean visible) {
        for (Actor button : buttons) {
            button.setVisible(visible);
        }
    }

    public Set<Actor> getButtons() {
        return buttons;
    }

    public Button getPauseButton() {
        return pause;
    }

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
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (Actor actor : buttons) {
            buttonAct((Button) actor, screenX, screenY);
        }

        return true;
    }

    private void buttonAct(Button button, int screenX, int screenY) {
        if (button.getSprite().getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)) {
            button.act();
        }
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
