package site.metacoding.red.handler.ex;

public class MyApiException extends RuntimeException{ // Exception은 컴파일 에런줄 착각함 Run으로 해야함.
	
	public MyApiException(String msg) {
		super(msg);
	}

}
