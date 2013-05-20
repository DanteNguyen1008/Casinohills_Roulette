package kibow.games.casinohills.roulette.acitivty;

import java.util.ArrayList;

import kibow.games.casinohills.roulette.components.AbItemComponent.ItemType;
import kibow.games.casinohills.roulette.components.ItemComponent;
import kibow.games.casinohills.roulette.components.UserComponent;
import kibow.games.casinohills.roulette.screen.GameEntity;
import kibow.networkmanagement.network.AsyncNetworkHandler;
import kibow.networkmanagement.network.ConnectionHandler;
import kibow.networkmanagement.network.IOnNetworkHandle;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.SimpleLayoutGameActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class SplashScreenAcivity extends SimpleLayoutGameActivity implements
		OnClickListener, IOnNetworkHandle {

	private BitmapTextureAtlas splashTextureAtlas;
	private ITextureRegion splashTextureRegion;
	private Sprite splash;

	private Scene splashScene;

	private Camera camera;

	private Button btnLogin;
	private EditText txtUsername;
	private EditText txtPassword;

	private LinearLayout llMainLayout;

	private int currentScreenWidth;
	private int currentScreenHeight;

	private ItemComponent staticChar;
	private ItemComponent staticTitle;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		camera = new Camera(0, 0, GameEntity.CAMERA_WIDTH,
				GameEntity.CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
				camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);

		return engineOptions;
	}

	// ===========================================================
	// INITIALIZIE
	// ===========================================================

	private void initSplashScene() {
		splashScene = new Scene();
		splashScene.setBackground(new Background(1f, 1f, 1f));
		splash = new Sprite(0, 0, splashTextureRegion,
				mEngine.getVertexBufferObjectManager()) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		splash.setScale(1.5f);
		splash.setPosition(
				(GameEntity.CAMERA_WIDTH - splash.getWidth()) * 0.5f,
				(GameEntity.CAMERA_HEIGHT - splash.getHeight()) * 0.5f);
		splashScene.registerTouchArea(splash);

		splashScene.attachChild(splash);
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),
				800, 480, TextureOptions.NEAREST);
		splashTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlas, this, "bg.jpg", 0, 0);
		splashTextureAtlas.load();

		staticChar = new ItemComponent(1, 116, 419, "char1.png", 550, 50, this,
				ItemType.NORMAL_ITEM);

		staticTitle = new ItemComponent(1, 152, 119, "roulette_title.png", 240,
				40, this, ItemType.NORMAL_ITEM);
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		initSplashScene();
		splashScene.attachChild(staticChar.sprite);
		splashScene.attachChild(staticTitle.sprite);
		return splashScene;
	}

	@Override
	protected int getLayoutID() {
		return R.layout.activity_opengl_render;
	}

	@Override
	protected int getRenderSurfaceViewID() {
		return R.id.render_surfaceview;
	}

	@Override
	protected void onSetContentView() {
		super.onSetContentView();
		Intent intent = this.getIntent();

		boolean isFromPlatfrom = intent
				.getBooleanExtra("isFromPlatform", false);
		if (isFromPlatfrom) {
			String username = intent.getStringExtra("username");
			String password = intent.getStringExtra("password");
			String gameID = intent.getStringExtra("gameID");
			GameEntity.getInstance().connectionHandler = new ConnectionHandler();
			AsyncNetworkHandler networkHandler = new AsyncNetworkHandler();
			ArrayList<String> paramsName = new ArrayList<String>();
			paramsName.add("username");
			paramsName.add("password");
			paramsName.add("game_id");
			ArrayList<Object> paramsValue = new ArrayList<Object>();
			paramsValue.add(username);
			paramsValue.add(password);
			paramsValue.add(gameID);
			GameEntity.GAME_ID = gameID;
			Object[] params = { GameEntity.getInstance().connectionHandler,
					this, GameEntity.SIGNIN_TASK, paramsName, paramsValue,
					this, false };
			networkHandler.execute(params);
		} else {
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			currentScreenHeight = displaymetrics.heightPixels;
			currentScreenWidth = displaymetrics.widthPixels;
			GameEntity.getInstance().connectionHandler = new ConnectionHandler();
			llMainLayout = (LinearLayout) findViewById(R.id.llMainLayout);
			llMainLayout.setOrientation(LinearLayout.VERTICAL);
			txtUsername = new EditText(this);
			LayoutParams usernameLayoutParams = new LayoutParams(300, 70);
			LayoutParams passwordLayoutParams = new LayoutParams(300, 70);
			LayoutParams loginLayoutParams = new LayoutParams(300, 60);
			usernameLayoutParams.setMargins(currentScreenWidth / 2 - 300 / 2,
					currentScreenHeight / 2 - 70, 0, 0);
			passwordLayoutParams.setMargins(currentScreenWidth / 2 - 300 / 2,
					0, 0, 0);
			loginLayoutParams.setMargins(currentScreenWidth / 2 - 300 / 2, 0,
					0, 0);
			txtPassword = new EditText(this);
			txtPassword.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			btnLogin = new Button(this);
			btnLogin.setText("Login");
			llMainLayout.addView(txtUsername, usernameLayoutParams);

			llMainLayout.addView(txtPassword, passwordLayoutParams);

			llMainLayout.addView(btnLogin, loginLayoutParams);

			txtUsername.setText("a1provip002");
			txtPassword.setText("123456");
			btnLogin.setOnClickListener(this);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(btnLogin)) {
			
			AsyncNetworkHandler networkHandler = new AsyncNetworkHandler();
			ArrayList<String> paramsName = new ArrayList<String>();
			paramsName.add("username");
			paramsName.add("password");
			paramsName.add("game_id");
			ArrayList<Object> paramsValue = new ArrayList<Object>();
			paramsValue.add(txtUsername.getText().toString().trim());
			paramsValue.add(txtPassword.getText().toString().trim());
			paramsValue.add(GameEntity.GAME_ID);
			Object[] params = { GameEntity.getInstance().connectionHandler,
					this, GameEntity.SIGNIN_TASK, paramsName, paramsValue,
					this, false };
			networkHandler.execute(params);
			
			/*
			Intent intent = new Intent(this, RouletteGameActivity.class);

			

			GameEntity.getInstance().userComponent = new UserComponent(
					"123123123", 1,
					"Dante",
					"awdawd",
					"23123123",
					"awdawd",
					"adadawdawd",
					10000, "213123123123");
			startActivity(intent);
			finish();*/
		}
	}

	@Override
	public void onNetwokrHandle(JSONObject result,
			ConnectionHandler connectionHandler, Activity activity)
			throws JSONException {
		// TODO Auto-generated method stub
		if (connectionHandler.status) {

			JSONObject loginData = result.getJSONObject("login");
			String token = result.getString("token");
			if (loginData.has("user")) {

				Intent intent = new Intent(activity, RouletteGameActivity.class);

				JSONObject userJson = loginData.getJSONObject("user");
				JSONObject creditJson = loginData.getJSONObject("credit");

				GameEntity.getInstance().userComponent = new UserComponent(
						userJson.getString("id"), userJson.getInt("status"),
						userJson.getString("nickname"),
						userJson.getString("language"),
						userJson.getString("created"),
						userJson.getString("modified"),
						userJson.getString("email"),
						creditJson.getDouble("balance"), token);
				activity.startActivity(intent);
				activity.finish();
			} else {
				Toast.makeText(activity, "Login fail, please try again",
						Toast.LENGTH_LONG).show();
			}
		} else {

			Toast.makeText(activity, "Login fail, please try again",
					Toast.LENGTH_LONG).show();

		}
	}

	@Override
	public void onNetworkError() {
		// TODO Auto-generated method stub

	}
}
