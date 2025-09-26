import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener{
    private Jogo jogo;

    public Teclado(Jogo jogo){
        this.jogo = jogo;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        jogo.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        jogo.keyReleased(e);
    }
    
}