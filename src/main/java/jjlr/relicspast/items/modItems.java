package jjlr.relicspast.items;

import jjlr.relicspast.reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class modItems {
	
	/*Items*/
	
	public static basicItem orbEnchanters = new basicItem("enchanters_orb", reference.modid).setCreativeTab(CreativeTabs.MATERIALS);
	
	/*Registration*/
	public static void register(IForgeRegistry<Item> registry) {
		registry.registerAll( 
					orbEnchanters
				);
		
	}
	
	public static void registerModels() {
		orbEnchanters.registerItemModel();
	}

}
