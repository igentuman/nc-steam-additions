package igentuman.ncsteamadditions.villager;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import nc.block.BlockMeta;
import nc.init.NCBlocks;
import net.minecraft.block.*;
import net.minecraft.block.BlockDoor.EnumHingePosition;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.gen.structure.StructureVillagePieces.*;
import net.minecraftforge.fml.common.registry.VillagerRegistry.*;

import java.util.*;

public class VillageScientistHouse extends Village
{
	public static ResourceLocation woodenCrateLoot = new ResourceLocation(NCSteamAdditions.MOD_ID, "chests/scientist_house");

	public VillageScientistHouse()
	{
	}

	public VillageScientistHouse(Start villagePiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, EnumFacing facing)
	{
		super(villagePiece, par2);
		this.setCoordBaseMode(facing);
		this.boundingBox = par4StructureBoundingBox;
	}

	static List<BlockPos> framesHung = new ArrayList();
	private int groundLevel = -1;

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox box)
	{
		if(groundLevel < 0)
		{
			groundLevel = this.getAverageGroundLevel(world, box);
			if(groundLevel < 0)
				return true;
			boundingBox.offset(0, groundLevel-boundingBox.maxY+10-1, 0);
		}

		//Clear Space
		this.fillWithBlocks(world, box, 0, 0, 0, 10, 9, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
		//Cobble
		this.fillWithBlocks(world, box, 1, 0, 1, 9, 0, 8, Blocks.COBBLESTONE.getDefaultState(), Blocks.COBBLESTONE.getDefaultState(), false);
		this.fillWithBlocks(world, box, 6, 0, 1, 9, 0, 2, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
		//Stair
		this.setBlockState(world, Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), 4, 0, 0, box);

		IBlockState lead = ((BlockMeta.BlockIngot) NCBlocks.ingot_block).getStateFromMeta(2);
		this.fillWithBlocks(world, box, 1, 1, 3, 1, 4, 3, lead, lead, false);
		this.fillWithBlocks(world, box, 1, 1, 8, 1, 6, 8, lead, lead, false);
		this.fillWithBlocks(world, box, 9, 1, 3, 9, 6, 3, lead, lead, false);
		this.fillWithBlocks(world, box, 9, 1, 8, 9, 6, 8, lead, lead, false);
		this.fillWithBlocks(world, box, 1, 4, 3, 9, 4, 8, lead, lead, false);
		this.fillWithBlocks(world, box, 6, 5, 3, 6, 7, 3, lead, lead, false);
		this.fillWithBlocks(world, box, 1, 5, 5, 1, 6, 5, lead, lead, false);

		this.fillWithBlocks(world, box, 2, 4, 5, 8, 4, 7, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

		IBlockState woolFloor = Blocks.WOOL.getStateFromMeta(4);
		//Wool
		this.fillWithBlocks(world, box, 2, 0, 3, 5, 0, 4, woolFloor, woolFloor, false);
		this.fillWithBlocks(world, box, 2, 0, 4, 8, 0, 7, woolFloor, woolFloor, false);
		this.fillWithBlocks(world, box, 6, 4, 4, 8, 4, 4, woolFloor, woolFloor, false);
		this.fillWithBlocks(world, box, 2, 4, 5, 7, 4, 5, woolFloor, woolFloor, false);
		this.fillWithBlocks(world, box, 2, 4, 6, 6, 4, 6, woolFloor, woolFloor, false);
		this.fillWithBlocks(world, box, 2, 4, 7, 4, 4, 7, woolFloor, woolFloor, false);

		IBlockState wall = Blocks.SANDSTONE.getStateFromMeta(2);
		//Walls
		//Front
		this.fillWithBlocks(world, box, 2, 1, 3, 8, 3, 3, wall, wall, false);
		this.fillWithBlocks(world, box, 7, 5, 3, 8, 6, 3, wall, wall, false);
		this.setBlockState(world, wall, 7, 7, 3, box);
		this.fillWithBlocks(world, box, 6, 5, 4, 6, 7, 4, wall, wall, false);
		this.fillWithBlocks(world, box, 2, 5, 5, 5, 6, 5, wall, wall, false);
		this.fillWithBlocks(world, box, 3, 7, 5, 5, 7, 5, wall, wall, false);
		this.setBlockState(world, wall, 5, 8, 5, box);
		//Back
		this.fillWithBlocks(world, box, 2, 1, 8, 8, 3, 8, wall, wall, false);
		this.fillWithBlocks(world, box, 2, 5, 8, 8, 6, 8, wall, wall, false);
		this.fillWithBlocks(world, box, 3, 7, 8, 7, 7, 8, wall, wall, false);
		this.setBlockState(world, wall, 5, 8, 8, box);
		//Left
		this.fillWithBlocks(world, box, 1, 1, 4, 1, 3, 7, wall, wall, false);
		this.fillWithBlocks(world, box, 1, 5, 6, 1, 5, 7, wall, wall, false);
		//Right
		this.fillWithBlocks(world, box, 9, 1, 4, 9, 3, 7, wall, wall, false);
		this.fillWithBlocks(world, box, 9, 5, 4, 9, 6, 7, wall, wall, false);

		//Windows
		//Front
		this.setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 3, box);
		this.setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 3, box);
		this.setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, box);
		this.fillWithBlocks(world, box, 7, 6, 3, 8, 6, 3, Blocks.GLASS_PANE.getDefaultState(), Blocks.GLASS_PANE.getDefaultState(), false);
		//Back
		this.fillWithBlocks(world, box, 3, 2, 8, 5, 2, 8, Blocks.GLASS_PANE.getDefaultState(), Blocks.GLASS_PANE.getDefaultState(), false);
		this.fillWithBlocks(world, box, 3, 6, 8, 4, 6, 8, Blocks.GLASS_PANE.getDefaultState(), Blocks.GLASS_PANE.getDefaultState(), false);
		this.fillWithBlocks(world, box, 6, 6, 8, 7, 6, 8, Blocks.GLASS_PANE.getDefaultState(), Blocks.GLASS_PANE.getDefaultState(), false);
		//Left
		this.fillWithBlocks(world, box, 1, 2, 5, 1, 2, 6, Blocks.GLASS_PANE.getDefaultState(), Blocks.GLASS_PANE.getDefaultState(), false);
		this.fillWithBlocks(world, box, 1, 6, 6, 1, 6, 7, Blocks.GLASS_PANE.getDefaultState(), Blocks.GLASS_PANE.getDefaultState(), false);
		//Right
		this.fillWithBlocks(world, box, 9, 2, 5, 9, 2, 6, Blocks.GLASS_PANE.getDefaultState(), Blocks.GLASS_PANE.getDefaultState(), false);
		this.fillWithBlocks(world, box, 9, 6, 5, 9, 6, 6, Blocks.GLASS_PANE.getDefaultState(), Blocks.GLASS_PANE.getDefaultState(), false);

		//Fences
		IBlockState bars = Blocks.IRON_BARS.getStateFromMeta(0);
		this.fillWithBlocks(world, box, 1, 1, 1, 1, 1, 2, bars, Blocks.IRON_BARS.getStateFromMeta(0), false);
		this.fillWithBlocks(world, box, 2, 1, 1, 3, 1, 1, bars, bars, false);
		this.fillWithBlocks(world, box, 5, 1, 1, 5, 1, 2, bars, bars, false);
		this.fillWithBlocks(world, box, 1, 5, 3, 1, 5, 4, bars, bars, false);
		this.fillWithBlocks(world, box, 2, 5, 3, 5, 5, 3, bars, bars, false);
		this.fillWithBlocks(world, box, 7, 1, 6, 7, 5, 6, bars, bars, false);

		//Doors
		this.generateDoor(world, box, rand, 4, 1, 3, EnumFacing.NORTH, Blocks.OAK_DOOR);
		if(getCoordBaseMode()==EnumFacing.SOUTH||getCoordBaseMode()==EnumFacing.WEST)
		{
			this.placeDoor(world, box, rand, 3, 5, 5, EnumFacing.NORTH, EnumHingePosition.LEFT);
			this.placeDoor(world, box, rand, 4, 5, 5, EnumFacing.NORTH, EnumHingePosition.RIGHT);
		}
		else
		{
			this.placeDoor(world, box, rand, 3, 5, 5, EnumFacing.NORTH, EnumHingePosition.LEFT);
			this.placeDoor(world, box, rand, 4, 5, 5, EnumFacing.NORTH, EnumHingePosition.RIGHT);
		}


		//Stairs
		IBlockState stairs = Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
		setBlockState(world, stairs, 8, 1, 6, box);
		stairs = stairs.withRotation(Rotation.COUNTERCLOCKWISE_90);
//		stairMeta = this.getMetadataWithOffset(Blocks.OAK_STAIRS, 1);
		setBlockState(world, wall, 8, 1, 7, box);
		setBlockState(world, stairs, 7, 2, 7, box);
		setBlockState(world, stairs, 6, 3, 7, box);
		setBlockState(world, stairs, 5, 4, 7, box);

		//Roof
		IBlockState brickSlab = Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.BRICK);
		IBlockState brickSlabInverted = brickSlab.withProperty(BlockSlab.HALF, EnumBlockHalf.TOP);
		this.fillWithBlocks(world, box, 0, 6, 4, 0, 6, 8, brickSlabInverted, brickSlabInverted, false);
		this.fillWithBlocks(world, box, 1, 7, 4, 1, 7, 8, brickSlab, brickSlab, false);
		this.fillWithBlocks(world, box, 3, 8, 4, 3, 8, 8, brickSlab, brickSlab, false);
		this.fillWithBlocks(world, box, 5, 9, 2, 5, 9, 8, brickSlab, brickSlab, false);
		this.fillWithBlocks(world, box, 7, 8, 2, 7, 8, 8, brickSlab, brickSlab, false);
		this.fillWithBlocks(world, box, 9, 7, 2, 9, 7, 8, brickSlab, brickSlab, false);
		this.fillWithBlocks(world, box, 10, 6, 2, 10, 6, 8, brickSlabInverted, brickSlabInverted, false);

		IBlockState brickStairs = Blocks.BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST);
		this.fillWithBlocks(world, box, 2, 7, 4, 2, 7, 8, brickStairs, brickStairs, false);
		this.fillWithBlocks(world, box, 4, 8, 4, 4, 8, 8, brickStairs, brickStairs, false);
		brickStairs = brickStairs.withRotation(Rotation.CLOCKWISE_180);
		this.fillWithBlocks(world, box, 6, 8, 2, 6, 8, 8, brickStairs, brickStairs, false);
		this.fillWithBlocks(world, box, 8, 7, 2, 8, 7, 8, brickStairs, brickStairs, false);

		this.fillWithBlocks(world, box, 2, 7, 5, 2, 8, 5, Blocks.BRICK_BLOCK.getDefaultState(), Blocks.BRICK_BLOCK.getDefaultState(), false);
		this.fillWithBlocks(world, box, 7, 8, 4, 7, 9, 4, Blocks.BRICK_BLOCK.getDefaultState(), Blocks.BRICK_BLOCK.getDefaultState(), false);


		//Details
		try
		{
			this.placeMachine(world, box, rand, 6, 0, 1);
			this.placeMachine(world, box, rand, 8, 0, 2);
			//this.placeMachine(world, box, rand, 5, 1, 7);

		} catch(Exception e)
		{
			e.printStackTrace();
		}

		for(int zz = 0; zz <= 9; zz++)
			for(int xx = 0; xx <= 10; xx++)
			{
				this.clearCurrentPositionBlocksUpwards(world, xx, 10, zz, box);
				this.replaceAirAndLiquidDownwards(world, Blocks.COBBLESTONE.getDefaultState(), xx, -1, zz, box);
			}

		if(NCSteamAdditionsConfig.spawn_villager)
			this.spawnVillagers(world, box, 4, 1, 2, 1);
		return true;
	}

	private Block getRandomMachine(Random rand)
	{
		Block[] machines = {
				NCBlocks.manufactory,
				NCBlocks.alloy_furnace,
				NCBlocks.crystallizer,
				NCBlocks.chemical_reactor,
				NCBlocks.cobblestone_generator,
				NCBlocks.decay_generator,
				NCBlocks.decay_hastener,
				NCBlocks.fuel_reprocessor,
				NCBlocks.centrifuge
		};
		return machines[rand.nextInt(machines.length-1)];
	}

	protected boolean placeMachine(World world, StructureBoundingBox box, Random rand, int x, int y, int z)
	{
		int i1 = this.getXWithOffset(x, z);
		int j1 = this.getYWithOffset(y);
		int k1 = this.getZWithOffset(x, z);
		BlockPos pos = new BlockPos(i1, j1, k1);
		Block bl = getRandomMachine(rand);
		if(box.isVecInside(pos)&&(world.getBlockState(pos)!=bl.getStateFromMeta(0)))
		{
			world.setBlockState(pos, bl.getStateFromMeta(0), 2);
			TileEntity tile = world.getTileEntity(pos);
			return true;
		}
		else
			return false;
	}

	protected void placeDoor(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, EnumFacing facing, EnumHingePosition hinge)
	{
		this.setBlockState(worldIn, Blocks.DARK_OAK_DOOR.getDefaultState().withProperty(BlockDoor.FACING, facing).withProperty(BlockDoor.HINGE, hinge), x, y, z, boundingBoxIn);
		this.setBlockState(worldIn, Blocks.DARK_OAK_DOOR.getDefaultState().withProperty(BlockDoor.FACING, facing).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER).withProperty(BlockDoor.HINGE, hinge), x, y+1, z, boundingBoxIn);
	}


	@Override
	protected VillagerProfession chooseForgeProfession(int count, VillagerProfession prof)
	{
		return NCSVillagerHandler.PROF_SCIENTIST;
	}

	public static class VillageManager implements IVillageCreationHandler
	{
		@Override
		public Village buildComponent(PieceWeight villagePiece, Start startPiece, List<StructureComponent> pieces, Random random, int p1, int p2, int p3, EnumFacing facing, int p5)
		{
			StructureBoundingBox box = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 11, 10, 9, facing);
			return (!canVillageGoDeeper(box))||(StructureComponent.findIntersecting(pieces, box)!=null)?null: new VillageScientistHouse(startPiece, p5, random, box, facing);
		}

		@Override
		public PieceWeight getVillagePieceWeight(Random random, int i)
		{
			return new PieceWeight(VillageScientistHouse.class, 15, MathHelper.getInt(random, 0+i, 1+i));
		}

		@Override
		public Class<?> getComponentClass()
		{
			return VillageScientistHouse.class;
		}
	}
}
