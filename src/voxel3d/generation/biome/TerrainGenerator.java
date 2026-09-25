package voxel3d.generation.biome;

import voxel3d.block.Block;
import voxel3d.level.Chunk;

public interface TerrainGenerator {
	
	public void populate(Chunk chunk);
	public Block getBlock(int x, int y, int z);
}