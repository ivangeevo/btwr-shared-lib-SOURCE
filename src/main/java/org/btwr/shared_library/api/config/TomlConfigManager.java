package org.btwr.shared_library.api.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class TomlConfigManager {

    private static final List<ConfigGroup> GROUPS = new CopyOnWriteArrayList<>();

    private TomlConfigManager() {}

    public static void registerGroup(ConfigGroup group) {
        if (!GROUPS.contains(group)) GROUPS.add(group);
        group.init(); // ensures file is loaded/created
    }

    public static File getConfigDir() {
        File dir = FabricLoader.getInstance().getConfigDir().toFile();
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("[ConfigLib] Failed to create config directory: " + dir.getAbsolutePath());
        }
        return dir;
    }

    public static void update() {
        for (ConfigGroup group : GROUPS) group.save();
    }

    /** Writes TOML file with comments and default values */
    public static void writeConfigFile(ConfigGroup group) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(group.getFile()), StandardCharsets.UTF_8))) {
            for (ConfigSetting<?> setting : group.getAll().values()) {
                String comment = setting.getComment();
                if (comment != null && !comment.isEmpty()) {
                    for (String line : comment.split("\n")) {
                        w.write("# " + line);
                        w.newLine();
                    }
                }

                w.write("# Default: " + setting.getDefaultValue());
                if (setting.getMin() != null || setting.getMax() != null) {
                    w.write(" | Min: " + setting.getMin() + " Max: " + setting.getMax());
                }
                w.newLine();
                w.write(setting.getKey() + " = " + serializeValue(setting));
                w.newLine();
                w.newLine();
            }
        }
    }

    private static String serializeValue(ConfigSetting<?> setting) {
        Object v = setting.get();
        if (v instanceof String || v instanceof Character) return "\"" + v.toString().replace("\"", "\\\"") + "\"";
        if (v instanceof Boolean) return v.toString();
        return v.toString();
    }

    public static void readConfigFile(ConfigGroup group) throws IOException {
        File file = group.getFile();
        if (!file.exists()) return;

        try (BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                int eq = line.indexOf('=');
                if (eq <= 0) continue;

                String key = line.substring(0, eq).trim();
                String val = line.substring(eq + 1).trim();

                ConfigSetting<?> s = group.get(key);
                if (s != null) applyValue(s, val);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void applyValue(ConfigSetting<T> setting, String raw) {
        Class<T> type = setting.getType();
        try {
            Object v = switch (type.getSimpleName()) {
                case "Integer" -> Integer.parseInt(raw);
                case "Double" -> Double.parseDouble(raw);
                case "Boolean" -> Boolean.parseBoolean(raw);
                case "String" -> {
                    if (raw.startsWith("\"") && raw.endsWith("\"")) raw = raw.substring(1, raw.length() - 1);
                    yield raw.replace("\\\"", "\"");
                }
                default -> raw;
            };
            setting.set((T) v);
        } catch (Exception e) {
            System.err.println("[ConfigLib] Failed to parse value for " + setting.getKey() + ": " + raw);
        }
    }

}