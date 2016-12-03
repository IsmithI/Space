package com.gda.spaceGame.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.gda.spaceGame.ShootType;

import static com.gda.spaceGame.SpaceMain.SCALE;

/**
 * Created by smith on 15.10.16.
 */
public class MenuShip extends Actor {

    private Sprite sprite;

    private float speed, turn, health;

    private BitmapFont nameFont;
    private CharSequence name;

    private Sprite shealth = new Sprite(new Texture(Gdx.files.internal("gui/heart.png")));
    private Sprite sspeed = new Sprite(new Texture(Gdx.files.internal("gui/speed.png")));
    private Sprite sturn = new Sprite(new Texture(Gdx.files.internal("gui/rotate.png")));
    private Sprite[] healthMas = new Sprite[10], speedMas = new Sprite[10], turnMas = new Sprite[10];

    public MenuShip(Texture texture, float speed, float turn, float health, CharSequence name) {
        this.sprite = new Sprite(texture);
        this.speed = speed;
        this.turn = turn;
        this.health = health;
        this.name = name;

        this.sprite.setOrigin(0, 0);
        this.sprite.setScale(1 / SCALE);

        shipName();
        generalWindow();
        generalChar();
    }

    private void generalChar() {
        addChar(shealth, 2);
        addChar(sspeed,38);
        addChar(sturn, 74);
    }

    private void addChar(Sprite sprite, float height) {
        sprite.setOrigin(0, 0);
        sprite.setScale(0.15f/SCALE);
        sprite.setPosition(Gdx.graphics.getWidth() / 4 - sprite.getWidth() * 0.2f/SCALE, Gdx.graphics.getHeight() / 4 - height*2.25f/SCALE);
    }

    private void shipName() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/m12.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = (int) (64 / SCALE);
        nameFont = gen.generateFont(parameter);

        gen.dispose();
    }

    private void generalWindow() {
        addMas(healthMas, health, 0);
        addMas(speedMas, speed, 36);
        addMas(turnMas, turn, 72);
    }

    private void addMas(Sprite[] mas, float character, float height) {
        for (int i = 0; i < 10; i++) {
            if (i < character)
                mas[i] = new Sprite(new Texture(Gdx.files.internal("gui/lvl.png")));
            else
                mas[i] = new Sprite(new Texture(Gdx.files.internal("gui/lvlEmpty.png")));
            mas[i].setOrigin(0, 0);
            mas[i].setScale(2.25f/SCALE);
            mas[i].setPosition(Gdx.graphics.getWidth() / 4 + i * mas[i].getWidth()*2.5f/SCALE, Gdx.graphics.getHeight() / 4 - height*2.25f/SCALE);
        }
    }

    private void drawGeneralChar(Batch batch) {
        shealth.draw(batch);
        sspeed.draw(batch);
        sturn.draw(batch);
    }

    public void drawInfo(Batch batch) {
        drawMas(batch, healthMas);
        drawMas(batch, speedMas);
        drawMas(batch, turnMas);
        nameFont.draw(batch, name, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 3 * 2, 0, Align.center, false);
    }

    private void drawMas(Batch batch, Sprite[] mas) {
        for (int i = 0; i < 10; i++)
            mas[i].draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getSpeed() {
        return speed;
    }

    public float getTurn() {
        return turn;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX() - sprite.getWidth() / 2 / SCALE, getY() - sprite.getHeight() / 2 / SCALE);
        sprite.draw(batch, parentAlpha);
        drawGeneralChar(batch);
    }
}
