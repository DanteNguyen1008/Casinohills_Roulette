package kibow.games.casinohills.roulette.components;

import java.util.ArrayList;

import kibow.games.casinohills.roulette.screen.GameEntity;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

public class PatternComponent extends AbItemComponent {

    public ArrayList<CoinComponent> coinList;
    private long systemTime1;
    public Sprite sprite;

    public PatternComponent(int id, int width, int height, String background,
                            float positionX, float positionY, BaseGameActivity activity,
                            ItemType itemType, Scene scene, GameEntity.PatternType patternType) {
        super(id, width, height, background, positionX, positionY, activity,
                itemType);
        this.scene = scene;
        this.patternType = patternType;
        coinList = new ArrayList<CoinComponent>();
        BitmapTextureAtlas atlastBig = new BitmapTextureAtlas(
                activity.getTextureManager(), width, height,
                TextureOptions.BILINEAR);

        ITextureRegion atlasRegionBig = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(atlastBig, activity, background, 0, 0);
        sprite = (new Sprite(positionX, positionY, atlasRegionBig,
                activity.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
                                         float Y) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        if (System.currentTimeMillis() - systemTime1 < 300) {
                            bet();
                        }

                        break;
                    case TouchEvent.ACTION_MOVE:
                        Log.d("Touch Event", "Touch to ID : " + getiID());
                        break;
                    case TouchEvent.ACTION_UP:
                        systemTime1 = System.currentTimeMillis();
                        break;
                }
                return false;
            }
        });
        atlastBig.load();
        sprite.setAlpha(0.1f);
    }

    public GameEntity.PatternType patternType;
    public Scene scene;

    public void sortCoinList(CoinComponent coin) {

        for (int i = 0; i < coinList.size(); i++) {
            if (coinList.get(i).getPositionY() < coin.getPositionY()) {
                coinList.get(i).sprite.setPosition(coinList.get(i)
                        .getPositionX(), coinList.get(i).getPositionY() + 5);
                coinList.get(i)
                        .setPositionY(coinList.get(i).getPositionY() + 5);
            }
        }
        coinList.remove(coin);
    }

    public void bet() {
    	/*
        if (GameEntity.getInstance().sceneManager.gameScene.playAnimationComponent.showBackgroundResult == true) {
            GameEntity.getInstance().sceneManager.gameScene.buttonPlaySound();
            GameEntity.getInstance().updateAfterBet();
            GameEntity.getInstance().sceneManager.gameScene.playAnimationComponent
                    .stopAnimation();
            GameEntity.getInstance().sceneManager.gameScene.playAnimationComponent.showBackgroundResult = false;
        }*/
        GameEntity.getInstance().bet(
                getPositionX() + getiWidth() / 2 - GameEntity.miniCoiWidth / 2,
                getPositionY() + getiHeight() / 2 - GameEntity.miniCoinHeight
                        / 2, PatternComponent.this);
    }

}
