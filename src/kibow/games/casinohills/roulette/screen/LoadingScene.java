package kibow.games.casinohills.roulette.screen;

import kibow.games.casinohills.roulette.components.BarComponent;
import kibow.games.casinohills.roulette.components.TextComponent;
import kibow.games.casinohills.roulette.components.AbItemComponent.ItemType;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import android.graphics.Typeface;

public class LoadingScene extends MyScene {

    // ItemComponent background;
    public BarComponent bar;
    public TextComponent percentText;
    TextComponent test;

    public LoadingScene(Engine engine, ZoomCamera camera, BaseGameActivity activity) {
        super(engine, camera, activity);
        // TODO Auto-generated constructor stub

    }

    @Override
    public void loadResource() {
        // TODO Auto-generated method stub
        /*
		 * background = new ItemComponent(1, 224, 200, "sicbo_logo.png", 0, 0,
		 * getActivity(), ItemType.NORMAL_ITEM);
		 */
        bar = new BarComponent(1, 600, 30, "", 100, 240, getActivity(),
                ItemType.NORMAL_ITEM);
        Font mChangableFontBig = FontFactory.create(getEngine()
                .getFontManager(), getEngine().getTextureManager(), 512, 512,
                Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32,
                Color.WHITE_ABGR_PACKED_INT);
        percentText = new TextComponent(1, 0, 0, "0%", 370, 200,
                getActivity(), ItemType.TEXT, 1f, Color.WHITE, mChangableFontBig);
        mChangableFontBig.load();
    }

    @Override
    public void loadScene() {
        // TODO Auto-generated method stub
        getScene().attachChild(bar.bar);
        getScene().attachChild(percentText.text);
        bar.bar.setColor(Color.GREEN);
    }

    @Override
    public void unLoadScene() {
        // TODO Auto-generated method stub
        getScene().detachChildren();
        getScene().detachSelf();
    }

    @Override
    public void disableAllTouch() {
        // TODO Auto-generated method stub

    }

    @Override
    public void enableAllTouch() {
        // TODO Auto-generated method stub

    }

}
