package plus.dragons.createdragonlib.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;

@SuppressWarnings("UnstableApiUsage")
public record FluidLavaReaction(BlockState withLava, BlockState withFlowingLava, BlockState lavaOnSelf) {

	private static final IdentityHashMap<FluidVariant, FluidLavaReaction> REACTIONS = new IdentityHashMap<>();

	public static void register(FluidVariant type, BlockState withLava, BlockState withFlowingLava, BlockState lavaOnSelf) {
		REACTIONS.put(type, new FluidLavaReaction(withLava, withFlowingLava, lavaOnSelf));
	}

	@Nullable
	public static FluidLavaReaction get(FlowingFluid fluid) {
		return REACTIONS.keySet().stream().anyMatch(variant -> variant.isOf(fluid.getSource())) ? REACTIONS.entrySet().stream().filter(p -> p.getKey().isOf(fluid.getSource())).findFirst().get().getValue() : null;
	}

}
