import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/* Author: Matthew Frank
 * Project: Slash! Slash!
 * Date Started: 6/25/2013
 * Purpose: Cover the Color Changer material presented in the Java Tutorials 
 * by applying it to my own current interests. 
 */

/******************************Runnable*****************************************
 * Looking into Runnables is something I should do, when I have time of course.
 * I used to create a Frame in main, add customized JPanels, pack it, and go.
 * 
 * Since the Java Tutorials suggested using a runnable for SWING components, 
 * I've switched to this, but I haven't dug into learning about the advantages
 * and reasons behind it.  It's ended up being a copy/paste that works, but
 * isn't understood.
 ******************************************************************************/

public class SwordFrame
{
    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    createAndShowGUI();
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(SwordFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static void createAndShowGUI() throws IOException
    {
        JFrame swordFrame = new JFrame("Color Changing Sword");
        swordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        swordFrame.add(new SwordPanel());
        swordFrame.pack();
        swordFrame.setSize(new Dimension(500, 500));
        swordFrame.setVisible(true);        
    }
};