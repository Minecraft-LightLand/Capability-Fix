package dev.xkmc.capabilityfix.event;


import dev.xkmc.capabilityfix.init.CapabilityFix;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CapabilityFix.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientCommands {

	@SubscribeEvent
	public static void onClientCommandRegister(RegisterClientCommandsEvent event){

	}

}
