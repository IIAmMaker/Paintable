package net.im_maker.paintable.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles server-side configuration for the Paintable mod.
 * Configuration is saved and loaded from a JSON file.
 */
public class PaintableServerConfigs {

    // Configuration file path
    private static final File CONFIG_FILE = new File("config/Paintable-Server.json");

    // Configuration options
    private boolean canPaint = false; // Default value

    // Gson instance for JSON parsing with pretty printing
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    // Singleton instance
    private static PaintableServerConfigs INSTANCE;

    // Private constructor to enforce singleton pattern
    private PaintableServerConfigs() {}

    /**
     * Returns the singleton instance of the configuration.
     * If the instance doesn't exist, it is created and the configuration is loaded.
     *
     * @return The singleton instance of PaintableServerConfigs.
     */
    public static PaintableServerConfigs getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PaintableServerConfigs();
            INSTANCE.load(); // Load the configuration when the instance is created
        }
        return INSTANCE;
    }

    /**
     * Loads the configuration from the JSON file.
     * If the file doesn't exist, a default configuration is created and saved.
     */
    public void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                PaintableServerConfigs config = GSON.fromJson(reader, PaintableServerConfigs.class);
                this.canPaint = config.canPaint;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            save(); // Create default config if the file doesn't exist
        }
    }

    /**
     * Saves the current configuration to the JSON file with comments.
     */
    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            // Write comments to the JSON file
            writer.write("{\n");
            //writer.write("  \"comment\": \"configs\",\n");
            //writer.write("  \"comment\": \"Can paint?:\",\n");

            // Convert the configuration object to JSON
            String json = GSON.toJson(this);

            // Remove the opening and closing braces from the JSON string
            json = json.substring(1, json.length() - 1);

            // Write the JSON data (without braces) after the comments
            writer.write("  " + json + "\n");
            writer.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns whether painting is allowed on the server.
     *
     * @return True if painting is allowed, false otherwise.
     */
    public boolean getCanPaint() {
        return canPaint;
    }

    /**
     * Sets whether painting is allowed on the server.
     *
     * @param canPaint True to allow painting, false to disallow.
     */
    public void setCanPaint(boolean canPaint) {
        this.canPaint = canPaint;
    }
}