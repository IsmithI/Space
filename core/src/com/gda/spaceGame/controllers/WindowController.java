package com.gda.spaceGame.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.gda.spaceGame.SpaceMain.SCALE;

/**
 * Created by ANTON on 01.12.2016.
 */
public class WindowController extends Actor{
    private Sprite sprite;
    private Texture passive = new Texture(Gdx.files.internal("gui/window.png"));
    private Texture active = new Texture(Gdx.files.internal("gui/windowAct.png"));

    public WindowController(float x, float y){
        sprite = new Sprite(passive);
        sprite.setOrigin(0, 0);
        sprite.setScale(1/SCALE);
        sprite.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }
}
