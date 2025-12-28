DES Encryption Algorithm
  Project Description

This project is a Java-based implementation of the Data Encryption Standard (DES) algorithm with RSA-based key management.
It demonstrates secure client–server communication where DES is used for data encryption and RSA is used for secure key exchange.
The project was developed using Apache NetBeans IDE as part of an academic cryptography course.

 Objectives

To understand and implement the DES encryption algorithm
To demonstrate symmetric encryption and decryption
To use RSA for secure key generation and exchange
To implement secure client–server communication in Java

 Technologies Used

Programming Language: Java
IDE: Apache NetBeans
Cryptography: DES, RSA
Architecture: Client–Server Model
Platform: Windows

  Algorithms Used
 🔹 Data Encryption Standard (DES)
DES is a symmetric-key encryption algorithm that encrypts data using a 56-bit secret key.
It operates on 64-bit blocks of plaintext and produces 64-bit ciphertext through multiple rounds of permutation and substitution.

  RSA Algorithm
RSA is an asymmetric encryption algorithm used in this project for:
Secure generation of encryption keys
Secure exchange of DES keys between client and server

📂 Project Structure
DES/
 ├── src/
 │   ├── Client/
 │   │   ├── Client.java
 │   │   └── ClientSender.java
 │   ├── security/
 │   │   ├── DESUtil.java
 │   │   ├── RSAEncrypt.java
 │   │   ├── RSADecrypt.java
 │   │   └── RSAKeyManager.java
 │   ├── server/
 │   │   ├── Server.java
 │   │   └── ServerReceiver.java
 │   └── ServerReceiverGUI.java
 └── manifest.mf

▶️ How to Run the Project
Open Apache NetBeans IDE
Click File → Open Project
Select the DES project folder
Run the Server first
Run the Client
Enter the plaintext message
View encrypted and decrypted outputs

  Features
DES encryption and decryption
RSA-based key management
Secure client–server communication
Modular and well-structured Java code
Educational and academic-focused implementation

  Applications
Learning cryptography concepts
Academic projects and demonstrations
Secure communication simulations

⚠️ Limitations
DES is not recommended for modern high-security applications
Intended for educational purposes only

  Author
 Jirraa
 Final-Year Cryptography Project

📜 License
This project is for educational purposes only.