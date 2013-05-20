package kibow.games.casinohills.roulette.components;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.ui.activity.BaseGameActivity;

public class BarComponent extends AbItemComponent {
    public Rectangle bar;

    public BarComponent(int id, int width, int height, String background,
                        float positionX, float positionY, BaseGameActivity activity,
                        ItemType itemType) {
        super(id, width, height, background, positionX, positionY, activity,
                itemType);
        // TODO Auto-generated constructor stub
        bar = new Rectangle(positionX, positionY, 0, height,
                activity.getVertexBufferObjectManager());
    }

    public void updateBar(float percent) {
        bar.setWidth(getiWidth() * percent);
    }
}
