package Common;

public class MessageImpl implements IMessage {

	private String message;
	private Boolean acknowledge;
	private Short checksum;
	private IResponse response;
	private Exception exception;
	private Short comAdr;
	
	public MessageImpl(String mes, Boolean ack, Short che, IResponse res, Exception ex, Short adr){
		this.message = mes;
		this.acknowledge = ack;
		this.checksum = che;
		this.response = res;
		this.exception = ex;
		this.comAdr = adr;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Boolean getAcknowledge() {
		return acknowledge;
	}
	
	@Override
	public Short getChecksum() {
	
		return checksum;
	}

	@Override
	public IResponse getResponse() {
		return response;
	}

	@Override
	public Exception getException() {
		return exception;
	}

	@Override
	public short getComAdr() {
		return comAdr;
	}
	
}


