# ST10496805 - PROG5121 POE (Final Submission)

## Student Details
* **Student Name:** Kutlwano Mogwane
* **Student ID:** ST10496805
* **Module:** Programming 1A (PROG5121)

---

## Submission Links
* **GitHub Repository:** https://github.com/Mogwane19/ST10496805_PROG5121POE_2026.git

---

## Project Overview

### Part 1: User Registration & Authentication System
* Implements user registration with custom field validations.
* **Username Check:** Validates length ($\le$ 5 characters) and requires an underscore (`_`).
* **Password Complexity:** Validates length ($\ge$ 8 characters), requiring a capital letter, a number, and a special character.
* **Cellphone Validation:** Validates South African international format (`+27...`).
* Includes automated unit testing via JUnit 4 (`LoginTest.java`).

### Part 2: QuickChat Messaging System
* Implements the interactive `Message` class and numeric navigation menu in `Main.java`.
* **Message ID:** Auto-generates a random 10-digit ID.
* **Message Hash:** Generates a formatted hash (`ID_PREFIX:MSG_NUM:FIRST_WORD:LAST_WORD`).
* **Message Restrictions:** Restricts message body length to a maximum of 250 characters.
* **Action Handling:** Options to Send, Disregard, or Store messages (with JSON file export).

### Part 3: Store Data, Array Management & Task Reports
* Implements dynamic **ArrayLists** to manage sent messages, disregarded messages, stored messages, message hashes, and message IDs[cite: 11].
* **Fourth Main Menu Option ("Stored Messages"):** Provides a dedicated sub-menu offering:
  * Displaying sender and recipient details for all stored messages[cite: 11].
  * Calculating and displaying the longest stored/sent message[cite: 11].
  * Searching for messages by unique **Message ID**[cite: 11].
  * Searching for all messages associated with a particular **recipient**[cite: 11].
  * Deleting messages using their unique **Message Hash**[cite: 11].
  * Generating a **Full Task Report** listing all message details[cite: 11].
* Includes an expanded JUnit test suite (`MessageTest.java`) validating array population, searches, longest message calculations, and hash deletions[cite: 12].

---

## How to Run the Application
1. Open the project folder in Visual Studio Code.
2. Run **`Main.java`** to launch the interactive console application.
3. Complete user registration and login, then select from the main menu options (including the new Stored Messages menu).
4. Run automated unit test suites by executing **`LoginTest.java`** and **`MessageTest.java`**.