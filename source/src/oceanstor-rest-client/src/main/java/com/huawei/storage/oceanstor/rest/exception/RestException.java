package com.huawei.storage.oceanstor.rest.exception;


public class RestException extends Exception
{
    private static final long serialVersionUID = -1041241007791942503L;

    private String errorCode;
    
    private String errorDescription;
    
    private String errorSuggestion;
    

    private Exception exception;

   
    public RestException(String errorCode,String errorDescription,String errorSuggestion)
    {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorSuggestion = errorSuggestion;
    }

    
    public void setException(Exception exception)
    {
        this.exception = exception;
    }

    
    public Exception getException()
    {
        return exception;
    }


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getErrorDescription() {
		return errorDescription;
	}


	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}


	public String getErrorSuggestion() {
		return errorSuggestion;
	}


	public void setErrorSuggestion(String errorSuggestion) {
		this.errorSuggestion = errorSuggestion;
	}
    
    @Override
    public String toString() {
    	return "Exception : [errorCode:"+errorCode
    			+",errorDescription:"+ errorDescription
    			+",errorSuggestion:" + errorSuggestion+"]";
    }
    
}
