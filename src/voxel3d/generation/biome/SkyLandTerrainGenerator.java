package voxel3d.generation.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;
import voxel3d.generation.GenerationUtility;
import voxel3d.generation.structures.*;
import voxel3d.level.Chunk;

public class SkyLandTerrainGenerator implements TerrainGenerator {
	
	private static final List<Structure> structures;
	static
	{
		structures = new ArrayList<Structure>();
		structures.add(new CherryTree());
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
		int height = getHeight(x, z);
		
		if(y > height + 1)
		{
			return AirBlock.getInstance();
		}
		else if(y == height + 1)
		{
			return foliageBlock(x, y, z);
		}
		else if(y == height && y > getStoneHeight(x, z))
		{
			return SkyGrassBlock.getInstance();
		}
		else if(y > height - 3 && y > getStoneHeight(x, z))
		{
			return DirtBlock.getInstance();
		}
		else
		{
			return WhiteStoneBlock.getInstance();
		}
	}
	
	private static int getHeight(int x, int z)
	{
		return (int) (Fields.OctaveMap2D(x, z, 128) * 16d);
	}
	
	private static int getStoneHeight(int x, int z)
	{
		return (int) (Fields.OctaveMap2D(x, z, 64) * 16d - 3d);
	}
	
	private static Block foliageBlock(int x, int y, int z)
	{
		if(y-1 <= getStoneHeight(x, z))
			return AirBlock.getInstance();
		
		Random random = new Random();
		random.setSeed(getPositionSeed(x, y, z));
		if(random.nextInt(25) == 0)
			return SkyFlower.getInstance();
		else if(random.nextInt(3) == 0)
			return SkyGrass.getInstance();
		
		return AirBlock.getInstance();
	}

	private static int getPositionSeed(int x, int y, int z)
	{
		return (x * 74317 + y * 2345897 + z * 4216754);
	}
}