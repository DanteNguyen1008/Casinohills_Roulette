package kibow.games.casinohills.roulette.components;

import java.util.ArrayList;

import kibow.games.casinohills.roulette.screen.GameEntity;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class MyMenuScene extends AbItemComponent implements IOnItemClick {
    ArrayList<ButtonComponent> itemList;
    public Sprite sprite;

    public MyMenuScene(int id, int width, int height, String background,
                       float positionX, float positionY, BaseGameActivity activity,
                       ItemType itemType) {
        super(id, width, height, background, positionX, positionY, activity,
                itemType);
        // TODO Auto-generated constructor stub
        BitmapTextureAtlas atlastBig = new BitmapTextureAtlas(
                activity.getTextureManager(), width, height,
                TextureOptions.BILINEAR);

        ITextureRegion atlasRegionBig = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(atlastBig, activity, background, 0, 0);
        sprite = (new Sprite(positionX, positionY, atlasRegionBig, activity
                .getEngine().getVertexBufferObjectManager()));
        atlastBig.load();
        // hideMenu();
        sprite.setPosition(-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT);
        itemList = new ArrayList<ButtonComponent>();
    }

    public void addItem(ButtonComponent item) {
        sprite.attachChild(item.tiledSprite);
        itemList.add(item);
    }

    public void displayMenu() {
        GameEntity.getInstance().sceneManager.gameScene.disableAllTouch();
        GameEntity.getInstance().isMenuDisplay = true;
        // GameEntity.getInstance().mSensorListener.stopRegisterShake();
        sprite.setPosition(0, 0);
        sprite.setZIndex(9999);
        sprite.getParent().sortChildren();
        if (GameEntity.getInstance().isResultDisplay) {
            GameEntity.getInstance().updateAfterBet();
            GameEntity.getInstance().sceneManager.gameScene.playAnimationComponent
                    .stopAnimation();
        }
    }

    public void hideMenu() {
        GameEntity.getInstance().sceneManager.gameScene.enableAllTouch();
        GameEntity.getInstance().isMenuDisplay = false;
        // GameEntity.getInstance().mSensorListener.registerShake();
        sprite.setPosition(-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT);
        sprite.setZIndex(0);
        sprite.getParent().sortChildren();
    }

    public void registerTouch(Scene parent) {
        for (int i = 0; i < itemList.size(); i++) {
            parent.registerTouchArea(itemList.get(i).tiledSprite);
        }
    }

    public void unRegisterTouch(Scene parent) {
        for (int i = 0; i < itemList.size(); i++) {
            parent.unregisterTouchArea(itemList.get(i).tiledSprite);
        }
    }

    @Override
    public void onClick(AbItemComponent component) {
        // TODO Auto-generated method stub
        ButtonComponent button = (ButtonComponent) component;
        hideMenu();
        switch (button.getiItemType()) {
            case MENU_RESUME:
                break;
            case MENU_HELP:
                GameEntity.getInstance().viewHelp();
                break;
            case MENU_LOGOUT:
                GameEntity.getInstance().logout();
                break;
            case MENU_EXIT:
                GameEntity.getInstance().sceneManager.gameScene
                        .onBackButtonPress(true);
                break;
            default:
                break;
        }
    }

}
