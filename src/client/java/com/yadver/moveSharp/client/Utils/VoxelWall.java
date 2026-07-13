package com.yadver.moveSharp.client.Utils;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VoxelWall {
    public final List<Map.Entry<Box, BlockPos>> voxelWall;
    public final List<Box> voxelLedge;
    public final List<BlockPos> DebugWallPos;
    private World world;

    private Vec3d p_pos;
    private Vec3d p_look;

    private final Box air = new Box(0, 0, 0, 0, 0, 0);

    public VoxelWall() {
        voxelWall = new ArrayList<>();
        voxelLedge = new ArrayList<>();
        DebugWallPos = new ArrayList<>();
    }

    public void restore(World world, Vec3d p_pos, Vec3d p_look) {
        this.world = world;
        this.p_pos = p_pos;
        this.p_look = p_look;
        voxelWall.clear();
        voxelLedge.clear();
        DebugWallPos.clear();
    }

    public void scanWall() {
        //  Позиция блока в упоре перед игроком на уровне ног
        BlockPos blockPos = BlockPos.ofFloored(p_pos.add(0.31 * p_look.x, 0, 0.31 * p_look.z));

        //  Расстояние между центром блока и игроком до его коллизи
        double pPosRelativeBlock = -(Math.abs(p_look.x) > Math.abs(p_look.z) ?
                blockPos.getX() - p_pos.x - 0.31 * p_look.x :
                blockPos.getZ() - p_pos.z - 0.31 * p_look.z);

        voxelWall.clear();

        //  Сканирование 4-х блоков от уровня ног игрока и выше
        for (int b = -1; b < 4; b++) {
            BlockState blockState = world.getBlockState(blockPos.add(0, b, 0));
            VoxelShape collisionShape = blockState.getCollisionShape(world, blockPos.add(0, b, 0));

            if (!collisionShape.isEmpty() && (b != -1 || collisionShape.getMax(Direction.Axis.Y) > 1)) {
                List<Box> boxes = collisionShape.getBoundingBoxes();
                for (Box box : boxes) {
                    if (p_look.x != 0) {
                        if (p_look.x > 0) {
                            if ((box.getMin(Direction.Axis.X) - pPosRelativeBlock) < 0) {
                                voxelWall.add(new AbstractMap.SimpleEntry<>(box, blockPos.add(0, b, 0)));
                            }
                        } else {
                            if ((pPosRelativeBlock - box.getMax(Direction.Axis.X)) < 0) {
                                voxelWall.add(new AbstractMap.SimpleEntry<>(box, blockPos.add(0, b, 0)));
                            }
                        }
                    } else if (p_look.z != 0) {
                        if (p_look.z > 0) {
                            if ((box.getMin(Direction.Axis.Z) - pPosRelativeBlock) < 0) {
                                voxelWall.add(new AbstractMap.SimpleEntry<>(box, blockPos.add(0, b, 0)));
                            }
                        } else {
                            if ((pPosRelativeBlock - box.getMax(Direction.Axis.Z)) < 0) {
                                voxelWall.add(new AbstractMap.SimpleEntry<>(box, blockPos.add(0, b, 0)));
                            }
                        }
                    }
                }
            }
        }
    }

    public void searchLedge() {
        Vec3d ledge = null;

        for (Map.Entry<Box, BlockPos> boxBlockPosEntry : voxelWall) {
            Box box = boxBlockPosEntry.getKey();
            BlockPos blockPos = boxBlockPosEntry.getValue();
            if (p_look.x != 0) {
                if (p_look.x > 0) {
                    ledge = new Vec3d(
                            blockPos.getX() + box.getMin(Direction.Axis.X),
                            blockPos.getY() + box.getMax(Direction.Axis.Y),
                            p_pos.z
                    );
                } else {
                    ledge = new Vec3d(
                            blockPos.getX() + box.getMax(Direction.Axis.X),
                            blockPos.getY() + box.getMax(Direction.Axis.Y),
                            p_pos.z
                    );
                }
            } else if (p_look.z != 0) {
                if (p_look.z > 0) {
                    ledge = new Vec3d(
                            p_pos.x,
                            blockPos.getY() + box.getMax(Direction.Axis.Y),
                            blockPos.getZ() + box.getMin(Direction.Axis.Z)
                    );
                } else {
                    ledge = new Vec3d(
                            p_pos.x,
                            blockPos.getY() + box.getMax(Direction.Axis.Y),
                            blockPos.getZ() + box.getMax(Direction.Axis.Z)
                    );
                }
            }
            if (ledge != null) {
                Box leadgeBox = new Box(
                         ledge.x - 0.3,
                        ledge.y - 0,
                        ledge.z - 0.3,
                        ledge.x + 0.3,
                        ledge.y + 0.6,
                        ledge.z + 0.3
                );
                if (world.isSpaceEmpty(leadgeBox)
                        && !world.isSpaceEmpty(leadgeBox.offset(0, -0.01, 0))) {
                    voxelLedge.add(leadgeBox);
                }
            }
        }
    }

}
