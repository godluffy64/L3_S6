import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    // Replace with your own Debug folder path
    // Under Visual Studio Code, build the CMake project then right-click your
    // build/Debug folder, select 'Reveal in File Explorer'
    // Make sure the path to the test.txt file is correct!
    private static String watchFile = "C:/Users/AslaN/Desktop/testBureau.txt";

    private static FileWatcher watcher;
    private static Thread thread;

    private static volatile boolean modified;

    private static synchronized void setModified() {
        modified = true;
    }

    private static synchronized void clearModified() {
        modified = false;
    }

    private static synchronized boolean isModified() {
        return modified;
    }

    private static void write(String contents) throws FileNotFoundException {
        System.out.println("Java client sends the following to server: " + contents);
        PrintWriter pw = new PrintWriter(watchFile);
        pw.println(contents);
        pw.close();
    }

    private static String read() throws IOException {
        clearModified();
        while (!isModified()) {
            // Wait
        }

        String contents = Files.readAllLines(Paths.get(watchFile), StandardCharsets.UTF_8).get(0);
        System.out.println("C++ server response: " + contents);
        return contents;
    }

    public static void main(String[] args) {

        // Set things up
        watcher = new FileWatcher(watchFile) {
            @Override
            public void onModified() {
                setModified();
            }
        };

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    watcher.watchFile();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        try {
            // Client/server string exchange, performs the actual interop between Java and
            // C++
            write("do_something");
            String response = read();
            write("do_something_else");
            response = read();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Complete.");
    }
}
