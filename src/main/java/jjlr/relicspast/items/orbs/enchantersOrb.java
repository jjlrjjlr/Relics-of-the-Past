package jjlr.relicspast.items.orbs;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jjlr.relicspast.reference;
import jjlr.relicspast.items.basicItem;
import jjlr.relicspast.util.enchantersOrbHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/*
 * jjlr - 01/28/2019
 * Notes:
 *   - Expand code for breaking orb, add perfection value
 *     to nbt to control chance of orb breaking.
 *   - Separate server from client code.
 *   - Look into rewriting how maximum enchantments are
 *     handled, improve remaining enchantment storage
 *     handling.
 */

public class enchantersOrb extends basicItem {

	private static final Logger logger = LogManager.getLogger("Relics_Of_The_Past/enchantersOrb");
	
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
		int maxEnchantments = 0;
		
		if(!helditem.hasTagCompound()) {
		
			enchantersOrbNBT = new NBTTagCompound();
				
			enchantersOrbNBT.setInteger("maxEnchantments", worldIn.rand.nextInt(10)+5);
		} else {
			
			enchantersOrbNBT = helditem.getTagCompound();
			
			numberOfStoredEnchantments = enchantersOrbNBT.getIntArray("storedEnchIds").length;
			
			maxEnchantments = enchantersOrbNBT.getInteger("maxEnchantments");
			
			remainingEnchantmentStorage = maxEnchantments - numberOfStoredEnchantments;
		}
		
		helditem.setTagCompound(enchantersOrbNBT);
		
		logger.info("There are currently {0} enchantments remaining on this orb.", remainingEnchantmentStorage);
		
		if(offhand.isItemEnchanted()) {
			
			if(remainingEnchantmentStorage > 0) {
			
				/*
				 * Start- Get list of enchantments on offhand item and
				 *   add them to the enchantments already present on
				 *   the current orb with 
				 *   enchantersOrbHelper.getEnchCompoundFromArrays
				 *   and enchantersOrbHelper.getEnchantmentIdList.
				 * Then- Retrieve enchantments and levels from
				 *   newEnchantmentList and add them to storedEnchIds
				 *   and storedEnchLvls, then save the edited
				 *   NBT data to the enchanters orb.
				 * Last- Remove enchantments from offhand item using
				 *   enchantersOrbHelper.removeEnchantmentsFromItemStack, 
				 *   if there are more enchantments on the item than
				 *   there are remaining enchantments left for the enchanters
				 *   orb only remove the number of remaining enchantments.
				 */
				
				Map<String, int[]> newEnchantmentList = enchantersOrbHelper.getEnchCompoundFromArrays(enchantersOrbHelper.getEnchantmentIdList(offhand, true), enchantersOrbHelper.getEnchantmentIdList(offhand, false), helditem);
				
				enchantersOrbNBT.setIntArray("storedEnchIds", newEnchantmentList.get("idArray"));
				enchantersOrbNBT.setIntArray("storedEnchLvls", newEnchantmentList.get("lvlArray"));
				
				helditem.setTagCompound(enchantersOrbNBT);
				
				enchantersOrbHelper.removeEnchantmentsFromItemStack(offhand, remainingEnchantmentStorage);
				
				//End of enchantment removal and saving.
				
				//Add stored enchantments to tooltips here.
				
				//Attempt to break item
				if(randomseed > 90) {
					//helditem.shrink(1);
					helditem.damageItem(1, playerIn);
					playerIn.sendMessage(new TextComponentString("This Enchanters orb appears to have broken"));
				}
			} else {
				
				playerIn.sendMessage(new TextComponentString("This Enchanters orb appears to be full."));
			}
			
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, helditem);
		} else if(worldIn.isRemote) {
			
			if(!offhand.isItemEnchanted()) {
			
				playerIn.sendMessage(new TextComponentString("Item is not enchanted"));
			}
			
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, helditem);
		} else {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, helditem);
		}
		
	}
}