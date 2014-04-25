package Common;

public interface IMessage {

	public String getMessage();
	public Boolean getAcknowledge();
	public Short getChecksum();
	public IResponse getResponse();
	public Exception getException();
	public short getComAdr();
}
