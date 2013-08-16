import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/* Author: Matthew Frank */

/*
 * NOTES
 * 
 * 1.  Sword Movement
 * 
 * 
 * 1.  Color + Particle Effects
 *     1a.  Sword coloring needs to be limited to the sword - currently it
 *          colors the whole background.  A base color for the sword should also
 *          be considered.
 *     1b.  Particle effects need to be developed.
 * 2.  Implement a more realistic swing
 * 3.  Implement a stab
 * 4.  Implement powering up attacks
 */

public class SwordPanel extends JPanel implements ChangeListener, MouseMotionListener
{
    Sword myRealSword = new Sword();
    JColorChooser newCC;     
    Point originLoc;
    Point tipLoc;
    Point cursorLoc;
    int counter;
    
    double result[][];
        
    /* Re: "Leaking in this constructor" warning message
     * July 7th, 2013
     * 
     * Using "this" as an argument in a constructor is dangerous because what
     * "this" refers to may not have been fully initialized.
     * 
     * In this instance since getSelectionModel() is adding its own change
     * listener which it, itself initialized upon its creation, it's probably
     * fine. */
    
    public SwordPanel() throws IOException
    {           
        counter = 0;
        result = new double[10][10];
        
        setBackground(Color.gray);

        originLoc = new Point(275, 280);
        tipLoc = new Point(275, 200);
        cursorLoc = new Point(0, 0);
        
        /* ColorChooser setup follows */
        newCC = new JColorChooser(Color.red);
        newCC.setPreviewPanel(new JPanel());
        AbstractColorChooserPanel panels[] = {new ChooserPanel()};         
        newCC.setChooserPanels(panels);
        this.setBorder(BorderFactory.createLineBorder(Color.black));        
        newCC.getSelectionModel().addChangeListener(this);
        add(newCC);
        
        // Adds the MML to the panel
        addMouseMotionListener(this);     
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        myRealSword.paintComponent(g);             
    }
    
    /* It would be nice to dig into the repaint() method and understand how it
     * works its magic better.  I understand that it's inherited from Component,
     * JContainer, and JComponent. */
    @Override
    public void stateChanged(ChangeEvent e) 
    { 
        myRealSword.setWeaponColor(newCC.getColor());
        repaint(); 
    }
    
    @Override
    public void mouseDragged(MouseEvent me)
    {}
    

    
    public double distanceBetweenPoints(Point a, Point b)
    {
        return Math.sqrt(Math.abs(a.x - b.x)^2 + Math.abs(a.y - b.y)^2);
        
        /* From the origin to the tip of the sword, also the height of the image
         * unless the image is changed, since the image is being rotated and the
         * tip of the sword points straight to the top.
         */
        //lengthOne = myRealSword.getHeight();
        //System.out.println(lengthOne);
          
        /* From the origin to the cursor, the origin being at the middle + base
         * of the image and the cursor being wherever the user has clicked
         */
        //xLength = Math.abs(myRealSword.getOriginX() + 75 - me.getX());
        //yLength = Math.abs(myRealSword.getOriginY() + 80 - me.getY());
        //lengthTwo = Math.sqrt(xLength*xLength + yLength*yLength);
        //System.out.println(lengthTwo);
        
        /* From the tip of the sword to the cursor... 
         */
        //xLength = Math.abs( - me.getX());
        //yLength = Math.abs( - me.getY());
        //lengthThree = Math.sqrt(xLength*xLength + yLength*yLength);
        //System.out.println(lengthThree);        
    }    
    
    public double calculateSSSAngle(double a, double b, double c)
    {
        //System.out.println("Angle: " + Math.cos((a*a + c*c - b*b) / 2*a*c));
               
        /*if(counter < 10)
        {
            // Math.cos utilizes radians!
            // This value is in degrees
            result[counter][9] = Math.toDegrees(Math.acos((a*a + c*c - b*b) / 2*a*c));
        }*/       
        
        counter += 1;

        DecimalFormat format=new DecimalFormat("#.##");     
        
        /*if(counter == 10)
        {
            for(int lister = 0; lister < 10; lister++)
            {
                System.out.println(
                        "Ox = " + result[lister][0] + " " +
                        "Oy = " + result[lister][1] + " " + 
                        "Tx = " + result[lister][2] + " " +
                        "Ty = " + result[lister][3] + " " +
                        "Cx = " + result[lister][4] + " " +
                        "Cy = " + result[lister][5] + " " +
                        "sideOne = " + format.format(result[lister][6]) + " " +
                        "sideTwo = " + format.format(result[lister][7]) + " " +
                        "sideThree = " + format.format(result[lister][8]) + " " +
                        "Angle = " + format.format(result[lister][9]));
            }
        }*/
        
        /* WHAT THE FUCK IS GOING ON HERE?
         * 1.  Had an exception which halted the program after using it for a while
         * 2.  Shouldn't I be using acos instead of cos?
         * 3.  Enable sword to move left, not just right
         * 4.  Moving left subtracts (transform left), moving right adds (transform right)
         */
        //System.out.println(Math.cos((a*a + c*c - b*b) / (2*a*c)));
        return Math.toDegrees(Math.cos((a*a + c*c - b*b) / (2*a*c)));
    }    

    @Override
    public void mouseMoved(MouseEvent me) 
    {
        /* 1.  originLoc should be constant, is set in the constructor, should not
         * be reset
         * 
         * 2.  tipLoc is initially set via the constructor, but since the sword
         * is always moved to the cursorLoc, the *previous* cursorLoc will have
         * the correct current tipLoc, and that should be copied here.
         * 
         * 3.  cursorLoc should always be set from the reported cursor location.
         */
        tipLoc.setLocation(cursorLoc.getX(), cursorLoc.getY());
        cursorLoc.setLocation(me.getX(), me.getY());
        
        /*if(counter < 10)
        {
            result[counter][0] = originLoc.getX();
            result[counter][1] = originLoc.getY();
            result[counter][2] = tipLoc.getX();
            result[counter][3] = tipLoc.getY();         
            result[counter][4] = cursorLoc.getX();
            result[counter][5] = cursorLoc.getY();        
        }*/
        
        // The numbers seem like they might be off
        double sideOne = distanceBetweenPoints(originLoc, tipLoc);
        double sideTwo = distanceBetweenPoints(tipLoc, cursorLoc);
        double sideThree = distanceBetweenPoints(cursorLoc, originLoc);
        //System.out.println(sideOne + " + " + sideTwo + " + " + sideThree);    
        
        if(counter < 10)
        {
            result[counter][6] = sideOne;
            result[counter][7] = sideTwo;
            result[counter][8] = sideThree;
        }        
                
        if(cursorLoc.x < tipLoc.x)
        {
            //System.out.println(-calculateSSSAngle(sideOne, sideTwo, sideThree));            
            myRealSword.setAngleTransform(-calculateSSSAngle(sideOne, sideTwo, sideThree));
        }
        else
        {
            //System.out.println(calculateSSSAngle(sideOne, sideTwo, sideThree));              
            myRealSword.setAngleTransform(calculateSSSAngle(sideOne, sideTwo, sideThree));
        }      
        
        repaint();        
    }
}