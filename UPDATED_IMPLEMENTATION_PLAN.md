# AgriMarket Uganda – Updated Implementation Plan

## Objective
To guide the development of the AgriMarket Uganda Android MVP, focusing on a feature-by-feature implementation flow for easier testing and bug tracking, while ensuring all specified project requirements are met.

## Overarching Principles
- Incremental Development: Build and test features in vertical slices
- Requirement Tracking: Each phase explicitly addresses relevant project requirements
- Early Testing: Test each feature module as it's completed
- Version Control: Commit frequently after each logical step

## Phase 0: Project Foundation & Initial Setup

### Step 0.1: Environment, Firebase, and Android Project Initialization

#### Environment & Accounts
- Ensure Android Studio and Kotlin are ready
- Create/access Firebase account

#### Firebase Project Setup
- Create "AgriMarket Uganda" project in Firebase Console
- Register the Android app with its package name
- Download google-services.json

#### Android Studio Project Creation
- Create a new Android Studio project using an "Empty Compose Activity" template
- Configure name, package name, language (Kotlin), and Min SDK

#### Firebase Integration in Android Project
- Place google-services.json in the app directory
- Add Firebase BoM and Google Services plugin to build.gradle files

#### Enable Firebase Services
- Authentication: Enable "Email/Password" sign-in method
- Firestore Database: Create Firestore in "Production mode," select a region

#### Firestore Security Rules (Initial)
- Implement basic rules: Allow authenticated reads for commodities, disallow client writes (or admin-only). Publish.

#### Basic Android Project Configuration
- Add INTERNET permission to AndroidManifest.xml
- Create the planned directory structure (data, ui, navigation, etc.)
- Add initial dependencies (Core Kotlin, basic Compose) to app/build.gradle. Sync.

## Phase 1: Theming & Visual Foundation

### Step 1.1: Define Visual Identity
- Custom Launcher Icon
- Color Scheme (Primary, Secondary, Background)
- Typography (Google Fonts)
- Shapes (Rounded corners)
- Material Theme Implementation

### Step 1.2: Resource Management Setup
- String Externalization
- Dimension Management

## Phase 2: Authentication Module (Login & Registration Screens)

### Step 2.1: Data Layer for Authentication
- Data Model (User.kt - optional)
- Repository Interface (AuthRepository.kt): Define methods for sign-in, register, get current user
- Repository Implementation (AuthRepositoryImpl.kt):
  - Implement sign-in logic
  - Implement registration logic
  - Implement getCurrentUser()
- DI for Auth

### Step 2.2: ViewModel for Authentication (AuthViewModel.kt)
- Create AuthViewModel extending ViewModel
- Inject AuthRepository
- Expose StateFlow for login state and registration state
- Implement login(email, password) function
- Implement register(email, password, name) function
- Implement function to check initial login state

### Step 2.3: UI for Login Screen (LoginScreen.kt)
- Layout & Components (TextFields, Buttons)
- User Input & Validation
- State Observation & UI Updates
- Interactivity (Button clicks, navigation to registration)
- Styling (Apply custom theme)

### Step 2.4: UI for Registration Screen (RegisterScreen.kt)
- Layout & Components (TextFields for email, password, name, Buttons)
- User Input & Validation
- State Observation & UI Updates
- Interactivity (Button clicks, navigation back to login)
- Styling (Apply custom theme)

### Step 2.5: Navigation Setup (Initial)
- Navigation Routes (Screen.kt): Define Screen.Login, Screen.Register, and Screen.Home
- Navigation Host (AppNavigation.kt)

### Step 2.6: Testing Authentication Module
- Unit Tests
- UI Tests
- Manual Tests

## Phase 3: Core Application - Commodity Listing (Home Screen)

### Step 3.1: Data Layer for Commodities
- Data Models (PriceDetail.kt, Commodity.kt)
- Repository Interface (CommodityRepository.kt)
- Repository Implementation (CommodityRepositoryImpl.kt)
- DI for Commodities
- Populate Firestore

### Step 3.2: ViewModel for Home Screen (HomeViewModel.kt)
- Create HomeViewModel
- Inject CommodityRepository
- Expose StateFlow for commodities
- Fetch commodities from repository

### Step 3.3: UI for Home Screen (HomeScreen.kt)
- Layout & App Bar
- Commodity List (LazyColumn)
- Commodity Item Card (CommodityCard.kt)
- Interactivity & Navigation
- Animation
- Styling

### Step 3.4: Testing Commodity Listing Module
- Unit Tests
- UI Tests
- Manual Tests

## Phase 4: Commodity Detail Screen

### Step 4.1: ViewModel for Detail Screen (DetailViewModel.kt)
- Create DetailViewModel
- Inject CommodityRepository and SavedStateHandle
- Retrieve commodityId and fetch specific commodity details

### Step 4.2: UI for Detail Screen (DetailScreen.kt)
- Layout & App Bar
- Data Display (Commodity info and district prices)
- Navigation (Back button)
- Styling

### Step 4.3: Testing Commodity Detail Module
- Unit Tests
- UI Tests
- Manual Tests

## Phase 5: Final Review, Use Case Definition, and Polishing

### Step 5.1: Define Use Case and Scope in Documentation
- Formalize use case and scope

### Step 5.2: Comprehensive Code and Resource Review
- Check for hardcoded strings
- Review dimension management
- Verify clean architecture
- Check styling consistency
- Verify configuration change handling

### Step 5.3: Final Testing & Bug Fixing
- Regression testing
- Test on various devices
- Fix remaining bugs

### Step 5.4: Prepare Deliverables
- Generate signed APK
- Finalize source code
- Update documentation
- Prepare presentation materials 