import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class BB extends JApplet implements Runnable, KeyListener{
    Ball b = new Ball (500, 500, 10, 10, Math.PI/5); 
    Paddle p = new Paddle (250, 25); 
    Brick [] bricks = new Brick [24]; 
    

    Thread t;
    public void init() {
        addKeyListener(this); 
        setFocusable(true);
        
        makeBricks();
        
        t = new Thread(this);
        t.start();
        
        
         
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyChar()== 'a'){
 
            p.move(0); 
        }
        if(e.getKeyChar()== 'd'){
 
            p.move(); 
        }
        
        // do something 
    }

    public void keyTyped (KeyEvent e){} 

    public void keyReleased(KeyEvent e){}

    public void run(){
        try{
            while (true){ // run perpetually 

                t.sleep(50);
                b.move(); 
                //p.paddlebounce(b, p.getX(), p.getY()); 
                //p.move(); 

                //if ball.y > p.y && b.y < p.y + p.l && b.x 
                //method to tell the ball to bounce to change the angle 
                //a = -a; 
                // b.setA 
            }

        }
        catch (InterruptedException e) {}
    }

    void makeBricks(){

        int i = 0;
        for (int j = 10; j <= 610; j += 120){//length = 80 
            for (int k = 10; k <= 160; k += 50){//width = 10 
                bricks[i] = new Brick(j, k); 
                i++; 

            }
        }
    }

    public void paint(Graphics g) { // draw the ball 
        resize(525,525); 
        g.setColor(Color.white); 
        g.fillRect(0,0,525,525);
        g.setColor(Color.black); 
        g.fillOval(b.getX(), b.getY(), b.getR()*2, b.getR()*2); //accessor method for x needs to return an int 
        g.setColor(Color.white); 
        repaint(); 
        g.setColor(Color.black); 
        g.drawRect (p.getX(), p.getY(), 75, 15); // the paddle 
        for (int i = 0; i < 15; i++){
            g.setColor(Color.black); 
            g.drawRect(bricks[i].getX(), bricks[i].getY(),120, 50); 
            // if (brick isn't there), don't draw it 
        }
        for (int i = 0; i < bricks.length; i++){
            bricks[i].breakbrick(b, b.getX(), b.getY()); 
        }

    }
}
class Ball {
    private double x; 
    private double y; 
    private double r; //radius 
    private double v; //velocity
    public double a; //angle in radians 

    int getX(){
        return (int)x; //cast x as an integer 
    }

    int getY(){
        return (int)y; 
    }

    int getR(){
        return (int)r; 
    }

    double getA(){
        return a; 
    }

    Ball (double x, double y, double r, double v, double a){
        this.x = x; 
        this.y = y; 
        this.r = r; 
        this.v = v; 
        this.a = a; 
    }

    void move (){//angle change a = -a; 
        x = x + v*Math.cos(a); 
        y = y + v*Math.sin(a); 

        if (y < 0){
            a = -a; 
        }
        if (x < 0){
            a = Math.PI - a; 
        }
        if (y > 500){
            a = -a; 
        }
        if (x > 500){
            a = Math.PI - a; 
        }

    }
}
class Paddle {
    private int x; 
    private int y; 
    private int v; 
    Paddle (int x, int v) {
        this.x = x; 
        y = 400; 
        this.v = v; 
    }

    int getY(){
        return y; 
    }

    int getX(){
        return x; 
    }

    public void move(){
        //if (x<0 || x>500-75) v = -v; 
        x = x + v; 
    
    } 
    public void move(int i){
        if (i == 0){
            x = x - v; 
        }
    }
    
    void paddlebounce (Ball b, int xb, int yb){
        if (xb + 10 >= x && xb <= x + 75 && yb + 10 >= y && yb <= y + 15){
            b.a = -b.a; 
        }
    }
}

class Brick {
    private int x; 
    private int y; 
    public boolean broken; // sets it to true if the center of the ball is inside the rectangle 

    Brick (int x, int y){
        this.x = x; 
        this.y = y; 
        broken = false; 
    }

    int getX (){
        return x; 
    }

    int getY (){
        return y; 
    }
    
    void breakbrick (Ball b, int xb, int yb){//x and y are coordinates of ball 
        if (broken) return; 
        if (xb + 10 >= x && xb <= x + 120 && yb + 10 >= y && yb <= y + 50){
            broken = true; 
            y = y + 750; 
            b.a = -b.a; 
            
        }
    }//radius is 10
}
