RLStatsJava
=
**Note: RocketLeagueStats has shut down. The API can no longer be used.**  
Java API Client for https://rocketleaguestats.com/

Usage
-
  
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
	}
	
	public void foo(RLStatsAPI api) {
		// If your application can wait then you can block the thread
		// until the request is finished and skip the Future<> object.
		List<Playlist> playlists = api.getPlaylistInfoBlocking();
	}
	
}

```
