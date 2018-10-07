package jjlr.relicspast.blocks.enchantment;

import jjlr.relicspast.blocks.basicBlock;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class enchantmentRemover extends basicBlock {

	public enchantmentRemover(String name) {
		
		super("enchantment_remover", Material.ROCK);
	}
	
	@Override
	public enchantmentRemover setCreativeTab(CreativeTabs tab) {
		
		super.setCreativeTab(tab);
		return this;
	}
	
}
