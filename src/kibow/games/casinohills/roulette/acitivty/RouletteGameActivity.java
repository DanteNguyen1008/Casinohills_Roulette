package kibow.games.casinohills.roulette.acitivty;

import kibow.games.casinohills.roulette.components.UserComponent;
import kibow.games.casinohills.roulette.screen.GameEntity;
import kibow.games.casinohills.roulette.screen.SceneManager;
import kibow.games.casinohills.roulette.screen.SceneManager.SceneType;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.input.touch.detector.PinchZoomDetector.IPinchZoomDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class RouletteGameActivity extends BaseGameActivity implements
		IScrollDetectorListener, IPinchZoomDetectorListener {

	private ZoomCamera camera;
	public UserComponent userComponent;
	public float mPinchZoomStartedCameraZoomFactor;
	public SurfaceScrollDetector mScrollDetector;
	public PinchZoomDetector mPinchZoomDetector;

	// shake phone object

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		GameEntity.getInstance().rouletteGameActivity = this;
		camera = new ZoomCamera(0, 0, GameEntity.CAMERA_WIDTH,
				GameEntity.CAMERA_HEIGHT);
		camera.setBounds(0, 0, GameEntity.CAMERA_WIDTH,
				GameEntity.CAMERA_HEIGHT);
		camera.setBoundsEnabled(true);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
				camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (GameEntity.getInstance().sceneManager != null) {
			if (!GameEntity.getInstance().sceneManager.gameScene.backgroundMusic.music
					.isReleased()) {
				GameEntity.getInstance().sceneManager.gameScene.backgroundMusic
						.resume();
			}
		}
	}

	@Override
	protected void onPause() {
		super.onStop();
		if (GameEntity.getInstance().sceneManager != null)
			if (!GameEntity.getInstance().sceneManager.gameScene.backgroundMusic.music
					.isReleased())
				GameEntity.getInstance().sceneManager.gameScene.backgroundMusic
						.pause();

	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		// createProgressDialog();
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		GameEntity.getInstance().sceneManager = new SceneManager(this, mEngine,
				camera);
		GameEntity.getInstance().sceneManager.loadScene(SceneType.LOADING);
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		// TODO Auto-generated method stub
		GameEntity.getInstance().sceneManager
				.setCurrentScene(SceneType.LOADING);
		mScrollDetector = new SurfaceScrollDetector(this);
		mPinchZoomDetector = new PinchZoomDetector(this);
		pOnCreateSceneCallback
				.onCreateSceneFinished(GameEntity.getInstance().sceneManager.loadingScene
						.getScene());
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// TODO Auto-generated method stub

		GameEntity.getInstance().sceneManager.asyncLoadNextScene();
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (GameEntity.getInstance().sceneManager.getCurrentScene() == SceneType.GAME) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				onBackPressed();
			} else if (keyCode == KeyEvent.KEYCODE_MENU) {
				if (!GameEntity.getInstance().isAnimationRunning
						&& !GameEntity.getInstance().isBackPress) {
					GameEntity.getInstance().sceneManager.gameScene.menuScene
							.displayMenu();
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		if (GameEntity.getInstance().sceneManager.getCurrentScene() == SceneType.GAME) {
			if (!GameEntity.getInstance().isMenuDisplay
					&& !GameEntity.getInstance().isAnimationRunning
					&& !GameEntity.getInstance().sceneManager.gameScene.playAnimationComponent.showBackgroundResult) {
				GameEntity.getInstance().sceneManager.gameScene
						.onBackButtonPress(true);
			}
		}
	}

	@Override
	public void onScrollStarted(final ScrollDetector pScollDetector,
			final int pPointerID, final float pDistanceX, final float pDistanceY) {
		final float zoomFactor = camera.getZoomFactor();
		this.camera.offsetCenter(-pDistanceX / zoomFactor, -pDistanceY
				/ zoomFactor);
	}

	@Override
	public void onScroll(final ScrollDetector pScollDetector,
			final int pPointerID, final float pDistanceX, final float pDistanceY) {
		final float zoomFactor = this.camera.getZoomFactor();
		this.camera.offsetCenter(-pDistanceX / zoomFactor, -pDistanceY
				/ zoomFactor);
	}

	@Override
	public void onScrollFinished(final ScrollDetector pScollDetector,
			final int pPointerID, final float pDistanceX, final float pDistanceY) {
		final float zoomFactor = this.camera.getZoomFactor();
		this.camera.offsetCenter(-pDistanceX / zoomFactor, -pDistanceY
				/ zoomFactor);
	}

	@Override
	public void onPinchZoomStarted(final PinchZoomDetector pPinchZoomDetector,
			final TouchEvent pTouchEvent) {

		mPinchZoomStartedCameraZoomFactor = this.camera.getZoomFactor();
	}

	@Override
	public void onPinchZoom(final PinchZoomDetector pPinchZoomDetector,
			final TouchEvent pTouchEvent, final float pZoomFactor) {
		if (mPinchZoomStartedCameraZoomFactor * pZoomFactor >= 1
				&& mPinchZoomStartedCameraZoomFactor * pZoomFactor <= 2)
			this.camera.setZoomFactor(mPinchZoomStartedCameraZoomFactor
					* pZoomFactor);
		else if (mPinchZoomStartedCameraZoomFactor * pZoomFactor < 1)
			this.camera.setZoomFactor(1f);
	}

	@Override
	public void onPinchZoomFinished(final PinchZoomDetector pPinchZoomDetector,
			final TouchEvent pTouchEvent, final float pZoomFactor) {
		if (mPinchZoomStartedCameraZoomFactor * pZoomFactor >= 1
				&& mPinchZoomStartedCameraZoomFactor * pZoomFactor <= 2)
			this.camera.setZoomFactor(mPinchZoomStartedCameraZoomFactor
					* pZoomFactor);
		else if (mPinchZoomStartedCameraZoomFactor * pZoomFactor < 1)
			this.camera.setZoomFactor(1f);
	}
}
