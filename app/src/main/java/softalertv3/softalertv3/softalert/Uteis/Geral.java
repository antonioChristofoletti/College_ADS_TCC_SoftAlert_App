package softalertv3.softalertv3.softalert.Uteis;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import softalertv3.softalertv3.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;

public abstract class Geral {

    public static boolean isCPF(String CPF) {
        if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555") || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999") || (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48);
            }
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static Date geraDataOrThrowsException(String formato, String data) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            return sdf.parse(data);
        } catch (Exception ex) {
            throw new Exception("Erro ao gerar data. Erro: " + ex.getMessage());
        }
    }

    public static Date geraData(String formato, String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            return sdf.parse(data);
        } catch (Exception ex) {
            return geraData("dd/MM/yyyy", "1/01/1500");
        }
    }

    public static String removerMascara(String texto) {
        texto = texto.replace(".", "");
        texto = texto.replace(",", "");
        texto = texto.replace(";", "");
        texto = texto.replace("(", "");
        texto = texto.replace(")", "");
        texto = texto.replace("-", "");

        return texto;
    }

    public static String setaMascara(String valor, String mascara) {
        StringBuilder stringModificada = new StringBuilder();

        for (int i=0; i < valor.length();i++){

            if(mascara.charAt(i) == '#')
            {
                stringModificada.append(valor.charAt(i));
            }
            else
            {
                stringModificada.append(mascara.charAt(i));
                stringModificada.append(valor.charAt(i));
            }
        }

        return stringModificada.toString();
    }

    public static void chamarAlertDialog(Context context, String titulo, String mensagem) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle(titulo);
        dlg.setMessage(mensagem);
        dlg.setNeutralButton(R.string.lbl_ok, null);
        dlg.show();
    }

    public static void chamarAlertDialog(AlertDialog.Builder dlg, String titulo, String mensagem) {
        dlg.setTitle(titulo);
        dlg.setMessage(mensagem);
        dlg.show();
    }

    public static boolean isCampoVazio(String valor) {
        if (valor == null)
            return true;

        if (TextUtils.isEmpty(valor) || valor.trim().isEmpty())
            return true;

        return false;
    }

    public static boolean isEmailValido(String email) {
        if (!isCampoVazio(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false;

        return true;
    }

    public static String getRandomString(int tamanho) {
        String caracteresValidos = "0123456789qwertyuiopasdfghjklzxcvbnm";

        Random random = new Random();

        final StringBuilder sb = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; ++i)
            sb.append(caracteresValidos.charAt(random.nextInt(caracteresValidos.length())));

        return sb.toString();
    }
}