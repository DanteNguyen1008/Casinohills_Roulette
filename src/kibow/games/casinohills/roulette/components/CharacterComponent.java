package kibow.games.casinohills.roulette.components;

import java.util.ArrayList;

import kibow.games.casinohills.roulette.components.MSComponent.MStype;
import kibow.games.casinohills.roulette.screen.GameEntity;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.media.MediaPlayer;

public class CharacterComponent extends AbItemComponent {

    public static final int CHAR_STATUS_NORMAL = 0;
    public static final int CHAR_STATUS_WIN1 = 1;
    public static final int CHAR_STATUS_WIN2 = 2;
    public static final int CHAR_STATUS_WIN3 = 3;
    public static final int CHAR_STATUS_LOSE1 = 4;
    public static final int CHAR_STATUS_LOSE2 = 5;
    public static final int CHAR_STATUS_NORMAL2 = 6;

    public ArrayList<MSComponent> soundList;

    public enum CharacterAction {
        NO_MORE_BET, PLEASE_PLAY_BET
    }

    private int currentIndex;

    public CharacterComponent(int id, int width, int height, int colum,
                              int row, String background, float positionX, float positionY,
                              BaseGameActivity activity, ItemType itemType) {
        super(id, width, height, background, positionX, positionY, activity,
                itemType);
        // TODO Auto-generated constructor stub
        BitmapTextureAtlas atlastBig = new BitmapTextureAtlas(
                activity.getTextureManager(), width, height,
                TextureOptions.BILINEAR);

        ITiledTextureRegion tiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
                .createTiledFromAsset(atlastBig, activity, background, 0, 0,
                        colum, row);

        soundList = new ArrayList<MSComponent>();

        loadCharacterSound(loadVoiceNames());

        tiledSprite = new TiledSprite(positionX, positionY, tiledTextureRegion,
                activity.getVertexBufferObjectManager());

        tiledSprite.setCurrentTileIndex(CHAR_STATUS_NORMAL);
        atlastBig.load();
    }

    public void changeSprite(int index) {
        tiledSprite.setCurrentTileIndex(index);
    }

    private void loadCharacterSound(String[] voiceFileNames) {
        for (int i = 0; i < voiceFileNames.length; i++) {
            soundList.add(new MSComponent(i, voiceFileNames[i], MStype.MUSIC,
                    getActivity().getEngine(), getActivity(), false));
        }
    }

    private String[] loadVoiceNames() {

        String[] voiceNames = new String[7];

        switch (getiItemType()) {
            case CHARACTER_GIRL:
                voiceNames[0] = "let_s_do_this_normal.mp3";
                voiceNames[1] = "yeah_it_s_your_lucky_day_happy.mp3";
                voiceNames[2] = "i_like_it_nice_move_cheer.mp3";
                voiceNames[3] = "why_is_this_happening_sad.mp3";
                voiceNames[4] = "what_the_heck_stunned.mp3";
                voiceNames[5] = "that_s_not_cool_angry.mp3";
                voiceNames[6] = "have_fun_goodluck_normal.mp3";

                break;
            default:
                break;
        }

        return voiceNames;
    }

    public MSComponent getPlayVoice(int index) {
        currentIndex = index;
        return soundList.get(index);
    }

    public void say(int index) {
        soundList.get(index).play();
    }

    public TiledSprite tiledSprite;

    public static void playSequence(MSComponent music1, final MSComponent music2) {
        if (GameEntity.getInstance().isMusicEnable) {
            music1.music.seekTo(0);
            music1.play();
            music1.music
                    .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // TODO Auto-generated method stub
                            music2.music.seekTo(0);
                            music2.play();

                        }
                    });
        }
    }

    public void stopSay() {
        if (soundList.get(currentIndex).music.isPlaying())
            soundList.get(currentIndex).pause();
    }

}
