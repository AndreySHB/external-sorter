package tst2;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class JavaFrame extends JFrame {
    Color color;
    Random random = new Random();

    public JavaFrame() throws HeadlessException {
        setSize(200, 200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showOnRandowPlace() {

        setLocation(random.nextInt(1200), random.nextInt(600));
        getContentPane().setBackground(color);
    }

    public static void main(String[] args) throws InterruptedException {
        JavaFrame javaFrame = new JavaFrame();
        Random random1 = new Random();
        while (true) {
            javaFrame.color =
                    new Color(random1.nextInt(255), random1.nextInt(255), random1.nextInt(255));
            javaFrame.showOnRandowPlace();
            Thread.sleep(100L);
        }
    }
}
