package com.yadver.movesharp.movement;

import net.minecraft.world.entity.Pose;

//  TODO: Добавить комментарии. А то хули, опять забью на мод и вот гадай, что за велосипед я тут изобрёл.
public class Crawling {
    private final PlayerSharp context;
    private boolean force = false;

    public Crawling(PlayerSharp context) {
        this.context = context;
    }

    public boolean canCrawling() {
        return force ||
                (context.getSprinting() && (context.getSneaking() ||
                        context.getPlayer().getPose() == Pose.SWIMMING) &&
                (context.getPlayer().onGround() ||
                        context.getPlayer().getPose() == Pose.SWIMMING) &&
                !context.climb_manager.isClimbingRightNow());
    }

    public void forceCrawling(boolean bool) {
        this.force = bool;
    }

    public boolean getForceCrawling() {
        return this.force;
    }
}
