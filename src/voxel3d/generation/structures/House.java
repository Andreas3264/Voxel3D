package voxel3d.generation.structures;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.GenerationContext;
import voxel3d.global.Settings;

public class House implements Structure {

	private static final int gridSize = 256;
	private static final int spreadSize = 256 - 16;
	private static final int gridSizeHalf = gridSize / 2;
	private static final int spreadSizeHalf = spreadSize / 2;
	
	@Override
	public void placeStructure(int x, int y, int z, GenerationContext context)
	{
		Block floor = OakPlanksBlock.getInstance();
		Block wall = StoneBricksSmallBlock.getInstance();
		Block roof = OakLogBlock.getInstance();
		Block side = OakLogBlock.getInstance();
		
		int size = 5;
		
		for(int xx = -size; xx <= size; xx++)
		{
			for(int zz = -size; zz <= size; zz++)
			{
				for(int yy = 0; yy <= 4; yy++)
				{
					if(yy == 0) {
						context.placeBlock(x + xx, y + yy, z + zz, floor.getBlockInstance());
					} else if(yy == 4) {
						context.placeBlock(x + xx, y + yy, z + zz, roof.getBlockInstance());
					} else if((xx == size || xx == -size) && (zz == size || zz == -size)) {
						context.placeBlock(x + xx, y + yy, z + zz, side.getBlockInstance());
					} else if(xx == size || xx == -size || zz == size || zz == -size) {
						context.placeBlock(x + xx, y + yy, z + zz, wall.getBlockInstance());
					} else {
						context.placeBlock(x + xx, y + yy, z + zz, AirBlock.getInstance());
					}
				}
			}
		}
		context.placeBlock(x, y + 1, z + size, AirBlock.getInstance());
		context.placeBlock(x, y + 2, z + size, AirBlock.getInstance());
		
		context.placeBlock(x, y + 1, z + size + 1, AirBlock.getInstance());
		context.placeBlock(x, y + 2, z + size + 1, AirBlock.getInstance());
		context.placeBlock(x, y + 3, z + size + 1, AirBlock.getInstance());
	}
	
	@Override
	public void placeInChunk(int cx, int cy, int cz, GenerationContext context)
	{
		int chunkX = cx * Settings.CHUNK_SIZE;
		//int chunkY = cy * Settings.CHUNK_SIZE;
		int chunkZ = cz * Settings.CHUNK_SIZE;
		
		// position of closest structure
		int closestX = Math.floorDiv(chunkX + gridSizeHalf, gridSize) * gridSize;
		int closestY = 0;
		int closestZ = Math.floorDiv(chunkZ + gridSizeHalf, gridSize) * gridSize;
		
		// random offset from grid
		Random random = new Random();
		random.setSeed(closestX * 2137 + closestY * 212231 + closestZ * 736125);
		int structureX = closestX + random.nextInt(spreadSize) - spreadSizeHalf;
		int structureY = closestY;
		int structureZ = closestZ + random.nextInt(spreadSize) - spreadSizeHalf;
		
		// find y level of surface, do not place if no valid spot is found
		for(int yy = -10; yy < 64; yy++)
		{
			structureY = yy;
			if(context.generator.getBlock(structureX, structureY, structureZ) instanceof GrassBlock)
			{
				placeStructure(structureX, structureY, structureZ, context);
				break;
			}
		}
	}
}
