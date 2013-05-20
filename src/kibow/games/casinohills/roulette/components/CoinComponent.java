package kibow.games.casinohills.roulette.components;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class CoinComponent extends AbItemComponent {
    public static final int COINTID_1 = 100;
    public static final int COINTID_5 = 500;
    public static final int COINTID_10 = 1000;
    public static final int COINTID_25 = 2500;
    public static final int COINTID_50 = 5000;
    public static final int COINTID_100 = 10000;

    public static final String cointName_1 = "icon_coin_1.png";
    public static final String cointName_5 = "icon_coin_5.png";
    public static final String cointName_10 = "icon_coin_10.png";
    public static final String cointName_25 = "icon_coin_25.png";
    public static final String cointName_50 = "icon_coin_50.png";
    public static final String cointName_100 = "icon_coin_100.png";

    CoinSprite coin;
    // public int patternID;
    public PatternComponent pattern;

    public Sprite sprite;

    public CoinComponent(int id, int width, int height, String background,
                         float positionX, float positionY, BaseGameActivity activity,
                         int coinID, PatternComponent pattern, ItemType itemType) {
        super(id, width, height, background, positionX, positionY, activity,
                itemType);
        this.CoinID = coinID;
        this.pattern = pattern;
        String currentBackground = "";
        switch (this.CoinID) {
            case CoinComponent.COINTID_1:
                currentBackground = CoinComponent.cointName_1;
                break;
            case CoinComponent.COINTID_5:
                currentBackground = CoinComponent.cointName_5;
                break;
            case CoinComponent.COINTID_10:
                currentBackground = CoinComponent.cointName_10;
                break;
            case CoinComponent.COINTID_25:
                currentBackground = CoinComponent.cointName_25;
                break;
            case CoinComponent.COINTID_50:
                currentBackground = CoinComponent.cointName_50;
                break;
            case CoinComponent.COINTID_100:
                currentBackground = CoinComponent.cointName_100;
                break;
            default:
                break;
        }

        BitmapTextureAtlas atlastBig = new BitmapTextureAtlas(
                activity.getTextureManager(), width, height,
                TextureOptions.BILINEAR);

        ITextureRegion atlasRegionBig = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(atlastBig, activity, currentBackground, 0, 0);

        coin = new CoinSprite(positionX, positionY, width, height, coinID,
                atlasRegionBig, activity.getEngine()
                .getVertexBufferObjectManager(), this);
        sprite = (coin.obtainPoolItem());
        sprite.setScale(0.7f);
        atlastBig.load();
    }

    private int CoinID;

    public int getCoinID() {
        return CoinID;
    }

    public void setCoinID(int coinID) {
        CoinID = coinID;
    }

    public void removeCoin() {
        // coin.recyclePoolItem(getSprite());
        coin.onHandleRecycleItem(sprite);
        // pattern.sortCoinList(this);
        // pattern.coinList.remove(this);
    }

    public void reBuildCoin() {
        coin.onHandleObtainItem(sprite);
    }

    public void deleteItSeft() {
        pattern.sortCoinList(this);

    }
}
