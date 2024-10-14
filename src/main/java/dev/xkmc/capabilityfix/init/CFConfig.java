package dev.xkmc.capabilityfix.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class CFConfig {

	public static class Common {

		public final ForgeConfigSpec.BooleanValue cacheAll;
		public final ForgeConfigSpec.ConfigValue<List<String>> capabilityCachingWhitelist;

		Common(ForgeConfigSpec.Builder builder) {
			cacheAll = builder.comment("Cache all entity capabilities. Could cause problems. Use it with caution").define("cacheAll",false);
			capabilityCachingWhitelist = builder.define("capabilityCachingWhitelist", new ArrayList<>(List.of("top/theillusivec4/curios/api/type/capability/ICuriosItemHandler")));

		}

	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	public static void init() {
		register(ModConfig.Type.COMMON, COMMON_SPEC);
	}

	private static void register(ModConfig.Type type, IConfigSpec<?> spec) {
		var mod = ModLoadingContext.get().getActiveContainer();
		String path = mod.getModId() + "-" + type.extension() + ".toml";
		ModLoadingContext.get().registerConfig(type, spec, path);
	}


}
