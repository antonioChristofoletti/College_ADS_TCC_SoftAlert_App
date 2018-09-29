package softalertv3.softalertv3.softalert.View.ActLocalizacoesUsuario;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import softalertv3.softalertv3.softalert.Uteis.CodigoPermissao;
import softalertv3.softalertv3.softalert.Uteis.Geral;

public class FragActLocalizacoesUsuario extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;

    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(getContext(), "O GPS do aparelho está desativado. Não é possível encontrar a localização atual", Toast.LENGTH_LONG).show();
                    return true;
                }


                return false;
            }
        });

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getContext(), "O GPS do aparelho está desativado. Não é possível encontrar a localização atual", Toast.LENGTH_LONG).show();
        }

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            buscaPermissao_ACCESS_FINE_LOCATION();
        } else {
            descobreLocalizacaoAtual();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getContext(), "Coordenadas" + latLng.toString(), Toast.LENGTH_LONG).show();
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
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setPositiveButton("Checar Permissão", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CodigoPermissao.ACCESS_FINE_LOCATION);
                    }
                });

                builder.setNegativeButton("Negar Permissão", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                Geral.chamarAlertDialog(builder, "", "A permissão ACCESS_FINE_LOCATION será utilizada para encontrar a sua localização atual");
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CodigoPermissao.ACCESS_FINE_LOCATION);
            }
        } else {
            descobreLocalizacaoAtual();
        }
    }

    public void descobreLocalizacaoAtual() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getContext(), "O GPS do aparelho está desativado. Não é possível encontrar a localização atual", Toast.LENGTH_LONG).show();

        }

        mMap.setMyLocationEnabled(true);
    }
}