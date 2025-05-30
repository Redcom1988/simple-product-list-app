# Simple Product List App

A Kotlin Android application built with Jetpack Compose for an internship entry test. This app displays a list of products and allows users to view detailed information for each product.

## Features

- Display a list of products with icons and basic information
- Search/filter products by name
- View detailed product information on a separate screen
- Material 3 design components
- MVVM architecture pattern

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/internshiptestapp/
│   │   ├── base/                      # Base application components
│   │   ├── data/                      # Data layer
│   │   │   ├── model/                 # Data models
│   │   │   └── repository/            # Data repositories
│   │   └── ui/                        # UI layer
│   │       ├── components/            # Reusable UI components
│   │       ├── screens/               # Screen-specific implementations
│   │       │   ├── productdetail/     # Product detail screen
│   │       │   └── productlist/       # Product list screen
│   │       └── theme/                 # Application theming
```

## Technologies Used

- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern Android UI toolkit
- **Material 3** - Design system
- **Navigation Compose** - Navigation framework
- **ViewModel** - State management
- **Kotlin Flow** - Reactive programming
- **Kotlin Serialization** - JSON serialization/deserialization
- **Lucide Icons** - Icon library

## Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- Android SDK with minimum API level 29

### Setup

1. Clone this repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device

## License

This project was created for demonstration purposes as part of an internship application.

## Created by

Redcom1988

## DISCLAIMER
I previously made this without making a git repo, that's the reason why all the commits are so close to each other
