package btwr.btwr_sl.lib;

import btwr.btwr_sl.BTWRSLMod;
import btwr.btwr_sl.lib.config.BTWRSLSettings;
import com.google.gson.Gson;
import net.fabricmc.api.ClientModInitializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BTWRSLModClient implements ClientModInitializer
{
    public BTWRSLSettings settings;
    private static BTWRSLModClient instance;

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
    }

    /**
     * Config loading and saving from Tough Environment (CC-BY-4.0)
     * @link <a href="https://github.com/ivangeevo/tough_environment/blob/1.21.1/release/src/main/java/org/tough_environment/ToughEnvironmentMod.java">Source</a>
     */

    // Do not remove this comment or the project will NOT compile!
    public void loadSettings() {
        File file = new File("./config/btwr/btwrsl_common.json");
        Gson gson = new Gson();
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                settings = gson.fromJson(fileReader, BTWRSLSettings.class);
                fileReader.close();
            } catch (IOException e) {
                BTWRSLMod.LOGGER.warn("Could not load BTWRSL settings: {}", e.getLocalizedMessage());
            }
        } else {
            settings = new BTWRSLSettings();
        }
    }

    public void saveSettings() {
        Gson gson = new Gson();
        File file = new File("./config/btwr/btwrsl_common.json");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(settings));
            fileWriter.close();
        } catch (IOException e) {
            BTWRSLMod.LOGGER.warn("Could not save BTWRSL settings: {}", e.getLocalizedMessage());
        }
    }
}
