package voxel3d.generation;

import voxel3d.block.Block;
import voxel3d.generation.biome.TerrainGenerator;
import voxel3d.global.Settings;
import voxel3d.level.Chunk;
import voxel3d.utility.MathX;

public class GenerationContext {
	
	public final TerrainGenerator generator;
	private final Chunk chunk;
	private final Block[] blocks;
	
	public GenerationContext(Chunk chunk, TerrainGenerator generator, Block[] blocks)
	{
		this.generator = generator;
		this.chunk = chunk;
		this.blocks = blocks;
	}
	
	public void placeBlock(int x, int y, int z, Block block)
	{
		int lx = x - chunk.cx * Settings.CHUNK_SIZE;
		int ly = y - chunk.cy * Settings.CHUNK_SIZE;
		int lz = z - chunk.cz * Settings.CHUNK_SIZE;
		if(lx >= 0 && lx < Settings.CHUNK_SIZE && ly >= 0 && ly < Settings.CHUNK_SIZE && lz >= 0 && lz < Settings.CHUNK_SIZE)
    		blocks[MathX.getXYZ(lx, ly, lz)] = block;
	}

}
