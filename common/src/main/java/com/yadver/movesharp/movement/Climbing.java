package com.yadver.movesharp.movement;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

//  TODO: Добавить комментарии. А то хули, опять забью на мод и вот гадай, что за велосипед я тут изобрёл.
public class Climbing {
    private final PlayerSharp context;
    private boolean climbingRightNow;
    private boolean canClimbing = false;
    private boolean waitTickForCrawl = false;
    private AABB target = null;
    private List<AABB> ledges = null;
    public boolean needCrouching = false;
    public Climbing(PlayerSharp context) {
        this.context = context;
    }

    public boolean isClimbingRightNow() {return climbingRightNow;}

    public void update() {
        if (!canClimbing && context.getPlayer().onGround()) canClimbing = true;
        if (climbingRightNow && !context.isFrontCollide()) climbEnd();
        if (canClimbing && context.freeBelow() && context.isFrontCollide()) {
            if (context.getSprinting()) {
                if (climbingRightNow && target != null) {
                    climbUpdate();
                } else {
                    context.restoreVoxelWall();
                    context.voxelWall.scanWall();
                    context.voxelWall.searchLedge();
                    climbUpdate();
                }
            } else restart();
        }
    }

    private void climbUpdate() {
        context.slide_manager.setCanSliding(false);
        if (context.voxelWall.checkForUpdate(context.getLevel())) {
            context.voxelWall.scanWall();
            context.voxelWall.searchLedge();
        }
        ledges = context.voxelWall.voxelLedge;
        if (!ledges.isEmpty()) {
            climbingRightNow = true;
            if (context.isAboveCollide()) {
                context.crawl_manager.forceCrawling(true);
            }
            if (context.getSneaking()) {
                for (AABB l : ledges) {
                    if (l.minY > (context.getPlayer().position().y - 1)) {
                        target = l;
                        break;
                    }
                }
            } else target = ledges.get(ledges.size() - 1);
            if (target.minY + 0.15 >= context.getPlayer().position().y) {
                context.getPlayer().setDeltaMovement(0, 0.15, 0);
            } else {
                if (!context.getLevel().noCollision(target.setMaxY(target.minY + 1.9))) {
                    context.crawl_manager.forceCrawling(true);
                    waitTickForCrawl = true;
                }
                climbEnd();
            }
        } else restart();
    }

    private void climbEnd() {
        Vec3 p_look = context.getNormalPlayerRotationVector();
        double endStepForce = 0.05;
        context.getPlayer().setDeltaMovement(endStepForce * p_look.x, 0, endStepForce * p_look.z);

        if (waitTickForCrawl) {
            waitTickForCrawl = false;
        } else restart();
    }

    private void restart() {
        context.crawl_manager.forceCrawling(false);
        canClimbing = false;
        climbingRightNow = false;
        needCrouching = false;
    }
}
