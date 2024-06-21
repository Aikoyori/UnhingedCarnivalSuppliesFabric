package xyz.aikoyori.unhinged_carnival_supplies.utils;

import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;

public class UnhingedCarnivalUtils {
    public enum SPECIAL_ITEM_LOCATION implements StringIdentifiable {
            MOUTH("mouth"),
            STOMACH("stomach"),
            HAIR("hair"),
            EYES("eye");
        private final String id;
        private SPECIAL_ITEM_LOCATION(final String id) {
            this.id = id;
        }

        @Override
        public String asString() {
            return this.id;
        }
    }
    public static final StringIdentifiable.EnumCodec<SPECIAL_ITEM_LOCATION> SPECIAL_ITEMS_CODEC = StringIdentifiable.createCodec(SPECIAL_ITEM_LOCATION::values);
    public static String MOD_ID = "unhingedcarnivalsupplies";
    public static Identifier makeID(String man){
        return Identifier.of(MOD_ID,man);
    }
}
