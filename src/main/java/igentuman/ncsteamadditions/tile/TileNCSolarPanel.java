package igentuman.ncsteamadditions.tile;

import nc.tile.generator.TileSolarPanel;

import static igentuman.ncsteamadditions.config.NCSteamAdditionsConfig.solar_power;

public class TileNCSolarPanel {

    public static class Quantum extends TileSolarPanel {
        public Quantum() {
            super(solar_power[0]);
        }
    }

    public static class Hyper extends TileSolarPanel {
        public Hyper() {
            super(solar_power[1]);
        }
    }


    public static class Fusion extends TileSolarPanel {
        public Fusion() {
            super(solar_power[2]);
        }
    }


    public static class Nova extends TileSolarPanel {
        public Nova() {
            super(solar_power[3]);
        }
    }


    public static class Stellar extends TileSolarPanel {
        public Stellar() {
            super(solar_power[4]);
        }
    }


    public static class Photon extends TileSolarPanel {
        public Photon() {
            super(solar_power[5]);
        }
    }


    public static class Nebula extends TileSolarPanel {
        public Nebula() {
            super(solar_power[6]);
        }
    }

    public static class Graviton extends TileSolarPanel {
        public Graviton() {
            super(solar_power[7]);
        }
    }
}
