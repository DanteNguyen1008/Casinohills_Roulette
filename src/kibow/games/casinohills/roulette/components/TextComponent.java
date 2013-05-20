package kibow.games.casinohills.roulette.components;

import kibow.games.casinohills.roulette.screen.GameEntity;

import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;


public class TextComponent extends AbItemComponent {
    public TextComponent(int id, int width, int height, String content,
                         float positionX, float positionY, BaseGameActivity activity,
                         ItemType itemType, float textSize, Color textColor, Font mFont) {
        super(id, width, height, "", positionX, positionY, activity, itemType);
        // mFont.load();
        text = new Text(positionX, positionY, mFont, content, 100,
                new TextOptions(HorizontalAlign.LEFT), activity.getEngine()
                .getVertexBufferObjectManager());
        //text.setColor(textColor);
        text.setScale(textSize);
    }

    public void updateText(String content) {
        text.setText(content);
    }

    public Text text;

    public void increaseBetRemain(double amount) {
        GameEntity.getInstance().betAmountRemain += amount;
        this.text.setText(GameEntity.getInstance().betAmountRemain / 100 + "");
    }

    public void decreaseBetRemain(double amount) {
        GameEntity.getInstance().betAmountRemain -= amount;
        this.text.setText(GameEntity.getInstance().betAmountRemain / 100 + "");
    }

    public void updateBetRemain(double amount) {
        GameEntity.getInstance().betAmountRemain = amount;
        this.text.setText(GameEntity.getInstance().betAmountRemain / 100 + "");
    }

    public void updateBalance(UserComponent.UserAction action, double amount) {
        this.text.setText(GameEntity.getInstance().userComponent.updateBalance(
                action, amount) / 100 + "");
    }

}
