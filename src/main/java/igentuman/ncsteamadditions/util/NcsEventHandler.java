package igentuman.ncsteamadditions.util;

import igentuman.ncsteamadditions.villager.NCSVillagerHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.event.village.MerchantTradeOffersEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;
import java.util.Random;

public class NcsEventHandler {
    @SubscribeEvent
    public void onMerchantTrade(MerchantTradeOffersEvent event)
    {
        if(event.getMerchant() instanceof EntityVillager &&((EntityVillager)event.getMerchant()).getProfessionForge()== NCSVillagerHandler.PROF_SCIENTIST&&event.getList()!=null)
        {
            Iterator<MerchantRecipe> iterator = event.getList().iterator();
            while(iterator.hasNext())
            {
                MerchantRecipe recipe = iterator.next();
                ItemStack output = recipe.getItemToSell();

            }
        }
    }
}
