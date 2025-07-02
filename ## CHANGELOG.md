## v0.56(dev)
+ Added a hook methods for the HungerPlayerManager to add multiplier

## v0.55
+ Added a new sound in the CraftingSoundConfig "FIZZ_SOUND", which is essentially the "Fire Extinguish" sound but tweaked with pitch and volume
+ Removed the item tag for items that can only stack up to 16 because it wasn't applying properly.
+ Updated the mod to Fabric API 0.116.1

## v0.54
+ Added a new item tag for items that can only stack up to 16
+ Updated the mod to Fabric API 0.116.0 & Fabric Loader 0.16.14

## v0.53
+ Cleanup old hoe functionality changes in favor of moving them to BTWR: Core version 0.31
+ Updated the mod to Fabric API 0.115.6 & Fabric Loader 0.16.13

## v0.52
+ Added a new common block tag "FARMLAND_VIABLE_DIRT" that defines which (dirt) blocks can be converted to farmland when broken with BTWR:Core's alternative hoe usage mechanic.
+ Added a new common block tag "FARMLAND_VIABLE_GRASS" that defines which (grass) blocks can be converted to dirt on hoe left click break (same as above functionality)

## v0.51
+ Add config options and added compatibility for PenaltyDisplayManager (jeffyjamzhd)
+ Fix game breaking error with CraftingResultSlotMixin (jeffyjamzhd)

## v0.50


### Penalty Display System (jeffyjamzhd)



+ Added a unified penalty registration system to standardize rendering across mods.
+ Introduced HUDInitializeListener for registering penalties via EventHUDInitialized.register(listener).
+ Added addPenalty(penalty) and removePenalty(penalty) methods for managing penalties dynamically.
+ Implemented priority-based rendering with condition lambdas for flexibility.

## v0.49
+ Better world generation replacement logic that preserves blockstate properties of original block and apply them to the new one if possible
+ Updated the mod to Fabric API 0.115.3

## v0.48
+ Added a utility class 'BlockReplacementRegistry' for easy block replacement during(before) world gen applies the original block. This allows to set custom blocks during world generation.
+ Fixed a bug that made blocks drop directionally for mods that didn't support these blocks. (Will only work with Tough Environment and Sturdy Trees for now)

## v0.47
+ Added a missin mixin code for making shears actually break blocks efficiently

## v0.46
+ Added a SHEARS_EFFICIENT block tag for adding blocks that shear items can efficiently break.

## v0.45
+ Fixed cobblestone missing from the COBBLESTONE_CRAFTING_MATERIALS tag
+ Updated to Fabric API 0.115.0 & Fabric Loader 0.16.10

## v0.44
+ Added a Block method in the mod API to check if a block is fertilized for growing plants ("getIsFertilizedForPlantGrowth")

## v0.43
+ Added a Block method in the mod API to check if another block is attached to the facing of a block ("isBlockAttachedToFacing").

## v0.42
+ Added a common block tag "WOODEN_MISC_BLOCKS" for modded blocks that are considered "wooden"
+ Fixed a bug where the game would crash when (non-player) entities tried to destroy blocks and drop stacks

## v0.41
+ Added Shears to the IRON_TOOLS item tag, which in turn makes it make the proper metallic sound when crafted.
+ Improved the crafting sound code even more and made sure it works in shift-click item retrieval as well.
+ Moved the crafting sounds of tools/items to be added by BTWR: Core.

## v0.40
+ Improved the code for the crafting sounds for items & made that a separate system.
+ Tried fixing mod version check failing problem on older versions by removing "SNAPSHOT" in the string that checks the version in the code.
+ Updated to Fabric API 0.114.0

## v0.39-SNAPSHOT
+ Changed the btwr-ds mod id to be btwr_ds in some places that had the old one.

## v0.38-SNAPSHOT
+ Added missing block tag entry for directional dropping blocks for the oak log block

## v0.37-SNAPSHOT
+ Conventional item tags changes (same but fixes)
+ Fixed item tag translations to display properly

## v0.36-SNAPSHOT
+ Conventional item tags changes

## v0.35-SNAPSHOT
- Added sounds to play on craft for tools (wooden, stone, metalic types)
- Added new item tags for adding sounds to modded tools like mentioned above
- Improved the crafting sounds for other misc items and added sound to play for items that get crafted with shears

## v0.34-SNAPSHOT
- Added shared conventional tag for meats viable for sandwich food.
- Added conventional block tag name translations
- Changed the sounds for crafting for Slime-like items to be louder

## v0.33-SNAPSHOT
- Improved conditions for placement for PlaceableAsBlock items. They are now not placeable on leaves

## v0.32-SNAPSHOT
- Added missing mixins for BlockAdded related functionality & Made hoe breaking logic work properly without needing BTWR:Core

## v0.31-SNAPSHOT
- Added ExtendedShapelessRecipe which allows adding new items to existing shapeless recipes to drop as side drops

## v0.29-SNAPSHOT
- Added translations for all BTWR conventional tags

## v0.27-SNAPSHOT
- Removed changes from previous version

## v0.26-SNAPSHOT
- Added Extended Shapeless Recipe with .additionalDrops method

## v0.22-SNAPSHOT
- Added WEB_BLOCKS block tag for cobweb and web block from animageddon

## v0.18-SNAPSHOT
- Testing improved directional drop logic injection

## v0.17-SNAPSHOT
- Added Item tag for AXES_CAN_BREAK_PLANKS
- Added datagen & generated all tags that are exclusively adding vanilla things in this mod, and the rest that are BTWR
exclusive items/block were left in the BTWR: core mod

## v0.14-SNAPSHOT
- Improved code for RecipeProviderUtils class

## v0.13-SNAPSHOT
- Added more methods to RecipeProviderUtils class

## v0.12-SNAPSHOT
-Reworked the directional dropping injection code to be less intrusive


## v0.11-SNAPSHOT
- Added RecipeProviderUtils class
- Refactored the StackDroppingManager class to better inject its code without breaking the original implementation

## v0.10-SNAPSHOT
- Added Disabled Recipe type

## v0.4-SNAPSHOT
- Added PlaceableAsBlock logic.

## v0.2-SNAPSHOT
- Moved BTWRConventionalTags to this library mod

## v0.1-SNAPSHOT
Initial release
- Added BlockAdded interface
- Added PlayerEntityAdded interface