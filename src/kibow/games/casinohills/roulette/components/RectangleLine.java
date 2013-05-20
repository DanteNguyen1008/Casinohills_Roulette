package kibow.games.casinohills.roulette.components;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

public class RectangleLine {

    float x, y, width, height;
    VertexBufferObjectManager v;
    Scene scene;
    Line[] lRect;

    public RectangleLine(Scene scene, float x, float y, float width, float height, VertexBufferObjectManager v) {
        this.v = v;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scene = scene;
        lRect = new Line[4];
        drawRectangleLine(x, y, width, height);
    }

    public void drawRectangleLine(float x1, float y1, float width, float height) {
        float x2, y2, x3, y3, x4, y4;
        x2 = x1 + width;
        y2 = y1;
        x3 = x2;
        y3 = y1 + height;
        x4 = x1;
        y4 = y1 + height;

        lRect[0] = new Line(x1, y1, x2, y2, v);

        lRect[1] = new Line(x2, y2, x3, y3, v);
        lRect[2] = new Line(x3, y3, x4, y4, v);
        lRect[3] = new Line(x4, y4, x1, y1, v);
        for (int i = 0; i < 4; i++) {
            lRect[i].setColor(Color.RED);
            lRect[i].setLineWidth(3f);
            scene.attachChild(lRect[i]);
        }
    }

    public void removeRect() {
        for (int i = 0; i < 4; i++) {
            lRect[i].detachSelf();
            lRect[i].dispose();
            lRect[i] = null;
        }
    }

    public void animation() {

    }
}
