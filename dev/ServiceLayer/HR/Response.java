package ServiceLayer.HR;

public class Response {
    public String ErrorMessage;
    public Object ReturnValue;

    public Response()
    {
        this.ErrorMessage = null;
        this.ReturnValue = null;
    }

    public Response(String msg, Object ReturnValue)
    {
        this.ErrorMessage = msg;
        this.ReturnValue = ReturnValue;
    }

}
