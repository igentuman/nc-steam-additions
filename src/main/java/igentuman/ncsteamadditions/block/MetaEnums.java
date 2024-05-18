package igentuman.ncsteamadditions.block;

import nc.enumm.*;
import net.minecraft.util.IStringSerializable;

public class MetaEnums {
    public MetaEnums() {
    }

    public static enum DustType implements IStringSerializable, IMetaEnum {
        ZINC("zinc", 0),
        URANIUM_OXIDE("uranium_oxide", 1);

        private final String name;
        private final int id;

        private DustType(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.getName();
        }

        public int getID() {
            return this.id;
        }
    }
    public static enum IngotType implements IStringSerializable, IBlockMetaEnum {
        ZINC("zinc", 0, 0, "pickaxe", 4.0F, 30.0F, 0, 0, 0, false);
        private final String name;
        private final int id;
        private final int harvestLevel;
        private final String harvestTool;
        private final float hardness;
        private final float resistance;
        private final int lightValue;
        private final int fireSpreadSpeed;
        private final int flammability;
        private final boolean isFireSource;

        private IngotType(String name, int id, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue, int fireSpreadSpeed, int flammability, boolean isFireSource) {
            this.name = name;
            this.id = id;
            this.harvestLevel = harvestLevel;
            this.harvestTool = harvestTool;
            this.hardness = hardness;
            this.resistance = resistance;
            this.lightValue = lightValue;
            this.fireSpreadSpeed = fireSpreadSpeed;
            this.flammability = flammability;
            this.isFireSource = isFireSource;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.getName();
        }

        public int getID() {
            return this.id;
        }

        public int getHarvestLevel() {
            return this.harvestLevel;
        }

        public String getHarvestTool() {
            return this.harvestTool;
        }

        public float getHardness() {
            return this.hardness;
        }

        public float getResistance() {
            return this.resistance;
        }

        public int getLightValue() {
            return this.lightValue;
        }

        public int getFireSpreadSpeed() {
            return this.fireSpreadSpeed;
        }

        public int getFlammability() {
            return this.flammability;
        }

        public boolean isFireSource() {
            return this.isFireSource;
        }
    }

    public static enum OreType implements IStringSerializable, IBlockMetaEnum {
        ZINC("zinc", 0, 2, "pickaxe", 3.0F, 15.0F, 0);

        private final String name;
        private final int id;
        private final int harvestLevel;
        private final String harvestTool;
        private final float hardness;
        private final float resistance;
        private final int lightValue;

        private OreType(String name, int id, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue) {
            this.name = name;
            this.id = id;
            this.harvestLevel = harvestLevel;
            this.harvestTool = harvestTool;
            this.hardness = hardness;
            this.resistance = resistance;
            this.lightValue = lightValue;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.getName();
        }

        public int getID() {
            return this.id;
        }

        public int getHarvestLevel() {
            return this.harvestLevel;
        }

        public String getHarvestTool() {
            return this.harvestTool;
        }

        public float getHardness() {
            return this.hardness;
        }

        public float getResistance() {
            return this.resistance;
        }

        public int getLightValue() {
            return this.lightValue;
        }
    }
}
