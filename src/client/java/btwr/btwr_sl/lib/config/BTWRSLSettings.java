package btwr.btwr_sl.lib.config;

public class BTWRSLSettings {
    protected boolean doHungerOffsetOverride = true;
    protected int renderXOffset = 0;
    protected int renderYOffset = 0;
    protected int renderMargin = 4;
    protected PenaltyDrawMode drawMode = PenaltyDrawMode.BTW;

    public boolean isHungerOffsetEnabled() {
        return doHungerOffsetOverride;
    }

    public int getRenderXOffset() {
        return renderXOffset;
    }

    public int getRenderYOffset() {
        return renderYOffset;
    }

    public int getRenderMargin() {
        return renderMargin;
    }

    public PenaltyDrawMode getDrawMode() {
        return drawMode;
    }
}
