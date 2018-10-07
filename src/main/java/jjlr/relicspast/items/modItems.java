package jjlr.relicspast.items;

import jjlr.relicspast.items.orbs.enchantersOrb;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class modItems {
	
	/*Items*/
	
	//public static basicItem orbEnchanters = new basicItem("enchanters_orb", reference.modid).setCreativeTab(CreativeTabs.MATERIALS);
	public static enchantersOrb EnchantersOrb = new enchantersOrb().setCreativeTab(CreativeTabs.BREWING);
	
	/*Registration*/
	public static void register(IForgeRegistry<Item> registry) {
		registry.registerAll( 
				
					EnchantersOrb
				);
		
	}
	
	public static void registerModels() {
		EnchantersOrb.registerItemModel();
	}

}
