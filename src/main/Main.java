package main;

import main.gui.Window;

public class Main {

    public static void main(String[] args) {
        Window window = new Window(args);
        try {
            window.show();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static String getVersionFromManifest() {
        String version = Main.class.getPackage().getImplementationVersion();
        return version == null ? "built from source" : version;
    }
}