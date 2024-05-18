package igentuman.ncsteamadditions.block;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import javax.annotation.Nullable;

public class BlockDummy extends Block {

    public static ItemBlock ITEM_BLOCK;

    public BlockDummy() {
        super(Material.IRON, MapColor.IRON);
        this.setHardness(2.0F);
        this.setResistance(9.0F);
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.0D, -1.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        BlockCustomModelProcessor block = getMainBlock(worldIn, pos);
        if(block == null) {
            worldIn.setBlockToAir(pos);
            return  false;
        };
        return block.onBlockActivated(worldIn, pos.down(), worldIn.getBlockState(pos.down()), playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        BlockCustomModelProcessor block = getMainBlock(worldIn, pos);
        if(block == null) return new ItemStack(ITEM_BLOCK);
        return new ItemStack(block);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (willHarvest) {
            return true;
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }


    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state)
    {
        BlockCustomModelProcessor block = getMainBlock(worldIn, pos);
        if(block == null) return;
        EntityPlayer pl = worldIn.getClosestPlayer(pos.getX(),pos.getY(), pos.getZ(), 10D, false);
        assert pl != null;
        if(pl.isCreative()) {
            super.onPlayerDestroy(worldIn, pos, state);
            return;
            //worldIn.setBlockToAir(pos.down());
        } else {
            super.harvestBlock(worldIn, pl, pos.down(), worldIn.getBlockState(pos.down()), worldIn.getTileEntity(pos.down()), new ItemStack(Items.DIAMOND_PICKAXE));
        }
        worldIn.setBlockToAir(pos);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool) {
        BlockCustomModelProcessor block = getMainBlock(world, pos);
        if(block == null) return;
        super.harvestBlock(world, player, pos.down(), world.getBlockState(pos.down()), world.getTileEntity(pos.down()), tool);
        world.setBlockToAir(pos);
    }

    public BlockCustomModelProcessor getMainBlock(World world, BlockPos pos)
    {
        Block block = world.getBlockState((pos.down())).getBlock();
        if(block instanceof BlockCustomModelProcessor) {
            return (BlockCustomModelProcessor) block;
        }
        return null;
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(!worldIn.isRemote && getMainBlock(worldIn, pos) == null) {
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

}
