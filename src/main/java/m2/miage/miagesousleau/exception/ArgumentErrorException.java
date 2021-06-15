package m2.miage.miagesousleau.exception;

public class ArgumentErrorException extends RuntimeException{
    private static final long serialVersionUID = 8590322588553521560L;

    public ArgumentErrorException(String s) { super(s);}

    public ArgumentErrorException(Throwable t) {super(t);}
}
