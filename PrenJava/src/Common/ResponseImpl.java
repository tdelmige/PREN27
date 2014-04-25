package Common;

public class ResponseImpl implements IResponse {
	
	private byte ack;
	private byte[] payload;
	private byte checksum;
	
	public ResponseImpl(Byte ack, byte[] pay, Byte che){
		
		this.ack = ack;
		this.payload = pay;
		this.checksum = che;
		
	}
	
	@Override
	public byte getAck() {
		return ack;
	}

	@Override
	public byte[] getPayload() {
		return payload;
	}

	@Override
	public byte getChecksum() {
		return checksum;
	}
	
}