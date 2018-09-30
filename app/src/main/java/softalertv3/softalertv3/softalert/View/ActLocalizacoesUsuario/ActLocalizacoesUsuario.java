package softalertv3.softalertv3.softalert.View.ActLocalizacoesUsuario;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import softalertv3.softalertv3.R;
import softalertv3.softalertv3.softalert.Uteis.CodigoPermissao;
import softalertv3.softalertv3.softalert.Uteis.Geral;

public class ActLocalizacoesUsuario extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_localizacoes_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if(mMap != null)
            return;

        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);
        locationManager = (LocationManager) ActLocalizacoesUsuario.this.getSystemService(Context.LOCATION_SERVICE);

        /*mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(ActLocalizacoesUsuario.this, "O GPS do aparelho está desativado. Não é possível encontrar a localização atual", Toast.LENGTH_LONG).show();
                    return true;
                }

                return false;
            }
        });*/

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "O GPS do aparelho está desativado. Não é possível encontrar a localização atual", Toast.LENGTH_LONG).show();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            buscaPermissao_ACCESS_FINE_LOCATION();
        } else {
            descobreLocalizacaoAtual();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(this, "Coordenadas" + latLng.toString(), Toast.LENGTH_LONG).show();
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
}
