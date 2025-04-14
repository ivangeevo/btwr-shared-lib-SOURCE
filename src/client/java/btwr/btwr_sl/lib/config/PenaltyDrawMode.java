package btwr.btwr_sl.lib.config;

import net.minecraft.client.gui.DrawContext;

import java.util.Objects;

import static btwr.btwr_sl.lib.gui.PenaltyDisplayManager.RIGHT;
import static btwr.btwr_sl.lib.gui.PenaltyDisplayManager.LEFT;
import static btwr.btwr_sl.lib.gui.PenaltyDisplayManager.BOTTOM;
import static btwr.btwr_sl.lib.gui.PenaltyDisplayManager.TOP;

/**
 * Holds translation key and x + y offset for penalties
 */

public enum PenaltyDrawMode {
    BTW(),
    BOTTOM_RIGHT(RIGHT, BOTTOM),
    TOP_RIGHT(RIGHT, TOP),
    BOTTOM_LEFT(LEFT, BOTTOM),
    TOP_LEFT(LEFT, TOP);

    private final byte sideX;
    private final byte sideY;

    PenaltyDrawMode(byte sideX, byte sideY) {
        this.sideX = sideX;
        this.sideY = sideY;
    }

    PenaltyDrawMode() {
        this.sideX = 0x0;
        this.sideY = 0x0;
    }

    public int getX(DrawContext context) {
        return context.getScaledWindowWidth() * this.sideX; //+ (-4 * (-1 * this.sideX));
    }

    public int getY(DrawContext context) {
        return context.getScaledWindowHeight() * this.sideY - (!this.shouldFlipY() ? 10 : 0);// + (-4 * (-1 * this.sideY));
    }

    public int getXMargin(int margin) {
        return margin * (1 + -2 * this.sideX);
    }

    public int getYMargin(int margin) {
        return margin * (1 + -2 * this.sideY);
    }

    public boolean shouldAddX() {
        return this.sideX == RIGHT;
    }

    public boolean shouldFlipY() {
        return this.sideY == TOP;
    }

    public boolean isAnchor() {
        return !name().equals("BTW");
    }
}
