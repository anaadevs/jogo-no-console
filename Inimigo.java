import java.awt.Color;

public class Inimigo extends Personagem{

    public Inimigo(Jogo jogo, Color color, int x, int y, int width, int height, int speed) {
        super(jogo, color, x, y, width, height, speed);
        
    }
    
    @Override
    public void update(){
        Personagem jogador = this.jogo.getJogador();

        this.reset();
        
        if(jogador.getX() > this.x){
            this.right = true;
        }else if(jogador.getX() < this.x){
            this.left = true;
        }

        if(jogador.getY() > this.y){
            this.down = true;
        }else if(jogador.getY() < this.y){
            this.up = true;
        }

        this.move();
    }
}