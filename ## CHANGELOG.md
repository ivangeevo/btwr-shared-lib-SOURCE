## v0.37-SNAPSHOT(not-released, not published)
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