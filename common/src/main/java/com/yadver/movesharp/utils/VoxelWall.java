package com.yadver.movesharp.utils;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;


public class VoxelWall {
    public record WallElement(AABB box, BlockPos pos) {}
    public final List<WallElement> voxelWall;
    public final List<AABB> voxelLedge;
    private Level level;
    //  Смещение для поиска voxelLedge. Чем больше значение, тем дальше от края. При >=0.3 бессмысленно.
    private final double offset = 0.2;

    private Vec3 p_pos;
    private Vec3 p_look;

    public VoxelWall() {
        this.voxelWall = new ArrayList<>();
        this.voxelLedge = new ArrayList<>();
    }
    //  Перезапись неактуальных данных и сброс voxelWall и voxelLedge
    public void restore(Level level, Vec3 p_pos, Vec3 p_look) {
        this.level = level;
        this.p_pos = p_pos;
        this.p_look = p_look;
        this.voxelWall.clear();
        this.voxelLedge.clear();
    }
    //  Сканирование стены и запись её хитбоксов в voxelWall.
    //  TODO: Добавить сканирование соседнего столба блоков, если игрок стоит на краю сканируемого столба.
    //      Так как мод не знает о соседнем блоке, и что на него можно забраться, просто не позваляет вскарабкаться.
    public void scanWall() {
        //  Позиция блока в упоре перед игроком на уровне ног
        BlockPos blockPos = BlockPos.containing(p_pos.add(0.31 * p_look.x, 0, 0.31 * p_look.z));

        this.voxelWall.clear();
        //  Расстояние между центром блока и игроком до его коллизи
        double pPosRelativeBlock = -(Math.abs(p_look.x) > Math.abs(p_look.z) ?
                blockPos.getX() - p_pos.x - 0.31 * p_look.x :
                blockPos.getZ() - p_pos.z - 0.31 * p_look.z);

        //  Сканирование 4-х блоков от уровня ног игрока и выше
        for (int b = -4; b < 3; b++) {
            BlockState blockState = level.getBlockState(blockPos.offset(0, b, 0));
            VoxelShape collisionShape = blockState.getCollisionShape(level, blockPos.offset(0, b, 0));

            if (!collisionShape.isEmpty()) {
//            if (!collisionShape.isEmpty() && (b != -1 || collisionShape.max(Direction.Axis.Y) > 1)) {
                List<AABB> boxes = collisionShape.toAabbs();
                for (AABB box : boxes) {
                    //  Поиск AABB в зависимости от направления взгляда игрока.
                    //  Параметр 0.5 в блоках if отвечает за расстояние сканирования.
                    //  Слишком маленькое значение может помешать найти нужный AABB.
                    //  Слишком большое, при очень детализитрованной модели с большим количеством AABB, может создать
                    //  множество мест для игрока в одном небольшом радиусе (но проблем не наблюдалось)
                    if (p_look.x != 0) {
                        if (p_look.x > 0) {
                            if ((box.min(Direction.Axis.X) - pPosRelativeBlock) < 0.5) {
                                voxelWall.add(new WallElement(box, blockPos.offset(0, b, 0)));
                            }
                        } else {
                            if ((pPosRelativeBlock - box.max(Direction.Axis.X)) < 0.5) {
                                voxelWall.add(new WallElement(box, blockPos.offset(0, b, 0)));
                            }
                        }
                    } else if (p_look.z != 0) {
                        if (p_look.z > 0) {
                            if ((box.min(Direction.Axis.Z) - pPosRelativeBlock) < 0.5) {
                                voxelWall.add(new WallElement(box, blockPos.offset(0, b, 0)));
                            }
                        } else {
                            if ((pPosRelativeBlock - box.max(Direction.Axis.Z)) < 0.5) {
                                voxelWall.add(new WallElement(box, blockPos.offset(0, b, 0)));
                            }
                        }
                    }
                }
            }
        }
    }
    // Поиск мест, в которые игрок может поместиться в лежачем положении
    public void searchLedge() {
        Vec3 ledge = null;
        this.voxelLedge.clear();

        for (WallElement boxBlockPosEntry : voxelWall) {
            AABB box = boxBlockPosEntry.box;
            BlockPos blockPos = boxBlockPosEntry.pos;
            //  В зависимости от направления взгляда игрока над каждим AABB создаёт временную зону под игрока.
            if (p_look.x != 0) {
                if (p_look.x > 0) {
                    ledge = new Vec3(
                            blockPos.getX() + box.min(Direction.Axis.X) - offset,
                            blockPos.getY() + box.max(Direction.Axis.Y),
                            p_pos.z
                    );
                } else {
                    ledge = new Vec3(
                            blockPos.getX() + box.max(Direction.Axis.X) + offset,
                            blockPos.getY() + box.max(Direction.Axis.Y),
                            p_pos.z
                    );
                }
            } else if (p_look.z != 0) {
                if (p_look.z > 0) {
                    ledge = new Vec3(
                            p_pos.x,
                            blockPos.getY() + box.max(Direction.Axis.Y),
                            blockPos.getZ() + box.min(Direction.Axis.Z) - offset
                    );
                } else {
                    ledge = new Vec3(
                            p_pos.x,
                            blockPos.getY() + box.max(Direction.Axis.Y),
                            blockPos.getZ() + box.max(Direction.Axis.Z) + offset
                    );
                }
            }
            //  Проверяет временную зону на отсутствие коллизий. Грубо говоря, сможет ли игрок там поместиться лёжа.
            if (ledge != null) {
                AABB leadgeBox = new AABB(
                        ledge.x - 0.3,
                        ledge.y - 0,
                        ledge.z - 0.3,
                        ledge.x + 0.3,
                        ledge.y + 0.6,
                        ledge.z + 0.3
                );
                if (level.noCollision(leadgeBox)
                        && !level.noCollision(leadgeBox.move(0, -0.01, 0))) {
                    voxelLedge.add(leadgeBox);
                }
            }
        }
    }
    // Если voxelLedge становиться не актуальным, то выводит true для запуска повторного сканирования
    public boolean checkForUpdate(Level level) {
        this.level = level;

        for (AABB ledge : voxelLedge) {
            if (!level.noCollision(ledge) || level.noCollision(ledge.move(0,-0.25, 0))) {
                return true;
            }
        }
        return false;
    }
}
