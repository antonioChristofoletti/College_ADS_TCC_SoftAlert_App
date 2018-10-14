package softalertv3.softalertv3.softalert.Controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.Alerta;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.View.ActCadastro_telefone_basico;

public class AlertaManagerNotification implements InterfaceListenerAPI{

    Context context;
    private static int uniqueID = 201019997;

    public static ArrayList<Alerta> listaAlerta;

    public AlertaManagerNotification(Context context) {
        this.context = context;
    }

    public void iniciarGerenciamentoNotificacoes() {
        listaAlerta = new ArrayList<>();

        UsuarioCliente uc = UsuarioClienteController.retornaUsuarioClienteDAOInterno();

        while (true) {
            try {
                AlertaController.retornaAlertas(uc, this);
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void retornaMensagemSucesso(String mensagem) {

        ArrayList<Alerta> novaListaAlerta = AlertaController.retornaAlertaArrayList();

        //Adiciona os novos alertas
        for (Alerta alertaNovo: novaListaAlerta) {

            for (Alerta alertaVelho : listaAlerta){

                if(alertaNovo.getId() == alertaVelho.getId()){
                    continue;
                }
            }

            listaAlerta.add(alertaNovo);
        }

        for (Alerta alerta : listaAlerta){

            if((alerta.getNivelAlerta().getId() == 1 || alerta.getNivelAlerta().getId() == 4) && !alerta.isSubiuNotificacao()){
                enviarMensagem(alerta);
                alerta.setSubiuNotificacao(true);
            }
        }

    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        Log.e("ALERT M. NOTIFICATION", mensagem);
    }

    public void enviarMensagem(Alerta alerta) {

        NotificationCompat.Builder notification;

        notification = new NotificationCompat.Builder(context);
        notification.setAutoCancel(false);

        notification.setSmallIcon(R.drawable.baseline_error_white_24);

        int idAlertCircleBitMap = R.drawable.alert_circle_green_96dp;

        if(alerta.getNivelAlerta().getId() == 2) {
            idAlertCircleBitMap = R.drawable.alert_circle_yellow_96dp;
            notification.setColor(Color.YELLOW);
        }
        if(alerta.getNivelAlerta().getId() == 3) {
            idAlertCircleBitMap = R.drawable.alert_circle_orange_96dp;
            notification.setColor(Color.rgb(255,0,0));
        }
        if(alerta.getNivelAlerta().getId() == 4) {
            idAlertCircleBitMap = R.drawable.alert_circle_red_96dp;
            notification.setColor(Color.RED);
        }

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), idAlertCircleBitMap);


        notification.setColorized(true);

        notification.setLargeIcon(bitmap);

        notification.setWhen(System.currentTimeMillis());
        notification.setVisibility(Notification.VISIBILITY_PUBLIC);

        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alert_sound);
        notification.setVibrate(new long[]{0,400,800,600,800,800,800,1000});
        notification.setSound(uri);

        notification.setContentTitle(alerta.getEvento().toUpperCase());

        notification.setContentText(alerta.getDescricao());
        notification.setSubText(alerta.getNivelAlerta().getDescricao().toUpperCase());

        notification.setLights(Color.RED, 3000, 3000);

        notification.setPriority(NotificationCompat.PRIORITY_MAX);

        Intent intent = new Intent(context, ActCadastro_telefone_basico.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
    }
}
