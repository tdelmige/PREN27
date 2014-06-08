package Common;

public interface IMessage {

	public String getMessage();
	public Boolean getAcknowledge();
	public Short getChecksum();
    public int getPayload();
	public IResponse getResponse();
	public Exception getException();
	public short getComAdr();
    public String getFunction();
    public Boolean getChecked();
    public void setChecked(Boolean checked);
}
