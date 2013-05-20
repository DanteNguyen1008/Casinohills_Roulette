package kibow.games.casinohills.roulette.components;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class ItemComponent extends AbItemComponent {
    public Sprite sprite;

    public ItemComponent(int id, int width, int height, String background,
                         float positionX, float positionY, BaseGameActivity activity,
                         ItemType itemType) {
        super(id, width, height, background, positionX, positionY, activity,
                itemType);
        BitmapTextureAtlas atlastBig = new BitmapTextureAtlas(
                activity.getTextureManager(), width, height,
                TextureOptions.BILINEAR);

        ITextureRegion atlasRegionBig = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(atlastBig, activity, background, 0, 0);

        sprite = (new Sprite(positionX, positionY, atlasRegionBig, activity
                .getEngine().getVertexBufferObjectManager()));

        atlastBig.load();
    }
}
