package org.distantnetwork.duels.utils.Config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

import static org.distantnetwork.duels.Duels.getInstance;

public class ConfigurationManager {
    public static void setValue(String filePath, String path, Object value) {
        FileConfiguration config = getConfig(filePath);
        config.set(path, value);
        saveConfig(filePath, config);
    }

    public static void setValue(File file, String path, Object value) {
        FileConfiguration config = getConfig(file);
        if (config == null) return;
        config.set(path, value);
        saveConfig(file, config);
    }

    public static boolean isKeyExists(String filePath, String path) {
        FileConfiguration config = getConfig(filePath);
        return config.contains(path);
    }

    public static boolean isKeyExists(File file, String path) {
        FileConfiguration config = getConfig(file);
        if (config == null) return false;
        return config.contains(path);
    }

    public static void setDefault(String filePath, String path, Object value, boolean save) {
        FileConfiguration config = getConfig(filePath);
        config.addDefault(path, value);
        if (save) config.options().copyDefaults(true);
        saveConfig(filePath, config);
    }

    public static void setDefault(File file, String path, Object value, boolean save) {
        FileConfiguration config = getConfig(file);
        if (config == null) return;
        config.addDefault(path, value);
        if (save) config.options().copyDefaults(true);
        saveConfig(file, config);
    }

    public static void save(String filePath) {
        FileConfiguration config = getConfig(filePath);
        if (config == null) return;
        saveConfig(filePath, config);
    }

    public static void save(File file) {
        FileConfiguration config = getConfig(file);
        if (config == null) return;
        saveConfig(file, config);
    }

    public static @Nullable File getFileFolder(@NotNull String folderPath) {
        File folder = new File(getInstance().getDataFolder(), folderPath);
        if (!folder.exists()) folder.mkdirs();
        if (!folder.isDirectory()) return null;
        return folder;
    }

    public static @Nullable File getFileFile(@NotNull String filePath) {
        File file = new File(getInstance().getDataFolder(), filePath);
        if (!file.getParentFile().exists()) file.mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!file.isFile()) return null;
        return file;
    }

    public static @NotNull File getFile(@NotNull String filePath) {
        File file = new File(getInstance().getDataFolder(), filePath);
        if (!file.getParentFile().exists()) file.mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static @Nullable File getFileFolder(@NotNull File file) {
        if (!file.exists()) file.mkdirs();
        if (!file.isDirectory()) return null;
        return file;
    }

    public static @Nullable File getFileFile(@NotNull File file) {
        if (!file.getParentFile().exists()) file.mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!file.isFile()) return null;
        return file;
    }

    public static @NotNull File getFile(@NotNull File file) {
        if (!file.getParentFile().exists()) file.mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    public static @Nullable FileConfiguration getConfig(@NotNull String filePath) {
        File file = getFileFile(filePath);
        if (file == null) return null;
        return YamlConfiguration.loadConfiguration(file);
    }

    public static @Nullable FileConfiguration getConfig(@NotNull File file) {
        file = getFileFile(file);
        if (file == null) return null;
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void saveConfig(@NotNull String filePath, @NotNull FileConfiguration config) {
        File file = getFileFile(filePath);
        if (file == null) return;
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig(@NotNull File file, @NotNull FileConfiguration config) {
        file = getFileFile(file);
        if (file == null) return;
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer getFilesAmountInFolder(@NotNull String folderPath) {
        File folder = getFileFolder(folderPath);
        if (folder == null) return 0;
        File[] files = folder.listFiles();
        return files == null ? 0 : files.length;
    }

    public static Integer getFilesAmountInFolder(@NotNull File folder) {
        folder = getFileFolder(folder);
        if (folder == null) return 0;
        File[] files = folder.listFiles();
        return files == null ? 0 : files.length;
    }

    public static void deleteFile(@NotNull String filePath) {
        File file = getFileFile(filePath);
        if (file == null) return;
        file.delete();
    }

    public static void deleteFile(@NotNull File file) {
        file = getFileFile(file);
        if (file == null) return;
        file.delete();
    }

    public static void deleteFolder(@NotNull String folderPath) {
        File folder = getFileFolder(folderPath);
        if (folder == null) return;
        folder.delete();
    }

    public static void deleteFolder(@NotNull File folder) {
        folder = getFileFolder(folder);
        if (folder == null) return;
        folder.delete();
    }

    public static boolean folderExists(@NotNull String folderPath) {
        File folder = getFileFolder(folderPath);
        if (folder == null) return false;
        return folder.exists();
    }

    public static boolean folderExists(@NotNull File folder) {
        folder = getFileFolder(folder);
        if (folder == null) return false;
        return folder.exists();
    }

    public static boolean fileExists(@NotNull String filePath) {
        File file = getFileFile(filePath);
        if (file == null) return false;
        return file.exists();
    }

    public static boolean fileExists(@NotNull File file) {
        file = getFileFile(file);
        if (file == null) return false;
        return file.exists();
    }



    //? DEFAULT CONFIGURATION

    public static FileConfiguration getDefaultConfig() {
        return getInstance().getConfig();
    }

    public static void saveDefaultConfig() {
        getInstance().saveConfig();
    }

    public static File getDefaultFolder() {
        return getInstance().getDataFolder();
    }
}
