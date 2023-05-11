package UtilSuper;

public class Response {
    private String errorMessage;


    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    private Object returnValue;

    public String getErrorMessage() {
        return errorMessage;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public Response()
    {
        this.errorMessage = null;
        this.returnValue = null;
    }

    public Response(String msg, Object ReturnValue)
    {
        this.errorMessage = msg;
        this.returnValue = ReturnValue;
    }

    public boolean isError() {
        return this.returnValue==null;
    }
}
