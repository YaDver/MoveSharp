package com.yadver.movesharp.movement;

import com.yadver.movesharp.utils.SmoothAcceleration;
import net.minecraft.world.phys.Vec3;

import static com.mojang.text2speech.Narrator.LOGGER;

//  TODO: Добавить комментарии. А то хули, опять забью на мод и вот гадай, что за велосипед я тут изобрёл.
public class Sliding {
    private final PlayerSharp context;
    private final SmoothAcceleration smoothAcceleration;
    private int slidingTickCount = 0;
    private int slidingTickMax = 20;
    private boolean canSliding = false;
    private boolean fallDistanceChanged = false;

    public Sliding(PlayerSharp context) {
        this.context = context;
        this.smoothAcceleration = new SmoothAcceleration(1, 0.5, 0.2);
    }

    public void setCanSliding(boolean bool) {
        this.canSliding = bool;
    }

    public void update() {
        if (!canSliding && context.getPlayer().onGround() && !context.climb_manager.isClimbingRightNow()) canSliding = true;
        if (canSliding && context.freeBelow() && context.isFrontCollide()) {
            if (context.getSneaking() && (context.getPlayer().fallDistance < 10 || slidingTickCount != 0)) {
                if (slidingTickCount <= slidingTickMax) {
                    Vec3 deltaMovement = context.getPlayer().getDeltaMovement();
                    context.getPlayer().setDeltaMovement(
                            deltaMovement.x * 0,
                            deltaMovement.y * smoothAcceleration.update(),
                            deltaMovement.z * 0);
                    slidingTickCount++;
                    LOGGER.info("{}", smoothAcceleration.getCurrentSpeed());
                } else canSliding = false;
            } else if (slidingTickCount != 0) {
                smoothAcceleration.restore();
            }
        } else if (!fallDistanceChanged && slidingTickCount > 1) {
            context.getPlayer().fallDistance = context.getPlayer().fallDistance * (1 - (float) slidingTickCount / slidingTickMax);
            fallDistanceChanged = true;
        }
        if (context.getPlayer().onGround() && slidingTickCount != 0){
            slidingTickCount = 0;
            fallDistanceChanged = false;
            smoothAcceleration.restore();
        }

    }
}
