RLStatsJava
=
Java API Client for https://rocketleaguestats.com/

Maven
-
```xml
<repository>
    <id>ssplugins</id>
    <url>http://ssplugins.com/repo/</url>
</repository>

<dependency>
    <groupId>com.ssplugins.rlstats</groupId>
    <artifactId>RLStatsAPI</artifactId>
    <version>1.0.1-beta</version>
</dependency>
```

Download
-
If you don't use Maven, you can check the [Releases Page](https://github.com/567legodude/RLStatsJava/releases).

Support
-
For help with the RocketLeagueStats API head over to the [Discord Server](https://discord.gg/Cq2naUu). For help with this library contact me in the Discord server or DM StarShadow#3546

Javadoc
-
Javadocs are here: [Javadoc](http://ssplugins.com/docs/java/RLStatsAPI/)

Usage
-
You will need an API key for RocketLeagueStats in order to use the API.  
API keys are available at the [Developer Portal](https://developers.rocketleaguestats.com/).
  
**Important:** Your application should take care to call [RLStatsAPI#shutdownThreads()](http://ssplugins.com/docs/java/RLStatsAPI/com/ssplugins/rlstats/RLStatsAPI.html#shutdownThreads--) when it it done using an instance of the API (i.e. about to close) or the application may not shutdown correctly.

Example getting player info.
```java
import com.ssplugins.rlstats.entities.Platform;
import com.ssplugins.rlstats.entities.Player;
import com.ssplugins.rlstats.entities.Playlist;
import com.ssplugins.rlstats.entities.PlaylistInfo;

import java.util.List;
import java.util.concurrent.Future;

public class GetStats {
	
	public GetStats(String key, String steamID) {
		// Get API
		RLStatsAPI api = RLStats.getAPI(key);
		// Make a request
		Future<Player> future = api.getPlayer(steamID, Platform.STEAM);
		
		// ... Do something to wait until API request is finished.
		
		Player player = future.get();
		PlaylistInfo info = player.getSeasonInfo(5).getPlaylistInfo(Playlist.RANKED_DOUBLES);
		int tier = info.getTier();
		int division = info.getDivision();
		
		// Should be called when you are finished using the API with this instance.
		// If not called your application may not shutdown correctly.
		api.shutdownThreads();
	}
	
	public void foo(RLStatsAPI api) {
		// If your application can wait then you can block the thread
		// until the request is finished and skip the Future<> object.
		List<Playlist> playlists = api.getPlaylistInfoBlocking();
	}
	
}

```
