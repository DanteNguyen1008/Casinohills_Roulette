package kibow.games.casinohills.roulette.components;

import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

public class AnimationComponent extends AbItemComponent {
    Scene scene;
    List<TextComponent> texts;

    public AnimationComponent(int id, int width, int height, int animatedColum, int animatedRow,
                              String background, float positionX, float positionY,
                              BaseGameActivity activity, ItemType itemType) {
        super(id, width, height, background, positionX, positionY, activity,
                itemType);
        this.animatedColum = animatedColum;
        this.animatedRow = animatedRow;
        BuildableBitmapTextureAtlas mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
                activity.getTextureManager(), getiWidth(), getiHeight(),
                TextureOptions.BILINEAR);
        TiledTextureRegion mDiceTextureRegion = BitmapTextureAtlasTextureRegionFactory
                .createTiledFromAsset(mBitmapTextureAtlas, activity,
                        getStrBackgroud(), animatedColum, animatedRow);

        try {
            mBitmapTextureAtlas
                    .build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
                            0, 0, 0));
            mBitmapTextureAtlas.load();
        } catch (TextureAtlasBuilderException e) {
            Debug.e(e);
        }

        animatedSprite = new AnimatedSprite(getPositionX(), getPositionY(),
                mDiceTextureRegion, activity.getEngine()
                .getVertexBufferObjectManager());
    }

    public int animatedColum;

    public int animatedRow;

    public AnimatedSprite animatedSprite;
}
