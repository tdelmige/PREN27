package Common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.Date;

public class MessageImpl implements IMessage {

	private String message;
	private Boolean acknowledge;
	private Short checksum;
    private int payload;
	private IResponse response;
	private Exception exception;
	private Short comAdr;
    private String function;
    private Boolean checked = false;

    private static Logger log = LogManager.getLogger(MessageImpl.class.getName());
	
	public MessageImpl(String mes, IResponse res, Exception exc, Short adr, String function){
		this.message = mes;

		this.response = res;
		this.exception = exc;
		this.comAdr = adr;
        this.function = function;
        this.acknowledge = res.getAck() !=0;
        this.checksum = (short)res.getChecksum();

        try
        {
            byte[] buffer = res.getPayload();
            int payload =  byteArrayToInt(buffer);
            this.payload = payload;
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
        }

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
    public int getPayload() {
        return payload;
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

    @Override
    public String getFunction() {
        return function;
    }

    @Override
    public Boolean getChecked() {
        return checked;
    }

    @Override
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public static int byteArrayToInt(byte[] b)
    {
        int value = 0;
        for (int i = 0; i < 3; i++) {
            int shift = (2 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }

}


