# Contact App

This is a simple contact management app built using the MVVM (Model-View-ViewModel) architecture in Android. The app leverages Room for local database storage and includes an image picker functionality.

---

### Features :

1. Add new contacts with a name, phone number, and optional profile picture.

2. Edit or delete existing contacts.

3. View a list of all saved contacts.

---

### Technologies and Libraries Used :

1. MVVM Architecture
- Used to separate concerns and ensure clean architecture.
- ViewModels handle UI-related data in a lifecycle-conscious way.

2. Room Database
- Used for local database storage to save contact information.
- Provides an abstraction layer over SQLite to allow robust database management.

3. Image Picker Library
- Allows users to select or capture a profile picture for each contact.
- Library used: Image Picker by Dhaval2404.

---

### Dependencies Used :

1. Add the following dependencies to your build.gradle file:

- ```val roomVersion = "2.6.1"``` 

- ```implementation("androidx.room:room-runtime:$roomVersion")``` // Room Database

2. ```annotationProcessor("androidx.room:room-compiler:$roomVersion")``` // Kotlin Symbol Processing (KSP) for Room

3. ```ksp("androidx.room:room-compiler:$roomVersion")``` // Image Picker Library

4. ```implementation("com.github.dhaval2404:imagepicker:2.1")```

---

### Project Structure :

```yaml
com.example.phoneapp
├── MVVM_arch
│   ├── AddEditActivityViewModel.kt    // ViewModel for Add/Edit screen
│   └── MainActivityViewModel.kt       // ViewModel for MainActivity
│
├── RoomDB
│   ├── DAO
│   │   └── ContactDAO.kt         // Data Access Object for Contact operations
│   ├── Entity
│   │   ├── Contact.kt           // Data class for Contact entity
│   │   └── ApnaDatabase.kt      // Room database setup
│   └── DbBuilder.kt                // Singleton for database initialization
│
├── AddEditActivity.kt                   // Activity for adding/editing a contact
├── ContactAdapter.kt                     // RecyclerView Adapter for displaying contacts
├── MainActivity.kt                       // Main activity displaying the contact list
└── Utils.kt                              // Utility functions
```

---

### How to Use :
1. Clone the repository
```git clone <repository-url>```

2. Open the project in Android Studio.

3. Add the required dependencies to your build.gradle file if not already included.

4. Run the app on an emulator or a physical device.

5. Use the app to manage your contacts by adding, editing, and deleting them.

---

### Screenshots & App Demo Video:

(Will upload these soon.)

---

### Future Improvements

1. Add search functionality to quickly find contacts.

2. Implement cloud sync to back up contacts.

3. Add sorting and filtering options.

---

### License

This project is open-source and available under the MIT License.
