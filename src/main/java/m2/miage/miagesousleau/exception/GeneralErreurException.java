package m2.miage.miagesousleau.exception;

public class GeneralErreurException extends Exception{

    private static final long serialVersionUID = 327710596327602969L;

    public GeneralErreurException() {
        String s = "Une erreur est survenue. Veuillez contactez notre développeur pour plus d'informaiton!";
    }

    public GeneralErreurException(Throwable t) {
        String s = "Une erreur est survenue. Veuillez contactez notre développeur pour plus d'informaiton!";
    }
}
