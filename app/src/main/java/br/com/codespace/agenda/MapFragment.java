package br.com.codespace.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.codespace.agenda.dao.StudentDAO;
import br.com.codespace.agenda.model.Student;

/**
 * Created by gilma on 09/04/2017.
 */
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        StudentDAO dao = new StudentDAO(getContext());
        for (Student student : dao.getAll()) {
            LatLng position = this.getCoordinates(student.getAddress());
            if (position != null) {
                MarkerOptions marker = new MarkerOptions();
                marker.position(position);
                marker.title(student.getFirstName());
                marker.snippet(student.getScore().toString());
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 17);
                googleMap.moveCamera(update);
                googleMap.addMarker(marker);
            }
        }
    }

    /**
     * Dado um endereço, retorna as coodernadas do mesmo
     * @param address   Endereço para o qual se deseja obter as coodernadas
     * @return  LatLng
     */
    private LatLng getCoordinates(String address) {
        Geocoder coder = new Geocoder(getContext());
        try {
            List<Address> resultado = coder.getFromLocationName("rua do cravo, sao paulo", 1);
            if (!resultado.isEmpty()){
                return new LatLng(resultado.get(0).getLatitude(), resultado.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
