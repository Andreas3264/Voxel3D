package voxel3d.generation.structures;

import voxel3d.generation.GenerationContext;

public interface Structure {
	
	public void placeInChunk(int cx, int cy, int cz, GenerationContext context);
	public void placeStructure(int x, int y, int z, GenerationContext context);
}
