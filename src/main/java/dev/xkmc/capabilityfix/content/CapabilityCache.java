package dev.xkmc.capabilityfix.content;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public record CapabilityCache<T>(Capability<T> cap, LazyOptional<T> data, int since) {

}
