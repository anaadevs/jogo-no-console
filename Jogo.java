import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Jogo extends JPanel implements Runnable{
    private Personagem jogador;
    private List<Personagem> inimigos;
    private List<Personagem> projeteis;
    private int sec, min;

    private boolean running = false;
    private Thread gameThread;

    public Jogo(){        
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);
        this.addKeyListener(new Teclado(this));
        this.addMouseListener(new Mouse(this));

        this.jogador = new Personagem(this, Color.blue, 200, 200, 32, 32, 2);

        this.inimigos = new ArrayList<>();
        this.projeteis = new ArrayList<>();
        this.novoInimigo();
    }
    
    public void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    
	@Override
	public void run() {
		final int FPS = 240;
        final double timePerTick = 1000000000 / FPS;
        long lastTime = System.nanoTime();
        long now;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while(gameThread != null) {

            while (running) {
                now = System.nanoTime();
                delta += (now - lastTime);
                lastTime = now;

                if (delta >= timePerTick) {
                    update();
                    repaint();               
                    delta-=timePerTick;
                }
                now = System.currentTimeMillis();
                if(now - timer >= 1000){
                    timer = now;
                    sec++;
                    if(sec >= 60){
                        sec = 0;
                        min++;
                    }
                    this.novoInimigo();
                }
            }
        }
	}

    public void update(){
        this.jogador.update();

        for(int i = 0; i < this.inimigos.size(); i++){
        this.inimigos.get(i).update();
        }

        for(int i = 0; i < this.projeteis.size(); i++){
        this.projeteis.get(i).update();
        }

        this.check();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.jogador.desenhar(g);

        for(int i = 0; i < this.inimigos.size(); i++){
        this.inimigos.get(i).desenhar(g);
        }

        for(int i = 0; i < this.projeteis.size(); i++){
        this.projeteis.get(i).desenhar(g);;
        }

        g.setColor(Color.red);
        String minString = "" + min;
        if(min < 10){
        minString = "0" + minString;
        }

        String secString = "" + sec;
        if(sec < 10){
        secString = "0" + secString;
        }
        g.drawString(minString + ":" + secString, 20, 20);
    }

    public boolean colisao(Personagem personagem){
        if(personagem.getX() < 0 || personagem.getY() < 0 || (personagem.getX() + personagem.getWidth()) > Programa.screenWidth || (personagem.getY() + personagem.getHeigth()) > Programa.screenHeigth){
        return false;
        }
        return true;
    }

    public void check(){
        for(int i = 0; i < this.inimigos.size(); i++){
            if((this.inimigos.get(i).getX() >= this.jogador.getX() 
                && this.inimigos.get(i).getX() <= (this.jogador.getX() + this.jogador.getWidth()))
                || ((this.inimigos.get(i).getX() + this.inimigos.get(i).getWidth()) >= this.jogador.getX() 
                && (this.inimigos.get(i).getX() + this.inimigos.get(i).getWidth()) <= (this.jogador.getX() + this.jogador.getWidth()))){

                if((this.inimigos.get(i).getY() >= this.jogador.getY() 
                    && this.inimigos.get(i).getY() <= (this.jogador.getY() + this.jogador.getHeigth()))
                    || ((this.inimigos.get(i).getY() + this.inimigos.get(i).getHeigth()) >= this.jogador.getY() 
                    && (this.inimigos.get(i).getY() + this.inimigos.get(i).getHeigth()) <= (this.jogador.getY() + this.jogador.getHeigth()))){
                        
                        this.running = false;
                }
            }

            for(int j = 0; j < this.projeteis.size(); j++){
                if((this.projeteis.get(j).getX() + this.projeteis.get(j).getWidth()/2) >= this.inimigos.get(i).getX()
                    && (this.projeteis.get(j).getX() + this.projeteis.get(j).getWidth()/2) <= (this.inimigos.get(i).getX() + this.inimigos.get(i).getWidth())
                    && (this.projeteis.get(j).getY() + this.projeteis.get(j).getHeigth()/2) >= this.inimigos.get(i).getY()
                    && (this.projeteis.get(j).getY() + this.projeteis.get(j).getHeigth()/2) <= (this.inimigos.get(i).getY() + this.inimigos.get(i).getHeigth())){
                    this.inimigos.remove(i);
                    this.projeteis.remove(j);
                    j = this.projeteis.size();
                    }
            }
        }
    }

    public void novoInimigo(){
        Random random = new Random();

        int rx, ry;
        int width = 32;
        int height = 32;

        int canto = random.nextInt(4);
        if(canto == 0 || canto == 3){
            rx = 5;
        }else{
            rx = Programa.screenWidth - 5 - width;
        }
        if(canto == 0 || canto == 1){
            ry = 5;
        }else{
            ry = Programa.screenHeigth - 5 - height;
        }

        this.inimigos.add(new Inimigo(this, Color.red, rx, ry, width, height, 1));
    }

    public void mousePressed(MouseEvent e){
        float projetilSpeed = 1;

        float dx = e.getX() - this.jogador.getX();
        float dy = e.getY() - this.jogador.getY();
        float distancia = (float) Math.sqrt(dx*dx + dy*dy);

        float tempo = distancia/projetilSpeed;
        float vx = dx/tempo;
        float vy = dy/tempo;

        this.projeteis.add(new Projetil(this, Color.green, this.jogador.getX() + this.jogador.getWidth()/2, 
            this.jogador.getY() + this.jogador.getHeigth()/2, 4, 4, vx, vy));
    }

    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> this.jogador.setUp(true);
            case KeyEvent.VK_S -> this.jogador.setDown(true);
            case KeyEvent.VK_A -> this.jogador.setLeft(true);
            case KeyEvent.VK_D -> this.jogador.setRigth(true);
        }
    }

    public void keyReleased(KeyEvent e){
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> this.jogador.setUp(false);
            case KeyEvent.VK_S -> this.jogador.setDown(false);
            case KeyEvent.VK_A -> this.jogador.setLeft(false);
            case KeyEvent.VK_D -> this.jogador.setRigth(false);
        }
    }

    public Personagem getJogador(){
        return this.jogador;
    }

    public void excluirProjetil(Personagem personagem){
        for(int i = 0; i < this.projeteis.size(); i++){
            if(this.projeteis.get(i) == personagem){
                this.projeteis.remove(i);
            }
        }
    }
}