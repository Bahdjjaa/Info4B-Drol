package Network.packets;

import Network.ClientJoueur;
import Network.ServeurCentral;

public abstract class Packet {
	public static enum PacketTypes{
		INVLIDE(-1), LOGIN(00), DISCONNECT(01);
		
		private int packetId;
		
		private PacketTypes(int packetId) {
			this.packetId = packetId;
		}
		
		public int getId() {
			return packetId;
		}
	}
	
	
	public byte packetId;
	
	public Packet(int packetId) {
		this.packetId = (byte) packetId;
	}
	
	public abstract void writeData(ClientJoueur joueur);
	public abstract void writeData(ServeurCentral serveur);
	
	public String lireData(byte[] data) {
		String message = new String(data).trim();
		return message.substring(2);
	}
	
	public static PacketTypes chercherPacket(int id) {
		for(PacketTypes p : PacketTypes.values()){
			if(p.getId() == id)
				return p;
		}
		return PacketTypes.INVLIDE;
	}

}