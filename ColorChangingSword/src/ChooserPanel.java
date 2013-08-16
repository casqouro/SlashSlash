import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/* Author: Oracle's Java Tutorials
 * Modifications: Matthew Frank
 */

public class ChooserPanel extends AbstractColorChooserPanel
                        implements ActionListener
{
    JToggleButton redButton;
    JToggleButton blueButton;
    JToggleButton yellowButton;
    JToggleButton greenButton;   
        
    /******************InstallColorChooser Source Code**************************
     * 
     * Source pulled from GrepCode; search for installChooserPanel
     * 
     * One of the issues I faced when trying to understand what this did and
     * why it was necessary was that I overrode this method and only left the
     * method call to buildChooser();
     * 
     * The result was that (I believe) the PropertyChangeListener was not
     * attached, meaning the method call getColorSelectionModel() performed
     * within actionPerformed, found below, would return null!
     * 
     * Typically you'd want to call it this way:
     * getColorSelectionModel().setSelectedColor(color)
     * for the purpose of setting the new color selected by the user.  If you
     * attempt to perform this full call you receive a null pointer exception
     * since you're attempting to stuff a color into, nowhere, basically.
     * 
     * 
     151    public void installChooserPanel(JColorChooser enclosingChooser) {
     152        if (chooser != null) {
     153            throw new RuntimeException ("This chooser panel is already installed");
     154        }
     155        chooser = enclosingChooser;
     156        chooser.addPropertyChangeListener("enabled", enabledListener);
     157        setEnabled(chooser.isEnabled());
     158        buildChooser();
     159        updateChooser();
     160    }
     **************************************************************************/ 
    
    /* I know this is called as part of installColorChooser(), but the API docs
     * also note that it's called whenever the model is updated.  Digging into
     * the model API + source was a quagmire I wanted to avoid after how much
     * work I put into understanding this.  It would be nice to understand where
     * and how this is called though as it DOES seem to be getting called every
     * time a new color is selected. */
        
    @Override
    public void updateChooser() 
    {
        Color color = getColorFromModel();
        
        if(Color.red.equals(color))
        { redButton.setSelected(true); }
        else if (Color.blue.equals(color))
        { blueButton.setSelected(true); }
        else if (Color.yellow.equals(color))
        { yellowButton.setSelected(true); }
        else if (Color.green.equals(color))
        { greenButton.setSelected(true); }        
    }
    
    /* buildChooser() utilizes a ButtonGroup, which only really serves to
     * enable sole selection of a single butt on in the group.  The buttons are
     * also added to the panel for other/main functionalities.
     */
    
    @Override
    protected void buildChooser() 
    {        
        ButtonGroup myColors = new ButtonGroup();
        
        redButton = new JToggleButton(new ImageIcon("colorgifs/redburst.png"));
        redButton.setActionCommand("red");
        redButton.addActionListener(this);
        blueButton = new JToggleButton(new ImageIcon("colorgifs/blueburst.png"));
        blueButton.setActionCommand("blue");
        blueButton.addActionListener(this);
        yellowButton = new JToggleButton(new ImageIcon("colorgifs/yellowburst.png"));
        yellowButton.setActionCommand("yellow");
        yellowButton.addActionListener(this);
        greenButton = new JToggleButton(new ImageIcon("colorgifs/greenburst.png"));
        greenButton.setActionCommand("green");
        greenButton.addActionListener(this);        
        
        myColors.add(redButton);
        myColors.add(blueButton);
        myColors.add(yellowButton);
        myColors.add(greenButton);
        
        add(redButton);
        add(blueButton);
        add(yellowButton);
        add(greenButton);
    }

    /* Gets the String-based named of the ActionCommand previously attached to
     * the button, such as "red" or "blue".  It then matches that with one of
     * the if/else statements and stores an associated color in the newColor
     * variable.  This is then passed to the ColorSelectionModel as the current
     * color. 
     */
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Color newColor = null;
        String command = ((JToggleButton)e.getSource()).getActionCommand();
        
        switch (command) 
        {
            case "red":
                newColor = Color.red;
                break;
            case "blue":
                newColor = Color.blue;
                break;
            case "yellow":
                newColor = Color.yellow;
                break;
            case "green":
                newColor = Color.green;
                break;
        }
        
        getColorSelectionModel().setSelectedColor(newColor);
    }    
   
    @Override
    public String getDisplayName() 
    { return "Fill Me In"; }

    @Override
    public Icon getSmallDisplayIcon() 
    { return null; }

    @Override
    public Icon getLargeDisplayIcon() 
    { return null; }
}