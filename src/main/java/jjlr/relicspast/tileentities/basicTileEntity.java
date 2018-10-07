package jjlr.relicspast.tileentities;

import javax.annotation.Nullable;

import jjlr.relicspast.blocks.basicBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class basicTileEntity<TE extends TileEntity> extends basicBlock {

	public basicTileEntity(String name, Material material) {
		
		super(name, material);
	}
	
	public abstract Class<TE> getTileEntityClass();
	
	public TE getTileEntity(IBlockAccess worldIn, BlockPos pos) {
		
		return (TE)worldIn.getTileEntity(pos);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		
		return true;
	}
	
	@Nullable
	@Override
	public abstract TE createTileEntity(World worldIn, IBlockState state);
}
