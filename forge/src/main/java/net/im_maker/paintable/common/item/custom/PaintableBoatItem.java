package net.im_maker.paintable.common.item.custom;

import net.im_maker.paintable.common.entity.custom.PBoat;
import net.im_maker.paintable.common.entity.custom.PChestBoat;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class PaintableBoatItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final DyeColor color;
    private final boolean hasChest;

    public PaintableBoatItem(boolean hasChest, DyeColor color, Properties properties) {
        super(properties);
        this.hasChest = hasChest;
        this.color = color;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        }

        if (isEntityBlockingPlacement(level, player)) {
            return InteractionResultHolder.pass(itemstack);
        }

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            Boat boat = createBoat(level, hitResult);

            if (boat instanceof PBoat paintableBoat) {
                paintableBoat.setVariant(color);
            } else if (boat instanceof PChestBoat paintableChestBoat) {
                paintableChestBoat.setVariant(color);
            }

            boat.setYRot(player.getYRot());

            if (!level.noCollision(boat, boat.getBoundingBox())) {
                return InteractionResultHolder.fail(itemstack);
            }

            if (!level.isClientSide) {
                level.addFreshEntity(boat);
                level.gameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getLocation());

                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }

        return InteractionResultHolder.pass(itemstack);
    }

    private boolean isEntityBlockingPlacement(Level level, Player player) {
        Vec3 viewVector = player.getViewVector(1.0F);
        List<Entity> entities = level.getEntities(player, player.getBoundingBox().expandTowards(viewVector.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);

        Vec3 eyePosition = player.getEyePosition();
        return entities.stream().anyMatch(entity -> entity.getBoundingBox().inflate(entity.getPickRadius()).contains(eyePosition));
    }

    private Boat createBoat(Level level, HitResult hitResult) {
        return hasChest
                ? new PChestBoat(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z)
                : new PBoat(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
    }
}
