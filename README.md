# BIN Card Info

An Android application that retrieves bank card information by its BIN number. The app uses the [Binlist API](https://binlist.net/) to fetch data and is built following Clean Architecture principles with the MVVM pattern.

## Features

- **BIN Number Lookup:** Enter the first 6–8 digits of a bank card to get detailed information about the issuing bank and card type.
- **Clean Architecture:** Clear separation of concerns using distinct layers (Domain, Data, Presentation) for improved maintainability.
- **MVVM Pattern:** Simplifies state management and testing.
- **API Integration:** Retrieves real-time data from [Binlist](https://binlist.net/).
- **Local Caching:** Uses ROOM to store data locally for faster access and offline support.

## Technologies Used

- **Kotlin & Coroutines:** For modern Android development and asynchronous programming.
- **Retrofit:** To manage REST API requests.
- **ROOM:** For local data persistence.
- **Hilt:** For dependency injection.
- **MVVM:** To separate UI and business logic.

## Installation & Setup
1. **Clone the repository:**

   ```bash
   git clone https://github.com/SemkaHub/BinScope.git

2. **Open the project in Android Studio.**

3. **Sync Gradle:** Android Studio will prompt you to download the necessary dependencies.

4. **Run the App:** Select an emulator or connected device and click "Run".
