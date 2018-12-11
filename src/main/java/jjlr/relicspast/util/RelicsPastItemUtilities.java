package jjlr.relicspast.util;

import net.minecraft.nbt.NBTTagCompound;

public class RelicsPastItemUtilities {
	
	//Logger relicsPastLogger = Logger.getLogger("relicsPastLogger")
	
	//Variables used for enchantment orb helper.
	private static NBTTagCompound enchantmentNBTReturn;
	
	/**
	 * @author jjlr
	 * 
	 * @param enchIdsIn Array of enchantment integer id's.
	 * @param enchLvlsIn Array of enchantment integer levels.
	 * 
	 * @return NBTTagCompound containing two NBTTagIntArray's,<br>one for id's and one for levels.
	 */
	public static NBTTagCompound getEnchCompoundFromArrays(int[] enchIdsIn, int[] enchLvlsIn) {
		
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
			System.out.println("WARN: One or both arrays empty! As a result nothing will be returned.");
			return null;
		}
		
	}
}
