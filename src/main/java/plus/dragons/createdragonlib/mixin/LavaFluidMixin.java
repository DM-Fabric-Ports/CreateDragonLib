package plus.dragons.createdragonlib.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.LavaFluid;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createdragonlib.fluid.FluidLavaReaction;

@Mixin(LavaFluid.class)
public class LavaFluidMixin {
	@Inject(method = "spreadTo", at = @At("HEAD"), cancellable = true)
	private void spread(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, Direction direction, FluidState fluidState, CallbackInfo ci) {
		if (direction == Direction.DOWN) {
			FluidState fluidState2 = levelAccessor.getFluidState(blockPos);
			if (fluidState.is(FluidTags.LAVA) && fluidState2.getType() instanceof FlowingFluid flow) {
				FluidLavaReaction reaction = FluidLavaReaction.get(flow);
				if (reaction == null) return;
				if (blockState.getBlock() instanceof LiquidBlock)
					levelAccessor.setBlock(blockPos, reaction.lavaOnSelf(), 3);
				levelAccessor.levelEvent(1501, blockPos, 0);
				ci.cancel();
			}
		}
	}
}
