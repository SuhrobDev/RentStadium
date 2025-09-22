import UIKit
import SwiftUI
import ComposeApp
import GoogleMaps

struct GoogleMapView: UIViewRepresentable {
    func makeUIView(context: Context) -> GMSMapView {
        let options = GMSMapViewOptions()
        options.camera = GMSCameraPosition.camera(withLatitude: 41.330162,  longitude: 69.285203, zoom: 10.0)

        let mapView = GMSMapView(options: options)
        
        // Creates a marker in the center of the map.
        let marker = GMSMarker()
        marker.position = CLLocationCoordinate2D(latitude: 41.330162,  longitude: 69.285203)
        marker.title = "Indiranagar"
        marker.snippet = "Bengaluru"
        marker.map = mapView
        
        return mapView
    }

    func updateUIView(_ uiView: GMSMapView, context: Context) {}
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            mapUIViewController: { () -> UIViewController in
                return UIHostingController(rootView: GoogleMapView())
            }
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea()
    }
}



