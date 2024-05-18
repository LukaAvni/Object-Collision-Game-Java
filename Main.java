import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

public class Main {
    public static int coins = 0; //Total coins throughout the run
    public static int lives = 3; //Total lives that will go down when you get hit
    public static JFrame frame; //Innitializing frames and panels to use later
    public static Panel1 level1;
    public static Panel2 level2;
    public static Panel3 level3;
    public static PanelW winScreen;
    public static PanelL loseScreen;

    public static void main(String[] args){
        frame = new JFrame();//Create frame
        level1 = new Panel1(); //Create 3 levels
        level2 = new Panel2();
        level3 = new Panel3();
        winScreen = new PanelW(); //Victory and loss screen
        loseScreen = new PanelL();
        frame.setVisible(true); //Frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280,720);
        frame.setResizable(false);
        frame.add(level1);
        level1.requestFocusInWindow();
    }
    public static void lvlLOSS(){ //to load each seperate world
        frame.add(loseScreen);
        loseScreen.requestFocusInWindow();
    }
    public static void lvl1(){
        frame.add(level1);
        level1.requestFocusInWindow();
    }
    public static void lvl2(){
        frame.add(level2);
        level2.requestFocusInWindow();
    }
    public static void lvl3(){
        frame.add(level3);
        level3.requestFocusInWindow();
    }
    public static void lvlWIN(){
        frame.add(winScreen);
        winScreen.requestFocusInWindow();
    }
}

class Panel1 extends JPanel implements KeyListener,ActionListener{
    //Variables
    public static int r1 = 40; //Radius for player
    public static int coin1_1 = 0; //Int for coin hitbox offset upon collection
    public static boolean bcoin1_1 = false; //Coin collected variable
    static int coin1_2 = 0; //Int for coin hitbox offset upon collection
    static boolean bcoin1_2 = false; //Coin collected variable
    int xpos1; //x position of player
    int ypos1; //y position of player

    //Monster instance variables
    int mxpos1 = 570; //x position of monster
    int mypos1 = 70; //y position of monster

    //Methods
    public Panel1(){ //Constructor
        Timer timer = new Timer(70,this); //Animation for monsters
        timer.start();
        addKeyListener(this); //Implement KeyListener to register WSAD movement
        setFocusable(true);
        requestFocusInWindow();
    }

    protected void paintComponent(Graphics g) { //Draws stuff
        super.paintComponent(g);
        level(g);
        player1(g,xpos1,ypos1);
        levelBounds();
        monster(g,mxpos1,mypos1);
        if (!bcoin1_1) { // Draw coin only if not collected
            g.setColor(Color.BLACK);
            g.drawOval(250, 200, 51, 51);
            g.setColor(Color.YELLOW);
            g.fillOval(250, 200, 50, 50);
        }
        if (!bcoin1_2) { // Draw coin only if not collected
            g.setColor(Color.BLACK);
            g.drawOval(575,400,51,51);
            g.setColor(Color.YELLOW);
            g.fillOval(575,400,50,50);
        }
    }
    public void monster(Graphics g, int mxpos1, int mypos1){ //Draws the monster
        g.setColor(Color.RED);
        g.fillOval(mxpos1,mypos1,60,60);
    }
    public void level(Graphics g){//create physical level
        g.fillRect(0,(int)(getHeight()*.9),1280,100);
        g.fillRect(0,0,getWidth()/10,720);
        g.fillRect(0,0,1280,(int)(getHeight()*.1));
        g.fillRect((int)(getWidth()*9/10),0,150, 720);
        g.fillRect(450,0,100,500);
        g.fillRect(650,0,100,500);
        g.setColor(Color.BLUE);
        g.fillOval(900,250,60,60);
        g.setColor(Color.YELLOW);
    }
    public void player1(Graphics g, int x, int y){//create the player
        xpos1 = x;
        ypos1 = y;
        g.setColor(Color.GREEN);
        g.fillOval((int) (x+getWidth()*.25), (int) (y+getHeight()*.75-r1/2), r1-1, r1-1);
        g.setColor(Color.BLACK);
        g.drawOval((int) (x+getWidth()*.25), (int) (y+getHeight()*.75-r1/2), r1, r1);
        g.setColor(Color.DARK_GRAY);
    }

    @Override
    public void keyPressed(KeyEvent e){//movement
        int step = 10;
        if (e.getKeyCode()==KeyEvent.VK_E){//Press e to next level, for testing purposes
            Main.frame.remove(Main.level1); // Remove Panel1
            Main.lvl2(); // Add Panel2
            Main.frame.revalidate();
            Main.frame.repaint();
        }
        if (e.getKeyCode()==KeyEvent.VK_W){//Move up
            moveCircle(0, -step);
        }
        if (e.getKeyCode()==KeyEvent.VK_S){//Move down
            moveCircle(0, step);
        }
        if (e.getKeyCode()==KeyEvent.VK_A){//Mode left
            moveCircle(-step, 0);
        }
        if (e.getKeyCode()==KeyEvent.VK_D){//Move right
            moveCircle(step, 0);
        }
        repaint();
    }
    private void moveCircle(int deltax, int deltay) {//Method to update the circle position
        // Update the position of the circle
        this.xpos1 += deltax;
        this.ypos1 += deltay;
    }

    public void levelBounds(){
        //In case of loss
        if(Main.lives==0){
            Main.frame.remove(Main.level1);
            Main.lvlLOSS();
            Main.frame.revalidate();
            Main.frame.repaint();
        }
        //Bounds for sending to next level
        if(((int)(xpos1+getWidth()*.25)<930)&&((int)(xpos1+getWidth()*.25)>870)&&((int)(ypos1+getHeight()*.75-r1/2)>220)&&((int)(ypos1+getHeight()*.75-r1/2)<280)){
            Main.frame.remove(Main.level1); //Remove Panel1
            Main.lvl2(); //Remove Panel2
            Main.frame.revalidate();
            Main.frame.repaint();
        }
        //Bounds for the walls
        if((int)(xpos1+getWidth()*.25)<(getWidth()/10)){
            xpos1 +=10;
        }
        if((int)(xpos1+getWidth()*.25)>(int)(getWidth()*8.6/10)){
            xpos1 -=10;
        }
        if((int)(ypos1+getHeight()*.75-r1/2)>(int)(getHeight()*.83)){
            ypos1-=10;
        }
        if((int)(ypos1+getHeight()*.75-r1/2)<(int)(getHeight()*.115)){
            ypos1+=10;
        }
        if(((int)(xpos1+getWidth()*.25)>610)&&((int)(xpos1+getWidth()*.25)<660)&&((int)(ypos1+getHeight()*.75-r1/2)<=480)){
            xpos1-=10;
        }
        if(((int)(xpos1+getWidth()*.25)>410)&&((int)(xpos1+getWidth()*.25)<460)&&((int)(ypos1+getHeight()*.75-r1/2)<=480)){
            xpos1-=10;
        }
        if(((int)(xpos1+getWidth()*.25)<550)&&((int)(xpos1+getWidth()*.25)>540)&&((int)(ypos1+getHeight()*.75-r1/2)<=480)){
            xpos1+=10;
        }
        if(((int)(xpos1+getWidth()*.25)<750)&&((int)(xpos1+getWidth()*.25)>740)&&((int)(ypos1+getHeight()*.75-r1/2)<=480)) {
            xpos1 += 10;
        }
        if(((int)(ypos1+getHeight()*.75-r1/2)<(500))&&((int)(xpos1+getWidth()*.25)>460)&&((int)(xpos1+getWidth()*.25)<540)){
            ypos1+=10;
        }
        if(((int)(ypos1+getHeight()*.75-r1/2)<(500))&&((int)(xpos1+getWidth()*.25)>660)&&((int)(xpos1+getWidth()*.25)<740)){
            ypos1+=10;
        }
        //Coin detection
        if((ypos1+getHeight()*.75-r1/2)<220&&(ypos1+getHeight()*.75-r1/2)>180&&(coin1_1+xpos1+getWidth()*.25)>230&&(coin1_1+xpos1+getWidth()*.25)<270){
            bcoin1_1=true;
            coin1_1+=1000;
            Main.coins++;
            System.out.println("Coins= "+Main.coins);
            repaint();
        }
        if((ypos1+getHeight()*.75-r1/2)<420&&(ypos1+getHeight()*.75-r1/2)>380&&(coin1_2+xpos1+getWidth()*.25)>555&&(coin1_2+xpos1+getWidth()*.25)<595){
            bcoin1_2=true;
            coin1_2+=1000;
            Main.coins++;
            System.out.println("Coins= "+Main.coins);
            repaint();
        }
        //Monster collision
        if((ypos1+getHeight()*.75-r1/2)<mypos1+30&&(ypos1+getHeight()*.75-r1/2)>mypos1-30&&(xpos1+getWidth()*.25)>mxpos1-30&&(xpos1+getWidth()*.25)<mxpos1+30){
            Main.lives--;
            System.out.println("Lives= "+Main.lives);
            xpos1= -100;
            ypos1= 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    static int dY = 1; //Set direction in so that in bounces back and forth as opposed to onwards for infinity
    @Override
    public void actionPerformed(ActionEvent e) {
        mypos1+=(15*dY);
        if(mypos1>550||mypos1<70){
            dY = dY*(-1);
        }
        repaint();
    }
}

class Panel2 extends JPanel implements KeyListener,ActionListener{
    //Variables
    static int coin2_1 = 0; //coin hitbox offset
    static boolean bcoin2_1 = false; //keep track if coin is collected or not
    static int coin2_2 = 0;
    static boolean bcoin2_2 = false;
    int xpos2 = 230;
    int ypos2 = 140;
    int r2 = 40;

    //Monsters
    int mxpos2 = 340;
    int mypos2 = 300;
    int mxpos3 = 615;
    int mypos3 = 550;
    int mxpos4 = 1010;
    int mypos4 = 550;

    public Panel2(){
        Timer timer2 = new Timer(35,this); //Animation for monsters
        timer2.start();
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }
    protected void paintComponent(Graphics g) {//Draw the stuff
        super.paintComponent(g);
        level22(g);
        player2(g,xpos2,ypos2);
        bounds2();
        monster2(g,mxpos2,mypos2,mxpos3,mypos3,mxpos4,mypos4);
        if (!bcoin2_1) { // Draw coin only if not collected
            g.setColor(Color.BLACK);
            g.drawOval(640, 350, 51, 51);
            g.setColor(Color.YELLOW);
            g.fillOval(640, 350, 50, 50);
        }
        if (!bcoin2_2) { // Draw coin only if not collected
            g.setColor(Color.BLACK);
            g.drawOval(1010, 300, 51, 51);
            g.setColor(Color.YELLOW);
            g.fillOval(1010, 300, 50, 50);
        }
    }
    public void monster2(Graphics g, int mxpos2, int mypos2, int mxpos3, int mypos3, int mxpos4, int mypos4){
        g.setColor(Color.RED);
        g.fillOval(mxpos2,mypos2,60,60);
        g.fillOval(mxpos3,mypos3,60,60);
        g.fillOval(mxpos4,mypos4,60,60);
    }
    public void bounds2(){
        //In case of loss
        if(Main.lives==0){
            Main.frame.remove(Main.level2);
            Main.lvlLOSS();
            Main.frame.revalidate();
            Main.frame.repaint();
        }
        //Walls
        if(xpos2<100){
            xpos2+=10;
        }
        if(xpos2>1140){
            xpos2-=10;
        }
        if(ypos2<100){
            ypos2+=10;
        }
        if(ypos2>580){
            ypos2-=10;
        }
        if(xpos2>360&&xpos2<430&&ypos2<450){
            xpos2-=10;
        }
        if(xpos2>500&&xpos2<560&&ypos2<450){
            xpos2+=10;
        }
        if(xpos2>400&&xpos2<550&&ypos2<450){
            ypos2+=10;
        }
        if(ypos2>210&&xpos2>680&&xpos2<830){
            ypos2-=10;
        }
        if(ypos2>210&&xpos2>670&&xpos2<720){
            xpos2-=10;
        }
        if(ypos2>250&&xpos2<850&&xpos2>830){
            xpos2+=10;
        }
        //Coin
        if(xpos2>600+coin2_1&&xpos2<coin2_1+680&&ypos2<390&&ypos2>310){
            bcoin2_1=true;
            Main.coins++;
            System.out.println("Coins= "+Main.coins);
            coin2_1+=1000;
        }
        if(xpos2<1050+coin2_2&&xpos2>coin2_2+970&&ypos2<340&&ypos2>260){
            bcoin2_2=true;
            Main.coins++;
            System.out.println("Coins= "+Main.coins);
            coin2_2+=1000;
        }
        //Monster collision
        if(xpos2<mxpos2+30&&xpos2>mxpos2-30&&ypos2>mypos2-30&&ypos2<mypos2+30){
            xpos2=230;
            ypos2=140;
            Main.lives--;
            System.out.println("Lives= "+Main.lives);
        }
        if(xpos2<mxpos3+30&&xpos2>mxpos3-30&&ypos2>mypos3-30&&ypos2<mypos3+30){
            xpos2=230;
            ypos2=140;
            Main.lives--;
            System.out.println("Lives= "+Main.lives);
        }
        if(xpos2<mxpos4+30&&xpos2>mxpos4-30&&ypos2>mypos4-30&&ypos2<mypos4+30){
            xpos2=230;
            ypos2=140;
            Main.lives--;
            System.out.println("Lives= "+Main.lives);
        }
        //Next level
        if(xpos2>865&&xpos2<935&&ypos2>480&&ypos2<550){
            Main.frame.remove(Main.level2); // Remove Panel2
            Main.lvl3(); // Add Panel3
            Main.frame.revalidate();
            Main.frame.repaint();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {//Movement
        int step = 10;
        if(e.getKeyCode()==KeyEvent.VK_R){ // Go next level for development purposes
            Main.frame.remove(Main.level2); // Remove Panel2
            Main.lvl3(); // Add Panel3
            Main.frame.revalidate();
            Main.frame.repaint();
        }
        if (e.getKeyCode()==KeyEvent.VK_W){//Move up
            moveCircle2(0, -step);
        }
        if (e.getKeyCode()==KeyEvent.VK_S){//Move down
            moveCircle2(0, step);
        }
        if (e.getKeyCode()==KeyEvent.VK_A){//Mode left
            moveCircle2(-step, 0);
        }
        if (e.getKeyCode()==KeyEvent.VK_D){//Move right
            moveCircle2(step, 0);
        }
        repaint();
    }
    public void moveCircle2(int deltax, int deltay){//Method to move player
        xpos2+=deltax;
        ypos2+=deltay;
    }
    public void level22(Graphics g){//Draws the physical level
        g.fillRect(0, 0, 1280, 100);
        g.fillRect(0, 0, 100, 720);
        g.fillRect(0, 620, 1280, 100);
        g.fillRect(1180, 0, 100, 720);
        g.fillRect(400,100,150,350);
        g.fillRect(700, 250, 150, 500);
        g.setColor(Color.BLUE);
        g.fillOval(900, 520,60,60);
    }
    public void player2(Graphics g,int xpos2, int ypos2){//draw the player
        this.ypos2=ypos2;
        this.xpos2=xpos2;
        g.setColor(Color.GREEN);
        g.fillOval(xpos2,ypos2,r2,r2);
        g.setColor(Color.BLACK);
        g.drawOval(xpos2,ypos2,r2+1,r2+1);
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    static int direction1 = -1;//Set direction to the opposite way if the monster reaches a wall
    static int direction2 = -1;
    @Override
    public void actionPerformed(ActionEvent e) {
        if(mxpos2<100||mxpos2>1140){
            direction1=direction1*-1;
        }
        mxpos2+=17*direction1;
        if(mypos3<100||mypos3>580){
            direction2=direction2*-1;
        }
        mypos3+=10*direction2;
        mypos4+=10*direction2;
        repaint();
    }
}

class Panel3 extends JPanel implements KeyListener,ActionListener{
    public static Timer timer3;
    int xpos3 = 1040;
    int ypos3 = 450;
    public static int coin3_1 = 0;
    public static boolean bcoin3_1 = false;
    public int mxpos5 = 930;
    static int mypos5 = 200;
    static int mxpos6 = 770;
    static int mypos6 = 450;
    static int mxpos7= 330;
    static int mypos7=200;
    static int mxpos8=170;
    static int mypos8=450;
    Panel3(){
        timer3 = new Timer(25,this); //Animation for monsters
        timer3.start();
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        loadlevel3(g);
        monster3(g);
        player3(g);
        if(!bcoin3_1){
            g.setColor(Color.YELLOW);
            g.fillOval(170,170,50,50);
            g.setColor(Color.BLACK);
            g.drawOval(170,170,50,50);
        }
        bounds3(g);
    }
    public void bounds3(Graphics g){
        //In case of loss
        if(Main.lives==0){
            Main.frame.remove(Main.level3);
            Main.lvlLOSS();
            Main.frame.revalidate();
            Main.frame.repaint();
        }
        //Walls
        if(xpos3<150){
            xpos3+=10;
        }
        if(xpos3>1080){
            xpos3-=10;
        }
        if(ypos3<150){
            ypos3+=10;
        }
        if(ypos3>520){
            ypos3-=10;
        }
        if(xpos3>610&&ypos3<430&&ypos3>420){
            ypos3+=10;
        }
        if(xpos3>610&&ypos3<310&&ypos3>290){
            ypos3-=10;
        }
        //Coin
        if(xpos3<coin3_1+200&&xpos3>coin3_1+140&&ypos3<200&&ypos3>140){
            bcoin3_1=true;
            Main.coins++;
            System.out.println("Coins= "+Main.coins);
            coin3_1+=1280;
        }
        //Monster
        if(xpos3<mxpos5+55&&xpos3>mxpos5-20&&ypos3>mypos5-55&&ypos3<mypos5+55){
            xpos3 = 1040;
            ypos3 = 450;
            Main.lives--;
            System.out.println("Lives= "+Main.lives);
        }
        if(xpos3<mxpos6+55&&xpos3>mxpos6-20&&ypos3>mypos6-55&&ypos3<mypos6+55){
            xpos3 = 1040;
            ypos3 = 450;
            Main.lives--;
            System.out.println("Lives= "+Main.lives);
        }
        if(xpos3<mxpos7+55&&xpos3>mxpos7-20&&ypos3>mypos7-55&&ypos3<mypos7+55){
            xpos3 = 1040;
            ypos3 = 450;
            Main.lives--;
            System.out.println("Lives= "+Main.lives);
        }
        if(xpos3<mxpos8+55&&xpos3>mxpos8-20&&ypos3>mypos8-55&&ypos3<mypos8+55){
            xpos3 = 1040;
            ypos3 = 450;
            Main.lives--;
            System.out.println("Lives= "+Main.lives);
        }
        //Goal
        if(xpos3>1050&&xpos3<1100&&ypos3<250&&ypos3>150){
            g.drawString("Press T for your reward", 600, 200);
        }

    }
    public void player3(Graphics g){
        g.setColor(Color.GREEN);
        g.fillOval(xpos3,ypos3,40,40);
        g.setColor(Color.BLACK);
        g.drawOval(xpos3,ypos3,40,40);
    }
    public void loadlevel3(Graphics g){
        g.fillRect(0,0, 1280,150);
        g.fillRect(0,570, 1280,150);
        g.fillRect(0,0, 150,720);
        g.fillRect(1130,0, 150,720);
        g.fillRect(640,310, 640,100);
        g.setColor(Color.BLUE);
        g.fillOval(1040,200,70,70);
    }
    public void monster3(Graphics g){
        g.setColor(Color.RED);
        g.fillOval(mxpos5,mypos5,70,70);
        g.fillOval(mxpos6,mypos6,70,70);
        g.fillOval(mxpos7,mypos7,70,70);
        g.fillOval(mxpos8,mypos8,70,70);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int step = 10;
        if(e.getKeyCode()==KeyEvent.VK_T){ // Go next level for development purposes
            Main.frame.remove(Main.level3); // Remove Panel3
            Main.lvlWIN(); // Finish the game by loading the win panel
            Main.frame.revalidate();
            Main.frame.repaint();
        }
        if (e.getKeyCode()==KeyEvent.VK_W){//Move up
            moveCircle3(0, -step);
        }
        if (e.getKeyCode()==KeyEvent.VK_S){//Move down
            moveCircle3(0, step);
        }
        if (e.getKeyCode()==KeyEvent.VK_A){//Mode left
            moveCircle3(-step, 0);
        }
        if (e.getKeyCode()==KeyEvent.VK_D){//Move right
            moveCircle3(step, 0);
        }
    }
    public void moveCircle3(int deltax, int deltay){
        xpos3+=deltax;
        ypos3+=deltay;
    }
    static int direction5 = 1;
    static int direction6 = -1;
    @Override
    public void actionPerformed(ActionEvent e) {
        if(mypos5<150||mypos5>520){
            direction5=direction5*-1;
        }
        if(mypos6<150||mypos6>520){
            direction6=direction6*-1;
        }
        mypos5+=15*direction5;
        mypos7+=15*direction5;
        mypos6+=15*direction6;
        mypos8+=15*direction6;
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}

class PanelL extends JPanel implements KeyListener{
    PanelL(){
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bigL(g);
    }
    public void bigL(Graphics g){//Displayes text
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("You Lose, press spacebar to try again",480,360);
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){//Set everything to zero and restart
            Panel1.coin1_1=0;
            Panel1.coin1_2=0;
            Panel1.bcoin1_1=false;
            Panel1.bcoin1_2=false;
            Panel2.coin2_1=0;
            Panel2.coin2_2=0;
            Panel2.bcoin2_1=false;
            Panel2.bcoin2_2=false;
            Panel3.coin3_1=0;
            Panel3.bcoin3_1=false;
            Main.lives=3;
            Main.coins=0;
            Main.frame.remove(Main.loseScreen);
            Main.lvl1();
            revalidate();
            repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}

class PanelW extends JPanel{
    PanelW(){
        setFocusable(true);
        requestFocusInWindow();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bigL(g);
    }
    public void bigL(Graphics g){
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("yay you win",600,50);
        g.drawString("You collected "+Main.coins+"/5 coins,",580,400);
        if(Main.lives>1) {
            g.drawString("You had " + Main.lives + " lives remaining.", 580, 500);
        }
        if(Main.lives==1) {
            g.drawString("You had " + Main.lives + " life remaining.", 580, 500);
        }
    }
}