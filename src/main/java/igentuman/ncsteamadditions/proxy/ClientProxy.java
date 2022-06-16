package igentuman.ncsteamadditions.proxy;

import static igentuman.ncsteamadditions.NCSteamAdditions.MOD_ID;
import static igentuman.ncsteamadditions.config.NCSteamAdditionsConfig.clientPreInit;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.render.RenderHandler;
import mekanism.client.sound.SoundHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ClientProxy extends CommonProxy 
{

	@Override
	public void preInit(FMLPreInitializationEvent preEvent)
	{
		super.preInit(preEvent);
		clientPreInit();
		RenderHandler.init();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		MinecraftForge.EVENT_BUS.register(SoundHandler.class);
	}


	@Override
	public EntityPlayer getPlayerClient() 
	{
		return Minecraft.getMinecraft().player;
	}


	@Override
	public void registerFluidBlockRendering(Block block, String name)
	{
		super.registerFluidBlockRendering(block, name);
		FluidStateMapper mapper = new FluidStateMapper(name);

		Item item = Item.getItemFromBlock(block);
		ModelBakery.registerItemVariants(item);
		ModelLoader.setCustomMeshDefinition(item, mapper);

		ModelLoader.setCustomStateMapper(block, mapper);
	}

	public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition
	{
		public final ModelResourceLocation location;

		public FluidStateMapper(String name)
		{
			location = new ModelResourceLocation(NCSteamAdditions.MOD_ID + ":fluids", name);
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state)
		{
			return location;
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			return location;
		}
	}
}