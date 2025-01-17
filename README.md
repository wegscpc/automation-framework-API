# automation-framework-API
 This is an Automatation Framework using Java and RestAssure for API testing.
 https://api.practicesoftwaretesting.com/api/documentation#/

 Here's a summary of the automation framework structure and its key improvements:

1. Framework Architecture:
 - Uses a modular structure with clear separation of concerns
 - Built using Java with RestAssured for API testing
 - TestNG as the testing framework
 - Allure for reporting
 - Maven for dependency management

2. Key Components:
 - base/: Contains BaseTest.java with common test functionality and setup
 - config/: Handles configuration management
 - models/: Data models and POJOs
 - services/: API service layer implementations
 - tests/: Test implementations
 - utils/: Utility classes for logging, data generation, and response validation

3. Notable Improvements:
 a. Robust Base Framework:
  - Centralized request specification setup
  - Common validation methods with Allure step annotations
  - Automated logging for failed requests/responses
 b. Enhanced Logging and Reporting:
  - Custom TestLogger implementation
  - Allure integration for detailed test reporting
  - Step-by-step test execution documentation
 c. Data Management:
  - Separate configuration management
  - Data model encapsulation
  - Test data generation utilities
 d. Validation and Assertions:
  - Dedicated ResponseValidator for response checking
  - Reusable validation methods
  - Detailed assertion messages

4. Best Practices Implementation:
- Clear package structure
- Separation of concerns
- Reusable components
- Detailed logging and reporting
- Configuration externalization

The framework follows modern automation practices with a focus on maintainability, reusability, and detailed reporting. The modular structure makes it easy to extend and modify while keeping the code organized and clean.
