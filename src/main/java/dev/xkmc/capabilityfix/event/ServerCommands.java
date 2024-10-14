package dev.xkmc.capabilityfix.event;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.xkmc.capabilityfix.init.CFConfig;
import dev.xkmc.capabilityfix.init.CapabilityFix;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CapabilityFix.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerCommands {

	@SubscribeEvent
	public static void serverCommandRegister(RegisterCommandsEvent event) {
		LiteralArgumentBuilder<CommandSourceStack> base = Commands.literal("capabilityfix");
		base.then(Commands.literal("reload_config").executes(ServerCommands::reload))
				.then(Commands.literal("gather_others").executes(ServerCommands::gather));
		event.getDispatcher().register(base);
	}

	private static int gather(CommandContext<CommandSourceStack> ctx) {
		if (CFConfig.COMMON.cacheAll.get()) {
			ctx.getSource().sendSystemMessage(Component.literal("Cache All is enabled. Nothing to gather."));
			return 0;
		}
		int count = 0, total = 0, logged = 0;
		for (var e : CapabilityFix.CACHE.values()) {
			total++;
			if (e.cached()) {
				count++;
				continue;
			}
			if (e.count > 0) {
				ctx.getSource().sendSystemMessage(Component.literal(e.cap().getName() + " - " + e.count));
				e.count = 0;
				logged++;
			}
		}
		if (total == 0) {
			ctx.getSource().sendSystemMessage(Component.literal("No capability was fetched"));
		}
		if (logged == 0) {
			ctx.getSource().sendSystemMessage(Component.literal(count + " Cached capabilities"));
		}
		return 0;
	}

	private static int reload(CommandContext<CommandSourceStack> ctx) {
		ctx.getSource().sendSystemMessage(Component.literal("Cache cleared"));
		CapabilityFix.CACHE.clear();
		CapabilityFix.SET = null;
		CapabilityFix.VERSION++;
		return 0;
	}

}
