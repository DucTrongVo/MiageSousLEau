package m2.miage.miagesousleau.exception;

public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = 2072764419774593801L;

    public NotFoundException(String s) { super(s);}

    public NotFoundException(Throwable t) {super(t);}
}
