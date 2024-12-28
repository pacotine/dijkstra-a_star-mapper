package main;

import main.gui.Window;

/**
 * Main class, initializes the application.
 */
public class Main {

    public static void main(String[] args) {
        try {
            Window window = new Window(args);
            window.show();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Retrieves implementation version from {@code MANIFEST.MF}.
     * @return the implementation version of this application
     */
    public static String getVersionFromManifest() {
        String version = Main.class.getPackage().getImplementationVersion();
        return version == null ? "built from source" : version;
    }
}