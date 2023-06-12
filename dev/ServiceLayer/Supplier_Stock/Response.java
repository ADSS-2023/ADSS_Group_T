package ServiceLayer.Supplier_Stock;

public class Response {
    private Object value;
    private boolean error;
    String errorMassage;
    private Response(Object value,boolean isError,String errorMassage){
        this.value = value;
        error = isError;
        this.errorMassage = errorMassage;
    }
    public Response okResponse(Object value){
        return new Response(value,false,null);
    }
    public Response errorResponse(String errorMassage){
        return new Response(null,true,errorMassage);
    }
    public boolean isError() {
        return error;
    }

    public Object getValue() {
        return value;
    }

    public String getErrorMassage() {
        return errorMassage;
    }
}

