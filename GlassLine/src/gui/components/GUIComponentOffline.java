
package gui.components;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import shared.ImageIcons;
import shared.enums.MachineType;
import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;

/**
 * GUIComponentoffline is the superclass of GUI components off the conveyor
 */
@SuppressWarnings("serial")
public class GUIComponentOffline extends GuiAnimationComponent implements ActionListener, Serializable
{
	/**
	 * The popup for the offline component
	 */
	GUIPopUp myPopUp;

	MachineType type;

	Integer index;

	Integer popUpIndex;

	TChannel channel;
	boolean glassBroken=false;
	boolean machineBroken =false;
	/**
	 * Frame counter
	 */
	int counter = 0;
	int roundCounter=0;

	ImageIcon myIcon;
	ImageIcon badIcon;
	/**
	 * List of icons for animations
	 */
	ArrayList<ImageIcon> imageicons = new ArrayList<ImageIcon>();

	/**
	 * Constructor for GUIComponentOffline
	 */
	public GUIComponentOffline(MachineType type, Transducer t)
	{
		super();
		transducer = t;
		this.type = type;
		initializeImages();

	}
	
	int speedDown = 1;
	/**
	 * Method that initializes the imageicons for the specific machines
	 * based on the MachineType enum
	 */
	public void initializeImages()
	{
		if (type == MachineType.CROSS_SEAMER)
		{
			imageicons = (ArrayList<ImageIcon>)ImageIcons.getIconList("crossSeamer");
			myIcon = new ImageIcon("imageicons/glassImage_CROSSSEAMER.png");
			badIcon = new ImageIcon("imageicons/crossSeamerBroken.png");
			
			channel = TChannel.CROSS_SEAMER;
			transducer.register(this, TChannel.CROSS_SEAMER);
		}

		else if (type == MachineType.DRILL)
		{
			imageicons = (ArrayList<ImageIcon>)ImageIcons.getIconList("drill");
			myIcon = new ImageIcon("imageicons/glassImage_DRILL.png");
			badIcon = new ImageIcon("imageicons/drillImageBroken.png");
			channel = TChannel.DRILL;
			transducer.register(this, TChannel.DRILL);

		}
		else if (type == MachineType.GRINDER)
		{
			imageicons = (ArrayList<ImageIcon>)ImageIcons.getIconList("grinder");
			myIcon = new ImageIcon("imageicons/glassImage_GRINDER.png");
			badIcon = new ImageIcon("imageicons/grinderImageBroken.png");
			channel = TChannel.GRINDER;
			transducer.register(this, TChannel.GRINDER);
		}
		setIcon(imageicons.get(0));
		setSize(getIcon().getIconWidth(), getIcon().getIconHeight());
	}

	/**
	 * Method that does the machine animation
	 */
	public void doAnimate()
	{
		if(!glassBroken&&!machineBroken){
			if (roundCounter < speedDown)
			{
				setIcon(imageicons.get(counter));
				if(counter<imageicons.size()-1)
					counter++;
				else if (counter ==imageicons.size()-1)
				{
					counter = 0;
					roundCounter++;
				}
			}
			else
			{
	
				setIcon(imageicons.get(0));
				counter = 0;
				roundCounter=0;
	
				Object[] args = new Object[1];
				args[0] = index;
				animationState = GuiAnimationComponent.AnimationState.DONE;
				transducer.fireEvent(channel, TEvent.WORKSTATION_GUI_ACTION_FINISHED, args);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if (animationState.equals(AnimationState.MOVING))
		{
			if (part != null)
			{
				movePartIn();
			}
		}
		if (animationState.equals(AnimationState.ANIMATING))
		{
			doAnimate();
		}
	}

	@Override
	public void addPart(GUIGlass part)
	{
		this.part = part;
		part.setIcon(myIcon);
	}

	public void setIndex(Integer index)
	{
		this.index = index;
	}

	public void paint(Graphics g)
	{
		super.paint(g);
	}

	private void movePartIn()
	{
		if (part.getCenterX() < getCenterX())
			part.setCenterLocation(part.getCenterX() + 1, part.getCenterY());
		else if (part.getCenterX() > getCenterX())
			part.setCenterLocation(part.getCenterX() - 1, part.getCenterY());

		if (part.getCenterY() < getCenterY())
			part.setCenterLocation(part.getCenterX(), part.getCenterY() + 1);
		else if (part.getCenterY() > getCenterY())
			part.setCenterLocation(part.getCenterX(), part.getCenterY() - 1);

		if (part.getCenterX() == getCenterX() && part.getCenterY() == getCenterY())
		{
			Object[] args = new Object[1];
			args[0] = index;
			transducer.fireEvent(channel, TEvent.WORKSTATION_LOAD_FINISHED, args);
		}
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args)
	{
		if (((Integer)args[0]).equals(index))
		{
			if (event == TEvent.WORKSTATION_DO_ACTION)
			{
				animationState = AnimationState.ANIMATING;
				return;
			}
			else if (event == TEvent.WORKSTATION_DO_LOAD_GLASS)
			{
				animationState = AnimationState.MOVING;
				return;
			}
			else if (event == TEvent.WORKSTATION_RELEASE_GLASS)
			{
				//added by monroe
				//animationState = AnimationState.DONE;
				this.transducer.fireEvent(this.channel, TEvent.WORKSTATION_RELEASE_FINISHED, args);
				animationState = AnimationState.IDLE;
				//above added by monroe

				nextComponent.addPart(part);
				return;
			}
			else if (event == TEvent.GLASS_BREAK_OFFLINE)
			{
				if(!glassBroken && part!=null && animationState ==AnimationState.ANIMATING)
				{
					part.setIcon(new ImageIcon("imageicons/glassImage_BROKEN.png"));
					glassBroken = true;
				}
				return;
			}
			else if ( event==TEvent.ROMOVE_GLASS_OFFLINE )
			{
				if(glassBroken)
				{
					setIcon(imageicons.get(0));
					part.setIcon(new ImageIcon());
					part = null;
					glassBroken = false;
					animationState =AnimationState.IDLE;
					return;
				}
				
			}
			else if (event == TEvent.WORKSTATION_BROKEN)
			{
				if(!machineBroken && part!=null && animationState ==AnimationState.ANIMATING)
				{
					
					machineBroken = true;
					return;
				}
				
			}
			else if (event == TEvent.WORKSTATION_FIXED)
			{
				if(machineBroken)
				{
					setIcon(imageicons.get(0));
					part.setIcon(new ImageIcon());
					part = null;
					machineBroken = false;
					animationState =AnimationState.IDLE;
					return;
				}
			}
			else if (event == TEvent.WORKSTATION_DISABLE_OFFLINE)
			{
				if(animationState == AnimationState.IDLE)
				{
					setIcon(badIcon);
				}
			}
			else if (event == TEvent.WORKSTATION_ENABLE_OFFLINE)
			{
				setIcon(imageicons.get(0));
			}
			

		}
		else if (event == TEvent.WORKSTATION_OFFLINE_CHANGE_SPEED)
		{
			if(channel == this.channel)
			{
				animationChangeSpeed(((Integer)args[0]).intValue());
			}
			
		}
		
	}
	public void animationChangeSpeed(int speed)
	{
		speedDown = 2*10/speed;
		
	}
}
