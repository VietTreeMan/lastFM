import de.umass.lastfm.*;

import java.text.DateFormat;
import java.util.Collection;

public class Test {
    public static void main(String [] args){
        String key = "5a78b2b840811b4ab8d26309e2b68ee6"; //this is the key used in the Last.fm API examples
        String user = "Roy19110";
        Chart<Artist> chart = User.getWeeklyArtistChart(user, 10, key);


        Collection<Track> tracks = User.getTopTracks(user, key);

        DateFormat format = DateFormat.getDateInstance();
        String from = format.format(chart.getFrom());
        String to = format.format(chart.getTo());

        System.out.printf("Charts for %s for the week from %s to %s:%n", user, from, to);
        Collection<Artist> artists = chart.getEntries();

        for (Artist artist : artists) {
            System.out.println(artist.getName());
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        for (Track track : tracks) {
            System.out.println(track.getName());
        }

    }
}
