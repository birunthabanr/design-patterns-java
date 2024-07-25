import java.util.*;

interface ThirdPartyYouTubeLib {
    void listVideos();
    void getVideoInfo(String id);
    void downloadVideo(String id);
}

// The concrete implementation of a service connector. 
// Methods of this class can request information from YouTube. 
// The speed of the request depends on a user's internet connection as well as YouTube's.
class ThirdPartyYouTubeClass implements ThirdPartyYouTubeLib {

    @Override
    public void listVideos() {
        // Send an API request to YouTube.
        System.out.println("Listing videos from YouTube...");
        // Simulate a slow operation
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Videos listed.");
    }

    @Override
    public void getVideoInfo(String id) {
        // Get metadata about some video.
        System.out.println("Fetching video info for ID: " + id + " from YouTube...");
        // Simulate a slow operation
        try {
            Thread.sleep(1000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Video info retrieved.");
    }

    @Override
    public void downloadVideo(String id) {
        // Download a video file from YouTube.
        System.out.println("Downloading video ID: " + id + " from YouTube...");
        // Simulate a slow operation
        try {
            Thread.sleep(3000); // Sleep for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Video downloaded.");
    }
}

// Proxy class to add caching capabilities
class CachedYouTubeClass implements ThirdPartyYouTubeLib {
    private ThirdPartyYouTubeLib service;
    private List<String> listCache = null;
    private Map<String, String> videoCache = null;
    private boolean needReset;

    public CachedYouTubeClass(ThirdPartyYouTubeLib service) {
        this.service = service;
        this.listCache = new ArrayList<>();
        this.videoCache = new HashMap<>();
    }

    @Override
    public void listVideos() {
        if (listCache.isEmpty() || needReset) {
            System.out.println("Cache miss: Fetching video list from service.");
            service.listVideos();
            // Simulating cache storage
            listCache.add("Video List");
            needReset = false;
        } else {
            System.out.println("Cache hit: Using cached video list.");
            // Display cached video list
            System.out.println("Videos listed from cache.");
        }
    }

    @Override
    public void getVideoInfo(String id) {
        if (!videoCache.containsKey(id) || needReset) {
            System.out.println("Cache miss: Fetching video info for ID: " + id + " from service.");
            service.getVideoInfo(id);
            // Simulating cache storage
            videoCache.put(id, "Video Info");
            needReset = false;
        } else {
            System.out.println("Cache hit: Using cached video info for ID: " + id + ".");
            // Display cached video info
            System.out.println("Video info retrieved from cache.");
        }
    }

    @Override
    public void downloadVideo(String id) {
        if (!downloadExists(id) || needReset) {
            System.out.println("Cache miss: Downloading video ID: " + id + " from service.");
            service.downloadVideo(id);
            // Simulating download existence
            System.out.println("Downloaded video ID: " + id + " stored in cache.");
            needReset = false;
        } else {
            System.out.println("Cache hit: Video ID: " + id + " already downloaded.");
        }
    }

    private boolean downloadExists(String id) {
        // Simulate download existence check
        return videoCache.containsKey(id);
    }
}

// The GUI class, which used to work directly with a service object, 
// stays unchanged as long as it works with the service object through an interface. 
// We can safely pass a proxy object instead of a real service object 
// since they both implement the same interface.
class YouTubeManager {
    protected ThirdPartyYouTubeLib service;

    public YouTubeManager(ThirdPartyYouTubeLib service) {
        this.service = service;
    }

    public void renderVideoPage(String id) {
        service.getVideoInfo(id);
        // Render the video page.
        System.out.println("Rendering video page for ID: " + id);
    }

    public void renderListPanel() {
        service.listVideos();
        // Render the list of video thumbnails.
        System.out.println("Rendering list panel with video thumbnails.");
    }

    public void reactOnUserInput(String videoId) {
        renderVideoPage(videoId);
        renderListPanel();
    }
}

// The application can configure proxies on the fly.
class example1 {
    public void init() {
        ThirdPartyYouTubeLib aYouTubeService = new ThirdPartyYouTubeClass();
        ThirdPartyYouTubeLib aYouTubeProxy = new CachedYouTubeClass(aYouTubeService);
        YouTubeManager manager = new YouTubeManager(aYouTubeProxy);
        
        // Simulating user interaction
        System.out.println("\nUser interacts with video ID: abc123");
        manager.reactOnUserInput("abc123");
        
        // Simulating a second request to demonstrate caching
        System.out.println("\nUser interacts again with video ID: abc123");
        manager.reactOnUserInput("abc123");
        
        // Simulating a new video request
        System.out.println("\nUser interacts with a new video ID: xyz789");
        manager.reactOnUserInput("xyz789");
    }
    
    public static void main(String[] args) {
        example1 app = new example1();
        app.init();
    }
}
