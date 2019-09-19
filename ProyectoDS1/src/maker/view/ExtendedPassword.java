package maker.view;

public class ExtendedPassword extends Password {

    private int decryptedPassword;

    public ExtendedPassword(int password) {
        super(password);
        this.decryptedPassword = password; //guarda la contraseña en decrypted
    }


}
