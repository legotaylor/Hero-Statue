## Hero Statue: Technical Details

### Networking
#### Packets
You can register code to execute on the client/server when they receive these packets by registering an event.
##### Client to Server
- **C2SUpdateStatuePayload** (BlockPos, boolean)
	- `CommonEvents.Network.updateStatue.register(Identifier.of("example", "c2s_update_statue"), (payload, context) -> {});`
- **C2SUpdateChunkStatuesPayload** (ChunkPos, boolean)
	- `CommonEvents.Network.updateChunkStatues.register(Identifier.of("example", "c2s_update_chunk_statues"), (payload, context) -> {});`
##### Server to Client
- **S2CUpdateStatuePayload** (StatueData(BlockPos, ItemStack))
	- `ClientEvents.Network.updateStatue.register(Identifier.of("example", "s2c_update_statue"), (payload, context) -> {});`
- **S2CUpdateChunkStatuesPayload** (List<StatueData(BlockPos, ItemStack)>)
	- `ClientEvents.Network.updateChunkStatues.register(Identifier.of("example", "s2c_update_chunk_statues"), (payload, context) -> {});`
##### Bi-directional
- **IdBooleanPayload** (Identifier, boolean)
	- `ClientEvents.Network.idBoolean.register(Identifier.of("example", "bid_id_boolean"), (payload, context) -> {});`
	- `CommonEvents.Network.idBoolean.register(Identifier.of("example", "bid_id_boolean"), (payload, context) -> {});`
- **IdStatueRenderTypePayload** (Identifier, StatueRenderType)
	- `ClientEvents.Network.idStatueRenderType.register(Identifier.of("example", "bid_id_statue_render_type"), (payload, context) -> {});`
	- `CommonEvents.Network.idStatueRenderType.register(Identifier.of("example", "bid_id_statue_render_type"), (payload, context) -> {});`
- **RequestPayload** (Identifier)
	- `ClientEvents.Network.request.register(Identifier.of("example", "bid_request"), (payload, context) -> {});`
	- `CommonEvents.Network.request.register(Identifier.of("example", "bid_request"), (payload, context) -> {});`

### Rendering
We have three different render types.  
- Default  
  - Our recommendation and the default value!  
  - Renders using the block entity renderer with custom core shaders.  
  - You can find these core shaders at `hero-statue:shaders/core/statue_eyes.vsh` and `hero-statue:shaders/core/statue_eyes.fsh`.  
- Vanilla  
  - Renders using the block entity renderer with vanilla core shaders, this may help with fps slightly, but if you're really lagging it's not going help a ton.  
- Basic  
  - Renders using block models.  
  - Doesn't show poses, and easter eggs, but you'll get all the fps.  

No matter the render type, the shown item is rendered using the block entity renderer.

When rendering with Iris Shaders, we copy the `ENTITY_CUTOUT` and `ENTITY_EYES` pipelines.  
Iris Compatibility isn't a priority, but we do want it to be at least functional for those who choose to use it.  

## [![Made for ModFest: Toybox](https://raw.githubusercontent.com/ModFest/art/refs/heads/v2/badge/svg/toybox/cozy.svg)](https://modfest.net/toybox)  
