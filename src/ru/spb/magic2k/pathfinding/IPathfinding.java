package ru.spb.magic2k.pathfinding;

import ru.spb.magic2k.field.Tile;

import java.util.Deque;

/**
 * Date: 04.01.12
 * Time: 15:15
 *
 * maybe there will be few Pathfinding implementations for different creatures
 */
public interface IPathfinding {
    Deque<PathfindingTile> bestWay(int _current_x, int _current_y, int _target_x, int _target_y);
}
