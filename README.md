# BattlefieldCommanderGame
## Abstract
Currently, Battlefield Commander is a work-in-progress template for a real time strategy game that involves the use of real world physics to simulate combat between military units. The project has also been a means of testing new ideas that I've had in recent times, such as with design patterns and Machine Learning.

## Artificial Intelligence
The game includes a basic program that uses basic memory storage in conjunction with a simple reinforcement system to encourage it to make optimal choices in the context of the problem at hand.

## Engine
Like with a few of my previous projects, the program uses a WorldManager class that simulates a World (called LevelManager and Level, respectively). Each Level contains Entities that are involved with the function of the program. Each Unit acts based on the will of a UnitBrain object, which contains several behaviors that each utilize Design Patterns to enable better design in the future.
