package dev.xkmc.capabilityfix.init;

import com.google.common.collect.Maps;
import dev.xkmc.capabilityfix.content.CapabilityTypeCache;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod(CapabilityFix.MODID)
@Mod.EventBusSubscriber(modid = CapabilityFix.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityFix {

	public static final String MODID = "capabilityfix";
	public static final Logger LOGGER = LogManager.getLogger();

	public CapabilityFix() {
		CFConfig.init();
	}

	public static int VERSION = 0;
	public static final Map<Capability<?>, CapabilityTypeCache> CACHE = Maps.newIdentityHashMap();
	public static Set<String> SET = null;

	public static <T> boolean isValidForCaching(Capability<T> cap) {
		if (CFConfig.COMMON.cacheAll.get()) {
			return true;
		}
		var lookup = CACHE.get(cap);
		if (lookup != null) {
			lookup.count++;
			return lookup.cached();
		}
		if (SET == null) {
			SET = new HashSet<>(CFConfig.COMMON.capabilityCachingWhitelist.get());
		}
		boolean ans = SET.contains(cap.getName());
		CACHE.put(cap, new CapabilityTypeCache(cap, ans));
		return ans;
	}

}
