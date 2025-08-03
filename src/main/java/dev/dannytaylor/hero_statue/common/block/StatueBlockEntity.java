package dev.dannytaylor.hero_statue.common.block;

import dev.dannytaylor.hero_statue.common.data.HeroStatueData;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class StatueBlockEntity extends BlockEntity {
	// TODO: Add a ItemStack inventory, to replace material.

	private Identifier material = Identifier.of(HeroStatueData.id, "none");

	public StatueBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.heroStatue, pos, state);
	}

	public void setMaterial(Identifier material) {
		this.material = material;
		markDirty();
	}

	public void clearMaterial() {
		setMaterial(Identifier.of(HeroStatueData.id, "none"));
	}

	public Identifier getMaterial() {
		return this.material;
	}

	public boolean hasMaterial() {
		return !this.material.equals(Identifier.of(HeroStatueData.id, "none"));
	}

	@Override
	public void writeDataWithoutId(WriteView data) {
		data.putString("material", material.toString());
		super.writeDataWithoutId(data);
	}

	@Override
	protected void readData(ReadView view) {
		Optional<String> oMaterialData = view.getOptionalString("material");
		oMaterialData.ifPresent(materialData -> material = Identifier.of(materialData));
		super.readData(view);
	}
}
