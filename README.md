**Loogle: A Crowdsourced Toilet Finder Application**

In 2017, as part of a RamHacks hackathon at Virginia Commonwealth University with @ShaneGifford and @WalkerLittle, we developed an Android application named Loogle. The primary goal of Loogle was to help users locate the nearest available public toilet using a crowdsourced model. This solution addressed a common issue: finding clean and accessible public restrooms, especially in unfamiliar places. The application leveraged user inputs to grow its database, thereby improving its utility over time. Loogle was designed with a minimalist front-end using Android Studio, making it user-friendly and easy to navigate. Below is a detailed explanation of the features and implementation of the application.

### **Key Features**

1. **Crowdsourced Location Database**:
   - Loogle depends on users who have previously used the app to contribute information about toilets they have located and visited. By doing so, the app crowdsources the locations of public restrooms and makes them accessible to other users in real-time. The system becomes increasingly more accurate and comprehensive as more users contribute data.

2. **Firebase Database Integration**:
   - Loogle uses a Firebase database to store and manage restroom locations. Firebase provides a cloud-based, real-time database that allows all users to access the most up-to-date information. This integration ensures that restroom locations are synchronized across all devices, providing users with accurate information. The benefits of using Firebase include scalability, real-time data synchronization, and ease of integration, which allows for seamless data storage and retrieval.

3. **Minimalist User Interface**:
   - The user interface was built with simplicity in mind. Using Android Studio, we crafted a clean design focusing on ease of use. The layout includes a simple button for initiating a search for the nearest available restroom and intuitive prompts for users to grant the required permissions.

4. **Location Services Integration**:
   - The application integrates Android's location services to determine the user's current location. Using GPS data, Loogle routes users to the nearest restroom available in its database. The app includes logic to handle permissions and manage the GPS settings seamlessly.

### **Detailed Breakdown of Code**

The following breakdown provides a comprehensive understanding of how the code enables Loogle to achieve its objectives.

1. **Package and Imports**
   - The package `gifford.com.loogle` and associated imports include several essential classes, such as `LocationManager` for accessing location services, `ActivityCompat` for permissions, and `TextView` and `Button` for managing UI elements.

2. **Activity Setup**
   - The primary activity in the application is `HomeActivity`, which serves as the main screen of Loogle. It initializes the essential components and prompts users to either allow or deny location permissions. It also includes a button (`firstHomeButton`) that allows users to start searching for restrooms once the necessary permissions are granted.

3. **Location Permission Handling**
   - The app utilizes `initializeLocationServices()` to set up location services. This function first checks if the GPS is enabled and, if not, directs the user to the appropriate settings screen to enable it. Additionally, the function checks if location permissions have been granted, prompting the user if necessary.

4. **User Interface Flow**
   - The layout of the app includes a button (`firstHomeButton`) and a text prompt (`requestText`). Depending on whether location permissions are granted, the `setFirstHomeButton()` function dynamically changes the button text and its click behavior:
     - If GPS is enabled and permissions are granted, the button text changes to "New" and clicking it will initiate a location request. The app will then fetch the user's current GPS coordinates and (in future versions) send this data to the Firebase database to look for nearby toilets.
     - If permissions are not granted, the button will prompt the user to provide access to location services.

5. **Button Click Handling**
   - The click listener for `firstHomeButton` is responsible for different behaviors depending on the state of permissions:
     - If permissions are granted, the app attempts to retrieve the user's current location and display a toast message with the coordinates. In a complete version of the app, these coordinates would be used to access the Firebase database and route the user to the nearest restroom.
     - If permissions are denied, the app will re-prompt the user to grant access to location services.

6. **Logging and Error Handling**
   - The app includes extensive use of the `Log` class for debugging purposes. These logs help developers track issues during the initialization of location services, permission handling, and button click actions.

### **Technical Workflow**

1. **App Launch**:
   - Upon launching the app, `onCreate()` initializes the `HomeActivity`, setting up the UI and requesting GPS access.

2. **Location Services Initialization**:
   - The `initializeLocationServices()` function checks if GPS is enabled and ensures that location permissions are granted. If either condition is not met, the app guides the user to resolve the issue.

3. **Button Behavior**:
   - Depending on the GPS and permission status, the `firstHomeButton` is configured accordingly. If all conditions are met, clicking the button fetches the user's last known location and forwards it to the Firebase database.

### **Challenges and Future Work**

- **Crowdsourced Data Collection**: One of the core challenges for Loogle was to build a sufficiently large and reliable database of restroom locations through crowdsourcing. For future versions, implementing incentive mechanisms (e.g., points or rewards) could encourage more users to contribute.
- **Real-time Data Accuracy**: Using a crowdsourced model means data accuracy depends on user contributions. To address this, a verification mechanism could be introduced to validate the locations of restrooms by allowing multiple users to confirm or rate the facility.
- **Scalability**: The application was developed as a prototype during a hackathon, meaning scalability features were not implemented. Future iterations could include backend development to store and manage restroom locations using a cloud database, such as Firebase, which offers real-time updates and scalability.

### **Conclusion**

Loogle represents an innovative solution for locating public restrooms, particularly in urban areas where finding clean and accessible facilities can be challenging. The minimalist interface ensures a smooth user experience, while the crowdsourced model has the potential to create a reliable and constantly improving restroom database. By focusing on user contributions, leveraging Android location services, and using Firebase for real-time data management, Loogle demonstrates how technology can address everyday challenges effectively.
