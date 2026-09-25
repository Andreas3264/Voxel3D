package voxel3d.generation.biome;

import java.util.ArrayList;
import java.util.List;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;
import voxel3d.generation.GenerationUtility;
import voxel3d.generation.structures.*;
import voxel3d.level.Chunk;

public class RainbowTerrainGenerator implements TerrainGenerator {
	
	private static final List<Structure> structures = new ArrayList<Structure>();
	
	private static Block[] stones = new Block[] {
		RedStoneBlock.getInstance(),
		MagentaStoneBlock.getInstance(),
		BlueStoneBlock.getInstance(),
		CyanStoneBlock.getInstance(),
		GreenStoneBlock.getInstance(),
		YellowStoneBlock.getInstance(),
	};
	
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
		
		if(y > height)
		{
			return AirBlock.getInstance();
		}
		else
		{
			if(y >= 0)
			{
				return stones[y % stones.length].getBlockInstance();
			}
			else
			{
				return StoneBlock.getInstance();
			}
		}
	}

	private static double getHeight(int x, int z)
	{
		double val = Fields.OctaveMap2D(x, z, 128);
		val = Math.pow(val, 2.0)*0.5;
		//val = val*val;
		return val * 512;
	}
}