package maker.view;

public class Password {
    private static final int key = 748576362;
    private final int encryptedPassword;

    public Password(int password) {
        this.encryptedPassword = encryptDecrypt(password);
    }

    private int encryptDecrypt(int password){
        return password ^ key;
    }

    public int getEncryptedPassword() {
        return encryptedPassword;
    }

    //guarda el password encriptado
    public final int storePassword(){
        System.out.println("guardando contraseña encriptada como " + this.encryptedPassword);
        return this.encryptedPassword;
    }

    //valida que la contraseña sea correcta
    public boolean letMeIn(int password) {
        System.out.println(encryptDecrypt(password) + " = " + this.encryptedPassword);
        if (encryptDecrypt(password) == this.encryptedPassword) {
            System.out.println("password correcto");
            return true;
        } else {
            System.out.println("contraseña incorrecta");
            return false;
        }
    }
}
