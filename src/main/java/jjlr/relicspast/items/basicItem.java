package jjlr.relicspast.items;

import jjlr.relicspast.reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class basicItem extends Item {
	
	protected String name;
	
	public basicItem(String name, String modid) {
		
		setRegistryName(reference.modid, name);
		setTranslationKey(name);
	}
	
	/*Register item model with proxy*/
	public void registerItemModel() {
		
		jjlr.relicspast.relicspastMain.proxy.registerItemRenderer(this, 0, name);
	}
	
	/*Set custom creative tab*/
	@Override
	public basicItem setCreativeTab(CreativeTabs tab) {
		
		super.setCreativeTab(tab);
		return this;
	}

}
