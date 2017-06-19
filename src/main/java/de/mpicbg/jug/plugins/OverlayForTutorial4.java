/**
 *
 */
package de.mpicbg.jug.plugins;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import bdv.util.BdvOverlay;
import net.imglib2.RealPoint;
import net.imglib2.realtransform.AffineTransform3D;
import net.imglib2.type.numeric.ARGBType;


/**
 * @author jug
 */
public class OverlayForTutorial4 extends BdvOverlay {

	private final List< RealPoint > points;

	public OverlayForTutorial4( final List< RealPoint > points ) {
		this.points = points;
	}

	/**
	 * @see bdv.util.BdvOverlay#draw(java.awt.Graphics2D)
	 */
	@Override
	protected void draw( final Graphics2D g ) {
		final AffineTransform3D t = new AffineTransform3D();
		getCurrentTransform3D( t );

		final double[] lPos = new double[ 3 ];
		final double[] gPos = new double[ 3 ];
		for ( final RealPoint p : points ) {
			p.localize( lPos );
			t.apply( lPos, gPos );
			final int size = getSize( gPos[ 2 ] );
			final int x = ( int ) ( gPos[ 0 ] - 0.5 * size );
			final int y = ( int ) ( gPos[ 1 ] - 0.5 * size );
			g.setColor( getColor( gPos[ 2 ] ) );
			g.fillOval( x, y, size, size );
		}
	}

	private Color getColor( final double depth ) {
		final int color = info.getColor().get();

		int alpha = 255 - ( int ) Math.round( Math.abs( depth ) );
		if ( alpha < 64 )
			alpha = 64;

		return new Color( ARGBType.red( color ), ARGBType.green( color ), ARGBType.blue( color ), alpha );
	}

	private int getSize( final double depth ) {
		final double max = info.getDisplayRangeMax();
		return ( int ) Math.max( 1, 10 - max * Math.round( Math.abs( depth ) ) );
	}
}
