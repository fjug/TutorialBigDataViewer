/**
 *
 */
package de.mpicbg.jug.plugins;

import net.imagej.ImgPlus;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import org.scijava.command.Command;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import bdv.util.Bdv;
import bdv.util.BdvFunctions;

/**
 * Plugin that opens the active image using the
 * <code>GenericClearVolumeGui</code>.
 *
 * @author jug
 */
@Plugin( menu = { @Menu( label = "Tutorials" ),
				  @Menu( label = "BDV Vistools" ),
				  @Menu( label = "Tutorial Plugin 1" ) }, description = "Hello BDV.", headless = false, type = Command.class )
public class TutorialPlugin1< T extends RealType< T > & NativeType< T >> implements Command {

	@Parameter( label = "3D ImgPlus to be shown." )
	private ImgPlus< T > imgPlus;

	/**
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings( "unused" )
	@Override
	public void run() {
		final Bdv bdv = BdvFunctions.show( imgPlus, imgPlus.getName() );
//		final Bdv bdv = BdvFunctions.show( imgPlus, imgPlus.getName(), Bdv.options().sourceTransform( 1, 1, 8 ) );
	}
}
