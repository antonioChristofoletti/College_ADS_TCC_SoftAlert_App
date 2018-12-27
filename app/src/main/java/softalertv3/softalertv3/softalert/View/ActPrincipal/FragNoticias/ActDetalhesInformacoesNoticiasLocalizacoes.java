package softalertv3.softalertv3.softalert.View.ActPrincipal.FragNoticias;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Model.Alerta;
import softalertv3.softalertv3.softalert.Model.AlertaRegiaoAfetada;
import softalertv3.softalertv3.softalert.Uteis.CodigoPermissao;
import softalertv3.softalertv3.softalert.Uteis.Geral;

public class ActDetalhesInformacoesNoticiasLocalizacoes extends AppCompatActivity
        implements OnMapReadyCallback {

    private Alerta alerta;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private LocationManager locationManager;

    private ProgressDialog progressDialog;

    private EditText txtPesquisaMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detalhes_informacoes_noticias_localizacoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.alerta = (Alerta) this.getIntent().getSerializableExtra("alerta");

        configurarComponentes();
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

                    Geocoder geocoder = new Geocoder(ActDetalhesInformacoesNoticiasLocalizacoes.this);
                    List<Address> listaEnderecos = new ArrayList<>();

                    try {
                        listaEnderecos = geocoder.getFromLocationName(pesquisaMapa, 1);

                        if (listaEnderecos.size() > 0) {
                            Address address = listaEnderecos.get(0);

                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 16);
                            mMap.moveCamera(update);
                        } else {
                            Toast.makeText(ActDetalhesInformacoesNoticiasLocalizacoes.this, "Nenhum endereço encontrado", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(ActDetalhesInformacoesNoticiasLocalizacoes.this, "Nenhum endereço encontrado", Toast.LENGTH_SHORT).show();
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

            if (ActivityCompat.shouldShowRequestPermissionRationale(ActDetalhesInformacoesNoticiasLocalizacoes.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setPositiveButton("Checar Permissão", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(ActDetalhesInformacoesNoticiasLocalizacoes.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CodigoPermissao.ACCESS_FINE_LOCATION);
                    }
                });

                builder.setNegativeButton("Negar Permissão", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                Geral.chamarAlertDialog(builder, "", "A permissão ACCESS_FINE_LOCATION será utilizada para encontrar a sua localização atual");
            } else {
                ActivityCompat.requestPermissions(ActDetalhesInformacoesNoticiasLocalizacoes.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CodigoPermissao.ACCESS_FINE_LOCATION);
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

    public void carregarRegioesAfetadas(Alerta alerta, GoogleMap mMap) {

        boolean primeiro = true;
        for (AlertaRegiaoAfetada arf : alerta.getListaAlertaRegiaoAfetada()) {

            LatLng latLng = new LatLng(arf.getLatitude(), arf.getLongitude());

            mMap.addCircle(new CircleOptions()
                    .center(latLng)
                    .radius(arf.getAbrangencia())
                    .strokeWidth(3)
                    .strokeColor(Color.parseColor(arf.getCorBordaCirculo()))
                    .fillColor(ColorUtils.setAlphaComponent(Color.parseColor(arf.getCorCirculo()), 70)));

            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(arf.getTitulo()))
                    .setSnippet(arf.getSubTitulo());

            if (primeiro) {
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 16);
                mMap.moveCamera(update);
                primeiro = false;
            }
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
        locationManager = (LocationManager) ActDetalhesInformacoesNoticiasLocalizacoes.this.getSystemService(Context.LOCATION_SERVICE);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(ActDetalhesInformacoesNoticiasLocalizacoes.this, "O GPS do aparelho está desativado. Não é possível encontrar a localização atual", Toast.LENGTH_LONG).show();
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

        carregarRegioesAfetadas(this.alerta, this.mMap);
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