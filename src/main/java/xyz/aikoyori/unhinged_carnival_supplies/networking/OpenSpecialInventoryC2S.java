package xyz.aikoyori.unhinged_carnival_supplies.networking;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import xyz.aikoyori.unhinged_carnival_supplies.utils.UnhingedCarnivalUtils;

public class OpenSpecialInventoryC2S implements CustomPayload {

    public static final PacketCodec<PacketByteBuf, OpenSpecialInventoryC2S> CODEC = CustomPayload.codecOf(OpenSpecialInventoryC2S::write, OpenSpecialInventoryC2S::new);
    public static final Id<OpenSpecialInventoryC2S> ID = CustomPayload.id(UnhingedCarnivalUtils.MOD_ID+"-open_special_inventory");
    boolean open;
    private OpenSpecialInventoryC2S(PacketByteBuf buf) {
        this(buf.readBoolean());
    }
    public OpenSpecialInventoryC2S(boolean open) {
        this.open = open;
    }

    private void write(PacketByteBuf buf) {
        buf.writeBoolean(open);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
