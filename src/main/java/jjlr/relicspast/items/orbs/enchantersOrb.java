package jjlr.relicspast.items.orbs;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;

import jjlr.relicspast.reference;
import jjlr.relicspast.items.basicItem;
import jjlr.relicspast.util.enchantersOrbHelper;
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
		NBTTagCompound enchantersOrbNBT;
		int randomseed = worldIn.rand.nextInt(100);
		int numberOfStoredEnchantments = 0;
		int remainingEnchantmentStorage = 0;
		
		if(!helditem.hasTagCompound()) {
		
			enchantersOrbNBT = new NBTTagCompound();
				
			enchantersOrbNBT.setInteger("maxEnchantments", worldIn.rand.nextInt(10)+5);
			
			numberOfStoredEnchantments = enchantersOrbNBT.getIntArray("StoredEnchIds").length;
			
			remainingEnchantmentStorage = enchantersOrbNBT.getInteger("maxEnchantments") - numberOfStoredEnchantments;
		} else {
			
			enchantersOrbNBT = helditem.getTagCompound();
		}
		
		helditem.setTagCompound(enchantersOrbNBT);
		
		if(offhand.isItemEnchanted()) {
			
			//Get enchantments currently stored and re add them to the array here.
			//Write stored enchantments to tooltips here.
			
			enchantersOrbNBT = enchantersOrbHelper.getEnchCompoundFromArrays(enchantersOrbHelper.getEnchantmentIdList(helditem, true), enchantersOrbHelper.getEnchantmentIdList(helditem, false), helditem, enchantersOrbNBT);
			helditem.setTagCompound(enchantersOrbNBT);
			//helditem.writeToNBT(getEnchCompoundFromArrays(retrievedEnchantmentIds, retrievedEnchantmentLvls));
			
			if(remainingEnchantmentStorage > 0) {
			
				enchantersOrbHelper.removeEnchantmentsFromItemStack(offhand, remainingEnchantmentStorage);
				
				/*Attempt to break item*/
				if(randomseed > 90) {
					//helditem.shrink(1);
					//int currentSlot = playerIn.inventory.currentItem;
					//playerIn.inventory.setInventorySlotContents(currentSlot, ItemStack.EMPTY);
					helditem.damageItem(1, playerIn);
					playerIn.sendMessage(new TextComponentString("This Enchanters orb appears to have broken"));
				}
			} else {
				
				playerIn.sendMessage(new TextComponentString("This Enchanters orb appears to be full."));
			}
			
			System.out.println(remainingEnchantmentStorage);
			
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, helditem);
		} else if(worldIn.isRemote) {
			
			playerIn.sendMessage(new TextComponentString("Item is not enchanted"));
			
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, helditem);
		} else {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, helditem);
		}
		
	}
}