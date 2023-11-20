# CipherBell
A java application to encrypt and decrypt strings using an integer key. It can be used via command line

# How to use it
it can be used via [command line](#command-line-help) like a native tool  
or just run it to use its cli interface

## Command line help:

### Mandatory Options:

`(-c | -d) (-s <string> | -fs <file>) (-k <positive_integer> | -fk <file>)`

### Options:

- **First parameter - Mode:**
    - `-c`: Encrypts the text with the provided key
    - `-d`: Decrypts the text with the provided key

- **Second parameter - Text:**
    - `-s`: Text provided directly as a string
    - `-fs`: Reads text from the indicated file as input

- **Third parameter - Key:**
    - `-k`: Positive integer key provided directly
    - `-fk`: Reads the positive integer key from the indicated file

### Notes:
- The key must be a positive integer and must not contain the digit 0.
- The text must only contain UTF-8 characters.
- The files must be in the same directory as the script or you have to specify the absolute path

### Examples:

- To encrypt a string "Hello" with the key 5: `cipherBell -c -s "Hello" -k 5`
- To decrypt an encrypted string "72F101F108F108F111F" with the key 5: `cipherBell -d -s "72F101F108F108F111F" -k 5`
- To encrypt the text inside the file "text.txt" with the key inside the file "key.txt"
    - the files are in the same directory as the script: `cipherBell -c -fs text.txt -k key.txt`
    - the files are in a different directory
        - current directory
            - Windows: `cipherBell -c -fs %cd%\text.txt -k %cd%\key.txt`
            - Unix-like (Linux, macOS): `cipherBell -c -fs $(pwd)/text.txt -k $(pwd)/key.txt`
        - just specify full path: `cipherBell -c -fs C:\...\text.txt -k C:\...\key.txt`
