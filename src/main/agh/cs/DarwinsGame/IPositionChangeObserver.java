package agh.cs.DarwinsGame;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition,Animal o);
}
