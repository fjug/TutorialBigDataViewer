/**
 *
 */
package de.mpicbg.jug.plugins;

import io.scif.SCIFIO;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;

import net.imagej.Dataset;
import net.imagej.ImgPlus;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.ARGBType;
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
@Plugin( menuPath = "Tutorials>BDV Vistools>Tutorial Plugin 3", description = "Multiple sources, tainted.", headless = false, type = Command.class )
public class TutorialPlugin3< T extends RealType< T > & NativeType< T >> implements Command {

	@Parameter
	private SCIFIO scifio;

	@Parameter( label = "3D ImgPlus to be shown." )
	private ImgPlus< T > imgPlus;

	private JFrame frame;

	public ImgPlus< ? > openDotsImage() {
		final String filename = "droso_dots.tif";
		final URL iconURL = ClassLoader.getSystemClassLoader().getResource( filename );
		final File file = new File( iconURL.getPath() );

		try {
			if ( file.exists() && file.canRead() ) {
				final Dataset ds = scifio.datasetIO().open( file.getAbsolutePath() );
				return ds.getImgPlus();
			}
		} catch ( final IOException e ) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		frame = new JFrame( "BDV Vistools Tutorial 3" );
		frame.add( new JLabel( "This is our own JFrame!" ), BorderLayout.SOUTH );
		frame.setBounds( 50, 50, 512, 512 );

		// Add a BDV panel to our JFrame
		final BdvHandlePanel bdvHandlePanel = new BdvHandlePanel( frame, Bdv.options() );
		frame.add( bdvHandlePanel.getViewerPanel(), BorderLayout.CENTER );

		frame.setVisible( true );

		// Add an image to our BDV HandlePanel
		final BdvSource bdvSource1 = BdvFunctions.show(
				imgPlus,
				imgPlus.getName(),
				Bdv.options().addTo( bdvHandlePanel ).sourceTransform( 1, 1, 5.25 ) );

		final ImgPlus< ? > imgPlusDots = openDotsImage();
		final BdvSource bdvSourceDrosoDots = BdvFunctions.show(
				imgPlusDots,
				"DOTS",
				Bdv.options().addTo( bdvHandlePanel ).sourceTransform( 1, 1, 5.25 ) );

		bdvSourceDrosoDots.setDisplayRangeBounds( 0, 255 );
		bdvSourceDrosoDots.setDisplayRange( 0, 255 );
		bdvSourceDrosoDots.setColor( new ARGBType( 0xFF0000 ) );

		bdvSource1.setActive( true );

	}

}
