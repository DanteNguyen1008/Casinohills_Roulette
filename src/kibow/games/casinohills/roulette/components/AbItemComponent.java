package kibow.games.casinohills.roulette.components;

import org.andengine.ui.activity.BaseGameActivity;

public abstract class AbItemComponent {

    public AbItemComponent(int id, int width, int height, String background,
                           float positionX, float positionY, BaseGameActivity activity,
                           ItemType itemType) {
        setiID(id);
        setiWidth(width);
        setiHeight(height);
        setStrBackgroud(background);
        setPositionX(positionX);
        setPositionY(positionY);
        setActivity(activity);
        setiItemType(itemType);
    }

    private int iID;
    private String strBackgroud;
    private ItemType iItemType;

    public ItemType getiItemType() {
        return iItemType;
    }

    public void setiItemType(ItemType iItemType) {
        this.iItemType = iItemType;
    }

    public enum ItemType {
        NORMAL_ITEM, TOUCHABLE_ITEM, GRAG_ITEM, BUTTON_ROLL, BUTTON_CLEAR, BUTTON_REBET, BUTTON_HISTORY, BUTTON_NEXT, BUTTON_SOUND, BUTTON_EXIT, BUTTON_MENU, DICE_MIDDLE, DICE_LEFT, DICE_RIGHT, TEXT, YESNO_DIALOG, LOADING_DIALOG, CONFIRM_DIALOG, WIN_ANIMATION, LOSE_ANIMATION, DICE, MENU_RESUME, MENU_PROFILE, MENU_HELP, MENU_EXIT, MENU_LOGOUT, CONFIRM_ERROR, CHARACTER_BOY, CHARACTER_GIRL
    }

    public String getStrBackgroud() {
        return strBackgroud;
    }

    public void setStrBackgroud(String strBackgroud) {
        this.strBackgroud = strBackgroud;
    }

    public int getiID() {
        return iID;
    }

    public void setiID(int iID) {
        this.iID = iID;
    }

    public int getiWidth() {
        return iWidth;
    }

    public void setiWidth(int iWidth) {
        this.iWidth = iWidth;
    }

    public int getiHeight() {
        return iHeight;
    }

    public void setiHeight(int iHeight) {
        this.iHeight = iHeight;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    private int iWidth;
    private int iHeight;
    private float positionX;
    private float positionY;
    private BaseGameActivity activity;

    public BaseGameActivity getActivity() {
        return activity;
    }

    public void setActivity(BaseGameActivity activity) {
        this.activity = activity;
    }
}
