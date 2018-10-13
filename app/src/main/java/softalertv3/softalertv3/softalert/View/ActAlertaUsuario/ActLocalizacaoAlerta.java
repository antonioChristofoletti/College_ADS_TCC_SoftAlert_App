package softalertv3.softalertv3.softalert.View.ActAlertaUsuario;

import android.Manifest;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
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
import softalertv3.softalertv3.softalert.Model.Endereco;
import softalertv3.softalertv3.softalert.Uteis.CodigoPermissao;
import softalertv3.softalertv3.softalert.Uteis.Geral;

public class ActLocalizacaoAlerta extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private LocationManager locationManager;

    private EditText txtPesquisaMapa;

    public static Marker markerSelecionado;
    public static Endereco enderecoSelecionado;
    public static Boolean selecionou = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_localizacao_alerta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configurarComponentes();

        enderecoSelecionado = (Endereco) this.getIntent().getSerializableExtra("enderecoSelecionado");

        if(enderecoSelecionado == null) {
            markerSelecionado = null;
            selecionou = false;
        }
        else
            selecionou = true;
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

                    Geocoder geocoder = new Geocoder(ActLocalizacaoAlerta.this);
                    List<Address> listaEnderecos = new ArrayList<>();

                    try {
                        listaEnderecos = geocoder.getFromLocationName(pesquisaMapa, 1);

                        if (listaEnderecos.size() > 0) {
                            Address address = listaEnderecos.get(0);

                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 16);
                            mMap.moveCamera(update);
                        } else {
                            Toast.makeText(ActLocalizacaoAlerta.this, "Nenhum endereço encontrado", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(ActLocalizacaoAlerta.this, "Nenhum endereço encontrado", Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        FloatingActionMenu floatingActionMenuFinalizar = (FloatingActionMenu) findViewById(R.id.FloatingActionMenuFinalizar_content_act_localizacao_alerta);

        floatingActionMenuFinalizar.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(markerSelecionado == null){
                    Toast.makeText(ActLocalizacaoAlerta.this, "Nenhum local foi selecionado", Toast.LENGTH_SHORT).show();
                    return;
                }

                selecionou =true;
                finish();
            }
        });
    }

    public void buscaPermissao_ACCESS_FINE_LOCATION() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(ActLocalizacaoAlerta.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setPositiveButton("Checar Permissão", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(ActLocalizacaoAlerta.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CodigoPermissao.ACCESS_FINE_LOCATION);
                    }
                });

                builder.setNegativeButton("Negar Permissão", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                Geral.chamarAlertDialog(builder, "", "A permissão ACCESS_FINE_LOCATION será utilizada para encontrar a sua localização atual");
            } else {
                ActivityCompat.requestPermissions(ActLocalizacaoAlerta.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CodigoPermissao.ACCESS_FINE_LOCATION);
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

    public boolean encontrarEndereco(Marker marker){
        try {
            Geocoder geo = new Geocoder(ActLocalizacaoAlerta.this.getApplicationContext(), Locale.getDefault());

            List<Address> addresses = geo.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
            if (addresses.isEmpty()) {
                Geral.chamarAlertDialog(this, "Mensagem", "Nenhum endereço válido foi encontrado para esse local");

                return false;
            } else {
                if (addresses.size() <= 0) {
                    Geral.chamarAlertDialog(this, "Mensagem", "Nenhum endereço válido foi encontrado para esse local");
                    return false;
                }

                Address a = addresses.get(0);

                enderecoSelecionado = new Endereco();
                enderecoSelecionado.setCidade(a.getSubAdminArea());
                enderecoSelecionado.setEndereco(a.getAddressLine(0));
                enderecoSelecionado.setEstado(a.getAdminArea());
                enderecoSelecionado.setLatitude(marker.getPosition().latitude);
                enderecoSelecionado.setLongitude(marker.getPosition().longitude);
                enderecoSelecionado.setPais(a.getCountryName());
                enderecoSelecionado.setStatus("A");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao encontrar o endereço de tal local. Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    //region EVENTOS

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (mMap != null)
            return;

        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);
        locationManager = (LocationManager) ActLocalizacaoAlerta.this.getSystemService(Context.LOCATION_SERVICE);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(ActLocalizacaoAlerta.this, "O GPS do aparelho está desativado. Não é possível encontrar a localização atual", Toast.LENGTH_LONG).show();
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

        if(enderecoSelecionado != null){
            Marker novoMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(enderecoSelecionado.getLatitude(), enderecoSelecionado.getLongitude()))
                    .title("Local Alerta"));

            novoMarker.showInfoWindow();

            if (markerSelecionado != null)
                markerSelecionado.remove();

            markerSelecionado = novoMarker;

            if (!encontrarEndereco(novoMarker))
                markerSelecionado.remove();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

        Marker novoMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .title("Local Alerta"));

        novoMarker.showInfoWindow();

        if (markerSelecionado != null)
            markerSelecionado.remove();

        markerSelecionado = novoMarker;

        if (!encontrarEndereco(novoMarker))
            markerSelecionado.remove();
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

    //endregion
}