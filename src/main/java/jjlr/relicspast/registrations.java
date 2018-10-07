package jjlr.relicspast;

import jjlr.relicspast.blocks.modBlocks;
import jjlr.relicspast.items.modItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class registrations {
	
	@EventBusSubscriber
	public static class registrationHandler{
		
		/*Register blocks*/
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			modBlocks.register(event.getRegistry());
		}
		
		/*Register Items*/
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			modBlocks.registerItemBlocks(event.getRegistry());
			modItems.register(event.getRegistry());
		}
		
		/*Register models*/
		@SubscribeEvent
		public static void registerModels(ModelRegistryEvent event) {
			modBlocks.registerModels();
			modItems.registerModels();
		}
	}

}
