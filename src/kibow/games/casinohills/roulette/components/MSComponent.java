package kibow.games.casinohills.roulette.components;

import java.io.IOException;

import kibow.games.casinohills.roulette.screen.GameEntity;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;


import android.content.Context;

public class MSComponent {
    public Sound sound;
    public Music music;
    String resourceURL;
    int iID;
    MStype type;
    Engine mEngine;
    Context context;

    public enum MStype {
        MUSIC, SOUND
    }

    public MSComponent(int iID, String resourceURL, MStype type,
                       Engine mEngine, Context context) {
        this.iID = iID;
        this.resourceURL = resourceURL;
        this.type = type;
        this.mEngine = mEngine;
        this.context = context;
        try {

            this.sound = SoundFactory.createSoundFromAsset(
                    mEngine.getSoundManager(), context, resourceURL);

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public MSComponent(int iID, String resourceURL, MStype type,
                       Engine mEngine, Context context, boolean isLooped) {
        this.iID = iID;
        this.resourceURL = resourceURL;
        this.type = type;
        this.mEngine = mEngine;
        this.context = context;

        try {
            this.music = MusicFactory.createMusicFromAsset(
                    mEngine.getMusicManager(), context, resourceURL);
            this.music.setLooping(isLooped);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void play() {
        if (GameEntity.getInstance().isMusicEnable) {
            switch (type) {
                case MUSIC:

                    music.play();
                    break;
                case SOUND:
                    sound.play();
                    break;
                default:
                    break;
            }
        }

    }

    public void stop() {
        if (GameEntity.getInstance().isMusicEnable) {
            switch (type) {
                case MUSIC:
                    music.stop();
                    break;
                case SOUND:
                    sound.stop();
                    break;
                default:
                    break;
            }
        }
    }

    public void pause() {
        if (GameEntity.getInstance().isMusicEnable) {
            switch (type) {
                case MUSIC:
                    music.pause();
                    break;
                case SOUND:
                    sound.pause();
                    break;
                default:
                    break;
            }
        }
    }

    public void resume() {
        if (GameEntity.getInstance().isMusicEnable) {
            switch (type) {
                case MUSIC:
                    music.resume();
                    break;
                case SOUND:
                    sound.resume();
                    break;
                default:
                    break;
            }
        }
    }
}
