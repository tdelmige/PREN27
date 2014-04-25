package Common;

public interface IResponse {
	
	public byte getAck();
	public byte[] getPayload();
	public byte getChecksum();

}
