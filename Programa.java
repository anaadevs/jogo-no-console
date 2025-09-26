import java.awt.Rectangle;
import javax.swing.JFrame;

public class Programa{
    public final static int screenWidth = 1280;
    public final static int screenHeigth = 720;

    public static void main(String[] args){
        JFrame frame = new JFrame("Mini Curso");
        //frame.setUndecorated(true);
        frame.setBounds(new Rectangle(200, 200, screenWidth, screenHeigth));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Jogo j = new Jogo();
        frame.add(j);

        frame.setVisible(true);

        j.start(); 
    }
}