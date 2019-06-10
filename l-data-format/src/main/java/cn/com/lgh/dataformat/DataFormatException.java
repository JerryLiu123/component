package cn.com.lgh.dataformat;

public class DataFormatException extends RuntimeException {

	private static final long serialVersionUID = -6813157083870768180L;

	public DataFormatException() {
		super();
	}

	public DataFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataFormatException(String message) {
		super(message);
	}

	public DataFormatException(Throwable cause) {
		super(cause);
	}
	
	public DataFormatException(String msg,Object ... objects) {
		super(String.format(msg,objects));
	}
	
    public DataFormatException(Throwable cause,String msg,Object ... objects) {
        super(String.format(msg,objects), cause);
    }
	
}
