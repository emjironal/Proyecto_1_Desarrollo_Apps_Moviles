package com.example.sergio.breakfoodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sergio.breakfoodapp.http.GestorGetRequest;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.example.sergio.breakfoodapp.model.Restaurant;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultadoActivity extends AppCompatActivity implements PermissionsListener {

    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    double longitude = 9.8560621;
    double latitude = -83.9112765;
    List<Restaurant> restaurantList;
    private MixpanelAPI mixpanelAPI;
    String nombreRestaurante, calificacion, distancia, precio, tipoComida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        getResults();

        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(),getString(R.string.mixpanel_token));
        mixpanelAPI.track(this.getClass().getName());
        mixpanelAPI.flush();



        // Create supportMapFragment
        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            // Create fragment
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Build mapboxMap
            MapboxMapOptions options = new MapboxMapOptions();
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(latitude,longitude))
                    .zoom(14)
                    .build());

            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);


            transaction.add(R.id.container, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                ResultadoActivity.this.mapboxMap = mapboxMap;
                mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
                        style.addImage("marker-icon-id",
                                BitmapFactory.decodeResource(
                                        ResultadoActivity.this.getResources(), R.drawable.mapbox_marker_icon_default));


                        GeoJsonSource restaurante;
                        SymbolLayer symbolLayer;
                        for(Restaurant r: restaurantList){
                            restaurante = new GeoJsonSource("res" + r.getId(), Feature.fromGeometry(
                                Point.fromLngLat(r.getLongitude(),r.getLat())));
                            style.addSource(restaurante);

                            symbolLayer = new SymbolLayer("layer-id" + r.getId(), "res" + r.getId());
                            symbolLayer.withProperties(
                                    PropertyFactory.iconImage("marker-icon-id"),
                                    PropertyFactory.iconAllowOverlap(true),
                                    PropertyFactory.textField(r.getName()) //nombre del label
                            );
                            style.addLayer(symbolLayer);
                        }





                        //Marker
                        /*
                        GeoJsonSource geoJsonSource = new GeoJsonSource("source-id", Feature.fromGeometry(
                                Point.fromLngLat(-84.0908232,9.9561258)));
                        style.addSource(geoJsonSource);

                        SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                        symbolLayer.withProperties(
                                PropertyFactory.iconImage("marker-icon-id")
                        );
                        style.addLayer(symbolLayer);

                        //Se annadió elemento

                        GeoJsonSource rostipollo = new GeoJsonSource("rostipollo", Feature.fromGeometry(
                                Point.fromLngLat(-84.0916764,9.9564563)));
                        style.addSource(rostipollo);

                        symbolLayer = new SymbolLayer("layer-id2", "rostipollo");
                        symbolLayer.withProperties(
                                PropertyFactory.iconImage("marker-icon-id"),
                                PropertyFactory.textField("Rostipollos"),
                                //nombre del label
                                PropertyFactory.iconAllowOverlap(true)
                        );
                        */

                        //TODO: add "ver más" onClickListener

                        //style.addLayer(symbolLayer);
                    }
                });
            }
        });

    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.NORMAL);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }


    private void getResults(){
        Intent intent = getIntent();
        nombreRestaurante = intent.getStringExtra("nombreRestaurante");
        calificacion = intent.getStringExtra("calificacion").toLowerCase();
        distancia = intent.getStringExtra("distancia").toLowerCase();
        precio = intent.getStringExtra("precio").toLowerCase();
        tipoComida = intent.getStringExtra("tipo");
        latitude = intent.getDoubleExtra("latitude",latitude);
        longitude = intent.getDoubleExtra("longitude",longitude);


        String url = "https://appetyte.herokuapp.com/android/buscarRestaurante";
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if(!nombreRestaurante.equals(""))
            nameValuePairs.add(new BasicNameValuePair("name", nombreRestaurante));
        if(!precio.equals("cualquiera"))
            nameValuePairs.add(new BasicNameValuePair("price", precio));
        if(!calificacion.equals("cualquiera"))
            nameValuePairs.add(new BasicNameValuePair("score", calificacion));
        if(!distancia.equals("cualquiera")){
            nameValuePairs.add(new BasicNameValuePair("distance", distancia));
            nameValuePairs.add(new BasicNameValuePair("latitudepos", Double.toString(latitude)));
            nameValuePairs.add(new BasicNameValuePair("longitudepos", Double.toString(longitude)));
        }
        if(!tipoComida.toLowerCase().equals("cualquiera"))
            nameValuePairs.add(new BasicNameValuePair("footype", tipoComida));

        String result = LectorHttpResponse.leer(GestorPostRequest.postData(url,nameValuePairs));
        JSONArray jsonArray = new JSONArray();
        restaurantList = new ArrayList<>();
        try{
            jsonArray = new JSONArray(result);
            Restaurant r1;
            for (int i = 0; i < jsonArray.length(); i++) {
                r1 = new Restaurant();
                JSONObject restaurant = jsonArray.getJSONObject(i);
                r1.setName(restaurant.getString("name"));
                r1.setLat(restaurant.getDouble("latitudepos"));
                r1.setLongitude(restaurant.getDouble("longitudepos"));
                r1.setPrice(restaurant.getString("price"));
                r1.setId(restaurant.getInt("idrestaurant"));
                restaurantList.add(r1);
            }
        }catch (Exception ignored){}
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.mapbox_attributionTelemetryMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.mapbox_attributionTelemetryMessage, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void search(View view){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(getApplicationContext(), BuscadorActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }

    public void getListView(View view){

        Intent newScreen = new Intent(getApplicationContext(), ResultListView.class);
        newScreen.putExtra("precio",precio);
        newScreen.putExtra("nombreRestaurante",nombreRestaurante);
        newScreen.putExtra("calificacion",calificacion);
        newScreen.putExtra("tipo",tipoComida);
        newScreen.putExtra("latitude",latitude);
        newScreen.putExtra("longitude",longitude);
        newScreen.putExtra("distancia",distancia);
        startActivity(newScreen);

    }


}
