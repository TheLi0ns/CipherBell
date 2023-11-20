import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Mods mod;
    static String text;
    static long key;

    public static void main(String[] args) {
        if(args.length > 0){
            if(Arrays.asList(args).contains("-help") || Arrays.asList(args).contains("--help") || Arrays.asList(args).contains("?")){
                printHelp();
                return;
            }
            try {
                validateArgs(args);
                switch (mod){
                    case ENCODE:
                        System.out.println(Cipher.encode(text, key));
                        break;
                    case DECODE:
                        System.out.println(Cipher.decode(text, key));
                        break;
                }
            }catch (ArgException e) {
                System.out.println(e.getMessage());
            }
        }else normalExec();
    }

    private static void validateArgs(String[] args) throws ArgException {
        if(args.length != 5)
            throw new ArgException(args.length > 5 ? "Too many arguments provided." :
                    "Too few arguments provided. Expected 5 arguments.");

        String mode = args[0].toLowerCase();
        String text_mod = args[1].toLowerCase();
        String key_mod = args[3].toLowerCase();

        if(!mode.equals("-c") && !mode.equals("-d"))
            throw new ArgException("Invalid mode specified. Please select either '-c' for encoding or '-d' for decoding.");
        else mod = mode.equals("-c") ? Mods.ENCODE : Mods.DECODE;

        if(!text_mod.equals("-s") && !text_mod.equals("-fs"))
            throw new ArgException("Invalid text option specified. Please use '-s' for string input or '-fs' for file input.");
        else{
            if(text_mod.equals("-s")) text = args[2];
            else text = readAllLines(readFromFile(args[2]));
        }

        if(!key_mod.equals("-k") && !key_mod.equals("-fk"))
            throw new ArgException("Invalid key option specified. Please use '-k' for string input or '-fk' for file input.");
        else {
            try{
                if(key_mod.equals("-k")) key = Integer.parseInt(args[4]);
                else key = readFromFile(args[4]).nextInt();
                if(String.valueOf(key).contains("0")) throw new InputMismatchException();
            }catch (InputMismatchException | NumberFormatException e){
                throw new ArgException("Invalid key: The key must be a positive integer and must not contain the digit 0.");
            }
        }
    }

    private static void printHelp(){
        System.out.println("Usage: script [option]\n");

        System.out.println("Mandatory Options:");
        System.out.println("    (-c | -d) (-s <string> | -fs <file>) (-k <positive_integer> | -fk <file>)");
        System.out.println();
        System.out.println("Options:");
        System.out.println("   1st parameter - Mode:");
        System.out.println("       -c: Encrypts the text with the provided key");
        System.out.println("       -d: Decrypts the text with the provided key");
        System.out.println();
        System.out.println("   2nd parameter - Text:");
        System.out.println("       -s: Text provided directly as a string");
        System.out.println("       -fs: Reads text from the indicated file as input");
        System.out.println();
        System.out.println("   3rd parameter - Key:");
        System.out.println("       -k: Positive integer key provided directly");
        System.out.println("       -fk: Reads the positive integer key from the indicated file");
        System.out.println();
        System.out.println("Notes:");
        System.out.println("    The key must be a positive integer and must not contain the digit 0.");
        System.out.println("    The text must only contain UTF-8 characters.");
        System.out.println("    The file must be in the same directory as the script or you must specify the absolute path");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("    To encrypt a string \"Hello\" with the key 5: cipherBell -c -s \"Hello\" -k 5");
        System.out.println("    To decrypt an encrypted string \"72F101F108F108F111F\" with the key 5: cipherBell -d -s \"72F101F108F108F111F\" -k 5");
        System.out.println("    To encrypt the text inside the file \"text.txt\" with the key inside the file \"key.txt\": cipherBell -c -fs text.txt -fk key.txt");

    }

    private static void normalExec(){
        Scanner sc = new Scanner(System.in);
        int chose;

        do {
            printMenu();
            chose = sc.nextInt();
            sc.nextLine();

            switch (chose){
                case 1:
                    System.out.println("Enter plainText: ");
                    text = sc.nextLine();

                    System.out.println("Enter key: ");
                    key = sc.nextInt();

                    System.out.println("Encrypted text: ");
                    System.out.println(Cipher.encode(text, key));
                    break;

                case 2:
                    System.out.println("Enter encrypted text: ");
                    text = sc.nextLine();

                    System.out.println("Enter key: ");
                    key = sc.nextInt();

                    System.out.println("Decrypted text: ");
                    System.out.println(Cipher.decode(text, key));
                    break;

                case 0: break;
                default:
                    System.out.println("Error: Invalid option selected. Please choose a valid option.");
                    break;
            }
            System.out.println();
            System.out.println();
        }while (chose != 0);
    }

    private static void printMenu(){
        System.out.println("[1] Encrypt string");
        System.out.println("[2] Decrypt string");
        System.out.println("[0] Exit");
    }

    private static Scanner readFromFile(String path) throws ArgException {
        File f;
        f = new File(path);
        try{
            return new Scanner(f);
        } catch (FileNotFoundException e) {
            throw new ArgException("File not found: " + f.getAbsolutePath());
        }
    }

    private static String readAllLines(Scanner sc){
        StringBuilder text = new StringBuilder();
        while (sc.hasNext()){
            text.append(sc.nextLine()).append("\r\n");
        }
        return text.replace(text.length()-2, text.length(), "").toString();
    }
}