package jjlr.relicspast.blocks;

import jjlr.relicspast.blocks.enchantment.enchantmentRemover;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class modBlocks {
	
	/*Blocks*/
	
	public static enchantmentRemover EnchantmentRemover = new enchantmentRemover("enchantment_remover").setCreativeTab(CreativeTabs.DECORATIONS);
	
	/*Registration*/
	public static void register(IForgeRegistry<Block> registry) {
		
		registry.registerAll( 
					EnchantmentRemover
				);
	}
	
	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		registry.registerAll(
					EnchantmentRemover.createItemBlock()
				);
	}
	
	public static void registerModels() {
		
		EnchantmentRemover.registerItemmodel(Item.getItemFromBlock(EnchantmentRemover));
	}

}
