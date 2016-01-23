
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author bettk6516
 */
public class Chambers extends JComponent implements KeyListener, MouseMotionListener, MouseListener {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    //array list for platfroms   
    ArrayList<Rectangle> walls = new ArrayList<>();
    //arraylist for grav-Erevators(elevator like things)
    ArrayList<Rectangle> grav = new ArrayList<>();
    //Array list for collectable cubes 
    ArrayList<Rectangle> pellet = new ArrayList<>();
    //Array list for deadly red squares in evlevators
    ArrayList<Rectangle> bullet = new ArrayList<>();
    //array list for the horizontal projeciles A
    ArrayList<Rectangle> shot = new ArrayList<>();
    //array list for x axis shots 2
    ArrayList<Rectangle> shotB = new ArrayList<>();
    //array list for x axis shots 3
    ArrayList<Rectangle> shotC = new ArrayList<>();
    boolean inAir = false;
    boolean pLeft = false;
    boolean pRight = false;
    boolean pjump = false;
    boolean prevJump = false;
    boolean elevator = false;
    Rectangle player = new Rectangle(390, 300, 20, 20);
   Rectangle win = new Rectangle(1000,1000,800,600);
   Rectangle loose = new Rectangle(1000,1000,800,600);
    int moveX = 0;
    int movey = 0;
    int frameCount = 0;
    int gravity = 1;
    int points = 0;
    
    int pelletLocX = 50;
    int PelletLocy =50;
    //if i feeling like putting lives in . AKA if their is time
    int lives = 3;
    BufferedImage CUBE = loadImage("CUBE.PNG");

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println("error lading image " + filename);
        }
        return img;
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // GAME DRAWING GOES HERE
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        g.fillRect(-3, -3, 8000, 8000);

        // go through each block
        for (Rectangle block : walls) {
            // draw the block
            g.setColor(Color.BLUE);
            g.fillRect(block.x, block.y, block.width, block.height);
        }

        //grav-Erevators, go through and draw them
        for (Rectangle block : grav) {
            g.setColor(Color.MAGENTA);
            g.fillRect(block.x, block.y, block.width, block.height);
        }


  //draws killing red squres of death in elevaters
        for(Rectangle shell :bullet){
        g.setColor(Color.red);
        g.fillRect(shell.x, shell.y, shell.height, shell.width);
        }

        //draws killing red squres of death on the x axis
        for(Rectangle shell:shot){
        g.setColor(Color.red);
        g.fillRect(shell.x, shell.y, shell.height, shell.width);
        }
        for(Rectangle shell:shotB){
            g.setColor(Color.red);
            g.fillRect(shell.x, shell.y, shell.height, shell.width);
        }
        
        for(Rectangle shell:shotC){
            g.setColor(Color.RED);
    g.fillRect(shell.x, shell.y, shell.height, shell.width);
        }
        
        //pellets
        for (Rectangle block : pellet) {
            g.setColor(Color.YELLOW);
            g.fillRect(block.x, block.y, block.width, block.height);
        }
        
        //draws player and puts cube over 
        g.setColor(Color.CYAN);
        g.fillRect(player.x, player.y, player.width, player.height);
        g.drawImage(CUBE, player.x, player.y, player.width, player.height, null);
      
        
        // win/loose screens
        g.setColor(Color.BLACK);
        g.fillRect(loose.x, loose.y, loose.width, loose.height);
        g.fillRect(win.x, win.y, win.width, win.height);
        // GAME DRAWING ENDS HERE
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {



        //prevents exit of the bounderys 
        walls.add(new Rectangle(-3, -3, 3, 600));
        walls.add(new Rectangle(800, 0, 3, 600));
        walls.add(new Rectangle(0, -4, 9000, 10));

        //platfroms
        walls.add(new Rectangle(500, 500, 300, 20));
        walls.add(new Rectangle(2, 500, 300, 20));
        walls.add(new Rectangle(375, 500, 50, 20));

        walls.add(new Rectangle(500, 400, 300, 20));
        walls.add(new Rectangle(2, 400, 300, 20));
        walls.add(new Rectangle(375, 400, 50, 20));

        walls.add(new Rectangle(500, 300, 300, 20));
        walls.add(new Rectangle(2, 300, 300, 20));
        walls.add(new Rectangle(375, 300, 50, 20));

        walls.add(new Rectangle(500, 200, 300, 20));
        walls.add(new Rectangle(2, 200, 300, 20));
        walls.add(new Rectangle(375, 200, 50, 20));

        walls.add(new Rectangle(500, 100, 300, 20));
        walls.add(new Rectangle(2, 100, 300, 20));
        walls.add(new Rectangle(375, 100, 50, 20));

        //grav-Erevators
        grav.add(new Rectangle(317, 0, 45, 9000));
        grav.add(new Rectangle(440, 0, 45, 9000));


        //pellets
        pellet.add(new Rectangle(50, 50, 25, 25));
        
        //bullet projectile
       //down elevators
        bullet.add(new Rectangle(317,45,45,45));
        bullet.add(new Rectangle(440,250,45,45));
        
        //shots on x axis
        shot.add(new Rectangle(760,80,10,10));
        shot.add(new Rectangle(10,580,10,10));
        
        shotB.add(new Rectangle(760,190,10,10));
        shotB.add(new Rectangle(10,480,10,10));
        
        shotC.add(new Rectangle(760,290,10,10));
        shotC.add(new Rectangle(15,380,10,10));
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            //generates random pellet location
            int pelletSpot = (int) (Math.random() * 29) + 1;
      //if player collects 20 pellets win screen is displaed 
            if(points == 20){
            win.x =0;
                win.y = 0;
      }
        //if player looses all lives Lose scrren
            if(lives == 0){
                loose.x =0;
                loose.y = 0;
            }
            
      
            //player movement
            if (pLeft) {
                moveX = -3;
            } else if (pRight) {
                moveX = 3;
            } else {
                moveX = 0;
            }

            // if feet of player become lower than the screen   
            if (player.y + player.height > HEIGHT) {
                // stops the falling
                player.y = HEIGHT - player.height;

                inAir = false;
            }

            // move the player
            player.x = player.x + moveX;
            player.y = player.y + movey;


            frameCount++;

            if (frameCount >= 1) {
                // gravity pulling player down
                movey = movey + gravity;
                frameCount = 0;
            }


            //jumping
            // jump being pressed and not in the air
            if (pjump && !prevJump && !inAir) {
                // make a big change in y direction
                movey = -10;
                inAir = true;
            }

            


            // if feet of player become lower than the screen   
            if (player.y + player.height > HEIGHT) {
                // stops the falling
                player.y = HEIGHT - player.height;
                movey = 0;
                inAir = false;
            }


            // go through all walls
            for (Rectangle block : walls) {
                // is the player hitting a block
                if (player.intersects(block)) {
                    // get the collision rectangle
                    Rectangle intersection = player.intersection(block);

                    // fix the x movement
                    if (intersection.width < intersection.height) {
                        // player on the left
                        if (player.x < block.x) {
                            // move the player the overlap
                            player.x = player.x - intersection.width;
                        } else {
                            player.x = player.x + intersection.width;





                        }
                    } else { // fix the y
                        // hit the block with my head
                        if (player.y > block.y) {
                            player.y = player.y + intersection.height;
                            movey = 0;
                        } else {
                            player.y = player.y - intersection.height;
                            movey = 0;
                            inAir = false;
                        }
                    }
                }
            }

            //gravity evevator physics 
            for (Rectangle block : grav) {
                if (player.intersects(block)) {
                    if (elevator == true) {
                        player.y = player.y - 1;
                        movey = -2;
                        inAir = true;

                    }
                }
            }

//pellets 
            for (Rectangle block : pellet) {
                if (player.intersects(block)) {


                    points ++;
                  //changes pellet location randomly
                    //far left pellet spots
                    if (pelletSpot == 1) {
                        block.x = 50;
                        block.y = 50;
                    }
                    if (pelletSpot == 2) {
                        block.x = 50;
                        block.y = 150;
                    }
                    if (pelletSpot == 3) {
                        block.x = 50;
                        block.y = 250;
                    }
                    if (pelletSpot == 4) {
                        block.x = 50;
                        block.y = 350;
                    }
                    if (pelletSpot == 5) {
                        block.x = 50;
                        block.y = 450;
                    }
                    if (pelletSpot == 6) {
                        block.x = 50;
                        block.y = 550;
                    }
                  //left pellet spots
                    if (pelletSpot == 7) {
                        block.x = 200;
                        block.y = 50;
                    }
                    if (pelletSpot == 8) {
                        block.x = 200;
                        block.y = 150;
                    }
                    if (pelletSpot == 9) {
                        block.x = 200;
                        block.y = 250;
                    }
                    if (pelletSpot == 10) {
                        block.x = 200;
                        block.y = 350;
                    }
                    if (pelletSpot == 11) {
                        block.x = 200;
                        block.y = 450;
                    }
                    if (pelletSpot == 12) {
                        block.x = 200;
                        block.y = 550;
                    }
                   //mid
                    if (pelletSpot == 13) {
                        block.x = 390;
                        block.y = 50;
                    }
                    if (pelletSpot == 14) {
                        block.x = 390;
                        block.y = 150;
                    }
                    if (pelletSpot == 15) {
                        block.x = 390;
                        block.y = 250;
                    }
                    if (pelletSpot == 16) {
                        block.x = 390;
                        block.y = 350;
                    }
                    if (pelletSpot == 17) {
                        block.x = 390;
                        block.y = 450;
                    }
                    if (pelletSpot == 18) {
                        block.x = 390;
                        block.y = 550;
                    }
                   //right
                    if (pelletSpot == 19) {
                        block.x = 510;
                        block.y = 50;
                    }
                    if (pelletSpot == 20) {
                        block.x = 510;
                        block.y = 150;
                    }
                    if (pelletSpot == 21) {
                        block.x = 510;
                        block.y = 250;
                    }
                    if (pelletSpot == 22) {
                        block.x = 510;
                        block.y = 350;
                    }
                    if (pelletSpot == 23) {
                        block.x = 510;
                        block.y = 450;
                    }
                    if (pelletSpot == 24) {
                        block.x = 510;
                        block.y = 550;
                    }
                   //far right
                    if (pelletSpot == 25) {
                        block.x = 750;
                        block.y = 50;
                    }
                    if (pelletSpot == 26) {
                        block.x = 750;
                        block.y = 150;
                    }
                    if (pelletSpot == 27) {
                        block.x = 750;
                        block.y = 250;
                    }
                    if (pelletSpot == 28) {
                        block.x = 750;
                        block.y = 350;
                    }
                    if (pelletSpot == 29) {
                        block.x = 750;
                        block.y = 450;
                    }
                    if (pelletSpot == 30) {
                        block.x = 750;
                        block.y = 550;
                    }
                }
            }

            //bullet muder + move abilty
            for(Rectangle shell: bullet){
              shell.y ++;
              if(points >= 10){
                        shell.y +=3;
                    }
              if(shell.y >600){
                shell.y = 0;
            }
                if(player.intersects(shell)){
                    player.x = 390;
                    player.y =300;
                    lives --;
                    
                }
            }

            for(Rectangle shell: shot){
                
                
               if(shell.x <0){
                   shell.x = 760;
               }
                
                if(points >= 5){
                    
                    shell.x --;
                    if(points >= 10){
                        shell.x -=3;
                    }
            if(player.intersects(shell)){
                 player.x = 390;
                    player.y =300;
                    lives --;
            }
                }
                
            }
 for(Rectangle shell: shotB){
                
                
               if(shell.x <0){
                   shell.x = 760;
               }
                
                if(points >= 5){
                   if(points >= 10){
                        shell.x -=3;
                    } 
                    shell.x --;
            if(player.intersects(shell)){
                 player.x = 390;
                    player.y =300;
                    lives --;
            }
                }
                
            }
            for(Rectangle shell: shotC){
                
                
               if(shell.x >800){
                   shell.x = 0;
               }
                
                if(points >= 5){
                    
                    shell.x ++;
                    if(points >= 10){
                        shell.x +=3;
                    }
            if(player.intersects(shell)){
                 player.x = 390;
                    player.y =300;
                    lives --;
            }
                }
                
            }
            
            
            System.out.println(points);
            // GAME LOGIC ENDS HERE 

            // update the drawing (calls paintComponent)
            repaint();



            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        Chambers game = new Chambers();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        frame.addKeyListener(game); //keyboard


        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            pLeft = true;
        } else if (key == KeyEvent.VK_RIGHT) {
            pRight = true;
        } else if (key == KeyEvent.VK_SPACE) {
            pjump = true;
            elevator = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            pLeft = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            pRight = false;
        } else if (key == KeyEvent.VK_SPACE) {
            pjump = false;
            elevator = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
