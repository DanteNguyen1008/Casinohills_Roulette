package kibow.games.casinohills.roulette.screen;

import java.util.concurrent.ExecutionException;

import kibow.games.casinohills.roulette.components.GameComponent;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.ui.activity.BaseGameActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class SceneManager {
    public enum SceneType {
        GAME, LOADING, ANIMATION, HISTORY, HELP
    }

    private SceneType currentScene;
    public BaseGameActivity activity;
    public Engine engine;
    public ZoomCamera camera;

    public GameScene gameScene;
    public LoadingScene loadingScene;

    // public AnimationScene animationScene;

    public SceneManager(BaseGameActivity activity, Engine engine, ZoomCamera camera) {
        this.activity = activity;
        this.engine = engine;
        this.camera = camera;
        GameEntity.getInstance().currentGame = new GameComponent();
        loadingScene = new LoadingScene(engine, camera, activity);
        gameScene = new GameScene(engine, camera, activity);

    }

    public SceneType getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(SceneType currentScene) {
        this.currentScene = currentScene;
    }

    public boolean setGameScene() {

        LoadingAsync loading = new LoadingAsync();
        try {
            return loading.execute().get();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public void loadScene(SceneType sceneType) {
        switch (sceneType) {
            case LOADING:
                loadingScene.loadResource();
                loadingScene.loadScene();
                break;
            case GAME:
                gameScene.loadResource();
                gameScene.loadScene();
                break;
            default:
                break;
        }
    }

    public void asyncLoadNextScene() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                LoadingAsync loading = new LoadingAsync();
                loading.execute();
            }
        });
    }

    public void setScene(SceneType nextScene) {
        // Clear current scene
        switch (getCurrentScene()) {
            case GAME:
                // May be clear all bet
                break;
            case LOADING:
                loadingScene.unLoadScene();
                break;
            default:
                break;
        }

        setCurrentScene(nextScene);

        switch (nextScene) {
            case GAME:
                engine.setScene(gameScene.getScene());
                break;
            case LOADING:
                engine.setScene(loadingScene.getScene());
                break;
            default:
                break;
        }
    }

    class LoadingAsync extends AsyncTask<Object, String, Boolean> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Object... arg0) {
            // TODO Auto-generated method stub
            // gameScene.loadResource();
            // gameScene.loadScene();
            loadingScene.bar.updateBar(0.1f);
            loadingScene.percentText.updateText("10%");
            loadScene(SceneType.GAME);
            loadingScene.bar.updateBar(1f);
            loadingScene.percentText.updateText("100%");
            return true;
        }

        @Override
        protected void onPostExecute(Boolean _result) {
            setScene(SceneType.GAME);
            GameEntity.getInstance().sceneManager.gameScene.backgroundMusic.play();
        }
    }
}
