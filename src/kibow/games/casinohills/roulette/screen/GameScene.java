package kibow.games.casinohills.roulette.screen;

import java.util.ArrayList;

import kibow.games.casinohills.roulette.acitivty.RouletteGameActivity;
import kibow.games.casinohills.roulette.components.AbItemComponent;
import kibow.games.casinohills.roulette.components.AbItemComponent.ItemType;
import kibow.games.casinohills.roulette.components.BetComponent;
import kibow.games.casinohills.roulette.components.ButtonComponent;
import kibow.games.casinohills.roulette.components.CharacterComponent;
import kibow.games.casinohills.roulette.components.CoinComponent;
import kibow.games.casinohills.roulette.components.DialogComponent;
import kibow.games.casinohills.roulette.components.DragComponent;
import kibow.games.casinohills.roulette.components.IOnItemClick;
import kibow.games.casinohills.roulette.components.ItemComponent;
import kibow.games.casinohills.roulette.components.MSComponent;
import kibow.games.casinohills.roulette.components.MSComponent.MStype;
import kibow.games.casinohills.roulette.components.MyMenuScene;
import kibow.games.casinohills.roulette.components.ParticleSystemComponent;
import kibow.games.casinohills.roulette.components.PatternComponent;
import kibow.games.casinohills.roulette.components.PlayAnimationComponent;
import kibow.games.casinohills.roulette.components.TextComponent;
import kibow.games.casinohills.roulette.screen.GameEntity.GameAction;

import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.PinchZoomDetector.IPinchZoomDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import android.graphics.Typeface;
import android.util.Log;

public class GameScene extends MyScene implements IOnSceneTouchListener,
		IOnItemClick {

	public ItemComponent background;
	public DialogComponent confirmDialog;
	public DialogComponent confirmErrorDialog;
	public DialogComponent yesnoDialog;
	public PlayAnimationComponent playAnimationComponent;

	// item list
	// public ArrayList<ItemComponent> itemList;
	public ArrayList<ButtonComponent> buttonList;
	public ArrayList<PatternComponent> patternList;
	public ArrayList<DragComponent> dragList;
	public ArrayList<TextComponent> textList;
	public ArrayList<BetComponent> betList;
	public ArrayList<ParticleSystemComponent> fireworkList;

	// Music and Sound
	public MSComponent backgroundMusic;
	public MSComponent betSound;
	public MSComponent releaseBetSound;
	public MSComponent winSound;
	public MSComponent loseSound;

	// DialogComponent loadingDialog;
	// Menu
	public MyMenuScene menuScene;

	// public TextComponent runableText;
	public VertexBufferObjectManager getVertextBuffer;

	// Character
	public CharacterComponent characterGirl;

	// HUD
	public ItemComponent hudItem;
	public ItemComponent hudTopItem;
	public ItemComponent coinBoard;
	public ItemComponent limitedText;

	// Pattern background
	public ItemComponent patternBackgroud;
	// Menu button
	public ButtonComponent btnMenu;

	public GameScene(Engine engine, ZoomCamera camera, BaseGameActivity activity) {
		super(engine, camera, activity);
		// TODO Auto-generated constructor stub
		playAnimationComponent = new PlayAnimationComponent(this);
		// itemList = new ArrayList<ItemComponent>();
		buttonList = new ArrayList<ButtonComponent>();
		patternList = new ArrayList<PatternComponent>();
		dragList = new ArrayList<DragComponent>();
		textList = new ArrayList<TextComponent>();
		betList = new ArrayList<BetComponent>();
		fireworkList = new ArrayList<ParticleSystemComponent>();
		getVertextBuffer = activity.getVertexBufferObjectManager();
		// GameEntity.getInstance().mSensorListener.setOnShakeListener(this);

	}

	// public MoveModifier move;
	// public LoopEntityModifier loopEntityModifier;

	private void loadCharacter() {
		characterGirl = new CharacterComponent(1, 888, 420, 6, 1,
				"char_sexy.png", 650, 50, getActivity(),
				ItemType.CHARACTER_GIRL);
	}

	private void loadDialog() {
		Font mFont = FontFactory.create(getEngine().getFontManager(),
				getEngine().getTextureManager(), 512, 512,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32,
				Color.WHITE_ABGR_PACKED_INT);
		mFont.load();
		// 0 0
		confirmDialog = new DialogComponent(1, 800, 480,
				"dialogbackground.png", -800, -480, getActivity(),
				ItemType.CONFIRM_DIALOG, 118, 38, "btnconfirm.png",
				GameEntity.CAMERA_WIDTH / 2 - 118 / 2, 300, mFont);

		confirmErrorDialog = new DialogComponent(1, 800, 480,
				"dialogbackground.png", -800, -480, getActivity(),
				ItemType.CONFIRM_ERROR, 118, 38, "btnconfirm.png",
				GameEntity.CAMERA_WIDTH / 2 - 118 / 2, 300, mFont);
		// 0 0
		yesnoDialog = new DialogComponent(2, 800, 480, "dialogbackground.png",
				-800, -480, getActivity(), ItemType.YESNO_DIALOG, 97, 38, 97,
				38, "btnyes.png", "btnno.png", "", 250, 350, 380, 350, mFont);
	}

	private void loadText() {
		Font mChangableFontSmall = FontFactory.create(getEngine()
				.getFontManager(), getEngine().getTextureManager(), 512, 512,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 17,
				Color.WHITE_ABGR_PACKED_INT);
		Font mChangableFontBig = FontFactory.create(getEngine()
				.getFontManager(), getEngine().getTextureManager(), 512, 512,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 24,
				Color.WHITE_ABGR_PACKED_INT);
		mChangableFontSmall.load();
		mChangableFontBig.load();
		textList.add(new TextComponent(1, 512, 512,
				GameEntity.getInstance().userComponent.getBalance() / 100 + "",
				154, 3, getActivity(), ItemType.TEXT, 1f, Color.WHITE,
				mChangableFontBig));
		textList.add(new TextComponent(3, 100, 23,
				GameEntity.getInstance().betAmountRemain / 100 + "", 585, 3,
				getActivity(), ItemType.TEXT, 1f, Color.WHITE,
				mChangableFontSmall));

		loadDialog();
	}

	private void loadMenuScene() {
		menuScene = new MyMenuScene(1, 800, 480, "dialogbackground.png", 0, 0,
				getActivity(), ItemType.NORMAL_ITEM);
		menuScene.addItem(new ButtonComponent(1, 200, 36, 1, 1, "resume.jpg",
				300, 50, getActivity(), ItemType.MENU_RESUME, menuScene));
		menuScene.addItem(new ButtonComponent(1, 200, 36, 1, 1, "help.jpg",
				300, 120, getActivity(), ItemType.MENU_HELP, menuScene));
		menuScene.addItem(new ButtonComponent(1, 200, 36, 1, 1, "logout.jpg",
				300, 190, getActivity(), ItemType.MENU_LOGOUT, menuScene));
		menuScene.addItem(new ButtonComponent(1, 200, 36, 1, 1, "exit.jpg",
				300, 260, getActivity(), ItemType.MENU_EXIT, menuScene));

	}

	private void loadMusicAndSound() {
		SoundFactory.setAssetBasePath("mfx/");
		MusicFactory.setAssetBasePath("mfx/");
		backgroundMusic = new MSComponent(1, "roulette_bg_music.mp3",
				MStype.MUSIC, getEngine(), getActivity(), true);
		betSound = new MSComponent(2, "betcoin.wav", MStype.SOUND, getEngine(),
				getActivity());
		releaseBetSound = new MSComponent(3, "pickcoin.mp3", MStype.SOUND,
				getEngine(), getActivity());

	}

	private void loadResourceItemList() {
		// background
		background = new ItemComponent(1, 800, 480, "bg.jpg", 0, 0,
				getActivity(), ItemType.NORMAL_ITEM);
		// HUD
		hudItem = (new ItemComponent(1, 800, 117, "hud.png", 0, 391,
				getActivity(), ItemType.NORMAL_ITEM));
		hudTopItem = (new ItemComponent(1, 800, 51, "top_hud.png", 0, 0,
				getActivity(), ItemType.NORMAL_ITEM));
		coinBoard = (new ItemComponent(1, 216, 107, "coin_board.png", 242, 373,
				getActivity(), ItemType.NORMAL_ITEM));
		limitedText = (new ItemComponent(1, 226, 49, "limit_text.png", 5, 420,
				getActivity(), ItemType.NORMAL_ITEM));

	}

	private void loadResourcePatternList() {
		// big small
		// Pattern
		patternBackgroud = new ItemComponent(1, 647, 354, "bet_board.png", 19,
				50, getActivity(), ItemType.NORMAL_ITEM);

		// The right colum
		patternList.add(new PatternComponent(2, 57, 71,
				"pt_top_right_coner.png", 603, 58, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.column_1));
		patternList.add(new PatternComponent(2, 57, 73,
				"pt_center_right_coner.png", 603, 130, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.column_2));
		patternList.add(new PatternComponent(2, 57, 73,
				"pt_bottom_right_coner.png", 603, 204, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.column_3));
		// The second colum
		patternList.add(new PatternComponent(2, 33, 52, "pt_colum2_big.png",
				569, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_36));
		patternList.add(new PatternComponent(2, 33, 41, "pt_colum2_2.png", 569,
				109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_35_36));
		patternList.add(new PatternComponent(2, 33, 33, "pt_colum2_3.png", 569,
				150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_35));
		patternList.add(new PatternComponent(2, 33, 41, "pt_colum2_4.png", 569,
				183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_34_35));
		patternList.add(new PatternComponent(2, 33, 33, "pt_colum2_5.png", 569,
				224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_34));
		patternList.add(new PatternComponent(2, 33, 29, "pt_colum2_6.png", 569,
				257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_34));

		// The first line - from second colum
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				549, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_33_36));
		patternList.add(new PatternComponent(2, 23, 51, "pt_line1_fat.png",
				526, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_33));
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				506, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_30_33));
		patternList.add(new PatternComponent(2, 23, 51, "pt_line1_fat.png",
				483, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_30));
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				463, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_27_30));
		patternList.add(new PatternComponent(2, 23, 51, "pt_line1_fat.png",
				440, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_27));
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				420, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_24_27));
		patternList.add(new PatternComponent(2, 23, 51, "pt_line1_fat.png",
				397, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_24));
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				377, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_21_24));
		patternList.add(new PatternComponent(2, 23, 51, "pt_line1_fat.png",
				354, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_21));
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				334, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_18_21));
		patternList.add(new PatternComponent(2, 23, 51, "pt_line1_fat.png",
				311, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_18));
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				291, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_15_18));
		patternList.add(new PatternComponent(2, 23, 51, "pt_line1_fat.png",
				268, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_15));
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				248, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_12_15));
		patternList.add(new PatternComponent(2, 23, 51, "pt_line1_fat.png",
				225, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_12));
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				205, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_9_12));
		patternList.add(new PatternComponent(2, 23, 51, "pt_line1_fat.png",
				182, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_9));
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				162, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_6_9));
		patternList.add(new PatternComponent(2, 23, 51, "pt_line1_fat.png",
				139, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_6));
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				119, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_3_6));
		patternList.add(new PatternComponent(2, 23, 51, "pt_line1_fat.png", 96,
				58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_3));

		// Second line - from second column
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				549, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_32));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				526, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_32_33));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				506, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_29));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				483, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_29_30));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				463, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_26));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				440, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_26_27));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				420, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_23));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				397, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_23_24));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				377, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_20));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				354, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_20_21));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				334, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_17));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				311, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_17_18));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				291, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_14));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				268, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_14_15));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				248, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_11));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				225, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_11_12));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				205, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_8));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				182, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_8_9));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				162, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_5));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				139, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_5_6));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				119, 109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_2));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png", 96,
				109, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_2_3));

		// 3rd line - from 2nd column
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				549, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_35));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				526, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_32_35));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				506, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_32));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				483, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_29_32));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				463, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_29));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				440, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_26_29));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				420, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_26));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				397, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_23_26));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				377, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_23));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				354, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_20_23));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				334, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_20));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				311, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_17_20));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				291, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_17));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				268, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_14_17));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				248, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_14));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				225, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_11_14));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				205, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_11));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				182, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_8_11));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				162, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_8));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				139, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_5_8));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				119, 150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_5));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png", 96,
				150, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_2_5));

		// 4st line
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				549, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_31));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				526, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_31_32));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				506, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_28));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				483, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_28_29));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				463, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_25));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				440, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_25_26));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				420, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_22));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				397, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_22_23));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				377, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_19));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				354, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_19_20));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				334, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_16));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				311, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_16_17));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				291, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_13));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				268, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_13_14));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				248, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_10));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				225, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_10_11));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				205, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_7));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				182, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_7_8));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				162, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_4));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png",
				139, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_4_5));
		patternList.add(new PatternComponent(2, 20, 41, "pt_line2_thin.png",
				119, 183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.corner_1));
		patternList.add(new PatternComponent(2, 23, 41, "pt_line2_fat.png", 96,
				183, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_1_2));

		// 5th line
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				549, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_35));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				526, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_32_35));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				506, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_32));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				483, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_29_32));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				463, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_29));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				440, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_26_29));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				420, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_26));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				397, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_23_26));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				377, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_23));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				354, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_20_23));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				334, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_20));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				311, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_17_20));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				291, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_17));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				268, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_14_17));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				248, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_14));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				225, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_11_14));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				205, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_11));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				182, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_8_11));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				162, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_8));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png",
				139, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_5_8));
		patternList.add(new PatternComponent(2, 20, 33, "pt_line3_thin.png",
				119, 224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_5));
		patternList.add(new PatternComponent(2, 23, 33, "pt_line3_fat.png", 96,
				224, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_2_5));

		// 6th line
		patternList.add(new PatternComponent(2, 20, 29, "pt_line6_thin.png",
				549, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.line_31));
		patternList.add(new PatternComponent(2, 23, 29, "pt_line6_fat.png",
				526, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_31));
		patternList.add(new PatternComponent(2, 20, 29, "pt_line6_thin.png",
				506, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.line_28));
		patternList.add(new PatternComponent(2, 23, 29, "pt_line6_fat.png",
				483, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_28));
		patternList.add(new PatternComponent(2, 20, 29, "pt_line6_thin.png",
				463, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.line_25));
		patternList.add(new PatternComponent(2, 23, 29, "pt_line6_fat.png",
				440, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_25));
		patternList.add(new PatternComponent(2, 20, 29, "pt_line6_thin.png",
				420, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.line_22));
		patternList.add(new PatternComponent(2, 23, 29, "pt_line6_fat.png",
				397, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_22));
		patternList.add(new PatternComponent(2, 20, 29, "pt_line6_thin.png",
				377, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.line_19));
		patternList.add(new PatternComponent(2, 23, 29, "pt_line6_fat.png",
				354, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_19));
		patternList.add(new PatternComponent(2, 20, 29, "pt_line6_thin.png",
				334, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.line_16));
		patternList.add(new PatternComponent(2, 23, 29, "pt_line6_fat.png",
				311, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_16));
		patternList.add(new PatternComponent(2, 20, 29, "pt_line6_thin.png",
				291, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.line_13));
		patternList.add(new PatternComponent(2, 23, 29, "pt_line6_fat.png",
				268, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_13));
		patternList.add(new PatternComponent(2, 20, 29, "pt_line6_thin.png",
				248, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.line_10));
		patternList.add(new PatternComponent(2, 23, 29, "pt_line6_fat.png",
				225, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_10));
		patternList.add(new PatternComponent(2, 20, 29, "pt_line6_thin.png",
				205, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.line_7));
		patternList.add(new PatternComponent(2, 23, 29, "pt_line6_fat.png",
				182, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_7));
		patternList.add(new PatternComponent(2, 20, 29, "pt_line6_thin.png",
				162, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.line_4));
		patternList.add(new PatternComponent(2, 23, 29, "pt_line6_fat.png",
				139, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_4));
		patternList.add(new PatternComponent(2, 20, 29, "pt_line6_thin.png",
				119, 257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.line_1));
		patternList.add(new PatternComponent(2, 23, 29, "pt_line6_fat.png", 96,
				257, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.street_1));

		// two final lines
		// line 1
		patternList.add(new PatternComponent(2, 174, 36, "pt_3_st_12.png", 430,
				286, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.dozen_3));
		patternList.add(new PatternComponent(2, 174, 36, "pt_2_st_12.png", 256,
				286, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.dozen_2));
		patternList.add(new PatternComponent(2, 175, 36, "pt_1_st_12.png", 81,
				286, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.dozen_1));

		// line 2
		patternList.add(new PatternComponent(2, 86, 45,
				"pt_final_line_colum1.png", 517, 322, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.high));
		patternList
				.add(new PatternComponent(2, 87, 45,
						"pt_final_line_colum2.png", 431, 322, getActivity(),
						ItemType.TOUCHABLE_ITEM, getScene(),
						GameEntity.PatternType.odd));
		patternList.add(new PatternComponent(2, 87, 45,
				"pt_final_line_colum3.png", 344, 322, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.black));
		patternList
				.add(new PatternComponent(2, 87, 45,
						"pt_final_line_colum4.png", 257, 322, getActivity(),
						ItemType.TOUCHABLE_ITEM, getScene(),
						GameEntity.PatternType.red));
		patternList.add(new PatternComponent(2, 87, 45,
				"pt_final_line_colum5.png", 170, 322, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.even));
		patternList
				.add(new PatternComponent(2, 88, 45,
						"pt_final_line_colum6.png", 82, 322, getActivity(),
						ItemType.TOUCHABLE_ITEM, getScene(),
						GameEntity.PatternType.low));

		// colum near final
		patternList.add(new PatternComponent(2, 20, 51, "pt_line1_thin.png",
				76, 58, getActivity(), ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_00_3));
		patternList.add(new PatternComponent(2, 21, 30,
				"pt_near_final_line2.png", 76, 109, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.basket_3));
		patternList.add(new PatternComponent(2, 21, 18,
				"pt_near_final_line3.png", 76, 139, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_00_2));
		patternList.add(new PatternComponent(2, 21, 20,
				"pt_near_final_line4.png", 76, 157, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.basket_2));
		patternList.add(new PatternComponent(2, 21, 18,
				"pt_near_final_line3.png", 76, 177, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_00_2));
		patternList.add(new PatternComponent(2, 21, 30,
				"pt_near_final_line2.png", 76, 195, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.basket_3));
		patternList.add(new PatternComponent(2, 21, 30,
				"pt_near_final_line6.png", 76, 225, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.basket_1));
		patternList.add(new PatternComponent(2, 21, 29,
				"pt_near_final_line_final.png", 76, 255, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.split_0_1));

		// Final column
		patternList.add(new PatternComponent(2, 46, 92,
				"pt_top_left_coner.png", 30, 58, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_00));
		patternList.add(new PatternComponent(2, 25, 36,
				"pt_top_center_coner.png", 51, 150, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_00));
		patternList.add(new PatternComponent(2, 45, 91,
				"pt_top_bottom_coner.png", 31, 186, getActivity(),
				ItemType.TOUCHABLE_ITEM, getScene(),
				GameEntity.PatternType.straight_00));
	}

	private void loadResourceDragList() {
		dragList.add(new DragComponent(1, 35, 35, "icon_coin_1.png", 253, 440,
				getActivity(), ItemType.GRAG_ITEM, getScene(),
				CoinComponent.COINTID_1));
		dragList.add(new DragComponent(2, 35, 35, "icon_coin_5.png", 275, 406,
				getActivity(), ItemType.GRAG_ITEM, getScene(),
				CoinComponent.COINTID_5));
		dragList.add(new DragComponent(3, 35, 35, "icon_coin_10.png", 313, 383,
				getActivity(), ItemType.GRAG_ITEM, getScene(),
				CoinComponent.COINTID_10));
		dragList.add(new DragComponent(4, 35, 35, "icon_coin_25.png", 357, 383,
				getActivity(), ItemType.GRAG_ITEM, getScene(),
				CoinComponent.COINTID_25));
		dragList.add(new DragComponent(5, 35, 35, "icon_coin_50.png", 392, 405,
				getActivity(), ItemType.GRAG_ITEM, getScene(),
				CoinComponent.COINTID_50));
		dragList.add(new DragComponent(6, 35, 35, "icon_coin_100.png", 417,
				442, getActivity(), ItemType.GRAG_ITEM, getScene(),
				CoinComponent.COINTID_100));
	}

	private void loadResourceButtonList() {
		// Button - Bottom

		buttonList.add(new ButtonComponent(59, 127, 75, 1, 1, "btn_start.png",
				515, 395, getActivity(), ItemType.BUTTON_ROLL, this));
		buttonList.add(new ButtonComponent(59, 90, 30, 1, 1, "btn_rebet.png",
				415, 382, getActivity(), ItemType.BUTTON_REBET, this));
		buttonList.add(new ButtonComponent(59, 74, 31, 1, 1, "btn_reset.png",
				445, 416, getActivity(), ItemType.BUTTON_CLEAR, this));
		buttonList.add(new ButtonComponent(59, 59, 29, 1, 1, "btn_exit.png",
				462, 450, getActivity(), ItemType.BUTTON_EXIT, this));
		buttonList.add(new ButtonComponent(59, 71, 25, 1, 1, "btn_history.png",
				680, 7, getActivity(), ItemType.BUTTON_HISTORY, this));

		// Button - top
		buttonList.add(new ButtonComponent(59, 106, 27, 4, 1, "volume.png",
				760, 7, getActivity(), ItemType.BUTTON_SOUND, this));

		btnMenu = new ButtonComponent(59, 55, 55, 1, 1, "menu_icon.png", 650,
				344, getActivity(), ItemType.BUTTON_MENU, this);

	}

	public void loadFireworkResource() {
		fireworkList.add(new ParticleSystemComponent(1, 32, 32,
				"particle_point.png", -800, -480, getActivity(),
				ItemType.NORMAL_ITEM, Color.RED, 20, 2, getScene()));
	}

	@Override
	public void loadResource() {
		// TODO Auto-generated method stub
		getScene().registerUpdateHandler(new FPSLogger());
		// getScene().setBackground(new Background(1f, 1f, 1f));
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.2f);
		getScene().setBackgroundEnabled(false);
		getScene().setOnAreaTouchTraversalFrontToBack();

		loadResourceItemList();
		GameEntity.getInstance().sceneManager.loadingScene.percentText
				.updateText("30%");
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.3f);
		loadResourceDragList();
		GameEntity.getInstance().sceneManager.loadingScene.percentText
				.updateText("33%");
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.33f);
		loadResourcePatternList();
		GameEntity.getInstance().sceneManager.loadingScene.percentText
				.updateText("35%");
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.35f);
		loadResourceButtonList();
		GameEntity.getInstance().sceneManager.loadingScene.percentText
				.updateText("40%");
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.4f);
		loadText();
		GameEntity.getInstance().sceneManager.loadingScene.percentText
				.updateText("45%");
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.45f);
		loadMusicAndSound();
		GameEntity.getInstance().sceneManager.loadingScene.percentText
				.updateText("60%");
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.6f);
		playAnimationComponent.loadResource();
		GameEntity.getInstance().sceneManager.loadingScene.percentText
				.updateText("70%");
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.7f);
		loadMenuScene();
		GameEntity.getInstance().sceneManager.loadingScene.percentText
				.updateText("75%");
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.75f);
		// loadRunableText();
		loadCharacter();
	}

	@Override
	public void loadScene() {
		// TODO Auto-generated method stub
		// load background
		GameEntity.getInstance().sceneManager.loadingScene.percentText
				.updateText("80%");
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.8f);

		getScene().attachChild(background.sprite);

		// add character before pattern
		getScene().attachChild(characterGirl.tiledSprite);

		// load pattern background
		getScene().attachChild(patternBackgroud.sprite);

		// load Game pattern

		for (int i = 0; i < patternList.size(); i++) {
			// itemList.get(i).setScale(1.5f);
			getScene().registerTouchArea(patternList.get(i).sprite);
			getScene().attachChild(patternList.get(i).sprite);
		}
		GameEntity.getInstance().sceneManager.loadingScene.percentText
				.updateText("90%");
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.9f);
		
		//attach menu button
		getScene().attachChild(btnMenu.tiledSprite);
		getScene().registerTouchArea(btnMenu.tiledSprite);

		getScene().attachChild(hudItem.sprite);
		getScene().attachChild(hudTopItem.sprite);
		getScene().attachChild(coinBoard.sprite);
		getScene().attachChild(limitedText.sprite);
		// load game coin drag
		for (int i = 0; i < dragList.size(); i++) {
			getScene().registerTouchArea(dragList.get(i).sprite);
			getScene().attachChild(dragList.get(i).sprite);
			getScene().attachChild(dragList.get(i).tempDrag);
		}

		// load button
		for (int i = 0; i < buttonList.size(); i++) {
			// itemList.get(i).setScale(1.5f);
			getScene().registerTouchArea(buttonList.get(i).tiledSprite);
			getScene().attachChild(buttonList.get(i).tiledSprite);
		}

		// LoadText

		for (int i = 0; i < textList.size(); i++) {
			getScene().attachChild(textList.get(i).text);
		}

		// load animation
		playAnimationComponent.loadAniamtionScene();

		// load Dialog
		getScene().attachChild(confirmDialog.sprite);
		getScene().attachChild(confirmErrorDialog.sprite);
		getScene().attachChild(yesnoDialog.sprite);

		// getScene().setTouchAreaBindingOnActionMoveEnabled(true);
		getScene().setTouchAreaBindingOnActionDownEnabled(true);

		// add partical
		// getScene().attachChild(particleSystem);
		// menuScene.attachMenu(getScene());

		getScene().attachChild(menuScene.sprite);

		menuScene.registerTouch(getScene());
		// getScene().attachChild(runableText.text);
		GameEntity.getInstance().sceneManager.loadingScene.percentText
				.updateText("95%");
		GameEntity.getInstance().sceneManager.loadingScene.bar.updateBar(0.95f);

		getScene().setOnSceneTouchListener(this);

	}

	@Override
	public void disableAllTouch() {
		for (int i = 0; i < patternList.size(); i++) {
			getScene().unregisterTouchArea(patternList.get(i).sprite);
			for (int j = 0; j < patternList.get(i).coinList.size(); j++) {
				getScene().unregisterTouchArea(
						patternList.get(i).coinList.get(j).sprite);
			}
		}
		for (int i = 0; i < dragList.size(); i++) {
			getScene().unregisterTouchArea(dragList.get(i).sprite);
		}
		for (int i = 0; i < buttonList.size(); i++) {
			getScene().unregisterTouchArea(buttonList.get(i).tiledSprite);
		}

	}

	@Override
	public void enableAllTouch() {

		for (int i = 0; i < patternList.size(); i++) {
			getScene().registerTouchArea(patternList.get(i).sprite);
			if (GameEntity.getInstance().gameAction != GameAction.RESET) {
				for (int j = 0; j < patternList.get(i).coinList.size(); j++) {
					getScene().registerTouchArea(
							patternList.get(i).coinList.get(j).sprite);
				}
			}

		}

		for (int i = 0; i < dragList.size(); i++) {
			getScene().registerTouchArea(dragList.get(i).sprite);
		}
		for (int i = 0; i < buttonList.size(); i++) {
			getScene().registerTouchArea(buttonList.get(i).tiledSprite);
		}
	}

	@Override
	public void unLoadScene() {
		// TODO Auto-generated method stub
		if (!backgroundMusic.music.isReleased())
			backgroundMusic.music.release();
		getScene().detachChildren();
	}

	public void buttonPlaySound() {
		if (releaseBetSound != null)
			releaseBetSound.play();
	}

	public void onBackButtonPress(boolean isDisplay) {
		if (isDisplay) {
			GameEntity.getInstance().displayYesNoDialog("Do you want to exit?",
					200, 300);
			// GameEntity.getInstance().mSensorListener.stopRegisterShake();
			GameEntity.getInstance().isBackPress = true;
		} else {
			// GameEntity.getInstance().mSensorListener.registerShake();
			GameEntity.getInstance().isBackPress = false;
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		GameEntity.getInstance().rouletteGameActivity.mPinchZoomDetector
				.onTouchEvent(pSceneTouchEvent);

		if (GameEntity.getInstance().rouletteGameActivity.mPinchZoomDetector
				.isZooming()) {
			GameEntity.getInstance().rouletteGameActivity.mScrollDetector
					.setEnabled(false);
		} else {
			if (pSceneTouchEvent.isActionDown()) {
				GameEntity.getInstance().rouletteGameActivity.mScrollDetector
						.setEnabled(true);
			}
			GameEntity.getInstance().rouletteGameActivity.mScrollDetector
					.onTouchEvent(pSceneTouchEvent);
		}
		if (!GameEntity.getInstance().isAnimationRunning) {
			if (pSceneTouchEvent.isActionUp()) {
				if (GameEntity.getInstance().sceneManager.gameScene.playAnimationComponent.showBackgroundResult == true) {
					GameEntity.getInstance().sceneManager.gameScene
							.buttonPlaySound();
					GameEntity.getInstance().updateAfterBet();
					GameEntity.getInstance().sceneManager.gameScene.playAnimationComponent
							.stopAnimation();
					GameEntity.getInstance().sceneManager.gameScene.playAnimationComponent
							.removeRectWin();
					/*
					 * GameEntity.getInstance().sceneManager.gameScene.
					 * playAnimationComponent .unregisterModifier();
					 */
					GameEntity.getInstance().sceneManager.gameScene.playAnimationComponent
							.resetEntityPosition();
					GameEntity.getInstance().sceneManager.gameScene.playAnimationComponent.showBackgroundResult = false;
					this.enableAllTouch();
					getCamera().setZoomFactor(1f);
				}
			}
		}

		return false;

	}

	@Override
	public void onClick(AbItemComponent component) {
		// TODO Auto-generated method stub
		ButtonComponent button = (ButtonComponent) component;
		buttonPlaySound();
		switch (button.getiItemType()) {
		case BUTTON_ROLL:
			GameEntity.getInstance().startGame();
			break;
		case BUTTON_CLEAR:
			GameEntity.getInstance().clearBet();
			break;
		case BUTTON_REBET:
			GameEntity.getInstance().rebet();
			break;
		case BUTTON_HISTORY:
			GameEntity.getInstance().viewHistory();
			break;
		case BUTTON_MENU:
			menuScene.displayMenu();
			break;
		/*
		 * case BUTTON_NEXT: GameEntity.getInstance().updateAfterBet();
		 * playAnimationComponent .stopAnimation(); enableAllTouch(); break;
		 */
		case BUTTON_SOUND:
			GameEntity.getInstance().enableMusic();
			if (GameEntity.getInstance().isMusicEnable)
				button.tiledSprite.setCurrentTileIndex(0);
			else
				button.tiledSprite.setCurrentTileIndex(2);
			break;
		case BUTTON_EXIT:
			yesnoDialog.displayDialog(
					GameEntity.getInstance().sceneManager.gameScene,
					"Do you want to exit?", 200, 300);
			break;
		default:
			break;
		}
	}

}
