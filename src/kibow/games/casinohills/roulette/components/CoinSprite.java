package kibow.games.casinohills.roulette.components;

import kibow.games.casinohills.roulette.components.AbItemComponent.ItemType;
import kibow.games.casinohills.roulette.screen.GameEntity;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;

public class CoinSprite extends GenericPool<Sprite> {

    int height;
    int width;
    int coinID;
    CoinComponent coinComponent;

    public CoinSprite(float positionX, float positionY, int width, int height,
                      int coinID, ITextureRegion atlasRegionBig,
                      VertexBufferObjectManager vbo, CoinComponent coinComponent) {
        setPositionX(positionX);
        setPositionY(positionY);
        this.atlasRegionBig = atlasRegionBig;
        this.vbo = vbo;
        this.height = height;
        this.width = width;
        this.coinID = coinID;
        this.coinComponent = coinComponent;
    }

    ITextureRegion atlasRegionBig;
    VertexBufferObjectManager vbo;

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    private float positionX;
    private float positionY;

    @Override
    protected Sprite onAllocatePoolItem() {
        // TODO Auto-generated method stub
        return new Sprite(positionX, positionY, atlasRegionBig, vbo) {
            boolean mGrabbed = false;

            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
                                         float Y) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        mGrabbed = true;
                        // this.setScale(1.5f);

                        for (int i = 0; i < GameEntity.getInstance().sceneManager.gameScene.textList
                                .size(); i++) {
                            if (GameEntity.getInstance().sceneManager.gameScene.textList
                                    .get(i).getiID() == 1) {
                                GameEntity.getInstance().sceneManager.gameScene.textList
                                        .get(i)
                                        .updateBalance(
                                                UserComponent.UserAction.INCREASE_BALANCE,
                                                coinID);
                            } else if (GameEntity.getInstance().sceneManager.gameScene.textList
                                    .get(i).getiID() == 3) {
                                GameEntity.getInstance().sceneManager.gameScene.textList
                                        .get(i).increaseBetRemain(coinID);
                            }
                        }
                        if (GameEntity.getInstance().sceneManager.gameScene != null)
                            GameEntity.getInstance().sceneManager.gameScene.releaseBetSound
                                    .play();
                        coinComponent.deleteItSeft();
                        break;
                    case TouchEvent.ACTION_MOVE:
                        if (mGrabbed) {
                            // this.setScale(1.5f);
                            this.setPosition(pSceneTouchEvent.getX() - width / 2,
                                    pSceneTouchEvent.getY() - height / 2);
                            GameEntity.getInstance().specifyDragOnItem(pSceneTouchEvent.getX(),
                                    pSceneTouchEvent.getY(), pSceneTouchEvent, true);

                        }
                        break;
                    case TouchEvent.ACTION_UP:
                        if (mGrabbed) {
                            mGrabbed = false;
                            // this.setScale(0.7f);
                            GameEntity.getInstance().currentCoint = coinComponent
                                    .getCoinID();
                            GameEntity.getInstance().specifyDragOnItem(pSceneTouchEvent.getX(),
                                    pSceneTouchEvent.getY(), pSceneTouchEvent,
                                    false);
                            GameEntity.getInstance().sceneManager.gameScene
                                    .getScene().unregisterTouchArea(this);
                            this.detachSelf();
                            coinComponent.deleteItSeft();
                        }

                        break;
                }
                return true;
            }
        };
    }

    @Override
    protected void onHandleRecycleItem(final Sprite coin) {
        coin.setIgnoreUpdate(true);
        coin.setVisible(false);
    }

    @Override
    protected void onHandleObtainItem(final Sprite coin) {
        coin.reset();
        coin.setScale(0.7f);
    }


}
