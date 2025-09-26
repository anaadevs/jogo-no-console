import java.awt.Color;
import java.awt.Graphics;

public class Personagem {
    protected Jogo jogo;
    protected Color color;
    protected float x, y;
    protected int width, height;
    protected int speed;
    protected boolean up, down, left, right;

    public Personagem(Jogo jogo, Color color, float x, float y, int width, int height, int speed){
        this.jogo = jogo;
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public void update(){
        this.move();
    }

    public void move(){        
        float saveX = this.x;
        float saveY = this.y;

        float realSpeed = this.speed;
        if((this.right || this.left) && (this.up || this.down)){
            realSpeed = this.speed/((float) Math.sqrt(2));
        }

        if(up){
            this.y -= realSpeed;
        }
        if(down){
            this.y += realSpeed;
        }

        if(!this.jogo.colisao(this)){
            this.y = saveY;
        }

        if(left){
            this.x -= realSpeed;
        }
        if(right){
            this.x += realSpeed;
        }

        if(!jogo.colisao(this)){
            this.x = saveX;
        }
    }

    public void desenhar(Graphics g) {
        g.setColor(color);
        g.fillRect(Math.round(x), Math.round(y), width, height);
    }

    public void reset(){
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;
    }

    public void setX(float x){
        this.x = x;
    }
    public float getX(){
        return this.x;
    }
    public void setY(float y){
        this.y = y;
    }
    public float getY(){
        return this.y;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getWidth(){
        return this.width;
    }
    public void setHeigth(int height){
        this.height = height;
    }
    public int getHeigth(){
        return this.height;
    }

    public void setUp(boolean up){
        this.up = up;
    }
    public void setDown(boolean down){
        this.down = down;
    }
    public void setLeft(boolean left){
        this.left = left;
    }
    public void setRigth(boolean right){
        this.right = right;
    }
}