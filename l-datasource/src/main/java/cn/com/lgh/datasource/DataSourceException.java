package cn.com.lgh.datasource;

public class DataSourceException extends RuntimeException {

	private static final long serialVersionUID = 9199563462569992703L;

	public DataSourceException() {
		super();
	}

	public DataSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataSourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataSourceException(String message) {
		super(message);
	}

	public DataSourceException(Throwable cause) {
		super(cause);
	}
	
	public DataSourceException(String msg,Object ... objects) {
		super(String.format(msg,objects));
	}
	
    public DataSourceException(Throwable cause,String msg,Object ... objects) {
        super(String.format(msg,objects), cause);
    }
}
