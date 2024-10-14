package dev.xkmc.capabilityfix.content;

import net.minecraftforge.common.capabilities.Capability;

public final class CapabilityTypeCache {

	private final Capability<?> cap;
	private final boolean cached;
	public int count = 1;

	public CapabilityTypeCache(Capability<?> cap, boolean cached) {
		this.cap = cap;
		this.cached = cached;
	}

	public Capability<?> cap() {
		return cap;
	}

	public boolean cached() {
		return cached;
	}


}
