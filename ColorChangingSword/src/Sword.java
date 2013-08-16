
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/* Author: Matthew Frank
 * Project: 
 * Date Started: 9
 * Date Completed:  
 */
public class Sword 
{
    BufferedImage newSwordImage;
    Image theSwordImage;
    
    final private int originX = 200;
    final private int originY = 200;
    final private int height;
    final private int width;
    double angleTransform;
    Color weaponColor;
    DecimalFormat help;
        
    Sword() throws IOException
    {
        angleTransform = 0.0;
        newSwordImage = ImageIO.read(new File("colorgifs/gimpsword2.png"));
        theSwordImage = (Image)newSwordImage;
        height = theSwordImage.getHeight(null);
        width = theSwordImage.getWidth(null);
        weaponColor = Color.white;
        help = new DecimalFormat("#.####");
    }
               
    public void paintComponent(Graphics g) throws RasterFormatException
    {    
        Graphics2D g2d = (Graphics2D)g;
                  
        double locationX = newSwordImage.getWidth() / 2;
        double locationY = newSwordImage.getHeight();
                
        try 
        {
            AffineTransform trans = AffineTransform.getRotateInstance(Math.toRadians(getAngleTransform()), locationX, locationY);
            //System.out.println("Radians: " + Math.toRadians(getAngleTransform()) + "   Angle: " + getAngleTransform());
            AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BILINEAR);         
            g2d.drawImage(op.filter(newSwordImage, null), 200, 200, getWeaponColor(), null);         
        } 
        catch (RasterFormatException ex) 
        {
            System.out.println("Bad things have happened, see the note in source re: large numbers...");
            Logger.getLogger(Sword.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
            
    /* I limited the arc of the sword since enemies will only be foreward.  This
     * also, incidentally, eliminated the occurrence of a show-stopping
     * exception.  I believe it was caused by excessive angle values.
     * 
     * I believe large angle values produced excessive radian values which the
     * getRotateInstance() method could not tolerate.
     * 
     * The intended solution was to limit angle and radian values to 2-4 decimal
     * places.  However, since that now seems like extra work to be avoided I'm
     * not going to implement that limitation, but rather leave this note should
     * the issue resurface. */
    
    public void setAngleTransform(double a)
    { 
        /* transformRate determines the rate at which the sword suffers 
         * rotational transformation or "swings".  Dividing by greater numbers
         * slows it increasingly. */
        double transformRate = a / 5;
        double newTransform = angleTransform + transformRate;
        
        /* "0 degrees" is straight up, rather than straight right.  To limit the
        * arc of the sword to 3/4ths of a circle it's effectively limited to 
        * between -135 and +135, rather than -45 and +225. */        
        if(newTransform > -136 && newTransform < 136)
        {
            angleTransform = newTransform;
        } 
    }
    
    public void setWeaponColor(Color a)
    { weaponColor = a; }
    
    public Color getWeaponColor()
    { return weaponColor; }    
    
    public double getAngleTransform()
    { return angleTransform; }
    
    public int getOriginX()
    { return originX; }
    
    public int getOriginY()
    { return originY; }  
    
    public int getHeight()
    { return height; }
}