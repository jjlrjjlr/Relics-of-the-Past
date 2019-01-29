package jjlr.relicspast.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class enchantersOrbHelper {
	
	private static final Logger logger = LogManager.getLogger("Relics_Of_The_Past/EnchanterOrbHelper");
	
	/**
	 * @author jjlr
	 * 
	 * @param itemIn ItemStack containing the Stored enchantments to be used in the random pool.
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
			
			tempIdArray.add(idIntArray[temp_x]);
			tempLvlArray.add(lvlIntArray[temp_x]);
			tempPosArray.add(temp_x);
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
	 * Used to get an {@code int[]} containing either the id's or the levels
	 * of all the enchantments on the item {@link itemIn} supplied. 
	 * 
	 * @author jjlr
	 * 
	 * @param itemIn ItemStack to generate enchantment list from.
	 * @param lvlOrId If true returns {@code int[]} containing the ids of the enchantments on the ItemStack, if false returns an {@code int[]} containing the level for each enchantment respectively.
	 * @return Returns an {@code int[]} containing either the ids or levels of the enchantments on the given ItemStack.
	 */
	public static int[] getEnchantmentIdList(ItemStack itemIn, boolean lvlOrId) {
		
		return enchantmentListGenerator(itemIn, lvlOrId);
	}
	
	
	private static int[] enchantmentListGenerator(ItemStack itemIn, boolean lvlOrId) {
		
		ArrayList<Integer> enchantId = new ArrayList<Integer>();
		ArrayList<Integer> enchantLevel = new ArrayList<Integer>();
		
		Map<Enchantment, Integer> enchall = EnchantmentHelper.getEnchantments(itemIn);
		for(Enchantment enchantList: enchall.keySet()) {
			
			enchantId.add(Enchantment.getEnchantmentID(enchantList));
			enchantLevel.add(enchall.get(enchantList));
		} 
		
		int[] returnIds = new int[enchantId.size()];
		int[] returnLvls = new int[enchantLevel.size()];
		int temp_i = 0;
		int temp_j = 0;
		
		for(int i: enchantId) {
			
			returnIds[temp_i] = i;
			temp_i++;
		}
		
		for(int j: enchantLevel) {
			
			returnLvls[temp_j] = j;
			temp_j++;
		}
		
		logger.debug("Enchantments returned from offhand item are {0} at levels {1}", returnIds, returnLvls);
		
		if(lvlOrId == true) {
			
			return returnIds;
		} else {
			
			return returnLvls;
		}
	}
	
	/**
	 * @author jjlr
	 * 
	 * @param enchIdsIn Array of enchantment integer id's to merge into output array.
	 * @param enchLvlsIn Array of enchantment integer levels to merge into output array.
	 * @param itemIn Item containing the initial list of enchantments in {@code storedEnchIds} and {@code storedEnchLvls}.
	 * 
	 * @return Returns Map<String, int[]> containing an array of id's {@code idArray} and lvl's {@code lvlArray}.
	 */
	public static Map<String, int[]> getEnchCompoundFromArrays(int[] enchIdsIn, int[] enchLvlsIn, ItemStack itemIn) {
		
		return intEnchantmentArrayToNBT(enchIdsIn, enchLvlsIn, itemIn);
	}
	
	private static Map<String, int[]> intEnchantmentArrayToNBT(int[] enchIdsIn, int[] enchLvlsIn, ItemStack itemIn) {
		
		ArrayList<Integer> returnIdIntegerArray = new ArrayList<Integer>();
		ArrayList<Integer> returnLvlIntegerArray = new ArrayList<Integer>();
		int maxArrayLength = itemIn.getTagCompound().getInteger("maxEnchantments");
		
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
			Map<String, int[]> returnArrayMap = new HashMap<String, int[]>();
			
			for(int i: returnIdIntegerArray) {
				
				tempIdArray[temp_x] = i;
				temp_x++;
			}
			
			for(int i: returnLvlIntegerArray) {
				
				tempLvlArray[temp_y] = i;
				temp_y++;
			}
			
			returnArrayMap.put("idArray", tempIdArray);
			returnArrayMap.put("lvlArray", tempLvlArray);
			
			return returnArrayMap;
		} else {
			
			Map<String, int[]> returnArrayMap = new HashMap<String, int[]>();
			
			int[] temp_m = {0};
			int[] temp_n = {0};
			
			returnArrayMap.put("idArray", temp_m);
			returnArrayMap.put("lvlArray", temp_n);
			
			return returnArrayMap;
		}
		
	}
	
	/**
	 * Used to remove the enchantments from the given item {@code itemIn}.
	 * If there are fewer enchantments on the item than {@code enchantmentsToRemove}
	 * all the enchantments are removed. If there are more enchantments
	 * than {@code enchantmentsToRemove} then the number of enchantments
	 * supplied by {@code enchantmentsToRemove} will be removed.
	 * 
	 * @param itemIn ItemStack to remove enchantments from.
	 * @param enchantmentsToRemove Number of enchantments to remove, enchantments are removed in order of first position to last.
	 */
	public static void removeEnchantmentsFromItemStack(ItemStack itemIn, int enchantmentsToRemove) {
		
		removeItemStackEnchantments(itemIn, enchantmentsToRemove);
	}
	
	private static void removeItemStackEnchantments(ItemStack itemIn, int enchantmentsToRemove) {
		
		int temp_i = 0;
		int enchNum = itemIn.getEnchantmentTagList().tagCount();
		
		if(enchNum < enchantmentsToRemove) {
			
			while(temp_i < enchNum) {
				
				try {
					itemIn.getEnchantmentTagList().removeTag(0);
				} catch(Exception e) {
					logger.warn("Error at enchantersOrbHelper.");
				}
				temp_i++;
			}
		} else {
			
			while(temp_i < enchantmentsToRemove) {
				
				try {
					itemIn.getEnchantmentTagList().removeTag(0);
				} catch(Exception e) {
					logger.warn("Error at enchantersOrbHelper");
				}
				temp_i++;
			}
		}
		
	}
}
