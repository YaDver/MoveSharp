package com.yadver.moveSharp.client.mixin;

import com.mojang.authlib.GameProfile;
import com.yadver.moveSharp.MoveSharp;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class playerClimbingClient extends AbstractClientPlayerEntity {

    public playerClimbingClient(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void movesharp$disableWASD(CallbackInfo ci) {
        if (this.getDataTracker().get(MoveSharp.IS_CLIMBING)) {
        }
    }
}
