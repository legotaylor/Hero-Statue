# Hero Statue
Adds a new block called the `Hero Statue`, which can hold an item, and be posed using redstone.  

> **Important**  
> If you have a slower computer, have a lot of statues placed down, or are using a resource intensive shader pack, you may want to change your statue render type config to `Basic`. See [Config](#config).  
> This renders statues using block models, which is a lot faster, however it does disable visual posing.  
>
> If you use Shaders, read [Known Issues: Shader Rendering Pipelines](#shader-rendering-pipelines), and check [Tested Shader Packs](#tested-shader-packs) below.  


## Features
### Hero Statue
- Holds an item
  - You can give the statue an item to display by interacting with it whilst holding the item.  
- Posable  
  - You can pose the statue using redstone.  
    - This is configurable via a gamerule, which is **ON** by default. (`hero-statue$allowRedstoneChangeStatuePose`)  
  - The statue remembers the last set pose when turned off.  
  - You can pose the statue by interacting with empty hands or when the statue already has an item, if the gamerule is active.  
    - This is configurable via a gamerule, which is **OFF** by default. (`hero-statue$allowPlayerChangeStatuePose`)  
- Comparators output the level of redstone required to set the current pose.  
- *rainbows ~~and unicorns~~*  
  - You can make the statues eyes cycle through the rainbow by giving the statue an item named `jeb_`, or by turning the `RAINBOW MODE` config on.  
  - You can also enable this in creative, by setting the `hero_statue_rainbow` blockstate with a debug stick or with commands.  
  - This only works with the `Default` render type, and if you are not using shaders.  
- *¿sᴉɥʇ pɐǝɹ noʎ uɐɔ*  
  - You can make the statue render upside down by giving the statue an item named `Dinnerbone`, `Grumm`, `legotaylor`, or `dannnytaylor`.  
  - This only works with the `Default` and `Vanilla` render types.  


## Config
You can edit the config by using [ModMenu](https://modrinth.com/mod/modmenu), or our `Open Config` keybinding (defaulted to `Home`).  
If you are using the `Open Config` keybinding, you don't need to be in a world, you just need to not be interacting with a text box.  


## Known Issues
### Shader Rendering Pipelines
#### [Iris Shaders](https://www.irisshaders.dev/)
Iris overrides core shader pipelines when rendering shader packs, and doesn't provide any documentation for how to add new overrides.  

As a workaround, we've copied the `ENTITY_CUTOUT` and `ENTITY_EYES` pipeline overrides. We picked these pipelines as they provide a similar effect to our custom pipelines, however it's not 1:1 and some features such as the `jeb_` easter egg won't be rendered when a shader pack is active. This functionality matches the `Vanilla` render type. See [Hero-Statue#1](https://github.com/legotaylor/Hero-Statue/issues/1).  

Rendering should work as intended when no shader packs are in use.  

> **Developer's Note**  
> You may encounter z-fighting with some shader packs.  
> You can disable our eyes layer using our config if desired. See [Config](#config).

##### Tested Shader Packs
| Shader Pack                                                                                                                                   |                                                                                                                                                                                                                                                      |
|-----------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Complementary [Unbound](https://modrinth.com/shader/complementary-unbound)/[Reimagined](https://modrinth.com/shader/complementary-reimagined) | No visual issues found.¹                                                                                                                                                                                                                             |
| [Euphoria Patches](https://modrinth.com/mod/euphoria-patches)                                                                                 | No visual issues found.¹                                                                                                                                                                                                                             |
| [BSL Shaders](https://modrinth.com/shader/bsl-shaders)                                                                                        | No visual issues found.¹                                                                                                                                                                                                                             |
| [Rudimentary](https://modrinth.com/shader/rudimentary-ps1)                                                                                    | Our eyes layer is offset when Vertex Snapping is enabled² and the current render type is set to default or vanilla. This does cause flickering when the player moves. Interestingly, this shader also renders the eyes layer over the player's hand. |
| [VECTOR](https://modrinth.com/shader/vector)                                                                                                  | No visual issues found.¹                                                                                                                                                                                                                             |
| [Super Duper Vanilla](https://modrinth.com/shader/super-duper-vanilla)                                                                        | No visual issues found.¹                                                                                                                                                                                                                             |

¹ Except for the above-mentioned render pipeline workaround.  
² You can disable Vertex Snapping in the shader pack settings, under Style.  

### Basic Render Type Poses
The basic render type doesn't render the poses, rainbow, and upside down easter eggs.  
I would love to update this in the future to add the poses to the basic render type, however this is easier said than done due to how block model elements rotate.  


## Technical
For technical details see [here](https://github.com/legotaylor/Hero-Statue/blob/master/DEVELOPER_README.md).  


## [![Made for ModFest: Toybox](https://raw.githubusercontent.com/ModFest/art/refs/heads/v2/badge/svg/toybox/cozy.svg)](https://modfest.net/toybox)
