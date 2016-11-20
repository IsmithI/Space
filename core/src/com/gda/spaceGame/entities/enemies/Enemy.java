package com.gda.spaceGame.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gda.spaceGame.entities.Bullet;
import com.gda.spaceGame.entities.Player;
import com.gda.spaceGame.screens.GameScreen;

import static com.gda.spaceGame.SpaceMain.SCALE;

/**
 * Created by Smith on 13.11.2016.
 */
public class Enemy extends Actor {

    private final int level;
    private final Sprite sprite;
    private float speed, angle, turn_angle;
    private float angleToPlayer;
    private Circle bounds;
    private final int worth;

    private final int z = 3;

    public Enemy(int level, Texture texture, float speed, float turn_angle, int worth) {
        this.level = level;
        this.speed = speed;
        angle = 90;
        this.turn_angle = turn_angle;
        this.worth = worth;

        sprite = new Sprite(texture);
        sprite.setScale(1/SCALE);
        sprite.rotate90(true);

        bounds = new Circle();
        bounds.setRadius(sprite.getWidth()/2/SCALE);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX() - sprite.getWidth()/2, getY() - sprite.getHeight()/2);
        sprite.draw(batch, parentAlpha);
        sprite.setRotation(angle);

        checkCollision();
    }

    public boolean checkCollision() {
        Bullet bullet;
        Player player;
        for (Actor actor : getStage().getActors()) {
            if (actor instanceof Bullet) {
                bullet = (Bullet) actor;
                if (bullet.getBounds().overlaps(getBounds())) {
                    bullet.remove();
                    die();
                    return true;
                }
            }
            else if (actor instanceof Player) {
                player = (Player) actor;
                if (player.getBounds().overlaps(getBounds())) {
                    remove();
                    return true;
                }
            }
        }
        return false;
    }

    public void die() {
        remove();
        GameScreen.money += worth;
    }

    public void move(Vector2 playerPosition) {
        calculateAngleToPlayer(playerPosition);

        if (Math.abs(angle - angleToPlayer) > turn_angle) {

            if (Math.abs(angle - angleToPlayer) > 180) {
                if (angle > angleToPlayer)
                    angle += turn_angle;
                else
                    angle -= turn_angle;
            }
            else {
                if (angle > angleToPlayer)
                    angle -= turn_angle;
                else
                    angle += turn_angle;
            }

        }
        if (angle < 0) angle += 360;
        if (angle > 360) angle -= 360;

        moveBy(speed * MathUtils.cosDeg(angle), speed * MathUtils.sinDeg(angle));
        bounds.setPosition(getX(), getY());
    }

    private void calculateAngleToPlayer(Vector2 playerPosition) {
        angleToPlayer = MathUtils.radiansToDegrees * MathUtils.atan2(getY() - playerPosition.y, getX() - playerPosition.x);
        angleToPlayer += 180;
    }

    public int getLevel() {
        return level;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getSpeed() {
        return speed;
    }

    public float getAngle() {
        return angle;
    }

    public float getTurn_angle() {
        return turn_angle;
    }

    public int getZ() {
        return z;
    }

    @Override
    public int getZIndex() {
        return z;
    }

    public Circle getBounds() {
        return bounds;
    }
}
