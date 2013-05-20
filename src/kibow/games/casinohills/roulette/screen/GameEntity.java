package kibow.games.casinohills.roulette.screen;

import java.util.ArrayList;

import kibow.games.casinohills.roulette.acitivty.HelpActivity;
import kibow.games.casinohills.roulette.acitivty.RouletteGameActivity;
import kibow.games.casinohills.roulette.acitivty.SplashScreenAcivity;
import kibow.games.casinohills.roulette.acitivty.WebviewHelpPage;
import kibow.games.casinohills.roulette.components.AbItemComponent.ItemType;
import kibow.games.casinohills.roulette.components.BetComponent;
import kibow.games.casinohills.roulette.components.CharacterComponent;
import kibow.games.casinohills.roulette.components.CharacterComponent.CharacterAction;
import kibow.games.casinohills.roulette.components.CoinComponent;
import kibow.games.casinohills.roulette.components.GameComponent;
import kibow.games.casinohills.roulette.components.PatternComponent;
import kibow.games.casinohills.roulette.components.UserComponent;
import kibow.networkmanagement.network.AsyncNetworkHandler;
import kibow.networkmanagement.network.ConnectionHandler;
import kibow.networkmanagement.network.IOnNetworkHandle;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.util.color.Color;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class GameEntity implements IOnNetworkHandle {
	// Implement single ton
	private static GameEntity INSTANCE = null;

	protected GameEntity() {

	}

	public static GameEntity getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEntity();
		return INSTANCE;
	}

	public final String runableTextContent = "Maximum bet is 100 Z - You can click back button to open menu - You can Shake phone to start game";

	// Final static fields
	public final static int CAMERA_WIDTH = 800;
	public final static int CAMERA_HEIGHT = 480;

	public static String GAME_ID = "13757946146066432";

	public final static String SIGNIN_TASK = "login";
	public final static String SIGNUP_TASK = "signup";
	public final static String SIGNOUT_TASK = "logout";
	public final static String CHANGE_PASSWORD_TASK = "changepassword";
	public final static String FORGOT_PASSWORD_TASK = "resetpassword";

	public final static String STARTGAME_TASK = "bet";

	public final static String VIEW_HISTORY = "view_bet_history";
	public final static String VIEW_HISTORY_NEXT = "view_bet_history_next";
	public static final double REMAIN_FIXED = 10000;
	public final static int miniCoiWidth = 35;
	public final static int miniCoinHeight = 35;
	public SceneManager sceneManager;
	public boolean isResultDisplay = false;
	public boolean isAnimationRunning = false;

	public EngineOptions engineOptions;
	public GameComponent currentGame;

	// Connection field object
	public ConnectionHandler connectionHandler;
	public int currentCoint = CoinComponent.COINTID_1;
	public UserComponent userComponent;
	public double betAmountRemain = REMAIN_FIXED;
	public boolean isMusicEnable = true;
	public boolean isMenuDisplay = false;
	public boolean isBackPress = false;
	public boolean isOnAnimationScreen = false;

	public RouletteGameActivity rouletteGameActivity;

	// Enum
	public enum GameAction {
		BETING, REBET, RESET
	}

	public GameAction gameAction = GameAction.BETING;

	// Pattern ID
	public enum PatternType {
		straight_00("straight-00"), straight_0("straight-0"), straight_1(
				"straight-1"), straight_2("straight-2"), straight_3(
				"straight-3"), straight_4("straight-4"), straight_5(
				"straight-5"), straight_6("straight-6"), straight_7(
				"straight-7"), straight_8("straight-8"), straight_9(
				"straight-9"), straight_10("straight-10"), straight_11(
				"straight-11"), straight_12("straight-12"), straight_13(
				"straight-13"), straight_14("straight-14"), straight_15(
				"straight-15"), straight_16("straight-16"), straight_17(
				"straight-17"), straight_18("straight-18"), straight_19(
				"straight-19"), straight_20("straight-20"), straight_21(
				"straight-21"), straight_22("straight-22"), straight_23(
				"straight-23"), straight_24("straight-24"), straight_25(
				"straight-25"), straight_26("straight-26"), straight_27(
				"straight-27"), straight_28("straight-28"), straight_29(
				"straight-29"), straight_30("straight-30"), straight_31(
				"straight-31"), straight_32("straight-32"), straight_33(
				"straight-33"), straight_34("straight-34"), straight_35(
				"straight-35"), straight_36("straight-36"), split_0_00(
				"split-0-00"), split_0_1("split-0-1"), split_00_2("split-00-2"), split_0_2(
				"split-0-2"), split_00_3("split-00-3"), split_1_2("split-1-2"), split_1_4(
				"split-1-4"), split_2_3("split-2-3"), split_2_5("split-2-5"), split_3_6(
				"split-3-6"), split_4_5("split-4-5"), split_4_7("split-4-7"), split_5_6(
				"split-5-6"), split_5_8("split-5-8"), split_6_9("split-6-9"), split_7_8(
				"split-4-5"), split_7_10("split-7-10"), split_8_9("split-8-9"), split_8_11(
				"split-8-11"), split_9_12("split-9-12"), split_10_11(
				"split-10-11"), split_10_13("split-10-13"), split_11_12(
				"split-11-12"), split_11_14("split-11-14"), split_12_15(
				"split-12-15"), split_13_14("split-13-14"), split_13_16(
				"split-13-16"), split_14_15("split-14-15"), split_14_17(
				"split-14-17"), split_15_18("split-15-18"), split_16_17(
				"split-16-17"), split_16_19("split-16-19"), split_17_18(
				"split-17-18"), split_17_20("split-17-20"), split_18_21(
				"split-18-21"), split_19_20("split-19-20"), split_19_22(
				"split-19-22"), split_20_21("split-20-21"), split_20_23(
				"split-20-23"), split_21_24("split-21-24"), split_22_23(
				"split-22-23"), split_22_25("split-22-25"), split_23_24(
				"split-23-24"), split_23_26("split-23-26"), split_24_27(
				"split-24-27"), split_25_26("split-25-26"), split_25_28(
				"split-25-28"), split_26_27("split-26-27"), split_26_29(
				"split-26-29"), split_27_30("split-27-30"), split_28_29(
				"split-28-29"), split_28_31("split-28-31"), split_29_30(
				"split-29-30"), split_29_32("split-29-32"), split_30_33(
				"split-30-33"), split_31_32("split-31-32"), split_31_34(
				"split-31-34"), split_32_33("split-32-33"), split_32_35(
				"split-32-35"), split_33_36("split-33-36"), split_34_35(
				"split-34-35"), split_35_36("split-35-36"), basket_1("basket-1"), basket_2(
				"basket-2"), basket_3("basket-3"), street_1("street-1"), street_4(
				"street-4"), street_7("street-7"), street_10("street-10"), street_13(
				"street-13"), street_16("street-16"), street_19("street-19"), street_22(
				"street-22"), street_25("street-25"), street_28("street-28"), street_31(
				"street-31"), street_34("street-34"), corner_1("corner-1"), corner_2(
				"corner-2"), corner_4("corner-4"), corner_5("corner-5"), corner_7(
				"corner-7"), corner_8("corner-8"), corner_10("corner-10"), corner_11(
				"corner-11"), corner_13("corner-13"), corner_14("corner-14"), corner_16(
				"corner-16"), corner_17("corner-17"), corner_19("corner-19"), corner_20(
				"corner-20"), corner_22("corner-22"), corner_23("corner-23"), corner_25(
				"corner-25"), corner_26("corner-26"), corner_28("corner-28"), corner_29(
				"corner-29"), corner_31("corner-31"), corner_32("corner-32"), top_line(
				"top-line"), column_1("column-1"), column_2("column-2"), column_3(
				"column-3"), dozen_1("dozen-1"), dozen_2("dozen-2"), dozen_3(
				"dozen-3"), odd("odd"), even("even"), red("red"), black("black"), high(
				"high"), low("low"), line_1("six-line-1"), line_4("six-line-4"), line_7(
				"six-line-7"), line_10("six-line-10"), line_13("six-line-13"), line_16(
				"six-line-16"), line_19("six-line-19"), line_22("six-line-22"), line_25(
				"six-line-25"), line_28("six-line-28"), line_31("six-line-31");

		private final String value;

		private PatternType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static PatternType getPattern(String v_name) {
			PatternType[] arr = PatternType.values();
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].value.equals(v_name))
					return arr[i];
			}

			return null;
		}
	}

	// public ShakeEventListener mSensorListener;

	/**
	 * User acction
	 * 
	 * */

	/**
	 * Enable and disable music This variable will be reference in MSComponent
	 * when handle music or sound
	 */
	public void enableMusic() {
		if (!isMusicEnable) {
			isMusicEnable = true;
			sceneManager.gameScene.backgroundMusic.resume();
		} else {
			sceneManager.gameScene.backgroundMusic.pause();
			isMusicEnable = false;
		}

	}

	private Sprite currentHoverSprite = null;

	public void specifyDragOnItem(float X, float Y,
			TouchEvent pSceneTouchEvent, boolean isMove) {
		for (int i = 0; i < sceneManager.gameScene.patternList.size(); i++) {
			if (sceneManager.gameScene.patternList.get(i).getiItemType() == ItemType.TOUCHABLE_ITEM) {
				if ((sceneManager.gameScene.patternList.get(i).getPositionX() < X && X < (GameEntity
						.getInstance().sceneManager.gameScene.patternList
						.get(i).getPositionX() + sceneManager.gameScene.patternList
						.get(i).getiWidth()))
						&& (sceneManager.gameScene.patternList.get(i)
								.getPositionY() < Y && Y < (GameEntity
								.getInstance().sceneManager.gameScene.patternList
								.get(i).getPositionY() + GameEntity
								.getInstance().sceneManager.gameScene.patternList
								.get(i).getiHeight()))) {

					if (isMove) {
						if (currentHoverSprite != null)
							currentHoverSprite.setAlpha(0.1f);
						currentHoverSprite = sceneManager.gameScene.patternList
								.get(i).sprite;
						currentHoverSprite.setAlpha(0.5f);
						/*
						 * if (!isZoomimg) {
						 * sceneManager.camera.setZoomFactor(1.3f);
						 * sceneManager.camera.setCenter(340, 230); isZoomimg =
						 * true; }
						 */
					} else {
						sceneManager.gameScene.patternList.get(i).bet();
					}
				}
			}
		}
	}

	/**
	 * Bet function, when user click on a pattern, the method will be call
	 * called from click action in pattern
	 * 
	 * @param X
	 * @param Y
	 * @param pattern
	 */
	public void bet(float X, float Y, PatternComponent pattern) {
		/*
		 * if (isZoomimg) { sceneManager.camera.setZoomFactor(1.0f);
		 * sceneManager.camera.setCenterDirect(CAMERA_WIDTH / 2, CAMERA_HEIGHT /
		 * 2); isZoomimg = false; }
		 */

		if (sceneManager.gameScene.playAnimationComponent.showBackgroundResult == true)
			return;
		if (isAnimationRunning)
			return;
		if (currentHoverSprite != null)
			currentHoverSprite.setAlpha(0.1f);
		if (userComponent.balance.balance - currentCoint < 0) {
			if (!isMusicEnable)
				displayConfirmDialog("You do not enough money", 170, 200);
			else
				playVoiceCharacter(CharacterAction.NO_MORE_BET);
		} else if (betAmountRemain - currentCoint < 0) {
			if (!isMusicEnable)
				displayConfirmDialog("You can not bet over 100 zenny", 170, 200);
			else
				playVoiceCharacter(CharacterAction.NO_MORE_BET);
		} else {

			if (gameAction.equals(GameEntity.GameAction.RESET)) {
				// GameEntity.sceneManager.gameScene.coinList.clear();
				clearAllCoinList();
			}
			Y -= 5 * pattern.coinList.size();
			CoinComponent coin = new CoinComponent(currentCoint,
					GameEntity.miniCoiWidth, GameEntity.miniCoinHeight, "", X,
					Y, sceneManager.activity, currentCoint, pattern,
					ItemType.NORMAL_ITEM);
			pattern.scene.registerTouchArea(coin.sprite);
			pattern.scene.attachChild(coin.sprite);
			for (int i = 0; i < sceneManager.gameScene.textList.size(); i++) {
				if (sceneManager.gameScene.textList.get(i).getiID() == 1) {
					sceneManager.gameScene.textList.get(i).updateBalance(
							UserComponent.UserAction.DECREASE_BALANCE,
							currentCoint);
				} else if (sceneManager.gameScene.textList.get(i).getiID() == 3) {
					sceneManager.gameScene.textList.get(i).decreaseBetRemain(
							currentCoint);
				}
			}
			pattern.coinList.add(coin);
			gameAction = GameEntity.GameAction.BETING;
			if (sceneManager.gameScene.betSound != null)
				sceneManager.gameScene.betSound.play();
		}
	}

	/**
	 * Clear all coin list
	 */
	private void clearAllCoinList() {
		int listSize = sceneManager.gameScene.patternList.size();
		for (int i = 0; i < listSize; i++) {
			if (listSize > 0) {
				sceneManager.gameScene.patternList.get(i).coinList.clear();
			}
		}

	}

	public void buttonPlaySoudEffect() {
		sceneManager.gameScene.buttonPlaySound();
	}

	/**
	 * This method called when user click "clear" or "reset" button Called from
	 * Button component class - click action
	 */
	public void clearBet() {

		if (gameAction.equals(GameAction.REBET)
				|| gameAction.equals(GameAction.BETING)) {
			double amoutUpdate = 0;
			int patternListSize = sceneManager.gameScene.patternList.size();
			for (int j = 0; j < patternListSize; j++) {
				int coinListSize = sceneManager.gameScene.patternList.get(j).coinList
						.size();
				for (int i = 0; i < coinListSize; i++) {
					sceneManager.gameScene.patternList.get(j).coinList.get(i)
							.removeCoin();
					amoutUpdate += sceneManager.gameScene.patternList.get(j).coinList
							.get(i).getCoinID();
					sceneManager.gameScene.getScene().unregisterTouchArea(
							sceneManager.gameScene.patternList.get(j).coinList
									.get(i).sprite);
				}
			}
			int textListSize = sceneManager.gameScene.textList.size();
			for (int i = 0; i < textListSize; i++) {
				if (sceneManager.gameScene.textList.get(i).getiID() == 1) {
					sceneManager.gameScene.textList.get(i).updateBalance(
							UserComponent.UserAction.INCREASE_BALANCE,
							amoutUpdate);
				} else if (sceneManager.gameScene.textList.get(i).getiID() == 3) {
					sceneManager.gameScene.textList.get(i).updateBetRemain(
							REMAIN_FIXED);
				}
			}
			gameAction = GameEntity.GameAction.RESET;
		}
	}

	private double checkBalanceRebet() {
		double amoutUpdate = 0;
		int patternListSize = sceneManager.gameScene.patternList.size();
		for (int j = 0; j < patternListSize; j++) {
			int coinListSize = sceneManager.gameScene.patternList.get(j).coinList
					.size();
			for (int i = 0; i < coinListSize; i++) {
				amoutUpdate += sceneManager.gameScene.patternList.get(j).coinList
						.get(i).getCoinID();
			}
		}

		return amoutUpdate;
	}

	/**
	 * This method called when user click "rebet" button Called from Button
	 * component class - click action
	 */
	public void rebet() {
		if (gameAction.equals(GameEntity.GameAction.RESET)) {
			double amoutUpdate = checkBalanceRebet();
			if (userComponent.balance.balance - amoutUpdate < 0) {
				if (!isMusicEnable)
					displayConfirmDialog("You do not enough money", 170, 200);
				else
					playVoiceCharacter(CharacterAction.NO_MORE_BET);
			} else {
				int patternListSize = sceneManager.gameScene.patternList.size();
				for (int j = 0; j < patternListSize; j++) {
					int coinListSize = sceneManager.gameScene.patternList
							.get(j).coinList.size();
					for (int i = 0; i < coinListSize; i++) {
						sceneManager.gameScene.patternList.get(j).coinList.get(
								i).reBuildCoin();

						sceneManager.gameScene.getScene()
								.registerTouchArea(
										sceneManager.gameScene.patternList
												.get(j).coinList.get(i).sprite);
					}
				}
				int textListSize = sceneManager.gameScene.textList.size();
				for (int i = 0; i < textListSize; i++) {
					if (sceneManager.gameScene.textList.get(i).getiID() == 1) {
						sceneManager.gameScene.textList.get(i).updateBalance(
								UserComponent.UserAction.DECREASE_BALANCE,
								amoutUpdate);
					} else if (sceneManager.gameScene.textList.get(i).getiID() == 3) {
						sceneManager.gameScene.textList.get(i)
								.decreaseBetRemain(amoutUpdate);
					}
				}
				gameAction = GameEntity.GameAction.REBET;
			}

		}
	}

	/**
	 * This method will be call after user click next game button Called from
	 * button action click
	 */
	public void updateAfterBet() {
		// clearAllBet();
		int patternListSize = sceneManager.gameScene.patternList.size();
		for (int j = 0; j < patternListSize; j++) {
			int coinListSize = sceneManager.gameScene.patternList.get(j).coinList
					.size();
			for (int i = 0; i < coinListSize; i++) {

				sceneManager.gameScene.patternList.get(j).coinList.get(i)
						.removeCoin();
				sceneManager.gameScene.getScene().unregisterTouchArea(
						sceneManager.gameScene.patternList.get(j).coinList
								.get(i).sprite);
			}
		}

		int textListSize = sceneManager.gameScene.textList.size();
		for (int i = 0; i < textListSize; i++) {
			if (sceneManager.gameScene.textList.get(i).getiID() == 1) {
				sceneManager.gameScene.textList.get(i).updateBalance(
						UserComponent.UserAction.UPDATE_BALANCE,
						userComponent.getBalance());
			} else if (sceneManager.gameScene.textList.get(i).getiID() == 3) {
				sceneManager.gameScene.textList.get(i).updateBetRemain(
						GameEntity.REMAIN_FIXED);
			}
		}

		sceneManager.gameScene.betList.clear();
		gameAction = GameEntity.GameAction.RESET;
	}

	public static int random(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	private void playVoiceCharacter(CharacterComponent.CharacterAction action) {

		CharacterComponent charSay = sceneManager.gameScene.characterGirl;
		switch (action) {
		case PLEASE_PLAY_BET:
			charSay.say(6);
			break;
		case NO_MORE_BET:
			charSay.say(0);
			break;
		default:
			break;
		}
	}

	/**
	 * When user click start game or shake phone, this method will be call
	 * Called from Button action click and Shake phone method on game scene
	 */
	public void startGame() {
		boolean isBet = false;
		// mSensorListener.stopRegisterShake();
		int patternListSize = sceneManager.gameScene.patternList.size();
		for (int i = 0; i < patternListSize; i++) {
			if (sceneManager.gameScene.patternList.get(i).coinList.size() > 0
					&& !gameAction.equals(GameEntity.GameAction.RESET)) {
				int coinListSize = sceneManager.gameScene.patternList.get(i).coinList
						.size();
				for (int j = 0; j < coinListSize; j++) {
					sceneManager.gameScene.betList.add(new BetComponent(
							sceneManager.gameScene.patternList.get(i).coinList
									.get(j).pattern.patternType.getValue(),
							sceneManager.gameScene.patternList.get(i).coinList
									.get(j).getCoinID()));
				}
				isBet = true;
			}
		}

		if (!isBet) {
			if (!isMusicEnable)
				displayConfirmDialog("You must bet before start game", 170, 200);
			else
				playVoiceCharacter(CharacterAction.PLEASE_PLAY_BET);
		} else {
			sortBetList();
			networkHandler = new AsyncNetworkHandler();
			sceneManager.gameScene.disableAllTouch();
			int paramsSize = sceneManager.gameScene.betList.size();

			ArrayList<String> paramsName = new ArrayList<String>();
			ArrayList<Object> paramsValue = new ArrayList<Object>();

			for (int i = 0; i < paramsSize; i++) {
				paramsName.add("bet[]");
				paramsName.add("point[]");
			}

			for (int i = 0; i < paramsSize; i++) {
				paramsValue
						.add(sceneManager.gameScene.betList.get(i).betPatternID);
				paramsValue
						.add(sceneManager.gameScene.betList.get(i).betAmount);
			}

			paramsName.add("token");
			paramsName.add("game_id");

			paramsValue.add(userComponent.token);
			paramsValue.add(GAME_ID);

			Object[] params = { connectionHandler,
					sceneManager.gameScene.getActivity(),
					GameEntity.STARTGAME_TASK, paramsName, paramsValue, this,
					true };
			sceneManager.gameScene.disableAllTouch();
			// mSensorListener.stopRegisterShake();
			networkHandler.execute(params);
		}
	}

	private int sortBetList() {
		// TODO Auto-generated method stub
		for (int i = 0; i < sceneManager.gameScene.betList.size(); i++) {
			for (int j = 0; j < sceneManager.gameScene.betList.size(); j++) {
				if (sceneManager.gameScene.betList.get(i).betPatternID == sceneManager.gameScene.betList
						.get(j).betPatternID && i != j) {
					sceneManager.gameScene.betList.get(i).betAmount += sceneManager.gameScene.betList
							.get(j).betAmount;
					sceneManager.gameScene.betList.remove(j);
					j--;
				}
			}
		}
		return sceneManager.gameScene.betList.size();
	}

	/**
	 * This method will be call when user click view history button Called from
	 * Button Action click - button component class
	 */
	public void viewHistory() {
		// Go to Webview
		Intent intent = new Intent(sceneManager.activity, WebviewHelpPage.class);
		intent.putExtra("idButton", 4);
		sceneManager.activity.startActivity(intent);
	}

	/**
	 * Go to profile activity
	 */
	public void viewProfile() {
		/*
		 * Intent intent1 = new Intent(sceneManager.gameScene.getActivity(),
		 * ProfileActivity.class);
		 * 
		 * sceneManager.gameScene.getActivity().startActivity(intent1);
		 */
	}

	/**
	 * Go to help page activity
	 */
	public void viewHelp() {
		Intent intent1 = new Intent(sceneManager.gameScene.getActivity(),
				HelpActivity.class);
		sceneManager.gameScene.getActivity().startActivity(intent1);

	}

	private boolean isLogout = false;

	/**
	 * This method will be call when user click exit button Called from Button
	 * Action click - button component class
	 */
	public void exitGame() {
		isLogout = false;
		sceneManager.gameScene.unLoadScene();
		networkHandler = new AsyncNetworkHandler();
		Object[] params = { connectionHandler,
				sceneManager.gameScene.getActivity(), GameEntity.SIGNOUT_TASK,
				null, null, this, false };
		networkHandler.execute(params);
		betAmountRemain = GameEntity.REMAIN_FIXED;
	}

	AsyncNetworkHandler networkHandler;

	public void logout() {
		isLogout = true;
		clearBet();
		sceneManager.gameScene.unLoadScene();
		networkHandler = new AsyncNetworkHandler();
		Object[] params = { connectionHandler,
				sceneManager.gameScene.getActivity(), GameEntity.SIGNOUT_TASK,
				null, null, this, false };
		networkHandler.execute(params);

	}

	/**
	 * @author Admin this class is the portal to sent and receive request
	 *         response with server
	 */

	@Override
	public void onNetwokrHandle(JSONObject result,
			ConnectionHandler connectionHandler, Activity activity)
			throws JSONException {
		// TODO Auto-generated method stub
		if (connectionHandler.getTaskID().equals("res_bet")) {
			// move to animation scene
			if (result.getBoolean("success")) {
				onReceiveStartGame(result);
			} else {
				Log.d("Bet error", "Something wrong???");
				onSessionExpire();
			}

		} else if (connectionHandler.getTaskID().equals("res_log_out")) {
			onReceiveSignout();
		} else if (connectionHandler.getTaskID().equals("res_session_expire")) {
			onSessionExpire();
		}
	}

	/**
	 * Method called when session expire
	 */
	public void onSessionExpire() {
		
		sceneManager.activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(sceneManager.activity, "Your session is expired",
						Toast.LENGTH_LONG).show();
				isLogout = false;
				clearBet();
				sceneManager.gameScene.unLoadScene();
				onReceiveSignout();
			}
		});
		
		
	}

	/**
	 * @param result
	 * @throws JSONException
	 *             This method called when receive response game result from
	 *             Server
	 *             <p/>
	 *             {"credit":{"balance":417585,"win":1300},"pocket":"31","wins":
	 *             ["column-1","dozen-3","odd","black"]}
	 */
	public void onReceiveStartGame(JSONObject result) throws JSONException {
		JSONObject game = result.getJSONObject("result");

		boolean isWin = false;
		if (!game.getString("wins").equals("[]")) {
			isWin = true;
		}

		JSONObject point = game.getJSONObject("credit");

		int totalBetAmount = 0;
		for (int i = 0; i < sceneManager.gameScene.betList.size(); i++) {
			totalBetAmount += sceneManager.gameScene.betList.get(i).betAmount;
		}

		currentGame.setGame(isWin, game.getString("pocket"),
				point.getDouble("balance"), totalBetAmount,
				point.getDouble("win"), game.getString("wins"));
		userComponent.balance.balance = currentGame.newBalance;
		// sceneManager.setScene(SceneType.ANIMATION);
		sceneManager.gameScene.playAnimationComponent.playAnimation();
	}

	public void onReceiveSignout() {
		if (!isLogout) {
			sceneManager.activity.finish();
			sceneManager = null;
			System.exit(1);
		} else {
			Intent intent = new Intent(sceneManager.activity,
					SplashScreenAcivity.class);
			sceneManager.activity.startActivity(intent);
			sceneManager.activity.finish();
			sceneManager = null;

		}
	}

	// Dialog display
	// Error display
	public void displayYesNoDialog(String errorContent, int posX, int posY) {
		sceneManager.gameScene.yesnoDialog.displayDialog(
				sceneManager.gameScene, errorContent, posX, posY);
	}

	public void displayConfirmDialog(String errorContent, int posX, int posY) {
		sceneManager.gameScene.confirmDialog.displayDialog(
				sceneManager.gameScene, errorContent, posX, posY);
	}

	public void displayConfirmErrorDialog(String errorContent, int posX,
			int posY) {
		sceneManager.gameScene.confirmErrorDialog.displayDialog(
				sceneManager.gameScene, errorContent, posX, posY);
	}

	// test particle
	public void createFireWork(final float posX, final float posY,
			final int width, final int height, final Color color, int mNumPart,
			int mTimePart) {

		PointParticleEmitter particleEmitter = new PointParticleEmitter(posX,
				posY);

		IEntityFactory<Rectangle> recFact = new IEntityFactory<Rectangle>() {

			@Override
			public Rectangle create(float pX, float pY) {
				Rectangle rect = new Rectangle(posX, posY, 10, 10,
						sceneManager.activity.getVertexBufferObjectManager());
				rect.setColor(color);
				return rect;
			}

		};
		final ParticleSystem<Rectangle> particleSystem = new ParticleSystem<Rectangle>(
				recFact, particleEmitter, 500, 500, mNumPart);

		particleSystem
				.addParticleInitializer(new VelocityParticleInitializer<Rectangle>(
						-50, 50, -50, 50));
		/*
		 * particleSystem .addParticleInitializer(new
		 * ColorParticleInitializer<Rectangle>( color));
		 */
		particleSystem
				.addParticleModifier(new AlphaParticleModifier<Rectangle>(0,
						0.6f * mTimePart, 1, 0));
		particleSystem
				.addParticleModifier(new RotationParticleModifier<Rectangle>(0,
						mTimePart, 0, 360));

		sceneManager.gameScene.getScene().attachChild(particleSystem);
		sceneManager.gameScene.getScene().registerUpdateHandler(
				new TimerHandler(mTimePart, new ITimerCallback() {
					@Override
					public void onTimePassed(final TimerHandler pTimerHandler) {
						particleSystem.detachSelf();
						sceneManager.gameScene.getScene().sortChildren();
						sceneManager.gameScene.getScene()
								.unregisterUpdateHandler(pTimerHandler);
					}
				}));

	}

	@Override
	public void onNetworkError() {
		// TODO Auto-generated method stub

	}

}
