# Hero Statue
Adds a new block 'Hero Statue', which can hold one item, and can be posed using redstone.


## Known Issues  
### Iris Shaders Rendering Pipelines  
> **Developer's Note**  
> When rendering using [Iris Shaders](https://www.irisshaders.dev/), you may encounter z-fighting with some shader packs. You can disable our eyes layer using our config if desired (Use [ModMenu](https://modrinth.com/mod/modmenu)). You'll also notice that the `jeb_`/`RAINBOW MODE` easter egg don't render, this is due to our workaround ([Hero-Statue#1](https://github.com/legotaylor/Hero-Statue/issues/1)) that allows our model to render when using Iris. You may encounter rendering issues with other rendering mods. Rendering should work as intended when no shader packs are in use.  
#### Tested Shader Packs  
| Shader Pack                                                                                                                                   |                                                                                                                                                                       |
|-----------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Rudimentary](https://modrinth.com/shader/rudimentary-ps1)                                                                                    | Our eyes layer is offset when Vertex Snapping is enabled, which does cause z-fighting. Interestingly, this shader also renders the eyes layer over the player's hand. |
| Complementary [Unbound](https://modrinth.com/shader/complementary-unbound)/[Reimagined](https://modrinth.com/shader/complementary-reimagined) | No visual issues found.¹                                                                                                                                              |
| [Euphoria Patches](https://modrinth.com/mod/euphoria-patches)                                                                                 | No visual issues found.¹                                                                                                                                              |
| [VECTOR](https://modrinth.com/shader/vector)                                                                                                  | No visual issues found.¹                                                                                                                                              |

¹ Except for the above-mentioned render pipeline workaround.  


##  
For technical details see [here](https://github.com/legotaylor/Hero-Statue/blob/master/DEVELOPER_README.md).  


## [![Made for ModFest: Toybox](https://raw.githubusercontent.com/ModFest/art/refs/heads/v2/badge/svg/toybox/cozy.svg)](https://modfest.net/toybox)
