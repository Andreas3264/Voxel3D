package voxel3d.generation;

import voxel3d.block.Block;
import voxel3d.generation.biome.TerrainGenerator;
import voxel3d.generation.structures.Structure;
import voxel3d.global.Settings;
import voxel3d.level.Chunk;
import voxel3d.utility.MathX;

public class GenerationUtility {
	
	public static void standardPopulation(Chunk chunk, TerrainGenerator generator, Iterable<Structure> structures)
	{
		Block[] blocks = new Block[Settings.CHUNK_SIZE3];
		
		GenerationContext context = new GenerationContext(chunk, generator, blocks);
		
		for(int xp = 0; xp < Settings.CHUNK_SIZE; xp++){
			for(int yp = 0; yp < Settings.CHUNK_SIZE; yp++){
				for(int zp = 0; zp < Settings.CHUNK_SIZE; zp++){
					int index = MathX.getXYZ(xp, yp, zp);
					
					int x = chunk.cx * Settings.CHUNK_SIZE + xp;
					int y = chunk.cy * Settings.CHUNK_SIZE + yp;
					int z = chunk.cz * Settings.CHUNK_SIZE + zp;
					
					blocks[index] = generator.getBlock(x, y, z);
				}
			}
		}
		
		for(Structure structure : structures)
			structure.placeInChunk(chunk.cx, chunk.cy, chunk.cz, context);
		
		chunk.setAllBlocks(blocks);
	}
}