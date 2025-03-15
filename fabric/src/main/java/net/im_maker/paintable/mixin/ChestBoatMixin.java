package net.im_maker.paintable.mixin;

import net.im_maker.paintable.common.entity.custom.PBoat;
import net.im_maker.paintable.common.entity.custom.PChestBoat;
import net.im_maker.paintable.common.item.PItems;
import net.im_maker.paintable.common.item.custom.DippedPaintBrushItem;
import net.im_maker.paintable.common.util.PTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoat.class)
public class ChestBoatMixin {
    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void onInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (!(stack.getItem() instanceof DippedPaintBrushItem brush)) return;

        Level world = player.level();

        if (!world.isClientSide) {
            ChestBoat oldBoat = (ChestBoat) (Object) this;
            PChestBoat newBoat = new PChestBoat(world, oldBoat.getX(), oldBoat.getY(), oldBoat.getZ());
            DyeColor color = brush.getColor();
            newBoat.setDeltaMovement(oldBoat.getDeltaMovement());
            newBoat.setYRot(oldBoat.getYRot());
            newBoat.setXRot(oldBoat.getXRot());
            for (Entity passenger : oldBoat.getPassengers()) {
                passenger.startRiding(newBoat, true);
            }
            if (oldBoat.getType().is(PTags.Entities.UNPAINTABLE_BOAT) || oldBoat.getVariant().getName() == "bamboo") {
                cir.setReturnValue(InteractionResult.PASS);
                return;
            }
            if (oldBoat instanceof PChestBoat paintedBoat) {
                if (paintedBoat.getModVariant().getColor() == color) {
                    cir.setReturnValue(InteractionResult.PASS);
                    return;
                }
                paintedBoat.setVariant(color);
            } else {
                newBoat.setVariant(color);
                oldBoat.discard();
                world.addFreshEntity(newBoat);
            }
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
