package igentuman.ncsteamadditions.block;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.processors.*;
import nc.block.property.BlockProperties;
import nc.block.tile.*;
import nc.init.NCItems;
import nc.tile.ITileGui;
import nc.tile.fluid.ITileFluid;
import nc.tile.processor.IProcessor;
import nc.util.BlockHelper;
import nclegacy.tile.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.items.*;

import javax.annotation.Nonnull;
import java.util.Random;

import static nc.block.property.BlockProperties.*;

public class BlockProcessor extends BlockSidedTile implements IActivatable, ITileType
{
	protected ProcessorType type;
	private AbstractProcessor processor;

	public <T extends AbstractProcessor> BlockProcessor(T processor)
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


	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING_HORIZONTAL, ACTIVE});
	}

	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(BlockProperties.FACING_HORIZONTAL, placer.getHorizontalFacing().getOpposite()).withProperty(BlockProperties.ACTIVE, false);
	}


	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player == null || hand != EnumHand.MAIN_HAND)
			return false;

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof IUpgradableLegacy)
		{
			if (installUpgrade(tile, ((IUpgradableLegacy) tile).getSpeedUpgradeSlot(), player, hand, facing,
					new ItemStack(NCItems.upgrade, 1, 0)))
				return true;
		}

		if (player.isSneaking())
			return false;

		if (!(tile instanceof ITileFluid) && !(tile instanceof ITileGui) && !(tile instanceof ITileGuiLegacy))
			return false;
		if (tile instanceof ITileFluid && !(tile instanceof ITileGui) && !(tile instanceof ITileGuiLegacy) && FluidUtil.getFluidHandler(player.getHeldItem(hand)) == null)
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
				else if (tile instanceof IProcessorLegacy)
				{
					((IProcessorLegacy) tile).refreshRecipe();
					((IProcessorLegacy) tile).refreshActivity();
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
				else if (tile instanceof IProcessorLegacy)
				{
					((IProcessorLegacy) tile).refreshRecipe();
					((IProcessorLegacy) tile).refreshActivity();
				}
				FMLNetworkHandler.openGui(player, NCSteamAdditions.instance, ((ITileGui) tile).getContainerInfo().getGuiId(), world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		else if (tile instanceof ITileGuiLegacy)
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
				else if (tile instanceof IProcessorLegacy)
				{
					((IProcessorLegacy) tile).refreshRecipe();
					((IProcessorLegacy) tile).refreshActivity();
				}
				FMLNetworkHandler.openGui(player, NCSteamAdditions.instance, ((ITileGuiLegacy) tile).getGuiID(), world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		else
			return false;

		return true;
	}
	
	protected boolean installUpgrade(TileEntity tile, int slot, EntityPlayer player, EnumHand hand, EnumFacing facing, @Nonnull ItemStack stack) {
		ItemStack held = player.getHeldItem(hand);
		if (held.isItemEqual(stack)) {
			IItemHandler inv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
			
			if (inv != null && inv.isItemValid(slot, held)) {
				if (player.isSneaking()) {
					player.setHeldItem(EnumHand.MAIN_HAND, inv.insertItem(slot, held, false));
					return true;
				}
				else {
					if (inv.insertItem(slot, stack, false).isEmpty()) {
						player.getHeldItem(hand).shrink(1);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		if (!state.getValue(ACTIVE))
			return;
		BlockHelper.spawnParticleOnProcessor(state, world, pos, rand, state.getValue(FACING_HORIZONTAL),
				type.getParticle1());
		BlockHelper.spawnParticleOnProcessor(state, world, pos, rand, state.getValue(FACING_HORIZONTAL),
				type.getParticle2());
	}
}
