package com.gda.spaceGame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gda.spaceGame.GUI.MenuShip;
import com.gda.spaceGame.ShootType;
import com.gda.spaceGame.entities.enemies.Enemy;

import static com.gda.spaceGame.SpaceMain.SCALE;

/**
 * Created by smith on 10/18/16.
 */
public class Player extends Actor{

    private MenuShip ship;

    private float speed, turn;
    private ShootType shootType;
    private Sprite sprite;

    private Circle bounds;

    private float angle = 90f;

    public Player(MenuShip ship) {
        this.ship = ship;

        setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        speed = ship.getSpeed();
        turn = ship.getTurn();
        shootType = ship.getShootType();
        sprite = new Sprite(ship.getSprite().getTexture());

        sprite.setScale(1/SCALE);
        sprite.rotate90(true);

        bounds = new Circle(getX(), getY(), getSprite().getWidth()/4/SCALE);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setRotation(angle);
        sprite.setPosition(getX() - sprite.getWidth()/2, getY() - sprite.getHeight()/2);
        sprite.draw(batch, parentAlpha);
        bounds.setPosition(getX(), getY());
    }

    public boolean collide() {
        Enemy enemy;
        for (Actor actor : getStage().getActors()) {
            if (actor instanceof Enemy) {
                enemy = (Enemy) actor;
                if (enemy.getBounds().overlaps(getBounds())) {
                    return true;
                }
            }
        }
        return false;
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getTurn() {
        return turn;
    }

    public void setTurn(float turn) {
        this.turn = turn;
    }

    public ShootType getShootType() {
        return shootType;
    }

    public void setShootType(ShootType shootType) {
        this.shootType = shootType;
    }

    public float getAngle() {
        return angle;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setAngle(float angle) {
        this.angle = angle;
        if (this.angle < 0) this.angle += 360;
        if (this.angle > 360) this.angle -= 360;
    }

    @Override
    public int getZIndex() {
        return 10;
    }

    public Circle getBounds() {
        return bounds;
    }

    public void die() {
        bounds.setRadius(0);
    }
}
