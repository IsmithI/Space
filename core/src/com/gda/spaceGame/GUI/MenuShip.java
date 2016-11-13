package com.gda.spaceGame.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gda.spaceGame.ShootType;

import static com.gda.spaceGame.SpaceMain.SCALE;

/**
 * Created by smith on 15.10.16.
 */
public class MenuShip extends Actor {

    private Sprite sprite;

    private float speed, turn;
    private ShootType shootType;

    private Sprite speedIcon, turnIcon, shootIcon;

    public MenuShip(Texture texture, float speed, float turn, ShootType shootType) {
        this.sprite = new Sprite(texture);
        this.speed = speed;
        this.turn = turn;
        this.shootType = shootType;

        this.sprite.setOrigin(0, 0);
        this.sprite.setScale(1/SCALE);

        createBuffIcons();
    }

    private void createBuffIcons() {
        //Speed
        if (speed <= 5) {
            speedIcon = new Sprite(new Texture(Gdx.files.internal("gui/shipStats/speed1.png")));
        }
        else if (speed <= 8) {
            speedIcon = new Sprite(new Texture(Gdx.files.internal("gui/shipStats/speed2.png")));
        }
        else if (speed <= 12) {
            speedIcon = new Sprite(new Texture(Gdx.files.internal("gui/shipStats/speed3.png")));
        }
        speedIcon.setOrigin(0, 0);
        speedIcon.setScale(1/SCALE);
        speedIcon.setPosition(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/6 - speedIcon.getHeight()/SCALE);

        //Turn angle
        if (turn <= 5) {
            turnIcon = new Sprite(new Texture(Gdx.files.internal("gui/shipStats/turn1.png")));
        }
        else if (turn <= 8) {
            turnIcon = new Sprite(new Texture(Gdx.files.internal("gui/shipStats/turn2.png")));
        }
        turnIcon.setOrigin(0, 0);
        turnIcon.setScale(1/SCALE);
        turnIcon.setPosition(Gdx.graphics.getWidth()/3 + turnIcon.getWidth()/SCALE, Gdx.graphics.getHeight()/6 - turnIcon.getHeight()/SCALE);

        //Shoot type
        if (shootType == ShootType.SINGLE) {
            shootIcon = new Sprite(new Texture(Gdx.files.internal("gui/shipStats/shoot1.png")));
        }
        else if (shootType == ShootType.DOUBLE) {
            shootIcon = new Sprite(new Texture(Gdx.files.internal("gui/shipStats/shoot2.png")));
        }
        else if (shootType == ShootType.TRIPLE) {
            shootIcon = new Sprite(new Texture(Gdx.files.internal("gui/shipStats/shoot3.png")));
        }
        shootIcon.setOrigin(0, 0);
        shootIcon.setScale(1/SCALE);
        shootIcon.setPosition(Gdx.graphics.getWidth()/3 + shootIcon.getWidth()/2/SCALE, Gdx.graphics.getHeight()/6);
    }

    public void drawInfo(Batch batch) {
        speedIcon.draw(batch);
        shootIcon.draw(batch);
        turnIcon.draw(batch);
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

    public ShootType getShootType() {
        return shootType;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX() - sprite.getWidth()/2/SCALE, getY() - sprite.getHeight()/2/SCALE);
        sprite.draw(batch, parentAlpha);
    }
}
