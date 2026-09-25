package voxel3d.generation;

public class Fields {
	
	public static double OctaveMap2D(int x, int z, double scale)
	{
		double val = 0;
		double amp = 0.5;
		
		for(int i = 0; i < 6; i++)
		{
			x += 734852;
			z += 326745;
			val += SimplexNoise.noise(x / scale, z / scale) * amp;
			scale *= 0.5;
			amp *= 0.5;
		}
		return val;
	}
	
	public static double OctaveMap3D(int x, int y, int z, double scale)
	{
		double val = 0;
		double amp = 0.5;
		
		for(int i = 0; i < 6; i++)
		{
			val += SimplexNoise.noise(x / scale, y / scale, z / scale) * amp;
			scale *= 0.5;
			amp *= 0.5;
		}
		return val;
	}
}