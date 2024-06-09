package igentuman.ncsteamadditions.villager;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import nc.init.*;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.village.*;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

import javax.annotation.Nonnull;
import java.util.Random;

public class NCSVillagerHandler
{
	private static final VillagerRegistry VILLAGER_REGISTRY = VillagerRegistry.instance();
	public static VillagerProfession PROF_SCIENTIST;

	public static void initVillagerHouse()
	{
		if(!NCSteamAdditionsConfig.spawn_villager)
			return;

		VILLAGER_REGISTRY.registerVillageCreationHandler(new VillageScientistHouse.VillageManager());
		MapGenStructureIO.registerStructureComponent(VillageScientistHouse.class, NCSteamAdditions.MOD_ID+":ScientistHouse");
	}

	public static void initVillagerTrades()
	{
		if(!NCSteamAdditionsConfig.spawn_villager)
			return;

		PROF_SCIENTIST = new VillagerProfession(NCSteamAdditions.MOD_ID+":engineer", NCSteamAdditions.MOD_ID+":textures/models/villager_engineer.png", NCSteamAdditions.MOD_ID+":textures/models/villager_engineer_zombie.png");
		ForgeRegistries.VILLAGER_PROFESSIONS.register(PROF_SCIENTIST);

		VillagerRegistry.VillagerCareer career_engineer = new VillagerRegistry.VillagerCareer(PROF_SCIENTIST, NCSteamAdditions.MOD_ID+".engineer");
		career_engineer.addTrade(1,
				new EmeraldForItemstack(new ItemStack(NCItems.part, 2, 0), new EntityVillager.PriceInfo(3, 5)),
				new ItemstackForEmerald(new ItemStack(NCItems.part, 1, 1), new EntityVillager.PriceInfo(2, 4)),
				new ItemstackForEmerald(new ItemStack(NCItems.part, 1, 12), new EntityVillager.PriceInfo(3, 5))
		);
		career_engineer.addTrade(2,
				new EmeraldForItemstack(new ItemStack(NCItems.uranium, 1, 5), new EntityVillager.PriceInfo(1, 2)),
				new ItemstackForEmerald(new ItemStack(NCItems.rad_shielding, 1, 0), new EntityVillager.PriceInfo(2, 4)),
				new ItemstackForEmerald(new ItemStack(NCItems.radaway, 1, 0), new EntityVillager.PriceInfo(15, 22))
		);
		career_engineer.addTrade(3,
				new EmeraldForItemstack(new ItemStack(NCItems.pellet_thorium, 1, 0), new EntityVillager.PriceInfo(2, 6)),
				new EmeraldForItemstack(new ItemStack(NCItems.radiation_badge, 1, 0), new EntityVillager.PriceInfo(2, 4)),
				new ItemstackForEmerald(new ItemStack(NCItems.upgrade, 1, 0), new EntityVillager.PriceInfo(1, 2))
		);
		career_engineer.addTrade(4,
				new EmeraldForItemstack(new ItemStack(NCItems.pellet_thorium, 4, 0), new EntityVillager.PriceInfo(1, 5)),
				new EmeraldForItemstack(new ItemStack(NCItems.part, 1, 11), new EntityVillager.PriceInfo(2, 5)),
				new ItemstackForEmerald(new ItemStack(NCItems.compound, 1, 0), new EntityVillager.PriceInfo(25, 35))
		);


		VillagerRegistry.VillagerCareer career_scientist = new VillagerRegistry.VillagerCareer(PROF_SCIENTIST, NCSteamAdditions.MOD_ID+".scientist");
		career_scientist.addTrade(1,
				new EmeraldForItemstack(new ItemStack(NCBlocks.glowing_mushroom, 5, 0), new EntityVillager.PriceInfo(1, 3)),
				new ItemstackForEmerald(new ItemStack(NCItems.fuel_uranium, 1, 10), new EntityVillager.PriceInfo(6, 12)),
				new ItemstackForEmerald(new ItemStack(NCItems.depleted_fuel_americium, 1, 0), new EntityVillager.PriceInfo(22, 26))
		);
		career_scientist.addTrade(2,
				new EmeraldForItemstack(new ItemStack(NCItems.ingot, 8, 6), new EntityVillager.PriceInfo(1, 3)),
				new ItemstackForEmerald(new ItemStack(NCItems.part, 1, 14), new EntityVillager.PriceInfo(8, 14)),
				new ItemstackForEmerald(new ItemStack(NCItems.radaway_slow, 1, 0), new EntityVillager.PriceInfo(2, 3))
		);
		career_scientist.addTrade(3,
				new EmeraldForItemstack(new ItemStack(NCItems.alloy, 4, 2), new EntityVillager.PriceInfo(1, 2)),
				new EmeraldForItemstack(new ItemStack(NCItems.part, 4, 8), new EntityVillager.PriceInfo(1, 2)),
				new ItemstackForEmerald(new ItemStack(NCItems.depleted_fuel_californium, 1, 3), new EntityVillager.PriceInfo(50, 64))
		);

	}

	private static class EmeraldForItemstack implements EntityVillager.ITradeList
	{
		public ItemStack buyingItem;
		public EntityVillager.PriceInfo buyAmounts;

		public EmeraldForItemstack(@Nonnull ItemStack item, @Nonnull EntityVillager.PriceInfo buyAmounts)
		{
			this.buyingItem = item;
			this.buyAmounts = buyAmounts;
		}

		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
		{
				recipeList.add(new MerchantRecipe(copyStackWithAmount(this.buyingItem, this.buyAmounts.getPrice(random)), Items.EMERALD));
		}
	}

	private static class ItemstackForEmerald implements EntityVillager.ITradeList
	{
		public ItemStack sellingItem;
		public EntityVillager.PriceInfo priceInfo;

		public ItemstackForEmerald(Item par1Item, EntityVillager.PriceInfo priceInfo)
		{
			this.sellingItem = new ItemStack(par1Item);
			this.priceInfo = priceInfo;
		}

		public ItemstackForEmerald(ItemStack stack, EntityVillager.PriceInfo priceInfo)
		{
			this.sellingItem = stack;
			this.priceInfo = priceInfo;
		}

		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
		{
			int i = 1;
			if(this.priceInfo!=null)
				i = this.priceInfo.getPrice(random);
			ItemStack itemstack;
			ItemStack itemstack1;
			if(i < 0)
			{
				itemstack = new ItemStack(Items.EMERALD);
				itemstack1 = copyStackWithAmount(sellingItem, -i);
			}
			else
			{
				itemstack = new ItemStack(Items.EMERALD, i, 0);
				itemstack1 = copyStackWithAmount(sellingItem, 1);
			}
				recipeList.add(new MerchantRecipe(itemstack, itemstack1));
		}
	}

	public static ItemStack copyStackWithAmount(ItemStack stack, int amount)
	{
		if(stack.isEmpty())
			return ItemStack.EMPTY;
		ItemStack s2 = stack.copy();
		s2.setCount(amount);
		return s2;
	}

}
