package voxel3d.generation.structures;

import java.util.Random;

import voxel3d.block.all.*;
import voxel3d.generation.GenerationContext;
import voxel3d.global.Settings;

public class EtherealTree implements Structure {
	
	@Override
	public void placeStructure(int x, int y, int z, GenerationContext context)
	{	
		for(int xx = -9; xx <= 9; xx++)
		{
			for(int yy = -3; yy <= 3; yy++)
			{
				for(int zz = -9; zz <= 9; zz++)
				{
					if(xx*xx + yy*yy*8 + zz*zz < 9*9)
						context.placeBlock(x + xx, y + 10 + yy, z + zz, EtherealLeavesBlock.getInstance());
				}
			}
		}
		
		for(int h = 0; h < 12; h++)
		{
			context.placeBlock(x, y + h, z, EtherealLogBlock.getInstance());
		}
		context.placeBlock(x, y - 1, z, DirtBlock.getInstance());
	}
	
	@Override
	public void placeInChunk(int cx, int cy, int cz, GenerationContext context)
	{
		for(int ox = -1; ox <=1 ; ox++) {
			for(int oy = -1; oy <= 1; oy++) {
				for(int oz = -1; oz <= 1; oz++) {
					
					// chunk that is being checked
					int rx = cx + ox;
					int ry = cy + oy;
					int rz = cz + oz;
					
					Random random = new Random();
					random.setSeed(rx * 2137 + ry * 212231 + rz * 736125);
					
					int tests = (Settings.CHUNK_SIZE3) / 150;
					for(int i = 0; i < tests; i++)
					{
						// chunk relative point
						int xx = random.nextInt(Settings.CHUNK_SIZE);
						int yy = random.nextInt(Settings.CHUNK_SIZE);
						int zz = random.nextInt(Settings.CHUNK_SIZE);
						
						// absolute position
						int x = xx + rx * Settings.CHUNK_SIZE;
						int y = yy + ry * Settings.CHUNK_SIZE;
						int z = zz + rz * Settings.CHUNK_SIZE;
						
						if(context.generator.getBlock(x, y - 1, z) instanceof EtherealGrassBlock)
						{
							placeStructure(x, y, z, context);
						}
					}
				}
			}
		}
	}
}