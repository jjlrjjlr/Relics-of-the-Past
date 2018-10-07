package jjlr.relicspast.items.orbs;

import jjlr.relicspast.reference;
import jjlr.relicspast.items.basicItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class enchantersOrb extends basicItem {

	public enchantersOrb() {
		super("orb_enchanters", reference.modid);
	}
	
	@Override
	public enchantersOrb setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		
		ItemStack helditem = playerIn.getHeldItem(handIn);
		
		ItemStack offhand = playerIn.getHeldItemOffhand();
		
		if(offhand.isItemEnchanted() && worldIn.isRemote) {
			
			String taglist = offhand.getEnchantmentTagList().toString();
			
			playerIn.sendMessage(new TextComponentString(taglist));
			
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, helditem);
		} else if(worldIn.isRemote) {
			
			playerIn.sendMessage(new TextComponentString("Item is not enchanted"));
			
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, helditem);
		} else {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, helditem);
		}
		
	}

}
