package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by user on 3/18/2018.
 */
public class BubbleBuster extends ApplicationAdapter {
    private Texture greenbubble;
    private Texture Pin;
    private Texture redbubble;
    private Texture bluebubble;
    int i=0;
    private Sound dropSound;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Rectangle pin;
    private Rectangle bubble;
    private int count;
    private int value;
    private int random=1;
    private String Score;
    BitmapFont ScoreBitmap;
    @Override
    public void create() {
        //Texture Creation

        greenbubble = new Texture(Gdx.files.internal("green.png"));
        Pin = new Texture(Gdx.files.internal("pin.png"));
        //Texture Creation
        redbubble = new Texture(Gdx.files.internal("red.jpg"));
        bluebubble = new Texture(Gdx.files.internal("blue.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

//Scorecard
        Score = "score: 0";
        ScoreBitmap = new BitmapFont();
        //Orthographic camera creation for 2D objects
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        pin = new Rectangle();
        pin.x = 800 / 2 - 50 / 2;
        pin.y = 20;
        pin.width = 50;
        pin.height = 50;


        bubble();
    }

    //random method dictates the colour of Bubble
    private void random() {
        random= MathUtils.random(1,3);
        //greenbubble=10 pts
        if (random == 1) value = 10;
        //bluebubble=15 pts
        if (random == 2) value = 15;
        //redbubble=5 pts
        if (random == 3) value = 5;
    }
    //instantiation of a Rectangle to be used as bubble
    private void bubble() {
        bubble = new Rectangle();
        bubble.x = MathUtils.random(50, 800 - 50);
        bubble.y = MathUtils.random(50, 480 - 50);
        bubble.width = 50;
        bubble.height = 50;
        random();
    }
    //render method dictates frames during the game
    @Override
    public void render() {

        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        ScoreBitmap.setColor(0.0f, 0.0f, 0.2f, 1.0f);
        ScoreBitmap.draw(batch, Score, 25, 100);
        batch.draw(Pin, pin.x, pin.y);

        if (random == 1) batch.draw(greenbubble, bubble.x, bubble.y);
        else if (random == 2) batch.draw(bluebubble, bubble.x, bubble.y);
        else if (random == 3) batch.draw(redbubble, bubble.x, bubble.y);

        batch.end();

        // processes user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            pin.x = touchPos.x - 50 / 2;
            pin.y = touchPos.y - 50 / 2;
        }

//check for overlap
        if (bubble.overlaps(pin)) {
            dropSound.play();
            count = value + count;
            Score = "score: " + count;
            bubble=null;
            bubble();

        }
    }
    @Override
    public void dispose () {
        // dispose of all the native resources
        greenbubble.dispose();
        bluebubble.dispose();
        redbubble.dispose();
        Pin.dispose();
        dropSound.dispose();
        batch.dispose();
    }
}


