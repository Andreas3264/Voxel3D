package voxel3d.level;

import java.io.FileInputStream;

import voxel3d.data.DataInputStream;
import voxel3d.generation.biome.*;
import voxel3d.generation.biome.TerrainGenerator;
import voxel3d.global.Debug;
import voxel3d.global.Settings;
import voxel3d.utility.Executable;

public class ChunkPopulator implements Executable {
	
	private final TerrainGenerator terrainGenerator;
	private final int chunkX, chunkY, chunkZ;
	private final String worldName;
	private final Chunk chunk;
	
	public ChunkPopulator(int chunkX, int chunkY, int chunkZ, String worldName, Chunk chunk)
	{
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.chunkZ = chunkZ;
		this.worldName = worldName;
		this.chunk = chunk;
		chunk.isBeingPopulated = true;
		terrainGenerator = new OverworldTerrainGenerator();
		//terrainGenerator = new SkyLandTerrainGenerator();
		//terrainGenerator = new MysticTerrainGenerator();
		//terrainGenerator = new RainbowTerrainGenerator();
		//terrainGenerator = new CostalTerrainGenerator();
	}
	
	public void execute()
	{
		if(Settings.loadEnable)
		{
			try {
				String chunkPath = ("store/worlds/" + worldName + "/chunks/chunk" + chunkX + "," + chunkY + "," + chunkZ);
				Debug.ioLog("loading: " + chunkPath);
				FileInputStream fis = new FileInputStream(chunkPath);
				byte[] data = fis.readAllBytes();
				fis.close();
				chunk.read(new DataInputStream(data));
			}
			catch (Exception e)
			{
				terrainGenerator.populate(chunk);
			}
		}
		else
		{
			terrainGenerator.populate(chunk);
		}
		
		chunk.isPopulated = true;
		chunk.isBeingPopulated = false;
		
		Debug.chunkGens++;
	}
}