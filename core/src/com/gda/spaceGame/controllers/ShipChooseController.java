package com.gda.spaceGame.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.gda.spaceGame.GUI.Button;
import com.gda.spaceGame.GUI.MenuShip;
import com.gda.spaceGame.ShootType;

import java.util.ArrayList;

import static com.gda.spaceGame.SpaceMain.SCALE;

/**
 * Created by smith on 15.10.16.
 */
public class ShipChooseController extends Actor{

    private ArrayList<MenuShip> ships;
    private MenuShip currentShip;
    private int i = 0;
    private Button nextShip, prevShip;
    private BitmapFont font;

    public ShipChooseController(float x, float y) {
        setPosition(x, y);

        generateFont();

        ships = new ArrayList<MenuShip>();

        ships.add(new MenuShip(new Texture(Gdx.files.internal("ships/ship1.png")), 8, 5, ShootType.SINGLE));
        ships.add(new MenuShip(new Texture(Gdx.files.internal("ships/ship2.png")), 10, 5, ShootType.SINGLE));
        ships.add(new MenuShip(new Texture(Gdx.files.internal("ships/ship3.png")), 10, 8, ShootType.SINGLE));
//        ships.add(new MenuShip(new Texture(Gdx.files.internal("ships/ship3.png")), 15, 8, ShootType.DOUBLE));

        nextShip = new Button(new Texture(Gdx.files.internal("gui/buttons/arrow.png")), "",
                Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/2,
                font) {
            @Override
            public void act() {
                nextShip();
            }
        };
        prevShip = new Button(new Texture(Gdx.files.internal("gui/buttons/arrow.png")), "",
                Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/2,
                font) {
            @Override
            public void act() {
                prevShip();
            }
        };

        nextShip.getSprite().flip(true, false);

        currentShip = ships.get(0);
    }

    private void generateFont() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/m12.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (32/SCALE);
        font = gen.generateFont(parameter);
    }

    private void prevShip() {
        if (i > 0) {
            addAction(Actions.moveBy(Gdx.graphics.getWidth() / 4, 0, 0.5f));
            currentShip = ships.get(--i);
        }
    }

    private void nextShip() {
        if (i < ships.size()-1) {
            addAction(Actions.moveBy(-Gdx.graphics.getWidth() / 4, 0, 0.5f));
            currentShip = ships.get(++i);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateShipPosition();

        nextShip.draw(batch, parentAlpha);
        prevShip.draw(batch, parentAlpha);

        for (MenuShip ship : ships) {
            ship.draw(batch, parentAlpha);
        }

        currentShip.drawInfo(batch);
    }

    private void updateShipPosition() {
        int i = 0;
        for (MenuShip ship : ships) {
            ship.setPosition(getX() + (Gdx.graphics.getWidth()/4)*i, getY());
            i++;
        }
    }

    public MenuShip getCurrentShip() {
        return currentShip;
    }

    public Button getNextShip() {
        return nextShip;
    }

    public Button getPrevShip() {
        return prevShip;
    }
}
