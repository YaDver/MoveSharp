package com.yadver.moveSharp.client.Movement;

import com.yadver.moveSharp.client.Utils.SmoothAcceleration;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class Sliding extends Climbing{
    private boolean isSliding;
    private SmoothAcceleration smoothSlide;

    public Sliding(ClientPlayerEntity player) {
        super(player);
    }

    public boolean isSliding() {
        return isSliding;
    }

    private void setSliding(boolean sliding) {
        isSliding = sliding;
    }

    public void setSmoothSlide(SmoothAcceleration accelerator) {
        this.smoothSlide = accelerator;
    }

    protected void Slide() {
        Vec3d vel = getPlayer().getVelocity();
        if (isSliding) getPlayer().setVelocity(vel.x/2, smoothSlide.update() , vel.z/2);
    }
}
