package jjlr.relicspast.proxy;

import jjlr.relicspast.reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class clientProxy extends commonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {
		
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(reference.modid + ":" + id, "inventory"));
	}

}
