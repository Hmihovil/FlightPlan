package chanjin.flightplan;

public class ScreenConfig {
    public static int screen_width;
    public static int screen_height;

    public static int virtual_width;
    public static int virtual_height;

    public ScreenConfig(int ScreenWidth, int ScreenHeight) {
        screen_width = ScreenWidth;
        screen_height = ScreenHeight;
    }

    public void setSize(int width, int height) {
        virtual_width = width;
        virtual_height = height;
    }

    public int getX(int x) {
        return (int) (x * screen_width / virtual_width);
    }

    public int getY(int y) {
        return (int) (y * screen_height / virtual_height);
    }
}
