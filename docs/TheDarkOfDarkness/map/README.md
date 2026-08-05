# TheDarkOfDarkness Map

This directory contains text-based level maps for **TheDarkOfDarkness** using PlantUML.

## Levels

- `ground-level.puml` (implemented locations and links from code)
- `first-level.puml` (planned)
- `tower-level.puml` (planned)
- `dungeon-under-shed.puml` (planned)

## Notes

- The current code implements ground-level locations: outside entrance, hallway, kitchen, living room, and yard.
- The map now includes a planned **Toy Chest** reachable from the living room (`toy chest` / `chest`) and return via (`leave` / `exit`).
- The map now includes a planned **Apple Tree** area from the yard (`tree` / `elf` / `dark elf`) with return via (`leave` / `exit`), where the player can talk to the Dark Elf.
- The map now includes planned **Shed Entrance** transitions: from yard via (`shed`) and back via (`leave` / `exit`).
- Transition from Shed Entrance down to the dungeon is planned as key-gated and requires **Dark Mysterious obscure Key**.
- Shed flow now includes an **ominous dark stairway** into the dungeon.
- Dungeon progression now includes:
  - passing cells filled with the wizard victims' fractured, meat-picked bones
  - reaching a summoning chamber with the mauled wizard corpse and tattered grimoire
  - restoring the incantation using the **Dark Cursed wand** + kitchen paper scrap
  - reciting the incantation for chapter victory and a cliffhanger transition to a demonic castle with stone gargoyles
- Current story plan: Dark Elf guides the player, fears the Troll on first floor, and repairs the collected wand parts into the **Dark Cursed wand**.
- Rabbit food is on the yard ground; feeding the kitchen rabbit reveals the Vegetable Spirit, who has a missing spellbook scrap.
- First-floor plan now includes:
  - **Bathroom Vampire** (defeated with Wooden sword, drops wand handle)
  - **Mirror-shard sunlight puzzle** (Study -> Landing -> Ballroom)
  - **Ballroom Troll** (turned to stone via sunlight) plus display gun and silver cutlery loot
  - **Troll drop**: wand tip (second wand half)
  - **Library Bookshelf** with _Bullet Making for Dummies_ and additional comic titles
  - **Study crafting chain**: silver cutlery -> forge (melted silver) -> bullet mold (requires melted silver + bookshelf book)
  - **Global combine action**: gun + silver bullets => Loaded Gun (usable anywhere)
- Tower plan now includes a **Werewolf** that can only be defeated with the Loaded Gun and drops the **Dark Mysterious obscure Key**.
- Entering the **Tower chamber** without a Loaded Gun is a planned death condition; `leave` / `exit` returns to tower stairwell.
- Entering the **Ballroom** without mirror-shard prerequisites (final shard + shard #1 and #2 placements) is a planned death condition.
- Facing the **Bathroom Vampire** without the Wooden sword is a planned death condition.
- All death terminals are narrative endings with **"The End"** and immediate restart availability.
- First level and tower are included as map scaffolding for expansion.

## Render

From the repository root (if `plantuml` is installed):

```bash
plantuml docs/TheDarkOfDarkness/map/*.puml
```
