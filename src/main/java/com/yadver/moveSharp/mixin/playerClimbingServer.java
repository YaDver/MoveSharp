package com.yadver.moveSharp.mixin;

import com.yadver.moveSharp.MoveSharp;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class playerClimbingServer extends LivingEntity {
    protected playerClimbingServer(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void lockXZ(CallbackInfo ci) {
        if (this.getDataTracker().get(MoveSharp.IS_CLIMBING)) {
            this.slowMovement(BlockStatees);
            this.setVelocity((double)0.0F, this.getVelocity().y, (double)0.0F);
            this.velocityDirty = true;
        }
    }
}
