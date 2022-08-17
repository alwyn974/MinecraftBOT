package me.herrphoenix.obamabot.packet;

import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.tcp.TcpClientSession;

/**
 * @author HerrPhoenix
 */
public class Client {
    private final TcpClientSession session;

    public Client(TcpClientSession session) {
        this.session = session;
    }

    protected void sendPacket(Packet packet) {
        getSession().send(packet);
    }

    public TcpClientSession getSession() {
        return session;
    }
}
