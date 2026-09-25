package voxel3d.generation.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;
import voxel3d.generation.GenerationUtility;
import voxel3d.generation.OreGen;
import voxel3d.generation.structures.*;
import voxel3d.level.Chunk;

public class CostalTerrainGenerator implements TerrainGenerator {
	
	private static final int sandHeight = 2;
	
	private static final List<Structure> structures;
	static
	{
		structures = new ArrayList<Structure>();
		//structures.add(new OakTree());
	}
	
	@Override
	public void populate(Chunk chunk) 
	{
		GenerationUtility.standardPopulation(chunk, this, structures);
	}
	
	@Override
	public Block getBlock(int x, int y, int z) {
		return staticGetBlock(x, y, z);
	}
	
	private static Block staticGetBlock(int x, int y, int z)
	{
		int height = (int) Math.floor(getHeight(x, z));
		
		Block ret = AirBlock.getInstance();
		
		if(y > height && y <= 0 && height <= 0)
		{
			ret = GlassBlock.getInstance();
		}
		else if(y > height + 1)
		{
			ret = AirBlock.getInstance();
		}
		else if(y == height + 1)
		{
			ret = foliageBlock(x,y,z);
		}
		else if(y == height)
		{
			ret = surfaceBlock(x,y,z);
		}
		else if(y >= height - 4)
		{
			ret = subSurfaceBlock(x,y,z);
		}
		else if(y < height - 4)
		{
			ret = OreGen.getBlock(x, y, z);
		}
		
		return ret;
	}
	
	private static int getHeight(int x, int z)
	{
		double height = Math.floor(Fields.OctaveMap2D(x, z, 512) * 32 + 8);
		height += getExtremeHeigh(x + 567238, z - 6538472);
		if(height >= 0) {height += getCosteHeigh(926835 - x, 758623 - z);}
		if(height < 0)  {height -= getCosteHeigh(926835 - x, 758623 - z);}
		return (int) height;
	}
	
	private static double getCosteHeigh(int x, int z)
	{
		double height = Math.max(Fields.OctaveMap2D(x, z, 128) * 32d, 0);
		return height;
	}
	
	private static double getExtremeHeigh(int x, int z)
	{
		double extremeHeight = Math.max(Fields.OctaveMap2D(x, z, 128) * 128d - 64d, 0);
		return extremeHeight;
	}
	
	private static Block foliageBlock(int x, int y, int z)
	{
		if(getHeight(x,z) <= sandHeight)
			return AirBlock.getInstance();
		
		Random random = new Random();
		random.setSeed(getPositionSeed(x, y, z));
		if(random.nextInt(8) == 0)
			return Grass.getInstance();
		
		return AirBlock.getInstance();
	}
	
	private static Block surfaceBlock(int x, int y, int z)
	{
		if(getHeight(x,z) <= sandHeight)
			return SandBlock.getInstance();
		
		return GrassBlock.getInstance();
	}
	
	private static Block subSurfaceBlock(int x, int y, int z)
	{
		if(getHeight(x,z) <= sandHeight)
			return SandBlock.getInstance();
		
		return DirtBlock.getInstance();
	}
	
	private static int getPositionSeed(int x, int y, int z)
	{
		return (x * 74317 + y * 2345897 + z * 4216754);
	}
}
