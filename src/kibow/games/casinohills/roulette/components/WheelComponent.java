package kibow.games.casinohills.roulette.components;

import java.util.ArrayList;

import kibow.games.casinohills.roulette.screen.GameEntity;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.ui.activity.BaseGameActivity;

public class WheelComponent extends ItemComponent {

    ArrayList<wheelPattern> bulletPos;

    public WheelComponent(int id, int width, int height, String background,
                          float positionX, float positionY, BaseGameActivity activity,
                          ItemType itemType) {
        super(id, width, height, background, positionX, positionY, activity,
                itemType);
        // TODO Auto-generated constructor stub
        loadBulletPost();
    }

    private void loadBulletPost() {
        bulletPos = new ArrayList<wheelPattern>();
        bulletPos.add(new wheelPattern("0", 170, 58));
        bulletPos.add(new wheelPattern("28", 190, 58));
        bulletPos.add(new wheelPattern("9", 208, 60));
        bulletPos.add(new wheelPattern("24", 277, 66));
        bulletPos.add(new wheelPattern("30", 245, 76));
        bulletPos.add(new wheelPattern("11", 259, 88));
        bulletPos.add(new wheelPattern("7", 275, 103));
        bulletPos.add(new wheelPattern("20", 283, 120));
        bulletPos.add(new wheelPattern("34", 294, 138));
        bulletPos.add(new wheelPattern("17", 299, 157));
        bulletPos.add(new wheelPattern("5", 297, 176));
        bulletPos.add(new wheelPattern("22", 297, 195));
        bulletPos.add(new wheelPattern("32", 294, 216));
        bulletPos.add(new wheelPattern("15", 286, 233));
        bulletPos.add(new wheelPattern("3", 275, 248));
        bulletPos.add(new wheelPattern("26", 259, 263));
        bulletPos.add(new wheelPattern("36", 245, 277));
        bulletPos.add(new wheelPattern("13", 227, 286));
        bulletPos.add(new wheelPattern("1", 208, 292));
        bulletPos.add(new wheelPattern("00", 189, 296));
        bulletPos.add(new wheelPattern("27", 171, 296));
        bulletPos.add(new wheelPattern("01", 150, 293));
        bulletPos.add(new wheelPattern("23", 131, 288));
        bulletPos.add(new wheelPattern("29", 114, 277));
        bulletPos.add(new wheelPattern("12", 99, 264));
        bulletPos.add(new wheelPattern("8", 84, 249));
        bulletPos.add(new wheelPattern("19", 74, 234));
        bulletPos.add(new wheelPattern("33", 66, 216));
        bulletPos.add(new wheelPattern("18", 61, 196));
        bulletPos.add(new wheelPattern("6", 59, 177));
        bulletPos.add(new wheelPattern("21", 91, 157));
        bulletPos.add(new wheelPattern("31", 66, 138));
        bulletPos.add(new wheelPattern("16", 72, 120));
        bulletPos.add(new wheelPattern("4", 82, 103));
        bulletPos.add(new wheelPattern("25", 99, 00));
        bulletPos.add(new wheelPattern("35", 114, 78));
        bulletPos.add(new wheelPattern("14", 134, 68));
        bulletPos.add(new wheelPattern("2", 150, 59));
    }

    public void shootedByBullet(ItemComponent bullet) {
        // this.sprite.attachChild(bullet.sprite);
        for (int i = 0; i < bulletPos.size(); i++) {
            if (GameEntity.getInstance().currentGame.pocket.equals(bulletPos
                    .get(i).pocketID)) {

                bullet.sprite.setPosition(bulletPos.get(i).x,
                        bulletPos.get(i).y);
            }
        }

    }

    public void startRotate(LoopEntityModifier rotateModifier) {
        this.sprite.registerEntityModifier(rotateModifier);
    }

    public void stopRotate(LoopEntityModifier rotateModifier) {
        this.sprite.unregisterEntityModifier(rotateModifier);
    }

    class wheelPattern {
        public wheelPattern(String pocketID, int x, int y) {
            // TODO Auto-generated constructor stub
            this.pocketID = pocketID;
            this.x = x;
            this.y = y;
        }

        int x;
        int y;
        String pocketID;
    }
}
