package jjlr.relicspast.items.orbs;

import java.util.ArrayList;
import java.util.Map;
import jjlr.relicspast.reference;
import jjlr.relicspast.items.basicItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class enchantersOrb extends basicItem {
	
	/*	NBT variables, not to be
	 * accessed outside of this class.*/
	private int[] retrievedEnchantmentIds = new int[15];
	private int[] retrievedEnchantmentLvls = new int[15];
	private int enchantId;
	private int enchantLevel;
	private NBTTagCompound enchantmentNBTReturn = new NBTTagCompound();

	public enchantersOrb() {
		super("orb_enchanters", reference.modid);
		
		setMaxDamage(3);
		setMaxStackSize(1);
		
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
		int randomseed = worldIn.rand.nextInt(100);
		int i = 0;
		
		if(offhand.isItemEnchanted()) {
			
			/*Copy enchantment to helditem, then remove enchantment from offhand item*/
			Map<Enchantment, Integer> enchall = EnchantmentHelper.getEnchantments(offhand);
			for(Enchantment enchantList: enchall.keySet()) {
				enchantId = Enchantment.getEnchantmentID(enchantList);
				enchantLevel = enchall.get(enchantList);
				retrievedEnchantmentIds[i] = enchantId;
				retrievedEnchantmentLvls[i] = enchantLevel;
				i++;
			} 
			
			//Get enchantments currently stored and re add them to the array here.
			//Write stored enchantments to tooltips here.
			
			helditem.setTagCompound(getEnchCompoundFromArrays(retrievedEnchantmentIds, retrievedEnchantmentLvls));
			//helditem.writeToNBT(getEnchCompoundFromArrays(retrievedEnchantmentIds, retrievedEnchantmentLvls));
			
			/*Attempt to break item*/
			if(randomseed > 90) {
				helditem.shrink(1);
				int currentSlot = playerIn.inventory.currentItem;
				playerIn.inventory.setInventorySlotContents(currentSlot, ItemStack.EMPTY);
				playerIn.sendMessage(new TextComponentString("This Enchanters orb appears to have broken"));
			}
			
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, helditem);
		} else if(worldIn.isRemote) {
			
			playerIn.sendMessage(new TextComponentString("Item is not enchanted"));
			
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, helditem);
		} else {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, helditem);
		}
		
	}
	
	/**
	 * @author jjlr
	 * 
	 * @param enchIdsIn Array of enchantment integer id's.
	 * @param enchLvlsIn Array of enchantment integer levels.
	 * 
	 * @return NBTTagCompound containing two NBTTagIntArray's,<br>one for id's and one for levels.
	 */
	public NBTTagCompound getEnchCompoundFromArrays(int[] enchIdsIn, int[] enchLvlsIn) {
		
		if(enchIdsIn.length != 0 && enchLvlsIn.length != 0) {
			
			enchantmentNBTReturn.setIntArray("StoredEnchIds", enchIdsIn);
			enchantmentNBTReturn.setIntArray("StoredEnchLvls", enchLvlsIn);
			if(!enchantmentNBTReturn.isEmpty()) {
				return enchantmentNBTReturn;
			} else {
				System.out.println("WARN: NBTTagComound being returned empty.");
				return enchantmentNBTReturn;
			}
		} else {
			System.out.println("WARN: One or both arrays empty! As a result an empty NBTTagCompound will be returned.");
			return enchantmentNBTReturn;
		}
		
	}

}
