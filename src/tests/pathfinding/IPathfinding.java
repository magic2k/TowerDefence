package tests.pathfinding;

import java.util.Deque;
import java.util.List;

/**
 * Date: 04.01.12
 * Time: 15:15
 *
 * maybe there will be few Pathfinding implementations for different creatures
 */
public interface IPathfinding {
    Deque<PathfindingTile> bestWay(int _current_x, int _current_y, int _target_x, int _target_y);
}
