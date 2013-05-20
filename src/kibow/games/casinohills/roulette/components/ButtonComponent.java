package kibow.games.casinohills.roulette.components;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class ButtonComponent extends AbItemComponent {

    IOnItemClick onItemClick;

    public ButtonComponent(int id, int width, int height, int colum, int row,
                           String background, float positionX, float positionY,
                           BaseGameActivity activity, ItemType itemType, final IOnItemClick onItemClick) {
        super(id, width, height, background, positionX, positionY, activity,
                itemType);
        this.onItemClick = onItemClick;
        BitmapTextureAtlas atlastBig = new BitmapTextureAtlas(
                activity.getTextureManager(), width, height,
                TextureOptions.BILINEAR);

        ITiledTextureRegion tiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
                .createTiledFromAsset(atlastBig, activity, background, 0, 0,
                        colum, row);

        tiledSprite = (new TiledSprite(positionX, positionY,
                tiledTextureRegion, activity.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                                         float X, float Y) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setScale(1.2f);
                        this.setCurrentTileIndex(0);
                        break;
                    case TouchEvent.ACTION_MOVE:

                        break;
                    case TouchEvent.ACTION_UP:
                        this.setScale(1f);
                        this.setCurrentTileIndex(0);
                        onItemClick.onClick(ButtonComponent.this);
                        break;
                }
                return true;
            }

        });
        tiledSprite.setCurrentTileIndex(0);
        atlastBig.load();
    }

    public TiledSprite tiledSprite;
}
