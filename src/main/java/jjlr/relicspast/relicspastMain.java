package jjlr.relicspast;

import org.apache.logging.log4j.*;
import jjlr.relicspast.proxy.commonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = reference.modid, name = reference.name, version = reference.version)
public class relicspastMain {

	private static final Logger logger = LogManager.getLogger("Relics_Of_The_Past");
	
	
	@Instance
	public static relicspastMain instance;
	
	@SidedProxy(clientSide = reference.clientProxy, serverSide = reference.commonProxy)
	public static commonProxy proxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		
		/*debug*/
		logger.info(reference.name + " is starting...");
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event) {
		
		/*debug*/
		logger.info(reference.name + " has Started Successfully.");
	}
	
}
