# Raiden

by 蔡辉宇 1600011742

An air fighting game similar to Raiden. The main function is under the App class.

操控说明：按↑↓←→键移动，躲避敌军的子弹，击毙敌机

程序中定义的接口：

Flyable

程序中定义的类：

- World
- BaseRaidenObject implements Flyable (abstract)
  - BaseAircraft (abstract)
    - BumpingAircraft (final)
    - BaseShootingAircraft (abstract)
      - BigShootingAircraft (final)
      - MiddleShootingAircraft (final)
      - SmallShootingAircraft (final)
      - PlayerAircraft (final)
     - BaseWeapon (abstract)
           - BigBullet (final)
           - SmallBullet (final)
           - StandardPlayerBullet (final)
      - Background

static, final, abstract等修饰符、函数继承及重载及setter/getter方法在各类中均有使用



## Dependencies
org.apache.commons.lang3

## TODO

- Add music and sound effects
- Complete docstrings
- Add crash planes, mines, boss, etc.
- Add a package (desirably, one with a GUI) to set the plot
- Rotate the planes