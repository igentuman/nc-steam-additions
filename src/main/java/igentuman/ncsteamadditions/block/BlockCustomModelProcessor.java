package igentuman.ncsteamadditions.block;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.processors.*;
import nc.block.property.BlockProperties;
import nc.block.tile.*;
import nc.init.NCItems;
import nc.tile.*;
import nc.tile.fluid.ITileFluid;
import nc.tile.processor.IProcessor;
import nc.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.Nonnull;
import java.util.Random;

import static nc.block.property.BlockProperties.*;

public class BlockCustomModelProcessor extends BlockSidedTile implements IActivatable, ITileType
{
	protected ProcessorType type;
	protected AbstractProcessor processor;

	public <T extends AbstractProcessor> BlockCustomModelProcessor(T processor)
	{
		super(Material.IRON);
		if (processor.getCreativeTab() != null)
			setCreativeTab(processor.getCreativeTab());
		this.type = processor.getType();
		this.processor = processor;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		if(processor == null) return true;
		return processor.isFullCube();
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		if(processor == null) return true;
		return processor.isFullCube();
	}
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return processor.getAABB();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public @Nonnull BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public String getTileName()
	{
		return type.getName();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return type.getTile();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.values()[meta & 7];
		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}
		return getDefaultState().withProperty(FACING_HORIZONTAL, enumfacing).withProperty(ACTIVE,
				Boolean.valueOf((meta & 8) > 0));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = state.getValue(FACING_HORIZONTAL).getIndex();
		if (state.getValue(ACTIVE).booleanValue())
			i |= 8;
		return i;
	}

	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {

		if(worldIn.isRemote || worldIn.getBlockState((pos.up())).getBlock() instanceof BlockDummy) return;
		EntityPlayer pl = worldIn.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10, false);
		if(pl != null && pl.isCreative()) {
			worldIn.setBlockToAir(pos);
			return;
		}
		EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ());
		IBlockState target = worldIn.getBlockState(pos);
		ItemStack items = getItem(worldIn,pos,target);
		if(target.getBlock().hasTileEntity(target)) {
			items.deserializeNBT(worldIn.getTileEntity(pos).serializeNBT());
		}
		item.setItem(items);
		worldIn.setBlockToAir(pos);
		worldIn.spawnEntity(item);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING_HORIZONTAL, ACTIVE});
	}

	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(BlockProperties.FACING_HORIZONTAL, placer.getHorizontalFacing().getOpposite()).withProperty(BlockProperties.ACTIVE, false);
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(worldIn.isRemote) return;
		//Block dummy = new BlockDummy(this, pos);
		BlockDummy dummy = (BlockDummy) ((ItemBlock)new ItemStack(Blocks.otherBlocks[1]).copy().getItem()).getBlock();
		BlockDummy.ITEM_BLOCK.placeBlockAt(new ItemStack(dummy).copy(), (EntityPlayer) placer,worldIn,(pos.up()),EnumFacing.NORTH,pos.getX(),pos.getY(),pos.getZ(),dummy.getDefaultState());
	}


	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{

		if (player == null || hand != EnumHand.MAIN_HAND)
			return false;

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof ITileInstallable && ((ITileInstallable) tile).tryInstall(player, hand, facing)) {
			return true;
		}

		if (player.isSneaking())
			return false;

		if (!(tile instanceof ITileFluid) && !(tile instanceof ITileGui))
			return false;
		if (tile instanceof ITileFluid && !(tile instanceof ITileGui)
				&& FluidUtil.getFluidHandler(player.getHeldItem(hand)) == null)
			return false;

		if (tile instanceof ITileFluid)
		{
			if (world.isRemote)
				return true;
			ITileFluid tileFluid = (ITileFluid) tile;
			boolean accessedTanks = BlockHelper.accessTanks(player, hand, facing, tileFluid);
			if (accessedTanks)
			{
				if (tile instanceof IProcessor)
				{
					((IProcessor) tile).refreshRecipe();
					((IProcessor) tile).refreshActivity();
				}
				return true;
			}
		}
		if (tile instanceof ITileGui)
		{
			if (world.isRemote)
			{
				onGuiOpened(world, pos);
				return true;
			}
			else
			{
				onGuiOpened(world, pos);
				if (tile instanceof IProcessor)
				{
					((IProcessor) tile).refreshRecipe();
					((IProcessor) tile).refreshActivity();
				}
				FMLNetworkHandler.openGui(player, NCSteamAdditions.instance, ((ITileGui) tile).getContainerInfo().getGuiId(), world, pos.getX(),
						pos.getY(), pos.getZ());
			}
		}
		else
			return false;

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		if (!state.getValue(ACTIVE))
			return;
		processor.spawnParticles(pos, rand, world, state);
	}
}
