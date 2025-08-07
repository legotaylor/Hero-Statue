# Hero Statue  
Adds a new block 'Hero Statue', which can hold one item, and can be posed using redstone.  

## Technical  

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
- **RequestPayload** (Identifier)
	- `ClientEvents.Network.request.register(Identifier.of("example", "bid_request"), (payload, context) -> {});`
	- `CommonEvents.Network.request.register(Identifier.of("example", "bid_request"), (payload, context) -> {});`

### Rendering
We render the block using a block entity renderer as that gives us more control on how we can render the block.

The `_eyes` texture is rendered using a custom pipeline. You can find the corrosponding core shaders at `hero-statue:shaders/core/statue_eyes.vsh` and `hero-statue:shaders/core/statue_eyes.fsh`.

When rendering with Iris Shaders, we copy the `ENTITY_EYES` pipeline.  
Iris Compatibility isn't a priority, but we do want it to be at least functional for those who choose to use it.

## [![Made for ModFest: Toybox](https://raw.githubusercontent.com/ModFest/art/refs/heads/v2/badge/svg/toybox/cozy.svg)](https://modfest.net/toybox)  
