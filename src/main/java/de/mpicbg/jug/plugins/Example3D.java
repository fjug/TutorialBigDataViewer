package de.mpicbg.jug.plugins;

import java.util.Random;

import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.integer.UnsignedByteType;

import bdv.util.Bdv;
import bdv.util.BdvFunctions;

public class Example3D
{
	public static void main( final String[] args )
	{
		System.setProperty( "apple.laf.useScreenMenuBar", "true" );

		final Random random = new Random();

		final Img< UnsignedByteType > img = ArrayImgs.unsignedBytes( 100, 100, 100 );
		img.forEach( t -> t.set( ( int ) ( random.nextDouble() * 255 ) ) );
		final Bdv bdv3D = BdvFunctions.show( img, "random" );

//		final Img< IntType > img = ArrayImgs.ints( 100, 100, 100 );
//		img.forEach( t -> t.set( ( int ) ( random.nextDouble() * 1000 ) ) );
//		final BdvSource bdv3D = BdvFunctions.show( img, "random" );
//		bdv3D.setDisplayRange( 0, 1000 );
//		bdv3D.setColor( new ARGBType( 0x00FF0000) );

//		final Img< ARGBType > img2 = ArrayImgs.argbs( 100, 100, 100 );
//		img2.forEach( t -> t.set( random.nextInt() & 0xFFFF0000 ) );
//		BdvFunctions.show( img2, "reds", Bdv.options().addTo( bdv3D ) );
	}
}
