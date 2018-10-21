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
import android.widget.Toast;

import java.util.ArrayList;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAlertaManagerNotification;
import softalertv3.softalertv3.softalert.Model.Alerta;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.View.ActCadastro_telefone_basico;
import softalertv3.softalertv3.softalert.View.ActPrincipal.FragNoticias.ActDetalhesInformacoesNoticias;

public class AlertaManagerNotification implements InterfaceListenerAPI{

    Context context;
    InterfaceListenerAlertaManagerNotification interfaceListenerAlertaManagerNotification;
    private static int IDNotificacao = 201019997;

    public static ArrayList<Alerta> listaAlerta;

    public AlertaManagerNotification(Context context,InterfaceListenerAlertaManagerNotification interfaceListenerAlertaManagerNotification) {
        this.context = context;
        this.interfaceListenerAlertaManagerNotification = interfaceListenerAlertaManagerNotification;
}

    public void iniciarGerenciamentoNotificacoes() {
        listaAlerta = new ArrayList<>();

        UsuarioCliente uc = UsuarioClienteController.retornaUsuarioClienteDAOInterno();

        while (true) {
            try {
                AlertaController.retornaAlertas(uc, this);
                Thread.sleep(30000);
            } catch (InterruptedException e) {
            }
        }
    }

    //region EVENTOS API
    @Override
    public void retornaMensagemSucesso(String mensagem) {

        ArrayList<Alerta> novaListaAlerta = AlertaController.retornaAlertaArrayList();

        interfaceListenerAlertaManagerNotification.atualizarListagemAlerta(novaListaAlerta);

        //Adiciona os novos alertas
        for (Alerta alertaNovo: novaListaAlerta) {

            boolean encontrou = false;
            for (Alerta alertaVelho : listaAlerta){

                if(alertaNovo.getId() == alertaVelho.getId()){
                    encontrou = true;
                    alertaNovo.setSubiuNotificacao(alertaVelho.isSubiuNotificacao());
                    alertaNovo.setIdNotification(alertaVelho.getIdNotification());

                    listaAlerta.remove(alertaVelho);
                    listaAlerta.add(alertaNovo);
                    break;
                }
            }

            if(!encontrou)
                listaAlerta.add(alertaNovo);
        }

        for (Alerta alerta : listaAlerta){

            if(alerta.getListaAlertaPossuiUsuarios() == null)
                continue;

            if(alerta.getListaAlertaPossuiUsuarios().get(0).getDataVisualizou() != null)
                continue;

            if(!alerta.getStatus().equals("Ativo"))
                continue;

            if((alerta.getNivelAlerta().getId() == 1 || alerta.getNivelAlerta().getId() == 2) && !alerta.isSubiuNotificacao()){
                enviarMensagem(alerta);
                alerta.setSubiuNotificacao(true);
            }

            if(alerta.getNivelAlerta().getId() == 3 || alerta.getNivelAlerta().getId() == 4){
                enviarMensagem(alerta);
                alerta.setSubiuNotificacao(true);
            }
        }

    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        Toast.makeText(context, "Erro ao atualizar os alertas enviados pelo usuário. " + mensagem, Toast.LENGTH_LONG).show();
    }

    //endregion

    public void enviarMensagem(Alerta alerta) {

        if(alerta.getIdNivelAlerta() == 0){
            alerta.setIdNivelAlerta(this.IDNotificacao++);
        }

        NotificationCompat.Builder notification;

        notification = new NotificationCompat.Builder(context);


        if(alerta.getNivelAlerta().getId() == 1 || alerta.getNivelAlerta().getId() == 2)
        {
            notification.setPriority(NotificationCompat.PRIORITY_HIGH);
            notification.setAutoCancel(true);

            notification.setVibrate(new long[]{Notification.DEFAULT_VIBRATE});
        }else{
            notification.setPriority(NotificationCompat.PRIORITY_MAX);
            notification.setAutoCancel(false);

            Uri uri;
            if(alerta.getNivelAlerta().getId() == 3)
                uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.amber_alert_11seconds);
            else
                uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.amber_alert_22seconds);

            notification.setVibrate(new long[]{0,400,800,600,800,800,800,1000});
            notification.setSound(uri);
        }

        notification.setSmallIcon(R.drawable.baseline_error_white_24);

        int idAlertCircleBitMap = R.drawable.alert_circle_green_96dp;

        notification.setColor(Color.GREEN);
        notification.setLights(Color.GREEN, 3000, 3000);

        if(alerta.getNivelAlerta().getId() == 2) {
            idAlertCircleBitMap = R.drawable.alert_circle_yellow_96dp;
            notification.setColor(Color.parseColor("#FFFDE7"));
            notification.setLights(Color.YELLOW, 3000, 3000);
        }

        if(alerta.getNivelAlerta().getId() == 3) {
            idAlertCircleBitMap = R.drawable.alert_circle_orange_96dp;
            notification.setColor(Color.parseColor("#FFB74D"));
            notification.setLights(Color.rgb(255,0,0), 3000, 3000);
        }

        if(alerta.getNivelAlerta().getId() == 4) {
            idAlertCircleBitMap = R.drawable.alert_circle_red_96dp;
            notification.setColor(Color.RED);
            notification.setLights(Color.RED, 3000, 3000);
        }

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), idAlertCircleBitMap);

        notification.setColorized(true);

        notification.setLargeIcon(bitmap);

        notification.setWhen(System.currentTimeMillis());
        notification.setVisibility(Notification.VISIBILITY_PUBLIC);

        notification.setContentTitle(alerta.getEvento().toUpperCase());

        notification.setContentText(alerta.getDescricao());
        notification.setSubText(alerta.getNivelAlerta().getDescricao().toUpperCase());

        Intent intent = new Intent(context, ActDetalhesInformacoesNoticias.class);
        intent.putExtra("alerta", alerta);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(IDNotificacao, notification.build());
    }
}
