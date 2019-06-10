package cn.com.lgh.cache;

public class CacheException extends RuntimeException {

	private static final long serialVersionUID = 9199563462569992703L;

	public CacheException() {
		super();
	}

	public CacheException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}
	
	public CacheException(String msg,Object ... objects) {
		super(String.format(msg,objects));
	}
	
    public CacheException(Throwable cause,String msg,Object ... objects) {
        super(String.format(msg,objects), cause);
    }
}
