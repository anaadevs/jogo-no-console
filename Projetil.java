import java.awt.Color;

public class Projetil extends Personagem{
    private float vx, vy;

    public Projetil(Jogo jogo, Color color, float x, float y, int width, int height, float vx, float vy) {
        super(jogo, color, x, y, width, height, 0);

        this.vx = vx;
        this.vy = vy;
    }
    
    @Override
    public void move(){
        this.x += vx;
        this.y += vy;

        if(!this.jogo.colisao(this)){
            this.jogo.excluirProjetil(this);
        }
    }
}