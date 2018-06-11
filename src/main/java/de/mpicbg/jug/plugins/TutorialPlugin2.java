/**
 *
 */
package de.mpicbg.jug.plugins;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.imagej.ImgPlus;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import bdv.util.Bdv;
import bdv.util.BdvFunctions;
import bdv.util.BdvHandlePanel;
import bdv.util.BdvSource;

/**
 * Plugin that opens the active image using the
 * <code>GenericClearVolumeGui</code>.
 *
 * @author jug
 */
@Plugin( menuPath = "Tutorials>BDV Vistools>Tutorial Plugin 2", description = "Hello BDV in own JFrame.", headless = false, type = Command.class )
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
		final JButton button = new JButton( "This is our own JFrame!" );
		button.addActionListener( e -> JOptionPane.showMessageDialog( null, "Wow! Congratulations! That is FANTASTIC!" ) );
		frame.add( button, BorderLayout.SOUTH );
		frame.setBounds( 50, 50, 512, 512 );

		// Add a BDV panel to our JFrame
		final BdvHandlePanel bdvHandlePanel = new BdvHandlePanel( frame, Bdv.options() );
		frame.add( bdvHandlePanel.getViewerPanel(), BorderLayout.CENTER );

		frame.setVisible( true );

		// Add an image to our BDV HandlePanel
		final BdvSource bdvSource = BdvFunctions.show(
				imgPlus,
				imgPlus.getName(),
				Bdv.options().addTo( bdvHandlePanel ).sourceTransform( 1, 1, 5.25 ) );
		// For a source you can set a range of useful things.
		bdvSource.setDisplayRangeBounds( 0, 60000 );
		bdvSource.setDisplayRange( 0, 32000 );
	}

}
