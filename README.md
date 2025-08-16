# Hero Statue
Adds a new block 'Hero Statue', which can hold one item, and can be posed using redstone.


## Known Issues  
### Shader Rendering Pipelines  
#### [Iris Shaders](https://www.irisshaders.dev/)  
Iris overrides core shader pipelines when rendering shader packs, and doesn't provide any documentation for how to add new overrides.  

As a workaround, we've copied the `ENTITY_CUTOUT` and `ENTITY_EYES` pipeline overrides. We picked these pipelines as they provide a similar effect to our custom pipelines, however it's not 1:1 and some features such as the `jeb_` easter egg won't be rendered when a shader pack is active. See [Hero-Statue#1](https://github.com/legotaylor/Hero-Statue/issues/1).  

Rendering should work as intended when no shader packs are in use.  

> **Developer's Note**  
> You may encounter z-fighting with some shader packs.  
> You can disable our eyes layer using our config if desired (Use [ModMenu](https://modrinth.com/mod/modmenu)).  

##### Tested Shader Packs  
| Shader Pack                                                                                                                                   |                                                                                                                                                                                              |
|-----------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Complementary [Unbound](https://modrinth.com/shader/complementary-unbound)/[Reimagined](https://modrinth.com/shader/complementary-reimagined) | No visual issues found.¹                                                                                                                                                                     |
| [Euphoria Patches](https://modrinth.com/mod/euphoria-patches)                                                                                 | No visual issues found.¹                                                                                                                                                                     |
| [BSL Shaders](https://modrinth.com/shader/bsl-shaders)                                                                                        | No visual issues found.¹                                                                                                                                                                     |
| [Rudimentary](https://modrinth.com/shader/rudimentary-ps1)                                                                                    | Our eyes layer is offset when Vertex Snapping is enabled², which does cause flickering when the player moves. Interestingly, this shader also renders the eyes layer over the player's hand. |
| [VECTOR](https://modrinth.com/shader/vector)                                                                                                  | No visual issues found.¹                                                                                                                                                                     |

¹ Except for the above-mentioned render pipeline workaround.  
² You can disable Vertex Snapping in the shader pack settings, under Style.  

##  
For technical details see [here](https://github.com/legotaylor/Hero-Statue/blob/master/DEVELOPER_README.md).  


## [![Made for ModFest: Toybox](https://raw.githubusercontent.com/ModFest/art/refs/heads/v2/badge/svg/toybox/cozy.svg)](https://modfest.net/toybox)
