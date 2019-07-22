package tmon.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;

/** Exposes constructor of PressurePlateBlock. */
public class ModPressurePlateBlock extends PressurePlateBlock {
	public ModPressurePlateBlock(PressurePlateBlock.ActivationRule type, Block.Settings settings) {
		super(type, settings);
	}

}
