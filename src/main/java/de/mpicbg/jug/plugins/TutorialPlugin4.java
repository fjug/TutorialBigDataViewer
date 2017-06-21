/**
 *
 */
package de.mpicbg.jug.plugins;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
import net.imagej.display.DatasetView;
import net.imglib2.RealPoint;
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
				  @Menu( label = "Tutorial Plugin 4" ) }, description = "Overlays", headless = false, type = Command.class )
public class TutorialPlugin4< T extends RealType< T > & NativeType< T >> implements Command {

	@Parameter( label = "3D ImgPlus to be shown." )
	private DatasetView datasetView;

	private ImgPlus< T > imgPlus;
	private JFrame frame;

	/**
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public void run() {
		imgPlus = ( ImgPlus< T > ) datasetView.getData().getImgPlus();
		frame = new JFrame( "BDV Vistools Tutorial 4" );
		frame.add( new JLabel( "This is our own JFrame!" ), BorderLayout.SOUTH );
		frame.setBounds( 50, 50, 1200, 900 );

		// Add a BDV panel to our JFrame
		final BdvHandlePanel bdvHandlePanel = new BdvHandlePanel( frame, Bdv.options() );
		frame.add( bdvHandlePanel.getViewerPanel(), BorderLayout.CENTER );

		frame.setVisible( true );

		// Add an image to our BDV HandlePanel
		final BdvSource bdvSource1 = BdvFunctions.show(
				imgPlus,
				imgPlus.getName(),
				Bdv.options().addTo( bdvHandlePanel ).sourceTransform( 1, 1, 6 ) );
		bdvSource1.setActive( true );

		final ArrayList< RealPoint > points = getTutorial4Points();

		final BdvSource bdvSource2 = BdvFunctions.showOverlay(
				new OverlayForTutorial4( points ),
				"overlay",
				Bdv.options().addTo( bdvHandlePanel ).sourceTransform( 1, 1, 6 ) );
		bdvSource2.setDisplayRangeBounds( 0, 1 );
		bdvSource2.setActive( true );
	}

	/**
	 * @return the points to be displayed.
	 */
	private ArrayList< RealPoint > getTutorial4Points() {
		final String filename = "dots.csv";
		
		
		final URL iconURL = ClassLoader.getSystemClassLoader().getResource( filename );
		List< String > lines = null;
		final ArrayList< RealPoint > points = new ArrayList<>();
		
		try {
			final boolean IS_WINDOWS = System.getProperty( "os.name" ).contains( "indow" );
			final String osAppropriatePath = IS_WINDOWS ? iconURL.getPath().substring(1) : iconURL.getPath();
			lines = Files.readAllLines( Paths.get(osAppropriatePath), Charset.forName( "UTF-8" ) );
			for ( final String line : lines ) {
				if ( line.startsWith( "#" ) ) {
					continue;
				}
				final String[] vals = line.split( "," );
				final int x = Integer.parseInt( vals[ 1 ] );
				final int y = Integer.parseInt( vals[ 2 ] );
				final int z = Integer.parseInt( vals[ 3 ] );
				points.add( new RealPoint( x, y, z ) );
			}
		} catch ( final IOException e ) {
			e.printStackTrace();
		}
		return points;
	}

}
