package com.gda.spaceGame.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static com.gda.spaceGame.SpaceMain.SCALE;

/**
 * Created by smith on 15.10.16.
 */
public abstract class Button extends Actor {

    private Sprite sprite;
    private CharSequence value;

    private boolean touched = false;

    public Button(Texture texture, CharSequence value, float x, float y) {
        this.sprite = new Sprite(texture);
        this.value = value;

        this.sprite.setOrigin(0, 0);
        this.sprite.setScale(1/SCALE);

        setBounds(x - sprite.getWidth()/2/SCALE, y - sprite.getHeight()/2/SCALE, sprite.getWidth()/SCALE, sprite.getHeight()/SCALE);

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                act();
                System.out.println("touched button");
            }
        });

    }

    @Override
    public void draw(Batch batch, float alpha) {

        sprite.setPosition(getX(), getY());
        sprite.draw(batch);
    }

    private boolean isTouched() {
        if (Gdx.input.justTouched()) {
            if (sprite.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                return true;
            }
        }
        return false;
    }



    public abstract void act();

    public Sprite getSprite() {
        return sprite;
    }
}
