package jjlr.relicspast.util;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class enchantersOrbHelper {

	@SuppressWarnings(value = {"unused" })
	private void nbtToEnchantmentList() {
		
	}
	
	/**
	 * @author jjlr
	 * @param itemIn ItemStack containing the Stored enchantments to be used in the random pool.
	 * 
	 * @param getEnchantmentPosition If true the chosen enchantment being returned will be removed from the ItemStack.
	 * 
	 * @return Returns an integer array containing one enchantment id and the level of that enchantment respectively, i.e. returnedInt[enchantmentId, EnchantmentLvl]. If removeEnchantmentOnReturn is true, then the returned integer array will contain an extra number marking the enchantment that was chosen, i.e. returnedInt[enchantmentId, enchantmentLvl, chosenEnchantmentPlace].
	 */
	public int[] getRandomEnchantment(ItemStack itemIn, boolean getEnchantmentPosition) {
		
		return randomEnchantment(itemIn, getEnchantmentPosition);
	}
	
	private int[] randomEnchantment(ItemStack itemIn, boolean getEnchantmentPosition) {
		
		int[] idIntArray = itemIn.getTagCompound().getIntArray("StoredEnchIds");
		int[] lvlIntArray = itemIn.getTagCompound().getIntArray("StoredEnchLvls");
		
		ArrayList<Integer> tempIdArray = new ArrayList<Integer>();
		ArrayList<Integer> tempLvlArray = new ArrayList<Integer>();
		ArrayList<Integer> tempPosArray = new ArrayList<Integer>();
		
		for(int temp_x = 0; temp_x < idIntArray.length; temp_x++) {
			
			if(idIntArray[temp_x] != -314) {
				
				tempIdArray.add(idIntArray[temp_x]);
				tempLvlArray.add(lvlIntArray[temp_x]);
				tempPosArray.add(temp_x);
			}
		}
		
		int randomInt = RandomUtils.nextInt(0, tempIdArray.size());
		
		if(getEnchantmentPosition == true) {
			
			int[] returnInt = {idIntArray[randomInt], lvlIntArray[randomInt], tempPosArray.get(randomInt)};
			return returnInt;
		} else {
			
			int[] returnInt = {idIntArray[randomInt], lvlIntArray[randomInt]};
			return returnInt;
		}
	}
	
	/**
	 * 
	 * @param itemIn ItemStack to generate enchantment list from.
	 * @param lvlOrId If true returns int[] containing the ids of the enchantments on the ItemStack, if false returns an int[] containing the level for each enchantment respectively.
	 * @return Returns an int[] containing either the ids or levels of the enchantments on the given ItemStack.
	 */
	public static int[] getEnchantmentIdList(ItemStack itemIn, boolean lvlOrId) {
		
		return enchantmentListGenerator(itemIn, lvlOrId);
	}
	
	
	private static int[] enchantmentListGenerator(ItemStack itemIn, boolean lvlOrId) {
		
		ArrayList<Integer> enchantId = new ArrayList<Integer>();
		ArrayList<Integer> enchantLevel = new ArrayList<Integer>();
		
		/*Copy enchantment to helditem, then remove enchantment from offhand item*/
		Map<Enchantment, Integer> enchall = EnchantmentHelper.getEnchantments(itemIn);
		for(Enchantment enchantList: enchall.keySet()) {
			
			enchantId.add(Enchantment.getEnchantmentID(enchantList));
			enchantLevel.add(enchall.get(enchantList));
		} 
		
		int[] returnIds = new int[enchantId.size()];
		int[] returnLvls = new int[enchantLevel.size()];
		
		for(int i: enchantId) {
			
			returnIds[i] = i;
			returnLvls[i] = i;
		}
		
		if(lvlOrId == true) {
			
			return returnIds;
		} else {
			
			return returnLvls;
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
	public static NBTTagCompound getEnchCompoundFromArrays(int[] enchIdsIn, int[] enchLvlsIn, ItemStack itemIn, NBTTagCompound nbtIn) {
		
		return intEnchantmentArrayToNBT(enchIdsIn, enchLvlsIn, itemIn, nbtIn);
	}
	
	private static NBTTagCompound intEnchantmentArrayToNBT(int[] enchIdsIn, int[] enchLvlsIn, ItemStack itemIn, NBTTagCompound nbtIn) {
		
		ArrayList<Integer> returnIdIntegerArray = new ArrayList<Integer>();
		ArrayList<Integer> returnLvlIntegerArray = new ArrayList<Integer>();
		int maxArrayLength = itemIn.getTagCompound().getInteger("maxEnchantments");
		
		NBTTagCompound enchantmentNBTReturn;
		
		if(nbtIn == null) {
			
			enchantmentNBTReturn = new NBTTagCompound();
		} else {
			
			enchantmentNBTReturn = nbtIn;
		}
		
		for(int i: itemIn.getTagCompound().getIntArray("StoredEnchIds")) {
			
			returnIdIntegerArray.add(i);
		}
		
		for(int i: itemIn.getTagCompound().getIntArray("StoredEnchLvls")) {
			
			returnLvlIntegerArray.add(i);
		}
		
		
		if(enchIdsIn.length != 0 && enchLvlsIn.length != 0) {
			
			for(int i: enchIdsIn) {
				
				if(returnIdIntegerArray.size() < maxArrayLength) {
					returnIdIntegerArray.add(i);
				}
			}
			
			for(int i: enchLvlsIn) {
				
				if(returnLvlIntegerArray.size() < maxArrayLength) {
					returnLvlIntegerArray.add(i);
				}
			}
			
			int[] tempIdArray = new int[returnIdIntegerArray.size()];
			int[] tempLvlArray = new int[returnIdIntegerArray.size()];
			int temp_x = 0;
			int temp_y = 0;
			
			for(int i: returnIdIntegerArray) {
				
				tempIdArray[temp_x] = i;
				temp_x++;
			}
			
			for(int i: returnLvlIntegerArray) {
				
				tempLvlArray[temp_y] = i;
				temp_y++;
			}
			
			enchantmentNBTReturn.setIntArray("StoredEnchIds", tempIdArray);
			enchantmentNBTReturn.setIntArray("StoredEnchLvls", tempLvlArray);
			
			return enchantmentNBTReturn;
		} else {
			
			return enchantmentNBTReturn;
		}
		
	}
	
	/**
	 * 
	 * @param itemIn ItemStack to remove enchantments from.
	 * @param enchantmentsToRemove Number of enchantments to remove, enchantments are removed in order of first position to last.
	 */
	public static void removeEnchantmentsFromItemStack(ItemStack itemIn, int enchantmentsToRemove) {
		
		removeItemStackEnchantments(itemIn, enchantmentsToRemove);
	}
	
	private static void removeItemStackEnchantments(ItemStack itemIn, int enchantmentsToRemove) {
		
		System.out.println("Enchantments not removed.");
	}
}
