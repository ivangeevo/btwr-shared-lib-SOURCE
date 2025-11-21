package org.btwr.shared_library;

import org.btwr.shared_library.compat.LeaveMyBarsAloneCompat;
import org.btwr.shared_library.compat.ToughAsNailsCompat;
import org.btwr.shared_library.config.BTWRSLSettings;
import com.google.gson.Gson;
import net.fabricmc.api.ClientModInitializer;
import org.btwr.shared_library.gui.hud.PenaltyDisplayManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BTWRSLModClient implements ClientModInitializer
{
    public BTWRSLSettings settings;
    private static BTWRSLModClient instance;

    private static final String CONFIG_FILE_PATH = "./config/btwr/btwrsl_common.json";

    /**
     * Getter for current BTWRSLModClient instance
     */
    public static BTWRSLModClient getInstance() {
        return instance;
    }

    /**
     * Getter for current BTWRSLSettings instance
     */
    public static BTWRSLSettings getSettings() {
        return getInstance().settings;
    }

    @Override
    public void onInitializeClient() {
        instance = this;
        loadSettings();

        // Default status bar offset conditions for penalty text
        PenaltyDisplayManager.HudYOffsetRegistry.registerDefaults();

        // Compatibility modules
        LeaveMyBarsAloneCompat.init();
        ToughAsNailsCompat.init();
    }

    /**
     * Config loading and saving from Tough Environment (CC-BY-4.0)
     * @link <a href="https://github.com/ivangeevo/tough_environment/blob/1.21.1/release/src/main/java/org/tough_environment/ToughEnvironmentMod.java">Source</a>
     */

    // Do not remove this comment or the project will NOT compile!
    public void loadSettings() {
        File file = new File(CONFIG_FILE_PATH);
        Gson gson = new Gson();
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                settings = gson.fromJson(fileReader, BTWRSLSettings.class);
                fileReader.close();
            }
            catch (IOException e) {
                BTWRSLMod.LOGGER.warn("Could not load BTWR: Shared Library settings: {}", e.getLocalizedMessage());
            }
        }
        else {
            settings = new BTWRSLSettings();
        }
    }

    public void saveSettings() {
        Gson gson = new Gson();
        File file = new File(CONFIG_FILE_PATH);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(settings));
            fileWriter.close();
        }
        catch (IOException e) {
            BTWRSLMod.LOGGER.warn("Could not save BTWR: Shared Library settings: {}", e.getLocalizedMessage());
        }
    }
}