package kibow.games.casinohills.roulette.screen;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

public abstract class MyScene {

    public MyScene(Engine engine, ZoomCamera camera, BaseGameActivity activity) {
        this.engine = engine;
        this.camera = camera;
        this.activity = activity;
        this.scene = new Scene();

    }

    // Inherite methods

    public abstract void loadResource();

    public abstract void loadScene();

    public abstract void unLoadScene();

    public abstract void disableAllTouch();

    public abstract void enableAllTouch();

    // Field

    private Engine engine;
    public ZoomCamera camera;
    private Scene scene;
    private BaseGameActivity activity;

    // Getter and Setter

    public BaseGameActivity getActivity() {
        return activity;
    }

    public void setActivity(BaseGameActivity activity) {
        this.activity = activity;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public ZoomCamera getCamera() {
        return camera;
    }

    public void setCamera(ZoomCamera camera) {
        this.camera = camera;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        engine.setScene(scene);
    }

}
