import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public abstract class FileWatcher
{
    private Path folderPath;
    private String watchFile;
    private boolean m_ShouldTerminate = false;

    public FileWatcher(String watchFile)
    {
        Path filePath = Paths.get(watchFile);

        boolean isRegularFile = Files.isRegularFile(filePath);

        if (!isRegularFile)
        {
            // Do not allow this to be a folder since we want to watch files
            throw new IllegalArgumentException(watchFile + " is not a regular file");
        }

        // This is always a folder
        folderPath = filePath.getParent();

        // Keep this relative to the watched folder
        this.watchFile = watchFile.replace(folderPath.toString() + File.separator, "");
    }
    
    public synchronized void requestTermination()
    {
    	m_ShouldTerminate = true;
    }

    public synchronized boolean shouldTerminate()
    {
    	return m_ShouldTerminate;
    }

    public void watchFile() throws Exception
    {
        // We obtain the file system of the Path
        FileSystem fileSystem = folderPath.getFileSystem();

        // We create the new WatchService using the try-with-resources block
        try (WatchService service = fileSystem.newWatchService())
        {
            // We watch for modification events
            folderPath.register(service, StandardWatchEventKinds.ENTRY_MODIFY);

            // Start the infinite polling loop
            while (true)
            {
            	if (shouldTerminate())
            		break;
            	
                // Wait for the next event
                WatchKey watchKey = service.take();

                for (WatchEvent<?> watchEvent : watchKey.pollEvents())
                {
                    // Get the type of the event
                    Kind<?> kind = watchEvent.kind();

                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY)
                    {
                        Path watchEventPath = (Path) watchEvent.context();

                        // Call this if the right file is involved
                        if (watchEventPath.toString().equals(watchFile))
                        {
                            onModified();
                        }
                    }
                }

                if (!watchKey.reset())
                {
                    // Exit if no longer valid
                    break;
                }
            }
        }
    }

    public abstract void onModified();
}
