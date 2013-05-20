package kibow.games.casinohills.roulette.components;

import java.util.ArrayList;
import java.util.List;

import kibow.games.casinohills.roulette.components.AbItemComponent.ItemType;
import kibow.games.casinohills.roulette.screen.GameEntity;
import kibow.games.casinohills.roulette.screen.GameScene;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.color.Color;

import android.graphics.Typeface;

public class PlayAnimationComponent implements IAnimationListener {

	GameScene scene;
	// public ItemComponent background;
	/*
	 * 
	 * 
	 * // public ArrayList<AnimationComponent> animatedItemList;
	 */
	public List<TextComponent> displayTextList;
	ArrayList<RectangleLine> rectWinList;
	List<Integer> getListWinPatter;
	// LoopEntityModifier entityModifier;
	LoopEntityModifier rotateEntityModifier;
	public boolean showBackgroundResult = false;
	public WheelComponent rouletteWheel;
	public ItemComponent bullet;
	public AnimationComponent shootAnimation;

	public PlayAnimationComponent(GameScene scene) {
		this.scene = scene;
		rectWinList = new ArrayList<RectangleLine>();
		getListWinPatter = new ArrayList<Integer>();
		// createAlphaModifier();
		createRotateEntityModifier();
	}

	private void createRotateEntityModifier() {
		rotateEntityModifier = new LoopEntityModifier(new RotationModifier(4,
				0, 360));
	}

	/*
	 * void createAlphaModifier() { entityModifier = new LoopEntityModifier(new
	 * SequenceEntityModifier( new AlphaModifier(1, 1, 0), new AlphaModifier(1,
	 * 0, 1))); }
	 */

	/*
	 * public void unregisterModifier() { int size = getListWinPatter.size();
	 * for (int i = 0; i < size; i++) {
	 * scene.patternList.get(getListWinPatter.get(i)).sprite
	 * .unregisterEntityModifier(entityModifier); } getListWinPatter.clear(); }
	 */

	public void loadText() {
		displayTextList = new ArrayList<TextComponent>();
		Font mChangableFont = FontFactory.create(scene.getEngine()
				.getFontManager(), scene.getEngine().getTextureManager(), 512,
				512, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 25,
				Color.WHITE_ABGR_PACKED_INT);
		mChangableFont.load();
		Font smallFont = FontFactory.create(scene.getEngine().getFontManager(),
				scene.getEngine().getTextureManager(), 512, 512,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 20,
				Color.WHITE_ABGR_PACKED_INT);
		smallFont.load();
		Font bigFont = FontFactory.create(scene.getEngine().getFontManager(),
				scene.getEngine().getTextureManager(), 512, 512,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 60,
				Color.WHITE_ABGR_PACKED_INT);
		bigFont.load();
		// 300 50
		displayTextList.add(new TextComponent(1, 512, 512, "",
				-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT, scene
						.getActivity(), ItemType.TEXT, 1f, Color.BLACK,
				mChangableFont));
		// 200 100
		displayTextList.add(new TextComponent(2, 512, 512, "",
				-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT, scene
						.getActivity(), ItemType.TEXT, 1f, Color.BLACK,
				smallFont));
		// 200 150
		displayTextList.add(new TextComponent(3, 512, 512, "",
				-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT, scene
						.getActivity(), ItemType.TEXT, 1f, Color.BLACK,
				smallFont));

		// result
		displayTextList
				.add(new TextComponent(4, 512, 512, "",
						-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT,
						scene.getActivity(), ItemType.TEXT, 1f, Color.BLACK,
						bigFont));
	}

	public void loadResource() {
		// TODO Auto-generated method stub
		// Load background
		loadText();

		// Load animation component
		// animatedItemList = new ArrayList<AnimationComponent>();
		rouletteWheel = new WheelComponent(1, 373, 373, "wheel.png",
				-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT,
				scene.getActivity(), ItemType.NORMAL_ITEM);
		// rouletteWheel.startRotate(rotateEntityModifier);

		bullet = new ItemComponent(1, 16, 20, "bullet.png",
				-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT,
				scene.getActivity(), ItemType.NORMAL_ITEM);

		shootAnimation = new AnimationComponent(1, 3729, 236, 11, 1,
				"shootanimate.png", -GameEntity.CAMERA_WIDTH,
				-GameEntity.CAMERA_HEIGHT, scene.getActivity(),
				ItemType.NORMAL_ITEM);
	}

	public void loadAniamtionScene() {
		// attach all to background
		for (int j = 0; j < displayTextList.size(); j++) {
			scene.getScene().attachChild(displayTextList.get(j).text);
		}
		scene.getScene().attachChild(rouletteWheel.sprite);
		rouletteWheel.sprite.attachChild(bullet.sprite);
		scene.getScene().attachChild(shootAnimation.animatedSprite);
	}

	public void removeRectWin() {
		int rectWinSize = rectWinList.size();
		for (int i = 0; i < rectWinSize; i++) {
			rectWinList.get(i).removeRect();
		}
		rectWinList.clear();
	}

	public void resetEntityPosition() {
		int size1 = scene.buttonList.size();
		int coinDragSize = scene.dragList.size();

		for (int i = 0; i < scene.patternList.size(); i++) {
			scene.patternList.get(i).sprite.setPosition(scene.patternList
					.get(i).getPositionX(), scene.patternList.get(i)
					.getPositionY());

			for (int m = 0; m < scene.patternList.get(i).coinList.size(); m++) {
				scene.patternList.get(i).coinList.get(m).sprite
						.setPosition(scene.patternList.get(i).coinList.get(m)
								.getPositionX(),
								scene.patternList.get(i).coinList.get(m)
										.getPositionY());
			}
		}

		for (int j = 0; j < size1; j++) {
			scene.buttonList.get(j).tiledSprite.setPosition(scene.buttonList
					.get(j).getPositionX(), scene.buttonList.get(j)
					.getPositionY());
		}

		for (int n = 0; n < coinDragSize; n++) {
			scene.dragList.get(n).sprite.setPosition(scene.dragList.get(n)
					.getPositionX(), scene.dragList.get(n).getPositionY());
			scene.dragList.get(n).tempDrag.setPosition(scene.dragList.get(n)
					.getPositionX(), scene.dragList.get(n).getPositionY());
		}

		scene.hudItem.sprite.setPosition(scene.hudItem.getPositionX(),
				scene.hudItem.getPositionY());

		scene.coinBoard.sprite.setPosition(scene.coinBoard.getPositionX(),
				scene.coinBoard.getPositionY());
		scene.limitedText.sprite.setPosition(scene.limitedText.getPositionX(),
				scene.limitedText.getPositionY());

		// Move board
		scene.patternBackgroud.sprite.setPosition(
				scene.patternBackgroud.getPositionX(),
				scene.patternBackgroud.getPositionY());
	}

	void changeEntityPosition() {
		int patternListSize = scene.patternList.size();
		int buttonListSize = scene.buttonList.size();
		int coinDragSize = scene.dragList.size();

		for (int i = 0; i < patternListSize; i++)

		{
			scene.patternList.get(i).sprite.setPosition(scene.patternList
					.get(i).getPositionX(), scene.patternList.get(i)
					.getPositionY() + 100);

			for (int m = 0; m < scene.patternList.get(i).coinList.size(); m++) {
				scene.patternList.get(i).coinList.get(m).sprite
						.setPosition(scene.patternList.get(i).coinList.get(m)
								.getPositionX(),
								scene.patternList.get(i).coinList.get(m)
										.getPositionY() + 100);
			}
		}

		for (int j = 0; j < buttonListSize; j++) {
			if (j != 4 && j != 5) {
				scene.buttonList.get(j).tiledSprite.setPosition(
						scene.buttonList.get(j).tiledSprite.getX(),
						scene.buttonList.get(j).tiledSprite.getY() + 100);
			}
		}
		for (int n = 0; n < coinDragSize; n++) {
			scene.dragList.get(n).sprite
					.setPosition(scene.dragList.get(n).getPositionX(),
							scene.dragList.get(n).getPositionY() + 100);

			scene.dragList.get(n).tempDrag.setPosition(
					scene.dragList.get(n).tempDrag.getX(),
					scene.dragList.get(n).tempDrag.getY() + 100);
		}

		scene.hudItem.sprite.setPosition(scene.hudItem.getPositionX(),
				scene.hudItem.getPositionY() + 100);
		scene.coinBoard.sprite.setPosition(scene.coinBoard.getPositionX(),
				scene.coinBoard.getPositionY() + 200);
		scene.limitedText.sprite.setPosition(scene.limitedText.getPositionX(),
				scene.limitedText.getPositionY() + 200);

		// Move board
		scene.patternBackgroud.sprite.setPosition(
				scene.patternBackgroud.getPositionX(),
				scene.patternBackgroud.getPositionY() + 100);
	}

	public void playAnimation() {
		GameEntity.getInstance().isAnimationRunning = true;
		changeEntityPosition();
		// display wheel

		// updateCharacter();
		showBackgroundResult = true;
		shootAnimation.animatedSprite.setZIndex(99999);
		shootAnimation.animatedSprite.animate(100, 0, this);
		shootAnimation.animatedSprite.setPosition(230, 155);
		shootAnimation.animatedSprite.setScale(2.4f);
		scene.getScene().sortChildren();
		GameEntity.getInstance().sceneManager.gameScene.enableAllTouch();
		scene.getCamera().setZoomFactor(1f);
	}

	public void stopAnimation() {
		hideResultText();
		resetCharacter();

	}

	/*
	 * Update character front of pattern and make animation (change tile index)
	 */
	private void updateCharacter() {
		scene.characterGirl.changeSprite(CharacterComponent.CHAR_STATUS_WIN1);

		scene.characterGirl.tiledSprite.setZIndex(99999);
		scene.getScene().sortChildren();
	}

	/*
	 * Update character after get result
	 */

	private void updateCharacterAfterResult(boolean isWin) {
		int index = 0;
		if (isWin) {
			index = GameEntity.random(CharacterComponent.CHAR_STATUS_WIN1,
					CharacterComponent.CHAR_STATUS_WIN3);
		} else {
			index = GameEntity.random(CharacterComponent.CHAR_STATUS_LOSE1,
					CharacterComponent.CHAR_STATUS_LOSE2);
		}

		scene.characterGirl.changeSprite(index);
		scene.characterGirl.say(index);
	}

	/*
	 * Reset character to normal position and tile
	 */
	private void resetCharacter() {
		scene.characterGirl.changeSprite(CharacterComponent.CHAR_STATUS_NORMAL);

		scene.characterGirl.tiledSprite.setZIndex(-1);
		scene.background.sprite.setZIndex(-2);
		scene.getScene().sortChildren();
	}

	@Override
	public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
			int pInitialLoopCount) {
		// TODO Auto-generated method stub

		// diceEntity.setScale(0.8f);
		// diceEntity.setPosition(60, -80);
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
			int pOldFrameIndex, int pNewFrameIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
			int pRemainingLoopCount, int pInitialLoopCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		// TODO Auto-generated method stub
		shootAnimation.animatedSprite.setPosition(
				shootAnimation.getPositionX(), shootAnimation.getPositionY());
		displayResultText();

		scene.disableAllTouch();
	}

	private void hideResultText() {
		GameEntity.getInstance().isResultDisplay = false;

		for (int i = 0; i < displayTextList.size(); i++) {
			if (displayTextList.get(i).getiID() == 1) {
				displayTextList.get(i).text.setPosition(
						-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT);
			}
			if (displayTextList.get(i).getiID() == 2) {
				displayTextList.get(i).text.setPosition(
						-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT);
			}
			if (displayTextList.get(i).getiID() == 3) {
				displayTextList.get(i).text.setPosition(
						-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT);
			}
			if (displayTextList.get(i).getiID() == 4) {
				displayTextList.get(i).text.setPosition(
						-GameEntity.CAMERA_WIDTH, -GameEntity.CAMERA_HEIGHT);
			}
		}
		bullet.sprite.setPosition(bullet.getPositionX(), bullet.getPositionY());
		rouletteWheel.sprite.setPosition(rouletteWheel.getPositionX(),
				rouletteWheel.getPositionY());
		scene.characterGirl.stopSay();
		displayWinPatterns(false);
	}

	private void displayWinPatterns(boolean isDisplay) {
		// Display win pattern.

		for (int i = 0; i < scene.patternList.size(); i++) {
			for (int j = 0; j < GameEntity.getInstance().currentGame.winPatterns
					.size(); j++) {
				if (scene.patternList.get(i).patternType == GameEntity
						.getInstance().currentGame.winPatterns.get(j)) {
					if (isDisplay) {
						rectWinList.add(new RectangleLine(scene.getScene(),
								scene.patternList.get(i).sprite.getX(),
								scene.patternList.get(i).sprite.getY(),
								scene.patternList.get(i).sprite.getWidth(),
								scene.patternList.get(i).sprite.getHeight(),
								scene.getVertextBuffer));
						// scene.patternList.get(i).sprite
						// .registerEntityModifier(entityModifier);
						getListWinPatter.add(i);
					} else {
						// scene.patternList.get(i).sprite.setAlpha(1f);

					}

				}
			}
		}

	}

	private void displayWinInLosePatterns(boolean isDisplay) {
		// Display win pattern.
		int size = GameEntity.getInstance().currentGame.winPatterns.size();

		if (GameEntity.getInstance().currentGame.winPatterns.size() != 0) {
			for (int i = 0; i < scene.patternList.size(); i++) {
				for (int j = 0; j < size; j++) {
					if (scene.patternList.get(i).patternType == GameEntity
							.getInstance().currentGame.winPatterns.get(j)) {
						if (isDisplay) {
							rectWinList
									.add(new RectangleLine(scene.getScene(),
											scene.patternList.get(i).sprite
													.getX(), scene.patternList
													.get(i).sprite.getY(),
											scene.patternList.get(i).sprite
													.getWidth(),
											scene.patternList.get(i).sprite
													.getHeight(),
											scene.getVertextBuffer));
							// scene.patternList.get(i).sprite
							// .registerEntityModifier(entityModifier);
							getListWinPatter.add(i);

						} else {
							scene.patternList.get(i).sprite.setAlpha(1f);

						}

					}
				}
			}
		}
	}

	private void displayResultText() {
		/*
		 * rouletteWheel.sprite.setPosition(0, 0);
		 * rouletteWheel.sprite.setZIndex(99999);
		 * rouletteWheel.shootedByBullet(bullet);
		 * rouletteWheel.sprite.registerEntityModifier(new RotationModifier(4,
		 * 0, 360));
		 */
		/*
		 * try { Thread.sleep(1000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		// rouletteWheel.sprite.setPosition(rouletteWheel.getPositionX(),
		// rouletteWheel.getPositionY());
		scene.getCamera().setZoomFactor(1f);
		GameEntity.getInstance().isResultDisplay = true;
		// TODO Auto-generated method stub
		String resultString = "You lose";

		String totalBetString = "Total money you bet : "
				+ GameEntity.getInstance().currentGame.totalBetAmount / 100;
		String totalWinString = "Total money you win : "
				+ ((GameEntity.getInstance().currentGame.totalWinAmount > 0) ? GameEntity
						.getInstance().currentGame.totalWinAmount / 100 : 0);
		if (GameEntity.getInstance().currentGame.isWin) {
			// Win
			resultString = "You win";
			displayWinPatterns(true);
			// displayFireWork();
		} else {
			// Lose
			displayWinInLosePatterns(true);
		}
		updateCharacterAfterResult(GameEntity.getInstance().currentGame.isWin);

		for (int i = 0; i < displayTextList.size(); i++) {
			if (displayTextList.get(i).getiID() == 1) {
				displayTextList.get(i).text.setText(resultString);
				displayTextList.get(i).text.setPosition(400, 62);
			}
			if (displayTextList.get(i).getiID() == 2) {
				displayTextList.get(i).text.setText(totalBetString);
				displayTextList.get(i).text.setPosition(400, 90);
			}
			if (displayTextList.get(i).getiID() == 3) {
				displayTextList.get(i).text.setText(totalWinString);
				displayTextList.get(i).text.setPosition(400, 110);
			}
			if (displayTextList.get(i).getiID() == 4) {
				displayTextList.get(i).text
						.setText(GameEntity.getInstance().currentGame.pocket);
				displayTextList.get(i).text.setPosition(187, 80);
			}
		}
		GameEntity.getInstance().isAnimationRunning = false;
	}
}
