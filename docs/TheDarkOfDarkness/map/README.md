# TheDarkOfDarkness Map

This directory contains text-based level maps for **TheDarkOfDarkness** using PlantUML.

## Levels

- `ground-level.puml` (implemented locations and links from code)
- `first-level.puml` (implemented locations and links from code)
- `tower-level.puml` (implemented locations and links from code)
- `dungeon-under-shed.puml` (implemented locations and links from code)

## Notes

- All locations described across the four maps are now wired into the game with plain, unconditional transitions (no item/key gating, no NPC encounters yet).
- The shed is reachable from the yard without needing the Dark Mysterious obscure Key; the dungeon stairway down is likewise free.
- The Toy Chest (Rubber duck, Beach ball, Wooden sword) is reachable from the Living Room (`toy chest` / `chest`), returning via `leave` / `exit`.
- The Apple Tree is reachable from the yard (`tree` / `elf` / `dark elf`) with no Dark Elf interaction wired yet; return via `leave` / `exit`.
- The dungeon under the shed is fully linked: Shed -> (`down`) Stairway -> (`descend`) Cells (Bones) -> (`pass`) Summoning Chamber (Tattered grimoire) -> (`restore`) Restored Incantation -> (`recite`) Victory/cliffhanger scene.
- The first floor is reachable from the Hallway (`up`) via the new Landing, connecting to Library/Bookshelf, Study (Sunlight Stream, Forge, Bullet Mold sub-rooms), Bathroom (Mirror shard), and Ballroom (Display gun, Silver cutlery).
- The Tower (Stairwell, Chamber, Balcony) is reachable from the Landing (`up`).
- NPCs (Dark Elf, Rabbit, Vegetable Spirit, Vampire, Troll, Werewolf) and any item/condition-gated transitions (keys, crafting, combat) are **not yet implemented** — this pass only wires up locations, plain transitions, and non-NPC-drop items.

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
- All death terminals are narrative endings with **"The End"** and immediate restart availability, each including a wistful dying hint at the missing item (wood for the Vampire, silver bullets for the Werewolf, sunlight/darkness for the Troll).
- First floor and tower are included as map scaffolding for expansion.

## Render

From the repository root (if `plantuml` is installed):

```bash
plantuml docs/TheDarkOfDarkness/map/*.puml
```
