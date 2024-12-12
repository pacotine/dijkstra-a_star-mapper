package main;

import main.gui.Window;

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

    public static String getVersionFromManifest() {
        String version = Main.class.getPackage().getImplementationVersion();
        return version == null ? "built from source" : version;
    }
}