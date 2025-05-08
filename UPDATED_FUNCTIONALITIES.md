# AgriMarket Uganda - Updated Functionalities

Based on the revised, requirement-focused implementation plan, the AgriMarket Uganda MVP application will have the following functionalities for the end-user:

## I. User Authentication & Access

### Secure Login and Registration
- Users can log in to the application using their email address and a password
- New users can register with their email, password, and name
- Registration form provides validation for all input fields
- Feedback on registration attempt (success/failure messages)

### Input Validation
- The login and registration forms will provide basic validation for email and password fields (e.g., checking if they are filled, email format validation)

### Feedback on Authentication Attempts
- Users will see visual feedback during login/registration processes (e.g., loading indicators)
- Clear error messages if authentication fails (e.g., "Invalid credentials," "User not found," "Email already in use")

### Authenticated Access to Data
- Only users who have successfully logged in will be able to view the commodity price information

## II. Commodity Price Information & Discovery

### View Commodity List (Home Screen)
- After logging in, users will see a scrollable list of agricultural commodities
- This list will display at least five items

### Information per Commodity in List
- Each item in the commodity list will display:
  - The name of the commodity
  - An image representing the commodity (for at least three items in the list). Images will have rounded corners
  - The average market price for that commodity (calculated across available districts)

### View Detailed Commodity Information (Detail Screen)
- Users can tap on any commodity in the list to navigate to a dedicated detail screen for that specific commodity

### Detailed Price Breakdown
- The Detail Screen will show:
  - The name of the selected commodity
  - An image of the commodity
  - A breakdown of prices by district (initially 7 districts):
    - District Name
    - Farm Gate Price (FGP) for that commodity in that district
    - Market Price (MP) for that commodity in that district

## III. User Interface & Experience

### Consistent Visual Design
- The app will have a unified look and feel across all screens
- Custom App Icon: A unique icon for the app on the device
- Material Theming: Utilizes a custom color scheme (primary, secondary, background), custom (non-default) fonts, and defined shapes (e.g., rounded corners for cards and images)
- App Bar: A consistent top app bar for navigation (e.g., screen titles, back button) and branding

### Multi-Screen Navigation
- Users can smoothly navigate between different screens (Login, Register, Home, Detail)

### Interactive Elements & Feedback
- Clickable components (buttons, list items) will provide visual feedback (e.g., ripple effect)
- At least one animation will be implemented (e.g., fade-in of list items)

### Responsive to Device Changes
- The app will handle configuration changes like screen rotation without losing user data or the current state (e.g., scroll position, entered text before submission)

## Functionalities Derived from the Problem Statement & Use Case

- Access to Market Price Information: The core functionality allows users (farmers, traders, etc.) to access agricultural commodity prices
- Enhanced Market Transparency: By providing structured price data, the app aims to improve transparency
- Informed Decision-Making: The provided FGP and MP data across districts helps users make better selling/buying decisions 