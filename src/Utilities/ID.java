package Utilities;

public enum ID{
    // This keeps track of all the IDs of the objects in the game
    Player(),

    // abilities
    RouletteWheelAbility(),
    CasinoChipAbility(),
    CardThrowAbility(),
    DiceRollAbility(),

    // Enemies
    SpiderBoss(),
    FlyingEyeEnemy(),
    WormEnemy(),
    ArachnidMageEnemy(),
    EnemySpawnerPortal(),
    EnemyBullet(),

    // Structures (things you can walk through)
    Teleporter(),
    Explosion(),
    HealthPack(),
    DroppedItems(),

    // Objects (things you can't walk though)
    BlockObject(),
    ExplosiveBarrelObject(),
    ChestObject(),
    ComputerObject(),

    // Traps
    BuzzSawTrap(),

    // Other
    Trail(),
    Bullet(),

}
