package kibow.games.casinohills.roulette.components;

import kibow.games.casinohills.roulette.screen.GameEntity;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

public class DragComponent extends AbItemComponent {

    public Sprite sprite;

    public DragComponent(int id, int width, int height, String background,
                         float positionX, float positionY, BaseGameActivity activity,
                         ItemType itemType, Scene scene, int coinID) {
        super(coinID, width, height, background, positionX, positionY,
                activity, itemType);
        this.scene = scene;
        setCoinID(coinID);
        BitmapTextureAtlas atlastBig = new BitmapTextureAtlas(
                activity.getTextureManager(), width, height,
                TextureOptions.BILINEAR);

        ITextureRegion atlasRegionBig = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(atlastBig, activity, background, 0, 0);
        tempDrag = (new Sprite(positionX, positionY, atlasRegionBig, activity
                .getEngine().getVertexBufferObjectManager()));
        sprite = (new Sprite(positionX, positionY, atlasRegionBig, activity
                .getEngine().getVertexBufferObjectManager()) {
            boolean mGrabbed = false;

            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X,
                                         float Y) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        mGrabbed = true;
                        this.setScale(1.5f);
                        break;
                    case TouchEvent.ACTION_MOVE:
                        if (mGrabbed) {
                            this.setScale(1.5f);
                            tempDrag.setPosition(pSceneTouchEvent.getX()
                                    - getiWidth() / 2, pSceneTouchEvent.getY()
                                    - getiHeight() / 2);
                            GameEntity.getInstance().specifyDragOnItem(
                                    pSceneTouchEvent.getX() - getiWidth() / 2,
                                    pSceneTouchEvent.getY() - getiHeight() / 2,
                                    pSceneTouchEvent, true);
                        }
                        break;
                    case TouchEvent.ACTION_UP:
                        if (mGrabbed) {
                            mGrabbed = false;
                            this.setScale(1.2f);
                            dropTempDrag();
                            GameEntity.getInstance().currentCoint = getCoinID();
                            GameEntity.getInstance().specifyDragOnItem(
                                    pSceneTouchEvent.getX() - getiWidth() / 2,
                                    pSceneTouchEvent.getY() - getiHeight() / 2,
                                    pSceneTouchEvent, false);
                        }
                        break;
                }
                return true;
            }
        });
        this.sprite.setScale(1.2f);

        atlastBig.load();
    }

    private int coinID;

    public int getCoinID() {
        return coinID;
    }

    public void setCoinID(int coinID) {
        this.coinID = coinID;
    }

    public Scene scene;

    public Sprite tempDrag;

    private void dropTempDrag() {
        tempDrag.setPosition(getPositionX(), getPositionY());
    }
}
