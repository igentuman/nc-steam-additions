package igentuman.ncsteamadditions.villager;

import igentuman.ncsteamadditions.NCSteamAdditions;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

import javax.annotation.Nonnull;
import java.util.Random;

public class NCSVillagerHandler
{
	private static final VillagerRegistry VILLAGER_REGISTRY = VillagerRegistry.instance();
	public static VillagerProfession PROF_SCIENTIST;

	@ObjectHolder("minecraft:librarian")
	public static final VillagerProfession LIBRARIAN = null;

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

		PROF_SCIENTIST = new VillagerProfession(NCSteamAdditions.MOD_ID+":engineer", "immersiveengineering:textures/models/villager_engineer.png", "immersiveengineering:textures/models/villager_engineer_zombie.png");
		ForgeRegistries.VILLAGER_PROFESSIONS.register(PROF_SCIENTIST);

		/* Engineer
		 * Deals in treated wood, later metal rods, scaffold and concrete
		 */
		VillagerRegistry.VillagerCareer career_engineer = new VillagerRegistry.VillagerCareer(PROF_SCIENTIST, NCSteamAdditions.MOD_ID+".engineer");
		/*career_engineer.addTrade(1,
				new EmeraldForItemstack(new ItemStack(IEContent.itemMaterial, 1, 0), new EntityVillager.PriceInfo(8, 16)),
				new ItemstackForEmerald(new ItemStack(IEContent.blockWoodenDecoration, 1, 1), new EntityVillager.PriceInfo(-10, -6)),
				new ItemstackForEmerald(new ItemStack(IEContent.blockClothDevice, 1, 1), new EntityVillager.PriceInfo(-3, -1))
		);
		career_engineer.addTrade(2,
				new EmeraldForItemstack(new ItemStack(IEContent.itemMaterial, 1, 1), new EntityVillager.PriceInfo(2, 6)),
				new ItemstackForEmerald(new ItemStack(IEContent.blockMetalDecoration1, 1, 1), new EntityVillager.PriceInfo(-8, -4)),
				new ItemstackForEmerald(new ItemStack(IEContent.blockMetalDecoration1, 1, 5), new EntityVillager.PriceInfo(-8, -4))
		);
		career_engineer.addTrade(3,
				new EmeraldForItemstack(new ItemStack(IEContent.itemMaterial, 1, 2), new EntityVillager.PriceInfo(2, 6)),
				new EmeraldForItemstack(new ItemStack(IEContent.itemMaterial, 1, 7), new EntityVillager.PriceInfo(4, 8)),
				new ItemstackForEmerald(new ItemStack(IEContent.blockStoneDecoration, 1, 5), new EntityVillager.PriceInfo(-6, -2))
		);
		career_engineer.addTrade(4,
				new OreveinMapForEmeralds()
		)*/;

		/* Machinist
		 * Sells tools, metals, blueprints and drillheads
		 */
		VillagerRegistry.VillagerCareer career_machinist = new VillagerRegistry.VillagerCareer(PROF_SCIENTIST, NCSteamAdditions.MOD_ID+".machinist");
		/*career_machinist.addTrade(1,
				new EmeraldForItemstack(new ItemStack(IEContent.itemMaterial, 1, 6), new EntityVillager.PriceInfo(8, 16)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemTool, 1, 0), new EntityVillager.PriceInfo(4, 7))
		);
		career_machinist.addTrade(2,
				new EmeraldForItemstack(new ItemStack(IEContent.itemMetal, 1, 0), new EntityVillager.PriceInfo(4, 6)),
				new EmeraldForItemstack(new ItemStack(IEContent.itemMetal, 1, 1), new EntityVillager.PriceInfo(4, 6)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemMaterial, 1, 9), new EntityVillager.PriceInfo(1, 3))
		);
		career_machinist.addTrade(3,
				new ItemstackForEmerald(new ItemStack(IEContent.itemToolbox, 1, 0), new EntityVillager.PriceInfo(6, 8)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemMaterial, 1, 10), new EntityVillager.PriceInfo(1, 3))
		);
		career_machinist.addTrade(4,
				new ItemstackForEmerald(new ItemStack(IEContent.itemDrillhead, 1, 1), new EntityVillager.PriceInfo(28, 40)),
				new ItemstackForEmerald(IEContent.itemEarmuffs, new EntityVillager.PriceInfo(4, 9))
		);
		career_machinist.addTrade(5,
				new ItemstackForEmerald(new ItemStack(IEContent.itemDrillhead, 1, 0), new EntityVillager.PriceInfo(32, 48)),
				new ItemstackForEmerald(BlueprintCraftingRecipe.getTypedBlueprint("electrode"), new EntityVillager.PriceInfo(12, 24))
		);*/

		/* Electrician
		 * Sells wires, tools and the faraday suit
		 */
		VillagerRegistry.VillagerCareer career_electrician = new VillagerRegistry.VillagerCareer(PROF_SCIENTIST, NCSteamAdditions.MOD_ID+".electrician");
		/*career_electrician.addTrade(1,
				new EmeraldForItemstack(new ItemStack(IEContent.itemMaterial, 1, 20), new EntityVillager.PriceInfo(8, 16)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemTool, 1, 1), new EntityVillager.PriceInfo(4, 7)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemWireCoil, 1, 0), new EntityVillager.PriceInfo(-4, -2))
		);
		career_electrician.addTrade(2,
				new EmeraldForItemstack(new ItemStack(IEContent.itemMaterial, 1, 21), new EntityVillager.PriceInfo(6, 12)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemTool, 1, 2), new EntityVillager.PriceInfo(4, 7)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemWireCoil, 1, 1), new EntityVillager.PriceInfo(-4, -1))
		);
		career_electrician.addTrade(3,
				new EmeraldForItemstack(new ItemStack(IEContent.itemMaterial, 1, 22), new EntityVillager.PriceInfo(4, 8)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemWireCoil, 1, 2), new EntityVillager.PriceInfo(-2, -1)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemToolUpgrades, 1, 6), new EntityVillager.PriceInfo(8, 12))
		);
		career_electrician.addTrade(4,
				new ItemstackForEmerald(new ItemStack(IEContent.itemToolUpgrades, 1, 9), new EntityVillager.PriceInfo(8, 12)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemFluorescentTube), new EntityVillager.PriceInfo(8, 12)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemsFaradaySuit[0]), new EntityVillager.PriceInfo(5, 7)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemsFaradaySuit[1]), new EntityVillager.PriceInfo(9, 11)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemsFaradaySuit[2]), new EntityVillager.PriceInfo(5, 7)),
				new ItemstackForEmerald(new ItemStack(IEContent.itemsFaradaySuit[3]), new EntityVillager.PriceInfo(11, 15))
		);*/
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
