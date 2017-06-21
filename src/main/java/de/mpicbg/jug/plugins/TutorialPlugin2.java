/**
 *
 */
package de.mpicbg.jug.plugins;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.scijava.command.Command;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import bdv.util.Bdv;
import bdv.util.BdvFunctions;
import bdv.util.BdvHandlePanel;
import bdv.util.BdvSource;
import net.imagej.ImgPlus;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

/**
 * Plugin that opens the active image using the
 * <code>GenericClearVolumeGui</code>.
 *
 * @author jug
 */
@Plugin( menu = { @Menu( label = "Tutorials" ),
				  @Menu( label = "BDV Vistools" ),
				  @Menu( label = "Tutorial Plugin 2" ) }, description = "Hello BDV in own JFrame.", headless = false, type = Command.class )
public class TutorialPlugin2< T extends RealType< T > & NativeType< T >> implements Command {

	@Parameter( label = "3D ImgPlus to be shown." )
	private ImgPlus< T > imgPlus;

	private JFrame frame;

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		frame = new JFrame( "BDV Vistools Tutorial 2" );
		frame.add( new JLabel( "This is our own JFrame!" ), BorderLayout.SOUTH );
		frame.setBounds( 50, 50, 512, 512 );

		// Add a BDV panel to our JFrame
		final BdvHandlePanel bdvHandlePanel = new BdvHandlePanel( frame, Bdv.options() );
		frame.add( bdvHandlePanel.getViewerPanel(), BorderLayout.CENTER );

		frame.setVisible( true );

		// Add an image to our BDV HandlePanel
		final BdvSource bdvSource = BdvFunctions.show(
				imgPlus,
				imgPlus.getName(),
				Bdv.options().addTo( bdvHandlePanel ) );
		// For a source you can set a range of useful things.
		bdvSource.setDisplayRangeBounds( 0, 60000 );
		bdvSource.setDisplayRange( 0, 32000 );
	}

}
