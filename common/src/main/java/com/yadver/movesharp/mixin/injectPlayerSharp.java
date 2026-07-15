package com.yadver.movesharp.mixin;

import com.yadver.movesharp.movement.PlayerSharp;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//  TODO: Добавить комментарии. А то хули, опять забью на мод и вот гадай, что за велосипед я тут изобрёл.
@Mixin(Player.class)
public abstract class injectPlayerSharp extends LivingEntity implements com.yadver.movesharp.PlayerSharpAccess {
    protected injectPlayerSharp(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }
    @Unique
    private PlayerSharp moveSharp$wrapper;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onPlayerInit(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        this.moveSharp$wrapper = new PlayerSharp(player);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPlayerTick(CallbackInfo ci) {
        if (this.moveSharp$wrapper != null) this.moveSharp$wrapper.tick();
    }

    @Override
    public PlayerSharp moveSharp$getWrapper() {
        return this.moveSharp$wrapper;
    }

//    @Inject(require = 1, method = "defineSynchedData", at = @At("HEAD"))
//    public void initDefineSynchedData(CallbackInfo ci) {
//        this.entityData.define(movesharp.IS_CRAWLING, false);
//        this.entityData.define(movesharp.IS_CLIMBING, false);
//        this.entityData.define(movesharp.IS_SNEAK_BUTTON, false);
//        this.entityData.define(movesharp.IS_SPRINT_BUTTON, false);
//    }
}
