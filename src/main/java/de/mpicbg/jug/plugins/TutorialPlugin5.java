/**
 *
 */
package de.mpicbg.jug.plugins;

import java.awt.BorderLayout;
import java.io.File;
import java.net.URL;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import bdv.spimdata.SpimDataMinimal;
import bdv.spimdata.XmlIoSpimDataMinimal;
import bdv.util.Bdv;
import bdv.util.BdvFunctions;
import bdv.util.BdvHandlePanel;
import bdv.util.BdvStackSource;
import mpicbg.spim.data.SpimDataException;

/**
 * Plugin that opens the active image using the
 * <code>GenericClearVolumeGui</code>.
 *
 * @author jug
 */
@Plugin( menuPath = "Tutorials>BDV Vistools>Tutorial Plugin 5", description = "Remote sources.", headless = false, type = Command.class )
public class TutorialPlugin5< T extends RealType< T > & NativeType< T >> implements Command {

	@Parameter( label = "File (empty for default): " )
	private String xmlFilename = "";

	private SpimDataMinimal spimData;

	private JFrame frame;

	/**
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public void run() {
		// get default XML from resources if nothing given
		if ( xmlFilename.isEmpty() ) {
			final URL iconURL = ClassLoader.getSystemClassLoader().getResource( "catmaid-abd1.5.xml" );
			final File file = new File( iconURL.getPath() );
			xmlFilename = file.getAbsolutePath();
		}

		try {
			spimData = new XmlIoSpimDataMinimal().load( xmlFilename );
		} catch ( final SpimDataException e ) {
			e.printStackTrace();
		}

		frame = new JFrame( "BDV Vistools Tutorial 5" );
		frame.add( new JLabel( "This is our own JFrame!" ), BorderLayout.SOUTH );
		frame.setBounds( 50, 50, 1200, 900 );

		// Add a BDV panel to our JFrame
		final BdvHandlePanel bdvHandlePanel = new BdvHandlePanel( frame, Bdv.options() );
		frame.add( bdvHandlePanel.getViewerPanel(), BorderLayout.CENTER );

		frame.setVisible( true );

		// Add an image to our BDV HandlePanel
		final List< BdvStackSource< ? > > bdvSources = BdvFunctions.show(
				spimData,
				Bdv.options().addTo( bdvHandlePanel ) );
	}

}
