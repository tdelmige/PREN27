package Common;

public class MessageImpl implements IMessage {

	private String message;
	private Boolean acknowledge;
	private Short checksum;
	private IResponse response;
	private Exception exception;
	private Short comAdr;
	
	public MessageImpl(String mes, IResponse res, Exception ex, Short adr){
		this.message = mes;

		this.response = res;
		this.exception = ex;
		this.comAdr = adr;

        this.acknowledge = res.getAck() !=0;
        this.checksum = (short)res.getChecksum();

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


