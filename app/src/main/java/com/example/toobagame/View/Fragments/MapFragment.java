package com.example.toobagame.View.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toobagame.Model.Build;
import com.example.toobagame.R;
import com.example.toobagame.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.FilteringMode;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapFragment extends Fragment {


    private static final double DESIRED_ACCURACY = 0;
    private static final long MINIMAL_TIME = 0;
    private static final double MINIMAL_DISTANCE = 50;
    private static final boolean USE_IN_BACKGROUND = false;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int COMFORTABLE_ZOOM_LEVEL = 18;
    private static final String PATH_TO_BUILD = "Build";


// _________________________________________________________________________________________________


    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    private LocationManager locationManager;
    private LocationListener myLocationListener;
    private MapObjectCollection mapObjects;
    private Point myLocation;
    private PlacemarkMapObject placeMark;
    private ImageProvider imageProvider;

    private List<Build> builds;
//    private final String MAPKIT_API_KEY = "69d278ed-05cb-4b84-8f4e-777dadafe483";
    private boolean flag_init = false;
    private ConstraintLayout search_me;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private User thisUser;


// _________________________________________________________________________________________________


    private void initMapKit() {
//        if (true) {
//            MapKitFactory.setApiKey(MAPKIT_API_KEY);
//            MapKitFactory.initialize(getContext());
//            flag_init = true;
//        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMapKit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.getMap().setRotateGesturesEnabled(true);
        mapView.getMap().move(new CameraPosition(new Point(42.963613, 47.476902), 14, 0, 0));//Координаты махачкалы
        search_me = (ConstraintLayout) view.findViewById(R.id.lay_search_me);
        search_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myLocation == null) {
                    return;
                }
                moveCamera(myLocation, COMFORTABLE_ZOOM_LEVEL);
            }
        });
        locationManager = MapKitFactory.getInstance().createLocationManager();
        builds = new ArrayList<>();
        MapKit mapKit = MapKitFactory.getInstance();
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("Build");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mapObjects = mapView.getMap().getMapObjects().addCollection();


        if (checkLocationPermission()) {//Проверка разрешения
            locationManager = MapKitFactory.getInstance().createLocationManager();
            myLocationListener = new LocationListener() {//При наличии разрешения при каждой загрузки карты камера будет перемещаться на местоположение пользователя
                @Override
                public void onLocationUpdated(Location location) {
                    if (myLocation == null) {
                        moveCamera(location.getPosition(), COMFORTABLE_ZOOM_LEVEL);
                    }
                    myLocation = location.getPosition();
                    Log.w("My_App", "my location - " + myLocation.getLatitude() + "," + myLocation.getLongitude());
                }

                @Override
                public void onLocationStatusUpdated(LocationStatus locationStatus) {
                    if (locationStatus == LocationStatus.NOT_AVAILABLE) {
                    }
                }
            };
        }
        mapObjects.addTapListener(mapObjectTapListener);
        return view;
    }


    private MapObjectTapListener mapObjectTapListener = new MapObjectTapListener() {
        @Override
        public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
            thisUser = User.getInstance();
            Build build = (Build) mapObject.getUserData();
            assert build != null;
            String owner = build.getOwner();
            String name = thisUser.getName();
            if (owner.equals("empty")) {
                AlertDialog.Builder free = buildDialog(true, build);
                free.show();
            } else if(owner.equals(name)){
                AlertDialog.Builder free = buildDialog(false, build);
                free.show();
            }else{
                Toast.makeText(getContext(), "Владелец этого здания: " + build.getOwner(), Toast.LENGTH_SHORT).show();
            }

            return true;
        }
    };


    //Код для проверки и запроса разрешение на отслеживанию геолакации
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Должны ли мы показать объяснение?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Показать объяснение пользователю *асинхронно* -- не блокировать
                // этот поток, ожидающий ответа пользователя! После того, как пользователь
                // видит объяснение, попробуйте еще раз запросить разрешение.
                new AlertDialog.Builder(getContext())
                        .setTitle("Предупреждение")
                        .setMessage("Необходимо разрешение для работы")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }
            return false;
        } else {
            return true;
        }
    }


    private AlertDialog.Builder buildDialog(boolean flag, Build build) {
        AlertDialog.Builder window = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.free_build, null);

        Button btn_buyBuild = (Button) view.findViewById(R.id.buy_button);
        Button backBuild = (Button) view.findViewById(R.id.back_button);

        TextView nameBuild = (TextView) view.findViewById(R.id.name_build);
        TextView priceBuild = (TextView) view.findViewById(R.id.price_build);
        TextView incomeBuild = (TextView) view.findViewById(R.id.income_build);
        nameBuild.setText(build.getName());



        if (flag) {
            btn_buyBuild.setText("Купить");
            priceBuild.setText(Integer.toString(build.getPrice()));
            incomeBuild.setText(Integer.toString(build.getIncome()));
            window.setTitle("Купить здание");
        } else {
            btn_buyBuild.setText("Улучшить");
            priceBuild.setText(Double.toString(build.getPrice() * 0.45));
            incomeBuild.setText(Double.toString(build.getIncome() + build.getIncome() * 0.2));
            window.setTitle("Улучшить здание");
        }


        window.setView(view);
        AlertDialog alert = window.create();

        backBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });

        btn_buyBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    buyBuild(build);
                } else {
                    upgradeBuild(build);
                }

            }
        });




        return window;

    }

    private void upgradeBuild(Build build) {
        int balance = thisUser.getBalance();
        Double price = build.getPrice() * 0.45;
        int income = (int) (build.getIncome() + build.getIncome() * 0.2);
        if(balance >= price){
            database.getReference("Build").child(build.getName()).child("income").setValue(income);
            database.getReference("Build").child(build.getName()).child("level").setValue(2);
            build.setPrice(build.getPrice() + build.getPrice()/2);
            database.getReference("Build").child(build.getName()).child("price").setValue(build.getPrice());
            database.getReference("User").child(thisUser.getPassword()).child("balance").setValue(thisUser.getBalance());
            thisUser.setBalance((int) (balance - price));
            build.setIncome(income);
            build.setLevel(2);
            Toast.makeText(getContext(), "Здание улучшено", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Недостаточно средств", Toast.LENGTH_SHORT).show();
        }
    }

    private void buyBuild(@NonNull Build build) {
        int balance = thisUser.getBalance();
        int price = build.getPrice();
        if (balance >= price) {
            HashMap<String, Object> property = thisUser.toMapProperty();
            property.put(build.getName(), build.getName());
            int result = balance - price;
            thisUser.addBuild(build.getName());
            thisUser.setBalance(result);
            database.getReference("Build").child(build.getName()).child("owner").setValue(thisUser.getName());
            database.getReference("User").child(thisUser.getPassword()).child("balance").setValue(result);
            database.getReference("User").child(thisUser.getPassword()).child("property").updateChildren(property);
            Toast.makeText(getActivity(), "Покупка успешно совершена", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Недостаточно средств", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        locationManager.unsubscribe(myLocationListener);
        MapKitFactory.getInstance().onStop();
        mapView.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
        subscribeToLocationUpdate();
        readDataBuilds();

    }


    private void moveCamera(Point point, float zoom) {// Движение камеры
        mapView.getMap().move(
                new CameraPosition(point, zoom, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
    }

    private void subscribeToLocationUpdate() {//Метод определяющий подписку на обновления отслежваиния местоположения устройства менеджера
        if (locationManager != null && myLocationListener != null) {
            locationManager.subscribeForLocationUpdates(DESIRED_ACCURACY, MINIMAL_TIME, MINIMAL_DISTANCE, USE_IN_BACKGROUND, FilteringMode.OFF, myLocationListener);
        }
    }

    private void readDataBuilds() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Build build = ds.getValue(Build.class);
                    builds.add(build);
                }
                arragePoints();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("My_App", String.valueOf(error));
                Toast.makeText(getActivity(), "Read data failed", Toast.LENGTH_SHORT).show();
            }

        });

    }


    private void arragePoints() {
        Point point;
        for (Build build : builds) {
            Double x = Double.parseDouble(build.getPoint_x());
            Double y = Double.parseDouble(build.getPoint_y());
            point = new Point(x, y);
            placeMark = mapObjects.addPlacemark(point);
            placeMark.setUserData(build);
            imageProvider = ImageProvider.fromResource(getContext(), R.drawable.build_test);
            placeMark.setIcon(imageProvider);
        }
    }


}