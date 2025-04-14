package btwr.btwr_sl.lib.gui;

import btwr.btwr_sl.BTWRSLMod;
import btwr.btwr_sl.lib.client.BTWRSLModClient;
import btwr.btwr_sl.lib.config.BTWRSLSettings;
import btwr.btwr_sl.lib.config.PenaltyDrawMode;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.TreeMap;

/**
 * Handles rendering of penalties, and their hierarchy
 */
public class PenaltyDisplayManager {
    /**
     * Instance of PenaltyDisplayManager
     */
    private static final PenaltyDisplayManager INSTANCE = new PenaltyDisplayManager();

    public static final int HEALTH_PRIORITY = 10;
    public static final int GLOOM_PRIORITY = 20;
    public static final int HUNGER_PRIORITY = 30;

    public static final byte RIGHT = 0x1;
    public static final byte LEFT = 0x0;
    public static final byte BOTTOM = 0x1;
    public static final byte TOP = 0x0;

    /**
     * List of current penalties to be rendered
     */
    private static TreeMap<Integer,Penalty> penalties = new TreeMap<>();

    /**
     * Indicates if the hunger bar is currently rendered
     */
    private static boolean isRenderingFood = false;

    public static PenaltyDisplayManager getInstance() {
        return INSTANCE;
    }

    /**
     * Lambda condition test that determines rendering
     */
    @FunctionalInterface
    public interface PenaltyCondition {
        boolean test();
    }

    /**
     * Lambda string getter that determines text drawn
     */
    @FunctionalInterface
    public interface PenaltyString {
        String get();
    }

    /**
     * Stores information on how and when to draw a penalty
     */
    static public class Penalty {
        /**
         * Determines rendering order, with lower values being closer to the hotbar
         */
        protected int priority;
        /**
         * String that will be rendered
         */
        protected PenaltyString display;
        /**
         * Condition that must pass in order for text to render
         */
        protected PenaltyCondition condition;

        /**
         * Creates a penalty with variable text and conditions
         * @param priority Order this penalty will be drawn
         * @param display Variable text to draw
         * @param condition Variable condition that must pass for this penalty to draw
         */
        public Penalty(int priority, PenaltyString display, PenaltyCondition condition) {
            this.priority = priority;
            this.display = display;
            this.condition = condition;
        }

        /**
         * Creates a penalty with a static string and variable conditions
         * @param priority Order this penalty will be drawn
         * @param string Text to draw
         * @param condition Variable condition that must pass for this penalty to draw
         */
        public Penalty(int priority, String string, PenaltyCondition condition) {
            this.priority = priority;
            this.display = () -> string;
            this.condition = condition;
        }

        /**
         * Creates a penalty with a variable string that will always draw
         * @param priority Order this penalty will be drawn
         * @param display Variable text to draw
         */
        public Penalty(int priority, PenaltyString display) {
            this.priority = priority;
            this.display = display;
            this.condition = () -> true;
        }

        /**
         * Gets the priority level for this penalty
         */
        public int getPriority() {
            return this.priority;
        }

        /**
         * Gets current displayed text for this penalty
         */
        public String getDisplay() {
            return this.display.get();
        }
    }

    public void render(DrawContext context, TextRenderer renderer) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        this.renderPenalties(context, renderer, player);
    }

    /**
     * Draws penalties (if applicable) onto provided context
     * @param context DrawContext to draw to
     * @param renderer TextRenderer to use for drawing text
     * @param player PlayerEntity to get information from
     */
    private void renderPenalties(DrawContext context, TextRenderer renderer, PlayerEntity player) {
        // Determine if draw should be diverted
        if (getDrawMode().isAnchor()) {
            renderPenaltiesAnchored(context, renderer, player);
            return;
        }

        // Calculate the position of the hunger bar
        int hungerBarX = context.getScaledWindowWidth() / 2 + 91;  // Center of the hunger bar
        int hungerBarY = context.getScaledWindowHeight() - 39; // Hunger bar position vertically

        // Add override if applicable
        if (BTWRSLModClient.getSettings().isPenaltyOverridesEnabled())
            hungerBarY -= BTWRSLModClient.getSettings().getRenderYOffset();

        // Get y position to start drawing penalties
        int textY = this.getTextY(player, hungerBarY);
        int offsetY = 0;

        // Begin drawing
        for (Penalty penalty : penalties.values()) {
            // Get translated text
            Text translatedText = Text.translatable(penalty.getDisplay());

            // Right align text to hot-bar
            int statusX = hungerBarX - renderer.getWidth(translatedText);

            // Render
            if (penalty.condition.test() && !translatedText.getString().isEmpty()) {
                context.drawText(renderer, translatedText, statusX, textY - offsetY, 0xFFFFFFFF, true);
                offsetY += 10;
            }
        }

        // Set render boolean to false in-case of HUD changes at runtime
        setRenderingFood(false);
    }

    private void renderPenaltiesAnchored(DrawContext context, TextRenderer renderer, PlayerEntity player) {
        // Calculate anchor point
        PenaltyDrawMode drawMode = getDrawMode();
        int renderX = drawMode.getX(context);
        int renderY = drawMode.getY(context);
        int offsetY = 0;
        int margin = getSettings().getRenderMargin();

        // Add override if applicable
        if (BTWRSLModClient.getSettings().isPenaltyOverridesEnabled())
            renderY -= BTWRSLModClient.getSettings().getRenderYOffset();
        renderY += drawMode.getYMargin(margin);

        // Begin drawing
        for (Penalty penalty : penalties.values()) {
            // Get translated text
            Text translatedText = Text.translatable(penalty.getDisplay());

            // Right align text to hot-bar
            int statusX = renderX - (drawMode.shouldAddX() ? renderer.getWidth(translatedText) : 0);
            statusX += drawMode.getXMargin(margin);

            // Render
            if (penalty.condition.test() && !translatedText.getString().isEmpty()) {
                context.drawText(renderer, translatedText, statusX, renderY - offsetY, 0xFFFFFFFF, true);
                offsetY += drawMode.shouldFlipY() ? -10 : 10;
            }
        }
    }

    /**
     * Calculates proper Y position for HUD elements
     * @param player PlayerEntity to get information from
     * @param hungerBarY Y position of the hunger bar
     * @return Calculated Y position
     */
    private int getTextY(PlayerEntity player, int hungerBarY) {
        // Get additional context
        boolean isRenderingAir = player.getAir() != player.getMaxAir();
        boolean isRenderingArmor = player.getArmor() > 0;

        // Default Y position (above the hunger bar, alternatively above hot-bar)
        int textY = isRenderingFood ? hungerBarY - 10 : hungerBarY;

        // Adjust the Y position under certain conditions
        if (
            (isRenderingAir && isRenderingFood) ||  // Player is underwater and food is rendered
            (isRenderingArmor && !isRenderingFood)) // Player is wearing armor and food is NOT rendered
            // Future explicit compatibility checks could be done here
        {
            textY -= 10;
        }

        return textY;
    }

    /**
     * Sets indicator for whether food bar is rendering or not
     */
    public void setRenderingFood(boolean value) {
        isRenderingFood = value;
        if (
                // Explicit compat checks here
                FabricLoader.getInstance().isModLoaded("granular_hunger") ||
                // Then the override
                (BTWRSLModClient.getSettings().isPenaltyOverridesEnabled() &&
                 BTWRSLModClient.getSettings().isHungerOffsetEnabled())
        )
            isRenderingFood = true;
    }

    /**
     * Adds penalty to TreeMap
     * @param newPenalty Penalty to add
     */
    public void addPenalty(Penalty newPenalty) {
        // Get index and verify another key doesn't exist
        int index = newPenalty.getPriority();
        boolean hasKey = penalties.containsKey(index);

        // If a key exists, continue bumping index until we reach an open key
        while (hasKey) {
            hasKey = penalties.containsKey(++index);
        }
        penalties.put(index, newPenalty);
    }

    /**
     * Removes penalty from TreeMap
     * @param newPenalty Penalty to remove
     */
    public void removePenalty(Penalty newPenalty) {
        int index = newPenalty.getPriority();
        removePenaltyAtIndex(index);
    }

    /**
     * Removes penalty at index from TreeMap
     * @param index Index to attempt removal at
     */
    public void removePenaltyAtIndex(int index) {
        try {
            penalties.remove(index);
        } catch (IndexOutOfBoundsException e) {
            BTWRSLMod.LOGGER.error("Could not remove penalty!", e.getCause());
        }
    }

    /**
     * Readability wrapper for settings getter
     * @return Mod settings
     */
    private BTWRSLSettings getSettings() {
        return BTWRSLModClient.getSettings();
    }

    /**
     * Readability wrapper for draw mode
     * @return Current draw mode
     */
    private PenaltyDrawMode getDrawMode() {
        return BTWRSLModClient.getSettings().getDrawMode();
    }

    /**
     * Readability wrapper for checking override
     * @return Override state
     */
    private boolean isOverriding() {
        return getSettings().isPenaltyOverridesEnabled();
    }
}
