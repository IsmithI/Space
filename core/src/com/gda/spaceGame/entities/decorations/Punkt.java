package com.gda.spaceGame.entities.decorations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.gda.spaceGame.SpaceMain.SCALE;

/**
 * Created by Smith on 20.11.2016.
 */
public class Punkt extends Actor {

    private Sprite sprite;
    private int z;
    private float alpha = 1;

    public Punkt(Texture texture, float x, float y, float angle) {
        sprite = new Sprite(texture);
        sprite.setScale(1/SCALE);
        sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
        sprite.setRotation(angle + 90);
        z = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (alpha <= 0) {
            this.remove();
            alpha = 0;
        }

        sprite.draw(batch, alpha);
        alpha -= Gdx.graphics.getDeltaTime()/2;
    }

    @Override
    public int getZIndex() {
        return z;
    }

    @Override
    public void setZIndex(int index) {
        z = index;
    }
}
