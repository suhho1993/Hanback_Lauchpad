package JNI;

public class TextLcd implements JNI {
	/** Called when the activity is first created. */
	static {
		System.loadLibrary("textlcd");
	} // JNI Library load

	// JNI Interface library function
	public native int TextLCDOut(String str, String str2);

	public native int IOCtlClear();

	public native int IOCtlReturnHome();

	public native int IOCtlDisplay(boolean bOn);

	int ret;
	boolean disp;

	String str2;

	public TextLcd() {
		disp = true;
		IOCtlClear();
		IOCtlReturnHome();
		IOCtlDisplay(disp);
	}
	
	public void getStr(String fString){
		str2=fString;
	}

	@Override
	public int UpdateValue() {
		ret = TextLCDOut("PARTY TIME!!", str2);
		return ret;
	}
}