package plus.dragons.createdragonlib.mixin;

import static net.minecraft.world.level.block.LiquidBlock.POSSIBLE_FLOW_DIRECTIONS;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createdragonlib.fluid.FluidLavaReaction;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin {
	@Shadow
	@Final
	protected FlowingFluid fluid;

	@Inject(method = "shouldSpreadLiquid", at = @At("HEAD"), cancellable = true)
	@SuppressWarnings("deprecation")
	private void spread(Level level, BlockPos blockPos, BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
		if (this.fluid.is(FluidTags.LAVA))
			for (Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
				BlockPos blockPos2 = blockPos.relative(direction.getOpposite());
				FluidLavaReaction reaction = level.getFluidState(blockPos2).getType() instanceof FlowingFluid flow ? FluidLavaReaction.get(flow) : null;
				if (reaction != null) {
					BlockState state = level.getFluidState(blockPos).isSource() ? reaction.withLava() : reaction.withFlowingLava();
					level.setBlockAndUpdate(blockPos, state);
					level.levelEvent(1501, blockPos, 0);
					cir.setReturnValue(false);
				}
			}
	}
}
