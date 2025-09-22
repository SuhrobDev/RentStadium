import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {
    
    init() {
        GMSServices.provideAPIKey("AIzaSyC8RqZqkYiY0VDBrvgaGFPBXZSlwx5VLBs")
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}


//class AppDelegate: NSObject, UIApplicationDelegate {
//    func application(
//        _ application: UIApplication,
//        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
//    ) -> Bool {
////        FirebaseApp.configure()
//        NotifierManager.shared.initialize(configuration: NotificationPlatformConfigurationIos(
//                    showPushNotification: true,
//                    askNotificationPermissionOnStart: true,
//                    notificationSoundName: nil
//                  )
//              )
//        return true
//    }
//    
//    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
//           Messaging.messaging().apnsToken = deviceToken
//     }
//}
