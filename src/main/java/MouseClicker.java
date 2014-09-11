import java.awt.*;
import java.awt.event.InputEvent;

public class MouseClicker {
    private static int x1, y1, sleep, minutes;
    private static boolean holdMouse;

    private static String help = "Use \"java MouseClicker 111 222 true 4 55\" to run this application\n" +
            "        111 - x coordinate of the screen\n" +
            "        222 - y coordinate of the screen\n" +
            "        true - hold mouse on the X;Y. if false mouse will be free to move\n" +
            "          4 - amount of seconds to wait before main clicking loop\n" +
            "         55 - amount of MINUTES to run MouseClicker\n";

    public static void main(String args[]) throws InterruptedException {
        parseArgs(args);

        Robot bot = null;
        try {
            bot = new Robot();
        } catch (Exception failed) {
            System.err.println("Failed instantiating Robot: " + failed);
        }
        int mask = InputEvent.BUTTON1_DOWN_MASK;

        Thread.sleep(sleep * 1000);
        long maxMinutes = minutes;
        long maxNanoSeconds = maxMinutes * 1000000000 * 60;
        long start = System.nanoTime();
        long i = 0;
        int x = x1;
        int y = y1;

        bot.mouseMove(x, y);
        for (; ; i++) {
            long passedTime = System.nanoTime() - start;
            if (passedTime > maxNanoSeconds) {
                break;
            }
            if (holdMouse) {
                bot.mouseMove(x, y);
            }
            bot.mousePress(mask);
            bot.mouseRelease(mask);
            Thread.sleep(5);
        }
        System.out.format("%10d iterations%n", i);
    }

    private static void parseArgs(String[] args) {
        try {
            if (args.length == 0 || args.length > 5) {
                throw new IllegalArgumentException();
            }
            x1 = Integer.parseInt(args[0]);
            y1 = Integer.parseInt(args[1]);
            holdMouse = Boolean.parseBoolean(args[2]);
            sleep = Integer.parseInt(args[3]);
            minutes = Integer.parseInt(args[4]);
        } catch (Exception e) {
            showHelp();
            System.exit(0);
        }
    }

    private static void showHelp() {
        System.out.println(help);
    }

    private static class Point {
        int x, y;

        Point(int xx, int yy) {
            x = xx;
            y = yy;
        }
    }
}
