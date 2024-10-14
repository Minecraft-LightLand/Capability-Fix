package dev.xkmc.capabilityfix.mixin;

import com.google.common.collect.Maps;
import dev.xkmc.capabilityfix.content.CapabilityCache;
import dev.xkmc.capabilityfix.init.CapabilityFix;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(Entity.class)
public abstract class EntityMixin extends CapabilityProvider<Entity> {

	@Shadow
	public int tickCount;

	protected EntityMixin(Class<Entity> baseClass) {
		super(baseClass);
	}

	@Unique
	private int capabilityfix$cacheVersion = CapabilityFix.VERSION;
	@Unique
	private final Map<Capability<?>, CapabilityCache<?>> capabilityfix$cache = Maps.newIdentityHashMap();

	@Override
	public void invalidateCaps() {
		capabilityfix$cache.clear();
		super.invalidateCaps();
	}

	@Override
	public void reviveCaps() {
		capabilityfix$cache.clear();
		super.reviveCaps();
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (side == null && CapabilityFix.isValidForCaching(cap)) {
			if (capabilityfix$cacheVersion != CapabilityFix.VERSION) {
				capabilityfix$cacheVersion = CapabilityFix.VERSION;
				capabilityfix$cache.clear();
			}
			var lookup = capabilityfix$cache.get(cap);
			if (lookup != null) {
				return lookup.data().cast();
			}
			var ans = super.getCapability(cap, null);
			capabilityfix$cache.put(cap, new CapabilityCache<>(cap, ans, tickCount));
			return ans;
		}
		return super.getCapability(cap, side);
	}
}
