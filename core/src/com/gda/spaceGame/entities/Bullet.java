package com.gda.spaceGame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gda.spaceGame.controllers.GameState;

import static com.gda.spaceGame.SpaceMain.SCALE;
import static com.gda.spaceGame.SpaceMain.gameState;

/**
 * Created by smith on 11/2/16.
 */
public class Bullet extends Actor{

    private final Sprite sprite;
    private final float speed;
    private float angle;
    private float lifetime = 5;
    private final int z = 0;
    private Circle bounds;

    public Bullet(Texture texture, float speed, float x, float y, float angle) {
        this.sprite = new Sprite(texture);
        this.sprite.rotate90(true);
        this.sprite.setScale(1/SCALE);

        this.speed = speed;
        this.angle = angle;
        setPosition(x, y);

        bounds = new Circle(x, y, sprite.getWidth()/2/SCALE);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (gameState != GameState.PAUSE) {
            if (lifetime <= 0) this.remove();

            moveBy(speed * MathUtils.cosDeg(angle), speed * MathUtils.sinDeg(angle));
            lifetime -= Gdx.graphics.getDeltaTime();
        }

        sprite.setRotation(angle);
        sprite.setPosition(getX() - sprite.getWidth()/2, getY() - sprite.getHeight()/2);
        sprite.draw(batch);

        bounds.setPosition(getX(), getY());
    }

    @Override
    public int getZIndex() {
        return z;
    }

    public Circle getBounds() {
        return bounds;
    }
}
