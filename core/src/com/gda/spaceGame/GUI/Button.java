package com.gda.spaceGame.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import static com.gda.spaceGame.SpaceMain.SCALE;

/**
 * Created by smith on 15.10.16.
 */
public abstract class Button extends Actor {

    private Sprite sprite;
    private CharSequence value;
    private BitmapFont font;

    private Texture touchDown;
    private Texture touchUp;
    private boolean checkTouch;

    private boolean touched = false;
    private int size;

    public Button(/*Texture touchUp, Texture touchDown,*/Texture texture, float scale, float x, float y) {
        this.touchUp = touchUp;
        this.touchDown = touchDown;

        sprite = new Sprite(/*touchUp*/texture);

        sprite.setOrigin(0, 0);
        sprite.setScale(scale);

        setBounds(x - sprite.getWidth() / 2 * scale, y - sprite.getHeight() / 2 * scale, sprite.getWidth() * scale, sprite.getHeight() * scale);

        myListener();
    }

    public Button(CharSequence value, float x, float y, float width, float height, BitmapFont font) {
        this.value = value;
        this.font = font;
        font.setColor(Color.WHITE);
        setBounds(x, y, width, height);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                act();
            }
        });
    }

    public Button(/*Texture touchUp,Texture touchDown*/Texture texture, float x, float y) {
//        this.touchUp = touchUp;
//        this.touchDown = touchDown;

        sprite = new Sprite(texture/*touchUp*/);

        this.sprite.setOrigin(0, 0);
        sprite.setScale(1 / SCALE);

        setBounds(x - sprite.getWidth() / 2 / SCALE, y - sprite.getHeight() / 2 / SCALE, sprite.getWidth() / SCALE, sprite.getHeight() / SCALE);

        myListener();
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (sprite != null) {
            sprite.setPosition(getX(), getY());
            sprite.draw(batch);
        }
        if (font != null) {
            font.draw(batch, value, getX(), getY(), 0, Align.center, false);
            System.out.println(getX() + "--" + getY());
        }
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

    private void myListener() {
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                checkTouch = true;
//                sprite.setTexture(touchDown);
//                if (setting.getVibration())
//                    Gdx.input.vibrate(50);
                return true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                checkTouch = false;
//                sprite.setTexture(touchUp);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (checkTouch) {
                    act();
                }
            }

        });
    }
}
