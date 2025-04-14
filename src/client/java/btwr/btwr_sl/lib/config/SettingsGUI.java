package btwr.btwr_sl.lib.config;

import btwr.btwr_sl.lib.client.BTWRSLModClient;
import net.minecraft.client.gui.screen.Screen;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class SettingsGUI {
    private static final BTWRSLSettings settingsCommon = BTWRSLModClient.getSettings();
    private static final String PREFIX = "config.btwrsl.draw_mode.anchor.";

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent).setTitle(Text.translatable("title.btwrsl.config"));
        builder.setSavingRunnable(() -> { BTWRSLModClient.getInstance().saveSettings(); });

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.btwrsl.category.penalties"));

        /** Penalties Category **/
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwrsl.penalty_override"), settingsCommon.doPenaltyDisplayOverride)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> settingsCommon.doPenaltyDisplayOverride = newValue)
                .setTooltip(Text.translatable("config.btwrsl.tooltip.penalty_override"))
                .build());
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwrsl.hunger_override"), settingsCommon.doHungerOffsetOverride)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.doHungerOffsetOverride = newValue)
                .setTooltip(Text.translatable("config.btwrsl.tooltip.hunger_override"))
                .build());
        general.addEntry(entryBuilder
                .startIntSlider(Text.translatable("config.btwrsl.render_y"), settingsCommon.renderYOffset, -100, 100)
                .setDefaultValue(0)
                .setSaveConsumer(newValue -> settingsCommon.renderYOffset = newValue)
                .setTooltip(Text.translatable("config.btwrsl.tooltip.render_y"))
                .build());
        general.addEntry(entryBuilder
                .startIntSlider(Text.translatable("config.btwrsl.draw_margin"), settingsCommon.renderMargin, 0, 32)
                .setDefaultValue(8)
                .setSaveConsumer(newValue -> settingsCommon.renderMargin = newValue)
                .setTooltip(Text.translatable("config.btwrsl.tooltip.draw_margin"))
                .build());
        general.addEntry(entryBuilder
                .startEnumSelector(Text.translatable("config.btwrsl.draw_mode"), PenaltyDrawMode.class, settingsCommon.drawMode)
                .setDefaultValue(PenaltyDrawMode.BTW)
                .setSaveConsumer(newValue -> settingsCommon.drawMode = newValue)
                .setTooltip(Text.translatable("config.btwrsl.tooltip.draw_mode"))
                .setEnumNameProvider(anEnum -> Text.translatable(PREFIX + anEnum.name()))
                .build());

        return builder.build();
    }
}
