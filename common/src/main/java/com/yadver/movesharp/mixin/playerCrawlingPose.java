package com.yadver.movesharp.mixin;

import com.yadver.movesharp.movement.PlayerSharp;
import com.yadver.movesharp.PlayerSharpAccess;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//  TODO: Добавить комментарии. А то хули, опять забью на мод и вот гадай, что за велосипед я тут изобрёл.
@Mixin(Player.class)
public abstract class playerCrawlingPose extends LivingEntity {
    @Shadow
    protected abstract boolean canPlayerFitWithinBlocksAndEntitiesWhen(Pose pose);

    protected playerCrawlingPose(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
    private void updatePlayerPoseHEAD(CallbackInfo ci) {
        PlayerSharpAccess access = (PlayerSharpAccess) this;
        PlayerSharp playerSharp = access.moveSharp$getWrapper();
        if (this.canPlayerFitWithinBlocksAndEntitiesWhen(Pose.SWIMMING)
                && playerSharp.crawl_manager.canCrawling()
        ) {
            this.setPose(Pose.SWIMMING);
            ci.cancel();
        }
    }

//    @Inject(method = "updatePlayerPose", at = @At("TAIL"))
//    private void updatePlayerPoseTAIL(CallbackInfo ci) {
//        PlayerSharpAccess access = (PlayerSharpAccess) this;
//        PlayerSharp playerSharp = access.moveSharp$getWrapper();
//        if (this.canEnterPose(Pose.SWIMMING)    private final double offset = (double) 1 / 16;    private final double offset = (double) 1 / 16;
//                && playerSharp.crawl_manager.canCrawling()
//        ) {
//            this.setPose(Pose.SWIMMING);
//        }
//        playerSharp.crawl_manager.update();
//    }
}
