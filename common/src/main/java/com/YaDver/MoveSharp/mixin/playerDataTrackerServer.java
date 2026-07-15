package com.YaDver.MoveSharp.mixin;

import com.YaDver.MoveSharp.MoveSharp;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class playerDataTrackerServer extends LivingEntity {
    protected playerDataTrackerServer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }
    @Inject(require = 1, method = "defineSynchedData", at = @At("HEAD"))
    public void initDefineSynchedData(CallbackInfo ci) {
        this.entityData.define(MoveSharp.IS_CRAWLING, false);
        this.entityData.define(MoveSharp.IS_CLIMBING, false);
    }
}
