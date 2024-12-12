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
}