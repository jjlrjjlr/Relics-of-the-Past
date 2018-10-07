package jjlr.relicspast.blocks.enchantment;

import jjlr.relicspast.blocks.basicBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class enchantmentRemover extends basicBlock {

	public enchantmentRemover(String name) {
		
		super("enchantment_remover", Material.ROCK);
	}
	
}
