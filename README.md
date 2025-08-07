# Hero Statue  

## Technical  
### Networking  
### Rendering  
We render the block using a block entity renderer as that gives us more control on how we can render the block.  

The `_eyes` texture is rendered using a custom pipeline. You can find the corrosponding core shaders at `hero-statue:shaders/core/statue_eyes.vsh` and `hero-statue:shaders/core/statue_eyes.fsh`.  

When rendering with Iris Shaders, we copy the `ENTITY_EYES` pipeline.  
Iris Compatibility isn't at the top of our list, but we do want it to be at least functional for those who choose to use it.  

## [![Made for ModFest: Toybox](https://raw.githubusercontent.com/ModFest/art/refs/heads/v2/badge/svg/toybox/cozy.svg)](https://modfest.net/toybox)  
