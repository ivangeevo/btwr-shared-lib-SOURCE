package btwr.btwr_sl.lib.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Handles rendering of penalties, and their hierarchy
 */
public class PenaltyDisplayManager {
    /**
     * Instance of PenaltyDisplayManager
     */
    private static final PenaltyDisplayManager INSTANCE = new PenaltyDisplayManager();

    public static final int HEALTH_PRIORITY = 0;
    public static final int GLOOM_PRIORITY = 1;
    public static final int HUNGER_PRIORITY = 2;
    /**
     * List of current penalties to be rendered
     */
    private static HashMap<Integer,Penalty> penalties = new HashMap<>();
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

    static public class Penalty {
        /**
         * Determines rendering order, with higher values being closer to the hotbar
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

        public Penalty(int priority, PenaltyString display, PenaltyCondition condition) {
            this.priority = priority;
            this.display = display;
            this.condition = condition;
        }

        public Penalty(int priority, String string, PenaltyCondition condition) {
            this.priority = priority;
            this.display = () -> string;
            this.condition = condition;
        }

        public Penalty(int priority, PenaltyString display) {
            this.priority = priority;
            this.display = display;
            this.condition = () -> true;
        }

        public int getPriority() {
            return this.priority;
        }

        public String getDisplay() {
            return this.display.get();
        }
    }

    public static void render(DrawContext context, TextRenderer renderer) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        renderPenalties(context, renderer, player);
    }

    /**
     * Draws penalties (if applicable) onto provided context
     * @param context DrawContext to draw to
     * @param renderer TextRenderer to use for drawing text
     * @param player PlayerEntity to get information from
     */
    private static void renderPenalties(DrawContext context, TextRenderer renderer, PlayerEntity player) {
        // Calculate the position of the hunger bar
        int hungerBarX = context.getScaledWindowWidth() / 2 + 91;  // Center of the hunger bar
        int hungerBarY = context.getScaledWindowHeight() - 39; // Hunger bar position vertically

        // Get y position to start drawing penalties
        int textY = getTextY(player, hungerBarY);
        int offsetY = 0;

        for (Penalty penalty : penalties.values()) {
            // Get translated text
            Text translatedText = Text.translatable(penalty.getDisplay());

            // Right align text to hot-bar
            int statusX = hungerBarX - renderer.getWidth(translatedText);

            // Render
            if (penalty.condition.test() && !translatedText.equals(Text.of(""))) {
                context.drawText(renderer, translatedText, statusX, textY - offsetY, 0xFFFFFFFF, true);
                offsetY += 10;
            }
        }

        // Set render boolean to false in-case of HUD changes at runtime
        isRenderingFood = false;
    }

    /**
     * Calculates proper Y position for HUD elements
     * @param player PlayerEntity to get information from
     * @param hungerBarY Y position of the hunger bar
     * @return Calculated Y position
     */
    private static int getTextY(PlayerEntity player, int hungerBarY) {
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

    public static void setRenderingFood(boolean value) {
        isRenderingFood = value;
    }

    public void addPenalty(Penalty penalty) {
        int index = penalty.getPriority();
        penalties.put(index, penalty);
    }
}
