package softalertv3.softalertv3.softalert.View.ActPrincipal.ActLocalizacoesUsuario;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Controller.EnderecoController;
import softalertv3.softalertv3.softalert.Controller.UsuarioClienteController;
import softalertv3.softalertv3.softalert.Interface.InterfaceListenerAPI;
import softalertv3.softalertv3.softalert.Model.Endereco;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Uteis.CodigoPermissao;
import softalertv3.softalertv3.softalert.Uteis.Geral;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class ActLocalizacoesUsuario extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, InterfaceListenerAPI{

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private LocationManager locationManager;
    private String operacaoFloatingButtao;

    private ArrayList<Endereco> listaEnderecos;
    private ArrayList<Marker> listaMarkers;

    private ProgressDialog progressDialog;

    private EditText txtPesquisaMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_localizacoes_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configurarComponentes();

        listaEnderecos = new ArrayList<>();
        listaMarkers = new ArrayList<>();
    }

    //region METODOS

    public void configurarComponentes() {
        txtPesquisaMapa = (EditText) findViewById(R.id.txtPesquisaMapa_content_act_localizacoes_usuario);

        txtPesquisaMapa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == KeyEvent.ACTION_DOWN || actionId == KeyEvent.KEYCODE_ENTER) {
                    String pesquisaMapa = txtPesquisaMapa.getText().toString();

                    Geocoder geocoder = new Geocoder(ActLocalizacoesUsuario.this);
                    List<Address> listaEnderecos = new ArrayList<>();

                    try {
                        listaEnderecos = geocoder.getFromLocationName(pesquisaMapa, 1);

                        if (listaEnderecos.size() > 0) {
                            Address address = listaEnderecos.get(0);

                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 16);
                            mMap.moveCamera(update);
                        }
                        else
                        {
                            Toast.makeText(ActLocalizacoesUsuario.this, "Nenhum endereço encontrado", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(ActLocalizacoesUsuario.this, "Nenhum endereço encontrado", Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    public void buscaPermissao_ACCESS_FINE_LOCATION() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(ActLocalizacoesUsuario.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setPositiveButton("Checar Permissão", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(ActLocalizacoesUsuario.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CodigoPermissao.ACCESS_FINE_LOCATION);
                    }
                });

                builder.setNegativeButton("Negar Permissão", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                Geral.chamarAlertDialog(builder, "", "A permissão ACCESS_FINE_LOCATION será utilizada para encontrar a sua localização atual");
            } else {
                ActivityCompat.requestPermissions(ActLocalizacoesUsuario.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CodigoPermissao.ACCESS_FINE_LOCATION);
            }
        } else {
            descobreLocalizacaoAtual();
        }
    }

    public void descobreLocalizacaoAtual() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "O GPS do aparelho está desativado. Não é possível encontrar a localização atual", Toast.LENGTH_LONG).show();

        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    public void FloatingActionMenuMapa_Item_Inserir_content_act_localizacoes_usuario_onClick(View v) {
        operacaoFloatingButtao = "i";
    }

    public void FloatingActionMenuMapa_Item_Editar_content_act_localizacoes_usuario_onClick(View v) {
        operacaoFloatingButtao = "e";
    }

    public void FloatingActionMenuMapa_Item_Remover_content_act_localizacoes_usuario_onClick(View v) {
        operacaoFloatingButtao = "r";
    }

    public void chamaModalIncAltLocal(final LatLng latLng, final Marker markerEdit) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ActLocalizacoesUsuario.this);
        View mview = getLayoutInflater().inflate(R.layout.content_act_localizacao_act_localizacoes_usuario_dialog_incalt, null);
        mBuilder.setView(mview);

        Button btnConfirmar = (Button) mview.findViewById(R.id.btnConfirmar_content_act_localizacoes_usuario_dialog_incalt);
        Button btnCancelar = (Button) mview.findViewById(R.id.btnCancelar_content_act_localizacoes_usuario_dialog_incalt);

        final EditText txtNomeLocal = (EditText) mview.findViewById(R.id.txtNomeLocal_content_act_localizacoes_usuario_dialog_incalt);
        final TextFieldBoxes txtbNomeLocal = (TextFieldBoxes) mview.findViewById(R.id.txtbNomeLocal_content_act_localizacoes_usuario_dialog_incalt);

        if(markerEdit != null){
            btnConfirmar.setText("Editar");
            txtNomeLocal.setText(markerEdit.getTitle());
        }

        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Geral.isCampoVazio(txtNomeLocal.getText().toString())) {
                    txtbNomeLocal.setError("O nome informado é inválido", true);
                    return;
                }

                if (markerEdit == null) {
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latLng.latitude, latLng.longitude))
                            .title(txtNomeLocal.getText().toString()));

                    marker.showInfoWindow();

                    if(!inserirEnderecoLista(marker))
                        marker.remove();
                    else
                        atualizarAPIComEnderecos();
                } else {
                    markerEdit.setTitle(txtNomeLocal.getText().toString());
                    markerEdit.showInfoWindow();

                    editarEnderecoLista(markerEdit);
                    atualizarAPIComEnderecos();
                }

                dialog.dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public boolean inserirEnderecoLista(Marker markerInc) {
        for (Marker m : listaMarkers) {

            if (m.getPosition().latitude == markerInc.getPosition().latitude &&
                    m.getPosition().longitude == markerInc.getPosition().longitude) {
                m = markerInc;
                return true;
            }
        }
        try {
            Geocoder geo = new Geocoder(ActLocalizacoesUsuario.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(markerInc.getPosition().latitude, markerInc.getPosition().longitude, 1);
            if (addresses.isEmpty()) {
                Geral.chamarAlertDialog(this, "Mensagem", "Nenhum endereço válido foi encontrado para esse local");
                return false;
            } else {
                if (addresses.size() <= 0) {
                    Geral.chamarAlertDialog(this, "Mensagem", "Nenhum endereço válido foi encontrado para esse local");
                    return false;
                }

                Address a = addresses.get(0);

                Endereco e = new Endereco();
                e.setCidade(a.getSubAdminArea());
                e.setEndereco(a.getAddressLine(0));
                e.setEstado(a.getAdminArea());
                e.setLatitude(markerInc.getPosition().latitude);
                e.setLongitude(markerInc.getPosition().longitude);
                e.setNomeLocal(markerInc.getTitle());
                e.setPais(a.getCountryName());
                e.setStatus("A");

                listaEnderecos.add(e);
            }
        } catch (Exception e) {
            Geral.chamarAlertDialog(this, "Erro", "Erro ao encontrar o endereço de tal local. Erro: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean editarEnderecoLista(Marker markerAlt) {

        int i = 0;
        for (Marker m : listaMarkers) {

            if (m.getPosition().latitude == markerAlt.getPosition().latitude &&
                    m.getPosition().longitude == markerAlt.getPosition().longitude) {
                listaEnderecos.get(i).setNomeLocal(markerAlt.getTitle());
                return true;
            }

            i++;
        }

        return true;
    }

    public boolean removerEnderecoLista(Marker markerAlt) {

        int i = 0;
        for (Marker m : listaMarkers) {

            if (m.getPosition().latitude == markerAlt.getPosition().latitude &&
                    m.getPosition().longitude == markerAlt.getPosition().longitude) {
                listaEnderecos.remove(i);
                return true;
            }

            i++;
        }

        return true;
    }

    public void atualizarAPIComEnderecos(){
        UsuarioCliente uc = UsuarioClienteController.retornaUsuarioClienteDAOInterno();
        uc.setListaEnderecos(listaEnderecos);

        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Atualizando registro. Aguarde...");
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();

            EnderecoController.inserirAPI(uc, this);
        } catch (Exception e) {
           Geral.chamarAlertDialog(this, "Erro", "Erro ao atualizar endereços. Erro: " + e.getMessage());
        }
    }

    //endregion

    //region EVENTOS

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (mMap != null)
            return;

        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        locationManager = (LocationManager) ActLocalizacoesUsuario.this.getSystemService(Context.LOCATION_SERVICE);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(ActLocalizacoesUsuario.this, "O GPS do aparelho está desativado. Não é possível encontrar a localização atual", Toast.LENGTH_LONG).show();
                    return true;
                }

                return false;
            }
        });

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "O GPS do aparelho está desativado. Não é possível encontrar a localização atual", Toast.LENGTH_LONG).show();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            buscaPermissao_ACCESS_FINE_LOCATION();
        } else {
            descobreLocalizacaoAtual();
        }

        progressDialog = new ProgressDialog(ActLocalizacoesUsuario.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Carregando as suas localizações...");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        EnderecoController.pesquisarEnderecosAPI(UsuarioClienteController.retornaUsuarioClienteDAOInterno(), this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(operacaoFloatingButtao == "" || operacaoFloatingButtao == null){
            Toast.makeText(this, "Selecione uma opção no botão roxo", Toast.LENGTH_LONG).show();
        }

        if (operacaoFloatingButtao == "i") {
            chamaModalIncAltLocal(latLng, null);
        }

        if (operacaoFloatingButtao == "e") {
            Toast.makeText(ActLocalizacoesUsuario.this, "Selecione um local para edita-lo",Toast.LENGTH_SHORT).show();
        }

        if (operacaoFloatingButtao == "r") {
            Toast.makeText(ActLocalizacoesUsuario.this, "Selecione um local para remove-lo",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CodigoPermissao.ACCESS_FINE_LOCATION: {
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        descobreLocalizacaoAtual();
                    }
                }
                break;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (operacaoFloatingButtao != "r" && operacaoFloatingButtao != "e") {
            Toast.makeText(this, "Selecione no botão roxo a opção para remoção ou edição do local", Toast.LENGTH_LONG).show();
        }

        if (operacaoFloatingButtao == "r") {
            marker.remove();

            removerEnderecoLista(marker);
            atualizarAPIComEnderecos();
        }

        if(operacaoFloatingButtao == "e") {
            chamaModalIncAltLocal(null, marker);
        }

        return false;
    }

    //endregion

    //region EVENTOS API

    @Override
    public void retornaMensagemSucesso(String mensagem) {

        progressDialog.cancel();

        if(mensagem != "") {
            return;
        }

        listaEnderecos = EnderecoController.retornaEnderecosPesquisados();

        for (Endereco e : listaEnderecos) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(e.getLatitude(), e.getLongitude()))
                    .title(e.getNomeLocal()));

            listaMarkers.add(marker);
        }
    }

    @Override
    public void retornaMensagemErro(String mensagem) {
        progressDialog.cancel();

        AlertDialog alertDialog = new AlertDialog.Builder(ActLocalizacoesUsuario.this).create();
        alertDialog.setTitle("Erro");
        alertDialog.setMessage("");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActLocalizacoesUsuario.this.finish();
                    }
                });
        alertDialog.show();
    }

    //endregion
}