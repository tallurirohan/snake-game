import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class gamepanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT =600;
    static final int  UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyparts = 6;
    int appleseaten;
    int applex;
    int appley;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    gamepanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startgame();
    }
    public void startgame(){
        newApple();
        running =true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
           /*for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            */
            g.setColor(Color.red);
            g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i < bodyparts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }

            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+appleseaten,(SCREEN_WIDTH-metrics.stringWidth("Score: "+appleseaten))/2,g.getFont().getSize());
        }
        else {
            gameover(g);
        }
    }
    public void newApple(){
        applex = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appley = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for (int i = bodyparts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
            switch (direction){
                case 'U':
                    y[0] = y[0]-UNIT_SIZE;
                    break;
                case 'D':
                    y[0] = y[0]+UNIT_SIZE;
                    break;
                case 'L':
                    x[0] = x[0]-UNIT_SIZE;
                    break;
                case 'R':
                    x[0] = x[0]+UNIT_SIZE;
                    break;
            }

    }
    public void checkapple(){
        if((x[0]==applex)&&(y[0]==appley)){
            bodyparts++;
            appleseaten++;
            newApple();
        }
    }
    public void checkCollisions() {
        for (int i = bodyparts; i>0 ; i--) {
            if((x[0]==x[i])&&(y[0]==y[i])){
                running = false;
            }
        }
        if(x[0]<0){
            running = false;
        }
        if(x[0]>SCREEN_WIDTH){
            running = false;
        }
        if(y[0]<0){
            running =false;
        }
        if(y[0]>SCREEN_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameover(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("game over",(SCREEN_WIDTH-metrics1.stringWidth("game over"))/2,SCREEN_HEIGHT/2);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+appleseaten,(SCREEN_WIDTH-metrics.stringWidth("Score: "+appleseaten))/2,g.getFont().getSize());
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(running){
            move();
            checkapple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction='D';
                    }
                    break;

            }
        }
    }
}
