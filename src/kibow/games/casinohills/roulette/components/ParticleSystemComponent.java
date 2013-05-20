package kibow.games.casinohills.roulette.components;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;


public class ParticleSystemComponent extends AbItemComponent {
    ParticleSystem<Sprite> particleSystem;
    int mTimePart;
    Scene scene;
    PointParticleEmitter particleEmitter;

    public ParticleSystemComponent(int id, final int width, final int height,
                                   final String background, final float positionX,
                                   final float positionY, final BaseGameActivity activity,
                                   ItemType itemType, Color color, int mNumPart, int mTimePart,
                                   Scene scene) {
        super(id, width, height, background, positionX, positionY, activity,
                itemType);
        // TODO Auto-generated constructor stub
        this.mTimePart = mTimePart;
        this.scene = scene;
        particleEmitter = new PointParticleEmitter(positionX, positionY);

        IEntityFactory<Sprite> recFact = new IEntityFactory<Sprite>() {

            @Override
            public Sprite create(float pX, float pY) {
                BitmapTextureAtlas atlastBig = new BitmapTextureAtlas(
                        activity.getTextureManager(), width, height,
                        TextureOptions.BILINEAR);

                ITextureRegion atlasRegionBig = BitmapTextureAtlasTextureRegionFactory
                        .createFromAsset(atlastBig, activity, background, 0, 0);

                Sprite sprite = new Sprite(positionX, positionY,
                        atlasRegionBig, activity.getEngine()
                        .getVertexBufferObjectManager());

                atlastBig.load();
                return sprite;
            }

        };
        particleSystem = new ParticleSystem<Sprite>(recFact, particleEmitter,
                500, 500, mNumPart);

        particleSystem
                .addParticleInitializer(new VelocityParticleInitializer<Sprite>(
                        -50, 50, -50, 50));

        particleSystem
                .addParticleInitializer(new ColorParticleInitializer<Sprite>(
                        color));

        particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0,
                0.6f * mTimePart, 1, 0));
        particleSystem
                .addParticleModifier(new RotationParticleModifier<Sprite>(0,
                        mTimePart, 0, 360));

        this.scene.attachChild(particleSystem);
        this.scene.registerUpdateHandler(new TimerHandler(mTimePart,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {
                        particleSystem.detachSelf();
                        ParticleSystemComponent.this.scene.sortChildren();
                        ParticleSystemComponent.this.scene
                                .unregisterUpdateHandler(pTimerHandler);
                    }
                }));
    }

    public void attacthParticleSystem(int posX, int posY) {
        particleEmitter.setCenter(posX, posY);
    }

}
