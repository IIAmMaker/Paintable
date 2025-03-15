package net.im_maker.paintable.mixin;

import net.im_maker.paintable.common.item.PItems;
import net.im_maker.paintable.common.item.custom.DippedPaintBrushItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheep.class)
public class SheepMixin {
    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void onInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (!(stack.getItem() instanceof DippedPaintBrushItem brush)) return;

        Sheep sheep = (Sheep) (Object) this;
        DyeColor color = brush.getColor();

        if (sheep.getColor() == color) {
            cir.setReturnValue(InteractionResult.PASS);
            return;
        }

        sheep.setColor(color);
        Level world = player.level();

        if (!world.isClientSide) {
            if (stack.getDamageValue() != 15) {
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(interactionHand));
            } else {
                player.setItemInHand(interactionHand, new ItemStack(PItems.PAINT_BRUSH));
                world.playSound(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            world.playSound(null, player.blockPosition(), SoundEvents.MUD_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        cir.setReturnValue(InteractionResult.sidedSuccess(world.isClientSide));
    }
}
